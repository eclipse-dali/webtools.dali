/*******************************************************************************
 * <copyright>
 *
 * Copyright (c) 2005, 2010 SAP AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Stefan Dimov - initial API, implementation and documentation
 *
 * </copyright>
 *
 *******************************************************************************/
package org.eclipse.jpt.jpadiagrameditor.ui.internal.modelintegration.util;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.WeakHashMap;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.ui.editor.DiagramEditorFactory;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.JPADiagramEditorPlugin;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.propertypage.JPADiagramPropertyPage;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.JPAEditorDiagramTypeProvider;


public class ModelIntegrationUtil {

	public static final String DIAGRAM_FILE_EXTENSION = "xmi"; 		//$NON-NLS-1$
	public static final String JPA_DIAGRAM_TYPE = "JPA Diagram"; 	//$NON-NLS-1$
	public static final String DEFAULT_RES_FOLDER = "src";		//$NON-NLS-1$
	private static WeakHashMap<Diagram, WeakReference<JpaProject>> diagramsToProjects = new WeakHashMap<Diagram, WeakReference<JpaProject>>();
	private static WeakHashMap<Diagram, WeakReference<JPAEditorDiagramTypeProvider>> diagramsToProviders = new WeakHashMap<Diagram, WeakReference<JPAEditorDiagramTypeProvider>>();
	private static HashMap<Diagram, Resource> diagramsToResources = new HashMap<Diagram, Resource>();

	
	public static IPath createDiagramPath(PersistenceUnit persistenceUnit) throws CoreException {
	    IProject project = persistenceUnit.getJpaProject().getProject();
	    IPath projectPath = project.getFullPath();
	    String diagramName = persistenceUnit.getName();
		IPath path = getDiagramsFolderPath(project).append(diagramName).addFileExtension(DIAGRAM_FILE_EXTENSION);
		path = projectPath.append(path);
	    return path;		
	}
	
	public static Diagram createDiagram(PersistenceUnit persistenceUnit, 
										int grid, 
										boolean snap) throws CoreException {
		IPath path = createDiagramPath(persistenceUnit);
		Diagram d = createDiagram(persistenceUnit.getJpaProject().getProject(), path, persistenceUnit.getName(), grid, snap); 
	    return d;
	}
	
	public static TransactionalEditingDomain getTransactionalEditingDomain(Diagram diagram) {
		TransactionalEditingDomain editingDomain = TransactionUtil.getEditingDomain(diagram);
		return editingDomain;
	}
	
	public static ResourceSet getResourceSet(Diagram diagram) {
		WeakReference<JpaProject> ref = diagramsToProjects.get(diagram);
		TransactionalEditingDomain defaultTransEditDomain = (TransactionalEditingDomain)ref.get().getProject().getAdapter(TransactionalEditingDomain.class);
		ResourceSet resourceSet = defaultTransEditDomain.getResourceSet();
		return resourceSet;
	}
	
	private static class Wrp {
		private Object o;
		public Object getObject() {
			return o;
		}
		
		public void setObject(Object o) {
			this.o = o;
		}
	}
	
	public static Diagram createDiagram(IProject project, 
									 IPath diagramFileName,
									 String diagramName,
									 int grid, 
									 boolean snap) {
		
		TransactionalEditingDomain defaultTransEditDomain = DiagramEditorFactory.createResourceSetAndEditingDomain();
		ResourceSet resourceSet = defaultTransEditDomain.getResourceSet();
		IFile diagramFile = project.getFile(diagramFileName.removeFirstSegments(1));
		IPath diagramFilePath = diagramFile.getFullPath();

		String pathName = diagramFilePath.toString();
		URI resourceURI = URI.createPlatformResourceURI(pathName, true);
		final Resource resource = resourceSet.createResource(resourceURI);
		//resource.setTrackingModification(false);
			//(ResourceSetManager.getProjectForResourceSet(resourceSet));
		defaultTransEditDomain.getCommandStack().execute(new RecordingCommand(defaultTransEditDomain) {
			@Override
			protected void doExecute() {
				try {
					resource.load(null);
				} catch (IOException e) {
					JPADiagramEditorPlugin.logInfo("The diagram file does not exist. It will be created");
					JPADiagramEditorPlugin.logInfo(e.getLocalizedMessage());
				}
			}
		});
		
		if (!resource.isLoaded())
			return createNewDiagram(defaultTransEditDomain, resourceSet, resource, diagramName, grid, snap);
		
		EList<EObject> objs = resource.getContents();
		if (objs == null) 
			return createNewDiagram(defaultTransEditDomain, resourceSet, resource, diagramName, grid, snap);
		Iterator<EObject> it = objs.iterator();
		while (it.hasNext()) {
			EObject obj = it.next();
			if ((obj == null) && !Diagram.class.isInstance(obj))
				continue;
			Diagram d = (Diagram)obj;
			diagramsToResources.put(d, resource);
			defaultTransEditDomain.getCommandStack().flush();
			return d;
		}
		return createNewDiagram(defaultTransEditDomain, resourceSet, resource, diagramName, grid, snap);
	}
	
	private static Diagram createNewDiagram(TransactionalEditingDomain editingDomain,
											ResourceSet resourceSet,
											final Resource resource,
											final String diagramName,
											final int grid, 
											final boolean snap) {
		final Wrp wrp = new Wrp();
		editingDomain.getCommandStack().execute(new RecordingCommand(editingDomain) {
			@Override
			protected void doExecute() {
				Diagram diagram = Graphiti.getPeService().createDiagram(JPA_DIAGRAM_TYPE, diagramName, grid, snap);
				wrp.setObject(diagram);
				resource.getContents().add(diagram);
				try {
					resource.save(Collections.EMPTY_MAP);
				} catch (IOException e) {
					JPADiagramEditorPlugin.logError("Cannot create new diagram", e); //$NON-NLS-1$
				}				
			}
		});	
		editingDomain.getCommandStack().flush();
		mapDiagramToProject((Diagram)wrp.getObject(), resource);
		return (Diagram)wrp.getObject();		
	}
	
	public static IPath getResourceFolderPath(IProject project) {
		return getEMFResourceFolderPath(project);
	}
		
	public static IPath getEMFResourceFolderPath(IProject project) {
		return project.getProjectRelativePath().append(DEFAULT_RES_FOLDER);		
	}	
	
	public static IPath getDiagramsFolderPath(IProject project) {
		Properties props = JPADiagramPropertyPage.loadProperties(project);		
		return new Path(JPADiagramPropertyPage.getDefaultFolder(project, props));
	}
	
	public static void mapDiagramToProject(Diagram diagram, JpaProject project) {
		diagramsToProjects.put(diagram, new WeakReference<JpaProject>(project));
	}
	
	public static void mapDiagramToProject(Diagram diagram, Resource resource) {
		diagramsToResources.put(diagram, resource);
	}
	
	public static JpaProject removeDiagramProjectMapping(Diagram diagram) {
		WeakReference<JpaProject> ref = diagramsToProjects.remove(diagram);
		diagramsToProviders.remove(diagram);
		if (ref == null)
			return null;
		return ref.get();
	}
	
	public static Resource removeDiagramResourceMapping(Diagram diagram) {
		return diagramsToResources.remove(diagram);
	}
	
	public static JpaProject getProjectByDiagram(Diagram diagram) {
		WeakReference<JpaProject> ref = diagramsToProjects.get(diagram);
		if (ref == null)
			return null;
		return ref.get();		
	}
	
	public static Resource getResourceByDiagram(Diagram diagram) {
		return diagramsToResources.get(diagram);
	}	
	
	public static JPAEditorDiagramTypeProvider getProviderByDiagram(Diagram diagram) {
		WeakReference<JPAEditorDiagramTypeProvider> ref = diagramsToProviders.get(diagram);
		if (ref == null)
			return null;
		return ref.get();		
	}
	
	public static void mapDiagramToProvider(Diagram diagram, JPAEditorDiagramTypeProvider provider) {
		diagramsToProviders.put(diagram, new WeakReference<JPAEditorDiagramTypeProvider>(provider));
	}
	
	
	
}
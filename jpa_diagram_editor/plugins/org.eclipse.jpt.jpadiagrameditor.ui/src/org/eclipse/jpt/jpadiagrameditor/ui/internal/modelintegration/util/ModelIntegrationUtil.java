/*******************************************************************************
 * <copyright>
 *
 * Copyright (c) 2005, 2012 SAP AG and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Stefan Dimov - initial API, implementation and documentation
 *
 * </copyright>
 *
 *******************************************************************************/
package org.eclipse.jpt.jpadiagrameditor.ui.internal.modelintegration.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.Iterator;
import java.util.Properties;
import java.util.WeakHashMap;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.filesystem.IFileSystem;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
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
import org.eclipse.graphiti.ui.services.GraphitiUi;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.JpaProjectManager;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.JPADiagramEditor;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.JPADiagramEditorPlugin;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.i18n.JPAEditorMessages;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.propertypage.JPADiagramPropertyPage;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.JPAEditorDiagramTypeProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JpaArtifactFactory;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;


public class ModelIntegrationUtil {

	public static final String DIAGRAM_FILE_EXTENSION = "xmi"; 		//$NON-NLS-1$
	public static final String DIAGRAM_XML_FILE_EXTENSION = "xml"; 		//$NON-NLS-1$
	public static final String JPA_DIAGRAM_TYPE = "JPA Diagram"; 	//$NON-NLS-1$
	public static final String DEFAULT_RES_FOLDER = "src";		//$NON-NLS-1$

	private static boolean xmiExists = false;
	private static WeakHashMap<IProject, WeakReference<Diagram>> projectToDiagram = new  WeakHashMap<IProject, WeakReference<Diagram>>();
	
	public static IPath createDiagramPath(PersistenceUnit persistenceUnit) throws CoreException {
	    IProject project = persistenceUnit.getJpaProject().getProject();
	    String diagramName = persistenceUnit.getJpaProject().getName();
		IPath newXMIFilePath = getDiagramsFolderPath(project).append(diagramName).addFileExtension(DIAGRAM_FILE_EXTENSION);	
		IFileSystem fileSystem = EFS.getLocalFileSystem();
		IFileStore newXMIFile = fileSystem.getStore(newXMIFilePath);
        
	    IPath folderPath = copyExistingXMIContentAndDeleteFile(project, diagramName, newXMIFile);
	    if(folderPath != null){
		    IPath path = new Path(folderPath.segment(0));
		    for (int i = 1; i < folderPath.segmentCount(); i++) {
		    	path = path.append(folderPath.segment(i));
			    IFolder folder = ResourcesPlugin.getWorkspace().getRoot().getFolder(path);
			    if (!folder.exists())
			    	folder.create(true, true, null);
		    }	    	
	    	IFile diagramXMLFile = ResourcesPlugin.getWorkspace().getRoot().getFile(folderPath.append(diagramName).addFileExtension(DIAGRAM_XML_FILE_EXTENSION));
	    	String content = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n"+ //$NON-NLS-1$
		   			"<entities>\n"+ //$NON-NLS-1$
		   			"</entities>\n"; //$NON-NLS-1$
	    	if(diagramXMLFile == null || !diagramXMLFile.exists()){
			   InputStream source = new ByteArrayInputStream(content.getBytes());
			   diagramXMLFile.create(source, true, new NullProgressMonitor());
		   } else
				try {
					if(diagramXMLFile.getContents().read() == -1){
						   diagramXMLFile.setContents(new ByteArrayInputStream(content.getBytes()), true, false, new NullProgressMonitor());
					}
				} catch (IOException e) {
					JPADiagramEditorPlugin.logError(JPAEditorMessages.ModelIntegrationUtil_CannotSetFileContentErrorMSG, e);
				}
	    }

	    return newXMIFilePath;		
	}
	
	private static IPath copyExistingXMIContent(IContainer container,
			String xmiFileName, IFileStore newXMIFile) throws JavaModelException,	CoreException {
		IPath folderPath = null;
		IResource[] resources = container.members();
	    for(IResource res : resources){
	    	if(res instanceof IFolder) {
	    		folderPath = ((IFolder)res).getFullPath();
				IFile existingXMIFile =((IFolder)res).getFile(xmiFileName);
	    		if(existingXMIFile != null && existingXMIFile.exists()){
		    		IFileStore folder = EFS.getLocalFileSystem().getStore(existingXMIFile.getLocationURI());
	    			folder.copy(newXMIFile, EFS.OVERWRITE, null);
	    		    existingXMIFile.delete(true, new NullProgressMonitor());
	    		    setXmiExists(true);
	    		    return folderPath;
	    		}
	    	}
	    	if(res instanceof IContainer) 
	    		copyExistingXMIContent((IContainer)res,
	    				xmiFileName, newXMIFile);
	    }		
		return null;
	}

	public static IPath copyExistingXMIContentAndDeleteFile(IProject project,
			String diagramName, IFileStore newXMIFile) throws JavaModelException,	CoreException {
		String xmiFileName = diagramName + "." + DIAGRAM_FILE_EXTENSION;		//$NON-NLS-1$
		IPath folderPath = copyExistingXMIContent(project, xmiFileName, newXMIFile);
		if (folderPath != null)
			return folderPath;
	    IPath projectPath = project.getFullPath();
	    folderPath = projectPath.append(getDiagramsXMLFolderPath(project));
	    return folderPath;
	}
	
	public static Diagram createDiagram(PersistenceUnit persistenceUnit, 
										int grid, 
										boolean snap) throws CoreException {
		IPath path = createDiagramPath(persistenceUnit);
		Diagram d = createDiagram(persistenceUnit.getJpaProject().getProject(), path, persistenceUnit.getJpaProject().getName(), grid, snap); 
	    return d;
	}
	
	public static TransactionalEditingDomain getTransactionalEditingDomain(Diagram diagram) {
		TransactionalEditingDomain editingDomain = TransactionUtil.getEditingDomain(diagram);
		return editingDomain;
	}
	
	public static ResourceSet getResourceSet(Diagram diagram) {
		JpaProject jpaProject = getProjectByDiagram(diagram.getName());
		if (jpaProject == null)
			return null;
		TransactionalEditingDomain defaultTransEditDomain = (TransactionalEditingDomain)jpaProject.getProject().getAdapter(TransactionalEditingDomain.class);
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
		
		Diagram diagram = getDiagramByProject(project);
		if (diagram != null)
			return diagram;
		TransactionalEditingDomain defaultTransEditDomain = GraphitiUi.getEmfService().createResourceSetAndEditingDomain();
		ResourceSet resourceSet = defaultTransEditDomain.getResourceSet();

		String pathName = diagramFileName.toString();
		URI resourceURI = URI.createFileURI(pathName);
		
		final Resource resource = resourceSet.createResource(resourceURI);		
		if (!resource.isLoaded())
			return createNewDiagram(project, defaultTransEditDomain, resourceSet, resource, diagramName, grid, snap);
		
		EList<EObject> objs = resource.getContents();
		if (objs == null) 
			return createNewDiagram(project, defaultTransEditDomain, resourceSet, resource, diagramName, grid, snap);
		Iterator<EObject> it = objs.iterator();
		while (it.hasNext()) {
			EObject obj = it.next();
			if ((obj == null) && !Diagram.class.isInstance(obj))
				continue;
			diagram = (Diagram)obj;
			//diagramsToResources.put(diagram, resource);
			defaultTransEditDomain.getCommandStack().flush();
			return diagram;
		}
		return createNewDiagram(project, defaultTransEditDomain, resourceSet, resource, diagramName, grid, snap);
	}
	
	private static Diagram createNewDiagram(final IProject project,
											TransactionalEditingDomain editingDomain,
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
				projectToDiagram.put(project, new WeakReference<Diagram>(diagram));
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
		//mapDiagramToProject((Diagram)wrp.getObject(), resource);
		return (Diagram)wrp.getObject();		
	}
	
	public static IPath getResourceFolderPath(IProject project) {
		return getEMFResourceFolderPath(project);
	}
		
	public static IPath getEMFResourceFolderPath(IProject project) {
		return project.getProjectRelativePath().append(DEFAULT_RES_FOLDER);		
	}	
	
	public static IPath getDiagramsXMLFolderPath(IProject project) {
		Properties props = JPADiagramPropertyPage.loadProperties(project);		
		return new Path(JPADiagramPropertyPage.getDefaultFolder(project, props));
	}
	
	public static IPath getDiagramsFolderPath(IProject project){
		return JPADiagramEditorPlugin.getDefault().getStateLocation();
	}
		
	public static JpaProject getProjectByDiagram(String diagramName) {
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(diagramName);
		Iterator<JpaProject> iter = getJpaProjectManager().getJpaProjects().iterator();
		while (iter.hasNext()) {
			JpaProject jpaProject = iter.next();
			if(jpaProject.getName().equalsIgnoreCase(diagramName)){
				return jpaProject;
			}
			
		}
		
		return JpaArtifactFactory.instance().getJpaProject(project);
	}
	
	private static JpaProjectManager getJpaProjectManager() {
		return (JpaProjectManager) ResourcesPlugin.getWorkspace().getAdapter(JpaProjectManager.class);
	}
	
	public static boolean isDiagramOpen(String diagramName) {
		IWorkbenchWindow workbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		IWorkbenchPage workbenchPage = workbenchWindow.getActivePage();
		IEditorReference[] editorRefs = workbenchPage.getEditorReferences();
		for (IEditorReference editorRef : editorRefs) {
			if(!JPADiagramEditorPlugin.PLUGIN_ID.equals(editorRef.getId())) 
				continue;
			JPADiagramEditor editor = (JPADiagramEditor)editorRef.getEditor(false);
			if (editor == null)
				continue;
			JPAEditorDiagramTypeProvider diagramProvider = editor.getDiagramTypeProvider();  
			Diagram d = diagramProvider.getDiagram();
			if (diagramName.equals(d.getName()))
				return true;
		}		
		return false;
	}
			
		
	public static JPAEditorDiagramTypeProvider getProviderByDiagram(String diagramName) {
		IWorkbenchWindow workbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		if (workbenchWindow == null)
			return null;
		IWorkbenchPage workbenchPage = workbenchWindow.getActivePage();
		IEditorReference[] editorRefs = workbenchPage.getEditorReferences();
		for (IEditorReference editorRef : editorRefs) {
			if(!JPADiagramEditorPlugin.PLUGIN_ID.equals(editorRef.getId())) 
				continue;
			JPADiagramEditor editor = (JPADiagramEditor)editorRef.getEditor(false);
			if (editor == null)
				continue;
			JPAEditorDiagramTypeProvider diagramProvider = editor.getDiagramTypeProvider();  
			Diagram d = diagramProvider.getDiagram();
			if (diagramName.equals(d.getName()))
				return diagramProvider;
		}
		return null;
	}
	
	public static boolean xmiExists() {
		return xmiExists;
	}

	public static void setXmiExists(boolean xmiExists) {
		ModelIntegrationUtil.xmiExists = xmiExists;
	}

	public static IPath getDiagramXMLFullPath(String diagramName) {
		if (diagramName == null)
			return null;
		JpaProject jpaProject = getProjectByDiagram(diagramName);
		if (jpaProject == null)
			return null;
		IProject project = jpaProject.getProject();
		return project.getFile(ModelIntegrationUtil
				.getDiagramsXMLFolderPath(project)
				.append(diagramName)
				.addFileExtension(
						ModelIntegrationUtil.DIAGRAM_XML_FILE_EXTENSION)).getFullPath();
	}
	
	synchronized public static void putProjectToDiagram(IProject project, Diagram d) {
		projectToDiagram.put(project, new WeakReference<Diagram>(d));
	}

	synchronized public static Diagram getDiagramByProject(IProject project) {
		if (project == null)
			return null;
		IWorkbenchWindow workbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		IWorkbenchPage workbenchPage = null; 
		try {
			workbenchPage = workbenchWindow.getActivePage();
			IEditorReference[] editorRefs = workbenchPage.getEditorReferences();
			for (IEditorReference editorRef : editorRefs) {
				if(!JPADiagramEditorPlugin.PLUGIN_ID.equals(editorRef.getId())) 
					continue;
				JPADiagramEditor editor = (JPADiagramEditor)editorRef.getEditor(false);
				if (editor == null)
					continue;
				JPAEditorDiagramTypeProvider diagramProvider = editor.getDiagramTypeProvider();  
				Diagram d = diagramProvider.getDiagram();
				if (d.getName().equals(project.getName()))
					return d;
			}			
		} catch (NullPointerException e) {
			// ignore
		}
		WeakReference<Diagram> ref = projectToDiagram.get(project);
		if (ref != null)
			return ref.get();
		return null;
	}
	
	public static void deleteDiagramXMIFile(final Diagram diagram) {
		if(diagram != null && diagram.eResource()!=null){
				TransactionalEditingDomain ted = ModelIntegrationUtil.getTransactionalEditingDomain(diagram);
			if(ted == null)
				return;
			ted.getCommandStack().execute(new RecordingCommand(ted) {
				
				@Override
				protected void doExecute() {
					try {
						diagram.eResource().delete(null);	
					} catch (IOException e) {
						JPADiagramEditorPlugin.logInfo("Cannot delete the digram xmi file."); //$NON-NLS-1$
						JPADiagramEditorPlugin.logInfo(e.getLocalizedMessage());
					}
				}
			});
		}
	}
}

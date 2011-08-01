/*******************************************************************************
 * <copyright>
 *
 * Copyright (c) 2005, 2011 SAP AG.
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
package org.eclipse.jpt.jpadiagrameditor.ui.internal.feature;

import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import java.util.ListIterator;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICreateContext;
import org.eclipse.graphiti.features.impl.AbstractCreateFeature;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.search.SearchEngine;
import org.eclipse.jdt.internal.core.search.JavaSearchScope;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.JptJpaCorePlugin;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.JPADiagramEditor;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.JPADiagramEditorPlugin;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.i18n.JPAEditorMessages;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.propertypage.JPADiagramPropertyPage;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorFeatureProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.JPAEditorImageProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorUtil;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchSite;


@SuppressWarnings({ "restriction" })
public class CreateJPAEntityFeature extends AbstractCreateFeature {
	
	private String PERSISTENCE_PROVIDER_LIBRARY_STRING = "javax/persistence/"; //$NON-NLS-1$
	private IPreferenceStore jpaPreferenceStore = JPADiagramEditorPlugin.getDefault().getPreferenceStore();
	private boolean isMappedSuperclassChild;
	private String mappedSuperclassName;
	private String mappedSuperclassPackage;
	private boolean hasPrimarykey;


	public CreateJPAEntityFeature(IFeatureProvider fp) {
        super(fp, JPAEditorMessages.CreateJPAEntityFeature_jpaEntityFeatureName, JPAEditorMessages.CreateJPAEntityFeature_jpaEntityFeatureDescription);
    }
	
	public CreateJPAEntityFeature(IFeatureProvider fp,
			boolean isMappedSuperclassChild, String mappedSuperclassName,
			String mappedSuperClassPackage) {
		this(fp, isMappedSuperclassChild, mappedSuperclassName,
				mappedSuperClassPackage, false);
	}

	public CreateJPAEntityFeature(IFeatureProvider fp,
			boolean isMappedSuperclassChild, String mappedSuperclassName) {
		this(fp, isMappedSuperclassChild, mappedSuperclassName, null, false);
	}

	public CreateJPAEntityFeature(IFeatureProvider fp,
			boolean isMappedSuperclassChild, String mappedSuperclassName,
			String mappedSuperClassPackage, boolean hasPrimaryKey) {
		this(fp);
		this.isMappedSuperclassChild = isMappedSuperclassChild;
		this.mappedSuperclassName = mappedSuperclassName;
		this.mappedSuperclassPackage = mappedSuperClassPackage;
		this.hasPrimarykey = hasPrimaryKey;
	}


    public boolean canCreate(ICreateContext context) {
        return context.getTargetContainer() instanceof Diagram;
    }

    public Object[] create(ICreateContext context) {
		List<Shape> shapes = this.getFeatureProvider().getDiagramTypeProvider().getDiagram().getChildren();
		IProject targetProject = null;
		JpaProject jpaProject = null;
		if ((shapes == null) || (shapes.size() == 0)) {
			jpaProject = getTargetJPAProject();
			targetProject = jpaProject.getProject();
		} else {
			Shape sh = shapes.get(0);
			JavaPersistentType jpt = (JavaPersistentType)getFeatureProvider().getBusinessObjectForPictogramElement(sh);
			if (jpt == null)
				return new Object[] {};
			jpaProject = jpt.getJpaProject();
			targetProject = jpaProject.getProject();
		}    	
		String entityName = getFeatureProvider().getJPAEditorUtil().
								generateUniqueEntityName(jpaProject, 
														 JPADiagramPropertyPage.getDefaultPackage(jpaProject.getProject()), 
														 getFeatureProvider()); 

		if(!checkIsSetPersistenceProviderLibrary(jpaProject)){
			Shell shell = JPADiagramEditorPlugin.getDefault().getWorkbench().getActiveWorkbenchWindow().getShell();
			IStatus status = new Status(IStatus.ERROR, JPADiagramEditor.ID, JPAEditorMessages.CreateJPAEntityFeature_createEntityErrorStatusMsg);
			ErrorDialog.openError(shell, JPAEditorMessages.CreateJPAEntityFeature_createEntityErrorMsgTitle, 
					JPAEditorMessages.CreateJPAEntityFeature_createEntityErrorMsg, status);
			return new Object[] {};
		}
		
		if (!JptJpaCorePlugin.discoverAnnotatedClasses(targetProject)) {
			JPAEditorUtil.createRegisterEntityInXMLJob(jpaProject, entityName);
		}
		
		try {
			this.getFeatureProvider().getJPAEditorUtil().createEntityInProject(targetProject, entityName, jpaPreferenceStore, isMappedSuperclassChild, mappedSuperclassName, mappedSuperclassPackage, hasPrimarykey);
		} catch (Exception e1) {
			JPADiagramEditorPlugin.logError("Cannot create an entity in the project " + targetProject.getName(), e1);  //$NON-NLS-1$		 
		}
		
		
		try {
			jpaProject.getProject().refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
		} catch (CoreException e1) {
			JPADiagramEditorPlugin.logError("Cannot refresh the project", e1);  //$NON-NLS-1$		 
		}
		
		ListIterator<PersistenceUnit> lit = jpaProject.getRootContextNode().getPersistenceXml().getPersistence().getPersistenceUnits().iterator();		
		PersistenceUnit pu = lit.next();
		JavaPersistentType jpt = (JavaPersistentType)pu.getPersistentType(entityName);

		int cnt = 0;
		while ((jpt == null) && (cnt < 25)) {
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				JPADiagramEditorPlugin.logError("Thread sleep interrupted", e);  //$NON-NLS-1$		 
			}
			jpt = (JavaPersistentType)pu.getPersistentType(entityName);
			cnt++;
		}		
		
		if (jpt != null) {
			addGraphicalRepresentation(context, jpt);
	        IWorkbenchSite ws = ((IEditorPart)getDiagramEditor()).getSite();
	        ICompilationUnit cu = getFeatureProvider().getCompilationUnit(jpt);
	        getFeatureProvider().getJPAEditorUtil().formatCode(cu, ws);			
			return new Object[] { jpt };
		} else {
			JPADiagramEditorPlugin.logError("The JPA entity " + entityName + " could not be created", new Exception());  //$NON-NLS-1$	//$NON-NLS-2$	 
		}		
		return new Object[] {};
    }
        
    @Override
	public String getCreateImageId() {
        return JPAEditorImageProvider.ADD_JPA_ENTITY;
    }
    
	@Override
	public IJPAEditorFeatureProvider getFeatureProvider() {
		return  (IJPAEditorFeatureProvider)super.getFeatureProvider();
	}        
        
	private JpaProject getTargetJPAProject() {
		return getFeatureProvider().getMoinIntegrationUtil().getProjectByDiagram(getDiagram());
	}
	
	private boolean isPersistenceProviderLibraryInClasspath(String classPathEntry) {
		try {
			JarFile jar = new JarFile(classPathEntry);
			Enumeration<JarEntry> entries = jar.entries();

			while (entries.hasMoreElements()) {
				JarEntry entry = entries.nextElement();
				if (entry.getName().equals(PERSISTENCE_PROVIDER_LIBRARY_STRING)) {
					return true;
				}
			}

		} catch (IOException e) {
			JPADiagramEditorPlugin.logError(e); 	     					
		}
		return false;
	}
	
	private boolean checkIsSetPersistenceProviderLibrary(JpaProject jpaProject) {
		IJavaProject javaProject = JavaCore.create(jpaProject.getProject());
		IJavaElement[] elements = new IJavaElement[] { javaProject };
		JavaSearchScope scope = (JavaSearchScope) SearchEngine.createJavaSearchScope(elements);
		boolean isAdded = false;

		IPath[] paths = scope.enclosingProjectsAndJars();
		for (int i = 1; i < paths.length; i++) {
			IPath path = paths[i];
			if (isPersistenceProviderLibraryInClasspath(path.toString())) {
				isAdded = true;
				break;
			}
		}
		return isAdded;
	}
		
	
}
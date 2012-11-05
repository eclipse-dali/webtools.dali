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

import java.util.List;
import java.util.ListIterator;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICreateContext;
import org.eclipse.graphiti.features.impl.AbstractCreateFeature;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jpt.jpa.core.JpaPreferences;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.JPADiagramEditor;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.JPADiagramEditorPlugin;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.i18n.JPAEditorMessages;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.propertypage.JPADiagramPropertyPage;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorFeatureProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.JPAEditorImageProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorUtil;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JpaArtifactFactory;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchSite;


public class CreateJPAEntityFeature extends AbstractCreateFeature {
	
	private IPreferenceStore jpaPreferenceStore = JPADiagramEditorPlugin.getDefault().getPreferenceStore();
	private boolean isMappedSuperclassChild;
	private JavaPersistentType mappedSuperclass;
	private String mappedSuperclassName;
	private String mappedSuperclassPackage;
	private boolean superHasPrimarykey;


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
			String mappedSuperClassPackage, boolean superHasPrimaryKey) {
		this(fp);
		this.isMappedSuperclassChild = isMappedSuperclassChild;
		this.mappedSuperclassName = mappedSuperclassName;
		this.mappedSuperclassPackage = mappedSuperClassPackage;
		this.superHasPrimarykey = superHasPrimaryKey;
	}

	public CreateJPAEntityFeature(IJPAEditorFeatureProvider fp,
			JavaPersistentType mappedSuperclass) throws JavaModelException {
		this(fp);
		this.isMappedSuperclassChild = true;
		this.mappedSuperclass = mappedSuperclass; 
		this.mappedSuperclassName = mappedSuperclass.getName();
		this.mappedSuperclassPackage = JpaArtifactFactory.instance().getMappedSuperclassPackageDeclaration(mappedSuperclass);
		this.superHasPrimarykey = JpaArtifactFactory.instance().hasOrInheritsPrimaryKey(mappedSuperclass);
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
								generateUniqueTypeName(jpaProject, 
														 JPADiagramPropertyPage.getDefaultPackage(jpaProject.getProject()), 
														 ".Entity", getFeatureProvider()); //$NON-NLS-1$

		if(!JPAEditorUtil.checkIsSetPersistenceProviderLibrary(jpaProject)){
			Shell shell = JPADiagramEditorPlugin.getDefault().getWorkbench().getActiveWorkbenchWindow().getShell();
			IStatus status = new Status(IStatus.ERROR, JPADiagramEditor.ID, JPAEditorMessages.CreateJPAEntityFeature_createEntityErrorStatusMsg);
			ErrorDialog.openError(shell, JPAEditorMessages.CreateJPAEntityFeature_createEntityErrorMsgTitle, 
					JPAEditorMessages.CreateJPAEntityFeature_createEntityErrorMsg, status);
			return new Object[] {};
		}
		
		if (! JpaPreferences.getDiscoverAnnotatedClasses(targetProject)) {
			JPAEditorUtil.createRegisterEntityInXMLJob(jpaProject, entityName);
		}
		
		try {
			if (mappedSuperclass != null) {
				this.getFeatureProvider().
				getJPAEditorUtil().
					createEntityInProject(targetProject, entityName, mappedSuperclass);	//$NON-NLS-1$								
			} else {
				this.getFeatureProvider().
				getJPAEditorUtil().
					createEntityInProject(targetProject, entityName, jpaPreferenceStore, 
										  isMappedSuperclassChild, mappedSuperclassName, 
										  mappedSuperclassPackage, "id", superHasPrimarykey);	//$NON-NLS-1$				
			}
		} catch (Exception e1) {
			JPADiagramEditorPlugin.logError("Cannot create an entity in the project " + targetProject.getName(), e1);  //$NON-NLS-1$		 
		}
		
		
		try {
			jpaProject.getProject().refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
		} catch (CoreException e1) {
			JPADiagramEditorPlugin.logError("Cannot refresh the project", e1);  //$NON-NLS-1$		 
		}
		
		ListIterator<PersistenceUnit> lit = jpaProject.getRootContextNode().getPersistenceXml().getRoot().getPersistenceUnits().iterator();		
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
	
}
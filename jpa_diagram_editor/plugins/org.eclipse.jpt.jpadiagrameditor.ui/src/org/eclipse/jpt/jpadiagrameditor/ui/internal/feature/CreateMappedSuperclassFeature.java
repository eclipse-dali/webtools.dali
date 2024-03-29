/*******************************************************************************
 * <copyright>
 *
 * Copyright (c) 2005, 2013 SAP AG.
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
package org.eclipse.jpt.jpadiagrameditor.ui.internal.feature;

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICreateContext;
import org.eclipse.graphiti.features.impl.AbstractCreateFeature;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.ui.editor.IDiagramContainerUI;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.jpa.core.JpaPreferences;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.JPADiagramEditorPlugin;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.i18n.JPAEditorMessages;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.propertypage.JPADiagramPropertyPage;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorFeatureProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.JPAEditorImageProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorUtil;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JpaArtifactFactory;
import org.eclipse.ui.IWorkbenchSite;
  


public class CreateMappedSuperclassFeature extends
		AbstractCreateFeature {

	public CreateMappedSuperclassFeature(IFeatureProvider fp) {
		super(
				fp,
				JPAEditorMessages.CreateMappedSuperclassFeature_createMappedSuperclassFeatureName,
				JPAEditorMessages.CreateMappedSuperclassFeature_createMappedSuperclassFeatureDescription);
	}

	public boolean canCreate(ICreateContext context) {
		return context.getTargetContainer() instanceof Diagram;
	}

	public Object[] create(ICreateContext context) {
		List<Shape> shapes = this.getFeatureProvider().getDiagramTypeProvider()
				.getDiagram().getChildren();
		IProject targetProject = null;
		JpaProject jpaProject = null;
		if ((shapes == null) || (shapes.size() == 0)) {
			jpaProject = getTargetJPAProject();
			targetProject = jpaProject.getProject();
		} else {
			Shape sh = shapes.get(0);
			PersistentType jpt = (PersistentType) getFeatureProvider()
					.getBusinessObjectForPictogramElement(sh);
			if (jpt == null)
				return new Object[] {};
			jpaProject = jpt.getJpaProject();
			targetProject = jpaProject.getProject();
		}
		
		String mappedSuperclassName = getFeatureProvider()
				.getJPAEditorUtil()
				.generateUniqueTypeName(
						jpaProject,
						JPADiagramPropertyPage.getDefaultPackage(jpaProject.getProject()),
						".MpdSuprcls", getFeatureProvider()); //$NON-NLS-1$
		
		if (! JpaPreferences.getDiscoverAnnotatedClasses(jpaProject.getProject())) {
			JPAEditorUtil.createRegisterEntityInXMLJob(jpaProject, mappedSuperclassName);
		}
				
		try {
			getFeatureProvider().getJPAEditorUtil().createMappedSuperclassInProject(targetProject, mappedSuperclassName);
		} catch (Exception e1) {
			JPADiagramEditorPlugin.logError("Cannot create a mapped superclass in the project " + targetProject.getName(), e1);  //$NON-NLS-1$		 
		}
//		jpaProject.updateAndWait();
		PersistenceUnit pu = JpaArtifactFactory.instance().getPersistenceUnit(jpaProject);
		PersistentType jpt = JpaArtifactFactory.instance().getJPT(mappedSuperclassName, pu);
		
		if (jpt != null) {
			if(JPADiagramPropertyPage.doesSupportOrmXml(targetProject)) {
				JpaArtifactFactory.instance().addPersistentTypeToORMXml(jpaProject, mappedSuperclassName, MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY);
			}
			addGraphicalRepresentation(context, jpt);
	        IWorkbenchSite ws = ((IDiagramContainerUI)getFeatureProvider().getDiagramTypeProvider().getDiagramBehavior().getDiagramContainer()).getSite();
	        ICompilationUnit cu = getFeatureProvider().getCompilationUnit(jpt);
	        getFeatureProvider().getJPAEditorUtil().formatCode(cu, ws);			
			return new Object[] { jpt };
		} else {
			JPADiagramEditorPlugin.logError("The mapped superclass " + 						//$NON-NLS-1$
						mappedSuperclassName + " could not be created", new Exception());	//$NON-NLS-1$	 
		}				

		return new Object[] {};
	}

	@Override
	public String getCreateImageId() {
		return JPAEditorImageProvider.ADD_MAPPED_SUPERCLASS;
	}


	@Override
	public IJPAEditorFeatureProvider getFeatureProvider() {
		return (IJPAEditorFeatureProvider) super.getFeatureProvider();
	}

	private JpaProject getTargetJPAProject() {
		return getFeatureProvider().getMoinIntegrationUtil()
				.getProjectByDiagram(getDiagram());
	}

}

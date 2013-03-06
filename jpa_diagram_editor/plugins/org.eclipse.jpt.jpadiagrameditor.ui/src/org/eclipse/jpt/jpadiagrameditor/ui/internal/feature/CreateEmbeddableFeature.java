/*******************************************************************************
 * <copyright>
 *
 * Copyright (c) 2012 SAP AG and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Petya Sabeva - initial API, implementation and documentation
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
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchSite;

public class CreateEmbeddableFeature extends AbstractCreateFeature {

	public CreateEmbeddableFeature(IFeatureProvider fp) {
		super(
				fp,
				JPAEditorMessages.CreateEmbeddableFeature_EmbeddableFeatureName,
				JPAEditorMessages.CreateEmbeddableFeature_EmbeddableFeatureDescription);
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

		String embeddableName = getFeatureProvider().getJPAEditorUtil()
				.generateUniqueTypeName(
						jpaProject,
						JPADiagramPropertyPage.getDefaultPackage(jpaProject.getProject()),
						".Embeddable", getFeatureProvider()); //$NON-NLS-1$

		if (!JpaPreferences
				.getDiscoverAnnotatedClasses(jpaProject.getProject())) {
			JPAEditorUtil.createRegisterEntityInXMLJob(jpaProject,
					embeddableName);
		}

		try {
			getFeatureProvider().getJPAEditorUtil()
					.createEmbeddableInProject(targetProject, embeddableName);
		} catch (Exception e1) {
			JPADiagramEditorPlugin
					.logError(
							"Cannot create a embeddable in the project " + targetProject.getName(), e1); //$NON-NLS-1$		 
		}
		// jpaProject.updateAndWait();
		PersistenceUnit pu = JpaArtifactFactory.instance().getPersistenceUnit(
				jpaProject);
		PersistentType jpt = JpaArtifactFactory.instance().getJPT(
				embeddableName, pu);

		if (jpt != null) {
			if(JPADiagramPropertyPage.doesSupportOrmXml(targetProject)) {
				JpaArtifactFactory.instance().addPersistentTypeToORMXml(jpaProject, embeddableName, MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY);
			}
			addGraphicalRepresentation(context, jpt);
			IWorkbenchSite ws = ((IEditorPart) getDiagramEditor()).getSite();
			ICompilationUnit cu = getFeatureProvider().getCompilationUnit(jpt);
			getFeatureProvider().getJPAEditorUtil().formatCode(cu, ws);
			return new Object[] { jpt };
		} else {
			JPADiagramEditorPlugin
					.logError(
							"The embeddable " + //$NON-NLS-1$
									embeddableName
									+ " could not be created", new Exception()); //$NON-NLS-1$	 
		}

		return new Object[] {};
	}

	@Override
	public String getCreateImageId() {
		return JPAEditorImageProvider.ADD_EMBEDDABLE;
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

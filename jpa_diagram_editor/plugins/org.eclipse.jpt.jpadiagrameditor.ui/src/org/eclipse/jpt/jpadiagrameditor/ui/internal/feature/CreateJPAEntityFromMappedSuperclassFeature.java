package org.eclipse.jpt.jpadiagrameditor.ui.internal.feature;
 
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICreateContext;
import org.eclipse.graphiti.features.impl.AbstractCreateFeature;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.JptJpaCorePlugin;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.JPADiagramEditorPlugin;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.i18n.JPAEditorMessages;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.propertypage.JPADiagramPropertyPage;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorFeatureProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.JPAEditorImageProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorUtil;
  


public class CreateJPAEntityFromMappedSuperclassFeature extends
		AbstractCreateFeature {
	private IPreferenceStore jpaPreferenceStore = JPADiagramEditorPlugin
			.getDefault().getPreferenceStore();

	public CreateJPAEntityFromMappedSuperclassFeature(IFeatureProvider fp) {
		super(
				fp,
				JPAEditorMessages.CreateJPAEntityFromMappedSuperclassFeature_createInheritedEntityFeatureName,
				JPAEditorMessages.CreateJPAEntityFromMappedSuperclassFeature_createInheritedEntityFeatureDescription);
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
			JavaPersistentType jpt = (JavaPersistentType) getFeatureProvider()
					.getBusinessObjectForPictogramElement(sh);
			if (jpt == null)
				return new Object[] {};
			jpaProject = jpt.getJpaProject();
			targetProject = jpaProject.getProject();
		}
		
		String mappedSuperclassName = getFeatureProvider()
				.getJPAEditorUtil()
				.generateUniqueMappedSuperclassName(
						jpaProject,
						JPADiagramPropertyPage.getDefaultPackage(jpaProject.getProject()),
						getFeatureProvider());
		
		if (!JptJpaCorePlugin.discoverAnnotatedClasses(jpaProject.getProject())) {
			JPAEditorUtil.createRegisterEntityInXMLJob(jpaProject, mappedSuperclassName);
		}
				

		try {
			this.getFeatureProvider()
					.getJPAEditorUtil()
					.createEntityFromMappedSuperclassInProject(targetProject,
							mappedSuperclassName, jpaPreferenceStore);
		} catch (Exception e1) {
			JPADiagramEditorPlugin.logError("Cannot create an entity in the project " + targetProject.getName(), e1);  //$NON-NLS-1$		 
		}
		
		CreateJPAEntityFeature createEntityFeature = new CreateJPAEntityFeature(
				getFeatureProvider(), true, mappedSuperclassName);
		return createEntityFeature.create(context);
	}

	public String getCreateImageId() {
		return JPAEditorImageProvider.ADD_INHERITED_ENTITY;
	}


	public IJPAEditorFeatureProvider getFeatureProvider() {
		return (IJPAEditorFeatureProvider) super.getFeatureProvider();
	}

	private JpaProject getTargetJPAProject() {
		return getFeatureProvider().getMoinIntegrationUtil()
				.getProjectByDiagram(getDiagram());
	}

}

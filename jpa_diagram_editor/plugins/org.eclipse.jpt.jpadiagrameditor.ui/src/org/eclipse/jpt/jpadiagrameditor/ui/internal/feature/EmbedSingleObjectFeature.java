package org.eclipse.jpt.jpadiagrameditor.ui.internal.feature;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICreateConnectionContext;
import org.eclipse.graphiti.features.context.impl.AddConnectionContext;
import org.eclipse.graphiti.features.impl.AbstractCreateConnectionFeature;
import org.eclipse.graphiti.mm.pictograms.Anchor;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.MappedSuperclass;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.context.persistence.ClassRef;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.core.resource.java.EmbeddedAnnotation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.i18n.JPAEditorMessages;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.modelintegration.util.ModelIntegrationUtil;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorFeatureProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.JPAEditorImageProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.HasReferanceRelation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.HasSingleReferenceRelation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorUtil;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JpaArtifactFactory;

public class EmbedSingleObjectFeature extends AbstractCreateConnectionFeature {
	
	private JavaPersistentType embeddingEntity;

	public EmbedSingleObjectFeature(IFeatureProvider fp) {
		this(fp, JPAEditorMessages.EmbedSingleObjectFeature_EmbeddedFeatureName, JPAEditorMessages.EmbedSingleObjectFeature_EmbeddedFeatureDescription);
	}
		
	public EmbedSingleObjectFeature(IFeatureProvider fp, String name,
			String description) {
		super(fp, name, description);
	}

	public boolean canCreate(ICreateConnectionContext context) {
		embeddingEntity = (JavaPersistentType)getPersistentType(context.getSourceAnchor());
		JavaPersistentType embeddable = (JavaPersistentType)getPersistentType(context.getTargetAnchor());
	    if ((embeddingEntity == null) || (embeddable == null)) 
	        return false;
		
	    if (!JpaArtifactFactory.instance().hasEmbeddableAnnotation(embeddable))
	    	return false;
	    
	    if(JpaArtifactFactory.instance().hasEmbeddableAnnotation(embeddingEntity) && 
	    				JPAEditorUtil.checkJPAFacetVersion(embeddable.getJpaProject(), JPAEditorUtil.JPA_PROJECT_FACET_10))
	    	return false;
	    
	    if(!JpaArtifactFactory.instance().hasEntityAnnotation(embeddingEntity) && 
	    				!JpaArtifactFactory.instance().hasEmbeddableAnnotation(embeddingEntity))
	    	return false;
	    return true;
	}

	public Connection create(ICreateConnectionContext context) {
		JavaPersistentType embeddingEntity = (JavaPersistentType)getPersistentType(context.getSourceAnchor());
		JavaPersistentType embeddable = (JavaPersistentType)getPersistentType(context.getTargetAnchor());
		
		JavaPersistentAttribute embeddedAttribute = JPAEditorUtil.addAnnotatedAttribute(getFeatureProvider(), embeddingEntity, embeddable, false, null);
		embeddedAttribute.setMappingKey(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY);
		embeddedAttribute.getResourceAttribute().addAnnotation(EmbeddedAnnotation.ANNOTATION_NAME);
		
		HasReferanceRelation rel = new HasSingleReferenceRelation(embeddingEntity, embeddable);
		rel.setEmbeddedAnnotatedAttribute(embeddedAttribute);
		
		AddHasReferenceRelationFeature ft = new AddHasReferenceRelationFeature(getFeatureProvider());
		
		AddConnectionContext cont =  new AddConnectionContext(context.getSourceAnchor(), context.getTargetAnchor());
		cont.setNewObject(rel);
		
		Connection connection = (Connection) ft.add(cont);	
		
		return connection;
	}

	public boolean canStartConnection(ICreateConnectionContext context) {
	    embeddingEntity = (JavaPersistentType)getPersistentType(context.getSourceAnchor());

	    if (embeddingEntity == null)
	        return false;
	    
	    return true;
	}
	
    public String getCreateImageId() {
        return JPAEditorImageProvider.ICON_EMBEDDED;
    }	
    
	protected PersistentType getPersistentType(Anchor anchor) {
	    if (anchor != null) {
	        Object refObject =
	            getBusinessObjectForPictogramElement(anchor.getParent());
	        if (refObject instanceof PersistentType) {
	            return (PersistentType) refObject;
	        }
	    }
	    return null;
	}
	
	public IJPAEditorFeatureProvider getFeatureProvider() {
		return (IJPAEditorFeatureProvider)super.getFeatureProvider(); 
	}
	
	@Override
	public void startConnecting() {
		super.startConnecting();
		disableAllMappedSuperclasses();
	}
	
	@Override
	public void attachedToSource(ICreateConnectionContext context) {
		super.attachedToSource(context);
		getFeatureProvider().setOriginalPersistentTypeColor();
		disableUnvalidRelationTargets();
	}
	
	@Override
	public void endConnecting() {
		super.endConnecting();
		getFeatureProvider().setOriginalPersistentTypeColor();
	}
	
	/**
	 * For each unvalid relationship's target, change the color of the respective
	 * java persistent type in gray to simulate disability of the persistent type.
	 */
	@SuppressWarnings("restriction")
	private void disableUnvalidRelationTargets(){
		Diagram d = getDiagram();
		JpaProject project = ModelIntegrationUtil.getProjectByDiagram(d.getName());
		PersistenceUnit unit = project.getRootContextNode().getPersistenceXml().
								getRoot().getPersistenceUnits().iterator().next();
		boolean isJPA10Project = JPAEditorUtil.checkJPAFacetVersion(embeddingEntity.getJpaProject(), JPAEditorUtil.JPA_PROJECT_FACET_10);
		if(JpaArtifactFactory.instance().hasEntityAnnotation(embeddingEntity)
				|| (!isJPA10Project && JpaArtifactFactory.instance().hasEmbeddableAnnotation(embeddingEntity))){
			disableAllJPTsThatAreNotEmbeddables(unit);
		} 
		else if(JpaArtifactFactory.instance().hasMappedSuperclassAnnotation(embeddingEntity)
				|| (isJPA10Project && JpaArtifactFactory.instance().hasEmbeddableAnnotation(embeddingEntity))){
			disableAllPersistenTypes(unit);
		}
	}

	/**
	 * Disable all {@link MappedSuperclass}es and {@link Entity}s
	 * registered in the persistence unit.
	 * @param unit
	 */
	private void disableAllJPTsThatAreNotEmbeddables(PersistenceUnit unit) {
		for (ClassRef classRef : unit.getClassRefs()) {
			if (classRef.getJavaPersistentType() != null) {
				final JavaPersistentType jpt = classRef.getJavaPersistentType();
				if(JpaArtifactFactory.instance().hasEntityAnnotation(jpt) || JpaArtifactFactory.instance().hasMappedSuperclassAnnotation(jpt)){
					getFeatureProvider().setGrayColor(jpt);
				}
				
			}
		}
	}
	
	/**
	 * Disable all {@link MappedSuperclass}es
	 * registered in the persistence unit.
	 * @param unit
	 */
	@SuppressWarnings("restriction")
	private void disableAllMappedSuperclasses() {
		Diagram d = getDiagram();
		JpaProject project = ModelIntegrationUtil.getProjectByDiagram(d.getName());
		PersistenceUnit unit = project.getRootContextNode().getPersistenceXml().
								getRoot().getPersistenceUnits().iterator().next();
		boolean isJPA10Project = JPAEditorUtil.checkJPAFacetVersion(ModelIntegrationUtil.getProjectByDiagram(getDiagram().getName()), JPAEditorUtil.JPA_PROJECT_FACET_10);
		for (ClassRef classRef : unit.getClassRefs()) {
			if (classRef.getJavaPersistentType() != null) {
				final JavaPersistentType jpt = classRef.getJavaPersistentType();
				if(JpaArtifactFactory.instance().hasMappedSuperclassAnnotation(jpt) ||
						(isJPA10Project && JpaArtifactFactory.instance().hasEmbeddableAnnotation(jpt))){
					getFeatureProvider().setGrayColor(jpt);
				}
				
			}
		}
	}

	/**
	 * Disable (color in gray) all {@link JavaPersistentType}s registered in the
	 * persistence unit.
	 * @param unit
	 */
	private void disableAllPersistenTypes(PersistenceUnit unit) {
		for (ClassRef classRef : unit.getClassRefs()) {
			if (classRef.getJavaPersistentType() != null) {
				final JavaPersistentType jpt = classRef.getJavaPersistentType();
				getFeatureProvider().setGrayColor(jpt);
			}
		}
	}

}
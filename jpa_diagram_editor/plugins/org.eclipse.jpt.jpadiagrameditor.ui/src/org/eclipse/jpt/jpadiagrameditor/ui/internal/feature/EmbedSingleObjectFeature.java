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
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.persistence.ClassRef;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.i18n.JPAEditorMessages;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.modelintegration.util.ModelIntegrationUtil;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorFeatureProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.JPAEditorImageProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.HasReferanceRelation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.HasSingleReferenceRelation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorUtil;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JpaArtifactFactory;

public class EmbedSingleObjectFeature extends AbstractCreateConnectionFeature {
	
	private PersistentType embeddingEntity;

	public EmbedSingleObjectFeature(IFeatureProvider fp) {
		this(fp, JPAEditorMessages.EmbedSingleObjectFeature_EmbeddedFeatureName, JPAEditorMessages.EmbedSingleObjectFeature_EmbeddedFeatureDescription);
	}
		
	public EmbedSingleObjectFeature(IFeatureProvider fp, String name,
			String description) {
		super(fp, name, description);
	}

	public boolean canCreate(ICreateConnectionContext context) {
		embeddingEntity = getPersistentType(context.getSourceAnchor());
		PersistentType embeddable = getPersistentType(context.getTargetAnchor());
	    if ((embeddingEntity == null) || (embeddable == null)) 
	        return false;
		
	    if (!JpaArtifactFactory.instance().isEmbeddable(embeddable))
	    	return false;
	    
	    if(JpaArtifactFactory.instance().isEmbeddable(embeddingEntity) && 
	    				JPAEditorUtil.checkJPAFacetVersion(embeddable.getJpaProject(), JPAEditorUtil.JPA_PROJECT_FACET_10))
	    	return false;
	    
	    if(!JpaArtifactFactory.instance().isEntity(embeddingEntity) && 
	    				!JpaArtifactFactory.instance().isEmbeddable(embeddingEntity))
	    	return false;
	    return true;
	}

	public Connection create(ICreateConnectionContext context) {
		PersistentType embeddingEntity = getPersistentType(context.getSourceAnchor());
		PersistentType embeddable = getPersistentType(context.getTargetAnchor());

		PersistentAttribute embeddedAttribute = JPAEditorUtil.addAnnotatedAttribute(embeddingEntity, embeddable, false, null);
		PersistentAttribute ormAttr = JpaArtifactFactory.instance().addOrmPersistentAttribute(embeddingEntity, embeddedAttribute, MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY);
		if(ormAttr == null || ormAttr.isVirtual()){
			embeddedAttribute.getJavaPersistentAttribute().setMappingKey(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY);
		}
		HasReferanceRelation rel = new HasSingleReferenceRelation(embeddingEntity, embeddable);
		rel.setEmbeddedAnnotatedAttribute(embeddedAttribute);
		
		AddHasReferenceRelationFeature ft = new AddHasReferenceRelationFeature(getFeatureProvider());
		
		AddConnectionContext cont =  new AddConnectionContext(context.getSourceAnchor(), context.getTargetAnchor());
		cont.setNewObject(rel);
		
		Connection connection = (Connection) ft.add(cont);	
		
		return connection;
	}

	public boolean canStartConnection(ICreateConnectionContext context) {
	    embeddingEntity = getPersistentType(context.getSourceAnchor());

	    if (embeddingEntity == null)
	        return false;
	    
	    return true;
	}
	
    @Override
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
	
	@Override
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
	private void disableUnvalidRelationTargets(){
		Diagram d = getDiagram();
		JpaProject project = ModelIntegrationUtil.getProjectByDiagram(d.getName());
		PersistenceUnit unit = project.getContextModelRoot().getPersistenceXml().
								getRoot().getPersistenceUnits().iterator().next();
		boolean isJPA10Project = JPAEditorUtil.checkJPAFacetVersion(embeddingEntity.getJpaProject(), JPAEditorUtil.JPA_PROJECT_FACET_10);
		if(JpaArtifactFactory.instance().isEntity(embeddingEntity)
				|| (!isJPA10Project && JpaArtifactFactory.instance().isEmbeddable(embeddingEntity))){
			disableAllJPTsThatAreNotEmbeddables(unit);
		} 
		else if(JpaArtifactFactory.instance().isMappedSuperclass(embeddingEntity)
				|| (isJPA10Project && JpaArtifactFactory.instance().isEmbeddable(embeddingEntity))){
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
				final PersistentType jpt = classRef.getJavaPersistentType();
				if(JpaArtifactFactory.instance().isEntity(jpt) || JpaArtifactFactory.instance().isMappedSuperclass(jpt)){
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
	private void disableAllMappedSuperclasses() {
		Diagram d = getDiagram();
		JpaProject project = ModelIntegrationUtil.getProjectByDiagram(d.getName());
		PersistenceUnit unit = project.getContextModelRoot().getPersistenceXml().
								getRoot().getPersistenceUnits().iterator().next();
		boolean isJPA10Project = JPAEditorUtil.checkJPAFacetVersion(ModelIntegrationUtil.getProjectByDiagram(getDiagram().getName()), JPAEditorUtil.JPA_PROJECT_FACET_10);
		for (ClassRef classRef : unit.getClassRefs()) {
			if (classRef.getJavaPersistentType() != null) {
				final PersistentType jpt = classRef.getJavaPersistentType();
				if(JpaArtifactFactory.instance().isMappedSuperclass(jpt) ||
						(isJPA10Project && JpaArtifactFactory.instance().isEmbeddable(jpt))){
					getFeatureProvider().setGrayColor(jpt);
				}
				
			}
		}
	}

	/**
	 * Disable (color in gray) all {@link PersistentType}s registered in the
	 * persistence unit.
	 * @param unit
	 */
	private void disableAllPersistenTypes(PersistenceUnit unit) {
		for (ClassRef classRef : unit.getClassRefs()) {
			if (classRef.getJavaPersistentType() != null) {
				final PersistentType jpt = classRef.getJavaPersistentType();
				getFeatureProvider().setGrayColor(jpt);
			}
		}
	}

}

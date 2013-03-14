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

import java.util.Set;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICreateConnectionContext;
import org.eclipse.graphiti.features.context.impl.AddConnectionContext;
import org.eclipse.graphiti.features.impl.AbstractCreateConnectionFeature;
import org.eclipse.graphiti.mm.pictograms.Anchor;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.context.Embeddable;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.MappedSuperclass;
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.persistence.ClassRef;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.core.jpa2.MappingKeys2_0;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.i18n.JPAEditorMessages;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.modelintegration.util.ModelIntegrationUtil;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.propertypage.JPADiagramPropertyPage;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorFeatureProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.JPAEditorImageProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.HasCollectionReferenceRelation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.HasReferanceRelation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.HasReferanceRelation.HasReferenceType;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorConstants;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorUtil;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JpaArtifactFactory;

public class EmbedCollectionOfObjectsFeature extends AbstractCreateConnectionFeature {
	
	private PersistentType embeddingEntity;

	public EmbedCollectionOfObjectsFeature(IFeatureProvider fp) {
		this(fp, JPAEditorMessages.EmbedCollectionOfObjectsFeature_ElementCollectionFeatureName, JPAEditorMessages.EmbedCollectionOfObjectsFeature_ElementCollectionFeatureDescription);
	}
		
	public EmbedCollectionOfObjectsFeature(IFeatureProvider fp, String name,
			String description) {
		super(fp, name, description);
	}

	public boolean canCreate(ICreateConnectionContext context) {
		PersistentType embeddingEntity = getPersistentType(context.getSourceAnchor());
		PersistentType embeddable = getPersistentType(context.getTargetAnchor());
	    if ((embeddable == null) || (embeddingEntity == null)) 
	        return false;
		
	    if (!JpaArtifactFactory.instance().isEmbeddable(embeddable))
	    	return false;
	    
	    if(!JpaArtifactFactory.instance().isEntity(embeddingEntity) && 
	    				!JpaArtifactFactory.instance().isEmbeddable(embeddingEntity))
	    	return false;
	    if(isNotAllowed(embeddingEntity, embeddable)){
	    	return false;
	    } 
	    return true;
	}

	public Connection create(ICreateConnectionContext context) {
		PersistentType embeddingEntity = getPersistentType(context.getSourceAnchor());
		PersistentType embeddable = getPersistentType(context.getTargetAnchor());
		
		boolean isMap = JPADiagramPropertyPage.isMapType(embeddingEntity.getJpaProject().getProject());
		String mapKeyType = null;
		if(isMap){
			mapKeyType = JPAEditorConstants.STRING_TYPE;
		}

		PersistentAttribute embeddedAttribute = JPAEditorUtil.addAnnotatedAttribute(embeddingEntity, embeddable, true, mapKeyType);
		PersistentAttribute ormAttr = JpaArtifactFactory.instance().addOrmPersistentAttribute(embeddingEntity, embeddedAttribute, MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY);
		if(ormAttr == null || ormAttr.isVirtual()){
			embeddedAttribute.getJavaPersistentAttribute().setMappingKey(MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY);
		}
		HasReferanceRelation rel = new HasCollectionReferenceRelation(embeddingEntity, embeddable);
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
        return JPAEditorImageProvider.ICON_ELEMENT_COLLECTION;
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
	
	/**
	 * Checks whether the given embeddable is already embedded as element collection in entity
	 * or it embed in itself an element collection of another embeddable.
	 * @param jpt - the given emebeddable
	 * @param isEmbedded - true if the embed connection is doing between entity and embeddable, false if the connection
	 * is trying to be done between two embeddables.
	 * @return true if the given emebddable is embedded as element collection in an entity or it embed in itself
	 * an element collection of another embeddable.
	 */
	private boolean isEmbeddableAlreadyEmbeddedAsElementCollection(PersistentType jpt, boolean isEmbedded){
		Set<HasReferanceRelation> refs = JpaArtifactFactory.instance().findAllHasReferenceRelationsByEmbeddable(jpt, getFeatureProvider());
		if(!refs.isEmpty()){
			for(HasReferanceRelation ref : refs){
				if(ref.getReferenceType().equals(HasReferenceType.COLLECTION)){
					if(!isEmbedded && ref.getEmbeddable().getName().equals(jpt.getName())){
						return true;
					} else if(isEmbedded && ref.getEmbeddingEntity().getName().equals(jpt.getName())){
						return true;
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * Checks whether the connection is possible. If the source of the connection is embeddable and it already
	 * embeds in itself an element collection of another embeddable, or if the target of the connection is already
	 * embedded as an element connection in some entity, or the if the target embeddable contains an attribute with
	 * mapping element-collection, then the connection is not allowed by specification.
	 * @param embeddingEntity - the source entity of the connection
	 * @param embeddable - the target entity of the connection
	 * @return true if the connection is possible, false otherwise.
	 */
	private boolean isNotAllowed(PersistentType embeddingEntity, PersistentType embeddable){
		boolean notAllowed = false;
		if(JpaArtifactFactory.instance().isEmbeddable(embeddable)){
			for(PersistentAttribute attr : embeddable.getAllAttributes()){
				if(attr.getMappingKey().equals(MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY)){
					return true;
				}
			}
		}
		if(JpaArtifactFactory.instance().isEmbeddable(embeddingEntity)){
			notAllowed = isEmbeddableAlreadyEmbeddedAsElementCollection(embeddingEntity, false);
		} else if(JpaArtifactFactory.instance().isEntity(embeddingEntity)){
			notAllowed = isEmbeddableAlreadyEmbeddedAsElementCollection(embeddable, true);
		}
		return notAllowed;		
	}
	
	@Override
	public IJPAEditorFeatureProvider getFeatureProvider() {
		return (IJPAEditorFeatureProvider)super.getFeatureProvider(); 
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
	
	@Override
	public void startConnecting() {
		super.startConnecting();
		disableAllMappedSuperclasses();
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
		if(JpaArtifactFactory.instance().isEntity(embeddingEntity) 
				|| JpaArtifactFactory.instance().isEmbeddable(embeddingEntity)){
			disableAllNotEmbeddablesOrEmbedAsElementCollection(unit);
		} 
		else if(JpaArtifactFactory.instance().isMappedSuperclass(embeddingEntity)){
			disableAllPersistentType(unit);
		}
	}

	/**
	 * Disable all {@link MappedSuperclass}es, {@link Entity}s and {@link Embeddable}s, which are part of
	 * element collection connection, registered in the persistence unit.
	 * @param unit
	 */
	private void disableAllNotEmbeddablesOrEmbedAsElementCollection(PersistenceUnit unit) {
		for (ClassRef classRef : unit.getClassRefs()) {
			if (classRef.getJavaPersistentType() != null) {
				final PersistentType jpt = classRef.getJavaPersistentType();
				if(JpaArtifactFactory.instance().isEntity(jpt) || JpaArtifactFactory.instance().isMappedSuperclass(jpt) 
						|| embeddingEntity.getName().equals(jpt.getName()) || isNotAllowed(embeddingEntity, jpt)){
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
	private void disableAllPersistentType(PersistenceUnit unit) {
		for (ClassRef classRef : unit.getClassRefs()) {
			if (classRef.getJavaPersistentType() != null) {
				final PersistentType jpt = classRef.getJavaPersistentType();
				getFeatureProvider().setGrayColor(jpt);
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
		for (ClassRef classRef : unit.getClassRefs()) {
			if (classRef.getJavaPersistentType() != null) {
				final PersistentType jpt = classRef.getJavaPersistentType();
				if(JpaArtifactFactory.instance().isMappedSuperclass(jpt)){
					getFeatureProvider().setGrayColor(jpt);
				}
				
			}
		}
	}
}

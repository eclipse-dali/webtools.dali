/*******************************************************************************
 * <copyright>
 *
 * Copyright (c) 2005, 2012 SAP AG.
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

import java.util.Set;

import org.eclipse.graphiti.features.context.ICreateConnectionContext;
import org.eclipse.graphiti.features.context.impl.AddConnectionContext;
import org.eclipse.graphiti.features.impl.AbstractCreateConnectionFeature;
import org.eclipse.graphiti.mm.pictograms.Anchor;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.context.Embeddable;
import org.eclipse.jpt.jpa.core.context.MappedSuperclass;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.persistence.ClassRef;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.modelintegration.util.ModelIntegrationUtil;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorFeatureProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.AbstractRelation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.HasReferanceRelation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorUtil;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JpaArtifactFactory;


abstract public class CreateRelationFeature extends AbstractCreateConnectionFeature {
	
	protected PersistentType owner;
	
	protected boolean isDerivedIdFeature;

	public CreateRelationFeature(IJPAEditorFeatureProvider fp, String name, String description, boolean isDerivedIdFeature) {
		super(fp, name, description);
		this.isDerivedIdFeature = isDerivedIdFeature;
	}

	public boolean canCreate(ICreateConnectionContext context) {
		owner = getPersistentType(context.getSourceAnchor());
		PersistentType inverse = getPersistentType(context.getTargetAnchor());
	    if ((owner == null) || (inverse == null)) 
	        return false;
	    if (JpaArtifactFactory.instance().isMappedSuperclass(inverse) 
	    		|| JpaArtifactFactory.instance().isEmbeddable(inverse))
	    	return false;
	    if(JpaArtifactFactory.instance().isEmbeddable(owner)) {
	    	if(!isRelationshipPossible() || isParentEntity(inverse) 
	    			|| JPAEditorUtil.checkJPAFacetVersion(owner.getJpaProject(), JPAEditorUtil.JPA_PROJECT_FACET_10))
	    		return false;
	    }
	    if ((this instanceof ICreateBiDirRelationFeature) && 
	    	(JpaArtifactFactory.instance().isMappedSuperclass(owner)))
	    	return false;
	    if(isDerivedIdFeature && (!JpaArtifactFactory.instance().isEntity(owner)
	    		|| !JpaArtifactFactory.instance().isEntity(inverse))){
	    	return false;
	    }
	    return true;
	}

	public boolean canStartConnection(ICreateConnectionContext context) {
		owner = getPersistentType(context.getSourceAnchor()); 
	    if (owner == null) 
	        return false;
	    return true;
	}
	
	public Connection create(ICreateConnectionContext context) {
	    Connection newConnection = null;
	    PersistentType owner = getPersistentType(context.getSourceAnchor());
	    PersistentType inverse = getPersistentType(context.getTargetAnchor());
	    if (owner != null && inverse != null) {
	    	
//	    	List<PersistentType> embeddingEntities = new ArrayList<PersistentType>();	    	
	    	if(JpaArtifactFactory.instance().isEmbeddable(owner) && (this instanceof ICreateBiDirRelationFeature)){
	    		Set<HasReferanceRelation> refs = JpaArtifactFactory.instance().findAllHasReferenceRelationsByEmbeddable(owner, getFeatureProvider());
//	    	    if(refs.size()>1){
//					SelectEmbeddingEntitiesDialog dlg = new SelectEmbeddingEntitiesDialog(owner, getFeatureProvider());
//					List<PersistentType> selectedJPTs = dlg.selectInverseRelationshipEntities();
//					if(selectedJPTs == null || selectedJPTs.isEmpty()){
//						return null;
//					}
//					embeddingEntities.addAll(selectedJPTs);
//	    	    } else {
//	    	    	embeddingEntities.add(refs.iterator().next().getEmbeddingEntity());
//	    	    }
	    	    
//	    	    for(PersistentType embeddingJPT : embeddingEntities) {
	    	    	newConnection = makeRelation(context, refs.iterator().next().getEmbeddingEntity());
//	    	    }
	    	} else {
				newConnection = makeRelation(context, null);
			}
	    }    
	    return newConnection;
	}

	private Connection makeRelation(ICreateConnectionContext context,
			PersistentType embeddingJPT) {
		Connection newConnection;
		AbstractRelation rel = createRelation(getFeatureProvider(), context.getSourceAnchor().getParent(), 
														context.getTargetAnchor().getParent(), embeddingJPT);
		AddConnectionContext addContext =
		    new AddConnectionContext(context.getSourceAnchor(), context
		        .getTargetAnchor());
		addContext.setNewObject(rel);
		newConnection =
		    (Connection) getFeatureProvider().addIfPossible(addContext);
		return newConnection;
	}
	
	/**
	 * Returns the PersistentType class belonging to the anchor, or null if not available.
	 */
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
	 * Creates a new OneToOneRelation between two PersistentType classes.
	 */
	abstract protected AbstractRelation createRelation(IJPAEditorFeatureProvider fp, PictogramElement source, 
															   PictogramElement target, PersistentType type);
	
	@Override
	public IJPAEditorFeatureProvider getFeatureProvider() {
		return (IJPAEditorFeatureProvider)super.getFeatureProvider(); 
	}
	
	/**
	 * If the relationship is between an embeddable class and an entity and if the embeddable has an
	 * element collection to another entity, then the relationships of type one-to-many and 
	 * many-to-many must not be allowed (by spec).
	 * @return true, if the relationship is allowed, false otherwise.
	 */
	abstract protected boolean isRelationshipPossible();
	
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
		disableAllEmbeddables();
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
		if(JpaArtifactFactory.instance().isEntity(owner)){
			disableAllJPTsThatAreNotEntities(unit);
		} 
		else if(JpaArtifactFactory.instance().isMappedSuperclass(owner)){
			if (isDerivedIdFeature || (this instanceof ICreateBiDirRelationFeature)){
				disableAllPersistentTypes(unit);
			} else {
				disableAllJPTsThatAreNotEntities(unit);
			}
		} 
		else if(JpaArtifactFactory.instance().isEmbeddable(owner)) {
	    	if(isDerivedIdFeature || !isRelationshipPossible() || JPAEditorUtil.checkJPAFacetVersion(owner.getJpaProject(), JPAEditorUtil.JPA_PROJECT_FACET_10)) {
	    		disableAllPersistentTypes(unit);
	    	} else {
	    		disableAllJPTsThatAreNotEntities(unit);
	    	}
	    }
	}

	/**
	 * Disable all {@link MappedSuperclass}es and {@link Embeddable}s
	 * registered in the persistence unit.
	 * @param unit
	 */
	private void disableAllJPTsThatAreNotEntities(PersistenceUnit unit) {
		for (ClassRef classRef : unit.getClassRefs()) {
			if (classRef.getJavaPersistentType() != null) {
				final PersistentType jpt = classRef.getJavaPersistentType();
				if(JpaArtifactFactory.instance().isEmbeddable(jpt) || JpaArtifactFactory.instance().isMappedSuperclass(jpt) || isParentEntity(jpt)){
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
	private void disableAllPersistentTypes(PersistenceUnit unit) {
		for (ClassRef classRef : unit.getClassRefs()) {
			if (classRef.getJavaPersistentType() != null) {
				final PersistentType jpt = classRef.getJavaPersistentType();
				getFeatureProvider().setGrayColor(jpt);
			}
		}
	}
	
	/**
	 * Disable all {@link Embeddable}s
	 * registered in the persistence unit.
	 * @param unit
	 */
	private void disableAllEmbeddables() {
		Diagram d = getDiagram();
		JpaProject project = ModelIntegrationUtil.getProjectByDiagram(d.getName());
		PersistenceUnit unit = project.getContextModelRoot().getPersistenceXml().
								getRoot().getPersistenceUnits().iterator().next();
		boolean isJPA10Project = JPAEditorUtil.checkJPAFacetVersion(ModelIntegrationUtil.getProjectByDiagram(getDiagram().getName()), JPAEditorUtil.JPA_PROJECT_FACET_10);

		for (ClassRef classRef : unit.getClassRefs()) {
			if (classRef.getJavaPersistentType() != null) {
				final PersistentType jpt = classRef.getJavaPersistentType();
				if((isJPA10Project && JpaArtifactFactory.instance().isEmbeddable(jpt))
						|| (isDerivedIdFeature && !JpaArtifactFactory.instance().isEntity(jpt))){
					getFeatureProvider().setGrayColor(jpt);
				}
				
			}
		}
	}
	
	private boolean isParentEntity(PersistentType jpt) {
		if(JpaArtifactFactory.instance().isEntity(jpt)){
			Set<HasReferanceRelation> refs = JpaArtifactFactory.instance().findAllHasReferenceRelationsByEmbeddable(owner, getFeatureProvider());
			if(!refs.isEmpty()){
				for(HasReferanceRelation ref : refs){
					if(ref.getEmbeddingEntity().getName().equals(jpt.getName())){
						return true;
					}
					
				}
			}	
		}
		return false;
	}
}

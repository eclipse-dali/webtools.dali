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
import org.eclipse.jpt.jpa.core.context.MappedSuperclass;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.context.persistence.ClassRef;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.i18n.JPAEditorMessages;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.modelintegration.util.ModelIntegrationUtil;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorFeatureProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.JPAEditorImageProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.IsARelation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorUtil;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JpaArtifactFactory;

public class CreateIsARelationFeature extends AbstractCreateConnectionFeature {
	
	private JavaPersistentType superclass;
	
	public CreateIsARelationFeature(IFeatureProvider fp) {
		this(fp, JPAEditorMessages.CreateIsARelationFeature_CreateIsARelationFeatureName, JPAEditorMessages.CreateIsARelationFeature_CreateIsARelationFeatureDescription);
	}
		
	public CreateIsARelationFeature(IFeatureProvider fp, String name,
			String description) {
		super(fp, name, description);
	}

	public boolean canCreate(ICreateConnectionContext context) {
		superclass = (JavaPersistentType)getPersistentType(context.getSourceAnchor());
		JavaPersistentType subclass = (JavaPersistentType)getPersistentType(context.getTargetAnchor());
	    if ((superclass == null) || (subclass == null)) 
	        return false;
		
	    if(superclass.equals(subclass))
	    	return false;
	    
	    if (!JpaArtifactFactory.instance().hasEntityAnnotation(subclass) || subclass.getSuperPersistentType() != null)
	    	return false;

	    if(!JpaArtifactFactory.instance().hasEntityAnnotation(superclass) && 
	    				!JpaArtifactFactory.instance().hasMappedSuperclassAnnotation(superclass))
	    	return false;
	    return true;
	}

	public Connection create(ICreateConnectionContext context) {
		superclass = (JavaPersistentType)getPersistentType(context.getSourceAnchor());
		JavaPersistentType subclass = (JavaPersistentType)getPersistentType(context.getTargetAnchor());
		
		if(JpaArtifactFactory.instance().hasOrInheritsPrimaryKey(superclass)){
			for(JavaSpecifiedPersistentAttribute jpa : subclass.getAttributes()){
				if(jpa.getMappingKey().equals(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY)){
					jpa.setMappingKey(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY);
				} else if(jpa.getMappingKey().equals(MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY)) {
					jpa.setMappingKey(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY);
				}
			}
		}
		
		JpaArtifactFactory.instance().buildHierarchy(superclass, subclass, true);
		
		JPAEditorUtil.createImport(JPAEditorUtil.getCompilationUnit(subclass), superclass.getName());
		
		IsARelation rel = new IsARelation(subclass, superclass);
		AddInheritedEntityFeature ft = new AddInheritedEntityFeature(getFeatureProvider());
		
		AddConnectionContext cont =  new AddConnectionContext(context.getTargetAnchor(), context.getSourceAnchor());
		cont.setNewObject(rel);
		
		Connection connection = (Connection) ft.add(cont);	
		
		return connection;
	}

	public boolean canStartConnection(ICreateConnectionContext context) {
		superclass = (JavaPersistentType)getPersistentType(context.getSourceAnchor());

	    if (superclass == null)
	        return false;
	    
	    return true;
	}
	
    @Override
	public String getCreateImageId() {
        return JPAEditorImageProvider.ICON_DERIVE_JPT;
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
		disableAllEmbeddables();
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
		for (ClassRef classRef : unit.getClassRefs()) {
			if (classRef.getJavaPersistentType() != null) {
				final JavaPersistentType jpt = classRef.getJavaPersistentType();
				if(!JpaArtifactFactory.instance().hasEntityAnnotation(jpt) || superclass.equals(jpt) || jpt.getSuperPersistentType() != null){
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
	private void disableAllEmbeddables() {
		Diagram d = getDiagram();
		JpaProject project = ModelIntegrationUtil.getProjectByDiagram(d.getName());
		PersistenceUnit unit = project.getContextModelRoot().getPersistenceXml().
								getRoot().getPersistenceUnits().iterator().next();
		for (ClassRef classRef : unit.getClassRefs()) {
			if (classRef.getJavaPersistentType() != null) {
				final JavaPersistentType jpt = classRef.getJavaPersistentType();
				if(JpaArtifactFactory.instance().hasEmbeddableAnnotation(jpt)){
					getFeatureProvider().setGrayColor(jpt);
				}
				
			}
		}
	}
}

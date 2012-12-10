/*******************************************************************************
 * <copyright>
 *
 * Copyright (c) 2005, 2010 SAP AG.
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

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICreateConnectionContext;
import org.eclipse.graphiti.features.context.ICreateContext;
import org.eclipse.graphiti.features.context.impl.AddContext;
import org.eclipse.graphiti.features.context.impl.CreateContext;
import org.eclipse.graphiti.features.impl.AbstractCreateConnectionFeature;
import org.eclipse.graphiti.mm.pictograms.Anchor;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.context.Embeddable;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.context.persistence.ClassRef;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.JPADiagramEditorPlugin;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.i18n.JPAEditorMessages;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.modelintegration.util.ModelIntegrationUtil;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorFeatureProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.JPAEditorImageProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JpaArtifactFactory;

public class CreateInheritedEntityFeature extends AbstractCreateConnectionFeature {

	public CreateInheritedEntityFeature(IFeatureProvider fp) {
		this(fp, JPAEditorMessages.CreateIsARelationFeature_name,	JPAEditorMessages.CreateIsARelationFeature_description);
	}
		
	public CreateInheritedEntityFeature(IFeatureProvider fp, String name,
			String description) {
		super(fp, name, description);
	}

	public boolean canCreate(ICreateConnectionContext context) {
		JavaPersistentType superclass = (JavaPersistentType)getPersistentType(context.getSourceAnchor());
	    if (superclass == null)
	    	return false;
	    if (context.getTargetAnchor() == null)
	    	return true;
		if (context.getTargetAnchor().getParent() instanceof Diagram)
			return true;
	    return false;
	}

	public Connection create(ICreateConnectionContext context) {
		JavaPersistentType mappedSuperclass = (JavaPersistentType)getPersistentType(context.getSourceAnchor());
		CreateJPAEntityFeature createEntityFeature = null;
		try {
			createEntityFeature = new CreateJPAEntityFeature(
					getFeatureProvider(), mappedSuperclass);
		} catch (JavaModelException e) {
			JPADiagramEditorPlugin.logError("Cannot create a new JPA entity class", e); //$NON-NLS-1$	
			return null;
		}
		ICreateContext ctx = new CreateContext();
		Object[] res = createEntityFeature.create(ctx);
		JavaPersistentType newEntity = (JavaPersistentType)res[0];
		AddJPAEntityFeature ft = new AddJPAEntityFeature(getFeatureProvider(), true);
		AddContext cont = new AddContext();
		cont.setTargetContainer(getFeatureProvider().getDiagram());
		cont.setNewObject(newEntity);
		cont.setLocation(context.getTargetLocation().getX(), context.getTargetLocation().getY());
		ft.add(cont);		
		return null;
	}

	public boolean canStartConnection(ICreateConnectionContext context) {
		JavaPersistentType superclass = (JavaPersistentType)getPersistentType(context.getSourceAnchor()); 
	    if (superclass == null) 
	        return false;
	    return true;
	}
	
    @Override
	public String getCreateImageId() {
        return JPAEditorImageProvider.ADD_INHERITED_ENTITY;
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
	public void endConnecting() {
		super.endConnecting();
		getFeatureProvider().setOriginalPersistentTypeColor();
	}
	
	@Override
	public void startConnecting() {
		super.startConnecting();
		disableAllNotValidJPTs();
	}
	
	private void disableAllNotValidJPTs(){
		Diagram d = getDiagram();
		JpaProject project = ModelIntegrationUtil.getProjectByDiagram(d.getName());
		PersistenceUnit unit = project.getRootContextNode().getPersistenceXml().
								getRoot().getPersistenceUnits().iterator().next();
		disableAllEmbeddables(unit);
	}
	
	/**
	 * Disable (color in gray) all {@link Embeddable}s registered in the persistence unit.
	 * @param unit
	 */
	private void disableAllEmbeddables(PersistenceUnit unit) {
		for (ClassRef classRef : unit.getClassRefs()) {
			if (classRef.getJavaPersistentType() != null) {
				final JavaPersistentType jpt = classRef.getJavaPersistentType();
				if(JpaArtifactFactory.instance().hasEmbeddableAnnotation(jpt))
					getFeatureProvider().setGrayColor(jpt);
			}
		}
	}

}

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

import org.eclipse.graphiti.features.context.ICreateConnectionContext;
import org.eclipse.graphiti.features.context.impl.AddConnectionContext;
import org.eclipse.graphiti.features.impl.AbstractCreateConnectionFeature;
import org.eclipse.graphiti.mm.pictograms.Anchor;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorFeatureProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.AbstractRelation;


abstract public class CreateRelationFeature extends AbstractCreateConnectionFeature {

	public CreateRelationFeature(IJPAEditorFeatureProvider fp, String name, String description) {
		super(fp, name, description);
	}

	public boolean canCreate(ICreateConnectionContext context) {
		JavaPersistentType source = (JavaPersistentType)getPersistentType(context.getSourceAnchor());
		JavaPersistentType target = (JavaPersistentType)getPersistentType(context.getTargetAnchor());
	    if (source != null && target != null) {
	        return true;
	    }
	    return false;
	}

	public boolean canStartConnection(ICreateConnectionContext context) {
	    if (getPersistentType(context.getSourceAnchor()) != null) 
	        return true;
	    return false;
	}
	
	public Connection create(ICreateConnectionContext context) {
	    Connection newConnection = null;
	    PersistentType source = getPersistentType(context.getSourceAnchor());
	    PersistentType target = getPersistentType(context.getTargetAnchor());
	    if (source != null && target != null) {
	    	AbstractRelation rel = createRelation(getFeatureProvider(), context.getSourceAnchor().getParent(), 
	    													context.getTargetAnchor().getParent());
	        AddConnectionContext addContext =
	            new AddConnectionContext(context.getSourceAnchor(), context
	                .getTargetAnchor());
	        addContext.setNewObject(rel);
	        newConnection =
	            (Connection) getFeatureProvider().addIfPossible(addContext);
	    }    
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
															   PictogramElement target);
	
	public IJPAEditorFeatureProvider getFeatureProvider() {
		return (IJPAEditorFeatureProvider)super.getFeatureProvider(); 
	}

}
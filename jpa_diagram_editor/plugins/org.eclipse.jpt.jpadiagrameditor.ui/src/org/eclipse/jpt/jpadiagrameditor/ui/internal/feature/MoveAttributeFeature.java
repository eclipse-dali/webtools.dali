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
import org.eclipse.graphiti.features.context.IContext;
import org.eclipse.graphiti.features.context.IMoveShapeContext;
import org.eclipse.graphiti.features.impl.DefaultMoveShapeFeature;

/*
 * The attribute shapes (residing within the entity shapes) 
 * are NOT supposed to be moveable (at least - not by 
 * drag-and-drop). The only purpose of this class is to 
 * disable this ability
 * 
 */

public class MoveAttributeFeature extends DefaultMoveShapeFeature {
	public MoveAttributeFeature(IFeatureProvider fp) {
		super(fp);
	}

    public boolean canMoveShape(IMoveShapeContext context) {
        return false;
    }
    
    
    public boolean canExecute(IContext context) {
        return false;
    }

    
    public boolean isAvailable(IContext context) {
    	return false;
    }	
	
}

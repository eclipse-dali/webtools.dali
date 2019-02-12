/*******************************************************************************
 * <copyright>
 *
 * Copyright (c) 2005, 2012 SAP AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
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

    @Override
	public boolean canMoveShape(IMoveShapeContext context) {
        return false;
    }
    
    
    @Override
	public boolean canExecute(IContext context) {
        return false;
    }

    
    @Override
	public boolean isAvailable(IContext context) {
    	return false;
    }	
	
}

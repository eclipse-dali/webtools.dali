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
import org.eclipse.graphiti.features.context.IResizeShapeContext;
import org.eclipse.graphiti.features.impl.DefaultResizeShapeFeature;


public class ResizeAttributeFeature  extends DefaultResizeShapeFeature {

	public ResizeAttributeFeature(IFeatureProvider fp) {
		super(fp);
	}
	
    public boolean canResizeShape(IResizeShapeContext context) {
        return false;
    }
    
    
    public boolean canExecute(IContext context) {
        return false;
    }

    
    public boolean isAvailable(IContext context) {
    	return false;
    }

}

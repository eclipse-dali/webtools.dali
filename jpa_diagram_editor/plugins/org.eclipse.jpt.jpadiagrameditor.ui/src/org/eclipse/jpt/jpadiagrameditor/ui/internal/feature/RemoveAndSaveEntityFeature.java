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

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IRemoveContext;
import org.eclipse.graphiti.features.context.impl.RemoveContext;
import org.eclipse.graphiti.features.impl.DefaultRemoveFeature;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorFeatureProvider;



public class RemoveAndSaveEntityFeature extends DefaultRemoveFeature {
	
    public RemoveAndSaveEntityFeature(IFeatureProvider fp) {
    	super(fp);
    }
    
    public void preRemove(IRemoveContext context) {
    	PictogramElement pe = context.getPictogramElement();
    	Object bo = getFeatureProvider().getBusinessObjectForPictogramElement(pe);
    	Set<Shape> shapesForDeletion = new HashSet<Shape>();
    	if (bo instanceof PersistentType) {
    		IFeatureProvider fp = getFeatureProvider();
    		List<Shape> lst = ((ContainerShape)pe).getChildren();
    		for (int i = lst.size() - 1; i >= 0; i--) {
    			Shape textShape = lst.get(i);
    			Object o = fp.getBusinessObjectForPictogramElement(textShape);
    			if ((o != null) && (o instanceof JavaPersistentAttribute)) {
    				shapesForDeletion.add(textShape);
    			}
    		}
    		Iterator<Shape> it = shapesForDeletion.iterator();
    		while(it.hasNext()) {
				RemoveAttributeFeature f = new RemoveAttributeFeature(fp, false, true);
				IRemoveContext ctx = new RemoveContext(it.next());
				f.remove(ctx);    			
    		}
    		getFeatureProvider().remove(((PersistentType)bo).getName(), true); 
    	} 			
    }
    
	public IJPAEditorFeatureProvider getFeatureProvider() {
		return  (IJPAEditorFeatureProvider)super.getFeatureProvider();
	}

}
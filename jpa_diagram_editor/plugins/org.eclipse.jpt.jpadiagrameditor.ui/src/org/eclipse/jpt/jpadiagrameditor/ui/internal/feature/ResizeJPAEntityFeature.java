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

import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IResizeShapeContext;
import org.eclipse.graphiti.features.impl.DefaultResizeShapeFeature;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorFeatureProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorUtil;


public class ResizeJPAEntityFeature extends DefaultResizeShapeFeature {

	public ResizeJPAEntityFeature(IFeatureProvider fp) {
        super(fp);
    }

    @Override
    public boolean canResizeShape(IResizeShapeContext context) {
        boolean canResize = super.canResizeShape(context);
        if (canResize) {
        	Shape shape = context.getShape();
            Object bo = getBusinessObjectForPictogramElement(shape);       	
            if (bo == null) return false;
            if (bo instanceof PersistentType) {
            	PersistentType c = (PersistentType) bo;
                if (c.getName() != null && 
                		c.getName().length() == 1) {
                    canResize = false;
                }
            }
        }
        return canResize;
    }
    
	private void resizeContainerShape(IResizeShapeContext context) {
		super.resizeShape(context);
	}

	public void resizeShape(final IResizeShapeContext context) {
		final ContainerShape entityShape = (ContainerShape) context.getShape();
		TransactionalEditingDomain ted = TransactionUtil.getEditingDomain(entityShape);
		ted.getCommandStack().execute(new RecordingCommand(ted) {
			protected void doExecute() {
				resizeContainerShape(context);
				JPAEditorUtil.rearrangeAllConnections(entityShape,
						getFeatureProvider(), false);
			}
		});
	}
	
	public IJPAEditorFeatureProvider getFeatureProvider() {
		return  (IJPAEditorFeatureProvider)super.getFeatureProvider();
	}
	
    
}

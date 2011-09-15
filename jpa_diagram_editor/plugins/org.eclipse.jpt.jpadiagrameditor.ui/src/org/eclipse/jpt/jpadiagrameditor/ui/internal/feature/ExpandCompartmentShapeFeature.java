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
 *    Petya Sabeva - initial API, implementation and documentation
 *
 * </copyright>
 *
 *******************************************************************************/
package org.eclipse.jpt.jpadiagrameditor.ui.internal.feature;

import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IContext;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.context.impl.CustomContext;
import org.eclipse.graphiti.features.custom.AbstractCustomFeature;
import org.eclipse.graphiti.features.custom.ICustomFeature;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorFeatureProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.GraphicsUpdater;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorConstants;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorUtil;


public class ExpandCompartmentShapeFeature extends AbstractCustomFeature{
	
	public ExpandCompartmentShapeFeature(IFeatureProvider fp) {
		super(fp);
	}

	public void execute(ICustomContext context) {
		PictogramElement el = context.getPictogramElements()[0];

		Object ob = getFeatureProvider().getBusinessObjectForPictogramElement(el);
		int pictogramHeight = JPAEditorConstants.COMPARTMENT_MIN_HEIGHT;
		int newHeight = JPAEditorConstants.COMPARTMENT_BUTTOM_OFFSET;
		final ContainerShape containerShape = (ContainerShape) el;
		if(ob == null)
			newHeight = GraphicsUpdater.increaseCompartmentHeigth(containerShape, newHeight);
		final int pictHeight = pictogramHeight + newHeight;
        final GraphicsAlgorithm algo = el.getGraphicsAlgorithm();
        final ContainerShape entityShape = containerShape.getContainer();
        TransactionalEditingDomain ted = TransactionUtil.getEditingDomain(algo);
        ted.getCommandStack().execute(new RecordingCommand(ted) {
			protected void doExecute() {
		        algo.setHeight(pictHeight);
		        int height = GraphicsUpdater.getPrimaryShape(entityShape).getGraphicsAlgorithm().getHeight() +
		                     GraphicsUpdater.getRelationShape(entityShape).getGraphicsAlgorithm().getHeight() + 
		                     GraphicsUpdater.getBasicShape(entityShape).getGraphicsAlgorithm().getHeight() + JPAEditorConstants.ENTITY_MIN_HEIGHT;
		        
		        if(height>entityShape.getGraphicsAlgorithm().getHeight()){
		        	ICustomContext cont = new CustomContext(new PictogramElement[] {entityShape});
		        	ICustomFeature expandEntity = new ExpandEntityFeature(getFeatureProvider());
		        	expandEntity.execute(cont);
		        }
		        GraphicsUpdater.updateEntityShape(entityShape);
				GraphicsUpdater.setCollapsed(containerShape, false);
				JPAEditorUtil.rearrangeAllConnections(entityShape, getFeatureProvider(), false);				
			}
        });
	}
	
	@Override
	public boolean canExecute(ICustomContext context) {
		return true;
	}
	
	
	@Override
	public boolean isAvailable(IContext context) {
		boolean ret = false;
		if (context instanceof ICustomContext) {
			PictogramElement[] pes = ((ICustomContext) context).getPictogramElements();
			if (pes != null && pes.length > 0) {
				boolean expandPossible = false;
				for (int i = 0; i < pes.length; i++) {
					PictogramElement pe = pes[i];
					Object bo = getBusinessObjectForPictogramElement(pe);
					if (bo != null) {
						expandPossible = false;
						return false;
					}
					if (!expandPossible) {
						if (GraphicsUpdater.isCollapsed((ContainerShape)pe)) {
							expandPossible = true;
						}
					}
				}
				if (expandPossible) {
					return true;
				}
			}
		}

		return ret;
	}
	
	public IJPAEditorFeatureProvider getFeatureProvider() {
		return  (IJPAEditorFeatureProvider)super.getFeatureProvider();
	}
	

}

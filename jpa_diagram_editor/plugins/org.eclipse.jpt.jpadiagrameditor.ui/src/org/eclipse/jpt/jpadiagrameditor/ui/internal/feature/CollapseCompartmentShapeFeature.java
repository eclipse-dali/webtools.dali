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
import org.eclipse.graphiti.features.custom.AbstractCustomFeature;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorFeatureProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.GraphicsUpdater;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorConstants;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorUtil;


public class CollapseCompartmentShapeFeature extends AbstractCustomFeature{

	public CollapseCompartmentShapeFeature(IFeatureProvider fp) {
		super(fp);
	}

	public void execute(ICustomContext context) {
		PictogramElement el = context.getPictogramElements()[0];
		
		final ContainerShape containerShape = (ContainerShape) el;
		final ContainerShape entityShape = containerShape.getContainer();
		
		final GraphicsAlgorithm algo = el.getGraphicsAlgorithm();
		TransactionalEditingDomain ted = TransactionUtil.getEditingDomain(algo);
		ted.getCommandStack().execute(new RecordingCommand(ted) {
			protected void doExecute() {
			    algo.setHeight(JPAEditorConstants.COMPARTMENT_MIN_HEIGHT);
				GraphicsUpdater.updateEntityShape(entityShape);
				GraphicsUpdater.setCollapsed(containerShape, true);
				JPAEditorUtil.rearrangeAllConnections(entityShape, getFeatureProvider(), false);				
			}
		});
	}

	@Override
	public boolean canExecute(ICustomContext context) {
		return true;
	}

	public IJPAEditorFeatureProvider getFeatureProvider() {
		return  (IJPAEditorFeatureProvider)super.getFeatureProvider();
	}
	
    @Override
	public boolean isAvailable(IContext context) {
		boolean ret = false;
		if (context instanceof ICustomContext) {
			PictogramElement[] pes = ((ICustomContext) context).getPictogramElements();
			if (pes != null && pes.length > 0) {
				boolean collapsePossible = false;
				for (int i = 0; i < pes.length; i++) {
					PictogramElement pe = pes[i];
					Object bo = getBusinessObjectForPictogramElement(pe);
					if (bo != null) {
						return false;
					}
					if (!collapsePossible) {
						if(!GraphicsUpdater.isCollapsed((ContainerShape)pe)) {
							collapsePossible = true;
						}
					}
				}
				if (collapsePossible) {
					return true;
				}
			}
		}
		return ret;
	}

}

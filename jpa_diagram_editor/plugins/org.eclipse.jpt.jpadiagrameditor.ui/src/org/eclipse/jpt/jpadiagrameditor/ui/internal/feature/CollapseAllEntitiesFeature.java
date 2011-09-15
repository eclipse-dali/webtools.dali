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

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IContext;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.custom.AbstractCustomFeature;
import org.eclipse.graphiti.features.custom.ICustomFeature;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.i18n.JPAEditorMessages;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorFeatureProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorConstants;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorUtil;


public class CollapseAllEntitiesFeature extends AbstractCustomFeature implements ICustomFeature{
	
	public CollapseAllEntitiesFeature(IFeatureProvider fp) {
		super(fp);
	}

	public void execute(ICustomContext context) {
		Diagram diagram = getFeatureProvider().getDiagramTypeProvider().getDiagram();
		final EList<Shape> entShapes = diagram.getChildren();
		TransactionalEditingDomain ted = TransactionUtil.getEditingDomain(diagram);
		ted.getCommandStack().execute(new RecordingCommand(ted) {
			protected void doExecute() {
				for (final PictogramElement el : entShapes) {
					String collapseFeatures = Graphiti.getPeService().getPropertyValue(el, JPAEditorConstants.COLLAPSE_FEATURES);
					if (collapseFeatures == null) {	
				        GraphicsAlgorithm algo = el.getGraphicsAlgorithm();
						algo.setHeight(JPAEditorConstants.ENTITY_MIN_HEIGHT);
						Graphiti.getPeService().setPropertyValue(el, JPAEditorConstants.COLLAPSE_FEATURES, String.valueOf(JPAEditorConstants.ENTITY_MIN_HEIGHT));
						JPAEditorUtil.rearrangeAllConnections((ContainerShape) el, getFeatureProvider(), false);					
					}
				}
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
		return true;
	}
	
	@Override
	public String getName() {
		return JPAEditorMessages.JPAEditorToolBehaviorProvider_collapseAllEntitiesMenuItem;
	}
	
}

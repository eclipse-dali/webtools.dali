/*******************************************************************************
 * <copyright>
 *
 * Copyright (c) 2005, 2013 SAP AG.
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

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IContext;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.custom.AbstractCustomFeature;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.i18n.JPAEditorMessages;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorFeatureProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.GraphicsUpdater;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorConstants;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorUtil;


public class ExpandAllEntitiesFeature extends AbstractCustomFeature{
		
	public ExpandAllEntitiesFeature(IFeatureProvider fp) {
		super(fp);
	}
	
	public void execute(ICustomContext context) {
		final Diagram diagram = getFeatureProvider().getDiagramTypeProvider().getDiagram();
		TransactionalEditingDomain ted = TransactionUtil.getEditingDomain(diagram);
		
		ted.getCommandStack().execute(new RecordingCommand(ted) {
			@Override
			protected void doExecute() {
				EList<Shape> shapes = diagram.getChildren();
				for (PictogramElement el : shapes) {			
					String collapseFeatures = Graphiti.getPeService().getPropertyValue(el, JPAEditorConstants.COLLAPSE_FEATURES);
					if (collapseFeatures != null) {
						Object bo = getBusinessObjectForPictogramElement(el);
						if(bo instanceof PersistentType){
							ContainerShape containerShape = (ContainerShape) el;	
							GraphicsUpdater.updateEntityShape(containerShape);
							GraphicsUpdater.updateEntityHeigth(containerShape);
							Graphiti.getPeService().removeProperty(el, JPAEditorConstants.COLLAPSE_FEATURES);
							JPAEditorUtil.rearrangeAllConnections(containerShape, getFeatureProvider(), false);
						}					
					}
				}
			}
		});
		
	}
	
	@Override
	public boolean canExecute(ICustomContext context) {
		return true;
	}

	@Override
	public boolean isAvailable(IContext context) {
		return true;
	}
	
	@Override
	public IJPAEditorFeatureProvider getFeatureProvider() {
		return  (IJPAEditorFeatureProvider)super.getFeatureProvider();
	}
	
	
	@Override
	public String getName() {
		return JPAEditorMessages.JPAEditorToolBehaviorProvider_expandAllEntitiesMenuItem;
	}
	

}

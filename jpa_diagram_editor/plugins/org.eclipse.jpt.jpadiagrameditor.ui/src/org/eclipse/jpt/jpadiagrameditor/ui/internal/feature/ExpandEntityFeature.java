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
import org.eclipse.graphiti.mm.Property;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.i18n.JPAEditorMessages;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorFeatureProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.GraphicsUpdater;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorConstants;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorUtil;


public class ExpandEntityFeature extends AbstractCustomFeature{
		
	public ExpandEntityFeature(IFeatureProvider fp) {
		super(fp);
	}
	
	public void execute(ICustomContext context) {
		final PictogramElement el = context.getPictogramElements()[0];

		Object bo = getBusinessObjectForPictogramElement(el);
		if(bo instanceof PersistentType){
		final ContainerShape containerShape = (ContainerShape) el;
	
		TransactionalEditingDomain ted = TransactionUtil.getEditingDomain(containerShape);
			ted.getCommandStack().execute(new RecordingCommand(ted) {
				@Override
				protected void doExecute() {

					GraphicsUpdater.updateEntityShape(containerShape);
					GraphicsUpdater.updateEntityHeigth(containerShape);
					Graphiti.getPeService().removeProperty(el, JPAEditorConstants.COLLAPSE_FEATURES);

					JPAEditorUtil.rearrangeAllConnections(containerShape, getFeatureProvider(), false);
				}
			});
		}
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
					if (!(bo instanceof PersistentType)) {
						return false;
					}
					if (!expandPossible) {
						Property property = Graphiti.getPeService().getProperty(pe, JPAEditorConstants.COLLAPSE_FEATURES);
						if (property != null) {
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
	
	@Override
	public IJPAEditorFeatureProvider getFeatureProvider() {
		return  (IJPAEditorFeatureProvider)super.getFeatureProvider();
	}
	
	@Override
	public String getName() {
		return JPAEditorMessages.JPAEditorToolBehaviorProvider_expandEntityMenuItem;
	}
	

}

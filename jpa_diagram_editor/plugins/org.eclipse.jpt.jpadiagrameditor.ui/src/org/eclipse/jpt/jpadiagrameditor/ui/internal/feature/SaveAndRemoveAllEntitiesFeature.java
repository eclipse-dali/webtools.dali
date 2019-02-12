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

import java.util.Iterator;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.context.impl.CustomContext;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.i18n.JPAEditorMessages;
import org.eclipse.swt.widgets.Display;


public class SaveAndRemoveAllEntitiesFeature extends RemoveAllEntitiesFeature {

	public SaveAndRemoveAllEntitiesFeature(IFeatureProvider fp) {
		super(fp);
	}
	
	@Override
	public String getConfirmationText() {
		return JPAEditorMessages.JPAEditorToolBehaviorProvider_removeAndSaveAllEntitiesConfirmation;
	}
	

	@Override
	public void execute(ICustomContext context) {
		MessageDialog dlg = new MessageDialog(Display.getCurrent().getShells()[0], 
				JPAEditorMessages.JPAEditorToolBehaviorProvider_removeAllEntitiesMenu, 
				null, getConfirmationText(), 0, 
				new String[] {JPAEditorMessages.BTN_OK, JPAEditorMessages.BTN_CANCEL}, Window.CANCEL);
		if (dlg.open() != Window.OK)
			return;
		Iterator<Shape> it = this.getDiagram().getChildren().iterator();
		while (it.hasNext()) {
			Shape sh = it.next();
			SaveEntityFeature ft = new SaveEntityFeature(getFeatureProvider());
			ICustomContext ctx = new CustomContext(new PictogramElement[] { sh });
			ft.execute(ctx);
			allShapes.add(sh);
		}
		super.execute(context);
	}	
	
	@Override
	public String getName() {
		return JPAEditorMessages.SaveAndRemoveAllEntitiesFeature_ContextMenuOperationDescription;
	}
}

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
package org.eclipse.jpt.jpadiagrameditor.ui.internal.provider;

import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.graphiti.ui.editor.DiagramEditorContextMenuProvider;
import org.eclipse.graphiti.ui.platform.IConfigurationProvider;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.swt.graphics.Point;

public class JPAEditorContextMenuProvider extends DiagramEditorContextMenuProvider {
	public JPAEditorContextMenuProvider(EditPartViewer viewer, 
										ActionRegistry registry, 
										IConfigurationProvider configurationProvider) {
		super(viewer, registry, configurationProvider);
	}
		

	protected void addDefaultMenuGroupEdit(IMenuManager manager, Point menuLocation) {
	}
	
	@Override
	protected void addActionToMenuIfAvailable(IMenuManager manager, String actionId, String menuGroup) {
		if (actionId.equals("predefined remove action") ||	//$NON-NLS-1$
			actionId.equals("predefined update action")) 	//$NON-NLS-1$
			return;
		super.addActionToMenuIfAvailable(manager, actionId, menuGroup);
	}
	
	
}

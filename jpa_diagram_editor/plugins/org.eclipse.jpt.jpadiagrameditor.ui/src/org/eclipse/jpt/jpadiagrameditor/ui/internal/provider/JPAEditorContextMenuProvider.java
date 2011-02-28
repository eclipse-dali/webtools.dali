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
package org.eclipse.jpt.jpadiagrameditor.ui.internal.provider;

import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.graphiti.ui.editor.DiagramEditorContextMenuProvider;
import org.eclipse.graphiti.ui.internal.config.IConfigurationProvider;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.swt.graphics.Point;

@SuppressWarnings("restriction")
public class JPAEditorContextMenuProvider extends DiagramEditorContextMenuProvider {
	public JPAEditorContextMenuProvider(EditPartViewer viewer, 
										ActionRegistry registry, 
										IConfigurationProvider configurationProvider) {
		super(viewer, registry, configurationProvider);
	}
		

	protected void addDefaultMenuGroupEdit(IMenuManager manager, Point menuLocation) {
	}
	
	protected void addActionToMenuIfAvailable(IMenuManager manager, String actionId, String menuGroup) {
		if (actionId.equals("predefined remove action") ||	//$NON-NLS-1$
			actionId.equals("predefined update action")) 	//$NON-NLS-1$
			return;
		super.addActionToMenuIfAvailable(manager, actionId, menuGroup);
	}
	
	
}

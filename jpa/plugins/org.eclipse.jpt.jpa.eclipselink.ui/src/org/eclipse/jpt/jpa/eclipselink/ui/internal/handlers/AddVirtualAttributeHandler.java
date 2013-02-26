/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jpt.common.core.internal.utility.PlatformTools;
import org.eclipse.jpt.jpa.core.context.orm.OrmModifiablePersistentAttribute;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkOrmPersistentType;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.dialogs.AddVirtualAttributeDialog;
import org.eclipse.jpt.jpa.ui.selection.JpaSelectionManager;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * Add a virtual attribute (no underlying java attribute) to the selected persistent type.
 * This handler is only active if <em>one</em> EclipseLink ORM persistent type is selected.
 * <p>
 * See <code>org.eclipse.jpt.jpa.eclipselink.ui/plugin.xml</code>.
 */
public class AddVirtualAttributeHandler
	extends AbstractHandler
{
	public Object execute(ExecutionEvent event) throws ExecutionException {
		this.execute_(event);
		return null;
	}

	private void execute_(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		IStructuredSelection selection = (IStructuredSelection) HandlerUtil.getCurrentSelectionChecked(event);
		EclipseLinkOrmPersistentType persistentType = (EclipseLinkOrmPersistentType) selection.getFirstElement();

		AddVirtualAttributeDialog dialog = new AddVirtualAttributeDialog(window.getShell(), persistentType);
		dialog.create();
		dialog.setBlockOnOpen(true);
		OrmModifiablePersistentAttribute attribute = dialog.openAndReturnAttribute();

		if (attribute != null) {
			JpaSelectionManager selectionManager = PlatformTools.getAdapter(window, JpaSelectionManager.class);
			selectionManager.setSelection(attribute);
		}
	}
}

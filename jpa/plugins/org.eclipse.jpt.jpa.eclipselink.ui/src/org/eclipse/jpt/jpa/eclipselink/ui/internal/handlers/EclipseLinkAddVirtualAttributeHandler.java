/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
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
import org.eclipse.jpt.jpa.core.context.orm.OrmSpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkOrmPersistentType;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.dialogs.EclipseLinkAddVirtualAttributeDialog;
import org.eclipse.jpt.jpa.ui.selection.JpaSelectionManager;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * Add a virtual attribute (no underlying java attribute) to the selected persistent type.
 * This handler is only active if <em>one</em> EclipseLink ORM persistent type is selected.
 * <p>
 * See <code>org.eclipse.jpt.jpa.eclipselink.ui/plugin.xml:org.eclipse.ui.handlers</code>.
 */
public class EclipseLinkAddVirtualAttributeHandler
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

		EclipseLinkAddVirtualAttributeDialog dialog = new EclipseLinkAddVirtualAttributeDialog(window.getShell(), persistentType);
		dialog.create();
		dialog.setBlockOnOpen(true);
		OrmSpecifiedPersistentAttribute attribute = dialog.openAndReturnAttribute();

		if (attribute != null) {
			JpaSelectionManager selectionManager = PlatformTools.getAdapter(window, JpaSelectionManager.class);
			selectionManager.setSelection(attribute);
		}
	}
}

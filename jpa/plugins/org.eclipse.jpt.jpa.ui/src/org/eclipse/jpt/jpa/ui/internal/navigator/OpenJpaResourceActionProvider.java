/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.navigator;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jpt.jpa.ui.internal.actions.OpenJpaResourceAction;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.actions.ActionContext;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionConstants;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;
import org.eclipse.ui.navigator.ICommonMenuConstants;

/**
 * The selection will be a JPA context node.
 * <p>
 * See <code>org.eclipse.jpt.jpa.ui/plugin.xml</code>.
 */
public class OpenJpaResourceActionProvider
	extends CommonActionProvider
{
	private OpenJpaResourceAction openAction;


	public OpenJpaResourceActionProvider() {
		super();
	}

	@Override
	public void init(ICommonActionExtensionSite config) {
		this.openAction = new OpenJpaResourceAction();
	}

	@Override
	public void setContext(ActionContext context) {
		if ((context != null) && (context.getSelection() instanceof IStructuredSelection)) {
			IStructuredSelection selection = (IStructuredSelection) context.getSelection();
			this.openAction.selectionChanged(selection);
		}
		super.setContext(context);
	}

	@Override
	public void fillActionBars(IActionBars actionBars) {
		if (this.openAction.isEnabled()) {
			actionBars.setGlobalActionHandler(ICommonActionConstants.OPEN, this.openAction);
		}
	}

	@Override
	public void fillContextMenu(IMenuManager menuManager) {
		ActionContext context = this.getContext();
		if ((context == null) || context.getSelection().isEmpty()) {
			return;
		}

		if (this.openAction.isEnabled()) {
			menuManager.insertAfter(ICommonMenuConstants.GROUP_OPEN, this.openAction);
		}
	}
}

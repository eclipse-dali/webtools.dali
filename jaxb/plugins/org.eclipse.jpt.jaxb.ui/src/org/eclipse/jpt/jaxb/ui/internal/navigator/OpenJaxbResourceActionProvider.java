/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.ui.internal.navigator;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jpt.jaxb.ui.internal.actions.OpenJaxbResourceAction;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.actions.ActionContext;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionConstants;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;
import org.eclipse.ui.navigator.ICommonMenuConstants;


public class OpenJaxbResourceActionProvider
		extends CommonActionProvider {
	
	private OpenJaxbResourceAction openAction;
	
	
	public OpenJaxbResourceActionProvider() {
		super();
	}
	
	
	@Override
	public void init(ICommonActionExtensionSite aConfig) {
		this.openAction = new OpenJaxbResourceAction();
	}
	
	@Override
	public void setContext(ActionContext aContext) {
		if (aContext != null && aContext.getSelection() instanceof IStructuredSelection) {
			IStructuredSelection selection = (IStructuredSelection) aContext.getSelection();
			this.openAction.selectionChanged(selection);
		}
		
		super.setContext(aContext);
	}
	
	@Override
	public void fillActionBars(IActionBars theActionBars) {
		if (this.openAction.isEnabled()) {
			theActionBars.setGlobalActionHandler(ICommonActionConstants.OPEN, this.openAction);
		}
	}
	
	@Override
	public void fillContextMenu(IMenuManager aMenu) {
		if (getContext() == null || getContext().getSelection().isEmpty()) {
			return;
		}
		
		if (this.openAction.isEnabled()) {
			aMenu.insertAfter(ICommonMenuConstants.GROUP_OPEN, this.openAction);
		}
	}
}

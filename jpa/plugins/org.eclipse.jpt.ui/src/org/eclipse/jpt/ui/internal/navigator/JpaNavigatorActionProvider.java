/*******************************************************************************
 *  Copyright (c) 2008  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.navigator;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jpt.ui.internal.actions.OpenJpaResourceAction;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.actions.ActionContext;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionConstants;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;
import org.eclipse.ui.navigator.ICommonMenuConstants;

public class JpaNavigatorActionProvider extends CommonActionProvider
{
	private OpenJpaResourceAction openAction;
	
	public JpaNavigatorActionProvider() {
		super();
	}
	
	public void init(ICommonActionExtensionSite aConfig) {
		openAction = new OpenJpaResourceAction();
	}
	
	public void setContext(ActionContext aContext) {
		if (aContext != null && aContext.getSelection() instanceof IStructuredSelection) {
			IStructuredSelection selection = (IStructuredSelection) aContext.getSelection();
			openAction.selectionChanged(selection);
		}
		
		super.setContext(aContext);
	}
	
	public void fillActionBars(IActionBars theActionBars) {
		if (openAction.isEnabled()) {
			theActionBars.setGlobalActionHandler(ICommonActionConstants.OPEN, openAction);
		}
	}
	
	public void fillContextMenu(IMenuManager aMenu) {
		if (getContext() == null || getContext().getSelection().isEmpty()) {
			return;
		}
		
		if (openAction.isEnabled()) {
			aMenu.insertAfter(ICommonMenuConstants.GROUP_OPEN, openAction);
		}
	}
}

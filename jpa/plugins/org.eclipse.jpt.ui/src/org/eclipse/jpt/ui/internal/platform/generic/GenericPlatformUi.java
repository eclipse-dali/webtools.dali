/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.platform.generic;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.ui.JpaUiFactory;
import org.eclipse.jpt.ui.internal.GenericJpaUiFactory;
import org.eclipse.jpt.ui.internal.JptUiMessages;
import org.eclipse.jpt.ui.internal.platform.base.BaseJpaPlatformUi;
import org.eclipse.jpt.ui.navigator.JpaNavigatorProvider;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class GenericPlatformUi extends BaseJpaPlatformUi
{
	public GenericPlatformUi() {
		super();
	}
	
	
	// **************** navigator content **************************************
	
	public JpaNavigatorProvider buildNavigatorProvider() {
		return new GenericNavigatorProvider();
	}
	
	
	@Override
	protected JpaUiFactory createJpaUiFactory() {
		return new GenericJpaUiFactory();
	}

	public void generateDDL(JpaProject project, IStructuredSelection selection) {
		this.displayNotSupportedMessage(JptUiMessages.GenericPlatformUiDialog_notSupportedMessageTitle, JptUiMessages.GenericPlatformUiDialog_notSupportedMessageText);
	}

	protected void displayNotSupportedMessage(String title, String message) {
	    Shell currentShell = Display.getCurrent().getActiveShell();
	    MessageDialog.openInformation(currentShell, title, message);
	}
}

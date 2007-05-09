/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.generic;

import java.text.MessageFormat;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jpt.core.internal.IJpaProject;
import org.eclipse.jpt.ui.internal.BaseJpaPlatformUi;

public class GenericPlatformUi extends BaseJpaPlatformUi
{
	public GenericPlatformUi() {
		super();
	}
	
	public void generateDLL(IJpaProject project, IStructuredSelection selection) {
		this.displayWarning("Warning", "DLL Generation not supported");
	}

	protected void displayWarning(String title, String message) {
	    String formattedMessage = MessageFormat.format( message, (Object [])(new String [] { message}));
	    MessageDialog.openWarning(this.getCurrentShell(), title, formattedMessage);	  
	}
	
}

/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.platform.generic;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.ui.JpaPlatformUiProvider;
import org.eclipse.jpt.jpa.ui.internal.JptUiMessages;
import org.eclipse.jpt.jpa.ui.internal.platform.base.BaseJpaPlatformUi;
import org.eclipse.jpt.jpa.ui.navigator.JpaNavigatorProvider;

public class GenericJpaPlatformUi
	extends BaseJpaPlatformUi
{
	public GenericJpaPlatformUi(
			JpaNavigatorProvider navigatorProvider, JpaPlatformUiProvider platformUiProvider) {
		
		super(navigatorProvider, platformUiProvider);
	}
	
	
	// ********** DDL generation **********
	
	public void generateDDL(JpaProject project, IStructuredSelection selection) {
		this.displayMessage(JptUiMessages.GenericPlatformUiDialog_notSupportedMessageTitle, JptUiMessages.GenericPlatformUiDialog_notSupportedMessageText);
	}
}

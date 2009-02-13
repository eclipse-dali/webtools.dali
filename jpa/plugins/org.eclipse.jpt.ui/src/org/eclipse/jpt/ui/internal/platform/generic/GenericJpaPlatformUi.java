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

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.ui.JpaPlatformUiProvider;
import org.eclipse.jpt.ui.JpaUiFactory;
import org.eclipse.jpt.ui.internal.GenericJpaUiFactory;
import org.eclipse.jpt.ui.internal.JptUiMessages;
import org.eclipse.jpt.ui.internal.platform.base.BaseJpaPlatformUi;
import org.eclipse.jpt.ui.navigator.JpaNavigatorProvider;

public class GenericJpaPlatformUi
	extends BaseJpaPlatformUi
{

	public GenericJpaPlatformUi(JpaPlatformUiProvider... platformUiProviders) {
		super();
	}


	// ********** factory **********

	@Override
	protected JpaUiFactory buildJpaUiFactory() {
		return new GenericJpaUiFactory();
	}


	// ********** navigator provider **********

	public JpaNavigatorProvider buildNavigatorProvider() {
		return new GenericNavigatorProvider();
	}


	// ********** DDL generation **********

	public void generateDDL(JpaProject project, IStructuredSelection selection) {
		this.displayMessage(JptUiMessages.GenericPlatformUiDialog_notSupportedMessageTitle, JptUiMessages.GenericPlatformUiDialog_notSupportedMessageText);
	}

}

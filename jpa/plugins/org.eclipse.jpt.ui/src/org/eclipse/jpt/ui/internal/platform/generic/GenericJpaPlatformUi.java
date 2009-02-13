/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
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
import org.eclipse.jpt.ui.internal.JptUiMessages;
import org.eclipse.jpt.ui.internal.platform.base.BaseJpaPlatformUi;
import org.eclipse.jpt.ui.navigator.JpaNavigatorProvider;
import org.eclipse.jpt.ui.structure.JpaStructureProvider;

public class GenericJpaPlatformUi
	extends BaseJpaPlatformUi
{

	public GenericJpaPlatformUi(
		JpaUiFactory jpaUiFactory,
		JpaNavigatorProvider navigatorProvider, 
		JpaStructureProvider persistenceStructureProvider, 
		JpaStructureProvider javaStructureProvider,
		JpaPlatformUiProvider... platformUiProviders) 
	{
		super(jpaUiFactory, navigatorProvider, persistenceStructureProvider, javaStructureProvider, platformUiProviders);
	}

	// ********** DDL generation **********

	public void generateDDL(JpaProject project, IStructuredSelection selection) {
		this.displayMessage(JptUiMessages.GenericPlatformUiDialog_notSupportedMessageTitle, JptUiMessages.GenericPlatformUiDialog_notSupportedMessageText);
	}

}

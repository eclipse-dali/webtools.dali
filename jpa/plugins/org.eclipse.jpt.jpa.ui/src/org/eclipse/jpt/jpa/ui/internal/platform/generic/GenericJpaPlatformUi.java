/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.platform.generic;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jpt.common.ui.jface.ItemTreeStateProviderFactoryProvider;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.ui.JpaPlatformUiProvider;
import org.eclipse.jpt.jpa.ui.JptJpaUiMessages;
import org.eclipse.jpt.jpa.ui.internal.platform.base.AbstractJpaPlatformUi;
import org.eclipse.jpt.jpa.ui.internal.wizards.conversion.java.GenericJavaGeneratorConversionWizardPage;
import org.eclipse.jpt.jpa.ui.internal.wizards.conversion.java.GenericJavaQueryConversionWizardPage;

public class GenericJpaPlatformUi
	extends AbstractJpaPlatformUi
{
	public GenericJpaPlatformUi(
			ItemTreeStateProviderFactoryProvider navigatorFactoryProvider,
			JpaPlatformUiProvider platformUiProvider
	) {
		super(navigatorFactoryProvider, platformUiProvider);
	}

	// ********** DDL generation **********

	public void generateDDL(JpaProject project, IStructuredSelection selection) {
		this.displayMessage(JptJpaUiMessages.GENERIC_PLATFORM_UI_DIALOG_NOT_SUPPORTED_MESSAGE_TITLE, JptJpaUiMessages.GENERIC_PLATFORM_UI_DIALOG_NOT_SUPPORTED_MESSAGE_TEXT);
	}
	
	// ********** metadata conversion **********
	
	public void convertJavaQueryMetadataToGlobal(JpaProject jpaProject) {
		this.openInDialog(new GenericJavaQueryConversionWizardPage(jpaProject));
	}
	
	public void convertJavaGeneratorMetadataToGlobal(JpaProject jpaProject) {
		this.openInDialog(new GenericJavaGeneratorConversionWizardPage(jpaProject));
	}
}

/*******************************************************************************
 * Copyright (c) 2011, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.wizards.conversion.java;

import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.jpt.jpa.ui.internal.JptUiMessages;
import org.eclipse.jpt.jpa.ui.internal.wizards.conversion.java.GenericJavaGeneratorConversionWizardPage;

public class EclipseLinkJavaGeneratorConversionWizardPage
	extends EclipseLinkJavaMetadataConversionWizardPage
{
	public EclipseLinkJavaGeneratorConversionWizardPage(JpaProject jpaProject) {
		super(jpaProject, JptUiMessages.JavaGeneratorConversionWizardPage_title, JptUiMessages.JavaGeneratorConversionWizardPage_description);
	}

	@Override
	protected boolean hasConvertibleJavaMetadata_() {
		return this.persistenceUnit.hasConvertibleJavaGenerators();
	}

	@Override
	protected String getMissingJavaMetadataWarningMessage() {
		return JptUiMessages.JavaMetadataConversion_noGeneratorsToConvert;
	}

	@Override
	protected ConversionCommand.Strategy getConversionCommandStrategy() {
		return GenericJavaGeneratorConversionWizardPage.CONVERSION_COMMAND_STRATEGY;
	}

	@Override
	protected boolean hasAnyEquivalentJavaMetadata_() {
		return this.getPersistenceUnit().hasAnyEquivalentJavaGenerators();
	}

	@Override
	public String getEquivalentJavaMetadataWarningDialogTitle() {
		return EclipseLinkUiMessages.JavaMetadataConversion_equivalentGeneratorsWarningTitle;
	}

	@Override
	public String getEquivalentJavaMetadataWarningDialogMessage() {
		return EclipseLinkUiMessages.JavaMetadataConversion_equivalentGeneratorsWarningMessage;
	}
}

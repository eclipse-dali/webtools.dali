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
import org.eclipse.jpt.jpa.eclipselink.ui.JptJpaEclipseLinkUiMessages;
import org.eclipse.jpt.jpa.ui.JptJpaUiMessages;
import org.eclipse.jpt.jpa.ui.internal.wizards.conversion.java.GenericJavaGeneratorConversionWizardPage;

public class EclipseLinkJavaGeneratorConversionWizardPage
	extends EclipseLinkJavaMetadataConversionWizardPage
{
	public EclipseLinkJavaGeneratorConversionWizardPage(JpaProject jpaProject) {
		super(jpaProject, JptJpaUiMessages.JAVA_GENERATOR_CONVERSION_WIZARD_PAGE_TITLE, JptJpaUiMessages.JAVA_GENERATOR_CONVERSION_WIZARD_PAGE_DESCRIPTION);
	}

	@Override
	protected boolean hasConvertibleJavaMetadata_() {
		return this.persistenceUnit.hasConvertibleJavaGenerators();
	}

	@Override
	protected String getMissingJavaMetadataWarningMessage() {
		return JptJpaUiMessages.JAVA_METADATA_CONVERSION_NO_GENERATORS_TO_CONVERT;
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
		return JptJpaEclipseLinkUiMessages.JAVA_METADATA_CONVERSION_EQUIVALENT_GENERATORS_WARNING_TITLE;
	}

	@Override
	public String getEquivalentJavaMetadataWarningDialogMessage() {
		return JptJpaEclipseLinkUiMessages.JAVA_METADATA_CONVERSION_EQUIVALENT_GENERATORS_WARNING_MESSAGE;
	}
}

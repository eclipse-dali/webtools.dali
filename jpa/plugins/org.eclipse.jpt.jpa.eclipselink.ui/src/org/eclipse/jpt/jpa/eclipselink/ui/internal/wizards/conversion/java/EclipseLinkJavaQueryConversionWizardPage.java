/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.wizards.conversion.java;

import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.eclipselink.ui.JptJpaEclipseLinkUiMessages;
import org.eclipse.jpt.jpa.ui.JptJpaUiMessages;
import org.eclipse.jpt.jpa.ui.internal.wizards.conversion.java.GenericJavaQueryConversionWizardPage;

public class EclipseLinkJavaQueryConversionWizardPage
	extends EclipseLinkJavaMetadataConversionWizardPage
{
	public EclipseLinkJavaQueryConversionWizardPage(JpaProject jpaProject) {
		super(jpaProject, JptJpaUiMessages.JAVA_QUERY_CONVERSION_WIZARD_PAGE_TITLE, JptJpaUiMessages.JAVA_QUERY_CONVERSION_WIZARD_PAGE_DESCRIPTION);
	}

	@Override
	protected boolean hasConvertibleJavaMetadata_() {
		return this.persistenceUnit.hasConvertibleJavaQueries();
	}

	@Override
	protected String getMissingJavaMetadataWarningMessage() {
		return JptJpaUiMessages.JAVA_METADATA_CONVERSION_NO_QUERIES_TO_CONVERT;
	}

	@Override
	protected ConversionCommand.Strategy getConversionCommandStrategy() {
		return GenericJavaQueryConversionWizardPage.CONVERSION_COMMAND_STRATEGY;
	}

	@Override
	protected boolean hasAnyEquivalentJavaMetadata_() {
		return this.getPersistenceUnit().hasAnyEquivalentJavaQueries();
	}

	@Override
	public String getEquivalentJavaMetadataWarningDialogTitle() {
		return JptJpaEclipseLinkUiMessages.JAVA_METADATA_CONVERSION_EQUIVALENT_QUERIES_WARNING_TITLE;
	}

	@Override
	public String getEquivalentJavaMetadataWarningDialogMessage() {
		return JptJpaEclipseLinkUiMessages.JAVA_METADATA_CONVERSION_EQUIVALENT_QUERIES_WARNING_MESSAGE;
	}
}

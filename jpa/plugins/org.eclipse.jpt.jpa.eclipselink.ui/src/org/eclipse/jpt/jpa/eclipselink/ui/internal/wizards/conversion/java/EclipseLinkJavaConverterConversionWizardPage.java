/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.wizards.conversion.java;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.context.orm.EntityMappings;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkEntityMappings;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkPersistenceUnit;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.jpa.eclipselink.ui.JptJpaEclipseLinkUiMessages;

public class EclipseLinkJavaConverterConversionWizardPage
	extends EclipseLinkJavaMetadataConversionWizardPage
{
	public EclipseLinkJavaConverterConversionWizardPage(JpaProject jpaProject) {
		super(jpaProject, JptJpaEclipseLinkUiMessages.JAVA_METADATA_CONVERSION_EQUIVALENT_CONVERTERS_WARNING_TITLE, JptJpaEclipseLinkUiMessages.JAVA_CONVERTER_CONVERSION_WIZARD_PAGE_DESCRIPTION);
	}

	@Override
	protected boolean hasConvertibleJavaMetadata_() {
		return this.getPersistenceUnit().hasConvertibleJavaConverters();
	}

	@Override
	protected String getMissingJavaMetadataWarningMessage() {
		return JptJpaEclipseLinkUiMessages.JAVA_METADATA_CONVERSION_noConvertersToConvert;
	}

	/**
	 * Mapping file must be an EclipseLink ORM file.
	 */
	@Override
	protected IContentType getMappingFileContentType() {
		return XmlEntityMappings.CONTENT_TYPE;
	}

	@Override
	protected ConversionCommand.Strategy getConversionCommandStrategy() {
		return CONVERSION_COMMAND_STRATEGY;
	}

	public static final ConversionCommandStrategy CONVERSION_COMMAND_STRATEGY = new ConversionCommandStrategy();

	protected static class ConversionCommandStrategy
		implements ConversionCommand.Strategy
	{
		public void execute(EntityMappings entityMappings, IProgressMonitor monitor) {
			((EclipseLinkPersistenceUnit) entityMappings.getPersistenceUnit()).convertJavaConverters((EclipseLinkEntityMappings) entityMappings, monitor);
		}
	}

	@Override
	protected boolean hasAnyEquivalentJavaMetadata_() {
		return this.getPersistenceUnit().hasAnyEquivalentJavaConverters();
	}

	@Override
	public String getEquivalentJavaMetadataWarningDialogTitle() {
		return JptJpaEclipseLinkUiMessages.JAVA_METADATA_CONVERSION_EQUIVALENT_CONVERTERS_WARNING_TITLE;
	}

	@Override
	public String getEquivalentJavaMetadataWarningDialogMessage() {
		return JptJpaEclipseLinkUiMessages.JAVA_METADATA_CONVERSION_EQUIVALENT_CONVERTERS_WARNING_MESSAGE;
	}
}

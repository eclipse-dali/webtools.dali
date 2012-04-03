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

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.context.orm.EntityMappings;
import org.eclipse.jpt.jpa.eclipselink.core.JptJpaEclipseLinkCorePlugin;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkEntityMappings;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkPersistenceUnit;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.EclipseLinkUiMessages;

public class EclipseLinkJavaConverterConversionWizardPage
	extends EclipseLinkJavaMetadataConversionWizardPage
{
	public EclipseLinkJavaConverterConversionWizardPage(JpaProject jpaProject) {
		super(jpaProject, EclipseLinkUiMessages.JavaConverterConversionWizardPage_title, EclipseLinkUiMessages.JavaConverterConversionWizardPage_description);
	}

	@Override
	protected boolean hasConvertibleJavaMetadata_() {
		return this.getPersistenceUnit().hasConvertibleJavaConverters();
	}

	@Override
	protected String getMissingJavaMetadataWarningMessage() {
		return EclipseLinkUiMessages.JavaMetadataConversion_noConvertersToConvert;
	}

	/**
	 * Mapping file must be an EclipseLink ORM file.
	 */
	@Override
	protected IContentType getMappingFileContentType() {
		return JptJpaEclipseLinkCorePlugin.ECLIPSELINK_ORM_XML_CONTENT_TYPE;
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
		return EclipseLinkUiMessages.JavaMetadataConversion_equivalentConvertersWarningTitle;
	}

	@Override
	public String getEquivalentJavaMetadataWarningDialogMessage() {
		return EclipseLinkUiMessages.JavaMetadataConversion_equivalentConvertersWarningMessage;
	}
}

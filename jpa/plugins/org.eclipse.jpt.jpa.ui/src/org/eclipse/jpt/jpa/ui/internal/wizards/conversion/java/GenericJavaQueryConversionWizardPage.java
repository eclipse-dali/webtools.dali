/*******************************************************************************
 * Copyright (c) 2011, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.wizards.conversion.java;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.context.orm.EntityMappings;
import org.eclipse.jpt.jpa.ui.internal.JptUiMessages;

public class GenericJavaQueryConversionWizardPage
	extends GenericJavaMetadataConversionWizardPage
{
	public GenericJavaQueryConversionWizardPage(JpaProject jpaProject) {
		super(jpaProject, JptUiMessages.JavaQueryConversionWizardPage_title, JptUiMessages.JavaQueryConversionWizardPage_description);
	}

	@Override
	protected boolean hasConvertibleJavaMetadata_() {
		return this.persistenceUnit.hasConvertibleJavaQueries();
	}

	@Override
	protected String getMissingJavaMetadataWarningMessage() {
		return JptUiMessages.JavaMetadataConversion_noQueriesToConvert;
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
			entityMappings.getPersistenceUnit().convertJavaQueries(entityMappings, monitor);
		}
	}
}

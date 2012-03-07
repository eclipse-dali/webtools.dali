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
import org.eclipse.jpt.common.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.jpa.core.context.orm.EntityMappings;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.ui.internal.JptUiMessages;

public class GenericJpaJavaQueryMetadataConversionWizardPage extends 
	GenericJpaJavaGlobalMetadataConversionWizardPage {

	public GenericJpaJavaQueryMetadataConversionWizardPage(
			PersistenceUnit persistenceUnit, SimplePropertyValueModel<String> model, String helpContextId) {
		super(persistenceUnit, model, helpContextId);
	}


	@Override
	protected String getWizardPageTitle() {
		return JptUiMessages.JpaJavaQueryMetadataConversionWizardPage_title;
	}

	@Override
	protected String getWizardPageDescription() {
		return JptUiMessages.JpaJavaQueryMetadataConversionWizardPage_description;
	}

	@Override
	protected void executeConversion(EntityMappings entityMappings, IProgressMonitor monitor) {
		persistenceUnit.convertJavaQueries(entityMappings, monitor);
	}

	@Override
	protected boolean hasConvertibleJavaGlobalMetadata() {
		return persistenceUnit.hasConvertibleJavaQueries();
	}

	@Override
	protected String getWarningMessageForNonExistentGlobals() {
		return 	JptUiMessages.JpaGlobalMetadataConversion_noQueryMetadataToConvert;
	}
}

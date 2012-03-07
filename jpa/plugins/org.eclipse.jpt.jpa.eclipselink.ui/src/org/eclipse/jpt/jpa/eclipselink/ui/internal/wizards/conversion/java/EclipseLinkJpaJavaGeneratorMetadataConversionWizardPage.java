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
import org.eclipse.jpt.common.ui.internal.dialogs.OptionalMessageDialog;
import org.eclipse.jpt.common.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.jpa.core.context.orm.EntityMappings;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkPersistenceUnit;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.jpt.jpa.ui.internal.JptUiMessages;

public class EclipseLinkJpaJavaGeneratorMetadataConversionWizardPage extends
	EclipseLinkJpaJavaGlobalMetadataConversionWizardPage {

	public EclipseLinkJpaJavaGeneratorMetadataConversionWizardPage(
			EclipseLinkPersistenceUnit persistenceUnit, SimplePropertyValueModel<String> model, String helpContextId) {
		super(persistenceUnit, model, helpContextId);
	}

	@Override
	protected String getWizardPageTitle() {
		return JptUiMessages.JpaJavaGeneratorMetadataConversionWizardPage_title;
	}

	@Override
	protected String getWizardPageDescription() {
		return JptUiMessages.JpaJavaGeneratorMetadataConversionWizardPage_description;
	}

	@Override
	protected void executeConversion(EntityMappings entityMappings, IProgressMonitor monitor){
		persistenceUnit.convertJavaGenerators(entityMappings, monitor);
	}

	@Override
	protected boolean isOKToConvert() {
		if ( ! OptionalMessageDialog.isDialogEnabled(EquivalentGlobalMetadataWarningDialog.ID)) {
			return true;
		}
		if (this.persistenceUnit.hasAnyEquivalentJavaGenerators()) {
			return this.openEquivalentGlobalMetadataWarningDialog();
		}
		return this.hasConvertibleJavaGlobalMetadata();
	}

	@Override
	protected String getWarningMessageForNonExistentGlobals() {
		return JptUiMessages.JpaGlobalMetadataConversion_noGeneratorMetadataToConvert;
	}

	@Override
	protected boolean hasConvertibleJavaGlobalMetadata() {
		return persistenceUnit.hasConvertibleJavaGenerators();
	}

	@Override
	public String getWarningDialogTitle() {
		return EclipseLinkUiMessages.JpaGlobalMetadataConversion_equivalentGeneratorMetadataWarningTitle;
	}

	@Override
	public String getWarningDialogMessage() {
		return EclipseLinkUiMessages.JpaGlobalMetadataConversion_equivalentGeneratorMetadataWarningMessage;
	}
}

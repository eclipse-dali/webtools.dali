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

public class EclipseLinkJpaJavaQueryMetadataConversionWizardPage extends
	EclipseLinkJpaJavaGlobalMetadataConversionWizardPage {

	public EclipseLinkJpaJavaQueryMetadataConversionWizardPage(
			EclipseLinkPersistenceUnit persistenceUnit, SimplePropertyValueModel<String> model, String helpContextId) {
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
	protected void executeConversion(EntityMappings entityMappings, IProgressMonitor monitor){
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

	@Override
	public String getWarningDialogTitle() {
		return EclipseLinkUiMessages.JpaGlobalMetadataConversion_equivalentQueryMetadataWarningTitle;
	}

	@Override
	public String getWarningDialogMessage() {
		return EclipseLinkUiMessages.JpaGlobalMetadataConversion_equivalentQueryMetadataWarningMessage;
	}

	@Override
	protected boolean isOKToConvert() {
		if ( ! OptionalMessageDialog.isDialogEnabled(EquivalentGlobalMetadataWarningDialog.ID)) {
			return true;
		}
		if (this.persistenceUnit.hasAnyEquivalentJavaQueries()) {
			return this.openEquivalentGlobalMetadataWarningDialog();
		}
		return this.hasConvertibleJavaGlobalMetadata();
	}
}

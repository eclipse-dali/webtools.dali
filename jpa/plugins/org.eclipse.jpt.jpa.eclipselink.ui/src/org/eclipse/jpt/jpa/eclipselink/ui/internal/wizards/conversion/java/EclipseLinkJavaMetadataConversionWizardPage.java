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

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jpt.common.ui.internal.dialogs.OptionalMessageDialog;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkPersistenceUnit;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.wizards.EclipseLinkEmbeddedMappingFileWizard;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.wizards.EclipseLinkSelectMappingFileDialog;
import org.eclipse.jpt.jpa.ui.internal.jface.XmlMappingFileViewerFilter;
import org.eclipse.jpt.jpa.ui.internal.wizards.SelectMappingFileDialog;
import org.eclipse.jpt.jpa.ui.internal.wizards.conversion.java.JavaMetadataConversionWizardPage;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

public abstract class EclipseLinkJavaMetadataConversionWizardPage
	extends JavaMetadataConversionWizardPage
{
	public EclipseLinkJavaMetadataConversionWizardPage(JpaProject jpaProject, String title, String description) {
		super(jpaProject, title, description);
	}

	@Override
	protected ViewerFilter buildSelectMappingFileDialogViewerFilter() {
		return new XmlMappingFileViewerFilter(this.jpaProject, XmlEntityMappings.CONTENT_TYPE);
	}

	@Override
	protected IPath openNewMappingFileWizard() {
		return EclipseLinkEmbeddedMappingFileWizard.createNewMappingFile(new StructuredSelection(this.jpaProject.getProject()));
	}

	@Override
	protected SelectMappingFileDialog buildSelectMappingFileDialog() {
		return new EclipseLinkSelectMappingFileDialog(this.getShell(), this.jpaProject.getProject());
	}

	protected EclipseLinkPersistenceUnit getPersistenceUnit() {
		return (EclipseLinkPersistenceUnit) this.persistenceUnit;
	}
	
	@Override
	protected IPath getDefaultMappingFileRuntimPath() {
		return XmlEntityMappings.DEFAULT_RUNTIME_PATH;
	}

	@Override
	protected IContentType getMappingFileContentType() {
		return XmlEntityMappings.CONTENT_TYPE;
	}


	// ********** finish **********

	@Override
	protected final boolean isOKToConvert() {
		if (this.hasAnyEquivalentJavaMetadata()) {
			return this.userWantsToContinueWithEquivalentJavaMetadata();
		}
		return super.isOKToConvert();
	}

	protected final boolean hasAnyEquivalentJavaMetadata() {
		return (this.persistenceUnit != null) && this.hasAnyEquivalentJavaMetadata_();
	}

	/**
	 * Pre-condition: the {@link #persistenceUnit persistence unit}
	 * is not <code>null</code>.
	 */
	protected abstract boolean hasAnyEquivalentJavaMetadata_();


	// ********** equivalent Java metadata warning dialog **********

	/**
	 * Return whether the user wants to continue the conversion even though
	 * the persistence unit contains "equivalent" Java metadata (e.g. one or
	 * more of the Java queries are "equivalent").
	 */
	protected boolean userWantsToContinueWithEquivalentJavaMetadata() {
		if ( ! OptionalMessageDialog.isDialogEnabled(EquivalentJavaMetadataWarningDialog.ID)) {
			return true;  // ?
		}
		MessageDialog dialog = this.buildEquivalentJavaMetadataWarningDialog();
		return dialog.open() == IDialogConstants.YES_ID;
	}

	protected MessageDialog buildEquivalentJavaMetadataWarningDialog() {
		return new EquivalentJavaMetadataWarningDialog(this.getShell(), this.getEquivalentJavaMetadataWarningDialogTitle(), this.getEquivalentJavaMetadataWarningDialogMessage());
	}

	protected abstract String getEquivalentJavaMetadataWarningDialogTitle();

	protected abstract String getEquivalentJavaMetadataWarningDialogMessage();


	/* CU private */ static class EquivalentJavaMetadataWarningDialog
		extends OptionalMessageDialog
	{
		static final String ID = "dontShowEquivalentMetadataExisting.warning"; //$NON-NLS-1$

		EquivalentJavaMetadataWarningDialog(Shell parent, String title, String message) {
			super(ID, parent, title, message, MessageDialog.WARNING,
					new String[] {IDialogConstants.YES_LABEL, IDialogConstants.NO_LABEL},
					1);
		}

		@Override
		protected void createButtonsForButtonBar(Composite parent) {
			this.createButton(parent, IDialogConstants.YES_ID, IDialogConstants.YES_LABEL, false);
			this.createButton(parent, IDialogConstants.NO_ID, IDialogConstants.NO_LABEL, true);
		}
	}
}

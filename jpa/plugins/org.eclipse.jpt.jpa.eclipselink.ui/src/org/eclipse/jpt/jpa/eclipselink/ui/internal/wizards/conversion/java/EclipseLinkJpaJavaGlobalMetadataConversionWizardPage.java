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

import java.lang.reflect.InvocationTargetException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jpt.common.ui.internal.dialogs.OptionalMessageDialog;
import org.eclipse.jpt.common.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.jpa.eclipselink.core.JptJpaEclipseLinkCorePlugin;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkPersistenceUnit;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.wizards.EmbeddedEclipseLinkMappingFileWizard;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.wizards.SelectEcliplseLinkMappingFileDialog;
import org.eclipse.jpt.jpa.ui.internal.jface.XmlMappingFileViewerFilter;
import org.eclipse.jpt.jpa.ui.internal.wizards.SelectJpaOrmMappingFileDialog;
import org.eclipse.jpt.jpa.ui.internal.wizards.conversion.java.JpaJavaGlobalMetadataConversionWizardPage;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

public abstract class EclipseLinkJpaJavaGlobalMetadataConversionWizardPage extends
	JpaJavaGlobalMetadataConversionWizardPage<EclipseLinkPersistenceUnit> {

	public EclipseLinkJpaJavaGlobalMetadataConversionWizardPage(
			EclipseLinkPersistenceUnit persistenceUnit, SimplePropertyValueModel<String> model, String helpContextId) {
		super(persistenceUnit, model, helpContextId);
	}

	@Override
	protected ViewerFilter buildSelectMappingFileDialogViewerFilter() {
		return new XmlMappingFileViewerFilter(this.getJpaProject(), JptJpaEclipseLinkCorePlugin.ECLIPSELINK_ORM_XML_CONTENT_TYPE);
	}

	@Override
	protected SelectJpaOrmMappingFileDialog buildSelectMappingFileDialog(
			Shell shell, IProject project, ILabelProvider lp,
			ITreeContentProvider cp) {
		return new SelectEcliplseLinkMappingFileDialog(shell, project, lp, cp);
	}

	@Override
	protected IPath getMappingFilePath() {
		return EmbeddedEclipseLinkMappingFileWizard.createNewMappingFile(
				new StructuredSelection(this.getJpaProject().getProject()), 
				getOrmXmlResourceName());
	}

	@Override
	protected String getDefaultMappingFileRuntimPath() {
		return JptJpaEclipseLinkCorePlugin.DEFAULT_ECLIPSELINK_ORM_XML_RUNTIME_PATH.toString();
	}

	@Override
	public void performFinish() throws InvocationTargetException {
		try {
			if( !this.isOKToConvert()) {
				return;
			}

			// true=fork; true=cancellable
			this.buildPerformFinishProgressMonitorDialog().run(true, true, this.buildPerformFinishRunableWithProgress());
		} 
		catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}

	// ********** abstract methods **********

	abstract public String getWarningDialogTitle();

	abstract public String getWarningDialogMessage();

	abstract protected boolean isOKToConvert();

	// ********** warning dialog **********

	static class EquivalentGlobalMetadataWarningDialog extends OptionalMessageDialog {

		static final String ID= "dontShowEquivalentMetadataExisting.warning"; //$NON-NLS-1$

		EquivalentGlobalMetadataWarningDialog(Shell parent, String title, String message) {
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


	protected boolean openEquivalentGlobalMetadataWarningDialog() {
		EquivalentGlobalMetadataWarningDialog dialog = 
				new EquivalentGlobalMetadataWarningDialog(this.getShell(), this.getWarningDialogTitle(), this.getWarningDialogMessage());
		return dialog.open() == IDialogConstants.YES_ID;
	}
}

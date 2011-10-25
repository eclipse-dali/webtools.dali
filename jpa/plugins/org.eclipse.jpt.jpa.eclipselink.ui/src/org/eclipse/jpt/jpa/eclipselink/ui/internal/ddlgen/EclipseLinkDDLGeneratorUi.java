/*******************************************************************************
* Copyright (c) 2007, 2011 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.ddlgen;

import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jpt.common.ui.internal.dialogs.OptionalMessageDialog;
import org.eclipse.jpt.common.ui.internal.util.SWTUtil;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.jpa.core.JpaPlatform;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.context.persistence.Persistence;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.OutputMode;
import org.eclipse.jpt.jpa.eclipselink.core.internal.ddlgen.EclipseLinkDDLGenerator;
import org.eclipse.jpt.jpa.eclipselink.ui.JptJpaEclipseLinkUiPlugin;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.ddlgen.wizards.GenerateDDLWizard;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 *  EclipseLinkDLLGeneratorUi is used by the EclipseLinkPlatformUi to initiate 
 *  the execution of EclipseLink DDL generator.
 */
public class EclipseLinkDDLGeneratorUi
{
	private final JpaProject project;
	private static final String CR = StringTools.CR;

	// ********** constructors **********
	
	public static void generate(JpaProject project) {
		new EclipseLinkDDLGeneratorUi(project).generate();
	}

	protected EclipseLinkDDLGeneratorUi(JpaProject project) {
		super();
		if (project == null) {
			throw new NullPointerException();
		}
		this.project = project;
	}

	// ********** behavior **********
	
	protected void generate() {

		PersistenceUnit persistenceUnit = this.getPersistenceUnits().iterator().next(); // Take the first persistenceUnit
		String puName = persistenceUnit.getName();

		GenerateDDLWizard wizard = new GenerateDDLWizard(this.project);
		WizardDialog wizardDialog = new WizardDialog(this.getCurrentShell(), wizard);
		wizardDialog.create();
		if(wizard.getPageCount() > 0) {
			int returnCode = wizardDialog.open();
			if (returnCode == Window.CANCEL) {
				return;
			}
		}
		OutputMode outputMode = wizard.getOutputMode();

		if((outputMode != OutputMode.sql_script) && ( ! this.displayGeneratingDDLWarning())) {
			return;
		}
		WorkspaceJob job = this.buildGenerateDDLJob(puName, this.project, outputMode);
		job.schedule();
	}
	
	protected WorkspaceJob buildGenerateDDLJob(String puName, JpaProject project, OutputMode outputMode) {
		return new GenerateDDLJob(puName, project, outputMode);
	}
	
	private Shell getCurrentShell() {
	    return Display.getCurrent().getActiveShell();
	}

	private boolean displayGeneratingDDLWarning() {
		if (!OptionalMessageDialog.isDialogEnabled(OverwriteConfirmerDialog.ID)) {
			return true;
		} else {
			OverwriteConfirmerDialog dialog = new OverwriteConfirmerDialog(this.getCurrentShell());
			return dialog.open() == IDialogConstants.YES_ID;
		}
	}

	// ********** Persistence Unit **********

	protected JpaPlatform getPlatform() {
		return this.project.getJpaPlatform();
	}
	
	protected ListIterable<PersistenceUnit> getPersistenceUnits() {
		return this.getPersistence().getPersistenceUnits();
	}

	protected Persistence getPersistence() {
		return this.project.getRootContextNode().getPersistenceXml().getPersistence();
	}

	// ********** runnable **********

	protected static class GenerateDDLJob extends WorkspaceJob {
		private final String puName;
		private final JpaProject project;
		private final OutputMode outputMode;

		public GenerateDDLJob(String puName, JpaProject project, OutputMode outputMode) {
			super(EclipseLinkUiMessages.ECLIPSELINK_GENERATE_TABLES_JOB);
			this.puName = puName;
			this.project = project;
			this.outputMode = outputMode;
		}

		@Override
		public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
			SubMonitor sm = SubMonitor.convert(monitor, EclipseLinkUiMessages.ECLIPSELINK_GENERATE_TABLES_TASK, 1);
			try {
				this.ddlGeneratorGenerate(this.puName, this.project, this.outputMode, sm.newChild(1));
			} 
			catch (OperationCanceledException e) {
				return Status.CANCEL_STATUS;
			}
			catch (RuntimeException re) {				
				this.logException(re);
			}
			return Status.OK_STATUS;
		}

		protected void ddlGeneratorGenerate(String puName, JpaProject project, OutputMode outputMode, IProgressMonitor monitor) {
			EclipseLinkDDLGenerator.generate(puName, project, outputMode, monitor);
		}

		protected void logException(RuntimeException re) {
			String msg = re.getMessage();
			String message = (msg == null) ? re.toString() : msg;
			this.displayError(message);
			JptJpaEclipseLinkUiPlugin.log(re);
		}
		
		private void displayError(final String message) {
			SWTUtil.syncExec(new Runnable() {
				public void run() {
					MessageDialog.openError(
						getShell(),
						EclipseLinkUiMessages.EclipseLinkDDLGeneratorUi_error,
						message
					);
				}
			});
		}

		protected Shell getShell() {
			Display display = Display.getCurrent();
			Shell shell = (display == null) ? null : display.getActiveShell();
			if (shell == null && display != null) {
				Shell[] shells = display.getShells();
				if (shells.length > 0)
					shell = shells[0];
			}
			return shell;
		}
	}

	// ********** overwrite dialog **********

	static class OverwriteConfirmerDialog extends OptionalMessageDialog {

		private static final String ID= "dontShowOverwriteExistingTablesFromClasses.warning"; //$NON-NLS-1$

		OverwriteConfirmerDialog(Shell parent) {
			super(ID, parent,
					EclipseLinkUiMessages.EclipseLinkDDLGeneratorUi_generatingDDLWarningTitle,
					NLS.bind(EclipseLinkUiMessages.EclipseLinkDDLGeneratorUi_generatingDDLWarningMessage, CR,  CR + CR),
					MessageDialog.WARNING,
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

/*******************************************************************************
* Copyright (c) 2007 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.ui.internal.generic;

import java.util.Collection;

import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jpt.core.internal.IJpaFile;
import org.eclipse.jpt.core.internal.IJpaProject;
import org.eclipse.jpt.core.internal.synch.SynchronizeClassesJob;
import org.eclipse.jpt.gen.internal.EntityGenerator;
import org.eclipse.jpt.gen.internal.PackageGenerator;
import org.eclipse.jpt.ui.internal.JptUiMessages;
import org.eclipse.jpt.ui.internal.wizards.GenerateEntitiesWizard;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

/**
 *  EntitiesGenerator
 */
public class EntitiesGenerator
{
	private IJpaProject project;
	private IStructuredSelection selection;

	// ********** constructors **********

	static public void generate( IJpaProject project, IStructuredSelection selection) {
		if(project == null) {
			throw new NullPointerException();
		}
		new EntitiesGenerator( project, selection).generate();
	}
	
	public EntitiesGenerator() {
		super();
	}

	private EntitiesGenerator( IJpaProject project, IStructuredSelection selection) {
		super();
		this.project = project;
		this.selection = selection;
	}

	// ********** behavior **********

	protected void generate() {
		GenerateEntitiesWizard wizard = new GenerateEntitiesWizard(this.project, this.selection);
		
		WizardDialog dialog = new WizardDialog(this.getCurrentShell(), wizard);
		dialog.create();
		int returnCode = dialog.open();
		if (returnCode == Window.OK) {
			IWorkspaceRunnable runnable = new GenerateEntitiesRunnable(
					wizard.getPackageGeneratorConfig(),
					wizard.getEntityGeneratorConfig(),
					wizard.getSelectedTables(),
					wizard.synchronizePersistenceXml(),
					project,
					new OverwriteConfirmer(this.getCurrentShell())
			);
			try {
				// TODO pass in real ProgressMonitor
				ResourcesPlugin.getWorkspace().run(runnable, new NullProgressMonitor());
			} catch (CoreException ex) {
				throw new RuntimeException(ex);
			}
		}
	}
	
	private Shell getCurrentShell() {
	    return Display.getCurrent().getActiveShell();
	}
	  
	// ********** runnable **********

	static class GenerateEntitiesRunnable implements IWorkspaceRunnable {
		private final PackageGenerator.Config packageConfig;
		private final EntityGenerator.Config entityConfig;
		private final Collection selectedTables;
		private final boolean synchronizePersistenceXml;
		private final EntityGenerator.OverwriteConfirmer overwriteConfirmer;
		private final IJpaProject project;
		
		GenerateEntitiesRunnable(
				PackageGenerator.Config packageConfig,
				EntityGenerator.Config entityConfig,
				Collection selectedTables,
				boolean synchronizePersistenceXml,
				IJpaProject project,
				EntityGenerator.OverwriteConfirmer overwriteConfirmer
		) {
			super();
			this.packageConfig = packageConfig;
			this.entityConfig = entityConfig;
			this.selectedTables = selectedTables;
			this.synchronizePersistenceXml = synchronizePersistenceXml;
			this.overwriteConfirmer = overwriteConfirmer;
			this.project = project;
		}

		public void run(IProgressMonitor monitor) throws CoreException {
			monitor.beginTask("", 1000);
			try {
				PackageGenerator.generateEntities(this.packageConfig, this.entityConfig, this.selectedTables, this.overwriteConfirmer, monitor);
				if (synchronizePersistenceXml) {
					// we currently only support *one* persistence.xml file per project
					IJpaFile resource = project.getPlatform().validPersistenceXmlFiles().next();
					if (resource != null) {
						SynchronizeClassesJob job = new SynchronizeClassesJob(resource.getFile());
						job.schedule();
					}
				}
			} catch (OperationCanceledException ex) {
				// fall through and tell monitor we are done
			}
			monitor.done();
		}

	}

	// ********** overwrite confirmer **********

	static class OverwriteConfirmer implements EntityGenerator.OverwriteConfirmer {
		private Shell shell;
		private boolean overwriteAll = false;
		private boolean skipAll = false;

		OverwriteConfirmer(Shell shell) {
			super();
			this.shell = shell;
		}

		public boolean overwrite(String className) {
			if (this.overwriteAll) {
				return true;
			}
			if (this.skipAll) {
				return false;
			}
			return this.promptUser(className);
		}

		private boolean promptUser(String className) {
			OverwriteConfirmerDialog dialog = new OverwriteConfirmerDialog(this.shell, className);
			if (dialog.open() == Window.CANCEL) {
				throw new OperationCanceledException();
			}
			if (dialog.yes()) {
				return true;
			} else if (dialog.yesToAll()) {
				this.overwriteAll = true;
				return true;
			} else if (dialog.no()) {
				return false;
			} else if (dialog.noToAll()) {
				this.skipAll = true;
				return false;
			}
			throw new IllegalStateException();
		}

	}

	// ********** dialog **********

	static class OverwriteConfirmerDialog extends Dialog {
		private final String className;
		private boolean yes = false;
		private boolean yesToAll = false;
		private boolean no = false;
		private boolean noToAll = false;

		OverwriteConfirmerDialog(Shell parent, String className) {
			super(parent);
			this.className = className;
		}

		protected void configureShell(Shell shell) {
			super.configureShell(shell);
			shell.setText(JptUiMessages.OverwriteConfirmerDialog_title);
		}

		protected Control createDialogArea(Composite parent) {
			Composite composite = (Composite) super.createDialogArea(parent);
			GridLayout gridLayout = (GridLayout) composite.getLayout();
			gridLayout.numColumns = 2;

			Label text = new Label(composite, SWT.LEFT);
			text.setText(NLS.bind(JptUiMessages.OverwriteConfirmerDialog_text, this.className));
			text.setLayoutData(new GridData());
			
			return composite;
		}

		protected void createButtonsForButtonBar(Composite parent) {
			this.createButton(parent, IDialogConstants.YES_ID, IDialogConstants.YES_LABEL, false);
			this.createButton(parent, IDialogConstants.YES_TO_ALL_ID, IDialogConstants.YES_TO_ALL_LABEL, false);
			this.createButton(parent, IDialogConstants.NO_ID, IDialogConstants.NO_LABEL, true);
			this.createButton(parent, IDialogConstants.NO_TO_ALL_ID, IDialogConstants.NO_TO_ALL_LABEL, false);
			this.createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
		}

		protected void buttonPressed(int buttonId) {
			switch (buttonId) {
				case IDialogConstants.YES_ID :
					this.yesPressed();
					break;
				case IDialogConstants.YES_TO_ALL_ID :
					this.yesToAllPressed();
					break;
				case IDialogConstants.NO_ID :
					this.noPressed();
					break;
				case IDialogConstants.NO_TO_ALL_ID :
					this.noToAllPressed();
					break;
				case IDialogConstants.CANCEL_ID :
					this.cancelPressed();
					break;
				default :
					break;
			}
		}

		private void yesPressed() {
			this.yes = true;
			this.setReturnCode(OK);
			this.close();
		}

		private void yesToAllPressed() {
			this.yesToAll = true;
			this.setReturnCode(OK);
			this.close();
		}

		private void noPressed() {
			this.no = true;
			this.setReturnCode(OK);
			this.close();
		}

		private void noToAllPressed() {
			this.noToAll = true;
			this.setReturnCode(OK);
			this.close();
		}

		boolean yes() {
			return this.yes;
		}

		boolean yesToAll() {
			return this.yesToAll;
		}

		boolean no() {
			return this.no;
		}

		boolean noToAll() {
			return this.noToAll;
		}
	}
}

/*******************************************************************************
* Copyright (c) 2007, 2010 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.ddlgen;

import java.util.Iterator;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jpt.common.ui.internal.util.SWTUtil;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.core.JpaPlatform;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.context.persistence.Persistence;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.eclipselink.core.internal.ddlgen.EclipseLinkDDLGenerator;
import org.eclipse.jpt.eclipselink.ui.JptEclipseLinkUiPlugin;
import org.eclipse.jpt.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.jpt.eclipselink.ui.internal.ddlgen.wizards.GenerateDDLWizard;
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
		
		PersistenceUnit persistenceUnit = this.getPersistenceUnits().next(); // Take the first persistenceUnit
		String puName = persistenceUnit.getName();
		if( ! this.displayGeneratingDDLWarning()) {
			return;
		}
		
		GenerateDDLWizard wizard = new GenerateDDLWizard(this.project);
		WizardDialog dialog = new WizardDialog(this.getCurrentShell(), wizard);
		dialog.create();
		if(wizard.getPageCount() > 0) {
			int returnCode = dialog.open();
			if (returnCode == Window.CANCEL) {
				return;
			}
		}
		WorkspaceJob job = this.buildGenerateDDLJob(puName, this.project);
		job.schedule();
	}
	
	protected WorkspaceJob buildGenerateDDLJob(String puName, JpaProject project) {
		return new GenerateDDLJob(puName, project);
	}
	
	private Shell getCurrentShell() {
	    return Display.getCurrent().getActiveShell();
	}

	private boolean displayGeneratingDDLWarning() {
		String message = org.eclipse.osgi.util.NLS.bind(
			EclipseLinkUiMessages.EclipseLinkDDLGeneratorUi_generatingDDLWarningMessage,
			CR,  CR + CR);
			
		return MessageDialog.openQuestion(
			this.getCurrentShell(), 
			EclipseLinkUiMessages.EclipseLinkDDLGeneratorUi_generatingDDLWarningTitle, 
			message);
	}

	// ********** Persistence Unit **********

	protected JpaPlatform getPlatform() {
		return this.project.getJpaPlatform();
	}
	
	protected Iterator<PersistenceUnit> getPersistenceUnits() {
		return this.getPersistence().persistenceUnits();
	}

	protected Persistence getPersistence() {
		return this.project.getRootContextNode().getPersistenceXml().getPersistence();
	}

	// ********** runnable **********

	protected static class GenerateDDLJob extends WorkspaceJob {
		private final String puName;
		private final JpaProject project;

		public GenerateDDLJob(String puName, JpaProject project) {
			super(EclipseLinkUiMessages.ECLIPSELINK_GENERATE_TABLES_JOB);
			this.puName = puName;
			this.project = project;
		}

		@Override
		public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
			SubMonitor sm = SubMonitor.convert(monitor, EclipseLinkUiMessages.ECLIPSELINK_GENERATE_TABLES_TASK, 1);
			try {
				this.ddlGeneratorGenerate(this.puName, this.project, sm.newChild(1));
			} 
			catch (OperationCanceledException e) {
				return Status.CANCEL_STATUS;
			}
			catch (RuntimeException re) {				
				this.logException(re);
			}
			return Status.OK_STATUS;
		}

		protected void ddlGeneratorGenerate(String puName, JpaProject project, IProgressMonitor monitor) {
			EclipseLinkDDLGenerator.generate(puName, project, monitor);
		}

		protected void logException(RuntimeException re) {
			String msg = re.getMessage();
			String message = (msg == null) ? re.toString() : msg;
			this.displayError(message);
			JptEclipseLinkUiPlugin.log(re);
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

		private Shell getShell() {
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
}

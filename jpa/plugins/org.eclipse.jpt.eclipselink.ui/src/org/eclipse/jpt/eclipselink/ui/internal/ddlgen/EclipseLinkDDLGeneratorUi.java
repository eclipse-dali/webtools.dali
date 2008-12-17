/*******************************************************************************
* Copyright (c) 2007, 2008 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.ddlgen;

import java.util.Iterator;

import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jpt.core.JpaPlatform;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.context.persistence.Persistence;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.eclipselink.core.internal.ddlgen.EclipseLinkDDLGenerator;
import org.eclipse.jpt.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.jpt.eclipselink.ui.internal.ddlgen.wizards.GenerateDDLWizard;
import org.eclipse.jpt.utility.internal.StringTools;
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

	private EclipseLinkDDLGeneratorUi(JpaProject project) {
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
		IWorkspaceRunnable runnable = new GenerateDDLRunnable(puName, this.project);
		try {
			ResourcesPlugin.getWorkspace().run(runnable, new NullProgressMonitor());
		} 
		catch (CoreException ex) {
			throw new RuntimeException(ex);
		}
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

	static class GenerateDDLRunnable implements IWorkspaceRunnable {
		private final String puName;
		private final JpaProject project;

		GenerateDDLRunnable(String puName, JpaProject project) {
			super();
			this.puName = puName;
			this.project = project;
		}

		public void run(IProgressMonitor monitor) throws CoreException {
			String projectLocation = this.project.getProject().getLocation().toString();
			try {
				EclipseLinkDDLGenerator.generate(this.puName, this.project, projectLocation, monitor);
			} 
			catch (OperationCanceledException e) {
				return;
				// fall through and tell monitor we are done
			}
			catch (RuntimeException re) {
				String msg = re.getMessage();
				String message = (msg == null) ? re.toString() : msg;
				
				this.logError(message);
				throw new RuntimeException(re);
			}
		}

		protected void logError(String message) {
				this.displayError(message);
		}
		
		private void displayError(String message) {
			MessageDialog.openError(
					getShell(),
					EclipseLinkUiMessages.EclipseLinkDDLGeneratorUi_error,
					message
				);
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

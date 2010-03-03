/*******************************************************************************
* Copyright (c) 2010 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jaxb.ui.internal;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.jaxb.core.internal.ClassesGenerator;
import org.eclipse.jpt.jaxb.ui.internal.wizards.ClassesGeneratorWizard;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 *  ClassesGeneratorUi
 */
public class ClassesGeneratorUi {
	private final JpaProject project;
	private final String xmlSchemaName;

	// ********** static methods **********
	
	public static void generate(IFile xsdFile) {
		JpaProject jpaProject = JptCorePlugin.getJpaProject(xsdFile.getProject());
		if (jpaProject == null) {
			return;
		}
		IPath xmlSchema = xsdFile.getProjectRelativePath();
		
		new ClassesGeneratorUi(jpaProject, xmlSchema.toOSString()).generate();
	}

	// ********** constructors **********
	private ClassesGeneratorUi(JpaProject project, String xmlSchemaName) {
		super();
		if (project == null || StringTools.stringIsEmpty(xmlSchemaName)) {
			throw new NullPointerException();
		}
		this.project = project;
		this.xmlSchemaName = xmlSchemaName;
	}

	// ********** generate **********
	/**
	 * prompt the user with a wizard
	 */
	protected void generate() {

		ClassesGeneratorWizard wizard = new ClassesGeneratorWizard(this.project, this.xmlSchemaName);
		WizardDialog dialog = new WizardDialog(this.getCurrentShell(), wizard);
		dialog.create();
		int returnCode = dialog.open();
		if (returnCode != Window.OK) {
			return;
		}
		String outputDir = wizard.getDestinationFolder();
		String targetPackage = wizard.getTargetPackage();
		String catalog = wizard.getCatalog();
		boolean useMoxy = wizard.getUseMoxy();
		String[] bindingsFileNames = wizard.getBindingsFileNames();

		this.run(outputDir, targetPackage, catalog, useMoxy, bindingsFileNames);
	}

	// ********** internal methods **********

	private void run(
		String outputDir,
		String targetPackage, 
		String catalog, 
		boolean useMoxyGenerator,
		String[] bindingsFileNames) {
		
		IWorkspaceRunnable runnable = this.buildGenerateEntitiesRunnable(
			this.project, 
			this.xmlSchemaName, 
			outputDir, 
			targetPackage, 
			catalog, 
			useMoxyGenerator,
			bindingsFileNames);
		try {
			ResourcesPlugin.getWorkspace().run(runnable, new NullProgressMonitor());
		} 
		catch (CoreException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	private IWorkspaceRunnable buildGenerateEntitiesRunnable(
		JpaProject project, 
		String xmlSchemaName, 
		String outputDir,
		String targetPackage, 
		String catalog, 
		boolean useMoxyGenerator,
		String[] bindingsFileNames) {

		return new GenerateEntitiesRunnable(project, xmlSchemaName, outputDir, targetPackage, catalog, useMoxyGenerator, bindingsFileNames);
	}
	
	private Shell getCurrentShell() {
	    return Display.getCurrent().getActiveShell();
	}

	// ********** Runnable Class **********

	private static class GenerateEntitiesRunnable implements IWorkspaceRunnable {
		private final JpaProject project;
		private final String xmlSchemaName;
		private final String outputDir;
		private final String targetPackage;
		private final String catalog;
		private final boolean useMoxyGenerator;
		private final String[] bindingsFileNames;

		// ********** constructors **********
		
		public GenerateEntitiesRunnable(
			JpaProject project, 
			String xmlSchemaName, 
			String outputDir,
			String targetPackage, 
			String catalog, 
			boolean useMoxyGenerator,
			String[] bindingsFileNames) {
			
			super();
			this.project = project;
			this.xmlSchemaName = xmlSchemaName;
			this.outputDir = outputDir;
			this.targetPackage = targetPackage;
			this.catalog = catalog;
			this.useMoxyGenerator = useMoxyGenerator;
			this.bindingsFileNames = bindingsFileNames;
		}

		public void run(IProgressMonitor monitor) {
			try {
				this.entitiesGeneratorGenerate(this.project, 
					this.xmlSchemaName, 
					this.outputDir, 
					this.targetPackage, 
					this.catalog, 
					this.useMoxyGenerator,
					this.bindingsFileNames,
					monitor);
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
	
		private void entitiesGeneratorGenerate(JpaProject project, 
			String xmlSchemaName, 
			String outputDir, 
			String targetPackage, 
			String catalog, 
			boolean useMoxyGenerator,
			String[] bindingsFileNames, 
			IProgressMonitor monitor) {
	
			ClassesGenerator.generate(project, 
				xmlSchemaName, 
				outputDir, 
				targetPackage, 
				catalog, 
				useMoxyGenerator, 
				bindingsFileNames, 
				monitor);
			return;
		}
	
		private void logError(String message) {
				this.displayError(message);
		}
		
		private void displayError(String message) {
			MessageDialog.openError(
					this.getShell(),
					JptJaxbUiMessages.ClassesGeneratorWizard_errorDialogTitle,
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

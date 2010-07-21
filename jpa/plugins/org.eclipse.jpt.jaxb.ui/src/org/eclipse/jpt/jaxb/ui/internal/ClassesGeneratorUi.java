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
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jpt.jaxb.core.internal.ClassesGenerator;
import org.eclipse.jpt.jaxb.ui.internal.wizards.classesgen.ClassesGeneratorWizard;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 *  ClassesGeneratorUi
 */
public class ClassesGeneratorUi {
	private final IJavaProject javaProject;
	private final String xmlSchemaName;

	// ********** static methods **********
	
	public static void generate(IFile xsdFile) {
		IJavaProject javaProject = JavaCore.create(xsdFile.getProject());
		if (javaProject == null) {
			throw new NullPointerException();
		}
		IPath xmlSchema = xsdFile.getProjectRelativePath();
		
		new ClassesGeneratorUi(javaProject, xmlSchema.toOSString()).generate();
	}

	// ********** constructors **********
	private ClassesGeneratorUi(IJavaProject javaProject, String xmlSchemaName) {
		super();
		if(javaProject == null || StringTools.stringIsEmpty(xmlSchemaName)) {
			throw new NullPointerException();
		}
		this.javaProject = javaProject;
		this.xmlSchemaName = xmlSchemaName;
	}

	// ********** generate **********
	/**
	 * prompt the user with a wizard
	 */
	protected void generate() {
		ClassesGeneratorWizard wizard = new ClassesGeneratorWizard(this.javaProject, this.xmlSchemaName);
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

		if(this.displayGeneratingClassesWarning()) {
			this.run(outputDir, targetPackage, catalog, useMoxy, bindingsFileNames);
		}
	}

	// ********** internal methods **********

	private void run(
		String outputDir,
		String targetPackage, 
		String catalog, 
		boolean useMoxyGenerator,
		String[] bindingsFileNames) {
		
		WorkspaceJob job = new GenerateEntitiesJob(
			this.javaProject, 
			this.xmlSchemaName, 
			outputDir, 
			targetPackage, 
			catalog, 
			useMoxyGenerator,
			bindingsFileNames);
		job.schedule();
	}

	private boolean displayGeneratingClassesWarning() {

		return MessageDialog.openQuestion(
			this.getCurrentShell(), 
			JptJaxbUiMessages.ClassesGeneratorUi_generatingClassesWarningTitle,
			JptJaxbUiMessages.ClassesGeneratorUi_generatingClassesWarningMessage);
	}
	
	private Shell getCurrentShell() {
	    return Display.getCurrent().getActiveShell();
	}

	// ********** Runnable Class **********

	private static class GenerateEntitiesJob extends WorkspaceJob {
		private final IJavaProject javaProject;
		private final String xmlSchemaName;
		private final String outputDir;
		private final String targetPackage;
		private final String catalog;
		private final boolean useMoxyGenerator;
		private final String[] bindingsFileNames;

		// ********** constructors **********
		
		public GenerateEntitiesJob(
			IJavaProject javaProject, 
			String xmlSchemaName, 
			String outputDir,
			String targetPackage, 
			String catalog, 
			boolean useMoxyGenerator,
			String[] bindingsFileNames) {
			
			super(JptJaxbUiMessages.ClassesGeneratorUi_generatingEntities);
			this.javaProject = javaProject;
			this.xmlSchemaName = xmlSchemaName;
			this.outputDir = outputDir;
			this.targetPackage = targetPackage;
			this.catalog = catalog;
			this.useMoxyGenerator = useMoxyGenerator;
			this.bindingsFileNames = bindingsFileNames;
			this.setRule(ResourcesPlugin.getWorkspace().getRuleFactory().modifyRule(this.javaProject.getProject()));
		}

		@Override
		public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
			SubMonitor sm = SubMonitor.convert(monitor, JptJaxbUiMessages.ClassesGeneratorUi_generatingEntitiesTask, 1);
			try {
				this.entitiesGeneratorGenerate(this.javaProject, 
					this.xmlSchemaName, 
					this.outputDir, 
					this.targetPackage, 
					this.catalog, 
					this.useMoxyGenerator,
					this.bindingsFileNames,
					sm.newChild(1));
			} 
			catch (OperationCanceledException e) {
				return Status.CANCEL_STATUS;
				// fall through and tell monitor we are done
			}
			catch (RuntimeException re) {
				String msg = re.getMessage();
				String message = (msg == null) ? re.toString() : msg;
				
				this.logError(message);
				throw new RuntimeException(re);
			}
			return Status.OK_STATUS;
	}
	
		private void entitiesGeneratorGenerate(IJavaProject javaProject, 
			String xmlSchemaName, 
			String outputDir, 
			String targetPackage, 
			String catalog, 
			boolean useMoxyGenerator,
			String[] bindingsFileNames, 
			IProgressMonitor monitor) {
	
			ClassesGenerator.generate(javaProject, 
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

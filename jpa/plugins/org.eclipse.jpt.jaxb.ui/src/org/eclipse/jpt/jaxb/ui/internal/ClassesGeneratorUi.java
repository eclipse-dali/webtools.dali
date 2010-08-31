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
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jpt.jaxb.core.internal.ClassesGeneratorExtensionOptions;
import org.eclipse.jpt.jaxb.core.internal.ClassesGeneratorOptions;
import org.eclipse.jpt.jaxb.core.internal.GenerateJaxbClassesJob;
import org.eclipse.jpt.jaxb.ui.JptJaxbUiPlugin;
import org.eclipse.jpt.jaxb.ui.internal.wizards.classesgen.ClassesGeneratorWizard;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 *  ClassesGeneratorUi
 */
public class ClassesGeneratorUi {
	private final IJavaProject javaProject;
	private final String schemaPathOrUri;

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
	private ClassesGeneratorUi(IJavaProject javaProject, String schemaPathOrUri) {
		super();
		if(javaProject == null || StringTools.stringIsEmpty(schemaPathOrUri)) {
			throw new NullPointerException();
		}
		this.javaProject = javaProject;
		this.schemaPathOrUri = schemaPathOrUri;
	}

	// ********** generate **********
	/**
	 * prompt the user with a wizard
	 */
	protected void generate() {
		ClassesGeneratorWizard wizard = new ClassesGeneratorWizard(this.javaProject, this.schemaPathOrUri);
		WizardDialog dialog = new WizardDialog(this.getCurrentShell(), wizard);
		dialog.create();
		int returnCode = dialog.open();
		if (returnCode != Window.OK) {
			return;
		}
		String outputDir = wizard.getDestinationFolder();
		String targetPackage = wizard.getTargetPackage();
		String catalog = wizard.getCatalog();
		boolean usesMoxy = wizard.usesMoxy();
		String[] bindingsFileNames = wizard.getBindingsFileNames();
		ClassesGeneratorOptions generatorOptions = wizard.getGeneratorOptions();
		ClassesGeneratorExtensionOptions generatorExtensionOptions = wizard.getGeneratorExtensionOptions();

		if(this.displayOverridingClassesWarning(generatorOptions)) {
			this.generateJaxbClasses(outputDir, targetPackage, catalog, usesMoxy, bindingsFileNames, generatorOptions, generatorExtensionOptions);
		}
	}

	// ********** internal methods **********

	private void generateJaxbClasses(
		String outputDir,
		String targetPackage, 
		String catalog, 
		boolean usesMoxyGenerator,
		String[] bindingsFileNames,
		ClassesGeneratorOptions generatorOptions,
		ClassesGeneratorExtensionOptions generatorExtensionOptions) {
		
		try {
			WorkspaceJob job = new GenerateJaxbClassesJob(
				this.javaProject, 
				this.schemaPathOrUri, 
				outputDir, 
				targetPackage, 
				catalog, 
				usesMoxyGenerator,
				bindingsFileNames,
				generatorOptions,
				generatorExtensionOptions);
			job.schedule();
		}
		catch(RuntimeException re) {
			JptJaxbUiPlugin.log(re);

			String msg = re.getMessage();
			String message = (msg == null) ? re.toString() : msg;
			this.logError(message);
		}
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
		if(shell == null && display != null) {
			Shell[] shells = display.getShells();
			if(shells.length > 0)
				shell = shells[0];
		}
		return shell;
	}
	
	private boolean isOverridingClasses(ClassesGeneratorOptions generatorOptions) {
		if(generatorOptions == null) {
			throw new NullPointerException();
		}
		if(generatorOptions.showsVersion() || generatorOptions.showsHelp()) {
			return false;
		}
		return true;
	}

	private boolean displayOverridingClassesWarning(ClassesGeneratorOptions generatorOptions) {
		
		if( ! this.isOverridingClasses(generatorOptions)) {
			return true;
		}
		return MessageDialog.openQuestion(
			this.getCurrentShell(), 
			JptJaxbUiMessages.ClassesGeneratorUi_generatingClassesWarningTitle,
			JptJaxbUiMessages.ClassesGeneratorUi_generatingClassesWarningMessage);
	}
	
	private Shell getCurrentShell() {
	    return Display.getCurrent().getActiveShell();
	}
}

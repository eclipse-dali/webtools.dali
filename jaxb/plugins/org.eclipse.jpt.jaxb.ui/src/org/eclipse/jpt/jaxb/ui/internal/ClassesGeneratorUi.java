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

import java.util.HashMap;
import java.util.Map;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jpt.jaxb.core.JaxbProject;
import org.eclipse.jpt.jaxb.core.JptJaxbCorePlugin;
import org.eclipse.jpt.jaxb.core.SchemaLibrary;
import org.eclipse.jpt.jaxb.core.internal.gen.ClassesGeneratorExtensionOptions;
import org.eclipse.jpt.jaxb.core.internal.gen.ClassesGeneratorOptions;
import org.eclipse.jpt.jaxb.core.internal.gen.GenerateJaxbClassesJob;
import org.eclipse.jpt.jaxb.core.xsd.XsdUtil;
import org.eclipse.jpt.jaxb.ui.JptJaxbUiPlugin;
import org.eclipse.jpt.jaxb.ui.internal.wizards.classesgen.ClassesGeneratorWizard;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wst.xsd.contentmodel.internal.XSDImpl;
import org.eclipse.xsd.XSDSchema;

/**
 *  ClassesGeneratorUi
 */
public class ClassesGeneratorUi {
	
	private final IJavaProject javaProject;
	private final IFile xsdFile;
	
	// ********** static methods **********
	
	public static void generate(IFile xsdFile) {
		IJavaProject javaProject = JavaCore.create(xsdFile.getProject());
		if (javaProject == null) {
			throw new NullPointerException();
		}
		
		new ClassesGeneratorUi(javaProject, xsdFile).generate();
	}

	// ********** constructors **********
	private ClassesGeneratorUi(IJavaProject javaProject, IFile xsdFile) {
		super();
		if (javaProject == null) {
			throw new NullPointerException();
		}
		this.javaProject = javaProject;
		this.xsdFile = xsdFile;
	}

	// ********** generate **********
	/**
	 * prompt the user with a wizard
	 */
	protected void generate() {
		ClassesGeneratorWizard wizard = new ClassesGeneratorWizard(this.javaProject, this.xsdFile);
		wizard.setWindowTitle(JptJaxbUiMessages.ClassesGeneratorWizard_title);
		WizardDialog dialog = new WizardDialog(this.getCurrentShell(), wizard);
		dialog.create();
		int returnCode = dialog.open();
		if (returnCode != Window.OK) {
			return;
		}
		
		if(this.displayOverridingClassesWarning(wizard.getGeneratorOptions())) {
			this.generateJaxbClasses(
					wizard.getLocalSchemaUri(), 
					wizard.getDestinationFolder(), 
					wizard.getTargetPackage(), 
					wizard.getCatalog(), 
					wizard.usesMoxy(), 
					wizard.getBindingsFileNames(),
					wizard.getGeneratorOptions(),
					wizard.getGeneratorExtensionOptions());
			addSchemaToLibrary(wizard.getSchemaLocation());
		}
	}

	// ********** internal methods **********

	private void generateJaxbClasses(
			URI schemaUri,
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
					schemaUri.toString(),
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
	
	private void addSchemaToLibrary(String schemaLocation) {
		JaxbProject jaxbProject = getJaxbProject();
		
		if (jaxbProject == null) {
			return;
		}
		
		String resolvedUri = XsdUtil.getResolvedUri(null, schemaLocation);
		XSDSchema schema = XSDImpl.buildXSDModel(resolvedUri);
		if (schema != null) {
			String schemaNamespace = 
				((schema.getTargetNamespace()) == null ? 
						""
						: schema.getTargetNamespace());
			
			SchemaLibrary schemaLib = jaxbProject.getSchemaLibrary();
			Map<String, String> schemas = new HashMap<String, String>(schemaLib.getSchemaLocations());
			schemas.put(schemaNamespace, schemaLocation);
			schemaLib.setSchemaLocations(schemas);
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
	
	/* may be null */
	private JaxbProject getJaxbProject() {
		return JptJaxbCorePlugin.getJaxbProject(this.javaProject.getProject());
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

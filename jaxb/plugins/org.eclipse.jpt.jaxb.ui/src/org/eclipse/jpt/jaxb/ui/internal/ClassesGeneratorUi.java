/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.ui.internal;

import java.util.List;
import java.util.Vector;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jpt.common.ui.internal.WorkbenchTools;
import org.eclipse.jpt.common.ui.internal.dialogs.OptionalMessageDialog;
import org.eclipse.jpt.jaxb.core.JaxbProject;
import org.eclipse.jpt.jaxb.core.JaxbProjectManager;
import org.eclipse.jpt.jaxb.core.JaxbWorkspace;
import org.eclipse.jpt.jaxb.core.SchemaLibrary;
import org.eclipse.jpt.jaxb.core.internal.gen.ClassesGeneratorExtensionOptions;
import org.eclipse.jpt.jaxb.core.internal.gen.ClassesGeneratorOptions;
import org.eclipse.jpt.jaxb.core.xsd.XsdUtil;
import org.eclipse.jpt.jaxb.ui.JaxbWorkbench;
import org.eclipse.jpt.jaxb.ui.JptJaxbUiMessages;
import org.eclipse.jpt.jaxb.ui.internal.gen.GenerateJaxbClassesJob;
import org.eclipse.jpt.jaxb.ui.internal.plugin.JptJaxbUiPlugin;
import org.eclipse.jpt.jaxb.ui.internal.wizards.classesgen.ClassesGeneratorWizard;
import org.eclipse.swt.widgets.Composite;
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
		WizardDialog dialog = new WizardDialog(this.getCurrentShell(), wizard);
		dialog.create();
		int returnCode = dialog.open();
		if (returnCode != Window.OK) {
			return;
		}
		
		if(this.displayOverwritingClassesWarning(wizard.getGeneratorOptions())) {
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
			JptJaxbUiPlugin.instance().logError(re);

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
		
		String resolvedUri = XsdUtil.getResolvedUri(schemaLocation);
		XSDSchema schema = XSDImpl.buildXSDModel(resolvedUri);
		if (schema != null) {
			SchemaLibrary schemaLib = jaxbProject.getSchemaLibrary();
			List<String> schemas = new Vector<String>(schemaLib.getSchemaLocations());
			if (! schemas.contains(schemaLocation)) {
				schemas.add(schemaLocation);
				schemaLib.setSchemaLocations(schemas);
			}
		}
	}
	
	private void logError(String message) {
		this.displayError(message);
	}

	private void displayError(String message) {
		MessageDialog.openError(
				this.getShell(),
				JptJaxbUiMessages.CLASSES_GENERATOR_WIZARD_ERROR_DIALOG_TITLE,
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
		JaxbProjectManager jaxbProjectManager = this.getJaxbProjectManager();
		return (jaxbProjectManager == null) ? null : jaxbProjectManager.getJaxbProject(this.javaProject.getProject());
	}
	
	private JaxbProjectManager getJaxbProjectManager() {
		JaxbWorkspace jaxbWorkspace = this.getJaxbWorkspace();
		return (jaxbWorkspace == null) ? null : jaxbWorkspace.getJaxbProjectManager();
	}

	private JaxbWorkspace getJaxbWorkspace() {
		JaxbWorkbench jaxbWorkbench = this.getJaxbWorkbench();
		return (jaxbWorkbench == null) ? null : jaxbWorkbench.getJaxbWorkspace();
	}

	private JaxbWorkbench getJaxbWorkbench() {
		return WorkbenchTools.getAdapter(JaxbWorkbench.class);
	}

	private boolean isOverwritingClasses(ClassesGeneratorOptions generatorOptions) {
		if(generatorOptions == null) {
			throw new NullPointerException();
		}
		if(generatorOptions.showsVersion() || generatorOptions.showsHelp()) {
			return false;
		}
		return true;
	}

	private boolean displayOverwritingClassesWarning(ClassesGeneratorOptions generatorOptions) {
		
		if( ! this.isOverwritingClasses(generatorOptions)
				|| !OptionalMessageDialog.isDialogEnabled(OverwriteConfirmerDialog.ID)) {
			return true;
		}else {
			OverwriteConfirmerDialog dialog = new OverwriteConfirmerDialog(this.getShell());
			return dialog.open() == IDialogConstants.YES_ID;			
		}
	}
	
	private Shell getCurrentShell() {
	    return Display.getCurrent().getActiveShell();
	}
	
	// ********** overwrite dialog **********

	static class OverwriteConfirmerDialog extends OptionalMessageDialog {

		private static final String ID= "dontShowOverwriteJaxbClassesFromSchemas.warning"; //$NON-NLS-1$

		OverwriteConfirmerDialog(Shell parent) {
			super(ID, parent,
					JptJaxbUiMessages.CLASSES_GENERATOR_UI_GENERATING_CLASSES_WARNING_TITLE,
					JptJaxbUiMessages.CLASSES_GENERATOR_UI_GENERATING_CLASSES_WARNING_MESSAGE,
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

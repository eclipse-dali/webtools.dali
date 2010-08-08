/*******************************************************************************
* Copyright (c) 2010 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jaxb.ui.internal.wizards.classesgen;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jpt.jaxb.core.internal.ClassesGeneratorExtensionOptions;
import org.eclipse.jpt.jaxb.core.internal.ClassesGeneratorOptions;
import org.eclipse.jpt.jaxb.ui.JptJaxbUiIcons;
import org.eclipse.jpt.jaxb.ui.JptJaxbUiPlugin;
import org.eclipse.jpt.jaxb.ui.internal.JptJaxbUiMessages;
import org.eclipse.osgi.util.NLS;

/**
 *  ClassesGeneratorWizard
 */
public class ClassesGeneratorWizard extends Wizard {	

	private final IJavaProject javaProject;
	private final String xmlSchemaName;

	private String destinationFolder;
	private String targetPackage;
	private String catalog;
	private boolean usesMoxy;
	private String[] bindingsFileNames;
	private ClassesGeneratorOptions generatorOptions;
	private ClassesGeneratorExtensionOptions generatorExtensionOptions;

	private ClassesGeneratorWizardPage settingsPage;
	private ClassesGeneratorOptionsWizardPage optionsPage;
	private ClassesGeneratorExtensionOptionsWizardPage extensionOptionsPage;

	// ********** constructor **********
	
	public ClassesGeneratorWizard(IJavaProject javaProject, String xmlSchemaName) {
		super();
		this.javaProject = javaProject;
		this.xmlSchemaName = xmlSchemaName;

		this.setWindowTitle(JptJaxbUiMessages.ClassesGeneratorWizard_title);
		this.setDefaultPageImageDescriptor(JptJaxbUiPlugin.getImageDescriptor(JptJaxbUiIcons.CLASSES_GEN_WIZ_BANNER));
		this.setNeedsProgressMonitor(true);
	}

	// ********** overrides **********
	
	@Override
	public void addPages() {
		super.addPages();
		
		this.settingsPage = this.buildClassesGeneratorPage();
		this.optionsPage = this.buildClassesGeneratorOptionsPage();
		this.extensionOptionsPage = this.buildExtensionOptionsPage();
		
		this.addPage(this.settingsPage);
		this.addPage(this.optionsPage);
		this.addPage(this.extensionOptionsPage);
	}
	
	@Override
	public boolean performFinish() {

		this.initializeGeneratorSettings();

		this.initializeGeneratorOptions();
		
		this.initializeGeneratorExtensionOptions();

		IFolder folder = this.javaProject.getProject().getFolder(this.destinationFolder);
		this.createFolderIfNotExist(folder);
		return true;
	}

	private void initializeGeneratorSettings() {
		this.destinationFolder = this.settingsPage.getTargetFolder();
		this.targetPackage = this.settingsPage.getTargetPackage();
		this.catalog = this.settingsPage.getCatalog();
		this.usesMoxy = this.settingsPage.usesMoxy();
		this.bindingsFileNames = this.settingsPage.getBindingsFileNames();
	}
	
	private void initializeGeneratorOptions() {
		this.generatorOptions = new ClassesGeneratorOptions();

		this.generatorOptions.setProxy(this.optionsPage.getProxy());
		this.generatorOptions.setProxyFile(this.optionsPage.getProxyFile());
		
		this.generatorOptions.setUsesStrictValidation(this.optionsPage.usesStrictValidation());
		this.generatorOptions.setMakesReadOnly(this.optionsPage.makesReadOnly());
		this.generatorOptions.setSuppressesPackageInfoGen(this.optionsPage.suppressesPackageInfoGen());
		this.generatorOptions.setSuppressesHeaderGen(this.optionsPage.suppressesHeaderGen());
		this.generatorOptions.setTargetIs20(this.optionsPage.getTarget());
		this.generatorOptions.setIsVerbose(this.optionsPage.isVerbose());
		this.generatorOptions.setIsQuiet(this.optionsPage.isQuiet());
		
		this.generatorOptions.setTreatsAsXmlSchema(this.optionsPage.treatsAsXmlSchema());
		this.generatorOptions.setTreatsAsRelaxNg(this.optionsPage.treatsAsRelaxNg());
		this.generatorOptions.setTreatsAsRelaxNgCompact(this.optionsPage.treatsAsRelaxNgCompact());
		this.generatorOptions.setTreatsAsDtd(this.optionsPage.treatsAsDtd());
		this.generatorOptions.setTreatsAsWsdl(this.optionsPage.treatsAsWsdl());
		this.generatorOptions.setShowsVersion(this.optionsPage.showsVersion());
		this.generatorOptions.setShowsHelp(this.optionsPage.showsHelp());
	}
	
	private void initializeGeneratorExtensionOptions() {

		this.generatorExtensionOptions = new ClassesGeneratorExtensionOptions();

		this.generatorExtensionOptions.setAllowsExtensions(this.extensionOptionsPage.allowsExtensions());
		this.generatorExtensionOptions.setClasspath(this.extensionOptionsPage.getClasspath());
		this.generatorExtensionOptions.setAdditionalArgs(this.extensionOptionsPage.getAdditionalArgs());
	}

    @Override
	public boolean canFinish() {
    	return this.settingsPage.isPageComplete();
    }

	// ********** public methods **********
    
    public String getDestinationFolder() {
		return this.destinationFolder;
	}
	
    public String getTargetPackage() {
		return this.targetPackage;
	}
	
    public String getCatalog() {
		return this.catalog;
	}
	
    public boolean usesMoxy() {
		return this.usesMoxy;
	}

    public String[] getBindingsFileNames() {
		return this.bindingsFileNames;
	}
	
    public ClassesGeneratorOptions getGeneratorOptions() {
		return this.generatorOptions;
	}
	
    public ClassesGeneratorExtensionOptions getGeneratorExtensionOptions() {
		return this.generatorExtensionOptions;
	}
	
	// ********** internal methods **********

	private ClassesGeneratorWizardPage buildClassesGeneratorPage() {
		return new ClassesGeneratorWizardPage(this.javaProject, this.xmlSchemaName);
	}
	
	private ClassesGeneratorOptionsWizardPage buildClassesGeneratorOptionsPage() {
		return new ClassesGeneratorOptionsWizardPage(this.javaProject);
	}
	
	private ClassesGeneratorExtensionOptionsWizardPage buildExtensionOptionsPage() {
		return new ClassesGeneratorExtensionOptionsWizardPage();
	}
	
	private void createFolderIfNotExist(IFolder folder) {
		if( folder.exists()) {
			return;
		}
		try {
			folder.create(true, true, null);
		}
		catch (CoreException e) {
			this.logError(NLS.bind(
				JptJaxbUiMessages.ClassesGeneratorWizard_couldNotCreate, 
				folder.getProjectRelativePath().toOSString()));
		}
	}
	
	protected void logError(String message) {
			this.displayError(message);
	}
	
	private void displayError(String message) {
		MessageDialog.openError(
				this.getShell(),
				JptJaxbUiMessages.ClassesGeneratorWizard_errorDialogTitle,
				message
			);
	}
}

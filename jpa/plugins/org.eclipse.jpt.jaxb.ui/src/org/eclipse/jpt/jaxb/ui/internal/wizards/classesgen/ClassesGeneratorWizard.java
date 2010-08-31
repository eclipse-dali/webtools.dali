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

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.jpt.jaxb.core.internal.ClassesGeneratorExtensionOptions;
import org.eclipse.jpt.jaxb.core.internal.ClassesGeneratorOptions;
import org.eclipse.jpt.jaxb.core.internal.GenerateJaxbClassesJob;
import org.eclipse.jpt.jaxb.ui.JptJaxbUiIcons;
import org.eclipse.jpt.jaxb.ui.JptJaxbUiPlugin;
import org.eclipse.jpt.jaxb.ui.internal.JptJaxbUiMessages;
import org.eclipse.jpt.jaxb.ui.internal.wizards.ProjectWizardPage;
import org.eclipse.osgi.util.NLS;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWizard;

/**
 *  ClassesGeneratorWizard
 */
public class ClassesGeneratorWizard extends Wizard implements IWorkbenchWizard {

	private IJavaProject javaProject;
	private String schemaPathOrUri;
	protected IStructuredSelection selection;
	
	private String destinationFolder;
	private String targetPackage;
	private String catalog;
	private boolean usesMoxy;
	private String[] bindingsFileNames;
	private ClassesGeneratorOptions generatorOptions;
	private ClassesGeneratorExtensionOptions generatorExtensionOptions;

	private ProjectWizardPage javaProjectWizardPage;
	private SchemaWizardPage schemaWizardPage;
	
	private ClassesGeneratorWizardPage settingsPage;
	private ClassesGeneratorOptionsWizardPage optionsPage;
	private ClassesGeneratorExtensionOptionsWizardPage extensionOptionsPage;
	private boolean performsGeneration;

	// ********** constructor **********
	
	public ClassesGeneratorWizard() {
		super();
		this.performsGeneration = true;
	}
	
	public ClassesGeneratorWizard(IJavaProject javaProject, String xsdSchemaPath) {
		super();
		this.javaProject = javaProject;
		this.schemaPathOrUri = xsdSchemaPath;

		this.performsGeneration = false;
	}

	// ********** IWorkbenchWizard implementation  **********

	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.selection = selection;
		
		this.setWindowTitle(JptJaxbUiMessages.ClassesGeneratorWizard_title);
		this.setDefaultPageImageDescriptor(JptJaxbUiPlugin.getImageDescriptor(JptJaxbUiIcons.CLASSES_GEN_WIZ_BANNER));
		this.setNeedsProgressMonitor(true);
	}

	// ********** IWizard implementation  **********
	
	@Override
	public void addPages() {
		super.addPages();

		if(this.selection != null) {
			this.javaProject = ProjectWizardPage.getJavaProjectFromSelection(this.selection);
			
			this.javaProjectWizardPage = new ProjectWizardPage(this.javaProject);
			this.javaProjectWizardPage.setTitle(JptJaxbUiMessages.ClassesGeneratorProjectWizardPage_title);
			this.addPage(this.javaProjectWizardPage);

			// SchemaWizardPage
			IFile schemaSelected = SchemaWizardPage.getSourceSchemaFromSelection(this.selection);
			if(schemaSelected == null) {
				this.schemaWizardPage = new SchemaWizardPage(this.selection);
				this.addPage(this.schemaWizardPage);
			}
			else {
				this.schemaPathOrUri = this.makeRelativeToProjectPath(schemaSelected.getFullPath());
			}
		}
		this.settingsPage = this.buildClassesGeneratorPage();
		this.optionsPage = this.buildClassesGeneratorOptionsPage();
		this.extensionOptionsPage = this.buildExtensionOptionsPage();
		
		this.addPage(this.settingsPage);
		this.addPage(this.optionsPage);
		this.addPage(this.extensionOptionsPage);
	}
	
	@Override
	public boolean performFinish() {
		
		WizardPage currentPage = (WizardPage)getContainer().getCurrentPage();
		if(currentPage != null) {
			if( ! currentPage.isPageComplete()) {
				return false;
			}
			this.retrieveGeneratorSettings();
			this.retrieveGeneratorOptions();
			this.retrieveGeneratorExtensionOptions();
			
			IFolder folder = this.getJavaProject().getProject().getFolder(this.destinationFolder);
			this.createFolderIfNotExist(folder);
		}

		if(this.performsGeneration) {
			if(this.displayOverridingClassesWarning(this.generatorOptions)) {
				this.generateJaxbClasses();
			}
		}

		return true;
	}

    @Override
	public boolean canFinish() {
    	return (this.settingsPage.isPageComplete() &&
    				this.optionsPage.isPageComplete() &&
    					this.extensionOptionsPage.isPageComplete());
    }
    
	// ********** intra-wizard methods **********
    
	public IJavaProject getJavaProject() {
		if(this.javaProjectWizardPage != null) {
			return this.javaProjectWizardPage.getJavaProject();
		}
    	return this.javaProject;
    }

	public String getSchemaPathOrUri() {
		if(this.schemaWizardPage != null) {
			IFile schemaFile = this.schemaWizardPage.getSourceSchema();
			if(schemaFile != null) {
				return this.makeRelativeToProjectPath(schemaFile.getFullPath());
			}
			else {
				return this.schemaWizardPage.getSourceURI();
			}
		}
		return this.schemaPathOrUri;
	}

	private String makeRelativeToProjectPath(IPath path) {
		IPath relativePath = path.makeRelativeTo(this.getJavaProject().getProject().getFullPath());
		return relativePath.toOSString();
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
	
	private boolean displayOverridingClassesWarning(ClassesGeneratorOptions generatorOptions) {
		
		if( ! this.isOverridingClasses(generatorOptions)) {
			return true;
		}
		return MessageDialog.openQuestion(
			this.getShell(), 
			JptJaxbUiMessages.ClassesGeneratorUi_generatingClassesWarningTitle,
			JptJaxbUiMessages.ClassesGeneratorUi_generatingClassesWarningMessage);
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
	
	private void generateJaxbClasses() {
		try {
			WorkspaceJob job = new GenerateJaxbClassesJob(
				this.getJavaProject(), 
				this.getSchemaPathOrUri(), 
				this.destinationFolder, 
				this.targetPackage, 
				this.catalog, 
				this.usesMoxy,
				this.bindingsFileNames,
				this.generatorOptions,
				this.generatorExtensionOptions);
			job.schedule();
		}
		catch(RuntimeException re) {
			JptJaxbUiPlugin.log(re);
			
			String msg = re.getMessage();
			String message = (msg == null) ? re.toString() : msg;
			this.logError(message);
		}
	}

	private void retrieveGeneratorSettings() {
		this.destinationFolder = this.settingsPage.getTargetFolder();
		this.targetPackage = this.settingsPage.getTargetPackage();
		this.catalog = this.settingsPage.getCatalog();
		this.usesMoxy = this.settingsPage.usesMoxy();
		this.bindingsFileNames = this.settingsPage.getBindingsFileNames();
	}
	
	private void retrieveGeneratorOptions() {
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
	
	private void retrieveGeneratorExtensionOptions() {

		this.generatorExtensionOptions = new ClassesGeneratorExtensionOptions();

		this.generatorExtensionOptions.setAllowsExtensions(this.extensionOptionsPage.allowsExtensions());
		this.generatorExtensionOptions.setClasspath(this.extensionOptionsPage.getClasspath());
		this.generatorExtensionOptions.setAdditionalArgs(this.extensionOptionsPage.getAdditionalArgs());
	}

	private ClassesGeneratorWizardPage buildClassesGeneratorPage() {

		return new ClassesGeneratorWizardPage();
	}
	
	private ClassesGeneratorOptionsWizardPage buildClassesGeneratorOptionsPage() {
		return new ClassesGeneratorOptionsWizardPage();
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
			JptJaxbUiPlugin.log(e);
			
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

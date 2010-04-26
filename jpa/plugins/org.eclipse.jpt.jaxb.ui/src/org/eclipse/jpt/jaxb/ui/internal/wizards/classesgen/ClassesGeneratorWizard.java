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
	private boolean useMoxy;
	private String[] bindingsFileNames;

	private ClassesGeneratorWizardPage generatorSettingsPage;

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
		
		this.generatorSettingsPage = this.buildClassesGeneratorPage();
		
		this.addPage(this.generatorSettingsPage);
	}
	
	@Override
	public boolean performFinish() {
		this.destinationFolder = this.generatorSettingsPage.getTargetFolder();
		this.targetPackage = this.generatorSettingsPage.getTargetPackage();
		this.catalog = this.generatorSettingsPage.getCatalog();
		this.useMoxy = this.generatorSettingsPage.usesMoxy();
		this.bindingsFileNames = this.generatorSettingsPage.getBindingsFileNames();
		
		IFolder folder = this.javaProject.getProject().getFolder(this.destinationFolder);
		this.createFolderIfNotExist(folder);
		return true;
	}

    @Override
	public boolean canFinish() {
    	return this.generatorSettingsPage.isPageComplete();
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
	
    public boolean getUseMoxy() {
		return this.useMoxy;
	}

    public String[] getBindingsFileNames() {
		return this.bindingsFileNames;
	}

	// ********** internal methods **********

	private ClassesGeneratorWizardPage buildClassesGeneratorPage() {
		return new ClassesGeneratorWizardPage(this.javaProject, this.xmlSchemaName);
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

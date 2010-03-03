/*******************************************************************************
* Copyright (c) 2010 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jaxb.ui.internal.wizards;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.jaxb.ui.internal.JptJaxbUiMessages;
import org.eclipse.osgi.util.NLS;

/**
 *  ClassesGeneratorWizard
 */
public class ClassesGeneratorWizard extends Wizard {	

	private final JpaProject jpaProject;
	private final String xmlSchemaName;

	private String destinationFolder;
	private String targetPackage;
	private String catalog;
	private boolean useMoxy;
	private String[] bindingsFileNames;

	private ClassesGeneratorWizardPage generatorSettingsPage;

	// ********** constructor **********
	
	public ClassesGeneratorWizard(JpaProject jpaProject, String xmlSchemaName) {
		super();
		this.jpaProject = jpaProject;
		this.xmlSchemaName = xmlSchemaName;
		this.setWindowTitle(JptJaxbUiMessages.ClassesGeneratorWizard_title); 
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
		
		IFolder folder = this.jpaProject.getProject().getFolder(this.destinationFolder);
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
		return new ClassesGeneratorWizardPage(this.jpaProject, this.xmlSchemaName);
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

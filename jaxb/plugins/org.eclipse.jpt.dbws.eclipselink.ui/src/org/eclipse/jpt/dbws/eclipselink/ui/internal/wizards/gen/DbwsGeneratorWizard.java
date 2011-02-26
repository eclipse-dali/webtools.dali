/*******************************************************************************
* Copyright (c) 2010, 2011 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.dbws.eclipselink.ui.internal.wizards.gen;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.jpt.dbws.eclipselink.ui.JptDbwsUiPlugin;
import org.eclipse.jpt.dbws.eclipselink.ui.internal.DbwsGeneratorUi;
import org.eclipse.jpt.dbws.eclipselink.ui.internal.JptDbwsUiIcons;
import org.eclipse.jpt.dbws.eclipselink.ui.internal.JptDbwsUiMessages;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWizard;

/**
 *  DbwsGeneratorWizard
 */
public class DbwsGeneratorWizard extends Wizard implements IWorkbenchWizard {

	private IJavaProject javaProject;
	private String builderXmlFile;
	private IStructuredSelection selection;
	
	private WebDynamicProjectWizardPage projectWizardPage;
	private BuilderXmlWizardPage builderXmlWizardPage;

	// Dialog store id constant
	private static final String WIZARD_NAME = DbwsGeneratorWizard.class.getName();
	private static final String DBWS_SECTION_NAME = "DbwsSettings";		//$NON-NLS-1$

	private JdbcDriverWizardPage jdbcDriversPage;
	
	// ********** constructor **********

	public DbwsGeneratorWizard() {
		super();

		this.initialize();
	}
	
	public DbwsGeneratorWizard(IJavaProject javaProject, String builderXmlFile) {
		super();

		this.javaProject = javaProject;
		this.builderXmlFile = builderXmlFile;

		this.initialize();
	}
	
	private void initialize() {
		if(this.getDialogSettings() == null) {
			IDialogSettings dbwsSettings = JptDbwsUiPlugin.instance().getDialogSettings();
			IDialogSettings wizardSettings = dbwsSettings.getSection(DBWS_SECTION_NAME);
			if(wizardSettings == null) {
				wizardSettings = dbwsSettings.addNewSection(DBWS_SECTION_NAME);
			}
			this.setDialogSettings(wizardSettings);
		}
	}

	// ********** IWorkbenchWizard implementation  **********

	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.selection = selection;

		this.setWindowTitle(JptDbwsUiMessages.DbwsGeneratorWizard_title);

		this.setDefaultPageImageDescriptor(JptDbwsUiPlugin.getImageDescriptor(JptDbwsUiIcons.DBWS_GEN_WIZ_BANNER));
		this.setNeedsProgressMonitor(true);
	}

	// ********** IWizard implementation  **********
	
	@Override
	public void addPages() {
		super.addPages();

		if(this.selection != null) {
			// WebDynamicProjectWizardPage
			this.javaProject = this.getJavaProjectFromSelection(this.selection);

			this.projectWizardPage = this.buildWebDynamicProjectPage(this.javaProject);
			this.addPage(this.projectWizardPage);

			// BuilderXmlWizardPage
			IFile builderXmlSelected = BuilderXmlWizardPage.getBuilderXmlFromSelection(this.selection);
			if(builderXmlSelected == null) {
				this.builderXmlWizardPage = this.buildBuilderXmlPage(this.selection);
				this.addPage(this.builderXmlWizardPage);
			}
			else {
				this.builderXmlFile = this.makeRelativeToProjectPath(builderXmlSelected.getFullPath());
			}
		}
		// JdbcDriverWizardPage
		this.jdbcDriversPage = this.buildJdbcDriversPage();
		this.addPage(this.jdbcDriversPage);
	}
	
	@Override
	public boolean performFinish() {
		
		WizardPage currentPage = (WizardPage)getContainer().getCurrentPage();
		if(currentPage != null) {
			if( ! currentPage.isPageComplete()) {
				return false;
			}
		}

		String driverJarList = this.jdbcDriversPage.getDriverJarList();
		this.jdbcDriversPage.finish();	// persist settings

		if(DbwsGeneratorUi.displayOverridingWebContentWarning(this.getShell())) {
			this.generateDbws(driverJarList);
		}
		return true;
	}
    
	// ********** intra-wizard methods **********
    
	public IJavaProject getJavaProject() {
		if(this.projectWizardPage != null) {
			this.javaProject = this.projectWizardPage.getJavaProject();
		}
    	return this.javaProject;
    }

	public String getBuilderXmlPathOrUri() {
		if(this.builderXmlWizardPage != null) {
			IFile xmlFile = this.builderXmlWizardPage.getBuilderXml();
			if(xmlFile != null) {
				return this.makeRelativeToProjectPath(xmlFile.getFullPath());
			}
			else {
				return this.builderXmlWizardPage.getSourceURI();
			}
		}
		return this.builderXmlFile;
	}
	
	// ********** internal methods **********

	private WebDynamicProjectWizardPage buildWebDynamicProjectPage(IJavaProject javaProject) {
		
		WebDynamicProjectWizardPage projectWizardPage = new WebDynamicProjectWizardPage(javaProject);
		projectWizardPage.setTitle(JptDbwsUiMessages.WebDynamicProjectWizardPage_title);
		projectWizardPage.setDescription(JptDbwsUiMessages.WebDynamicProjectWizardPage_desc);
		projectWizardPage.setDestinationLabel(JptDbwsUiMessages.WebDynamicProjectWizardPage_destinationProject);

		return projectWizardPage;
	}

	private BuilderXmlWizardPage buildBuilderXmlPage(IStructuredSelection selection) {
		return new BuilderXmlWizardPage(selection);
	}
	
	private JdbcDriverWizardPage buildJdbcDriversPage() {
		return new JdbcDriverWizardPage(WIZARD_NAME);
	}

	private String makeRelativeToProjectPath(IPath path) {
		IPath relativePath = path.makeRelativeTo(this.getJavaProject().getProject().getFullPath());
		return relativePath.toOSString();
	}
	
	private void generateDbws(String driverJarList) {

		String stageDirName = this.getJavaProject().getProject().getLocation().toOSString();

		WorkspaceJob generateJob = new DbwsGeneratorUi.GenerateDbwsJob(
			this.getJavaProject(),
			this.getBuilderXmlPathOrUri(),
			stageDirName,
			driverJarList
		);
		generateJob.schedule();
	}

	private IJavaProject getJavaProjectFromSelection(IStructuredSelection selection) {
    	if(selection == null) {
    		return null;
    	}
		Object firstElement = selection.getFirstElement();
		if(firstElement instanceof IJavaProject) {
			return (IJavaProject)firstElement;
		}
		else if(firstElement instanceof IResource) {
			IProject project = ((IResource) firstElement).getProject();
			return getJavaProjectFrom(project);
		}
		else if(firstElement instanceof IJavaElement) {
			return ((IJavaElement)firstElement).getJavaProject();
		}
		return null;
    }
    
	private IJavaProject getJavaProjectFrom(IProject project) {
    	return (IJavaProject)((IJavaElement)((IAdaptable)project).getAdapter(IJavaElement.class));
    }
}

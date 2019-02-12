/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.dbws.eclipselink.ui.internal.wizards.gen;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.jpt.dbws.eclipselink.ui.JptDbwsEclipseLinkUiImages;
import org.eclipse.jpt.dbws.eclipselink.ui.JptDbwsEclipseLinkUiMessages;
import org.eclipse.jpt.dbws.eclipselink.ui.internal.DbwsGeneratorUi;
import org.eclipse.jpt.dbws.eclipselink.ui.internal.plugin.JptDbwsEclipseLinkUiPlugin;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWizard;

/**
 *  DbwsGeneratorWizard
 */
public class DbwsGeneratorWizard extends Wizard implements IWorkbenchWizard {

	private IJavaProject javaProject;
	private String builderXmlFile;
	private ResourceManager resourceManager;
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
		if (this.getDialogSettings() == null) {
			this.setDialogSettings(JptDbwsEclipseLinkUiPlugin.instance().getDialogSettings(DBWS_SECTION_NAME));
		}
	}

	// ********** IWorkbenchWizard implementation  **********

	public void init(IWorkbench workbench, IStructuredSelection sel) {
		this.resourceManager = new LocalResourceManager(JFaceResources.getResources(workbench.getDisplay()));
		this.selection = sel;

		this.setWindowTitle(JptDbwsEclipseLinkUiMessages.DBWS_GENERATOR_WIZARD__TITLE);

		this.setDefaultPageImageDescriptor(JptDbwsEclipseLinkUiImages.NEW_WEB_SERVICES_CLIENT_BANNER);
		this.setNeedsProgressMonitor(true);
	}

	// ********** IWizard implementation  **********
	
	@Override
	public void addPages() {
		super.addPages();

		if(this.selection != null) {
			// WebDynamicProjectWizardPage
			this.javaProject = this.getJavaProjectFromSelection();

			this.projectWizardPage = this.buildWebDynamicProjectPage();
			this.addPage(this.projectWizardPage);

			// BuilderXmlWizardPage
			IFile builderXmlSelected = BuilderXmlWizardPage.getBuilderXmlFromSelection(this.selection);
			if(builderXmlSelected == null) {
				this.builderXmlWizardPage = this.buildBuilderXmlPage();
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
			this.scheduleGenerateDbwsJob(driverJarList);
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
			return this.builderXmlWizardPage.getSourceURI();
		}
		return this.builderXmlFile;
	}
	
	// ********** internal methods **********

	private WebDynamicProjectWizardPage buildWebDynamicProjectPage() {
		
		WebDynamicProjectWizardPage page = new WebDynamicProjectWizardPage(this.javaProject);
		page.setTitle(JptDbwsEclipseLinkUiMessages.WEB_DYNAMIC_PROJECT_WIZARD_PAGE__TITLE);
		page.setDescription(JptDbwsEclipseLinkUiMessages.WEB_DYNAMIC_PROJECT_WIZARD_PAGE__DESC);
		page.setDestinationLabel(JptDbwsEclipseLinkUiMessages.WEB_DYNAMIC_PROJECT_WIZARD_PAGE__DESTINATION_PROJECT);

		return page;
	}

	private BuilderXmlWizardPage buildBuilderXmlPage() {
		return new BuilderXmlWizardPage(this.selection, this.resourceManager);
	}
	
	private JdbcDriverWizardPage buildJdbcDriversPage() {
		return new JdbcDriverWizardPage(WIZARD_NAME);
	}

	private String makeRelativeToProjectPath(IPath path) {
		IPath relativePath = path.makeRelativeTo(this.getJavaProject().getProject().getFullPath());
		return relativePath.toOSString();
	}
	
	private void scheduleGenerateDbwsJob(String driverJarList) {

		String stageDirName = this.getJavaProject().getProject().getLocation().toOSString();

		WorkspaceJob generateJob = new DbwsGeneratorUi.GenerateDbwsJob(
			this.getJavaProject(),
			this.getBuilderXmlPathOrUri(),
			stageDirName,
			driverJarList
		);
		generateJob.schedule();
	}

	private IJavaProject getJavaProjectFromSelection() {
    	if(this.selection == null) {
    		return null;
    	}
		Object firstElement = this.selection.getFirstElement();
		if(firstElement instanceof IJavaProject) {
			return (IJavaProject)firstElement;
		}
		else if(firstElement instanceof IResource) {
			IProject project = ((IResource) firstElement).getProject();
			return findJavaProject(project);
		}
		else if(firstElement instanceof IJavaElement) {
			return ((IJavaElement)firstElement).getJavaProject();
		}
		return null;
    }
    
	private IJavaProject findJavaProject(IProject project) {
		IJavaElement javaElement = this.findJavaElement(project);
		return (javaElement == null) ? null : javaElement.getJavaProject();
	}

	private IJavaElement findJavaElement(IResource resource) {
		return (IJavaElement) resource.getAdapter(IJavaElement.class);
	}

	@Override
	public void dispose() {
		this.resourceManager.dispose();
		super.dispose();
	}
}

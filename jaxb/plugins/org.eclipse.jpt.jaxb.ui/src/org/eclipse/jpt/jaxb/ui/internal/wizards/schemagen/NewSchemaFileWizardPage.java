/*******************************************************************************
* Copyright (c) 2010 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jaxb.ui.internal.wizards.schemagen;

import static org.eclipse.jpt.core.internal.operations.JpaFileCreationDataModelProperties.CONTAINER_PATH;
import static org.eclipse.jpt.core.internal.operations.JpaFileCreationDataModelProperties.FILE_NAME;
import static org.eclipse.jpt.core.internal.operations.JpaFileCreationDataModelProperties.PROJECT;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jpt.jaxb.ui.internal.JptJaxbUiMessages;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;
import org.eclipse.ui.internal.ide.IDEWorkbenchMessages;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;

/**
 *  NewSchemaFileWizardPage
 */
public class NewSchemaFileWizardPage extends WizardNewFileCreationPage {
	
	protected IDataModel dataModel; 

	private IStructuredSelection initialSelection;
	
	static public String DEFAULT_SCHEMA_NAME = "NewXMLSchema" + SchemaGeneratorWizard.XSD_EXTENSION;   //$NON-NLS-1$
	
	// ********** constructor **********
	
	public NewSchemaFileWizardPage(String pageName, IStructuredSelection selection, IDataModel dataModel,
			String title, String description) {
		
		super(pageName, selection);
		this.initialSelection = selection;

		this.init(dataModel);
		this.setTitle(title);
		this.setDescription(description);
	}
	
	protected void init(IDataModel dataModel) {
		this.dataModel = dataModel;
		
		IProject project = this.getProjectFromInitialSelection();
		this.dataModel.setProperty(PROJECT, project);
		
		IPath containerPath = (IPath) this.dataModel.getProperty(CONTAINER_PATH);
		if (containerPath != null) {
			this.setContainerFullPath(containerPath);
		}
	}

	// ********** IDialogPage implementation  **********
    @Override
	public void createControl(Composite parent) {
    	super.createControl(parent);
    	
    	this.setAllowExistingResources(true);
		this.setFileName(DEFAULT_SCHEMA_NAME);
    }
	
	// ********** intra-wizard methods **********

	public IProject getProject() {
		return (IProject) this.dataModel.getProperty(PROJECT);
	}

	public IPath getFilePath() {
		return this.getContainerFullPath();
	}

	// ********** validation **********
	
	@Override
	protected boolean validatePage() {
		this.dataModel.setProperty(PROJECT, this.getProjectNamed(this.getContainerName()));
		this.dataModel.setProperty(CONTAINER_PATH, this.getContainerFullPath());
		this.dataModel.setProperty(FILE_NAME, this.getFileName());

		boolean valid = super.validatePage();
		if( ! valid) {
			return valid;
		}
		this.overrideFileExistsWarning();
		
		valid = this.projectIsJavaProject(this.getProject());
		if( ! valid) {
			this.setErrorMessage(JptJaxbUiMessages.NewSchemaFileWizardPage_errorNotJavaProject);
				return valid;
		}
		this.setErrorMessage(null);

		return valid;
	}

	// ********** internal methods **********

	private boolean projectIsJavaProject(IProject project) {
		try {
			return (project != null) && (project.hasNature(JavaCore.NATURE_ID));
		}
		catch (CoreException e) {
			return false;
		}
    }

    private IProject getProjectFromInitialSelection() {
		Object firstElement = initialSelection.getFirstElement();
		if(firstElement instanceof IProject) {
			return (IProject)firstElement;
		}
		else if(firstElement instanceof IResource) {
			return ((IResource) firstElement).getProject();
		}
		else if(firstElement instanceof IJavaElement) {
			return ((IJavaElement)firstElement).getJavaProject().getProject();
		}
		return null;
    }

	private IProject getProjectNamed(String projectName) {
		if(StringTools.stringIsEmpty(projectName)) {
			return null;
		}
		return ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
	}
	
	private String getContainerName() {
		IPath containerPath = this.getContainerFullPath();
		if(containerPath == null) {
			return null;
		}
		String containerName = containerPath.segment(0);
		return containerName;
	}

	private void overrideFileExistsWarning() {
		String existsString= IDEWorkbenchMessages.ResourceGroup_nameExists;
		existsString.toString();
		
		existsString = existsString.substring("''{0}''".length(), existsString.length());    //$NON-NLS-1$
		String message = this.getMessage();
		if(message != null && message.endsWith(existsString)) { 
			this.setMessage(null);
		}
	}

}

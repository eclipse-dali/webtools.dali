/*******************************************************************************
* Copyright (c) 2010, 2011 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jaxb.ui.internal.wizards.schemagen;

import static org.eclipse.jpt.common.core.internal.operations.JptFileCreationDataModelProperties.CONTAINER_PATH;
import static org.eclipse.jpt.common.core.internal.operations.JptFileCreationDataModelProperties.FILE_NAME;
import static org.eclipse.jpt.common.core.internal.operations.JptFileCreationDataModelProperties.PROJECT;
import static org.eclipse.jpt.jaxb.ui.internal.wizards.schemagen.SchemaGeneratorWizard.XSD_EXTENSION;

import java.io.File;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jpt.common.utility.internal.FileTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.jaxb.ui.internal.JptJaxbUiMessages;
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
	
	static public String DEFAULT_SCHEMA_NAME = "NewXMLSchema" + XSD_EXTENSION;   //$NON-NLS-1$
	
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
		
		//TODO move this validation to the dataModel - see MappingFileNewFileWizardPage
		// Validate Project
		valid = this.projectIsJavaProject(this.getProject());
		if( ! valid) {
			this.setErrorMessage(JptJaxbUiMessages.NewSchemaFileWizardPage_errorNotJavaProject);
			return valid;
		}
		// Validate XSD file not exists.
		valid = this.xsdFileNotExists();
		if( ! valid) {
			this.setMessage(JptJaxbUiMessages.NewSchemaFileWizardPage_overwriteExistingSchemas, IMessageProvider.WARNING);
			return true;
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

	private boolean xsdFileNotExists() {
		
		return ! this.xsdFileExists(this.getContainerAbsolutePath().toFile());
	}
	
	private boolean xsdFileExists(File directory) {
		
		if(directory.listFiles() == null) {
			throw new RuntimeException("Could not find directory: " + directory);   //$NON-NLS-1$
		}
		for(File file : directory.listFiles()) {
			if (file.isDirectory()) {
				continue;
			}
			else if(XSD_EXTENSION.equalsIgnoreCase(FileTools.extension(file))) {
				return true;
			}
		}
		return false;
	}

	private IPath getContainerAbsolutePath() {
		IPath projectRelativePath = this.getContainerFullPath().makeRelativeTo(this.getProject().getFullPath());
		IResource directory = (projectRelativePath.isEmpty()) ?
				this.getProject() :
				this.getProject().getFile(projectRelativePath);
		return directory.getLocation();
	}

}

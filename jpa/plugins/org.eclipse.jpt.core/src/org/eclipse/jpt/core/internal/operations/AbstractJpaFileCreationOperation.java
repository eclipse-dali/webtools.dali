/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.operations;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.jem.util.emf.workbench.ProjectUtilities;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.resource.AbstractXmlResourceProvider;
import org.eclipse.wst.common.frameworks.datamodel.AbstractDataModelOperation;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;

public abstract class AbstractJpaFileCreationOperation
	extends AbstractDataModelOperation
	implements JpaFileCreationDataModelProperties
{
	/**
	 * Will be null until folder is created
	 */
	protected IFolder createdSourceFolder;
	
	/**
	 * Will be null until file is created
	 */
	protected IFile createdFile;
	
	
	protected AbstractJpaFileCreationOperation(IDataModel dataModel) {
		super(dataModel);
	}
	
	
	@Override
	public IStatus execute(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		SubMonitor sm = SubMonitor.convert(monitor, 5);
		// Create source folder if it does not exist
		createSourceFolder(sm.newChild(1));
		// Create file
		createFile(sm.newChild(4));
		return OK_STATUS;
	}
	
	protected IProject getProject() throws ExecutionException {
		String projectName = (String) getDataModel().getProperty(PROJECT_NAME);
		IProject project = ProjectUtilities.getProject(projectName);
		if (project == null) {
			throw new ExecutionException("No project name specified"); //$NON-NLS-1$
		}
		return project;
	}
	
	protected JpaProject getJpaProject() throws ExecutionException {
		IProject project = getProject();
		JpaProject jpaProject = JptCorePlugin.getJpaProject(project);
		if (jpaProject == null) {
			throw new ExecutionException("Project does not have JPA content"); //$NON-NLS-1$
		}
		return jpaProject;
	}
	
	/**
	 * This method will return the source folder as specified in the data model. 
	 * It will create the source folder if it does not exist. It will not add
	 * it as a source folder to the project build path if it is not already.
	 * This method may return null.
	 */
	// copied from NewJavaClassOperation
	protected void createSourceFolder(IProgressMonitor monitor) throws ExecutionException {
		// Get the source folder name from the data model
		String folderPath = this.model.getStringProperty(SOURCE_FOLDER);
		IProject project = getProject();
		IFolder folder = project.getWorkspace().getRoot().getFolder(new Path(folderPath));
		// If folder does not exist, create the folder with the specified path
		if (! folder.exists()) {
			try {
				folder.create(true, true, monitor);
			} catch (CoreException e) {
				throw new ExecutionException("Could not create folder", e); //$NON-NLS-1$
			}
		}
		// Return the source folder
		this.createdSourceFolder = folder;
	}
	
	protected void createFile(IProgressMonitor monitor) {
		String filePath = getDataModel().getStringProperty(FILE_PATH);
		IFile newFile = this.createdSourceFolder.getFile(new Path(filePath));
		AbstractXmlResourceProvider resourceProvider = getXmlResourceProvider(newFile);
		try {
			resourceProvider.createFileAndResource(getDataModel(), monitor);
		}
		catch (CoreException e) {
			JptCorePlugin.log(e);
			newFile = null;
		}
		this.createdFile = newFile;
	}
	
	@Override
	public ISchedulingRule getSchedulingRule() {
		try {
			return this.getProject();
		} catch (ExecutionException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	protected abstract AbstractXmlResourceProvider getXmlResourceProvider(IFile file);
}

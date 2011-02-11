/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.operations;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.jem.util.emf.workbench.ProjectUtilities;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.internal.utility.PlatformTools;
import org.eclipse.jpt.core.resource.AbstractXmlResourceProvider;
import org.eclipse.wst.common.componentcore.ComponentCore;
import org.eclipse.wst.common.componentcore.resources.IVirtualFolder;
import org.eclipse.wst.common.frameworks.datamodel.AbstractDataModelOperation;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;

public abstract class AbstractJpaFileCreationOperation
	extends AbstractDataModelOperation
	implements JpaFileCreationDataModelProperties
{
	/**
	 * Will be null until folder is created
	 */
	protected IContainer createdSourceFolder;
	
	/**
	 * Will be null until file is created
	 */
	protected IFile createdFile;
	
	
	protected AbstractJpaFileCreationOperation(IDataModel dataModel) {
		super(dataModel);
	}
	
	
	@Override
	public IStatus execute(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		// Create source folder if it does not exist
		createSourceFolder();
		// Create file
		createFile();
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
	protected void createSourceFolder() throws ExecutionException {
		// Get the source folder name from the data model
		String folderPath = model.getStringProperty(SOURCE_FOLDER);
		IProject project = getProject();
		IContainer container = PlatformTools.getContainer(new Path(folderPath));
		boolean createVirtualReference = ComponentCore.createFolder(project, new Path("")).exists();
		if (container instanceof IFolder) {
			IFolder folder = (IFolder) container;
			// If folder does not exist, create the folder with the specified path
			if (! folder.exists()) {
				try {
					folder.create(true, true, null);
				} catch (CoreException e) {
					throw new ExecutionException("Could not create folder", e); //$NON-NLS-1$
				}
			}
			if (createVirtualReference) {
				IVirtualFolder rootFolder = ComponentCore.createComponent(project).getRootFolder();
				try {
					rootFolder.getFolder(new Path("/")).createLink(container.getProjectRelativePath(), 0, null);
				}
				catch (CoreException ce) {
					JptCorePlugin.log(ce);
				}
			}
		}
		else if (container instanceof IProject) {
			if (createVirtualReference) {
				IVirtualFolder rootFolder = ComponentCore.createComponent(project).getRootFolder();
				try {
					rootFolder.getFolder(new Path("/META-INF")).createLink(container.getProjectRelativePath().append("META-INF"), 0, null);
				}
				catch (CoreException ce) {
					JptCorePlugin.log(ce);
				}
			}
		}
		// Return the source folder
		this.createdSourceFolder = container;
	}
	
	protected void createFile() {
		String filePath = getDataModel().getStringProperty(FILE_PATH);
		IFile newFile = this.createdSourceFolder.getFile(new Path(filePath));
		AbstractXmlResourceProvider resourceProvider = getXmlResourceProvider(newFile);
		try {
			resourceProvider.createFileAndResource(getDataModel());
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

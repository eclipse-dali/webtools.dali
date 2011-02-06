/*******************************************************************************
 * Copyright (c) 2009, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.operations;


import static org.eclipse.jpt.common.core.internal.operations.JptFileCreationDataModelProperties.*;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.jpt.common.core.internal.utility.PlatformTools;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.JptJpaCorePlugin;
import org.eclipse.jpt.jpa.core.resource.AbstractXmlResourceProvider;
import org.eclipse.wst.common.frameworks.datamodel.AbstractDataModelOperation;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;

public abstract class AbstractJpaFileCreationOperation
	extends AbstractDataModelOperation
{
	/**
	 * Will be null until container is created or verified to exist
	 */
	protected IContainer container;
	
	/**
	 * Will be null until file is created or verified to exist
	 */
	protected IFile file;
	
	
	protected AbstractJpaFileCreationOperation(IDataModel dataModel) {
		super(dataModel);
	}
	
	
	@Override
	public IStatus execute(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		SubMonitor sm = SubMonitor.convert(monitor, 5);
		// Create folder if it does not exist
		createContainer(sm.newChild(1));
		// Create file
		createFile(sm.newChild(4));
		return OK_STATUS;
	}
	
	protected IContainer getContainer() throws ExecutionException {
		IPath containerPath = (IPath) getDataModel().getProperty(CONTAINER_PATH);
		IContainer container = PlatformTools.getContainer(containerPath);
		if (container == null) {
			throw new ExecutionException("No container path specified"); //$NON-NLS-1$
		}
		return container;
	}
	
	protected IProject getProject() throws ExecutionException {
		return getContainer().getProject();
	}
	
	protected JpaProject getJpaProject() throws ExecutionException {
		IProject project = getProject();
		JpaProject jpaProject = JptJpaCorePlugin.getJpaProject(project);
		if (jpaProject == null) {
			throw new ExecutionException("Project does not have JPA facet"); //$NON-NLS-1$
		}
		return jpaProject;
	}
	
	/**
	 * This method will create the container as specified in the data model if it does not exist.
	 */
	protected void createContainer(IProgressMonitor monitor) throws ExecutionException {
		IContainer container = getContainer();
		if (! container.exists()) {
			if (container.getType() == IContainer.PROJECT) {
				throw new ExecutionException("Project does not exist");
			}
			else if (container.getType() == IContainer.FOLDER) {
				try {
					((IFolder) container).create(true, true, null);
				}
				catch (CoreException e) {
					throw new ExecutionException("Could not create folder", e); //$NON-NLS-1$
				}
			}
			else {
				throw new ExecutionException("Container is not a project or folder"); //$NON-NLS-1$
			}
		}
		this.container = container;
	}
	
	protected void createFile(IProgressMonitor monitor) throws ExecutionException {
		String fileName = getDataModel().getStringProperty(FILE_NAME);
		IFile newFile = this.container.getFile(new Path(fileName));
		AbstractXmlResourceProvider resourceProvider = getXmlResourceProvider(newFile);
		try {
			resourceProvider.createFileAndResource(getDataModel(), monitor);
		}
		catch (CoreException e) {
			throw new ExecutionException("Could not create file", e);
		}
		this.file = newFile;
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

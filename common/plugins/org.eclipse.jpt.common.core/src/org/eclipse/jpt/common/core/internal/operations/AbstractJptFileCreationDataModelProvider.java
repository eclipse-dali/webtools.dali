/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal.operations;

import java.util.Set;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jpt.common.core.JptCommonCoreMessages;
import org.eclipse.jpt.common.core.internal.plugin.JptCommonCorePlugin;
import org.eclipse.jpt.common.core.internal.utility.PathTools;
import org.eclipse.jpt.common.core.resource.ProjectResourceLocator;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.wst.common.frameworks.datamodel.AbstractDataModelProvider;

public abstract class AbstractJptFileCreationDataModelProvider
	extends AbstractDataModelProvider
	implements JptFileCreationDataModelProperties
{
	protected AbstractJptFileCreationDataModelProvider() {
		super();
	}


	@Override
	public Set<String> getPropertyNames() {
		@SuppressWarnings("unchecked")
		Set<String> propertyNames = super.getPropertyNames();
		propertyNames.add(PROJECT);
		propertyNames.add(CONTAINER_PATH);
		propertyNames.add(FILE_NAME);
		return propertyNames;
	}

	@Override
	public Object getDefaultProperty(String propertyName) {
		if (propertyName.equals(CONTAINER_PATH)) {
			IContainer sourceLocation = getDefaultContainer();
			if (sourceLocation != null && sourceLocation.exists()) {
				return sourceLocation.getFullPath();
			}
		}
		else if (propertyName.equals(FILE_NAME)) {
			return getDefaultFileName();
		}
		return super.getDefaultProperty(propertyName);
	}

	protected abstract String getDefaultFileName();


	// **************** validation *********************************************

	@Override
	public IStatus validate(String propertyName) {
		IStatus status = Status.OK_STATUS;
		if (propertyName.equals(CONTAINER_PATH)
				|| propertyName.equals(FILE_NAME)) {
			status = validateContainerPathAndFileName();
		}
		if (! status.isOK()) {
			return status;
		}
		
		return status;
	}

	protected IStatus validateContainerPathAndFileName() {
		IContainer container = getContainer();
		if (container == null) {
			// verifies container has been specified, but should be unnecessary in most cases.
			// there is almost always a default, and the new file wizard does this validation as well.
			return JptCommonCorePlugin.instance().buildErrorStatus(JptCommonCoreMessages.VALIDATE_CONTAINER_NOT_SPECIFIED);
		}
		String fileName = getStringProperty(FILE_NAME);
		if (StringTools.isBlank(fileName)) {
			// verifies file name has been specified, but should be unnecessary in most cases.
			// there is almost always a default, and the new file wizard does this validation as well.
			return JptCommonCorePlugin.instance().buildErrorStatus(JptCommonCoreMessages.VALIDATE_FILE_NAME_NOT_SPECIFIED);
		}
		if (container.getFile(new Path(fileName)).exists()) {
			// verifies file does not exist, but should be unnecessary in most cases.
			// the new file wizard does this validation as well.
			return JptCommonCorePlugin.instance().buildErrorStatus(JptCommonCoreMessages.VALIDATE_FILE_ALREADY_EXISTS);
		}
		return Status.OK_STATUS;
	}


	// **************** helper methods *****************************************

	protected IPath getContainerPath() {
		return (IPath) this.model.getProperty(CONTAINER_PATH);
	}

	protected IContainer getContainer() {
		IPath containerPath = getContainerPath();
		return (containerPath == null) ? null : PathTools.getContainer(containerPath);
	}

	protected IProject getProject() {
		IContainer container = this.getContainer();
		return (container == null) ? null : container.getProject();
	}
	
	protected IJavaProject getJavaProject() {
		IProject project = getProject();
		return (project == null) ? null : JavaCore.create(project);
	}

	/**
	 * Return a best guess source location for the for the specified project
	 */
	protected IContainer getDefaultContainer() {
		IProject project = (IProject) this.model.getProperty(PROJECT);
		if (project != null) {
			ProjectResourceLocator locator = (ProjectResourceLocator) project.getAdapter(ProjectResourceLocator.class);
			return locator.getDefaultLocation();
		}
		return null;
	}
}

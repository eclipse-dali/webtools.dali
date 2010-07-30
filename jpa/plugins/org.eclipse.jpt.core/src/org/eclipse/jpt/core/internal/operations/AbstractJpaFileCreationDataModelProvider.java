/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.operations;

import java.util.Set;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.internal.JptCoreMessages;
import org.eclipse.jpt.core.internal.utility.PlatformTools;
import org.eclipse.jpt.core.resource.ResourceLocator;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.wst.common.frameworks.datamodel.AbstractDataModelProvider;
import org.eclipse.wst.common.project.facet.core.FacetedProjectFramework;
import org.eclipse.wst.common.project.facet.core.IFacetedProject;
import org.eclipse.wst.common.project.facet.core.ProjectFacetsManager;

public abstract class AbstractJpaFileCreationDataModelProvider
	extends AbstractDataModelProvider
	implements JpaFileCreationDataModelProperties
{
	protected AbstractJpaFileCreationDataModelProvider() {
		super();
	}
	
	
	@Override
	public Set<String> getPropertyNames() {
		@SuppressWarnings("unchecked")
		Set<String> propertyNames = super.getPropertyNames();
		propertyNames.add(PROJECT);
		propertyNames.add(CONTAINER_PATH);
		propertyNames.add(FILE_NAME);
		propertyNames.add(VERSION);
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
		else if (propertyName.equals(VERSION)) {
			return getDefaultVersion();
		}
		return super.getDefaultProperty(propertyName);
	}
	
	protected abstract String getDefaultFileName();
	
	protected abstract String getDefaultVersion();
	
	
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
		
		if (propertyName.equals(CONTAINER_PATH)
				|| propertyName.equals(VERSION)) {
			status = validateVersion();
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
			return new Status(
				IStatus.ERROR, JptCorePlugin.PLUGIN_ID, 
				JptCoreMessages.VALIDATE_CONTAINER_NOT_SPECIFIED);
		}
		IProject project = getProject(container);
		if (! hasJpaFacet(project)) {
			// verifies project has jpa facet
			return new Status(
				IStatus.ERROR, JptCorePlugin.PLUGIN_ID, 
				JptCoreMessages.VALIDATE_PROJECT_NOT_JPA);
		}
		if (! hasSupportedPlatform(project)) {
			// verifies project has platform that supports this file type
			return new Status(
				IStatus.ERROR, JptCorePlugin.PLUGIN_ID,
				JptCoreMessages.VALIDATE_PROJECT_IMPROPER_PLATFORM);
		}
		ResourceLocator resourceLocator = JptCorePlugin.getResourceLocator(project);
		if (resourceLocator != null /* should never be null, but there might be crazy circumstances */
				&& ! resourceLocator.acceptResourceLocation(project, container)) {
			return new Status(
				IStatus.WARNING, JptCorePlugin.PLUGIN_ID,
				JptCoreMessages.VALIDATE_CONTAINER_QUESTIONABLE);
		}
		String fileName = getStringProperty(FILE_NAME);
		if (StringTools.stringIsEmpty(fileName)) {
			// verifies file name has been specified, but should be unnecessary in most cases.
			// there is almost always a default, and the new file wizard does this validation as well.
			return new Status(
				IStatus.ERROR, JptCorePlugin.PLUGIN_ID,
				JptCoreMessages.VALIDATE_FILE_NAME_NOT_SPECIFIED);
		}
		if (container.getFile(new Path(fileName)).exists()) {
			// verifies file does not exist, but should be unnecessary in most cases.
			// the new file wizard does this validation as well.
			return new Status(
				IStatus.ERROR, JptCorePlugin.PLUGIN_ID,
				JptCoreMessages.VALIDATE_FILE_ALREADY_EXISTS);
		}
		return Status.OK_STATUS;
	}
	
	protected IStatus validateVersion() {
		if (getProject() == null) {
			return Status.OK_STATUS;
		}
		String fileVersion = getStringProperty(VERSION);
		if (! fileVersionSupported(fileVersion)) {
			return new Status(
					IStatus.ERROR, JptCorePlugin.PLUGIN_ID,
					JptCoreMessages.VALIDATE_FILE_VERSION_NOT_SUPPORTED);
		}
		try {
			String jpaFacetVersion = getJpaFacetVersion(getProject());
			if (! fileVersionSupportedForFacetVersion(fileVersion, jpaFacetVersion)) {
				return new Status(
						IStatus.ERROR, JptCorePlugin.PLUGIN_ID,
						JptCoreMessages.VALIDATE_FILE_VERSION_NOT_SUPPORTED_FOR_FACET_VERSION);
			}
		}
		catch (CoreException ce) {
			// project should have been validated already, so assume that this will never get hit
			// fall through to final return
		}
		return Status.OK_STATUS;
	}
	
	protected abstract boolean fileVersionSupported(String fileVersion);
	
	protected abstract boolean fileVersionSupportedForFacetVersion(String fileVersion, String jpaFacetVersion);
	
	
	// **************** helper methods *****************************************
	
	protected IPath getContainerPath() {
		return (IPath) this.model.getProperty(CONTAINER_PATH);
	}
	
	protected IContainer getContainer() {
		IPath containerPath = getContainerPath();
		if (containerPath == null) {
			return null; 
		}
		return PlatformTools.getContainer(containerPath);
	}
	
	protected IProject getProject() {
		return getProject(getContainer());
	}
	
	protected IProject getProject(IContainer container) {
		return (container == null) ? null : container.getProject();
	}
	
	protected JpaProject getJpaProject() {
		return getJpaProject(getProject());
	}
	
	protected JpaProject getJpaProject(IProject project) {
		return (project == null) ? null : JptCorePlugin.getJpaProject(project);
	}
	
	/**
	 * Return a best guess source location for the for the specified project
	 */
	protected IContainer getDefaultContainer() {
		IProject project = (IProject) this.model.getProperty(PROJECT);
		if (project != null) {
			return JptCorePlugin.getResourceLocator(project).getDefaultResourceLocation(project);
		}
		return null;
	}
	
	protected boolean hasJpaFacet(IProject project) {
		try {
			return FacetedProjectFramework.hasProjectFacet(project, JptCorePlugin.FACET_ID);
		}
		catch (CoreException ce) {
			return false;
		}
	}
	
	protected String getJpaFacetVersion(IProject project) throws CoreException {
		IFacetedProject fproj = ProjectFacetsManager.create(project);
		return fproj.getProjectFacetVersion(
				ProjectFacetsManager.getProjectFacet(JptCorePlugin.FACET_ID)).getVersionString();
	}
	
	protected boolean hasSupportedPlatform(IProject project) {
		JpaProject jpaProject = JptCorePlugin.getJpaProject(project);
		return (jpaProject != null) && isSupportedPlatformId(jpaProject.getJpaPlatform().getId());
	}
	
	protected boolean isSupportedPlatformId(String id) {
		return true;
	}
}

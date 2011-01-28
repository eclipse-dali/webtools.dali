/*******************************************************************************
 * Copyright (c) 2009, 2011 Oracle. All rights reserved.
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
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jpt.core.JpaFacet;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.internal.JptCoreMessages;
import org.eclipse.jpt.core.resource.ResourceLocator;
import org.eclipse.wst.common.project.facet.core.IFacetedProject;
import org.eclipse.wst.common.project.facet.core.ProjectFacetsManager;

public abstract class AbstractJpaFileCreationDataModelProvider
	extends AbstractJptFileCreationDataModelProvider
	implements JpaFileCreationDataModelProperties
{
	protected AbstractJpaFileCreationDataModelProvider() {
		super();
	}
	
	
	@Override
	public Set<String> getPropertyNames() {
		Set<String> propertyNames = super.getPropertyNames();
		propertyNames.add(VERSION);
		return propertyNames;
	}
	
	@Override
	public Object getDefaultProperty(String propertyName) {
		if (propertyName.equals(VERSION)) {
			return getDefaultVersion();
		}
		return super.getDefaultProperty(propertyName);
	}
	
	protected abstract String getDefaultVersion();
	
	
	// **************** validation *********************************************
	
	@Override
	public IStatus validate(String propertyName) {
		IStatus status = super.validate(propertyName);
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
	
	@Override
	protected IStatus validateContainerPathAndFileName() {
		IStatus status = super.validateContainerPathAndFileName();
		if (! status.isOK()) {
			return status;
		}
		IContainer container = getContainer();
		IProject project = getProject(container);
		if (! JpaFacet.isInstalled(project)) {
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
	
	protected JpaProject getJpaProject() {
		return getJpaProject(getProject());
	}
	
	protected JpaProject getJpaProject(IProject project) {
		return (project == null) ? null : JptCorePlugin.getJpaProject(project);
	}
	
	protected String getJpaFacetVersion(IProject project) throws CoreException {
		IFacetedProject fproj = ProjectFacetsManager.create(project);
		return fproj.getProjectFacetVersion(JpaFacet.FACET).getVersionString();
	}
	
	protected boolean hasSupportedPlatform(IProject project) {
		JpaProject jpaProject = JptCorePlugin.getJpaProject(project);
		return (jpaProject != null) && isSupportedPlatformId(jpaProject.getJpaPlatform().getId());
	}
	
	protected boolean isSupportedPlatformId(@SuppressWarnings("unused") String id) {
		return true;
	}
}

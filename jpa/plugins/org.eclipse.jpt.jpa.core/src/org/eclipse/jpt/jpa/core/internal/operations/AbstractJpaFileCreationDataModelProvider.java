/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.operations;

import java.util.Set;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.common.core.internal.operations.AbstractJptFileCreationDataModelProvider;
import org.eclipse.jpt.common.core.internal.utility.ProjectTools;
import org.eclipse.jpt.common.core.resource.ProjectResourceLocator;
import org.eclipse.jpt.jpa.core.JpaPlatform;
import org.eclipse.jpt.jpa.core.JpaPreferences;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.JpaWorkspace;
import org.eclipse.jpt.jpa.core.internal.JptCoreMessages;
import org.eclipse.jpt.jpa.core.internal.plugin.JptJpaCorePlugin;
import org.eclipse.jpt.jpa.core.platform.JpaPlatformManager;

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
	
	protected final String getDefaultVersion() {
		IProject project = this.getProject();
		if (project == null) {
			return null;
		}
		JpaProject jpaProject = this.getJpaProject_(project);
		JpaPlatform jpaPlatform = (jpaProject != null) ? jpaProject.getJpaPlatform() : this.getJpaPlatform();
		return jpaPlatform.getMostRecentSupportedResourceType(this.getContentType()).getVersion();
	}

	protected abstract IContentType getContentType();

	protected JpaPlatform getJpaPlatform() {
		IProject project = this.getProject();
		if (project == null) {
			return null;
		}
		String jpaPlatformID = JpaPreferences.getJpaPlatformID(project);
		JpaPlatformManager jpaPlatformManager = this.getJpaPlatformManager();
		return (jpaPlatformManager == null) ? null : jpaPlatformManager.getJpaPlatform(jpaPlatformID);
	}

	protected JpaPlatformManager getJpaPlatformManager() {
		JpaWorkspace jpaWorkspace = this.getJpaWorkspace();
		return (jpaWorkspace == null) ? null : jpaWorkspace.getJpaPlatformManager();
	}

	protected JpaWorkspace getJpaWorkspace() {
		return (JpaWorkspace) ResourcesPlugin.getWorkspace().getAdapter(JpaWorkspace.class);
	}
	
	
	
	// **************** validation *********************************************
	
	@Override
	protected IStatus validateContainerPathAndFileName() {
		IStatus status = super.validateContainerPathAndFileName();
		if (! status.isOK()) {
			return status;
		}
		IContainer container = this.getContainer();
		IProject project = container.getProject();
		if ( ! ProjectTools.hasFacet(project, JpaProject.FACET)) {
			return JptJpaCorePlugin.instance().buildErrorStatus(JptCoreMessages.VALIDATE_PROJECT_NOT_JPA);
		}
		if (! hasSupportedPlatform(project)) {
			return JptJpaCorePlugin.instance().buildErrorStatus(JptCoreMessages.VALIDATE_PROJECT_IMPROPER_PLATFORM);
		}
		ProjectResourceLocator resourceLocator = (ProjectResourceLocator) project.getAdapter(ProjectResourceLocator.class);
		if ( ! resourceLocator.resourceLocationIsValid(container)) {
			return JptJpaCorePlugin.instance().buildWarningStatus(JptCoreMessages.VALIDATE_CONTAINER_QUESTIONABLE);
		}
		return Status.OK_STATUS;
	}
	
	
	// **************** helper methods *****************************************
	
	protected JpaProject getJpaProject() {
		return this.getJpaProject(this.getProject());
	}
	
	protected JpaProject getJpaProject(IProject project) {
		return (project == null) ? null : this.getJpaProject_(project);
	}
	
	protected JpaProject getJpaProject_(IProject project) {
		try {
			JpaProject.Reference ref = this.getJpaProjectReference(project);
			return (ref == null) ? null : ref.getValue();
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
			return null;
		}
	}
	
	protected JpaProject.Reference getJpaProjectReference(IProject project) {
		return (JpaProject.Reference) project.getAdapter(JpaProject.Reference.class);
	}
	
	protected boolean hasSupportedPlatform(IProject project) {
		JpaProject jpaProject = this.getJpaProject(project);
		return (jpaProject != null) && this.platformIsSupported(jpaProject.getJpaPlatform());
	}
	
	protected abstract boolean platformIsSupported(JpaPlatform jpaPlatform);
}

/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.facet;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.jpa.core.JpaPlatform;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.JptJpaCoreMessages;
import org.eclipse.jpt.jpa.core.internal.plugin.JptJpaCorePlugin;

public class JpaFacetVersionChangeDataModelProvider
	extends JpaFacetDataModelProvider
{
	/**
	 * required default constructor
	 */
	public JpaFacetVersionChangeDataModelProvider() {
		super();
	}
	
	
	protected String getProjectName() {
		return getStringProperty(FACET_PROJECT_NAME);
	}
	
	protected JpaProject getJpaProject() {
		try {
			JpaProject.Reference ref = this.getJpaProjectReference();
			return (ref == null) ? null : ref.getValue();
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
			return null;
		}
	}

	protected JpaProject.Reference getJpaProjectReference() {
		return (JpaProject.Reference) this.getProject().getAdapter(JpaProject.Reference.class);
	}

	protected IProject getProject() {
		return ResourcesPlugin.getWorkspace().getRoot().getProject(this.getProjectName());
	}
	
	
	// **************** defaults **********************************************
	
	@Override
	protected JpaPlatform.Config getDefaultPlatformConfig() {
		return getJpaProject().getJpaPlatform().getConfig();
	}
	
	@Override
	protected String getDefaultConnection() {
		return getJpaProject().getDataSource().getConnectionProfileName();
	}
	
	@Override
	protected Boolean getDefaultUserWantsToOverrideDefaultCatalog() {
		return Boolean.valueOf(this.getDefaultUserWantsToOverrideDefaultCatalog_());
	}
	
	protected boolean getDefaultUserWantsToOverrideDefaultCatalog_() {
		return this.getJpaProject().getUserOverrideDefaultCatalog() != null;
	}
	
	@Override
	protected String getDefaultCatalogIdentifier() {
		return getJpaProject().getUserOverrideDefaultCatalog();
	}
	
	@Override
	protected Boolean getDefaultUserWantsToOverrideDefaultSchema() {
		return Boolean.valueOf(this.getDefaultUserWantsToOverrideDefaultSchema_());
	}
	
	protected boolean getDefaultUserWantsToOverrideDefaultSchema_() {
		return this.getJpaProject().getUserOverrideDefaultSchema() != null;
	}
	
	@Override
	protected String getDefaultSchemaIdentifier() {
		return getJpaProject().getDefaultSchema();
	}
	
	@Override
	protected Boolean getDefaultDiscoverAnnotatedClasses() {
		return Boolean.valueOf(this.getDefaultDiscoverAnnotatedClasses_());
	}
	
	protected boolean getDefaultDiscoverAnnotatedClasses_() {
		return getJpaProject().discoversAnnotatedClasses();
	}
	
	
	// **************** valid property descriptors ****************************
	
	@Override
	protected Iterable<JpaPlatform.Config> buildValidPlatformConfigs() {
		// add existing platform to list of choices
		Iterable<JpaPlatform.Config> validPlatformConfigs = super.buildValidPlatformConfigs();
		if (! IterableTools.contains(validPlatformConfigs, getDefaultPlatformConfig())) {
			validPlatformConfigs = IterableTools.insert(getDefaultPlatformConfig(), validPlatformConfigs);
		}
		return validPlatformConfigs;
	}
	
	
	// **************** validation ********************************************
	
	@Override
	protected IStatus validatePlatform() {
		IStatus status = super.validatePlatform();
		
		if (status.isOK()) {
			if (! getPlatformConfig().supportsJpaFacetVersion(getProjectFacetVersion())) {
				status = JptJpaCorePlugin.instance().buildErrorStatus(JptJpaCoreMessages.VALIDATE_PLATFORM_DOES_NOT_SUPPORT_FACET_VERSION);
			}
		}
		
		return status;
	}
}

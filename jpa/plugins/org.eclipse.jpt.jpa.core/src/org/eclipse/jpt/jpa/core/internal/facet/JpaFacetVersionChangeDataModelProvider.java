/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
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
import org.eclipse.jpt.jpa.core.JpaPreferences;
import org.eclipse.jpt.jpa.core.JptJpaCoreMessages;
import org.eclipse.jpt.jpa.core.internal.plugin.JptJpaCorePlugin;
import org.eclipse.jpt.jpa.core.platform.JpaPlatformManager;

/**
 * See <code>org.eclipse.jpt.jpa.core/plugin.xml:org.eclipse.wst.common.project.facet.core.facets</code>.
 * <p>
 * <strong>NB:</strong> We could get the default values from the JPA project
 * (instead of from the preferences);
 * but that exposes us to deadlocks (see bug 406352): We have a lock on the
 * faceted project here and would be waiting for the JPA project to be built;
 * meanwhile, the JPA project manager is building the JPA project and could be
 * waiting on a lock on the faceted project....
 */
public class JpaFacetVersionChangeDataModelProvider
	extends JpaFacetDataModelProvider
{
	/**
	 * default constructor used by extension point
	 */
	public JpaFacetVersionChangeDataModelProvider() {
		super();
	}


	// ********** default values **********

	@Override
	protected JpaPlatform.Config getDefaultPlatformConfig() {
		String id = JpaPreferences.getJpaPlatformID(this.getProject());
		return (id == null) ? null : this.getPlatformConfig(id);
	}

	protected JpaPlatform.Config getPlatformConfig(String id) {
		JpaPlatformManager jpaPlatformManager = this.getJpaPlatformManager();
		return (jpaPlatformManager == null) ? null : jpaPlatformManager.getJpaPlatformConfig(id);
	}

	@Override
	protected String getDefaultConnectionName() {
		return JpaPreferences.getConnectionProfileName(this.getProject());
	}

	@Override
	protected Boolean getDefaultUserWantsToOverrideDefaultCatalog() {
		return Boolean.valueOf(this.getDefaultUserWantsToOverrideDefaultCatalog_());
	}

	protected boolean getDefaultUserWantsToOverrideDefaultCatalog_() {
		return this.getDefaultCatalogIdentifier() != null;
	}

	@Override
	protected String getDefaultCatalogIdentifier() {
		return JpaPreferences.getUserOverrideDefaultCatalog(this.getProject());
	}

	@Override
	protected Boolean getDefaultUserWantsToOverrideDefaultSchema() {
		return Boolean.valueOf(this.getDefaultUserWantsToOverrideDefaultSchema_());
	}

	protected boolean getDefaultUserWantsToOverrideDefaultSchema_() {
		return this.getDefaultSchemaIdentifier() != null;
	}

	@Override
	protected String getDefaultSchemaIdentifier() {
		return JpaPreferences.getUserOverrideDefaultSchema(this.getProject());
	}

	@Override
	protected Boolean getDefaultDiscoverAnnotatedClasses() {
		return Boolean.valueOf(this.getDefaultDiscoverAnnotatedClasses_());
	}

	protected boolean getDefaultDiscoverAnnotatedClasses_() {
		return JpaPreferences.getDiscoverAnnotatedClasses(this.getProject());
	}

	protected IProject getProject() {
		return ResourcesPlugin.getWorkspace().getRoot().getProject(this.getProjectName());
	}

	protected String getProjectName() {
		return this.getStringProperty(FACET_PROJECT_NAME);
	}


	// ********** valid property descriptors **********

	@Override
	protected Iterable<JpaPlatform.Config> buildValidPlatformConfigs() {
		// add existing platform to list of choices
		Iterable<JpaPlatform.Config> validPlatformConfigs = super.buildValidPlatformConfigs();
		if (! IterableTools.contains(validPlatformConfigs, this.getDefaultPlatformConfig())) {
			validPlatformConfigs = IterableTools.insert(this.getDefaultPlatformConfig(), validPlatformConfigs);
		}
		return validPlatformConfigs;
	}


	// ********** validation **********

	@Override
	protected IStatus validatePlatform() {
		IStatus status = super.validatePlatform();
		if (status.isOK()) {
			if (! this.getPlatformConfig().supportsJpaFacetVersion(this.getProjectFacetVersion())) {
				status = JptJpaCorePlugin.instance().buildErrorStatus(JptJpaCoreMessages.VALIDATE_PLATFORM_DOES_NOT_SUPPORT_FACET_VERSION);
			}
		}
		return status;
	}
}

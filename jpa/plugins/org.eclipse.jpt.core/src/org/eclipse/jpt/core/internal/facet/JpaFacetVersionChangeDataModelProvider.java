/*******************************************************************************
 *  Copyright (c) 2009, 2010  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.facet;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterables.CompositeIterable;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.internal.JptCoreMessages;
import org.eclipse.jpt.core.platform.JpaPlatformDescription;

public class JpaFacetVersionChangeDataModelProvider
	extends JpaFacetDataModelProvider
	implements JpaFacetDataModelProperties
{
	protected static final IStatus PLATFORM_DOES_NOT_SUPPORT_FACET_VERSION_STATUS = 
			buildErrorStatus(JptCoreMessages.VALIDATE_PLATFORM_DOES_NOT_SUPPORT_FACET_VERSION);
	
	
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
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(getProjectName());
		return JptCorePlugin.getJpaProject(project);
	}
	
	
	// **************** defaults **********************************************
	
	@Override
	protected JpaPlatformDescription getDefaultPlatform() {
		return getJpaProject().getJpaPlatform().getDescription();
	}
	
	@Override
	protected String getDefaultConnection() {
		return getJpaProject().getDataSource().getConnectionProfileName();
	}
	
	@Override
	protected Boolean getDefaultUserWantsToOverrideDefaultCatalog() {
		return getJpaProject().getUserOverrideDefaultCatalog() != null;
	}
	
	@Override
	protected String getDefaultCatalogIdentifier() {
		return getJpaProject().getUserOverrideDefaultCatalog();
	}
	
	@Override
	protected Boolean getDefaultUserWantsToOverrideDefaultSchema() {
		return getJpaProject().getUserOverrideDefaultSchema() != null;
	}
	
	@Override
	protected String getDefaultSchemaIdentifier() {
		return getJpaProject().getDefaultSchema();
	}
	
	@Override
	protected Boolean getDefaultDiscoverAnnotatedClasses() {
		return getJpaProject().discoversAnnotatedClasses();
	}
	
	
	// **************** valid property descriptors ****************************
	
	@Override
	protected Iterable<JpaPlatformDescription> buildValidPlatformDescriptions() {
		// add existing platform to list of choices
		Iterable<JpaPlatformDescription> validPlatformDescs = super.buildValidPlatformDescriptions();
		if (! CollectionTools.contains(validPlatformDescs, getDefaultPlatform())) {
			validPlatformDescs = new CompositeIterable(getDefaultPlatform(), validPlatformDescs);
		}
		return validPlatformDescs;
	}
	
	
	// **************** validation ********************************************
	
	@Override
	protected IStatus validatePlatform() {
		IStatus status = super.validatePlatform();
		
		if (status.isOK()) {
			if (! getPlatform().supportsJpaFacetVersion(getProjectFacetVersion())) {
				status = PLATFORM_DOES_NOT_SUPPORT_FACET_VERSION_STATUS;
			}
		}
		
		return status;
	}
}

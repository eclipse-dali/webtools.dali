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
package org.eclipse.jpt.jpa.core.internal.operations;

import org.eclipse.jpt.jpa.core.JpaFacet;
import org.eclipse.jpt.jpa.core.JpaPlatform;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.JptJpaCorePlugin;
import org.eclipse.jpt.jpa.core.resource.persistence.JPA;
import org.eclipse.jpt.jpa.core.resource.persistence.v2_0.JPA2_0;
import org.eclipse.wst.common.frameworks.datamodel.IDataModelOperation;

public class PersistenceFileCreationDataModelProvider
	extends AbstractJpaFileCreationDataModelProvider
	implements PersistenceFileCreationDataModelProperties
{
	/**
	 * required default constructor
	 */
	public PersistenceFileCreationDataModelProvider() {
		super();
	}
	
	
	@Override
	public IDataModelOperation getDefaultOperation() {
		return new PersistenceFileCreationOperation(getDataModel());
	}
	
	@Override
	protected String getDefaultFileName() {
		return JptJpaCorePlugin.DEFAULT_PERSISTENCE_XML_RUNTIME_PATH.lastSegment();
	}
	
	@Override
	protected String getDefaultVersion() {
		if (getProject() == null) {
			return null;
		}
		JpaPlatform jpaPlatform;
		JpaProject jpaProject = getJpaProject();
		jpaPlatform = (jpaProject == null) 
				? JptJpaCorePlugin.getJpaPlatformManager().buildJpaPlatformImplementation(getProject()) 
				: jpaProject.getJpaPlatform();
		return jpaPlatform.getMostRecentSupportedResourceType(JptJpaCorePlugin.PERSISTENCE_XML_CONTENT_TYPE).getVersion();
	}
	
	
	// **************** validation *********************************************
	
	@Override
	protected boolean fileVersionSupported(String fileVersion) {
		return (fileVersion.equals(JPA.SCHEMA_VERSION)
				|| fileVersion.equals(JPA2_0.SCHEMA_VERSION));
	}
	
	@Override
	protected boolean fileVersionSupportedForFacetVersion(String fileVersion, String jpaFacetVersion) {
		if (jpaFacetVersion.equals(JpaFacet.VERSION_1_0.getVersionString())
				&& fileVersion.equals(JPA2_0.SCHEMA_VERSION)) {
			return false;
		}
		return true;
	}
}

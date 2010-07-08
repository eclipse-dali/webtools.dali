/*******************************************************************************
 *  Copyright (c) 2009  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.operations;

import org.eclipse.core.runtime.Path;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.resource.persistence.JPA;
import org.eclipse.jpt.core.resource.persistence.v2_0.JPA2_0;
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
	protected String getDefaultFilePath() {
		return new Path(JptCorePlugin.DEFAULT_PERSISTENCE_XML_FILE_PATH).toPortableString();
	}
	
	@Override
	protected String getDefaultVersion() {
		if (getProject() == null) {
			return null;
		}
		return JptCorePlugin.getJpaPlatform(getProject()).getMostRecentSupportedResourceType(
				JptCorePlugin.PERSISTENCE_XML_CONTENT_TYPE).getVersion();
	}
	
	
	// **************** validation *********************************************
	
	@Override
	protected boolean fileVersionSupported(String fileVersion) {
		return (fileVersion.equals(JPA.SCHEMA_VERSION)
				|| fileVersion.equals(JPA2_0.SCHEMA_VERSION));
	}
	
	@Override
	protected boolean fileVersionSupportedForFacetVersion(String fileVersion, String jpaFacetVersion) {
		if (jpaFacetVersion.equals(JptCorePlugin.JPA_FACET_VERSION_1_0)
				&& fileVersion.equals(JPA2_0.SCHEMA_VERSION)) {
			return false;
		}
		return true;
	}

}

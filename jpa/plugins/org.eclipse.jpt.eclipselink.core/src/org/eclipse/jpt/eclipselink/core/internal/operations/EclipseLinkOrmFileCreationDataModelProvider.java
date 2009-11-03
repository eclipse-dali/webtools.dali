/*******************************************************************************
 *  Copyright (c) 2008, 2009 Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.operations;

import org.eclipse.core.runtime.Path;
import org.eclipse.jpt.core.internal.operations.OrmFileCreationDataModelProvider;
import org.eclipse.jpt.eclipselink.core.internal.EclipseLinkJpaPlatformProvider;
import org.eclipse.jpt.eclipselink.core.internal.JptEclipseLinkCorePlugin;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.EclipseLink1_1JpaPlatformProvider;
import org.eclipse.jpt.eclipselink.core.internal.v2_0.EclipseLink2_0JpaPlatformProvider;
import org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLink;
import org.eclipse.jpt.eclipselink.core.resource.orm.v1_1.EclipseLink1_1;
import org.eclipse.jpt.eclipselink.core.resource.orm.v2_0.EclipseLink2_0;
import org.eclipse.wst.common.frameworks.datamodel.IDataModelOperation;

public class EclipseLinkOrmFileCreationDataModelProvider
	extends OrmFileCreationDataModelProvider
{
	/**
	 * required default constructor
	 */
	public EclipseLinkOrmFileCreationDataModelProvider() {
		super();
	}
	
	
	@Override
	public IDataModelOperation getDefaultOperation() {
		return new EclipseLinkOrmFileCreationOperation(getDataModel());
	}
	
	@Override
	public Object getDefaultProperty(String propertyName) {
		if (propertyName.equals(FILE_PATH)) {
			return new Path(JptEclipseLinkCorePlugin.DEFAULT_ECLIPSELINK_ORM_XML_FILE_PATH).toPortableString();
		}
		return super.getDefaultProperty(propertyName);
	}
	
	@Override
	protected String getDefaultVersion() {
		if (getProject() == null) {
			return null;
		}
		return getJpaProject().getJpaPlatform().getMostRecentSupportedResourceType(
				JptEclipseLinkCorePlugin.ECLIPSELINK_ORM_XML_CONTENT_TYPE).getVersion();
	}
	
	@Override
	protected boolean fileVersionSupported(String fileVersion) {
		return fileVersion.equals(EclipseLink.SCHEMA_VERSION)
				|| fileVersion.equals(EclipseLink1_1.SCHEMA_VERSION)
				|| fileVersion.equals(EclipseLink2_0.SCHEMA_VERSION);
	}
	
	@Override
	protected boolean fileVersionSupportedForFacetVersion(String fileVersion, String jpaFacetVersion) {
		// assume that platform has been policed for facet version already
		return true;
	}
	
	@Override
	protected boolean isSupportedPlatformId(String id) {
		return id.equals(EclipseLinkJpaPlatformProvider.ID) 
				|| id.equals(EclipseLink1_1JpaPlatformProvider.ID)
				|| id.equals(EclipseLink2_0JpaPlatformProvider.ID);
	}
}

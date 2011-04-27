/*******************************************************************************
 *  Copyright (c) 2008, 2010 Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.operations;

import org.eclipse.jpt.jpa.core.JptJpaCorePlugin;
import org.eclipse.jpt.jpa.core.internal.operations.OrmFileCreationDataModelProvider;
import org.eclipse.jpt.jpa.core.platform.JpaPlatformDescription;
import org.eclipse.jpt.jpa.eclipselink.core.JptJpaEclipseLinkCorePlugin;
import org.eclipse.jpt.jpa.eclipselink.core.platform.EclipseLinkPlatform;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLink;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v1_1.EclipseLink1_1;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v1_2.EclipseLink1_2;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_0.EclipseLink2_0;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.EclipseLink2_1;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.EclipseLink2_2;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.EclipseLink2_3;
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
	protected String getDefaultFileName() {
		return JptJpaEclipseLinkCorePlugin.DEFAULT_ECLIPSELINK_ORM_XML_RUNTIME_PATH.lastSegment();
	}
	
	@Override
	protected String getDefaultVersion() {
		if (getProject() == null) {
			return null;
		}
		try {
			return getJpaProject().getJpaPlatform().getMostRecentSupportedResourceType(
					JptJpaEclipseLinkCorePlugin.ECLIPSELINK_ORM_XML_CONTENT_TYPE).getVersion();
		}
		catch (IllegalArgumentException iae) {
			// eclipselink content not supported for project
			return null;
		}
	}
	
	@Override
	protected boolean fileVersionSupported(String fileVersion) {
		return fileVersion.equals(EclipseLink.SCHEMA_VERSION)
				|| fileVersion.equals(EclipseLink1_1.SCHEMA_VERSION)
				|| fileVersion.equals(EclipseLink1_2.SCHEMA_VERSION)
				|| fileVersion.equals(EclipseLink2_0.SCHEMA_VERSION)
				|| fileVersion.equals(EclipseLink2_1.SCHEMA_VERSION)
				|| fileVersion.equals(EclipseLink2_2.SCHEMA_VERSION)
				|| fileVersion.equals(EclipseLink2_3.SCHEMA_VERSION);
	}
	
	@Override
	protected boolean fileVersionSupportedForFacetVersion(String fileVersion, String jpaFacetVersion) {
		// assume that platform has been policed for facet version already
		return true;
	}
	
	@Override
	protected boolean isSupportedPlatformId(String id) {
		JpaPlatformDescription platform = JptJpaCorePlugin.getJpaPlatformManager().getJpaPlatform(id);
		if (platform == null) {
			return false;
		}
		return EclipseLinkPlatform.GROUP.equals(platform.getGroup());
	}
}

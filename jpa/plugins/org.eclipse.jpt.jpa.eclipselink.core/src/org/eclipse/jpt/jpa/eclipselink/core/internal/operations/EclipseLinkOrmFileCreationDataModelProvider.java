/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.operations;

import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.jpa.core.JpaPlatform;
import org.eclipse.jpt.jpa.core.internal.operations.OrmFileCreationDataModelProvider;
import org.eclipse.jpt.jpa.eclipselink.core.platform.EclipseLinkPlatform;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLink;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v1_1.EclipseLink1_1;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v1_2.EclipseLink1_2;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_0.EclipseLink2_0;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.EclipseLink2_1;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.EclipseLink2_2;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.EclipseLink2_3;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.EclipseLink2_4;
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
		return XmlEntityMappings.DEFAULT_RUNTIME_PATH.lastSegment();
	}
	
	@Override
	protected IContentType getContentType() {
		return XmlEntityMappings.CONTENT_TYPE;
	}
	
	@Override
	protected boolean fileVersionSupported(String fileVersion) {
		return ArrayTools.contains(ECLIPSE_LINK_SCHEMA_VERSIONS, fileVersion);
	}

	protected static final String[] ECLIPSE_LINK_SCHEMA_VERSIONS = new String[] {
		EclipseLink.SCHEMA_VERSION,
		EclipseLink1_1.SCHEMA_VERSION,
		EclipseLink1_2.SCHEMA_VERSION,
		EclipseLink2_0.SCHEMA_VERSION,
		EclipseLink2_1.SCHEMA_VERSION,
		EclipseLink2_2.SCHEMA_VERSION,
		EclipseLink2_3.SCHEMA_VERSION,
		EclipseLink2_4.SCHEMA_VERSION
	};
	
	@Override
	protected boolean fileVersionSupportedForFacetVersion(String fileVersion, String jpaFacetVersion) {
		// assume that platform has been policed for facet version already
		return true;
	}
	
	@Override
	protected boolean platformIsSupported(JpaPlatform jpaPlatform) {
		return jpaPlatform.getDescription().getGroupDescription().equals(EclipseLinkPlatform.GROUP);
	}
}

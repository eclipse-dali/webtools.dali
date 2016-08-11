/*******************************************************************************
 * Copyright (c) 2008, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.context.persistence;

import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnitProperties;

/**
 * EclipseLink schema generation
 */
@SuppressWarnings("nls")
public interface EclipseLinkSchemaGeneration
	extends PersistenceUnitProperties
{
	EclipseLinkDdlGenerationType getDdlGenerationType();
	void setDdlGenerationType(EclipseLinkDdlGenerationType ddlGenerationType);
		String DDL_GENERATION_TYPE_PROPERTY = "ddlGenerationType";
		// EclipseLink key string
		String ECLIPSELINK_DDL_GENERATION_TYPE = "eclipselink.ddl-generation";
	EclipseLinkDdlGenerationType getDefaultDdlGenerationType();
		EclipseLinkDdlGenerationType DEFAULT_DDL_GENERATION_TYPE = EclipseLinkDdlGenerationType.none;

	EclipseLinkOutputMode getOutputMode();
	void setOutputMode(EclipseLinkOutputMode outputMode); // put
		String OUTPUT_MODE_PROPERTY = "outputMode";
		// EclipseLink key string
		String ECLIPSELINK_DDL_GENERATION_OUTPUT_MODE = "eclipselink.ddl-generation.output-mode";
	EclipseLinkOutputMode getDefaultOutputMode();
		EclipseLinkOutputMode DEFAULT_OUTPUT_MODE = null;		// No Default

	String getCreateFileName();
	void setCreateFileName(String createFileName);
		String CREATE_FILE_NAME_PROPERTY = "createFileName";
		// EclipseLink key string
		String ECLIPSELINK_CREATE_FILE_NAME = "eclipselink.create-ddl-jdbc-file-name";
	String getDefaultCreateFileName();
		String DEFAULT_CREATE_FILE_NAME = "createDDL.sql";
	
	String getDropFileName();
	void setDropFileName(String dropFileName);
		String DROP_FILE_NAME_PROPERTY = "dropFileName";
		// EclipseLink key string
		String ECLIPSELINK_DROP_FILE_NAME = "eclipselink.drop-ddl-jdbc-file-name";
	String getDefaultDropFileName();
		String DEFAULT_DROP_FILE_NAME = "dropDDL.sql";
		
	String getApplicationLocation();
	void setApplicationLocation(String applicationLocation);
		String APPLICATION_LOCATION_PROPERTY = "applicationLocation";
		// EclipseLink key string
		String ECLIPSELINK_APPLICATION_LOCATION = "eclipselink.application-location";
	String getDefaultApplicationLocation();
		String DEFAULT_APPLICATION_LOCATION = null;
}

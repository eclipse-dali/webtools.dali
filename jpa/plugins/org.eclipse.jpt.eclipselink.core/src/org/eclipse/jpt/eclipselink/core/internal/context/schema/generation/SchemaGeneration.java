/*******************************************************************************
* Copyright (c) 2008 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.context.schema.generation;

import org.eclipse.jpt.eclipselink.core.internal.context.PersistenceUnitProperties;

/**
 *  SchemaGeneration
 */
public interface SchemaGeneration extends PersistenceUnitProperties
{
	DdlGenerationType getDefaultDdlGenerationType();
	DdlGenerationType getDdlGenerationType();
	void setDdlGenerationType(DdlGenerationType ddlGenerationType);
		static final String DDL_GENERATION_TYPE_PROPERTY = "ddlGenerationTypeProperty";
		// EclipseLink key string
		static final String ECLIPSELINK_DDL_GENERATION_TYPE = "eclipselink.ddl-generation";
		static final DdlGenerationType DEFAULT_SCHEMA_GENERATION_DDL_GENERATION_TYPE = DdlGenerationType.none;

	OutputMode getDefaultOutputMode();
	OutputMode getOutputMode();
	void setOutputMode(OutputMode outputMode); // put
		static final String OUTPUT_MODE_PROPERTY = "outputModeProperty";
		// EclipseLink key string
		static final String ECLIPSELINK_DDL_GENERATION_OUTPUT_MODE = "eclipselink.ddl-generation.output-mode";
		static final OutputMode DEFAULT_SCHEMA_GENERATION_OUTPUT_MODE = null;		// No Default

	String getDefaultCreateFileName();
	String getCreateFileName();
	void setCreateFileName(String createFileName);
		static final String CREATE_FILE_NAME_PROPERTY = "createFileNameProperty";
		// EclipseLink key string
		static final String ECLIPSELINK_CREATE_FILE_NAME = "eclipselink.create-ddl-jdbc-file-name";
		static final String DEFAULT_SCHEMA_GENERATION_CREATE_FILE_NAME = "createDDL.jdbc";
	
	String getDefaultDropFileName();
	String getDropFileName();
	void setDropFileName(String dropFileName);
		static final String DROP_FILE_NAME_PROPERTY = "dropFileNameProperty";
		// EclipseLink key string
		static final String ECLIPSELINK_DROP_FILE_NAME = "eclipselink.drop-ddl-jdbc-file-name";
		static final String DEFAULT_SCHEMA_GENERATION_DROP_FILE_NAME = "dropDDL.jdbc";
		
	String getDefaultApplicationLocation();
	String getApplicationLocation();
	void setApplicationLocation(String applicationLocation);
		static final String APPLICATION_LOCATION_PROPERTY = "applicationLocationProperty";
		// EclipseLink key string
		static final String ECLIPSELINK_APPLICATION_LOCATION = "eclipselink.application-location";
		static final String DEFAULT_SCHEMA_GENERATION_APPLICATION_LOCATION = ".";
	
}

/*******************************************************************************
* Copyright (c) 2008, 2010 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.eclipselink.core.context.persistence.schema.generation;

import org.eclipse.jpt.core.context.persistence.PersistenceUnitProperties;

/**
 *  SchemaGeneration
 */
public interface SchemaGeneration extends PersistenceUnitProperties
{
	DdlGenerationType getDefaultDdlGenerationType();
	DdlGenerationType getDdlGenerationType();
	void setDdlGenerationType(DdlGenerationType ddlGenerationType);
		static final String DDL_GENERATION_TYPE_PROPERTY = "ddlGenerationType"; //$NON-NLS-1$
		// EclipseLink key string
		static final String ECLIPSELINK_DDL_GENERATION_TYPE = "eclipselink.ddl-generation"; //$NON-NLS-1$
		static final DdlGenerationType DEFAULT_SCHEMA_GENERATION_DDL_GENERATION_TYPE = DdlGenerationType.none;

	OutputMode getDefaultOutputMode();
	OutputMode getOutputMode();
	void setOutputMode(OutputMode outputMode); // put
		static final String OUTPUT_MODE_PROPERTY = "outputMode"; //$NON-NLS-1$
		// EclipseLink key string
		static final String ECLIPSELINK_DDL_GENERATION_OUTPUT_MODE = "eclipselink.ddl-generation.output-mode"; //$NON-NLS-1$
		static final OutputMode DEFAULT_SCHEMA_GENERATION_OUTPUT_MODE = null;		// No Default

	String getDefaultCreateFileName();
	String getCreateFileName();
	void setCreateFileName(String createFileName);
		static final String CREATE_FILE_NAME_PROPERTY = "createFileName"; //$NON-NLS-1$
		// EclipseLink key string
		static final String ECLIPSELINK_CREATE_FILE_NAME = "eclipselink.create-ddl-jdbc-file-name"; //$NON-NLS-1$
		static final String DEFAULT_SCHEMA_GENERATION_CREATE_FILE_NAME = "createDDL.jdbc"; //$NON-NLS-1$
	
	String getDefaultDropFileName();
	String getDropFileName();
	void setDropFileName(String dropFileName);
		static final String DROP_FILE_NAME_PROPERTY = "dropFileName"; //$NON-NLS-1$
		// EclipseLink key string
		static final String ECLIPSELINK_DROP_FILE_NAME = "eclipselink.drop-ddl-jdbc-file-name"; //$NON-NLS-1$
		static final String DEFAULT_SCHEMA_GENERATION_DROP_FILE_NAME = "dropDDL.jdbc"; //$NON-NLS-1$
		
	String getDefaultApplicationLocation();
	String getApplicationLocation();
	void setApplicationLocation(String applicationLocation);
		static final String APPLICATION_LOCATION_PROPERTY = "applicationLocation"; //$NON-NLS-1$
		// EclipseLink key string
		static final String ECLIPSELINK_APPLICATION_LOCATION = "eclipselink.application-location"; //$NON-NLS-1$
		static final String DEFAULT_SCHEMA_GENERATION_APPLICATION_LOCATION = null; //$NON-NLS-1$
	
}

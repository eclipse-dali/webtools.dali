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

import org.eclipse.jpt.common.utility.closure.BiClosure;
import org.eclipse.jpt.common.utility.internal.closure.BiClosureAdapter;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnitProperties;

/**
 * EclipseLink schema generation
 */
@SuppressWarnings("nls")
public interface EclipseLinkSchemaGeneration
	extends PersistenceUnitProperties
{
	EclipseLinkDdlGenerationType getDefaultDdlGenerationType();
	EclipseLinkDdlGenerationType getDdlGenerationType();
	void setDdlGenerationType(EclipseLinkDdlGenerationType ddlGenerationType);
		String DDL_GENERATION_TYPE_PROPERTY = "ddlGenerationType";
		// EclipseLink key string
		String ECLIPSELINK_DDL_GENERATION_TYPE = "eclipselink.ddl-generation";
		EclipseLinkDdlGenerationType DEFAULT_SCHEMA_GENERATION_DDL_GENERATION_TYPE = EclipseLinkDdlGenerationType.none;
		Transformer<EclipseLinkSchemaGeneration, EclipseLinkDdlGenerationType> DDL_GENERATION_TYPE_TRANSFORMER = new DdlGenerationTypeTransformer();
		class DdlGenerationTypeTransformer
			extends TransformerAdapter<EclipseLinkSchemaGeneration, EclipseLinkDdlGenerationType>
		{
			@Override
			public EclipseLinkDdlGenerationType transform(EclipseLinkSchemaGeneration logging) {
				return logging.getDdlGenerationType();
			}
		}
	
		BiClosure<EclipseLinkSchemaGeneration, EclipseLinkDdlGenerationType> SET_DDL_GENERATION_TYPE_CLOSURE = new SetDdlGenerationTypeClosure();
		class SetDdlGenerationTypeClosure
			extends BiClosureAdapter<EclipseLinkSchemaGeneration, EclipseLinkDdlGenerationType>
		{
			@Override
			public void execute(EclipseLinkSchemaGeneration logging, EclipseLinkDdlGenerationType timestamp) {
				logging.setDdlGenerationType(timestamp);
			}
		}

	EclipseLinkOutputMode getDefaultOutputMode();
	EclipseLinkOutputMode getOutputMode();
	void setOutputMode(EclipseLinkOutputMode outputMode); // put
		String OUTPUT_MODE_PROPERTY = "outputMode";
		// EclipseLink key string
		String ECLIPSELINK_DDL_GENERATION_OUTPUT_MODE = "eclipselink.ddl-generation.output-mode";
		EclipseLinkOutputMode DEFAULT_SCHEMA_GENERATION_OUTPUT_MODE = null;		// No Default
		Transformer<EclipseLinkSchemaGeneration, EclipseLinkOutputMode> OUTPUT_MODE_TRANSFORMER = new OutputModeTransformer();
		class OutputModeTransformer
			extends TransformerAdapter<EclipseLinkSchemaGeneration, EclipseLinkOutputMode>
		{
			@Override
			public EclipseLinkOutputMode transform(EclipseLinkSchemaGeneration logging) {
				return logging.getOutputMode();
			}
		}
	
		BiClosure<EclipseLinkSchemaGeneration, EclipseLinkOutputMode> SET_OUTPUT_MODE_CLOSURE = new SetOutputModeClosure();
		class SetOutputModeClosure
			extends BiClosureAdapter<EclipseLinkSchemaGeneration, EclipseLinkOutputMode>
		{
			@Override
			public void execute(EclipseLinkSchemaGeneration logging, EclipseLinkOutputMode mode) {
				logging.setOutputMode(mode);
			}
		}

	String getDefaultCreateFileName();
	String getCreateFileName();
	void setCreateFileName(String createFileName);
		String CREATE_FILE_NAME_PROPERTY = "createFileName";
		// EclipseLink key string
		String ECLIPSELINK_CREATE_FILE_NAME = "eclipselink.create-ddl-jdbc-file-name";
		String DEFAULT_SCHEMA_GENERATION_CREATE_FILE_NAME = "createDDL.sql";
	
	String getDefaultDropFileName();
	String getDropFileName();
	void setDropFileName(String dropFileName);
		String DROP_FILE_NAME_PROPERTY = "dropFileName";
		// EclipseLink key string
		String ECLIPSELINK_DROP_FILE_NAME = "eclipselink.drop-ddl-jdbc-file-name";
		String DEFAULT_SCHEMA_GENERATION_DROP_FILE_NAME = "dropDDL.sql";
		
	String getDefaultApplicationLocation();
	String getApplicationLocation();
	void setApplicationLocation(String applicationLocation);
		String APPLICATION_LOCATION_PROPERTY = "applicationLocation";
		// EclipseLink key string
		String ECLIPSELINK_APPLICATION_LOCATION = "eclipselink.application-location";
		String DEFAULT_SCHEMA_GENERATION_APPLICATION_LOCATION = null;
}

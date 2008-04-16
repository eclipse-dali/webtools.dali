/*******************************************************************************
* Copyright (c) 2008 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.context;

import org.eclipse.jpt.eclipselink.core.internal.context.caching.Caching;
import org.eclipse.jpt.eclipselink.core.internal.context.connection.Connection;
import org.eclipse.jpt.eclipselink.core.internal.context.customization.Customization;
import org.eclipse.jpt.eclipselink.core.internal.context.logging.Logging;
import org.eclipse.jpt.eclipselink.core.internal.context.options.Options;
import org.eclipse.jpt.eclipselink.core.internal.context.schema.generation.SchemaGeneration;

/**
 *  EclipseLinkProperties
 */
public interface EclipseLinkProperties extends PersistenceUnitProperties
{
	Caching getCaching();
		static final String CACHING_PROPERTY = "cachingProperty";
		
	Connection getConnection();
	static final String CONNECTION_PROPERTY = "connectionProperty";
	
	Customization getCustomization();
		static final String CUSTOMIZATION_PROPERTY = "customizationProperty";
		
		Logging getLogging();
			static final String LOGGING_PROPERTY = "loggingProperty";
			
		Options getOptions();
			static final String OPTIONS_PROPERTY = "optionsProperty";
		
	SchemaGeneration getSchemaGeneration();
		static final String SCHEMA_GENERATION_PROPERTY = "schemaGenerationProperty";
	
}

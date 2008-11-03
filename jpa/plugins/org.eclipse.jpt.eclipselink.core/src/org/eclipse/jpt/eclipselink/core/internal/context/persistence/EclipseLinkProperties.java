/*******************************************************************************
* Copyright (c) 2008 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.context.persistence;

import org.eclipse.jpt.eclipselink.core.internal.context.persistence.caching.Caching;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.connection.Connection;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.customization.Customization;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.general.GeneralProperties;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.logging.Logging;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.options.Options;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.schema.generation.SchemaGeneration;

/**
 *  EclipseLinkProperties
 */
public interface EclipseLinkProperties extends PersistenceUnitProperties
{
	GeneralProperties getGeneralProperties();
		static final String GENERAL_PROPERTY = "generalProperties";

	Connection getConnection();
		static final String CONNECTION_PROPERTY = "connectionProperty";
	
	Customization getCustomization();
		static final String CUSTOMIZATION_PROPERTY = "customizationProperty";
	
	Caching getCaching();
		static final String CACHING_PROPERTY = "cachingProperty";
		
	Logging getLogging();
		static final String LOGGING_PROPERTY = "loggingProperty";
		
	Options getOptions();
		static final String OPTIONS_PROPERTY = "optionsProperty";
		
	SchemaGeneration getSchemaGeneration();
		static final String SCHEMA_GENERATION_PROPERTY = "schemaGenerationProperty";
	
}

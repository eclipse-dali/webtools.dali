/*******************************************************************************
* Copyright (c) 2009, 2010 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.eclipselink.core.v2_0.context.persistence.logging;

import org.eclipse.jpt.eclipselink.core.context.persistence.logging.Logging;
import org.eclipse.jpt.eclipselink.core.context.persistence.logging.LoggingLevel;
/**
 *  Logging2_0
 */
public interface Logging2_0 extends Logging
{
	public static final String CATEGORY_PREFIX_ = "eclipselink.logging.level."; //$NON-NLS-1$

	LoggingLevel getCategoriesDefaultLevel();
	LoggingLevel getLevel(String category);
	void setLevel(String category, LoggingLevel level);
	void setDefaultLevel(LoggingLevel level);
	static final String CATEGORIES_DEFAULT_LOGGING_PROPERTY = "categoriesDefaultLoggingLevel"; //$NON-NLS-1$

		static final String SQL_CATEGORY_LOGGING_PROPERTY = "sqlLoggingLevel"; //$NON-NLS-1$
		static final String ECLIPSELINK_SQL_CATEGORY_LOGGING_LEVEL = CATEGORY_PREFIX_ + "sql"; //$NON-NLS-1$
	
		static final String TRANSACTION_CATEGORY_LOGGING_PROPERTY = "transactionLoggingLevel"; //$NON-NLS-1$
		static final String ECLIPSELINK_TRANSACTION_CATEGORY_LOGGING_LEVEL = CATEGORY_PREFIX_ + "transaction"; //$NON-NLS-1$
	
		static final String EVENT_CATEGORY_LOGGING_PROPERTY = "eventLoggingLevel"; //$NON-NLS-1$
		static final String ECLIPSELINK_EVENT_CATEGORY_LOGGING_LEVEL = CATEGORY_PREFIX_ + "event"; //$NON-NLS-1$
	
		static final String CONNECTION_CATEGORY_LOGGING_PROPERTY = "connectionLoggingLevel"; //$NON-NLS-1$
		static final String ECLIPSELINK_CONNECTION_CATEGORY_LOGGING_LEVEL = CATEGORY_PREFIX_ + "connection"; //$NON-NLS-1$
	
		static final String QUERY_CATEGORY_LOGGING_PROPERTY = "queryLoggingLevel"; //$NON-NLS-1$
		static final String ECLIPSELINK_QUERY_CATEGORY_LOGGING_LEVEL = CATEGORY_PREFIX_ + "query"; //$NON-NLS-1$
	
		static final String CACHE_CATEGORY_LOGGING_PROPERTY = "cacheLoggingLevel"; //$NON-NLS-1$
		static final String ECLIPSELINK_CACHE_CATEGORY_LOGGING_LEVEL = CATEGORY_PREFIX_ + "cache"; //$NON-NLS-1$
	
		static final String PROPAGATION_CATEGORY_LOGGING_PROPERTY = "propagationLoggingLevel"; //$NON-NLS-1$
		static final String ECLIPSELINK_PROPAGATION_CATEGORY_LOGGING_LEVEL = CATEGORY_PREFIX_ + "propagation"; //$NON-NLS-1$
	
		static final String SEQUENCING_CATEGORY_LOGGING_PROPERTY = "sequencingLoggingLevel"; //$NON-NLS-1$
		static final String ECLIPSELINK_SEQUENCING_CATEGORY_LOGGING_LEVEL = CATEGORY_PREFIX_ + "sequencing"; //$NON-NLS-1$
	
		static final String EJB_CATEGORY_LOGGING_PROPERTY = "ejbLoggingLevel"; //$NON-NLS-1$
		static final String ECLIPSELINK_EJB_CATEGORY_LOGGING_LEVEL = CATEGORY_PREFIX_ + "ejb"; //$NON-NLS-1$
	
		static final String DMS_CATEGORY_LOGGING_PROPERTY = "dmsLoggingLevel"; //$NON-NLS-1$
		static final String ECLIPSELINK_DMS_CATEGORY_LOGGING_LEVEL = CATEGORY_PREFIX_ + "dms"; //$NON-NLS-1$
	
		static final String EJB_OR_METADATA_CATEGORY_LOGGING_PROPERTY = "ejb_or_metadataLoggingLevel"; //$NON-NLS-1$
		static final String ECLIPSELINK_EJB_OR_METADATA_CATEGORY_LOGGING_LEVEL = CATEGORY_PREFIX_ + "ejb_or_metadata"; //$NON-NLS-1$
	
		static final String METAMODEL_CATEGORY_LOGGING_PROPERTY = "jpa_metamodelLoggingLevel"; //$NON-NLS-1$
		static final String ECLIPSELINK_METAMODEL_CATEGORY_LOGGING_LEVEL = CATEGORY_PREFIX_ + "jpa_metamodel"; //$NON-NLS-1$

		static final String WEAVER_CATEGORY_LOGGING_PROPERTY = "weaverLoggingLevel"; //$NON-NLS-1$
		static final String ECLIPSELINK_WEAVER_CATEGORY_LOGGING_LEVEL = CATEGORY_PREFIX_ + "weaver"; //$NON-NLS-1$

		static final String PROPERTIES_CATEGORY_LOGGING_PROPERTY = "propertiesLoggingLevel"; //$NON-NLS-1$
		static final String ECLIPSELINK_PROPERTIES_CATEGORY_LOGGING_LEVEL = CATEGORY_PREFIX_ + "properties"; //$NON-NLS-1$

		static final String SERVER_CATEGORY_LOGGING_PROPERTY = "serverLoggingLevel"; //$NON-NLS-1$
		static final String ECLIPSELINK_SERVER_CATEGORY_LOGGING_LEVEL = CATEGORY_PREFIX_ + "server"; //$NON-NLS-1$

}
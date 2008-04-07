/*******************************************************************************
* Copyright (c) 2007, 2008 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal;

/**
 *  EclipseLinkUiMessages
 */
import org.eclipse.osgi.util.NLS;

public class EclipseLinkUiMessages extends NLS 
{	
	private static final String BUNDLE_NAME = "eclipselink_ui"; //$NON-NLS-1$
	
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, EclipseLinkUiMessages.class);
	}
	
	public static String Boolean_True;
	public static String Boolean_False;
	
	public static String PersistenceXmlTabFolder_defaultEmpty;
	public static String PersistenceXmlTabFolder_defaultWithOneParam;
	
	public static String PersistenceXmlGeneralTab_name;
	public static String PersistenceXmlGeneralTab_provider;
	public static String PersistenceXmlGeneralTab_browse;
	public static String PersistenceXmlGeneralTab_description;
	
	// SchemaGeneration
	public static String PersistenceXmlSchemaGenerationTab_title;
	public static String PersistenceXmlSchemaGenerationTab_sectionTitle;
	public static String PersistenceXmlSchemaGenerationTab_sectionDescription;
	public static String PersistenceXmlSchemaGenerationTab_defaultWithOneParam;
	public static String PersistenceXmlSchemaGenerationTab_defaultEmpty;
	
	public static String PersistenceXmlSchemaGenerationTab_ddlGenerationType;
	public static String PersistenceXmlSchemaGenerationTab_outputMode;
	
	public static String PersistenceXmlSchemaGenerationTab_createDdlFileName;
	public static String PersistenceXmlSchemaGenerationTab_dropDdlFileName;

	public static String OutputModeComposite_both;
	public static String OutputModeComposite_sql_script;
	public static String OutputModeComposite_database;
	
	public static String DdlGenerationTypeComposite_none;
	public static String DdlGenerationTypeComposite_create_tables;
	public static String DdlGenerationTypeComposite_drop_and_create_tables;
	
	// Caching
	public static String PersistenceXmlCachingTab_title;
	public static String PersistenceXmlCachingTab_sectionTitle;
	public static String PersistenceXmlCachingTab_sectionDescription;
	
	public static String PersistenceXmlCachingTab_defaultCacheTypeLabel;
	public static String PersistenceXmlCachingTab_cacheTypeLabel;
	
	public static String PersistenceXmlCachingTab_defaultSharedCacheLabel;
	public static String PersistenceXmlCachingTab_sharedCacheLabel;

	public static String PersistenceXmlCachingTab_defaultSharedCacheDefaultLabel;
	public static String PersistenceXmlCachingTab_sharedCacheDefaultLabel;
	
	public static String CacheTypeComposite_full;
	public static String CacheTypeComposite_hard_weak;
	public static String CacheTypeComposite_none;
	public static String CacheTypeComposite_soft_weak;
	public static String CacheTypeComposite_weak;

	public static String DefaultCacheTypeComposite_full;
	public static String DefaultCacheTypeComposite_hard_weak;
	public static String DefaultCacheTypeComposite_none;
	public static String DefaultCacheTypeComposite_soft_weak;
	public static String DefaultCacheTypeComposite_weak;
	
	public static String EntityDialog_selectEntity;
	public static String EntityDialog_name;

	public static String CachingEntityListComposite_groupTitle;
	public static String CachingEntityListComposite_editButton;
	
	// Customization
	public static String PersistenceXmlCustomizationTab_title;
	public static String PersistenceXmlCustomizationTab_sectionTitle;
	public static String PersistenceXmlCustomizationTab_sectionDescription;
	
	public static String PersistenceXmlCustomizationTab_throwExceptionsLabelDefault;
	public static String PersistenceXmlCustomizationTab_throwExceptionsLabel;
	
	public static String PersistenceXmlCustomizationTab_weavingLabel;

	public static String PersistenceXmlCustomizationTab_weavingLazyLabelDefault;
	public static String PersistenceXmlCustomizationTab_weavingLazyLabel;
		
	public static String PersistenceXmlCustomizationTab_weavingChangeTrackingLabelDefault;
	public static String PersistenceXmlCustomizationTab_weavingChangeTrackingLabel;
		
	public static String WeavingComposite_true_;
	public static String WeavingComposite_false_;
	public static String WeavingComposite_static_;

	public static String CustomizationEntityListComposite_groupTitle;
	public static String CustomizationEntityListComposite_editButton;
	public static String PersistenceXmlCustomizationTab_customizerLabel;
	public static String PersistenceXmlCustomizationTab_sessionCustomizerLabel;

	// Logging
	public static String PersistenceXmlLoggingTab_title;
	public static String PersistenceXmlLoggingTab_sectionTitle;
	public static String PersistenceXmlLoggingTab_sectionDescription;
	public static String PersistenceXmlLoggingTab_defaultWithOneParam;
	public static String PersistenceXmlLoggingTab_defaultEmpty;

	public static String PersistenceXmlLoggingTab_loggingLevelLabel;

	public static String LoggingLevelComposite_off;
	public static String LoggingLevelComposite_severe;
	public static String LoggingLevelComposite_warning;
	public static String LoggingLevelComposite_info;
	public static String LoggingLevelComposite_config;
	public static String LoggingLevelComposite_fine;
	public static String LoggingLevelComposite_finer;
	public static String LoggingLevelComposite_finest;

	public static String PersistenceXmlLoggingTab_loggerLabel;
	
	public static String LoggerComposite_default_logger;
	public static String LoggerComposite_java_logger;
	public static String LoggerComposite_server_logger;

	public static String PersistenceXmlLoggingTab_timestampLabel;
	public static String PersistenceXmlLoggingTab_timestampLabelDefault;
	public static String PersistenceXmlLoggingTab_threadLabel;
	public static String PersistenceXmlLoggingTab_threadLabelDefault;
	public static String PersistenceXmlLoggingTab_sessionLabel;
	public static String PersistenceXmlLoggingTab_sessionLabelDefault;
	public static String PersistenceXmlLoggingTab_exceptionsLabel;
	public static String PersistenceXmlLoggingTab_exceptionsLabelDefault;
	public static String PersistenceXmlLoggingTab_logFileLabel;
	public static String PersistenceXmlLoggingTab_logFileLabelDefault;
	public static String PersistenceXmlLoggingTab_loggersLabel;

	
	private EclipseLinkUiMessages() {
		throw new UnsupportedOperationException();
	}
}
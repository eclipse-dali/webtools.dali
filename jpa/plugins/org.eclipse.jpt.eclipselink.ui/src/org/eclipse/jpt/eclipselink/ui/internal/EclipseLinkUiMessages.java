/*******************************************************************************
* Copyright (c) 2007, 2010 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
*
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal;

import org.eclipse.osgi.util.NLS;

/**
 * Localized messages used by ElipseLink UI.
 */
public class EclipseLinkUiMessages {

	public static String MappingFileWizard_title;
	public static String MappingFileWizardPage_title;
	public static String MappingFileWizardPage_desc;
	
	public static String EclipseLink1_1MappingFileWizard_title;
	public static String EclipseLink1_1MappingFileWizardPage_title;
	public static String EclipseLink1_1MappingFileWizardPage_desc;

	public static String Boolean_True;
	public static String Boolean_False;

	public static String DefaultWithoutValue;
	public static String DefaultWithValue;

	public static String PersistenceXmlTabFolder_defaultEmpty;
	public static String PersistenceXmlTabFolder_defaultWithOneParam;

	public static String PersistenceXmlGeneralTab_name;
	public static String PersistenceXmlGeneralTab_provider;
	public static String PersistenceXmlGeneralTab_browse;
	public static String PersistenceXmlGeneralTab_description;

	// General
	public static String PersistenceXmlGeneralTab_title;
	public static String PersistenceXmlGeneralTab_generalSectionTitle;
	public static String PersistenceXmlGeneralTab_mappedClassesSectionTitle;
	public static String PersistenceXmlGeneralTab_mappedClassesSectionDescription;
	public static String PersistenceXmlGeneralTab_xmlMappingFilesSectionTitle;
	public static String PersistenceXmlGeneralTab_xmlMappingFilesSectionDescription;
	
	public static String PersistenceXmlGeneralTab_nameLabel;
	public static String PersistenceXmlGeneralTab_persistenceProviderLabel;
	public static String PersistenceXmlGeneralTab_descriptionLabel;
	
	public static String PersistenceXmlGeneralTab_excludeUnlistedMappedClasses;
	public static String PersistenceXmlGeneralTab_excludeUnlistedMappedClassesWithDefault;
	public static String PersistenceXmlGeneralTab_mappedClassesNoName;
	public static String PersistenceXmlGeneralTab_open;
	
	public static String PersistenceXmlGeneralTab_xmlMappingFilesDialog_title;
	public static String PersistenceXmlGeneralTab_xmlMappingFilesDialog_message;
	public static String PersistenceXmlGeneralTab_ormNoName;
	public static String PersistenceXmlGeneralTab_excludeEclipselinkOrm;
	public static String PersistenceXmlGeneralTab_excludeEclipselinkOrmWithDefault;

	// Connection
	public static String PersistenceXmlConnectionTab_title;
	public static String PersistenceXmlConnectionTab_sectionTitle;
	public static String PersistenceXmlConnectionTab_sectionDescription;
	public static String PersistenceXmlConnectionTab_defaultWithOneParam;
	public static String PersistenceXmlConnectionTab_defaultEmpty;

	public static String PersistenceXmlConnectionTab_transactionTypeLabel;

	public static String ConnectionPropertiesComposite_Database_GroupBox;

	public static String JdbcPropertiesComposite_EclipseLinkConnectionPool_GroupBox;

	public static String JdbcConnectionPropertiesComposite_ConnectionDialog_Message;
	public static String JdbcConnectionPropertiesComposite_ConnectionDialog_Title;

	public static String TransactionTypeComposite_jta;
	public static String TransactionTypeComposite_resource_local;

	public static String PersistenceXmlConnectionTab_nativeSqlLabel;
	public static String PersistenceXmlConnectionTab_nativeSqlLabelDefault;

	public static String PersistenceXmlConnectionTab_batchWritingLabel;

	public static String BatchWritingComposite_none;
	public static String BatchWritingComposite_jdbc;
	public static String BatchWritingComposite_buffered;
	public static String BatchWritingComposite_oracle_jdbc;

	public static String PersistenceXmlConnectionTab_cacheStatementsLabel;

	public static String PersistenceXmlConnectionTab_jtaDataSourceLabel;
	public static String PersistenceXmlConnectionTab_nonJtaDataSourceLabel;

	public static String PersistenceXmlConnectionTab_driverLabel;
	public static String PersistenceXmlConnectionTab_urlLabel;
	public static String PersistenceXmlConnectionTab_userLabel;
	public static String PersistenceXmlConnectionTab_passwordLabel;
	public static String PersistenceXmlConnectionTab_bindParametersLabel;
	public static String PersistenceXmlConnectionTab_bindParametersLabelDefault;

	public static String PersistenceXmlConnectionTab_readConnectionsSharedLabel;
	public static String PersistenceXmlConnectionTab_readConnectionsSharedLabelDefault;
	public static String PersistenceXmlConnectionTab_readConnectionsSectionTitle;
	public static String PersistenceXmlConnectionTab_readConnectionsMinLabel;
	public static String PersistenceXmlConnectionTab_readConnectionsMaxLabel;
	public static String PersistenceXmlConnectionTab_writeConnectionsSectionTitle;
	public static String PersistenceXmlConnectionTab_writeConnectionsMinLabel;
	public static String PersistenceXmlConnectionTab_writeConnectionsMaxLabel;

	public static String JdbcExclusiveConnectionsPropertiesComposite_GroupBox;
	
	public static String PersistenceXmlConnectionTab_exclusiveConnectionModeLabel;
	public static String PersistenceXmlConnectionTab_lazyConnectionLabel;
	public static String PersistenceXmlConnectionTab_lazyConnectionLabelDefault;

	public static String JdbcExclusiveConnectionModeComposite_always;
	public static String JdbcExclusiveConnectionModeComposite_isolated;
	public static String JdbcExclusiveConnectionModeComposite_transactional;
	
	// SchemaGeneration
	public static String PersistenceXmlSchemaGenerationTab_title;
	public static String PersistenceXmlSchemaGenerationTab_sectionTitle;
	public static String PersistenceXmlSchemaGenerationTab_sectionDescription;
	public static String PersistenceXmlSchemaGenerationTab_defaultWithOneParam;
	public static String PersistenceXmlSchemaGenerationTab_defaultEmpty;
	public static String PersistenceXmlSchemaGenerationTab_defaultDot;

	public static String PersistenceXmlSchemaGenerationTab_ddlGenerationTypeLabel;
	public static String PersistenceXmlSchemaGenerationTab_outputModeLabel;

	public static String PersistenceXmlSchemaGenerationTab_createDdlFileNameLabel;
	public static String PersistenceXmlSchemaGenerationTab_dropDdlFileNameLabel;

	public static String OutputModeComposite_both;
	public static String OutputModeComposite_sql_script;
	public static String OutputModeComposite_database;

	public static String DdlGenerationTypeComposite_none;
	public static String DdlGenerationTypeComposite_create_tables;
	public static String DdlGenerationTypeComposite_drop_and_create_tables;

	public static String PersistenceXmlSchemaGenerationTab_ddlGenerationLocationLabel;

	public static String DdlGenerationLocationComposite_dialogTitle;
	public static String DdlGenerationLocationComposite_dialogMessage;

	// Caching
	public static String PersistenceXmlCachingTab_title;
	public static String PersistenceXmlCachingTab_sectionTitle;
	public static String PersistenceXmlCachingTab_sectionDescription;
	
	public static String CacheDefaultsComposite_groupTitle;

	public static String PersistenceXmlCachingTab_defaultCacheTypeLabel;
	public static String PersistenceXmlCachingTab_cacheTypeLabel;

	public static String PersistenceXmlCachingTab_defaultSharedCacheLabel;
	public static String PersistenceXmlCachingTab_sharedCacheLabel;

	public static String PersistenceXmlCachingTab_defaultSharedCacheDefaultLabel;
	public static String PersistenceXmlCachingTab_sharedCacheDefaultLabel;

	public static String CacheSizeComposite_cacheSize;

	public static String CacheTypeComposite_full;
	public static String CacheTypeComposite_hard_weak;
	public static String CacheTypeComposite_none;
	public static String CacheTypeComposite_soft;
	public static String CacheTypeComposite_soft_weak;
	public static String CacheTypeComposite_weak;

	public static String DefaultCacheSizeComposite_defaultCacheSize;

	public static String DefaultCacheTypeComposite_full;
	public static String DefaultCacheTypeComposite_hard_weak;
	public static String DefaultCacheTypeComposite_none;
	public static String DefaultCacheTypeComposite_soft;
	public static String DefaultCacheTypeComposite_soft_weak;
	public static String DefaultCacheTypeComposite_weak;

	public static String EntityDialog_selectEntity;
	public static String EntityDialog_name;

	public static String CachingEntityListComposite_groupTitle;
	public static String CachingEntityListComposite_editButton;

	public static String CachingEntityListComposite_dialogMessage;
	public static String CachingEntityListComposite_dialogTitle;

	public static String PersistenceXmlCachingTab_FlushClearCacheLabel;
	
	public static String FlushClearCacheComposite_drop;
	public static String FlushClearCacheComposite_drop_invalidate;
	public static String FlushClearCacheComposite_merge;
	
	// Customization
	public static String PersistenceXmlCustomizationTab_title;
	public static String PersistenceXmlCustomizationTab_sectionTitle;
	public static String PersistenceXmlCustomizationTab_sectionDescription;
	public static String PersistenceXmlCustomizationTab_defaultWithOneParam;

	public static String PersistenceXmlCustomizationTab_weavingPropertiesGroupBox;
	public static String PersistenceXmlCustomizationTab_weavingLabel;

	public static String PersistenceXmlCustomizationTab_weavingLazyLabelDefault;
	public static String PersistenceXmlCustomizationTab_weavingLazyLabel;

	public static String PersistenceXmlCustomizationTab_weavingChangeTrackingLabelDefault;
	public static String PersistenceXmlCustomizationTab_weavingChangeTrackingLabel;

	public static String PersistenceXmlCustomizationTab_weavingFetchGroupsLabelDefault;
	public static String PersistenceXmlCustomizationTab_weavingFetchGroupsLabel;

	public static String PersistenceXmlCustomizationTab_weavingInternalLabelDefault;
	public static String PersistenceXmlCustomizationTab_weavingInternalLabel;

	public static String PersistenceXmlCustomizationTab_weavingEagerLabelDefault;
	public static String PersistenceXmlCustomizationTab_weavingEagerLabel;
	public static String PersistenceXmlCustomizationTab_defaultEmpty;
	
	public static String PersistenceXmlCustomizationTab_throwExceptionsLabelDefault;
	public static String PersistenceXmlCustomizationTab_throwExceptionsLabel;
	
	public static String PersistenceXmlCustomizationTab_exceptionHandlerLabel;

	public static String WeavingComposite_true_;
	public static String WeavingComposite_false_;
	public static String WeavingComposite_static_;

	public static String CustomizationEntityListComposite_groupTitle;
	public static String CustomizationEntityListComposite_editButton;

	public static String CustomizationEntityListComposite_dialogMessage;
	public static String CustomizationEntityListComposite_dialogTitle;
	
	public static String PersistenceXmlCustomizationTab_customizerLabel;
	public static String PersistenceXmlCustomizationTab_sessionCustomizerLabel;
	public static String PersistenceXmlCustomizationTab_noName;

	public static String PersistenceXmlCustomizationTab_validationOnlyLabel;
	public static String PersistenceXmlCustomizationTab_validationOnlyLabelDefault;

	public static String PersistenceXmlCustomizationTab_validateSchemaLabel;
	public static String PersistenceXmlCustomizationTab_validateSchemaLabelDefault;
	
	public static String PersistenceXmlCustomizationTab_profilerLabel;
	public static String PersistenceXmlCustomizationTab_browse;
	
	public static String ProfilerComposite_performance_profiler;
	public static String ProfilerComposite_query_monitor;
	public static String ProfilerComposite_no_profiler;

	// Logging
	public static String PersistenceXmlLoggingTab_title;
	public static String PersistenceXmlLoggingTab_sectionTitle;
	public static String PersistenceXmlLoggingTab_sectionDescription;
	public static String PersistenceXmlLoggingTab_defaultWithOneParam;
	public static String PersistenceXmlLoggingTab_defaultEmpty;
	public static String PersistenceXmlLoggingTab_defaultStdout;

	public static String PersistenceXmlLoggingTab_categoryLoggingLevelSectionTitle;
	public static String PersistenceXmlLoggingTab_loggingLevelLabel;
	public static String PersistenceXmlLoggingTab_sqlLoggingLevelLabel;
	public static String PersistenceXmlLoggingTab_transactionLoggingLevelLabel;
	public static String PersistenceXmlLoggingTab_eventLoggingLevelLabel;
	public static String PersistenceXmlLoggingTab_connectionLoggingLevelLabel;
	public static String PersistenceXmlLoggingTab_queryLoggingLevelLabel;
	public static String PersistenceXmlLoggingTab_cacheLoggingLevelLabel;
	public static String PersistenceXmlLoggingTab_propagationLoggingLevelLabel;
	public static String PersistenceXmlLoggingTab_sequencingLoggingLevelLabel;
	public static String PersistenceXmlLoggingTab_ejbLoggingLevelLabel;
	public static String PersistenceXmlLoggingTab_dmsLoggingLevelLabel;
	public static String PersistenceXmlLoggingTab_ejb_or_metadataLoggingLevelLabel;
	public static String PersistenceXmlLoggingTab_jpa_metamodelLoggingLevelLabel;
	public static String PersistenceXmlLoggingTab_weaverLoggingLevelLabel;
	public static String PersistenceXmlLoggingTab_propertiesLoggingLevelLabel;
	public static String PersistenceXmlLoggingTab_serverLoggingLevelLabel;
		
	public static String LoggingLevelComposite_off;
	public static String LoggingLevelComposite_severe;
	public static String LoggingLevelComposite_warning;
	public static String LoggingLevelComposite_info;
	public static String LoggingLevelComposite_config;
	public static String LoggingLevelComposite_fine;
	public static String LoggingLevelComposite_finer;
	public static String LoggingLevelComposite_finest;
	public static String LoggingLevelComposite_all;

	public static String EclipseLinkCategoryLoggingLevelComposite_off;
	public static String EclipseLinkCategoryLoggingLevelComposite_severe;
	public static String EclipseLinkCategoryLoggingLevelComposite_warning;
	public static String EclipseLinkCategoryLoggingLevelComposite_info;
	public static String EclipseLinkCategoryLoggingLevelComposite_config;
	public static String EclipseLinkCategoryLoggingLevelComposite_fine;
	public static String EclipseLinkCategoryLoggingLevelComposite_finer;
	public static String EclipseLinkCategoryLoggingLevelComposite_finest;
	public static String EclipseLinkCategoryLoggingLevelComposite_all;

	public static String PersistenceXmlLoggingTab_loggerLabel;
	public static String PersistenceXmlLoggingTab_browse;

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
	public static String PersistenceXmlLoggingTab_connectionLabel;
	public static String PersistenceXmlLoggingTab_connectionLabelDefault;
	
	public static String PersistenceXmlLoggingTab_loggersLabel;
	public static String PersistenceXmlLoggingTab_loggingFileLabel;

	public static String LoggingFileLocationComposite_dialogTitle;

	// Session Options
	public static String PersistenceXmlOptionsTab_title;
	public static String PersistenceXmlOptionsTab_sessionSectionTitle;
	public static String PersistenceXmlOptionsTab_sessionSectionDescription;
	public static String PersistenceXmlOptionsTab_defaultWithOneParam;
	public static String PersistenceXmlOptionsTab_defaultEmpty;

	public static String PersistenceXmlOptionsTab_sessionName;
	public static String PersistenceXmlOptionsTab_sessionsXml;

	public static String PersistenceXmlOptionsTab_includeDescriptorQueriesLabel;
	public static String PersistenceXmlOptionsTab_includeDescriptorQueriesLabelDefault;

	public static String PersistenceXmlOptionsTab_eventListenerLabel;
	public static String PersistenceXmlOptionsTab_targetDatabaseLabel;
	
	public static String PersistenceXmlOptionsTab_miscellaneousSectionTitle;
	public static String PersistenceXmlOptionsTab_miscellaneousSectionDescription;
	
	public static String PersistenceXmlOptionsTab_temporalMutableLabel;
	public static String PersistenceXmlOptionsTab_temporalMutableLabelDefault;

	public static String TargetDatabaseComposite_attunity;
	public static String TargetDatabaseComposite_auto;
	public static String TargetDatabaseComposite_cloudscape;
	public static String TargetDatabaseComposite_database;
	public static String TargetDatabaseComposite_db2;
	public static String TargetDatabaseComposite_db2mainframe;
	public static String TargetDatabaseComposite_dbase;
	public static String TargetDatabaseComposite_derby;
	public static String TargetDatabaseComposite_hsql;
	public static String TargetDatabaseComposite_informix;
	public static String TargetDatabaseComposite_javadb;
	public static String TargetDatabaseComposite_mysql;
	public static String TargetDatabaseComposite_oracle;
	public static String TargetDatabaseComposite_oracle11;
	public static String TargetDatabaseComposite_oracle10;
	public static String TargetDatabaseComposite_oracle9;
	public static String TargetDatabaseComposite_oracle8;
	public static String TargetDatabaseComposite_pointbase;
	public static String TargetDatabaseComposite_postgresql;
	public static String TargetDatabaseComposite_sqlanywhere;
	public static String TargetDatabaseComposite_sqlserver;
	public static String TargetDatabaseComposite_sybase;
	public static String TargetDatabaseComposite_timesten;

	public static String PersistenceXmlOptionsTab_targetServerLabel;
	public static String PersistenceXmlOptionsTab_noName;
	
	public static String TargetServerComposite_none;
	public static String TargetServerComposite_oc4j;
	public static String TargetServerComposite_sunas9;
	public static String TargetServerComposite_websphere;
	public static String TargetServerComposite_websphere_6_1;
	public static String TargetServerComposite_weblogic;
	public static String TargetServerComposite_weblogic_9;
	public static String TargetServerComposite_weblogic_10;
	public static String TargetServerComposite_jboss;

	// DDL Generation
	public static String EclipseLinkDDLGeneratorUi_generatingDDLWarningTitle;
	public static String EclipseLinkDDLGeneratorUi_generatingDDLWarningMessage;
	public static String EclipseLinkDDLGeneratorUi_error;

	private static final String BUNDLE_NAME = "eclipselink_ui"; //$NON-NLS-1$
	private static final Class<?> BUNDLE_CLASS = EclipseLinkUiMessages.class;
	static {
		NLS.initializeMessages(BUNDLE_NAME, BUNDLE_CLASS);
	}
	
	private EclipseLinkUiMessages() {
		throw new UnsupportedOperationException();
	}

}

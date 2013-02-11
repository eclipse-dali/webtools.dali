/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.jpa2.persistence;

import org.eclipse.osgi.util.NLS;

/**
 * Localized messages used by Dali JPA 2.0 UI persistence editor.
 */
public class JptJpaUiPersistenceMessages2_0 {

	private static final String BUNDLE_NAME = "jpt_jpa_ui_persistence2_0"; //$NON-NLS-1$
	private static final Class<?> BUNDLE_CLASS = JptJpaUiPersistenceMessages2_0.class;
	static {
		NLS.initializeMessages(BUNDLE_NAME, BUNDLE_CLASS);
	}

	// Connection
	public static String CONNECTION_PROPERTIES_COMPOSITE_DATABASE_GROUP_BOX;

	public static String DATA_SOURCE_PROPERTIES_COMPOSITE_JTA_DATA_SOURCE_LABEL;
	public static String DATA_SOURCE_PROPERTIES_COMPOSITE_NON_JTA_DATA_SOURCE_LABEL;
	
	public static String PERSISTENCE_UNIT_CONNECTION_COMPOSITE_SECTION_TITLE;
	public static String PERSISTENCE_UNIT_CONNECTION_COMPOSITE_SECTION_DESCRIPTION;
	public static String PERSISTENCE_UNIT_CONNECTION_TAB_TITLE;
	
	public static String PERSISTENCE_UNIT_OPTIONS_COMPOSITE_MISCELLANEOUS_SECTION_TITLE;
	public static String PERSISTENCE_UNIT_OPTIONS_COMPOSITE_MISCELLANEOUS_SECTION_DESCRIPTION;
	public static String PERSISTENCE_UNIT_OPTIONS_TAB_TITLE;
	public static String PERSISTENCE_UNIT_OPTIONS_TAB_NO_NAME;

	public static String JDBC_CONNECTION_PROPERTIES_COMPOSITE_CONNECTION_DIALOG_MESSAGE;
	public static String JDBC_CONNECTION_PROPERTIES_COMPOSITE_CONNECTION_DIALOG_TITLE;

	public static String JDBC_CONNECTION_PROPERTIES_COMPOSITE_POPULATE_FROM_CONNECTION_HYPER_LINK;
	public static String JDBC_CONNECTION_PROPERTIES_COMPOSITE_DRIVER_LABEL;
	public static String JDBC_CONNECTION_PROPERTIES_COMPOSITE_URL_LABEL;
	public static String JDBC_CONNECTION_PROPERTIES_COMPOSITE_USER_LABEL;
	public static String JDBC_CONNECTION_PROPERTIES_COMPOSITE_PASSWORD_LABEL;

	public static String JDBC_PROPERTIES_COMPOSITE_JDBC_CONNECTION_PROPERTIES_GROUP_BOX;

	public static String LOCKING_CONFIGURATION_COMPOSITE_LOCK_TIMEOUT_LABEL;
	public static String QUERY_CONFIGURATION_COMPOSITE_QUERY_TIMEOUT_LABEL;
	
	public static String TRANSACTION_TYPE_COMPOSITE_TRANSACTION_TYPE_LABEL;

	public static String TRANSACTION_TYPE_COMPOSITE_JTA;
	public static String TRANSACTION_TYPE_COMPOSITE_RESOURCE_LOCAL;
	
	public static String SHARED_CACHE_MODE_COMPOSITE_SHARED_CACHE_MODE_LABEL;

	public static String SHARED_CACHE_MODE_COMPOSITE_ALL;
	public static String SHARED_CACHE_MODE_COMPOSITE_NONE;
	public static String SHARED_CACHE_MODE_COMPOSITE_ENABLE_SELECTIVE;
	public static String SHARED_CACHE_MODE_COMPOSITE_DISABLE_SELECTIVE;
	public static String SHARED_CACHE_MODE_COMPOSITE_UNSPECIFIED;
	
	public static String VALIDATION_MODE_COMPOSITE_VALIDATION_MODE_LABEL;
	
	public static String VALIDATION_MODE_COMPOSITE_AUTO;
	public static String VALIDATION_MODE_COMPOSITE_CALLBACK;
	public static String VALIDATION_MODE_COMPOSITE_NONE;

	public static String VALIDATION_CONFIGURATION_COMPOSITE_GROUP_PRE_PERSIST_LABEL;
	public static String VALIDATION_CONFIGURATION_COMPOSITE_GROUP_PRE_UPDATE_LABEL;
	public static String VALIDATION_CONFIGURATION_COMPOSITE_GROUP_PRE_REMOVE_LABEL;


	private JptJpaUiPersistenceMessages2_0() {
		throw new UnsupportedOperationException();
	}
}

/*******************************************************************************
 * Copyright (c) 2006, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.persistence;

import org.eclipse.osgi.util.NLS;

/**
 * Localized messages used by Dali JPA UI persistence editor.
 */
public class JptJpaUiPersistenceMessages {

	private static final String BUNDLE_NAME = "jpt_jpa_ui_persistence"; //$NON-NLS-1$
	private static final Class<?> BUNDLE_CLASS = JptJpaUiPersistenceMessages.class;
	static {
		NLS.initializeMessages(BUNDLE_NAME, BUNDLE_CLASS);
	}

	public static String ARCHIVE_FILE_SELECTION_DIALOG_JAR_PATH_HELP_LABEL;
	public static String ARCHIVE_FILE_SELECTION_DIALOG_JAR_PATH_LABEL;

	public static String PERSISTENCE_UNIT_CLASSES_COMPOSITE_DESCRIPTION;
	public static String PERSISTENCE_UNIT_CLASSES_COMPOSITE_EXCLUDE_UNLISTED_CLASSES;
	public static String PERSISTENCE_UNIT_CLASSES_COMPOSITE_EXCLUDE_UNLISTED_CLASSES_WITH_DEFAULT;
	public static String PERSISTENCE_UNIT_CLASSES_COMPOSITE_CLASS_REF_NO_NAME;
	public static String PERSISTENCE_UNIT_CLASSES_COMPOSITE_OPEN;

	public static String PERSISTENCE_UNIT_CONNECTION_COMPOSITE_CONNECTION;
	public static String PERSISTENCE_UNIT_CONNECTION_COMPOSITE_database;
	public static String PERSISTENCE_UNIT_CONNECTION_COMPOSITE_general;

	public static String PERSISTENCE_UNIT_CONNECTION_DATABASE_COMPOSITE_JTA_DATASOURCE_NAME;
	public static String PERSISTENCE_UNIT_CONNECTION_DATABASE_COMPOSITE_NON_JTA_DATASOURCE_NAME;

	public static String PERSISTENCE_UNIT_CONNECTION_GENERAL_COMPOSITE_DEFAULT;
	public static String PERSISTENCE_UNIT_CONNECTION_GENERAL_COMPOSITE_JTA;
	public static String PERSISTENCE_UNIT_CONNECTION_GENERAL_COMPOSITE_RESOURCE_LOCAL;
	public static String PERSISTENCE_UNIT_CONNECTION_GENERAL_COMPOSITE_TRANSACTION_TYPE;

	public static String PERSISTENCE_UNIT_GENERAL_COMPOSITE_GENERAL;
	public static String PERSISTENCE_UNIT_GENERAL_COMPOSITE_JAR_FILES;
	public static String PERSISTENCE_UNIT_GENERAL_COMPOSITE_JPA_MAPPING_DESCRIPTORS;
	public static String PERSISTENCE_UNIT_GENERAL_COMPOSITE_JPA_MAPPING_DESCRIPTORS_DESCRIPTION;
	public static String PERSISTENCE_UNIT_GENERAL_COMPOSITE_MAPPED_CLASSES;
	public static String PERSISTENCE_UNIT_GENERAL_COMPOSITE_NAME;
	public static String PERSISTENCE_UNIT_GENERAL_COMPOSITE_PERSISTENCE_PROVIDER;
	public static String PERSISTENCE_UNIT_GENERAL_COMPOSITE_DESCRIPTION;
	
	public static String PERSISTENCE_UNIT_JAR_FILES_COMPOSITE_NO_FILE_NAME;
	public static String PERSISTENCE_UNIT_MAPPING_FILES_COMPOSITE_JAR_FILE_DIALOG_TITLE;
	public static String PERSISTENCE_UNIT_MAPPING_FILES_COMPOSITE_JAR_FILE_DIALOG_MESSAGE;
	
	public static String PERSISTENCE_UNIT_MAPPING_FILES_COMPOSITE_MAPPING_FILE_DIALOG_MESSAGE;
	public static String PERSISTENCE_UNIT_MAPPING_FILES_COMPOSITE_MAPPING_FILE_DIALOG_TITLE;
	public static String PERSISTENCE_UNIT_MAPPING_FILES_COMPOSITE_ORM_NO_NAME;

	public static String PERSISTENCE_UNIT_PROPERTIES_COMPOSITE_NAME_COLUMN;
	public static String PERSISTENCE_UNIT_PROPERTIES_COMPOSITE_PROPERTIES;
	public static String PERSISTENCE_UNIT_PROPERTIES_COMPOSITE_PROPERTIES_DESCRIPTION;
	public static String PERSISTENCE_UNIT_PROPERTIES_COMPOSITE_VALUE_COLUMN;

	private JptJpaUiPersistenceMessages() {
		throw new UnsupportedOperationException();
	}
}

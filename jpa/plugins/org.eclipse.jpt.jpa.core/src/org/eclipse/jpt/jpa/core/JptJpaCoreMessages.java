/*******************************************************************************
 * Copyright (c) 2006, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core;

import org.eclipse.osgi.util.NLS;

/**
 * Localized messages used by Dali JPA core.
 */
public class JptJpaCoreMessages {

	private static final String BUNDLE_NAME = "jpt_jpa_core"; //$NON-NLS-1$
	private static final Class<?> BUNDLE_CLASS = JptJpaCoreMessages.class;
	static {
		NLS.initializeMessages(BUNDLE_NAME, BUNDLE_CLASS);
	}
	
	public static String NONE;

	public static String BUILD_JPA_PROJECT_JOB_NAME;
	public static String DISPOSE_JPA_PROJECTS_JOB_NAME;
	public static String GET_JPA_PROJECTS_JOB_NAME;
	public static String GET_JPA_PROJECT_JOB_NAME;
	public static String REBUILD_JPA_PROJECT_JOB_NAME;
	public static String BUILD_VALIDATION_MESSAGES_JOB_NAME;
	public static String PROJECT_CHANGE_EVENT_HANDLER_JOB_NAME;
	public static String PROJECT_POST_CLEAN_BUILD_EVENT_HANDLER_JOB_NAME;
	public static String FACET_FILE_CHANGE_EVENT_HANDLER_JOB_NAME;
	public static String JAVA_CHANGE_EVENT_HANDLER_JOB_NAME;

	public static String VALIDATE_JOB;
	public static String VALIDATE_PROJECT_NOT_JPA;
	public static String VALIDATE_PROJECT_IMPROPER_PLATFORM;
	public static String VALIDATE_CONTAINER_QUESTIONABLE;
	public static String VALIDATE_PERSISTENCE_UNIT_DOES_NOT_SPECIFIED;
	public static String VALIDATE_PERSISTENCE_UNIT_NOT_IN_PROJECT;
	public static String VALIDATE_PLATFORM_NOT_SPECIFIED;
	public static String VALIDATE_PLATFORM_DOES_NOT_SUPPORT_FACET_VERSION;
	public static String VALIDATE_LIBRARY_PROVIDER_INVALID;
	public static String VALIDATE_CONNECTION_INVALID;
	public static String VALIDATE_CONNECTION_NOT_CONNECTED;
	public static String VALIDATE_DEFAULT_CATALOG_NOT_SPECIFIED;
	public static String VALIDATE_CONNECTION_DOESNT_CONTAIN_CATALOG;
	public static String VALIDATE_DEFAULT_SCHEMA_NOT_SPECIFIED;
	public static String VALIDATE_CONNECTION_DOESNT_CONTAIN_SCHEMA;
	public static String CONTEXT_MODEL_SYNC_JOB_NAME;
	public static String UPDATE_JOB_NAME;
	public static String METAMODEL_SYNC_JOB_NAME;
	public static String INVALID_FACET;

	public static String JAVA_METADATA_CONVERSION_IN_PROGRESS;
	public static String JAVA_METADATA_CONVERSION_CONVERT_GENERATOR;
	public static String JAVA_METADATA_CONVERSION_CONVERT_QUERY;
	public static String JAVA_METADATA_CONVERSION_CANCELED;
	public static String JAVA_METADATA_CONVERSION_COMPLETE;

	public static String MAKE_PERSISTENT_ADD_TO_XML_RESOURCE_MODEL;
	public static String MAKE_PERSISTENT_BUILDING_PERSISTENT_TYPE;
	public static String MAKE_PERSISTENT_PROCESSING_MAPPED_SUPERCLASSES;
	public static String MAKE_PERSISTENT_PROCESSING_ENTITIES;
	public static String MAKE_PERSISTENT_PROCESSING_EMBEDDABLES;
	public static String MAKE_PERSISTENT_UPDATING_JPA_MODEL;
	public static String MAKE_PERSISTENT_PROCESSING_JAVA_CLASSES;
	public static String MAKE_PERSISTENT_ANNOTATING_CLASS;
	public static String MAKE_PERSISTENT_ADD_TO_PERSISTENCE_XML_RESOURCE_MODEL;
	public static String MAKE_PERSISTENT_LISTING_IN_PERSISTENCE_XML;
	
	public static String BASIC_JPA_SE_PRESET_CONFIG__LABEL;
	public static String BASIC_JPA_SE_PRESET_CONFIG__DESC;
	public static String BASIC_JPA_EE_PRESET_CONFIG__LABEL;
	public static String BASIC_JPA_EE_PRESET_CONFIG__DESC;
	

	private JptJpaCoreMessages() {
		throw new UnsupportedOperationException();
	}
}

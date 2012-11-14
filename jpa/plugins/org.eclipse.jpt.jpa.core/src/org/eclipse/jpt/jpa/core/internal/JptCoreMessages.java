/*******************************************************************************
 * Copyright (c) 2006, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal;

import org.eclipse.osgi.util.NLS;

/**
 * Localized messages used by Dali core.
 */
// TODO bjv rename...
public class JptCoreMessages {
	private static final String BUNDLE_NAME = "jpa_core"; //$NON-NLS-1$
	private static final Class<?> BUNDLE_CLASS = JptCoreMessages.class;
	static {
		NLS.initializeMessages(BUNDLE_NAME, BUNDLE_CLASS);
	}
	
	public static String NONE;

	public static String BUILD_JPA_PROJECTS_JOB_NAME;
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
	public static String OSGI_BUNDLES_LIBRARY_VALIDATOR__BUNDLE_NOT_FOUND;
	public static String OSGI_BUNDLES_LIBRARY_VALIDATOR__IMPROPER_BUNDLE_VERSION;
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

	private JptCoreMessages() {
		throw new UnsupportedOperationException();
	}
}

/*******************************************************************************
 * Copyright (c) 2006, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core;

import org.eclipse.osgi.util.NLS;

/**
 * Localized messages used by Dali common core.
 */
public class JptCommonCoreMessages {

	private static final String BUNDLE_NAME = "jpt_common_core"; //$NON-NLS-1$
	private static final Class<?> BUNDLE_CLASS = JptCommonCoreMessages.class;
	static {
		NLS.initializeMessages(BUNDLE_NAME, BUNDLE_CLASS);
	}

	public static String DALI_JOB_NAME;
	public static String DALI_EVENT_HANDLER_THREAD_NAME;
	public static String GENERATION_CREATING_LAUNCH_CONFIG_TASK;
	public static String GENERATION_SAVING_LAUNCH_CONFIG_TASK;
	public static String GENERATION_LAUNCHING_CONFIG_TASK;
	public static String PREFERENCES_FLUSH_JOB_NAME;
	public static String REGISTRY_MISSING_ATTRIBUTE;
	public static String REGISTRY_INVALID_VALUE;
	public static String REGISTRY_MISSING_BUNDLE;
	public static String REGISTRY_DUPLICATE;
	public static String REGISTRY_FAILED_CLASS_LOAD;
	public static String REGISTRY_FAILED_INTERFACE_ASSIGNMENT;
	public static String REGISTRY_FAILED_INSTANTIATION;
	public static String MULTIPLE_LIBRARY_VALIDATOR_ENABLEMENTS;
	public static String USER_LIBRARY_VALIDATOR__CLASS_NOT_FOUND;
	public static String OSGI_BUNDLES_LIBRARY_VALIDATOR__BUNDLE_NOT_FOUND;
	public static String OSGI_BUNDLES_LIBRARY_VALIDATOR__IMPROPER_BUNDLE_VERSION;
	public static String INVALID_RESOURCE_LOCATOR_PRIORITY;
	public static String MULTIPLE_RESOURCE_LOCATOR_ENABLEMENTS;
	public static String VALIDATE_CONTAINER_NOT_SPECIFIED;
	public static String VALIDATE_FILE_NAME_NOT_SPECIFIED;
	public static String VALIDATE_FILE_ALREADY_EXISTS;
	public static String RESOURCE_TYPE_INVALID_CONTENT_TYPE;
	public static String RESOURCE_TYPE_INVALID_BASE_TYPE;

	private JptCommonCoreMessages() {
		throw new UnsupportedOperationException();
	}
}

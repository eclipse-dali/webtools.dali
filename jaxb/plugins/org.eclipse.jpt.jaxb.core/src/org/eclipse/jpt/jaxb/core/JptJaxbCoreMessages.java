/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core;

import org.eclipse.osgi.util.NLS;

/**
 * Localized messages used by Dali JAXB Core.
 */
public class JptJaxbCoreMessages {

	private static final String BUNDLE_NAME = "jpt_jaxb_core"; //$NON-NLS-1$
	private static final Class<?> BUNDLE_CLASS = JptJaxbCoreMessages.class;
	
	static {
		NLS.initializeMessages(BUNDLE_NAME, BUNDLE_CLASS);
	}
	
	public static String CONTEXT_MODEL_SYNC_JOB_NAME;
	public static String UPDATE_JOB_NAME;
	public static String PREFERENCES_FLUSH_JOB_NAME;
	
	public static String JAXB_FACET_CONFIG_VALIDATE_PLATFORM_NOT_SPECIFIED;
	public static String JAXB_FACET_CONFIG_VALIDATE_PLATFORM_DOES_NOT_SUPPORT_FACET_VERSION;
	
	public static String JRE_LIBRARY_VALIDATOR_INVALID_PLATFORM;
	public static String JRE_LIBRARY_VALIDATOR_INVALID_JAVA_FACET;
	public static String JRE_LIBRARY_VALIDATOR_INVALID_JAVA_LIBRARY;
	
	public static String USER_LIBRARY_VALIDATOR_INCOMPATIBLE_JAVA_FACET;
	public static String USER_LIBRARY_VALIDATOR_INCOMPATIBLE_JAVA_LIBRARY;
	
	public static String SCHEMA_GENERATOR_CREATING_JAXB_PROPERTIES_FILE_TASK;
	public static String SCHEMA_GENERATED;
	
	public static String XML_ATTRIBUTE_DESC;
	public static String XML_ELEMENT_DESC;
	public static String XML_TYPE_DESC;
	public static String SUBSTITUTION_HEAD_DESC;
	
	public static String XML_ELEMENT_DECL__SCOPE;
	
	public static String INVALID_FACET;

	private JptJaxbCoreMessages() {
		throw new UnsupportedOperationException();
	}
}

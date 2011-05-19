/*******************************************************************************
* Copyright (c) 2010, 2011 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal;

import org.eclipse.osgi.util.NLS;

/**
 * Localized messages used by Dali JAXB Core.
 */
public class JptJaxbCoreMessages {
	
	public static String ClassesGenerator_generatingClasses;
	public static String ClassesGenerator_generatingClassesTask;
	
	public static String JaxbFacetConfig_validatePlatformNotSpecified;
	public static String JaxbFacetConfig_validatePlatformDoesNotSupportFacetVersion;
	
	public static String JreLibraryValidator_invalidPlatform;
	public static String JreLibraryValidator_invalidJavaFacet;
	public static String JreLibraryValidator_invalidJavaLibrary;
	
	public static String UserLibraryValidator_incompatibleJavaFacet;
	public static String UserLibraryValidator_incompatibleJavaLibrary;
	
	public static String PREFERENCES_FLUSH_JOB_NAME;
	
	public static String SchemaGenerator_creatingJAXBPropertiesFileTask;
	
	
	private static final String BUNDLE_NAME = "jpt_jaxb_core"; //$NON-NLS-1$
	private static final Class<?> BUNDLE_CLASS = JptJaxbCoreMessages.class;
	
	static {
		NLS.initializeMessages(BUNDLE_NAME, BUNDLE_CLASS);
	}
	
	
	private JptJaxbCoreMessages() {
		throw new UnsupportedOperationException();
	}
}

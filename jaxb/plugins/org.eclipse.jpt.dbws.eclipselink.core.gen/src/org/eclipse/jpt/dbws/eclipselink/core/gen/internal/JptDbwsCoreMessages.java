/*******************************************************************************
* Copyright (c) 2010, 2011 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.dbws.eclipselink.core.gen.internal;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Localized messages used by Dali DBWS generation core.
 */
public class JptDbwsCoreMessages
{
	
	public static final String GENERATION_COMPLETED = "GENERATION_COMPLETED";
	public static final String GENERATION_FAILED = "GENERATION_FAILED";
	public static final String NO_GENERATION_PERFORMED = "NO_GENERATION_PERFORMED";
	public static final String WSDL_EXCEPTION = "WSDL_EXCEPTION";
	public static final String NO_OPERATIONS_SPECIFIED = "NO_OPERATIONS_SPECIFIED";
	public static final String UNABLE_TO_LOCATE_BUILDER_XML = "UNABLE_TO_LOCATE_BUILDER_XML";
	public static final String DRIVER_NOT_ON_CLASSPATH = "DRIVER_NOT_ON_CLASSPATH";


	private static final String BUNDLE_NAME = "org.eclipse.jpt.dbws.eclipselink.core.gen.internal.jpt_dbws_core"; //$NON-NLS-1$
	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

	private JptDbwsCoreMessages() {
	}

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		}
		catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
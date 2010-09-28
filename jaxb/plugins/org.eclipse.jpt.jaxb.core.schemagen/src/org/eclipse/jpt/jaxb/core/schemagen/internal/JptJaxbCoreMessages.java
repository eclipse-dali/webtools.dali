/*******************************************************************************
* Copyright (c) 2010 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jaxb.core.schemagen.internal;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Localized messages used by Dali JAXB core.
 */
public class JptJaxbCoreMessages
{

	public static final String LOADING_CLASSES = "LOADING_CLASSES";
	public static final String GENERATING_SCHEMA = "GENERATING_SCHEMA";
	public static final String SCHEMA_GENERATED = "SCHEMA_GENERATED";
	public static final String SCHEMA_NOT_CREATED = "SCHEMA_NOT_CREATED";
	public static final String NOT_FOUND = "NOT_FOUND";
	public static final String CONTEXT_FACTORY_NOT_FOUND = "CONTEXT_FACTORY_NOT_FOUND";
	

	private static final String BUNDLE_NAME = "org.eclipse.jpt.jaxb.core.schemagen.internal.jpt_jaxb_core"; //$NON-NLS-1$
	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

	private JptJaxbCoreMessages() {
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
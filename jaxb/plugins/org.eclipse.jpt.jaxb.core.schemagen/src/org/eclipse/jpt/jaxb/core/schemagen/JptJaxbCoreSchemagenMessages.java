/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core.schemagen;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Localized messages used by Dali JAXB core schema generation.
 */
@SuppressWarnings("nls")
public class JptJaxbCoreSchemagenMessages {

	private static final String BUNDLE_NAME = "jpt_jaxb_core_schemagen"; //$NON-NLS-1$
	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException ex) {
			return '!' + key + '!';
		}
	}

	public static final String LOADING_CLASSES = "LOADING_CLASSES";
	public static final String GENERATING_SCHEMA = "GENERATING_SCHEMA";
	public static final String SCHEMA_GENERATED = "SCHEMA_GENERATED";
	public static final String SCHEMA_NOT_CREATED = "SCHEMA_NOT_CREATED";
	public static final String NOT_FOUND = "NOT_FOUND";
	public static final String CONTEXT_FACTORY_NOT_FOUND = "CONTEXT_FACTORY_NOT_FOUND";

	private JptJaxbCoreSchemagenMessages() {
		throw new UnsupportedOperationException();
	}
}
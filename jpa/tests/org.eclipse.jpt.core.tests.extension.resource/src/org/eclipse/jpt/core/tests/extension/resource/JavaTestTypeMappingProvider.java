/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.tests.extension.resource;

import org.eclipse.jpt.core.JpaFactory;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.context.java.JavaTypeMappingProvider;

/**
 * 
 */
public class JavaTestTypeMappingProvider
	implements JavaTypeMappingProvider
{
	// singleton
	private static final JavaTestTypeMappingProvider INSTANCE = new JavaTestTypeMappingProvider();

	/**
	 * Return the singleton.
	 */
	public static JavaTypeMappingProvider instance() {
		return INSTANCE;
	}

	/**
	 * Ensure non-instantiability.
	 */
	private JavaTestTypeMappingProvider() {
		super();
	}

	public String key() {
		return JavaTestTypeMapping.TEST_TYPE_MAPPING_KEY;
	}
	
	public String annotationName() {
		return JavaTestTypeMapping.TEST_TYPE_ANNOTATION_NAME;
	}

	public JavaTestTypeMapping buildMapping(JavaPersistentType parent, JpaFactory factory) {
		return ((TestJpaFactory) factory).buildJavaTestTypeMapping(parent);
	}

}

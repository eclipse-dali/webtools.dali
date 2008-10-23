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
import org.eclipse.jpt.core.context.java.JavaAttributeMappingProvider;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;

public class JavaTestAttributeMappingProvider
	implements JavaAttributeMappingProvider
{
	// singleton
	private static final JavaTestAttributeMappingProvider INSTANCE = new JavaTestAttributeMappingProvider();

	/**
	 * Return the singleton.
	 */
	public static JavaAttributeMappingProvider instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private JavaTestAttributeMappingProvider() {
		super();
	}

	public String getKey() {
		return JavaTestAttributeMapping.TEST_ATTRIBUTE_MAPPING_KEY;
	}

	public String getAnnotationName() {
		return JavaTestAttributeMapping.TEST_ATTRIBUTE_ANNOTATION_NAME;
	}

	public JavaTestAttributeMapping buildMapping(JavaPersistentAttribute parent, JpaFactory factory) {
		return ((TestJpaFactory) factory).buildJavaTestAttributeMapping(parent);
	}		

}

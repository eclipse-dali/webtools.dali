/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.tests.extension.resource;

import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.core.JpaFactory;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.context.java.JavaTypeMappingDefinition;
import org.eclipse.jpt.core.resource.java.Annotation;

public class JavaTestTypeMappingDefinition
	implements JavaTypeMappingDefinition
{
	// singleton
	private static final JavaTestTypeMappingDefinition INSTANCE = new JavaTestTypeMappingDefinition();

	/**
	 * Return the singleton
	 */
	public static JavaTypeMappingDefinition instance() {
		return INSTANCE;
	}


	/**
	 * Enforce singleton usage
	 */
	private JavaTestTypeMappingDefinition() {
		super();
	}

	public String getKey() {
		return JavaTestTypeMapping.TEST_TYPE_MAPPING_KEY;
	}

	public String getAnnotationName() {
		return JavaTestTypeMapping.TEST_TYPE_ANNOTATION_NAME;
	}

	public Iterable<String> getSupportingAnnotationNames() {
		return EmptyIterable.instance();
	}

	public JavaTestTypeMapping buildMapping(JavaPersistentType persistentType, Annotation annotation, JpaFactory factory) {
		return ((TestJpaFactory) factory).buildJavaTestTypeMapping(persistentType);
	}
}

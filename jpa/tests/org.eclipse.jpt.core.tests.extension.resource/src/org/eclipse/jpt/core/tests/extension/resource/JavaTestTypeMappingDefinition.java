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
import org.eclipse.jpt.core.context.java.JavaTypeMappingDefinition;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaTypeMappingDefinition;

public class JavaTestTypeMappingDefinition
	extends AbstractJavaTypeMappingDefinition
{
	// singleton
	private static final JavaTestTypeMappingDefinition INSTANCE = 
			new JavaTestTypeMappingDefinition();
	
	
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

	public JavaTestTypeMapping buildMapping(JavaPersistentType parent, JpaFactory factory) {
		return ((TestJpaFactory) factory).buildJavaTestTypeMapping(parent);
	}
}

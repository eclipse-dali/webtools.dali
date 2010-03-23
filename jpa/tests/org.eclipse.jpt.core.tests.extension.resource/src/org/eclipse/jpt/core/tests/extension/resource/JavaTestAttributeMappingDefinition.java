/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.tests.extension.resource;

import org.eclipse.jpt.core.JpaFactory;
import org.eclipse.jpt.core.context.java.JavaAttributeMappingDefinition;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaAttributeMappingDefinition;

public class JavaTestAttributeMappingDefinition
	extends AbstractJavaAttributeMappingDefinition
{
	// singleton
	private static final JavaTestAttributeMappingDefinition INSTANCE = 
		new JavaTestAttributeMappingDefinition();
	
	
	/**
	 * Return the singleton.
	 */
	public static JavaAttributeMappingDefinition instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Enforce singleton usage
	 */
	private JavaTestAttributeMappingDefinition() {
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

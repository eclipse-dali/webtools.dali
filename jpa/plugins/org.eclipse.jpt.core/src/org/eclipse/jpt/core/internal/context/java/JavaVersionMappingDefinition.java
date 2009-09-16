/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.java;

import org.eclipse.jpt.core.JpaFactory;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.core.context.java.JavaAttributeMappingDefinition;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.resource.java.VersionAnnotation;

public class JavaVersionMappingDefinition
	extends AbstractJavaAttributeMappingDefinition
{
	// singleton
	private static final JavaVersionMappingDefinition INSTANCE = 
		new JavaVersionMappingDefinition();
	
	
	/**
	 * Return the singleton.
	 */
	public static JavaAttributeMappingDefinition instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Enforce singleton usage
	 */
	private JavaVersionMappingDefinition() {
		super();
	}
	
	
	public String getKey() {
		return MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY;
	}
	
	public String getAnnotationName() {
		return VersionAnnotation.ANNOTATION_NAME;
	}
	
	public JavaAttributeMapping buildMapping(JavaPersistentAttribute parent, JpaFactory factory) {
		return factory.buildJavaVersionMapping(parent);
	}
}

/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.context.java;

import org.eclipse.jpt.core.JpaFactory;
import org.eclipse.jpt.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.core.context.java.JavaAttributeMappingDefinition;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaAttributeMappingDefinition;
import org.eclipse.jpt.eclipselink.core.EclipseLinkMappingKeys;
import org.eclipse.jpt.eclipselink.core.internal.EclipseLinkJpaFactory;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkBasicMapAnnotation;

public class JavaEclipseLinkBasicMapMappingDefinition
	extends AbstractJavaAttributeMappingDefinition
{
	// singleton
	private static final JavaEclipseLinkBasicMapMappingDefinition INSTANCE = 
			new JavaEclipseLinkBasicMapMappingDefinition();
	
	
	/**
	 * Return the singleton.
	 */
	public static JavaAttributeMappingDefinition instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Enforce singleton usage
	 */
	private JavaEclipseLinkBasicMapMappingDefinition() {
		super();
	}
	
	
	public String getKey() {
		return EclipseLinkMappingKeys.BASIC_MAP_ATTRIBUTE_MAPPING_KEY;
	}
	
	public String getAnnotationName() {
		return EclipseLinkBasicMapAnnotation.ANNOTATION_NAME;
	}
	
	public JavaAttributeMapping buildMapping(JavaPersistentAttribute parent, JpaFactory factory) {
		return ((EclipseLinkJpaFactory) factory).buildJavaEclipseLinkBasicMapMapping(parent);
	}
}

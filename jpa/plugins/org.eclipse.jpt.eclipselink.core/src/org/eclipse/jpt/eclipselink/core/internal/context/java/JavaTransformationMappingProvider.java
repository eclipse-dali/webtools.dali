/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.context.java.JavaAttributeMappingProvider;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.eclipselink.core.EclipseLinkMappingKeys;
import org.eclipse.jpt.eclipselink.core.internal.EclipseLinkJpaFactory;
import org.eclipse.jpt.eclipselink.core.resource.java.TransformationAnnotation;

public class JavaTransformationMappingProvider
	implements JavaAttributeMappingProvider
{

	// singleton
	private static final JavaTransformationMappingProvider INSTANCE = new JavaTransformationMappingProvider();

	/**
	 * Return the singleton.
	 */
	public static JavaAttributeMappingProvider instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private JavaTransformationMappingProvider() {
		super();
	}

	public String getKey() {
		return EclipseLinkMappingKeys.TRANSFORMATION_ATTRIBUTE_MAPPING_KEY;
	}
	
	public String getAnnotationName() {
		return TransformationAnnotation.ANNOTATION_NAME;
	}

	public JavaAttributeMapping buildMapping(JavaPersistentAttribute parent, JpaFactory factory) {
		return ((EclipseLinkJpaFactory) factory).buildJavaTransformationMapping(parent);
	}
}

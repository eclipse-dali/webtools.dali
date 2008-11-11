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
import org.eclipse.jpt.eclipselink.core.resource.java.BasicCollectionAnnotation;

public class JavaBasicCollectionMappingProvider
	implements JavaAttributeMappingProvider
{

	// singleton
	private static final JavaBasicCollectionMappingProvider INSTANCE = new JavaBasicCollectionMappingProvider();

	/**
	 * Return the singleton.
	 */
	public static JavaAttributeMappingProvider instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private JavaBasicCollectionMappingProvider() {
		super();
	}

	public String getKey() {
		return EclipseLinkMappingKeys.BASIC_COLLECTION_ATTRIBUTE_MAPPING_KEY;
	}
	
	public String getAnnotationName() {
		return BasicCollectionAnnotation.ANNOTATION_NAME;
	}

	public JavaAttributeMapping buildMapping(JavaPersistentAttribute parent, JpaFactory factory) {
		return ((EclipseLinkJpaFactory) factory).buildJavaBasicCollectionMapping(parent);
	}
}

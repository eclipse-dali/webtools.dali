/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.java;

import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.jpa.core.JpaFactory;
import org.eclipse.jpt.jpa.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaAttributeMappingDefinition;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.internal.context.java.JavaBasicMappingDefinition;
import org.eclipse.jpt.jpa.eclipselink.core.EclipseLinkMappingKeys;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.ArrayAnnotation2_3;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.ConvertAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.ConverterAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.ObjectTypeConverterAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.StructConverterAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.TypeConverterAnnotation;

public class EclipseLinkJavaArrayMappingDefinition2_3
	implements JavaAttributeMappingDefinition
{
	// singleton
	private static final JavaAttributeMappingDefinition INSTANCE = new EclipseLinkJavaArrayMappingDefinition2_3();

	/**
	 * Return the singleton.
	 */
	public static JavaAttributeMappingDefinition instance() {
		return INSTANCE;
	}


	/**
	 * Enforce singleton usage
	 */
	private EclipseLinkJavaArrayMappingDefinition2_3() {
		super();
	}

	public String getKey() {
		return EclipseLinkMappingKeys.ARRAY_ATTRIBUTE_MAPPING_KEY;
	}

	public String getAnnotationName() {
		return ArrayAnnotation2_3.ANNOTATION_NAME;
	}

	public boolean isSpecified(JavaSpecifiedPersistentAttribute persistentAttribute) {
		return persistentAttribute.getResourceAttribute().getAnnotation(this.getAnnotationName()) != null;
	}

	public Iterable<String> getSupportingAnnotationNames() {
		return COMBINED_SUPPORTING_ANNOTATION_NAMES;
	}

	private static final String[] ECLIPSE_LINK_SUPPORTING_ANNOTATION_NAMES_ARRAY = new String[] {
		ConvertAnnotation.ANNOTATION_NAME,
		ConverterAnnotation.ANNOTATION_NAME,
		ObjectTypeConverterAnnotation.ANNOTATION_NAME,
		StructConverterAnnotation.ANNOTATION_NAME,
		TypeConverterAnnotation.ANNOTATION_NAME
	};
	private static final Iterable<String> ECLIPSE_LINK_SUPPORTING_ANNOTATION_NAMES = IterableTools.iterable(ECLIPSE_LINK_SUPPORTING_ANNOTATION_NAMES_ARRAY);

	public JavaAttributeMapping buildMapping(JavaSpecifiedPersistentAttribute persistentAttribute, JpaFactory factory) {
		return new EclipseLinkJavaArrayMapping2_3(persistentAttribute);
	}

	@SuppressWarnings("unchecked")
	private static final Iterable<String> COMBINED_SUPPORTING_ANNOTATION_NAMES = IterableTools.concatenate(
		JavaBasicMappingDefinition.instance().getSupportingAnnotationNames(),
		ECLIPSE_LINK_SUPPORTING_ANNOTATION_NAMES
	);

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}
}

/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.context.java;

import org.eclipse.jpt.core.context.java.JavaAttributeMappingDefinition;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaManyToManyMappingDefinition;
import org.eclipse.jpt.core.jpa2.resource.java.MapKeyClass2_0Annotation;
import org.eclipse.jpt.core.jpa2.resource.java.MapKeyColumn2_0Annotation;
import org.eclipse.jpt.core.jpa2.resource.java.MapKeyEnumerated2_0Annotation;
import org.eclipse.jpt.core.jpa2.resource.java.MapKeyJoinColumn2_0Annotation;
import org.eclipse.jpt.core.jpa2.resource.java.MapKeyJoinColumns2_0Annotation;
import org.eclipse.jpt.core.jpa2.resource.java.MapKeyTemporal2_0Annotation;
import org.eclipse.jpt.core.jpa2.resource.java.OrderColumn2_0Annotation;
import org.eclipse.jpt.core.resource.java.AttributeOverrideAnnotation;
import org.eclipse.jpt.core.resource.java.AttributeOverridesAnnotation;
import org.eclipse.jpt.utility.internal.ArrayTools;
import org.eclipse.jpt.utility.internal.iterables.ArrayIterable;

public class JavaManyToManyMappingDefinition2_0
	extends AbstractJavaManyToManyMappingDefinition
{
	// singleton
	private static final JavaAttributeMappingDefinition INSTANCE = new JavaManyToManyMappingDefinition2_0();

	/**
	 * Return the singleton.
	 */
	public static JavaAttributeMappingDefinition instance() {
		return INSTANCE;
	}


	/**
	 * Enforce singleton usage
	 */
	private JavaManyToManyMappingDefinition2_0() {
		super();
	}

	@Override
	public Iterable<String> getSupportingAnnotationNames() {
		return COMBINED_SUPPORTING_ANNOTATION_NAMES;
	}

	public static final String[] SUPPORTING_ANNOTATION_NAMES_ARRAY_2_0 = new String[] {
		AttributeOverrideAnnotation.ANNOTATION_NAME,
		AttributeOverridesAnnotation.ANNOTATION_NAME,
		MapKeyClass2_0Annotation.ANNOTATION_NAME,
		MapKeyColumn2_0Annotation.ANNOTATION_NAME,
		MapKeyEnumerated2_0Annotation.ANNOTATION_NAME,
		MapKeyJoinColumn2_0Annotation.ANNOTATION_NAME,
		MapKeyJoinColumns2_0Annotation.ANNOTATION_NAME,
		MapKeyTemporal2_0Annotation.ANNOTATION_NAME,
		OrderColumn2_0Annotation.ANNOTATION_NAME
	};

	protected static final String[] COMBINED_SUPPORTING_ANNOTATION_NAMES_ARRAY = ArrayTools.concatenate(
		SUPPORTING_ANNOTATION_NAMES_ARRAY,
		SUPPORTING_ANNOTATION_NAMES_ARRAY_2_0
	);
	protected static final Iterable<String> COMBINED_SUPPORTING_ANNOTATION_NAMES = new ArrayIterable<String>(COMBINED_SUPPORTING_ANNOTATION_NAMES_ARRAY);
}

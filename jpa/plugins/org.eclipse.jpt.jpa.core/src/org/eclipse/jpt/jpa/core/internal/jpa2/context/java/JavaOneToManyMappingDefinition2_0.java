/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2.context.java;

import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.jpa.core.context.java.JavaAttributeMappingDefinition;
import org.eclipse.jpt.jpa.core.internal.context.java.JavaAttributeMappingDefinitionWrapper;
import org.eclipse.jpt.jpa.core.internal.context.java.JavaOneToManyMappingDefinition;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.AccessAnnotation2_0;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.JPA2_0;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.MapKeyClassAnnotation2_0;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.MapKeyColumnAnnotation2_0;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.MapKeyEnumeratedAnnotation2_0;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.MapKeyJoinColumnAnnotation2_0;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.MapKeyTemporalAnnotation2_0;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.OrderColumnAnnotation2_0;
import org.eclipse.jpt.jpa.core.resource.java.AttributeOverrideAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.JPA;

public class JavaOneToManyMappingDefinition2_0
	extends JavaAttributeMappingDefinitionWrapper
{
	private static final JavaAttributeMappingDefinition DELEGATE = JavaOneToManyMappingDefinition.instance();

	// singleton
	private static final JavaAttributeMappingDefinition INSTANCE = new JavaOneToManyMappingDefinition2_0();

	/**
	 * Return the singleton.
	 */
	public static JavaAttributeMappingDefinition instance() {
		return INSTANCE;
	}


	/**
	 * Enforce singleton usage
	 */
	private JavaOneToManyMappingDefinition2_0() {
		super();
	}

	@Override
	protected JavaAttributeMappingDefinition getDelegate() {
		return DELEGATE;
	}

	@Override
	public Iterable<String> getSupportingAnnotationNames() {
		return COMBINED_SUPPORTING_ANNOTATION_NAMES;
	}

	private static final String[] SUPPORTING_ANNOTATION_NAMES_ARRAY_2_0 = new String[] {
		AccessAnnotation2_0.ANNOTATION_NAME,
		AttributeOverrideAnnotation.ANNOTATION_NAME,
		JPA.ATTRIBUTE_OVERRIDES,
		MapKeyClassAnnotation2_0.ANNOTATION_NAME,
		MapKeyColumnAnnotation2_0.ANNOTATION_NAME,
		MapKeyEnumeratedAnnotation2_0.ANNOTATION_NAME,
		MapKeyJoinColumnAnnotation2_0.ANNOTATION_NAME,
		JPA2_0.MAP_KEY_JOIN_COLUMNS,
		MapKeyTemporalAnnotation2_0.ANNOTATION_NAME,
		OrderColumnAnnotation2_0.ANNOTATION_NAME
	};
	private static final Iterable<String> SUPPORTING_ANNOTATION_NAMES_2_0 = IterableTools.iterable(SUPPORTING_ANNOTATION_NAMES_ARRAY_2_0);

	@SuppressWarnings("unchecked")
	private static final Iterable<String> COMBINED_SUPPORTING_ANNOTATION_NAMES = IterableTools.concatenate(
		DELEGATE.getSupportingAnnotationNames(),
		SUPPORTING_ANNOTATION_NAMES_2_0
	);
}

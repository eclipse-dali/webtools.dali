/*******************************************************************************
 * Copyright (c) 2006, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.java;

import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.jpa.core.JpaFactory;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.java.DefaultJavaAttributeMappingDefinition;
import org.eclipse.jpt.jpa.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaModifiablePersistentAttribute;
import org.eclipse.jpt.jpa.core.resource.java.BasicAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.ColumnAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.EnumeratedAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.LobAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.TemporalAnnotation;

public class JavaBasicMappingDefinition
	implements DefaultJavaAttributeMappingDefinition
{
	// singleton
	private static final DefaultJavaAttributeMappingDefinition INSTANCE = new JavaBasicMappingDefinition();

	/**
	 * Return the singleton.
	 */
	public static DefaultJavaAttributeMappingDefinition instance() {
		return INSTANCE;
	}


	/**
	 * Enforce singleton usage
	 */
	private JavaBasicMappingDefinition() {
		super();
	}

	public String getKey() {
		return MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY;
	}

	public String getAnnotationName() {
		return BasicAnnotation.ANNOTATION_NAME;
	}

	public boolean isSpecified(JavaModifiablePersistentAttribute persistentAttribute) {
		return persistentAttribute.getResourceAttribute().getAnnotation(this.getAnnotationName()) != null;
	}

	public Iterable<String> getSupportingAnnotationNames() {
		return SUPPORTING_ANNOTATION_NAMES;
	}

	private static final String[] SUPPORTING_ANNOTATION_NAMES_ARRAY = new String[] {
		ColumnAnnotation.ANNOTATION_NAME,
		LobAnnotation.ANNOTATION_NAME,
		TemporalAnnotation.ANNOTATION_NAME,
		EnumeratedAnnotation.ANNOTATION_NAME
	};
	private static final Iterable<String> SUPPORTING_ANNOTATION_NAMES = IterableTools.iterable(SUPPORTING_ANNOTATION_NAMES_ARRAY);

	public JavaAttributeMapping buildMapping(JavaModifiablePersistentAttribute persistentAttribute, JpaFactory factory) {
		return factory.buildJavaBasicMapping(persistentAttribute);
	}

	public boolean isDefault(JavaModifiablePersistentAttribute persistentAttribute) {
		return persistentAttribute.typeIsBasic();
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}
}

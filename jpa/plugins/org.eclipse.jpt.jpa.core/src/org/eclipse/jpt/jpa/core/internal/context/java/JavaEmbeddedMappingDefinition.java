/*******************************************************************************
 * Copyright (c) 2006, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
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
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.resource.java.AttributeOverrideAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.EmbeddedAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.JPA;

public class JavaEmbeddedMappingDefinition
	implements DefaultJavaAttributeMappingDefinition
{
	// singleton
	private static final DefaultJavaAttributeMappingDefinition INSTANCE = new JavaEmbeddedMappingDefinition();

	/**
	 * Return the singleton.
	 */
	public static DefaultJavaAttributeMappingDefinition instance() {
		return INSTANCE;
	}


	/**
	 * Enforce singleton usage
	 */
	private JavaEmbeddedMappingDefinition() {
		super();
	}

	public String getKey() {
		return MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY;
	}

	public String getAnnotationName() {
		return EmbeddedAnnotation.ANNOTATION_NAME;
	}

	public boolean isSpecified(JavaSpecifiedPersistentAttribute persistentAttribute) {
		return persistentAttribute.getResourceAttribute().getAnnotation(this.getAnnotationName()) != null;
	}

	public Iterable<String> getSupportingAnnotationNames() {
		return SUPPORTING_ANNOTATION_NAMES;
	}

	private static final String[] SUPPORTING_ANNOTATION_NAMES_ARRAY = new String[] {
		AttributeOverrideAnnotation.ANNOTATION_NAME,
		JPA.ATTRIBUTE_OVERRIDES,
	};
	private static final Iterable<String> SUPPORTING_ANNOTATION_NAMES = IterableTools.iterable(SUPPORTING_ANNOTATION_NAMES_ARRAY);

	public JavaAttributeMapping buildMapping(JavaSpecifiedPersistentAttribute persistentAttribute, JpaFactory factory) {
		return factory.buildJavaEmbeddedMapping(persistentAttribute);
	}

	public boolean isDefault(JavaSpecifiedPersistentAttribute persistentAttribute) {
		String targetEmbeddable = persistentAttribute.getSingleReferenceTargetTypeName();
		return (targetEmbeddable != null)
				&& (persistentAttribute.getPersistenceUnit().getEmbeddable(targetEmbeddable) != null);
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}
}

/*******************************************************************************
 * Copyright (c) 2006, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.java;

import org.eclipse.jpt.common.core.resource.java.Annotation;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.jpa.core.JpaFactory;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.java.JavaEmbeddable;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.context.java.JavaTypeMappingDefinition;
import org.eclipse.jpt.jpa.core.resource.java.EmbeddableAnnotation;

public class JavaEmbeddableDefinition
	implements JavaTypeMappingDefinition
{
	// singleton
	private static final JavaEmbeddableDefinition INSTANCE = new JavaEmbeddableDefinition();

	/**
	 * Return the singleton
	 */
	public static JavaTypeMappingDefinition instance() {
		return INSTANCE;
	}


	/**
	 * Enforce singleton usage
	 */
	private JavaEmbeddableDefinition() {
		super();
	}

	public String getKey() {
		return MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY;
	}

	public String getAnnotationName() {
		return EmbeddableAnnotation.ANNOTATION_NAME;
	}

	public Iterable<String> getSupportingAnnotationNames() {
		return EmptyIterable.instance();
	}

	public JavaEmbeddable buildMapping(JavaPersistentType persistentType, Annotation mappingAnnotation, JpaFactory factory) {
		return factory.buildJavaEmbeddable(persistentType, (EmbeddableAnnotation) mappingAnnotation);
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}
}

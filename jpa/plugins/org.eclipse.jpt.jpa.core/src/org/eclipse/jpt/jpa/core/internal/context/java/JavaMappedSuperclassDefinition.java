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

import org.eclipse.jpt.common.core.resource.java.Annotation;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.jpa.core.JpaFactory;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.java.JavaMappedSuperclass;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.context.java.JavaTypeMappingDefinition;
import org.eclipse.jpt.jpa.core.resource.java.IdClassAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.resource.java.MappedSuperclassAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.NamedNativeQueryAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.NamedQueryAnnotation;

public class JavaMappedSuperclassDefinition
	implements JavaTypeMappingDefinition
{
	// singleton
	private static final JavaMappedSuperclassDefinition INSTANCE = new JavaMappedSuperclassDefinition();

	/**
	 * Return the singleton
	 */
	public static JavaTypeMappingDefinition instance() {
		return INSTANCE;
	}


	/**
	 * Enforce singleton usage
	 */
	private JavaMappedSuperclassDefinition() {
		super();
	}

	public String getKey() {
		return MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY;
	}

	public String getAnnotationName() {
		return MappedSuperclassAnnotation.ANNOTATION_NAME;
	}

	public Iterable<String> getSupportingAnnotationNames() {
		return SUPPORTING_ANNOTATION_NAMES;
	}

	private static final String[] SUPPORTING_ANNOTATION_NAMES_ARRAY = new String[] {
			IdClassAnnotation.ANNOTATION_NAME,
			NamedQueryAnnotation.ANNOTATION_NAME,
			JPA.NAMED_QUERIES,
			NamedNativeQueryAnnotation.ANNOTATION_NAME,
			JPA.NAMED_NATIVE_QUERIES,
			JPA.EXCLUDE_DEFAULT_LISTENERS,
			JPA.EXCLUDE_SUPERCLASS_LISTENERS,
			JPA.ENTITY_LISTENERS,
			JPA.PRE_PERSIST,
			JPA.POST_PERSIST,
			JPA.PRE_REMOVE,
			JPA.POST_REMOVE,
			JPA.PRE_UPDATE,
			JPA.POST_UPDATE,
			JPA.POST_LOAD
	};
	private static final Iterable<String> SUPPORTING_ANNOTATION_NAMES = IterableTools.iterable(SUPPORTING_ANNOTATION_NAMES_ARRAY);

	public JavaMappedSuperclass buildMapping(JavaPersistentType persistentType, Annotation mappingAnnotation, JpaFactory factory) {
		return factory.buildJavaMappedSuperclass(persistentType, (MappedSuperclassAnnotation) mappingAnnotation);
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}
}

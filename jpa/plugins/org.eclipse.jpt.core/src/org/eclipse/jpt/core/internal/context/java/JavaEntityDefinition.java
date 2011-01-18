/*******************************************************************************
 * Copyright (c) 2006, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.java;

import org.eclipse.jpt.core.JpaFactory;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.java.JavaEntity;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.context.java.JavaTypeMappingDefinition;
import org.eclipse.jpt.core.resource.java.Annotation;
import org.eclipse.jpt.core.resource.java.AssociationOverrideAnnotation;
import org.eclipse.jpt.core.resource.java.AssociationOverridesAnnotation;
import org.eclipse.jpt.core.resource.java.AttributeOverrideAnnotation;
import org.eclipse.jpt.core.resource.java.AttributeOverridesAnnotation;
import org.eclipse.jpt.core.resource.java.DiscriminatorColumnAnnotation;
import org.eclipse.jpt.core.resource.java.DiscriminatorValueAnnotation;
import org.eclipse.jpt.core.resource.java.EntityAnnotation;
import org.eclipse.jpt.core.resource.java.IdClassAnnotation;
import org.eclipse.jpt.core.resource.java.InheritanceAnnotation;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.NamedNativeQueriesAnnotation;
import org.eclipse.jpt.core.resource.java.NamedNativeQueryAnnotation;
import org.eclipse.jpt.core.resource.java.NamedQueriesAnnotation;
import org.eclipse.jpt.core.resource.java.NamedQueryAnnotation;
import org.eclipse.jpt.core.resource.java.PrimaryKeyJoinColumnAnnotation;
import org.eclipse.jpt.core.resource.java.PrimaryKeyJoinColumnsAnnotation;
import org.eclipse.jpt.core.resource.java.SecondaryTableAnnotation;
import org.eclipse.jpt.core.resource.java.SecondaryTablesAnnotation;
import org.eclipse.jpt.core.resource.java.SequenceGeneratorAnnotation;
import org.eclipse.jpt.core.resource.java.TableAnnotation;
import org.eclipse.jpt.core.resource.java.TableGeneratorAnnotation;
import org.eclipse.jpt.utility.internal.iterables.ArrayIterable;

public class JavaEntityDefinition
	implements JavaTypeMappingDefinition
{
	// singleton
	private static final JavaEntityDefinition INSTANCE = new JavaEntityDefinition();

	/**
	 * Return the singleton
	 */
	public static JavaTypeMappingDefinition instance() {
		return INSTANCE;
	}


	/**
	 * Enforce singleton usage
	 */
	private JavaEntityDefinition() {
		super();
	}

	public String getKey() {
		return MappingKeys.ENTITY_TYPE_MAPPING_KEY;
	}

	public String getAnnotationName() {
		return EntityAnnotation.ANNOTATION_NAME;
	}

	public Iterable<String> getSupportingAnnotationNames() {
		return SUPPORTING_ANNOTATION_NAMES;
	}

	protected static final String[] SUPPORTING_ANNOTATION_NAMES_ARRAY = new String[] {
			TableAnnotation.ANNOTATION_NAME,
			SecondaryTableAnnotation.ANNOTATION_NAME,
			SecondaryTablesAnnotation.ANNOTATION_NAME,
			PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME,
			PrimaryKeyJoinColumnsAnnotation.ANNOTATION_NAME,
			IdClassAnnotation.ANNOTATION_NAME,
			InheritanceAnnotation.ANNOTATION_NAME,
			DiscriminatorValueAnnotation.ANNOTATION_NAME,
			DiscriminatorColumnAnnotation.ANNOTATION_NAME,
			SequenceGeneratorAnnotation.ANNOTATION_NAME,
			TableGeneratorAnnotation.ANNOTATION_NAME,
			NamedQueryAnnotation.ANNOTATION_NAME,
			NamedQueriesAnnotation.ANNOTATION_NAME,
			NamedNativeQueryAnnotation.ANNOTATION_NAME,
			NamedNativeQueriesAnnotation.ANNOTATION_NAME,
			JPA.SQL_RESULT_SET_MAPPING,
			JPA.EXCLUDE_DEFAULT_LISTENERS,
			JPA.EXCLUDE_SUPERCLASS_LISTENERS,
			JPA.ENTITY_LISTENERS,
			JPA.PRE_PERSIST,
			JPA.POST_PERSIST,
			JPA.PRE_REMOVE,
			JPA.POST_REMOVE,
			JPA.PRE_UPDATE,
			JPA.POST_UPDATE,
			JPA.POST_LOAD,
			AttributeOverrideAnnotation.ANNOTATION_NAME,
			AttributeOverridesAnnotation.ANNOTATION_NAME,
			AssociationOverrideAnnotation.ANNOTATION_NAME,
			AssociationOverridesAnnotation.ANNOTATION_NAME
	};
	protected static final Iterable<String> SUPPORTING_ANNOTATION_NAMES = new ArrayIterable<String>(SUPPORTING_ANNOTATION_NAMES_ARRAY);

	public JavaEntity buildMapping(JavaPersistentType persistentType, Annotation mappingAnnotation, JpaFactory factory) {
		return factory.buildJavaEntity(persistentType, (EntityAnnotation) mappingAnnotation);
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}
}

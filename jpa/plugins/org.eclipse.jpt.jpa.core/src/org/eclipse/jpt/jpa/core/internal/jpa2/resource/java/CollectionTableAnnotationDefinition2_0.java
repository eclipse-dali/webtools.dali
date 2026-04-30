/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2.resource.java;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.common.core.resource.java.Annotation;
import org.eclipse.jpt.common.core.resource.java.AnnotationDefinition;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.jpa.core.internal.jpa2.resource.java.binary.BinaryCollectionTableAnnotation2_0;
import org.eclipse.jpt.jpa.core.internal.jpa2.resource.java.source.SourceCollectionTableAnnotation2_0;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.CollectionTableAnnotation2_0;
import org.eclipse.jpt.jpa.core.resource.java.JPA;

/**
 * <code>javax.persistence.CollectionTable</code> / <code>jakarta.persistence.CollectionTable</code>
 */
public final class CollectionTableAnnotationDefinition2_0
	implements AnnotationDefinition
{
	private static final AnnotationDefinition INSTANCE =
			new CollectionTableAnnotationDefinition2_0(JPA.JAVAX_PACKAGE);

	public static AnnotationDefinition instance() {
		return INSTANCE;
	}

	public static AnnotationDefinition instance(String jpaPackage) {
		if (JPA.JAVAX_PACKAGE.equals(jpaPackage)) {
			return INSTANCE;
		}
		return new CollectionTableAnnotationDefinition2_0(jpaPackage);
	}

	private final String annotationName;

	private CollectionTableAnnotationDefinition2_0(String jpaPackage) {
		super();
		this.annotationName = jpaPackage +
				CollectionTableAnnotation2_0.ANNOTATION_NAME.substring(JPA.JAVAX_PACKAGE.length());
	}

	public Annotation buildAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement annotatedElement) {
		return new SourceCollectionTableAnnotation2_0(parent, annotatedElement);
	}

	public Annotation buildNullAnnotation(JavaResourceAnnotatedElement parent) {
		return new NullCollectionTableAnnotation2_0(parent);
	}

	public Annotation buildAnnotation(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation) {
		return new BinaryCollectionTableAnnotation2_0(parent, jdtAnnotation);
	}

	public String getAnnotationName() {
		return this.annotationName;
	}
}

/*******************************************************************************
 * Copyright (c) 2009, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.resource.java;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.common.core.resource.java.Annotation;
import org.eclipse.jpt.common.core.resource.java.AnnotationDefinition;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.internal.utility.jdt.JakartaAwareDeclarationAnnotationAdapter;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.internal.resource.java.binary.BinaryManyToOneAnnotation;
import org.eclipse.jpt.jpa.core.internal.resource.java.source.SourceManyToOneAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.ManyToOneAnnotation;

/**
 * javax.persistence.ManyToOne
 */
public final class ManyToOneAnnotationDefinition
	implements AnnotationDefinition
{
	// singleton
	private static final AnnotationDefinition INSTANCE = new ManyToOneAnnotationDefinition(JPA.JAVAX_PACKAGE);

	/**
	 * Return the singleton.
	 */
	public static AnnotationDefinition instance() {
		return INSTANCE;
	}

	/**
	 * Returns an annotation definition for the given JPA annotations package
	 * (either {\@link JPA#JAVAX_PACKAGE} or {\@link JPA#JAKARTA_PACKAGE}).
	 */
	public static AnnotationDefinition instance(String jpaPackage) {
		if (JPA.JAVAX_PACKAGE.equals(jpaPackage)) {
			return INSTANCE;
		}
		return new ManyToOneAnnotationDefinition(jpaPackage);
	}

	private final String annotationName;

		private ManyToOneAnnotationDefinition(String jpaPackage) {
		super();
		this.annotationName = jpaPackage + ManyToOneAnnotation.ANNOTATION_NAME.substring(JPA.JAVAX_PACKAGE.length());
	}

	public Annotation buildAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement annotatedElement) {
		if (ManyToOneAnnotation.ANNOTATION_NAME.equals(this.annotationName)) {
			return new SourceManyToOneAnnotation(parent, annotatedElement);
		}
		return new SourceManyToOneAnnotation(parent, annotatedElement,
				JakartaAwareDeclarationAnnotationAdapter.forJakarta(this.annotationName));
	}

	public Annotation buildNullAnnotation(JavaResourceAnnotatedElement parent) {
		throw new UnsupportedOperationException();
	}

	public Annotation buildAnnotation(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation) {
		return new BinaryManyToOneAnnotation(parent, jdtAnnotation);
	}

	public String getAnnotationName() {
		return this.annotationName;
	}
}

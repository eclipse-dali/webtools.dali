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
import org.eclipse.jpt.jpa.core.internal.resource.java.binary.BinaryIdAnnotation;
import org.eclipse.jpt.jpa.core.internal.resource.java.source.SourceIdAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.IdAnnotation;

/**
 * javax.persistence.Id
 */
public final class IdAnnotationDefinition
	implements AnnotationDefinition
{
	// singleton
	private static final AnnotationDefinition INSTANCE = new IdAnnotationDefinition(JPA.JAVAX_PACKAGE);

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
		return new IdAnnotationDefinition(jpaPackage);
	}

	private final String annotationName;

		private IdAnnotationDefinition(String jpaPackage) {
		super();
		this.annotationName = jpaPackage + IdAnnotation.ANNOTATION_NAME.substring(JPA.JAVAX_PACKAGE.length());
	}

	public Annotation buildAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement annotatedElement) {
		if (IdAnnotation.ANNOTATION_NAME.equals(this.annotationName)) {
			return new SourceIdAnnotation(parent, annotatedElement);
		}
		return new SourceIdAnnotation(parent, annotatedElement,
				JakartaAwareDeclarationAnnotationAdapter.forJakarta(this.annotationName));
	}

	public Annotation buildNullAnnotation(JavaResourceAnnotatedElement parent) {
		throw new UnsupportedOperationException();
	}

	public Annotation buildAnnotation(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation) {
		return new BinaryIdAnnotation(parent, jdtAnnotation);
	}

	public String getAnnotationName() {
		return this.annotationName;
	}
}

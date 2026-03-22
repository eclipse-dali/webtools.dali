/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
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
import org.eclipse.jpt.jpa.core.internal.jpa1.resource.java.binary.BinarySequenceGeneratorAnnotation1_0;
import org.eclipse.jpt.jpa.core.internal.jpa1.resource.java.source.SourceSequenceGeneratorAnnotation1_0;
import org.eclipse.jpt.jpa.core.resource.java.SequenceGeneratorAnnotation;

/**
 * javax.persistence.SequenceGenerator
 */
public final class SequenceGeneratorAnnotationDefinition
	implements AnnotationDefinition
{
	// singleton
	private static final AnnotationDefinition INSTANCE = new SequenceGeneratorAnnotationDefinition(JPA.JAVAX_PACKAGE);

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
		return new SequenceGeneratorAnnotationDefinition(jpaPackage);
	}

	private final String annotationName;

	private SequenceGeneratorAnnotationDefinition(String jpaPackage) {
		super();
		this.annotationName = jpaPackage + SequenceGeneratorAnnotation.ANNOTATION_NAME.substring(JPA.JAVAX_PACKAGE.length());
	}

	public Annotation buildAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement annotatedElement) {
		if (SequenceGeneratorAnnotation.ANNOTATION_NAME.equals(this.annotationName)) {
			return new SourceSequenceGeneratorAnnotation1_0(parent, annotatedElement);
		}
		return new SourceSequenceGeneratorAnnotation1_0(parent, annotatedElement,
				JakartaAwareDeclarationAnnotationAdapter.forJakarta(this.annotationName));
	}

	public Annotation buildNullAnnotation(JavaResourceAnnotatedElement parent) {
		throw new UnsupportedOperationException();
	}

	public Annotation buildAnnotation(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation) {
		return new BinarySequenceGeneratorAnnotation1_0(parent, jdtAnnotation);
	}

	public String getAnnotationName() {
		return this.annotationName;
	}
}

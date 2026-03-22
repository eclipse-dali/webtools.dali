/*******************************************************************************
 * Copyright (c) 2009, 2025 Oracle. All rights reserved.
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
import org.eclipse.jpt.jpa.core.internal.resource.java.binary.BinaryEntityAnnotation;
import org.eclipse.jpt.jpa.core.internal.resource.java.source.SourceEntityAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.EntityAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.JPA;

/**
 * Builds {@link EntityAnnotation} instances for both source and binary Java
 * types.
 * <p>
 * Use {@link #instance()} for the default <code>javax.persistence.Entity</code>
 * definition (JPA 2.x platforms). Use {@link #instance(String)} supplying
 * {@link JPA#JAKARTA_PACKAGE} for JPA 3.x platforms.
 */
public final class EntityAnnotationDefinition
	implements AnnotationDefinition
{
	// Default singleton – javax.persistence (JPA 2.x and below)
	private static final AnnotationDefinition INSTANCE =
			new EntityAnnotationDefinition(JPA.JAVAX_PACKAGE);

	/**
	 * Returns the default singleton for <code>javax.persistence.Entity</code>.
	 */
	public static AnnotationDefinition instance() {
		return INSTANCE;
	}

	/**
	 * Returns an annotation definition for the given JPA annotations package
	 * (either {@link JPA#JAVAX_PACKAGE} or {@link JPA#JAKARTA_PACKAGE}).
	 */
	public static AnnotationDefinition instance(String jpaPackage) {
		if (JPA.JAVAX_PACKAGE.equals(jpaPackage)) {
			return INSTANCE;
		}
		return new EntityAnnotationDefinition(jpaPackage);
	}

	// ---- instance ----

	private final String annotationName;

	private EntityAnnotationDefinition(String jpaPackage) {
		super();
		this.annotationName = jpaPackage + ".Entity"; //$NON-NLS-1$
	}

	public Annotation buildAnnotation(JavaResourceAnnotatedElement parent,
			AnnotatedElement annotatedElement) {
		// Pass the resolved annotation name so the source annotation binds to
		// the correct FQN in the AST (javax or jakarta).
		if (EntityAnnotation.ANNOTATION_NAME.equals(this.annotationName)) {
			return new SourceEntityAnnotation(parent, annotatedElement);
		}
		return new SourceEntityAnnotation(parent, annotatedElement, this.annotationName);
	}

	public Annotation buildNullAnnotation(JavaResourceAnnotatedElement parent) {
		throw new UnsupportedOperationException();
	}

	public Annotation buildAnnotation(JavaResourceAnnotatedElement parent,
			IAnnotation jdtAnnotation) {
		if (EntityAnnotation.ANNOTATION_NAME.equals(this.annotationName)) {
			return new BinaryEntityAnnotation(parent, jdtAnnotation);
		}
		return new BinaryEntityAnnotation(parent, jdtAnnotation, this.annotationName);
	}

	public String getAnnotationName() {
		return this.annotationName;
	}
}

/*******************************************************************************
 * Copyright (c) 2026 Lakshminarayana Nekkanti. All rights reserved.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Lakshminarayana Nekkanti - initial API and implementation
 *
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.resource.java;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.common.core.resource.java.Annotation;
import org.eclipse.jpt.common.core.resource.java.AnnotationDefinition;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.jpa.core.resource.java.JPA;

/**
 * Wraps an existing {@link AnnotationDefinition} and translates its annotation
 * name from the <code>javax.persistence</code> package to the
 * <code>jakarta.persistence</code> package.
 * <p>
 * This adapter is used by JPA 3.x annotation definition providers to re-use
 * the existing JPA 2.x annotation definitions without duplicating every
 * source/binary annotation class. The delegate still handles building the
 * annotation model objects; only {@link #getAnnotationName()} is intercepted.
 * <p>
 * <b>Note:</b> For annotations whose source/binary implementations embed a
 * hard-coded {@link org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter}
 * using the <code>javax.persistence</code> name (e.g.
 * {@link org.eclipse.jpt.jpa.core.internal.resource.java.source.SourceEntityAnnotation}),
 * the annotation <em>definition</em> must override
 * {@link #buildAnnotation(JavaResourceAnnotatedElement, AnnotatedElement)} to
 * supply the correct jakarta FQN to the source annotation's constructor.
 * {@link EntityAnnotationDefinition} already does this — it is the reference
 * implementation to follow for each annotation type.
 */
public class JakartaAnnotationDefinitionAdapter
	implements AnnotationDefinition
{
	private final AnnotationDefinition delegate;
	private final String jakartaAnnotationName;

	/**
	 * Wraps {@code delegate}, replacing {@code javax.persistence} with
	 * {@code jakarta.persistence} in the annotation name.
	 */
	public JakartaAnnotationDefinitionAdapter(AnnotationDefinition delegate) {
		this.delegate = delegate;
		String name = delegate.getAnnotationName();
		this.jakartaAnnotationName = (name != null && name.startsWith(JPA.JAVAX_PACKAGE))
				? JPA.JAKARTA_PACKAGE + name.substring(JPA.JAVAX_PACKAGE.length())
				: name;
	}

	// ---- AnnotationDefinition ----

	/**
	 * Returns the jakarta-translated annotation name.
	 */
	public String getAnnotationName() {
		return this.jakartaAnnotationName;
	}

	/**
	 * Delegates to the wrapped definition.
	 * <p>
	 * <b>Important:</b> If the underlying source annotation uses a
	 * <code>static final DeclarationAnnotationAdapter</code> that embeds the
	 * javax FQN, the resulting model object will not correctly bind to the
	 * {@code @jakarta.persistence.*} annotation in the AST.  In that case
	 * subclass this adapter and override this method to invoke the
	 * package-aware constructor of the source annotation (see
	 * {@link EntityAnnotationDefinition#instance(String)} for the pattern).
	 */
	public Annotation buildAnnotation(JavaResourceAnnotatedElement parent,
			AnnotatedElement annotatedElement) {
		return this.delegate.buildAnnotation(parent, annotatedElement);
	}

	public Annotation buildNullAnnotation(JavaResourceAnnotatedElement parent) {
		return this.delegate.buildNullAnnotation(parent);
	}

	public Annotation buildAnnotation(JavaResourceAnnotatedElement parent,
			IAnnotation jdtAnnotation) {
		return this.delegate.buildAnnotation(parent, jdtAnnotation);
	}

	@Override
	public String toString() {
		return "JakartaAnnotationDefinitionAdapter(" + this.jakartaAnnotationName + ')'; //$NON-NLS-1$
	}
}

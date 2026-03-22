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
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.common.core.resource.java.NestableAnnotationDefinition;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.jpa.core.resource.java.JPA;

/**
 * Wraps a {@link NestableAnnotationDefinition} and translates its annotation
 * names from <code>javax.persistence</code> to <code>jakarta.persistence</code>.
 *
 * @see JakartaAnnotationDefinitionAdapter
 */
public class JakartaNestableAnnotationDefinitionAdapter
	implements NestableAnnotationDefinition
{
	private final NestableAnnotationDefinition delegate;
	private final String jakartaNestableAnnotationName;
	private final String jakartaContainerAnnotationName;

	public JakartaNestableAnnotationDefinitionAdapter(NestableAnnotationDefinition delegate) {
		this.delegate = delegate;
		this.jakartaNestableAnnotationName = translate(delegate.getNestableAnnotationName());
		this.jakartaContainerAnnotationName = translate(delegate.getContainerAnnotationName());
	}

	private static String translate(String name) {
		if (name != null && name.startsWith(JPA.JAVAX_PACKAGE)) {
			return JPA.JAKARTA_PACKAGE + name.substring(JPA.JAVAX_PACKAGE.length());
		}
		return name;
	}

	// ---- NestableAnnotationDefinition ----

	public String getNestableAnnotationName() {
		return this.jakartaNestableAnnotationName;
	}

	public String getContainerAnnotationName() {
		return this.jakartaContainerAnnotationName;
	}

	public String getElementName() {
		return this.delegate.getElementName();
	}

	public NestableAnnotation buildAnnotation(JavaResourceAnnotatedElement parent,
			AnnotatedElement element, int index) {
		return this.delegate.buildAnnotation(parent, element, index);
	}

	public NestableAnnotation buildAnnotation(JavaResourceAnnotatedElement parent,
			IAnnotation jdtAnnotation, int index) {
		return this.delegate.buildAnnotation(parent, jdtAnnotation, index);
	}

	@Override
	public String toString() {
		return "JakartaNestableAnnotationDefinitionAdapter(" //$NON-NLS-1$
				+ this.jakartaNestableAnnotationName + ')';
	}
}

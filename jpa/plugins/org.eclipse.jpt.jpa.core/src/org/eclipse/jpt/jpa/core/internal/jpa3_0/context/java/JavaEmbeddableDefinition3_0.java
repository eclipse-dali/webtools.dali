/*******************************************************************************
 * Copyright (c) 2024 Lakshminarayana Nekkanti. All rights reserved.
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
 *******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa3_0.context.java;

import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.jpa.core.context.java.JavaTypeMappingDefinition;
import org.eclipse.jpt.jpa.core.internal.context.java.JavaTypeMappingDefinitionWrapper;
import org.eclipse.jpt.jpa.core.internal.jpa2.context.java.JavaEmbeddableDefinition2_0;
import org.eclipse.jpt.jpa.core.resource.java.JPA;

/**
 * JPA 3.0 embeddable type mapping definition.
 * <p>
 * Wraps {@link JavaEmbeddableDefinition2_0} via {@link JavaTypeMappingDefinitionWrapper},
 * overriding {@link #getAnnotationName()} to return {@code jakarta.persistence.Embeddable}
 * and translating all supporting annotation names to the {@code jakarta.persistence.*}
 * namespace, so that Dali correctly recognises embeddables in JPA 3.x projects.
 */
public class JavaEmbeddableDefinition3_0
	extends JavaTypeMappingDefinitionWrapper
{
	private static final JavaTypeMappingDefinition DELEGATE = JavaEmbeddableDefinition2_0.instance();

	private static final Iterable<String> JAKARTA_SUPPORTING_ANNOTATION_NAMES =
			IterableTools.transform(
					DELEGATE.getSupportingAnnotationNames(),
					JakartaJavaAttributeMappingDefinitionAdapter.ToJakartaAnnotationNameTransformer.INSTANCE);

	// singleton
	private static final JavaTypeMappingDefinition INSTANCE = new JavaEmbeddableDefinition3_0();

	public static JavaTypeMappingDefinition instance() {
		return INSTANCE;
	}

	private JavaEmbeddableDefinition3_0() {
		super();
	}

	@Override
	protected JavaTypeMappingDefinition getDelegate() {
		return DELEGATE;
	}

	@Override
	public String getAnnotationName() {
		return JPA.JAKARTA_PACKAGE + ".Embeddable"; //$NON-NLS-1$
	}

	@Override
	public Iterable<String> getSupportingAnnotationNames() {
		return JAKARTA_SUPPORTING_ANNOTATION_NAMES;
	}
}

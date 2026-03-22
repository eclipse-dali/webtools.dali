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
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.jpa.core.JpaFactory;
import org.eclipse.jpt.jpa.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaAttributeMappingDefinition;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.resource.java.JPA;

/**
 * Wraps a JPA 2.x {@link JavaAttributeMappingDefinition} and translates its
 * {@code javax.persistence.*} annotation name to the {@code jakarta.persistence.*}
 * equivalent for use in JPA 3.x platform providers.
 * <p>
 * Both {@link #getAnnotationName()} and {@link #isSpecified(JavaSpecifiedPersistentAttribute)}
 * use the jakarta-translated name so that Dali correctly matches attributes
 * in JPA 3.x projects that use {@code jakarta.persistence.*} annotations.
 * <p>
 * Supporting annotation names are also translated so that annotation-ownership
 * is correctly maintained when changing a mapping type in the JPA Details view.
 */
public class JakartaJavaAttributeMappingDefinitionAdapter
	implements JavaAttributeMappingDefinition
{
	protected final JavaAttributeMappingDefinition delegate;
	private final String jakartaAnnotationName;
	private final Iterable<String> jakartaSupportingAnnotationNames;

	public JakartaJavaAttributeMappingDefinitionAdapter(JavaAttributeMappingDefinition delegate) {
		this.delegate = delegate;
		this.jakartaAnnotationName = toJakarta(delegate.getAnnotationName());
		this.jakartaSupportingAnnotationNames = IterableTools.transform(
				delegate.getSupportingAnnotationNames(),
				ToJakartaAnnotationNameTransformer.INSTANCE);
	}

	// ---- JavaAttributeMappingDefinition ----

	public String getKey() {
		return this.delegate.getKey();
	}

	/**
	 * Returns the {@code jakarta.persistence.*} equivalent of the wrapped
	 * definition's annotation name.
	 */
	public String getAnnotationName() {
		return this.jakartaAnnotationName;
	}

	/**
	 * Checks for the {@code jakarta.persistence.*} annotation rather than the
	 * {@code javax.persistence.*} annotation used by the delegate.
	 */
	public boolean isSpecified(JavaSpecifiedPersistentAttribute persistentAttribute) {
		return persistentAttribute.getResourceAttribute().getAnnotation(this.jakartaAnnotationName) != null;
	}

	/**
	 * Returns supporting annotation names translated to {@code jakarta.persistence.*}.
	 */
	public Iterable<String> getSupportingAnnotationNames() {
		return this.jakartaSupportingAnnotationNames;
	}

	public JavaAttributeMapping buildMapping(JavaSpecifiedPersistentAttribute persistentAttribute, JpaFactory factory) {
		return this.delegate.buildMapping(persistentAttribute, factory);
	}

	@Override
	public String toString() {
		return "JakartaJavaAttributeMappingDefinitionAdapter(" + this.jakartaAnnotationName + ')'; //$NON-NLS-1$
	}

	// ---- helpers ----

	/**
	 * Translates a {@code javax.persistence.*} annotation name to its
	 * {@code jakarta.persistence.*} equivalent. Names that do not start with
	 * {@code javax.persistence} are returned unchanged.
	 */
	static String toJakarta(String annotationName) {
		if (annotationName != null && annotationName.startsWith(JPA.JAVAX_PACKAGE)) {
			return JPA.JAKARTA_PACKAGE + annotationName.substring(JPA.JAVAX_PACKAGE.length());
		}
		return annotationName;
	}

	static class ToJakartaAnnotationNameTransformer
		extends TransformerAdapter<String, String>
	{
		static final ToJakartaAnnotationNameTransformer INSTANCE = new ToJakartaAnnotationNameTransformer();

		@Override
		public String transform(String name) {
			return toJakarta(name);
		}
	}
}

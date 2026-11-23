/*******************************************************************************
 * Copyright (c) 2026 Lakshminarayana Nekkanti and others.
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
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.java;

import org.eclipse.jpt.jpa.core.context.java.JavaTypeMappingDefinition;
import org.eclipse.jpt.jpa.core.internal.context.java.JavaTypeMappingDefinitionWrapper;
import org.eclipse.jpt.jpa.core.internal.jpa3_0.context.java.JakartaJavaAttributeMappingDefinitionAdapter;
import org.eclipse.jpt.jpa.core.resource.java.JPA;

/**
 * EclipseLink Jakarta Persistence entity type mapping definition.
 * <p>
 * Wraps the EclipseLink 2.x definition, translating {@code javax.persistence.*}
 * annotation names to {@code jakarta.persistence.*} so that EclipseLink 3.0 and
 * later projects are recognised. EclipseLink's own annotations stay in
 * {@code org.eclipse.persistence.annotations} and are passed through unchanged.
 */
public class EclipseLinkJavaEntityDefinition3_0
	extends JavaTypeMappingDefinitionWrapper
{
	private static final JavaTypeMappingDefinition DELEGATE = EclipseLinkJavaEntityDefinition2_3.instance();

	private static final Iterable<String> JAKARTA_SUPPORTING_ANNOTATION_NAMES =
			JakartaJavaAttributeMappingDefinitionAdapter.toJakarta(DELEGATE.getSupportingAnnotationNames());

	// singleton
	private static final JavaTypeMappingDefinition INSTANCE = new EclipseLinkJavaEntityDefinition3_0();

	/**
	 * Return the singleton.
	 */
	public static JavaTypeMappingDefinition instance() {
		return INSTANCE;
	}

	/**
	 * Enforce singleton usage
	 */
	private EclipseLinkJavaEntityDefinition3_0() {
		super();
	}

	@Override
	protected JavaTypeMappingDefinition getDelegate() {
		return DELEGATE;
	}

	@Override
	public String getAnnotationName() {
		return JPA.JAKARTA_PACKAGE + ".Entity"; //$NON-NLS-1$
	}

	@Override
	public Iterable<String> getSupportingAnnotationNames() {
		return JAKARTA_SUPPORTING_ANNOTATION_NAMES;
	}
}

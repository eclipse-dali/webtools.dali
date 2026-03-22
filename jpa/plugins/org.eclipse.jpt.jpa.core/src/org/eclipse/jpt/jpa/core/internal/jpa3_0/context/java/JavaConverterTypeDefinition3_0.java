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

import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.jpa.core.JpaFactory;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.core.context.java.JavaManagedTypeDefinition;
import org.eclipse.jpt.jpa.core.internal.jpa2_1.context.java.JavaConverterTypeDefinition2_1;
import org.eclipse.jpt.jpa.core.jpa2_1.JpaFactory2_1;
import org.eclipse.jpt.jpa.core.jpa2_1.context.ConverterType2_1;
import org.eclipse.jpt.jpa.core.jpa2_1.context.java.JavaConverterType2_1;
import org.eclipse.jpt.jpa.core.resource.java.JPA;

/**
 * JPA 3.0 converter managed type definition.
 * <p>
 * Identical to {@link JavaConverterTypeDefinition2_1} except that
 * {@link #getAnnotationNames} returns the Jakarta annotation name
 * ({@code jakarta.persistence.Converter}) so that Dali correctly recognises
 * converter types in JPA 3.x projects.
 */
public class JavaConverterTypeDefinition3_0
	implements JavaManagedTypeDefinition
{
	// singleton
	private static final JavaManagedTypeDefinition INSTANCE = new JavaConverterTypeDefinition3_0();

	public static JavaManagedTypeDefinition instance() {
		return INSTANCE;
	}

	private JavaConverterTypeDefinition3_0() {
		super();
	}

	public Class<ConverterType2_1> getManagedTypeType() {
		return ConverterType2_1.class;
	}

	public Iterable<String> getAnnotationNames(JpaProject jpaProject) {
		return IterableTools.singletonIterable(JPA.JAKARTA_PACKAGE + ".Converter"); //$NON-NLS-1$
	}

	public JavaConverterType2_1 buildContextManagedType(JpaContextModel parent, JavaResourceType jrt, JpaFactory factory) {
		return ((JpaFactory2_1) factory).buildJavaConverterType(parent, jrt);
	}
}

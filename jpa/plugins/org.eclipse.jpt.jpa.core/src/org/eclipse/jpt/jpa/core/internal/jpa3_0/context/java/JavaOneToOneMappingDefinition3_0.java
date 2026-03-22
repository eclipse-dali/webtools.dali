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

import org.eclipse.jpt.jpa.core.context.java.JavaAttributeMappingDefinition;
import org.eclipse.jpt.jpa.core.internal.jpa2.context.java.JavaOneToOneMappingDefinition2_0;

/**
 * JPA 3.0 one-to-one attribute mapping definition.
 * <p>
 * Extends {@link JakartaJavaAttributeMappingDefinitionAdapter} wrapping
 * {@link JavaOneToOneMappingDefinition2_0} so that annotation detection uses
 * {@code jakarta.persistence.OneToOne} in JPA 3.x projects.
 */
public class JavaOneToOneMappingDefinition3_0
	extends JakartaJavaAttributeMappingDefinitionAdapter
{
	// singleton
	private static final JavaAttributeMappingDefinition INSTANCE =
			new JavaOneToOneMappingDefinition3_0();

	public static JavaAttributeMappingDefinition instance() {
		return INSTANCE;
	}

	private JavaOneToOneMappingDefinition3_0() {
		super(JavaOneToOneMappingDefinition2_0.instance());
	}
}

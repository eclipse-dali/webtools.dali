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

import org.eclipse.jpt.jpa.core.context.java.DefaultJavaAttributeMappingDefinition;
import org.eclipse.jpt.jpa.core.internal.jpa2.context.java.JavaEmbeddedMappingDefinition2_0;

/**
 * JPA 3.0 embedded attribute mapping definition.
 * <p>
 * Extends {@link JakartaDefaultJavaAttributeMappingDefinitionAdapter} wrapping
 * {@link JavaEmbeddedMappingDefinition2_0} so that annotation detection and
 * default-mapping logic use {@code jakarta.persistence.Embedded} in JPA 3.x projects.
 */
public class JavaEmbeddedMappingDefinition3_0
	extends JakartaDefaultJavaAttributeMappingDefinitionAdapter
{
	// singleton
	private static final DefaultJavaAttributeMappingDefinition INSTANCE =
			new JavaEmbeddedMappingDefinition3_0();

	public static DefaultJavaAttributeMappingDefinition instance() {
		return INSTANCE;
	}

	private JavaEmbeddedMappingDefinition3_0() {
		super(JavaEmbeddedMappingDefinition2_0.instance());
	}
}

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
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedPersistentAttribute;

/**
 * Extends {@link JakartaJavaAttributeMappingDefinitionAdapter} to also implement
 * {@link DefaultJavaAttributeMappingDefinition}, delegating {@link #isDefault}
 * to the wrapped definition.
 * <p>
 * Used for {@code JavaEmbeddedMappingDefinition2_0} and
 * {@code JavaBasicMappingDefinition2_0} in JPA 3.x platform providers.
 */
public class JakartaDefaultJavaAttributeMappingDefinitionAdapter
	extends JakartaJavaAttributeMappingDefinitionAdapter
	implements DefaultJavaAttributeMappingDefinition
{
	private final DefaultJavaAttributeMappingDefinition defaultDelegate;

	public JakartaDefaultJavaAttributeMappingDefinitionAdapter(DefaultJavaAttributeMappingDefinition delegate) {
		super(delegate);
		this.defaultDelegate = delegate;
	}

	public boolean isDefault(JavaSpecifiedPersistentAttribute persistentAttribute) {
		return this.defaultDelegate.isDefault(persistentAttribute);
	}
}

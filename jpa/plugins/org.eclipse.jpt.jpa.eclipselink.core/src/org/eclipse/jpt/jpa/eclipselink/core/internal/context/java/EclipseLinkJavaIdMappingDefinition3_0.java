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

import org.eclipse.jpt.jpa.core.context.java.JavaAttributeMappingDefinition;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.internal.jpa3_0.context.java.JakartaJavaAttributeMappingDefinitionAdapter;

/**
 * EclipseLink Jakarta Persistence <code>Id</code> mapping definition.
 * <p>
 * {@link JakartaJavaAttributeMappingDefinitionAdapter} replaces the wrapped
 * definition's {@link #isSpecified(JavaSpecifiedPersistentAttribute)} with a
 * plain annotation lookup, which would lose the EclipseLink rule in
 * {@link EclipseLinkJavaIdMappingDefinition2_0}: an <code>Id</code> on an
 * attribute that EclipseLink maps by default as a 1:1 (a derived id) is not a
 * specified <code>Id</code> mapping. This definition restores that rule for the
 * <code>jakarta.persistence.Id</code> annotation.
 */
public class EclipseLinkJavaIdMappingDefinition3_0
	extends JakartaJavaAttributeMappingDefinitionAdapter
{
	// singleton
	private static final JavaAttributeMappingDefinition INSTANCE = new EclipseLinkJavaIdMappingDefinition3_0();

	/**
	 * Return the singleton.
	 */
	public static JavaAttributeMappingDefinition instance() {
		return INSTANCE;
	}

	/**
	 * Enforce singleton usage
	 */
	private EclipseLinkJavaIdMappingDefinition3_0() {
		super(EclipseLinkJavaIdMappingDefinition2_2.instance());
	}

	@Override
	public boolean isSpecified(JavaSpecifiedPersistentAttribute persistentAttribute) {
		return super.isSpecified(persistentAttribute) &&
				! EclipseLinkJavaIdMappingDefinition2_0.isDefaultDerivedId(persistentAttribute);
	}
}

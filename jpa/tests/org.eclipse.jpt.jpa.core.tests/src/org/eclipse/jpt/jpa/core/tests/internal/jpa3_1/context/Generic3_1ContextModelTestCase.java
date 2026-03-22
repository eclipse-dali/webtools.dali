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
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.jpa3_1.context;

import org.eclipse.jpt.jpa.core.internal.jpa3_1.GenericJpaPlatformFactory3_1;
import org.eclipse.jpt.jpa.core.jpa3_1.JpaProject3_1;
import org.eclipse.jpt.jpa.core.tests.internal.context.ContextModelTestCase;

/**
 * Base test case for JPA 3.1 context model tests.
 * <p>
 * Configures the JPA platform to {@code generic3_1} and the facet version to
 * {@code "3.1"}.  JPA 3.1 adds {@code GenerationType.UUID} support; all other
 * behaviour is inherited from JPA 3.0.
 */
public abstract class Generic3_1ContextModelTestCase
	extends ContextModelTestCase
{
	protected Generic3_1ContextModelTestCase(String name) {
		super(name);
	}

	@Override
	protected String getJpaPlatformID() {
		return GenericJpaPlatformFactory3_1.ID;
	}

	@Override
	protected String getJpaFacetVersionString() {
		return JpaProject3_1.FACET_VERSION_STRING;
	}
}

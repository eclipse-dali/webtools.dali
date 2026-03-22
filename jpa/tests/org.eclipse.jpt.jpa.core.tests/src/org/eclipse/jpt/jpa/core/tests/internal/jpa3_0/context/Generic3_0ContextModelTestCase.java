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
package org.eclipse.jpt.jpa.core.tests.internal.jpa3_0.context;

import org.eclipse.jpt.jpa.core.internal.jpa3_0.GenericJpaPlatformFactory3_0;
import org.eclipse.jpt.jpa.core.jpa3_0.JpaProject3_0;
import org.eclipse.jpt.jpa.core.tests.internal.context.ContextModelTestCase;

/**
 * Base test case for JPA 3.0 context model tests.
 * <p>
 * Configures the JPA platform to {@code generic3_0} and the facet version to
 * {@code "3.0"} so that {@code jakarta.persistence.*} annotations are
 * recognised by the annotation definition provider.
 */
public abstract class Generic3_0ContextModelTestCase
	extends ContextModelTestCase
{
	protected Generic3_0ContextModelTestCase(String name) {
		super(name);
	}

	@Override
	protected String getJpaPlatformID() {
		return GenericJpaPlatformFactory3_0.ID;
	}

	@Override
	protected String getJpaFacetVersionString() {
		return JpaProject3_0.FACET_VERSION_STRING;
	}
}

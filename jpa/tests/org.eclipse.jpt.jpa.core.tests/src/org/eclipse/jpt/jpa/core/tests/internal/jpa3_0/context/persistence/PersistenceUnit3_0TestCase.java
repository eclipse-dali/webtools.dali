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
package org.eclipse.jpt.jpa.core.tests.internal.jpa3_0.context.persistence;

import org.eclipse.jpt.common.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.internal.jpa3_0.GenericJpaPlatformFactory3_0;
import org.eclipse.jpt.jpa.core.jpa3_0.JpaProject3_0;
import org.eclipse.jpt.jpa.core.jpa3_0.context.persistence.PersistenceUnit3_0;
import org.eclipse.jpt.jpa.core.tests.internal.context.persistence.PersistenceUnitTestCase;

/**
 * Base test case for JPA 3.0 persistence unit tests.
 * <p>
 * Mirrors {@code PersistenceUnit2_1TestCase} but targets the JPA 3.0 platform
 * ({@code generic3_0}) and uses {@link PersistenceUnit3_0} as the subject type.
 */
public abstract class PersistenceUnit3_0TestCase
	extends PersistenceUnitTestCase
{
	protected PersistenceUnit3_0 subject;

	protected PropertyValueModel<PersistenceUnit3_0> subjectHolder;

	protected PersistenceUnit3_0TestCase(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.subject = this.getPersistenceUnit();
		this.subjectHolder = new SimplePropertyValueModel<PersistenceUnit3_0>(this.subject);
		this.populatePu();
	}

	@Override
	protected String getJpaFacetVersionString() {
		return JpaProject3_0.FACET_VERSION_STRING;
	}

	@Override
	protected String getJpaPlatformID() {
		return GenericJpaPlatformFactory3_0.ID;
	}

	@Override
	protected PersistenceUnit3_0 getPersistenceUnit() {
		return (PersistenceUnit3_0) super.getPersistenceUnit();
	}
}

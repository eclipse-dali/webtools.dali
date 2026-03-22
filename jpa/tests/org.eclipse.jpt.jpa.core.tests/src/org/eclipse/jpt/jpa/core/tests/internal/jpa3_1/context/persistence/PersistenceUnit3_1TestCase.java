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
package org.eclipse.jpt.jpa.core.tests.internal.jpa3_1.context.persistence;

import org.eclipse.jpt.common.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.internal.jpa3_1.GenericJpaPlatformFactory3_1;
import org.eclipse.jpt.jpa.core.jpa3_1.JpaProject3_1;
import org.eclipse.jpt.jpa.core.jpa3_1.context.persistence.PersistenceUnit3_1;
import org.eclipse.jpt.jpa.core.tests.internal.context.persistence.PersistenceUnitTestCase;

/**
 * Base test case for JPA 3.1 persistence unit tests.
 * <p>
 * Mirrors {@code PersistenceUnit3_0TestCase} but targets the JPA 3.1 platform
 * ({@code generic3_1}) and uses {@link PersistenceUnit3_1} as the subject type.
 */
public abstract class PersistenceUnit3_1TestCase
	extends PersistenceUnitTestCase
{
	protected PersistenceUnit3_1 subject;

	protected PropertyValueModel<PersistenceUnit3_1> subjectHolder;

	protected PersistenceUnit3_1TestCase(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.subject = this.getPersistenceUnit();
		this.subjectHolder = new SimplePropertyValueModel<PersistenceUnit3_1>(this.subject);
		this.populatePu();
	}

	@Override
	protected String getJpaFacetVersionString() {
		return JpaProject3_1.FACET_VERSION_STRING;
	}

	@Override
	protected String getJpaPlatformID() {
		return GenericJpaPlatformFactory3_1.ID;
	}

	@Override
	protected PersistenceUnit3_1 getPersistenceUnit() {
		return (PersistenceUnit3_1) super.getPersistenceUnit();
	}
}

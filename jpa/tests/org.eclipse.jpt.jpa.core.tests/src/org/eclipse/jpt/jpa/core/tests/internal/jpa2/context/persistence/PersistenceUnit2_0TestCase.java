/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.jpa2.context.persistence;

import org.eclipse.jpt.common.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.internal.jpa2.GenericJpaPlatformFactory2_0;
import org.eclipse.jpt.jpa.core.jpa2.JpaProject2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.persistence.PersistenceUnit2_0;
import org.eclipse.jpt.jpa.core.tests.internal.context.persistence.PersistenceUnitTestCase;

public abstract class PersistenceUnit2_0TestCase
	extends PersistenceUnitTestCase
{
	protected PersistenceUnit2_0 subject;

	protected PropertyValueModel<PersistenceUnit2_0> subjectHolder;

	// ********** constructors **********
	protected PersistenceUnit2_0TestCase(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.subject = this.getPersistenceUnit();
		this.subjectHolder = new SimplePropertyValueModel<PersistenceUnit2_0>(this.subject);
		this.populatePu();
	}
	
	@Override
	protected String getJpaFacetVersionString() {
		return JpaProject2_0.FACET_VERSION_STRING;
	}

	@Override
	protected String getJpaPlatformID() {
		return GenericJpaPlatformFactory2_0.ID;
	}

	@Override
	protected PersistenceUnit2_0 getPersistenceUnit() {
		return (PersistenceUnit2_0) super.getPersistenceUnit();
	}
}

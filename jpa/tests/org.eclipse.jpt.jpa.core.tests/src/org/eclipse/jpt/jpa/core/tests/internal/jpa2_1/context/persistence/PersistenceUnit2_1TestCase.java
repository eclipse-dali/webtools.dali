/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.jpa2_1.context.persistence;

import org.eclipse.jpt.common.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.internal.jpa2_1.Generic2_1JpaPlatformFactory;
import org.eclipse.jpt.jpa.core.jpa2_1.JpaProject2_1;
import org.eclipse.jpt.jpa.core.jpa2_1.context.persistence.PersistenceUnit2_1;
import org.eclipse.jpt.jpa.core.tests.internal.context.persistence.PersistenceUnitTestCase;

/**
 *  PersistenceUnit2_1TestCase
 */
public abstract class PersistenceUnit2_1TestCase 
	extends PersistenceUnitTestCase
{
	protected PersistenceUnit2_1 subject;

	protected PropertyValueModel<PersistenceUnit2_1> subjectHolder;

	// ********** constructors **********
	protected PersistenceUnit2_1TestCase(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.subject = this.getPersistenceUnit();
		this.subjectHolder = new SimplePropertyValueModel<PersistenceUnit2_1>(this.subject);
		this.populatePu();
	}
	
	@Override
	protected String getJpaFacetVersionString() {
		return JpaProject2_1.FACET_VERSION_STRING;
	}

	@Override
	protected String getJpaPlatformID() {
		return Generic2_1JpaPlatformFactory.ID;
	}

	@Override
	protected PersistenceUnit2_1 getPersistenceUnit() {
		return (PersistenceUnit2_1) super.getPersistenceUnit();
	}
}

/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.tests.internal.context.persistence;

import org.eclipse.jpt.common.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.jpa2.JpaProject2_0;
import org.eclipse.jpt.jpa.core.tests.internal.context.persistence.PersistenceUnitTestCase;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkPersistenceUnit;
import org.eclipse.jpt.jpa.eclipselink.core.internal.EclipseLink2_0JpaPlatformFactory;

/**
 *  EclipseLink2_0PersistenceUnitTestCase
 */
public abstract class EclipseLink2_0PersistenceUnitTestCase
	extends PersistenceUnitTestCase
{
	protected EclipseLinkPersistenceUnit subject;

	protected PropertyValueModel<EclipseLinkPersistenceUnit> subjectHolder;

	// ********** constructors **********
	protected EclipseLink2_0PersistenceUnitTestCase(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.subject = this.getPersistenceUnit();
		this.subjectHolder = new SimplePropertyValueModel<EclipseLinkPersistenceUnit>(this.subject);
		this.populatePu();
	}

	@Override
	protected String getJpaFacetVersionString() {
		return JpaProject2_0.FACET_VERSION_STRING;
	}

	@Override
	protected String getJpaPlatformID() {
		return EclipseLink2_0JpaPlatformFactory.ID;
	}

	@Override
	protected EclipseLinkPersistenceUnit getPersistenceUnit() {
		return (EclipseLinkPersistenceUnit) super.getPersistenceUnit();
	}
}

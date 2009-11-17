/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.tests.internal.context.persistence;

import org.eclipse.jpt.core.internal.facet.JpaFacetInstallDataModelProperties;
import org.eclipse.jpt.core.tests.internal.context.persistence.PersistenceUnitTestCase;
import org.eclipse.jpt.eclipselink.core.internal.EclipseLinkJpaPlatformProvider;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.EclipseLinkPersistenceUnit;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;

/**
 * PersistenceUnitTestCase
 */
public abstract class EclipseLinkPersistenceUnitTestCase extends PersistenceUnitTestCase
{
	protected EclipseLinkPersistenceUnit subject;

	protected PropertyValueModel<EclipseLinkPersistenceUnit> subjectHolder;

	// ********** constructors **********
	protected EclipseLinkPersistenceUnitTestCase(String name) {
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
	protected IDataModel buildJpaConfigDataModel() {
		IDataModel dataModel = super.buildJpaConfigDataModel();
		dataModel.setProperty(JpaFacetInstallDataModelProperties.PLATFORM_ID, EclipseLinkJpaPlatformProvider.ID);
		return dataModel;
	}
	
	@Override
	protected EclipseLinkPersistenceUnit getPersistenceUnit() {
		return (EclipseLinkPersistenceUnit) super.getPersistenceUnit();
	}
}

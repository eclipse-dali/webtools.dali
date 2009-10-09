/*******************************************************************************
* Copyright (c) 2009 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.eclipselink2_0.core.tests.internal.context.persistence;

import org.eclipse.jpt.core.internal.facet.JpaFacetInstallDataModelProperties;
import org.eclipse.jpt.core.tests.internal.context.persistence.PersistenceUnitTestCase;
import org.eclipse.jpt.eclipselink.core.internal.v2_0.EclipseLink2_0JpaPlatformProvider;
import org.eclipse.jpt.eclipselink.core.internal.v2_0.context.persistence.EclipseLinkPersistenceUnit2_0;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.wst.common.componentcore.datamodel.properties.IFacetDataModelProperties;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;

/**
 *  EclipseLink2_0PersistenceUnitTestCase
 */
public abstract class EclipseLink2_0PersistenceUnitTestCase extends PersistenceUnitTestCase
{
	protected EclipseLinkPersistenceUnit2_0 subject;

	protected PropertyValueModel<EclipseLinkPersistenceUnit2_0> subjectHolder;

	// ********** constructors **********
	protected EclipseLink2_0PersistenceUnitTestCase(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.subject = this.getPersistenceUnit();
		this.subjectHolder = new SimplePropertyValueModel<EclipseLinkPersistenceUnit2_0>(this.subject);
		this.populatePu();
	}

	@Override
	protected IDataModel buildJpaConfigDataModel() {
		IDataModel dataModel = super.buildJpaConfigDataModel();
		dataModel.setProperty(IFacetDataModelProperties.FACET_VERSION_STR, "2.0"); //$NON-NLS-1$	
		dataModel.setProperty(JpaFacetInstallDataModelProperties.PLATFORM_ID, EclipseLink2_0JpaPlatformProvider.ID);
		return dataModel;
	}

	@Override
	protected EclipseLinkPersistenceUnit2_0 getPersistenceUnit() {
		return (EclipseLinkPersistenceUnit2_0) super.getPersistenceUnit();
	}

}
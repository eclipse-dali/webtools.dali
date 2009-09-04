/*******************************************************************************
* Copyright (c) 2009 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.core.tests.internal.jpa2.context.persistence;

import org.eclipse.jpt.core.internal.facet.JpaFacetDataModelProperties;
import org.eclipse.jpt.core.internal.facet.JpaFacetDataModelProvider;
import org.eclipse.jpt.core.internal.jpa2.Generic2_0JpaPlatformProvider;
import org.eclipse.jpt.core.jpa2.context.persistence.PersistenceUnit2_0;
import org.eclipse.jpt.core.tests.internal.context.persistence.PersistenceUnitTestCase;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.wst.common.componentcore.datamodel.properties.IFacetDataModelProperties;
import org.eclipse.wst.common.frameworks.datamodel.DataModelFactory;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;

/**
 *  GenericPersistenceUnit2_0Tests
 */
public abstract class Generic2_0PersistenceUnitTests extends PersistenceUnitTestCase
{
	protected PersistenceUnit2_0 subject;

	protected PropertyValueModel<PersistenceUnit2_0> subjectHolder;

	// ********** constructors **********
	protected Generic2_0PersistenceUnitTests(String name) {
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
	protected IDataModel buildJpaConfigDataModel() {
		IDataModel dataModel = DataModelFactory.createDataModel(new JpaFacetDataModelProvider());		
		dataModel.setProperty(IFacetDataModelProperties.FACET_VERSION_STR, "2.0"); //$NON-NLS-1$
		dataModel.setProperty(JpaFacetDataModelProperties.PLATFORM_ID, Generic2_0JpaPlatformProvider.ID);
		dataModel.setProperty(JpaFacetDataModelProperties.CREATE_ORM_XML, Boolean.TRUE);
		return dataModel;
	}

	@Override
	protected PersistenceUnit2_0 getPersistenceUnit() {
		return (PersistenceUnit2_0) super.getPersistenceUnit();
	}

}

/*******************************************************************************
 *  Copyright (c) 2009, 2011  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.jpa2.context;

import org.eclipse.jpt.jpa.core.JpaFacet;
import org.eclipse.jpt.jpa.core.internal.facet.JpaFacetDataModelProperties;
import org.eclipse.jpt.jpa.core.internal.facet.JpaFacetInstallDataModelProperties;
import org.eclipse.jpt.jpa.core.platform.GenericPlatform;
import org.eclipse.jpt.jpa.core.platform.JpaPlatformDescription;
import org.eclipse.jpt.jpa.core.tests.internal.context.ContextModelTestCase;
import org.eclipse.wst.common.componentcore.datamodel.properties.IFacetDataModelProperties;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;

public abstract class Generic2_0ContextModelTestCase extends ContextModelTestCase
{
	protected Generic2_0ContextModelTestCase(String name) {
		super(name);
	}
	
	@Override
	protected IDataModel buildJpaConfigDataModel() {
		IDataModel dataModel = super.buildJpaConfigDataModel();
		dataModel.setProperty(IFacetDataModelProperties.FACET_VERSION_STR, JpaFacet.VERSION_2_0.getVersionString());
		dataModel.setProperty(JpaFacetDataModelProperties.PLATFORM, GenericPlatform.VERSION_2_0);
		dataModel.setProperty(JpaFacetInstallDataModelProperties.CREATE_ORM_XML, Boolean.TRUE);
		return dataModel;
	}

	@Override
	protected String getJpaFacetVersionString() {
		return JpaFacet.VERSION_2_0.getVersionString();
	}

	@Override
	protected JpaPlatformDescription getJpaPlatformDescription() {
		return GenericPlatform.VERSION_2_0;
	}
}

/*******************************************************************************
 *  Copyright (c) 2009  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink1_2.core.tests.internal.context;

import org.eclipse.jpt.core.internal.facet.JpaFacetDataModelProperties;
import org.eclipse.jpt.core.internal.facet.JpaFacetInstallDataModelProperties;
import org.eclipse.jpt.eclipselink.core.internal.v1_2.EclipseLink1_2JpaPlatformProvider;
import org.eclipse.jpt.eclipselink.core.tests.internal.context.EclipseLinkContextModelTestCase;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;

public abstract class EclipseLink1_2ContextModelTestCase extends EclipseLinkContextModelTestCase
{
	public static final String JPA_ANNOTATIONS_PACKAGE_NAME = "javax.persistence"; //$NON-NLS-1$

	protected EclipseLink1_2ContextModelTestCase(String name) {
		super(name);
	}
	
	@Override
	protected IDataModel buildJpaConfigDataModel() {
		IDataModel dataModel = super.buildJpaConfigDataModel();
		dataModel.setProperty(JpaFacetDataModelProperties.PLATFORM_ID, EclipseLink1_2JpaPlatformProvider.ID);
		dataModel.setProperty(JpaFacetInstallDataModelProperties.CREATE_ORM_XML, Boolean.TRUE);
		return dataModel;
	}	
}

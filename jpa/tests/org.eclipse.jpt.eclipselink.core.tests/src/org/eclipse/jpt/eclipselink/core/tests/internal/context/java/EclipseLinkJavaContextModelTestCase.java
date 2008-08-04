/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.tests.internal.context.java;

import org.eclipse.jpt.core.internal.facet.JpaFacetDataModelProperties;
import org.eclipse.jpt.core.internal.facet.JpaFacetDataModelProvider;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;
import org.eclipse.jpt.eclipselink.core.internal.EclipseLinkJpaPlatform;
import org.eclipse.wst.common.frameworks.datamodel.DataModelFactory;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;

public class EclipseLinkJavaContextModelTestCase extends ContextModelTestCase
{	
	
	public static final String ECLIPSELINK_ANNOTATIONS_PACKAGE_NAME = "org.eclipse.persistence.annotations";

	public EclipseLinkJavaContextModelTestCase(String name) {
		super(name);
	}

	@Override
	protected IDataModel buildJpaConfigDataModel() {
		IDataModel dataModel = DataModelFactory.createDataModel(new JpaFacetDataModelProvider());		
		dataModel.setProperty(JpaFacetDataModelProperties.PLATFORM_ID, EclipseLinkJpaPlatform.ID);
		return dataModel;
	}

}

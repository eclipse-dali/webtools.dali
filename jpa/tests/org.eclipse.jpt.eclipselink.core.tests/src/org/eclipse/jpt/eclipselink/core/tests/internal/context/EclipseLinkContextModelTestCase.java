/*******************************************************************************
 *  Copyright (c) 2008, 2009 Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.tests.internal.context;

import org.eclipse.jpt.core.internal.facet.JpaFacetInstallDataModelProperties;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;
import org.eclipse.jpt.eclipselink.core.EclipseLinkJpaProject;
import org.eclipse.jpt.eclipselink.core.context.java.JavaEclipseLinkEntity;
import org.eclipse.jpt.eclipselink.core.internal.EclipseLinkJpaPlatformProvider;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.EclipseLinkPersistenceUnit;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;

public abstract class EclipseLinkContextModelTestCase
	extends ContextModelTestCase
{
	protected EclipseLinkContextModelTestCase(String name) {
		super(name);
	}
	
	
	@Override
	protected IDataModel buildJpaConfigDataModel() {
		IDataModel dataModel = super.buildJpaConfigDataModel();
		dataModel.setProperty(JpaFacetInstallDataModelProperties.PLATFORM_ID, EclipseLinkJpaPlatformProvider.ID);
		return dataModel;
	}
	
	@Override
	protected EclipseLinkJpaProject getJpaProject() {
		return (EclipseLinkJpaProject) super.getJpaProject();
	}
	
	@Override
	protected EclipseLinkPersistenceUnit getPersistenceUnit() {
		return (EclipseLinkPersistenceUnit) super.getPersistenceUnit();
	}
	
	@Override
	protected JavaEclipseLinkEntity getJavaEntity() {
		return (JavaEclipseLinkEntity) getJavaPersistentType().getMapping();
	}
}

/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.tests.internal.context.orm;

import org.eclipse.jpt.core.internal.operations.OrmFileCreationDataModelProperties;
import org.eclipse.jpt.core.tests.internal.projects.TestJpaProject;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLinkEntityMappings;
import org.eclipse.jpt.eclipselink.core.internal.operations.EclipseLinkOrmFileCreationDataModelProvider;
import org.eclipse.jpt.eclipselink.core.internal.operations.EclipseLinkOrmFileCreationOperation;
import org.eclipse.jpt.eclipselink.core.internal.resource.orm.EclipseLinkOrmXmlResourceProvider;
import org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmXmlResource;
import org.eclipse.jpt.eclipselink.core.tests.internal.context.EclipseLinkContextModelTestCase;
import org.eclipse.wst.common.frameworks.datamodel.DataModelFactory;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;

public abstract class EclipseLinkOrmContextModelTestCase
	extends EclipseLinkContextModelTestCase
{
	protected EclipseLinkOrmXmlResourceProvider eclipseLinkOrmResourceModelProvider;
	
	
	protected EclipseLinkOrmContextModelTestCase(String name) {
		super(name);
	}
	
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.eclipseLinkOrmResourceModelProvider = 
			EclipseLinkOrmXmlResourceProvider.getDefaultXmlResourceProvider(getJavaProject().getProject());
	}
	
	@Override
	protected TestJpaProject buildJpaProject(String projectName, boolean autoBuild, IDataModel jpaConfig) throws Exception {
		TestJpaProject testJpaProject = super.buildJpaProject(projectName, autoBuild, jpaConfig);
		
		EclipseLinkOrmFileCreationOperation operation = 
			new EclipseLinkOrmFileCreationOperation(buildEclipseLinkOrmConfig(testJpaProject));
		operation.execute(null, null);
		
		return testJpaProject;
	}
	
	protected IDataModel buildEclipseLinkOrmConfig(TestJpaProject testJpaProject) {
		IDataModel dataModel = 
			DataModelFactory.createDataModel(new EclipseLinkOrmFileCreationDataModelProvider());		
		dataModel.setProperty(OrmFileCreationDataModelProperties.PROJECT_NAME, testJpaProject.getProject().getName());
		dataModel.setProperty(OrmFileCreationDataModelProperties.ADD_TO_PERSISTENCE_UNIT, Boolean.TRUE);
		return dataModel;
	}
	
	@Override
	protected void tearDown() throws Exception {
		this.eclipseLinkOrmResourceModelProvider = null;
		super.tearDown();
	}
	
	@Override
	protected EclipseLinkOrmXmlResource getOrmXmlResource() {
		return this.eclipseLinkOrmResourceModelProvider.getXmlResource();
	}
	
	@Override
	protected EclipseLinkEntityMappings getEntityMappings() {
		return (EclipseLinkEntityMappings) super.getEntityMappings();
	}
}

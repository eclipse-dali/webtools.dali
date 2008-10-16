/*******************************************************************************
 *  Copyright (c) 2008  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.tests.internal.context.orm;

import org.eclipse.jpt.core.context.MappingFile;
import org.eclipse.jpt.core.context.orm.EntityMappings;
import org.eclipse.jpt.core.internal.operations.OrmFileCreationDataModelProperties;
import org.eclipse.jpt.core.tests.internal.projects.TestJpaProject;
import org.eclipse.jpt.eclipselink.core.internal.operations.EclipseLinkOrmFileCreationDataModelProvider;
import org.eclipse.jpt.eclipselink.core.internal.operations.EclipseLinkOrmFileCreationOperation;
import org.eclipse.jpt.eclipselink.core.internal.resource.orm.EclipseLinkOrmResourceModelProvider;
import org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmResource;
import org.eclipse.jpt.eclipselink.core.tests.internal.context.EclipseLinkContextModelTestCase;
import org.eclipse.wst.common.frameworks.datamodel.DataModelFactory;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;

public abstract class EclipseLinkOrmContextModelTestCase
	extends EclipseLinkContextModelTestCase
{
	protected EclipseLinkOrmResourceModelProvider eclipseLinkOrmResourceModelProvider;
	
	
	protected EclipseLinkOrmContextModelTestCase(String name) {
		super(name);
	}
	
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.eclipseLinkOrmResourceModelProvider = 
			EclipseLinkOrmResourceModelProvider.getDefaultModelProvider(getJavaProject().getProject());
	}
	
	@Override
	protected TestJpaProject buildJpaProject(String projectName, boolean autoBuild, IDataModel jpaConfig) throws Exception {
		TestJpaProject testJpaProject = super.buildJpaProject(projectName, autoBuild, jpaConfig);
		
		IDataModel dataModel = 
			DataModelFactory.createDataModel(new EclipseLinkOrmFileCreationDataModelProvider());		
		dataModel.setProperty(OrmFileCreationDataModelProperties.PROJECT_NAME, testJpaProject.getProject().getName());
		dataModel.setProperty(OrmFileCreationDataModelProperties.ADD_TO_PERSISTENCE_UNIT, Boolean.TRUE);
		EclipseLinkOrmFileCreationOperation operation = new EclipseLinkOrmFileCreationOperation(dataModel);
		operation.execute(null, null);
		
		return testJpaProject;
	}
	
	@Override
	protected void tearDown() throws Exception {
		this.eclipseLinkOrmResourceModelProvider = null;
		super.tearDown();
	}
	
	protected EclipseLinkOrmResource ormResource() {
		return this.eclipseLinkOrmResourceModelProvider.getResource();
	}
	
	protected EntityMappings entityMappings() {
		MappingFile mappingFile = persistenceUnit().mappingFileRefs().next().getMappingFile();
		return (mappingFile == null) ? null : (EntityMappings) mappingFile.getRoot();
	}
}

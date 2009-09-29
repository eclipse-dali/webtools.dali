/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink1_1.core.tests.internal.context.orm;

import org.eclipse.jpt.core.internal.facet.JpaFacetDataModelProperties;
import org.eclipse.jpt.core.internal.operations.OrmFileCreationDataModelProperties;
import org.eclipse.jpt.core.resource.xml.JpaXmlResource;
import org.eclipse.jpt.core.tests.internal.projects.TestJpaProject;
import org.eclipse.jpt.eclipselink.core.context.orm.EclipseLinkEntityMappings;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.operations.EclipseLink1_1OrmFileCreationDataModelProvider;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.operations.EclipseLink1_1OrmFileCreationOperation;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.eclipselink1_1.core.tests.internal.context.EclipseLink1_1ContextModelTestCase;
import org.eclipse.wst.common.frameworks.datamodel.DataModelFactory;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;

public abstract class EclipseLink1_1OrmContextModelTestCase
	extends EclipseLink1_1ContextModelTestCase
{
	protected JpaXmlResource eclipseLink1_1OrmXmlResource;
	
	
	protected EclipseLink1_1OrmContextModelTestCase(String name) {
		super(name);
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.eclipseLink1_1OrmXmlResource = getJpaProject().getDefaultEclipseLinkOrmXmlResource();
	}
	@Override
	protected IDataModel buildJpaConfigDataModel() {
		IDataModel dataModel = super.buildJpaConfigDataModel();
		// don't create default orm.xml - instead build eclipselink-orm.xml
		dataModel.setProperty(JpaFacetDataModelProperties.CREATE_ORM_XML, Boolean.FALSE);
		return dataModel;
	}
	
	@Override
	protected TestJpaProject buildJpaProject(String projectName, boolean autoBuild, IDataModel jpaConfig) throws Exception {
		TestJpaProject testJpaProject = super.buildJpaProject(projectName, autoBuild, jpaConfig);
		
		EclipseLink1_1OrmFileCreationOperation operation = 
			new EclipseLink1_1OrmFileCreationOperation(buildEclipseLinkOrmConfig(testJpaProject));
		operation.execute(null, null);
		
		return testJpaProject;
	}
	
	protected IDataModel buildEclipseLinkOrmConfig(TestJpaProject testJpaProject) {
		IDataModel dataModel = 
			DataModelFactory.createDataModel(new EclipseLink1_1OrmFileCreationDataModelProvider());		
		dataModel.setProperty(OrmFileCreationDataModelProperties.PROJECT_NAME, testJpaProject.getProject().getName());
		dataModel.setProperty(OrmFileCreationDataModelProperties.ADD_TO_PERSISTENCE_UNIT, Boolean.TRUE);
		return dataModel;
	}
	
	@Override
	protected void tearDown() throws Exception {
		this.eclipseLink1_1OrmXmlResource = null;
		super.tearDown();
	}
	
	@Override
	protected JpaXmlResource getOrmXmlResource() {
		return this.eclipseLink1_1OrmXmlResource;
	}
	
	@Override
	protected XmlEntityMappings getXmlEntityMappings() {
		return (XmlEntityMappings) super.getXmlEntityMappings();
	}
	
	@Override
	protected EclipseLinkEntityMappings getEntityMappings() {
		return (EclipseLinkEntityMappings) super.getEntityMappings();
	}
}

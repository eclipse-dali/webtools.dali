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

import org.eclipse.jpt.core.internal.facet.JpaFacetDataModelProperties;
import org.eclipse.jpt.core.internal.facet.JpaFacetDataModelProvider;
import org.eclipse.jpt.core.internal.operations.OrmFileCreationDataModelProperties;
import org.eclipse.jpt.core.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.core.resource.persistence.XmlPersistenceUnit;
import org.eclipse.jpt.core.resource.xml.JpaXmlResource;
import org.eclipse.jpt.core.tests.internal.projects.TestJpaProject;
import org.eclipse.jpt.eclipselink.core.internal.EclipseLinkJpaPlatformProvider;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.EclipseLinkPersistenceUnit;
import org.eclipse.jpt.eclipselink.core.internal.operations.EclipseLinkOrmFileCreationDataModelProvider;
import org.eclipse.jpt.eclipselink.core.tests.internal.context.orm.EclipseLinkOrmContextModelTestCase;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.wst.common.frameworks.datamodel.DataModelFactory;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;

public class EclipseLinkPersistenceUnitTests
	extends EclipseLinkOrmContextModelTestCase
{
	public EclipseLinkPersistenceUnitTests(String name) {
		super(name);
	}
	
	
	@Override
	protected IDataModel buildJpaConfigDataModel() {
		IDataModel dataModel = DataModelFactory.createDataModel(new JpaFacetDataModelProvider());		
		dataModel.setProperty(JpaFacetDataModelProperties.PLATFORM_ID, EclipseLinkJpaPlatformProvider.ID);
		dataModel.setProperty(JpaFacetDataModelProperties.CREATE_ORM_XML, Boolean.TRUE);
		return dataModel;
	}
	
	@Override
	protected IDataModel buildEclipseLinkOrmConfig(TestJpaProject testJpaProject) {
		IDataModel dataModel = 
			DataModelFactory.createDataModel(new EclipseLinkOrmFileCreationDataModelProvider());		
		dataModel.setProperty(OrmFileCreationDataModelProperties.PROJECT_NAME, testJpaProject.getProject().getName());
		dataModel.setProperty(OrmFileCreationDataModelProperties.ADD_TO_PERSISTENCE_UNIT, Boolean.FALSE);
		return dataModel;
	}
	
	
	public void testUpdateEclipseLinkImpliedMappingFileRef1() throws Exception {
		EclipseLinkPersistenceUnit persistenceUnit = getPersistenceUnit();
		
		// test that there is one initially
		JpaXmlResource eclipseLinkOrmResource = getOrmXmlResource();
		assertTrue(eclipseLinkOrmResource.fileExists());
		assertNotNull(persistenceUnit.getImpliedMappingFileRef());
		assertNotNull(persistenceUnit.getImpliedEclipseLinkMappingFileRef());
		
		// remove eclipselink-orm.xml
		deleteResource(eclipseLinkOrmResource);
		
		assertFalse(eclipseLinkOrmResource.fileExists());
		assertNotNull(persistenceUnit.getImpliedMappingFileRef());
		assertNull(persistenceUnit.getImpliedEclipseLinkMappingFileRef());
	}
	
	public void testUpdateEclipseLinkImpliedMappingFileRef2() {
		XmlPersistenceUnit xmlPersistenceUnit = getXmlPersistenceUnit();
		EclipseLinkPersistenceUnit persistenceUnit = getPersistenceUnit();
		
		// test that there is one initially
		JpaXmlResource eclipseLinkOrmResource = getOrmXmlResource();
		assertTrue(eclipseLinkOrmResource.fileExists());
		assertNotNull(persistenceUnit.getImpliedMappingFileRef());
		assertNotNull(persistenceUnit.getImpliedEclipseLinkMappingFileRef());
		
		// add specified eclipselink-orm.xml
		XmlMappingFileRef xmlMappingFileRef = PersistenceFactory.eINSTANCE.createXmlMappingFileRef();
		xmlMappingFileRef.setFileName("META-INF/eclipselink-orm.xml");
		xmlPersistenceUnit.getMappingFiles().add(xmlMappingFileRef);
		
		assertEquals(1, CollectionTools.size(persistenceUnit.specifiedMappingFileRefs()));
		
		assertTrue(eclipseLinkOrmResource.fileExists());
		assertNotNull(persistenceUnit.getImpliedMappingFileRef());
		assertNull(persistenceUnit.getImpliedEclipseLinkMappingFileRef());
	}
	
	public void testUpdateEclipseLinkImpliedMappingFileRef3() {
		EclipseLinkPersistenceUnit persistenceUnit = getPersistenceUnit();
		
		// test that there is one initially
		JpaXmlResource eclipseLinkOrmResource = getOrmXmlResource();
		assertTrue(eclipseLinkOrmResource.fileExists());
		assertNotNull(persistenceUnit.getImpliedMappingFileRef());
		assertNotNull(persistenceUnit.getImpliedEclipseLinkMappingFileRef());
		
		// ignore in persistence unit
		persistenceUnit.getGeneralProperties().setExcludeEclipselinkOrm(Boolean.TRUE);
		
		assertTrue(eclipseLinkOrmResource.fileExists());
		assertNotNull(persistenceUnit.getImpliedMappingFileRef());
		assertNull(persistenceUnit.getImpliedEclipseLinkMappingFileRef());
	}
}

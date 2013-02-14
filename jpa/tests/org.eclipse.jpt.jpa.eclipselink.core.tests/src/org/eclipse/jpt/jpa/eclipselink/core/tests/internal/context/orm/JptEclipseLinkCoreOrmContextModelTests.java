/*******************************************************************************
 *  Copyright (c) 2007, 2013 Oracle. 
 *  All rights reserved.  This program and the accompanying materials 
 *  are made available under the terms of the Eclipse Public License v1.0 
 *  which accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.tests.internal.context.orm;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class JptEclipseLinkCoreOrmContextModelTests extends TestCase
{	
	public static Test suite() {
		TestSuite suite = new TestSuite(JptEclipseLinkCoreOrmContextModelTests.class.getName());
		suite.addTestSuite(EclipseLinkEntityMappingsTests.class);
		suite.addTestSuite(EclipseLinkPersistenceUnitMetadataTests.class);
		suite.addTestSuite(EclipseLinkPersistenceUnitDefaultsTests.class);
		suite.addTestSuite(EclipseLinkOrmEmbeddableTests.class);
		suite.addTestSuite(EclipseLinkOrmEntityTests.class);
		suite.addTestSuite(EclipseLinkOrmMappedSuperclassTests.class);
		suite.addTestSuite(EclipseLinkOrmBasicMappingTests.class);
		suite.addTestSuite(EclipseLinkOrmIdMappingTests.class);
		suite.addTestSuite(EclipseLinkOrmManyToManyMappingTests.class);
		suite.addTestSuite(EclipseLinkOrmManyToOneMappingTests.class);
		suite.addTestSuite(EclipseLinkOrmOneToManyMappingTests.class);
		suite.addTestSuite(EclipseLinkOrmOneToOneMappingTests.class);
		suite.addTestSuite(EclipseLinkOrmPersistentAttributeTests.class);
		suite.addTestSuite(EclipseLinkOrmVersionMappingTests.class);
		suite.addTestSuite(EclipseLinkOrmTransientMappingTests.class);
		suite.addTestSuite(EclipseLinkOrmConverterTests.class);
		suite.addTestSuite(EclipseLinkOrmObjectTypeConverterTests.class);
		suite.addTestSuite(EclipseLinkOrmStructConverterTests.class);
		suite.addTestSuite(EclipseLinkOrmTypeConverterTests.class);
		suite.addTestSuite(EclipseLinkOrmCachingTests.class);	
		suite.addTestSuite(EclipseLink1_1OrmPersistentAttributeTests.class);		
		suite.addTestSuite(EclipseLink1_1OrmPersistentTypeTests.class);	
		suite.addTestSuite(EclipseLink1_1OrmTransientMappingTests.class);	
		suite.addTestSuite(EclipseLink2_0OrmCollectionTableTests.class);
		suite.addTestSuite(EclipseLink2_0OrmElementCollectionMappingTests.class);
		suite.addTestSuite(EclipseLink2_0OrmEntityTests.class);
		suite.addTestSuite(EclipseLink2_0OrmManyToManyMappingTests.class);
		suite.addTestSuite(EclipseLink2_0OrmManyToOneMappingTests.class);
		suite.addTestSuite(EclipseLink2_0OrmMappedSuperclassTests.class);
		suite.addTestSuite(EclipseLink2_0OrmOneToOneMappingTests.class);
		suite.addTestSuite(EclipseLink2_0OrmOneToManyMappingTests.class);
		suite.addTestSuite(EclipseLink2_1EntityMappingsTests.class);
		suite.addTestSuite(EclipseLink2_1PersistenceUnitDefaultsTests.class);
		suite.addTestSuite(EclipseLink2_1OrmPersistentTypeTests.class);
		suite.addTestSuite(EclipseLink2_1OrmEmbeddableTests.class);
		suite.addTestSuite(EclipseLink2_1OrmEntityTests.class);
		suite.addTestSuite(EclipseLink2_1OrmMappedSuperclassTests.class);
		suite.addTestSuite(EclipseLink2_3OrmMultitenancyTests.class);
		suite.addTestSuite(EclipseLink2_5EntityMappingsTests.class);
		suite.addTestSuite(EclipseLink2_5OrmEntityTests.class);
		return suite;
	}
	
	
	private JptEclipseLinkCoreOrmContextModelTests() {
		throw new UnsupportedOperationException();
	}
}

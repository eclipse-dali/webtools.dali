/*******************************************************************************
 *  Copyright (c) 2007, 2010 Oracle. 
 *  All rights reserved.  This program and the accompanying materials 
 *  are made available under the terms of the Eclipse Public License v1.0 
 *  which accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.tests.internal.context.orm;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class JptCoreOrmContextModelTests extends TestCase
{
	public static Test suite() {
		TestSuite suite = new TestSuite(JptCoreOrmContextModelTests.class.getName());
		suite.addTestSuite(OrmXmlTests.class);
		suite.addTestSuite(EntityMappingsTests.class);
		suite.addTestSuite(PersistenceUnitMetadataTests.class);
		suite.addTestSuite(PersistenceUnitDefaultsTests.class);
		suite.addTestSuite(GenericOrmPersistentAttributeTests.class);
		suite.addTestSuite(GenericOrmPersistentTypeTests.class);
		suite.addTestSuite(OrmAssociationOverrideTests.class);
		suite.addTestSuite(OrmAttributeOverrideTests.class);
		suite.addTestSuite(OrmBasicMappingTests.class);
		suite.addTestSuite(OrmCascadeTests.class);
		suite.addTestSuite(OrmColumnTests.class);
		suite.addTestSuite(OrmDiscriminatorColumnTests.class);
		suite.addTestSuite(OrmIdMappingTests.class);
		suite.addTestSuite(OrmEmbeddableTests.class);
		suite.addTestSuite(OrmEmbeddedMappingTests.class);
		suite.addTestSuite(OrmEmbeddedIdMappingTests.class);
		suite.addTestSuite(OrmEntityTests.class);
		suite.addTestSuite(OrmGeneratedValueTests.class);
		suite.addTestSuite(OrmJoinColumnTests.class);
		suite.addTestSuite(OrmJoinTableTests.class);
		suite.addTestSuite(OrmMappedSuperclassTests.class);
		suite.addTestSuite(OrmManyToManyMappingTests.class);
		suite.addTestSuite(OrmManyToOneMappingTests.class);
		suite.addTestSuite(OrmNamedQueryTests.class);
		suite.addTestSuite(OrmNamedNativeQueryTests.class);
		suite.addTestSuite(OrmOneToManyMappingTests.class);
		suite.addTestSuite(OrmOneToOneMappingTests.class);
		suite.addTestSuite(OrmPrimaryKeyJoinColumnTests.class);
		suite.addTestSuite(OrmQueryHintTests.class);
		suite.addTestSuite(OrmSecondaryTableTests.class);
		suite.addTestSuite(OrmSequenceGeneratorTests.class);
		suite.addTestSuite(OrmTableGeneratorTests.class);
		suite.addTestSuite(OrmTableTests.class);
		suite.addTestSuite(OrmTransientMappingTests.class);
		suite.addTestSuite(OrmVersionMappingTests.class);
		return suite;
	}
	
	private JptCoreOrmContextModelTests() {
		throw new UnsupportedOperationException();
	}
}

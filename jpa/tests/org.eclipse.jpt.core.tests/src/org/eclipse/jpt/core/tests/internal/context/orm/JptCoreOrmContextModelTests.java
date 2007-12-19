/*******************************************************************************
 *  Copyright (c) 2007 Oracle. 
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
		return suite(true);
	}
	
	public static Test suite(boolean all) {
		TestSuite suite = new TestSuite(JptCoreOrmContextModelTests.class.getName());
		suite.addTestSuite(OrmXmlTests.class);
		suite.addTestSuite(EntityMappingsTests.class);
		suite.addTestSuite(PersistenceUnitMetadataTests.class);
		suite.addTestSuite(PersistenceUnitDefaultsTests.class);
		suite.addTestSuite(XmlPersistentTypeTests.class);
		suite.addTestSuite(XmlEntityTests.class);
		suite.addTestSuite(XmlEmbeddableTests.class);
		suite.addTestSuite(XmlMappedSuperclassTests.class);
		suite.addTestSuite(XmlBasicMappingTests.class);
		suite.addTestSuite(XmlIdMappingTests.class);
		suite.addTestSuite(XmlVersionMappingTests.class);
		suite.addTestSuite(XmlEmbeddedMappingTests.class);
		suite.addTestSuite(XmlTableTests.class);
		suite.addTestSuite(XmlSecondaryTableTests.class);
		suite.addTestSuite(XmlColumnTests.class);
		suite.addTestSuite(XmlSequenceGeneratorTests.class);
		suite.addTestSuite(XmlTableGeneratorTests.class);
		suite.addTestSuite(XmlGeneratedValueTests.class);
		suite.addTestSuite(XmlDiscriminatorColumnTests.class);
		suite.addTestSuite(XmlPrimaryKeyJoinColumnTests.class);
		suite.addTestSuite(XmlAttributeOverrideTests.class);
		return suite;
	}
	
	private JptCoreOrmContextModelTests() {
		throw new UnsupportedOperationException();
	}
}

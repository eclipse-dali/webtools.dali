/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.tests.internal.resource.java;

import junit.framework.Test;
import junit.framework.TestSuite;

public class JptJavaResourceTests {

	public static Test suite() {
		return suite(true);
	}
	
	public static Test suite(boolean all) {
		TestSuite suite = new TestSuite(JptJavaResourceTests.class.getName());
		suite.addTestSuite(JpaCompilationUnitResourceTests.class);
		suite.addTestSuite(JavaPersistentTypeResourceTests.class);
		suite.addTestSuite(JavaPersistentAttributeResourceTests.class);
		suite.addTestSuite(JPTToolsTests.class);
		suite.addTestSuite(EmbeddableTests.class);
		suite.addTestSuite(EntityTests.class);
		suite.addTestSuite(MappedSuperclassTests.class);
		suite.addTestSuite(BasicTests.class);
		suite.addTestSuite(IdTests.class);
		suite.addTestSuite(EmbeddedTests.class);
		suite.addTestSuite(EmbeddedIdTests.class);
		suite.addTestSuite(ManyToManyTests.class);
		suite.addTestSuite(ManyToOneTests.class);
		suite.addTestSuite(OneToManyTests.class);
		suite.addTestSuite(OneToOneTests.class);
		suite.addTestSuite(TransientTests.class);
		suite.addTestSuite(VersionTests.class);
		suite.addTestSuite(TableTests.class);
		suite.addTestSuite(SecondaryTableTests.class);
		suite.addTestSuite(SecondaryTablesTests.class);
		suite.addTestSuite(JoinTableTests.class);
		suite.addTestSuite(ColumnTests.class);
		suite.addTestSuite(JoinColumnTests.class);
		suite.addTestSuite(JoinColumnsTests.class);
		suite.addTestSuite(AttributeOverrideTests.class);
		suite.addTestSuite(AttributeOverridesTests.class);
		return suite;
	}

	private JptJavaResourceTests() {
		super();
		throw new UnsupportedOperationException();
	}

}

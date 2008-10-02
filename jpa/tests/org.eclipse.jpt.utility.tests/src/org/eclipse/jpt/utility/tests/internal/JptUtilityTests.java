/*******************************************************************************
 * Copyright (c) 2005, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.tests.internal;

import junit.framework.Test;
import junit.framework.TestSuite;
import org.eclipse.jpt.utility.tests.internal.iterators.JptUtilityIteratorsTests;
import org.eclipse.jpt.utility.tests.internal.model.JptUtilityModelTests;
import org.eclipse.jpt.utility.tests.internal.node.JptUtilityNodeTests;

/**
 * decentralize test creation code
 */
public class JptUtilityTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(JptUtilityTests.class.getPackage().getName());

		suite.addTest(JptUtilityIteratorsTests.suite());
		suite.addTest(JptUtilityModelTests.suite());
		suite.addTest(JptUtilityNodeTests.suite());

		suite.addTestSuite(BitToolsTests.class);
		suite.addTestSuite(BooleanHolderTests.class);
		suite.addTestSuite(ClasspathTests.class);
		suite.addTestSuite(ClassToolsTests.class);
		suite.addTestSuite(CollectionToolsTests.class);
		suite.addTestSuite(CounterTests.class);
		suite.addTestSuite(EmptyIterableTests.class);
		suite.addTestSuite(FileToolsTests.class);
		suite.addTestSuite(HashBagTests.class);
		suite.addTestSuite(IdentityHashBagTests.class);
		suite.addTestSuite(IndentingPrintWriterTests.class);
		suite.addTestSuite(JavaTypeTests.class);
		suite.addTestSuite(JDBCTypeTests.class);
		suite.addTestSuite(MethodSignatureTests.class);
		suite.addTestSuite(NameToolsTests.class);
		suite.addTestSuite(RangeTests.class);
		suite.addTestSuite(ReverseComparatorTests.class);
		suite.addTestSuite(SimpleAssociationTests.class);
		suite.addTestSuite(SimpleStackTests.class);
		suite.addTestSuite(StringToolsTests.class);
		suite.addTestSuite(SynchronizedBooleanTests.class);
		suite.addTestSuite(SynchronizedObjectTests.class);
		suite.addTestSuite(SynchronizedStackTests.class);
		suite.addTestSuite(XMLStringEncoderTests.class);

		return suite;
	}

	private JptUtilityTests() {
		super();
		throw new UnsupportedOperationException();
	}

}

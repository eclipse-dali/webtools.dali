/*******************************************************************************
 * Copyright (c) 2005, 2007 Oracle. All rights reserved.
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
import org.eclipse.jpt.utility.internal.ClassTools;
import org.eclipse.jpt.utility.tests.internal.iterators.JptUtilityIteratorsTests;

/**
 * decentralize test creation code
 */
public class JptUtilityTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(ClassTools.packageNameFor(JptUtilityTests.class));

		suite.addTest(JptUtilityIteratorsTests.suite());

		suite.addTestSuite(BitToolsTests.class);
		suite.addTestSuite(ClasspathTests.class);
		suite.addTestSuite(ClassToolsTests.class);
		suite.addTestSuite(CollectionToolsTests.class);
		suite.addTestSuite(FileToolsTests.class);
		suite.addTestSuite(HashBagTests.class);
		suite.addTestSuite(IndentingPrintWriterTests.class);
		suite.addTestSuite(JavaTypeTests.class);
		suite.addTestSuite(JDBCTypeTests.class);
		suite.addTestSuite(NameToolsTests.class);
		suite.addTestSuite(ReverseComparatorTests.class);
		suite.addTestSuite(StringToolsTests.class);
		suite.addTestSuite(XMLStringEncoderTests.class);

		return suite;
	}

	private JptUtilityTests() {
		super();
		throw new UnsupportedOperationException();
	}

}

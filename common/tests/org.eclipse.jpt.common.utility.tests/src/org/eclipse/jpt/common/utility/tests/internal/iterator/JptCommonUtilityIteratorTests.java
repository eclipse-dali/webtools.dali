/*******************************************************************************
 * Copyright (c) 2005, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.iterator;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * decentralize test creation code
 */
public class JptCommonUtilityIteratorTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(JptCommonUtilityIteratorTests.class.getPackage().getName());

		suite.addTestSuite(ArrayIteratorTests.class);
		suite.addTestSuite(ArrayListIteratorTests.class);
		suite.addTestSuite(ChainIteratorTests.class);
		suite.addTestSuite(CloneIteratorTests.class);
		suite.addTestSuite(CloneListIteratorTests.class);
		suite.addTestSuite(CompositeIteratorTests.class);
		suite.addTestSuite(CompositeListIteratorTests.class);
		suite.addTestSuite(EmptyIteratorTests.class);
		suite.addTestSuite(EmptyListIteratorTests.class);
		suite.addTestSuite(EnumerationIteratorTests.class);
		suite.addTestSuite(FilteringIteratorTests.class);
		suite.addTestSuite(GraphIteratorTests.class);
		suite.addTestSuite(IteratorToolsTests.class);
		suite.addTestSuite(PeekableIteratorTests.class);
		suite.addTestSuite(ReadOnlyCompositeListIteratorTests.class);
		suite.addTestSuite(ReadOnlyIteratorTests.class);
		suite.addTestSuite(ReadOnlyListIteratorTests.class);
		suite.addTestSuite(SimultaneousIteratorTests.class);
		suite.addTestSuite(SimultaneousListIteratorTests.class);
		suite.addTestSuite(SingleElementIteratorTests.class);
		suite.addTestSuite(SingleElementListIteratorTests.class);
		suite.addTestSuite(SuperIteratorWrapperTests.class);
		suite.addTestSuite(SynchronizedIteratorTests.class);
		suite.addTestSuite(SynchronizedListIteratorTests.class);
		suite.addTestSuite(TransformationIteratorTests.class);
		suite.addTestSuite(TransformationListIteratorTests.class);
		suite.addTestSuite(TreeIteratorTests.class);

		return suite;
	}

	private JptCommonUtilityIteratorTests() {
		super();
		throw new UnsupportedOperationException();
	}
}

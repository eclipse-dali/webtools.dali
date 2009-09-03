/*******************************************************************************
 * Copyright (c) 2005, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.tests.internal.iterators;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * decentralize test creation code
 */
public class JptUtilityIteratorsTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(JptUtilityIteratorsTests.class.getPackage().getName());

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
		suite.addTestSuite(GenericIteratorWrapperTests.class);
		suite.addTestSuite(GraphIteratorTests.class);
		suite.addTestSuite(PeekableIteratorTests.class);
		suite.addTestSuite(ReadOnlyCompositeListIteratorTests.class);
		suite.addTestSuite(ReadOnlyIteratorTests.class);
		suite.addTestSuite(ReadOnlyListIteratorTests.class);
		suite.addTestSuite(SingleElementIteratorTests.class);
		suite.addTestSuite(SingleElementListIteratorTests.class);
		suite.addTestSuite(SynchronizedIteratorTests.class);
		suite.addTestSuite(SynchronizedListIteratorTests.class);
		suite.addTestSuite(TransformationIteratorTests.class);
		suite.addTestSuite(TransformationListIteratorTests.class);
		suite.addTestSuite(TreeIteratorTests.class);

		return suite;
	}

	private JptUtilityIteratorsTests() {
		super();
		throw new UnsupportedOperationException();
	}

}

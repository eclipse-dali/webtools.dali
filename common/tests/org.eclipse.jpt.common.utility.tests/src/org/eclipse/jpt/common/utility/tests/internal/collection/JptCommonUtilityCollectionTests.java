/*******************************************************************************
 * Copyright (c) 2005, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.collection;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * decentralize test creation code
 */
public class JptCommonUtilityCollectionTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(JptCommonUtilityCollectionTests.class.getPackage().getName());

		suite.addTestSuite(ArrayQueueTests.class);
		suite.addTestSuite(ArrayStackTests.class);
		suite.addTestSuite(BagTests.class);
		suite.addTestSuite(CollectionToolsTests.class);
		suite.addTestSuite(FixedSizeArrayQueueTests.class);
		suite.addTestSuite(FixedSizeArrayStackTests.class);
		suite.addTestSuite(HashBagTests.class);
		suite.addTestSuite(IdentityHashBagTests.class);
		suite.addTestSuite(IdentityHashSetTests.class);
		suite.addTestSuite(LinkedQueueTests.class);
		suite.addTestSuite(LinkedStackTests.class);
		suite.addTestSuite(ListQueueTests.class);
		suite.addTestSuite(ListStackTests.class);
		suite.addTestSuite(ListToolsTests.class);
		suite.addTestSuite(MapToolsTests.class);
		suite.addTestSuite(NullElementListTests.class);
		suite.addTestSuite(RepeatingElementListTests.class);
		suite.addTestSuite(SynchronizedQueueTests.class);
		suite.addTestSuite(SynchronizedStackTests.class);
		suite.addTestSuite(TightMapTests.class);

		return suite;
	}

	private JptCommonUtilityCollectionTests() {
		super();
		throw new UnsupportedOperationException();
	}
}

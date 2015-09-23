/*******************************************************************************
 * Copyright (c) 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.deque;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * decentralize test creation code
 */
public class JptCommonUtilityDequeTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(JptCommonUtilityDequeTests.class.getPackage().getName());

		suite.addTestSuite(ArrayDequeTests.class);
		suite.addTestSuite(DequeToolsTests.class);
		suite.addTestSuite(EmptyDequeTests.class);
		suite.addTestSuite(FixedCapacityArrayDequeTests.class);
		suite.addTestSuite(FixedCapacityPriorityDequeTests.class);
		suite.addTestSuite(LinkedDequeTests.class);
		suite.addTestSuite(ListDequeTests.class);
		suite.addTestSuite(PriorityDequeTests.class);
		suite.addTestSuite(ReverseDequeTests.class);
		suite.addTestSuite(SynchronizedDequeTests.class);

		return suite;
	}

	private JptCommonUtilityDequeTests() {
		super();
		throw new UnsupportedOperationException();
	}
}

/*******************************************************************************
 * Copyright (c) 2005, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.stack;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * decentralize test creation code
 */
public class JptCommonUtilityStackTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(JptCommonUtilityStackTests.class.getPackage().getName());

		suite.addTestSuite(ArrayStackTests.class);
		suite.addTestSuite(DequeStackTests.class);
		suite.addTestSuite(EmptyStackTests.class);
		suite.addTestSuite(FixedCapacityArrayStackTests.class);
		suite.addTestSuite(LinkedStackTests.class);
		suite.addTestSuite(ListStackTests.class);
		suite.addTestSuite(StackToolsTests.class);
		suite.addTestSuite(SynchronizedStackTests.class);

		return suite;
	}

	private JptCommonUtilityStackTests() {
		super();
		throw new UnsupportedOperationException();
	}
}

/*******************************************************************************
 * Copyright (c) 2015, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.queue;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * decentralize test creation code
 */
public class JptCommonUtilityQueueTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(JptCommonUtilityQueueTests.class.getPackage().getName());

		suite.addTestSuite(ArrayQueueTests.class);
		suite.addTestSuite(CachingLinkedQueueTests.class);
		suite.addTestSuite(ConcurrentQueueTests.class);
		suite.addTestSuite(DequeQueueTests.class);
		suite.addTestSuite(EmptyQueueTests.class);
		suite.addTestSuite(FixedCapacityArrayQueueTests.class);
		suite.addTestSuite(LinkedQueueTests.class);
		suite.addTestSuite(ListQueueTests.class);
		suite.addTestSuite(PriorityQueueTests.class);
		suite.addTestSuite(QueueToolsTests.class);
		suite.addTestSuite(StackQueueTests.class);
		suite.addTestSuite(SynchronizedQueueTests.class);

		return suite;
	}

	private JptCommonUtilityQueueTests() {
		super();
		throw new UnsupportedOperationException();
	}
}

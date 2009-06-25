/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.tests.internal.iterables;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * decentralize test creation code
 */
public class JptUtilityIterablesTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(JptUtilityIterablesTests.class.getPackage().getName());

		suite.addTestSuite(ArrayIterableTests.class);
		suite.addTestSuite(ChainIterableTests.class);
		suite.addTestSuite(CompositeIterableTests.class);
		suite.addTestSuite(EmptyIterableTests.class);
		suite.addTestSuite(FilteringIterableTests.class);
		suite.addTestSuite(GraphIterableTests.class);
		suite.addTestSuite(LiveCloneIterableTests.class);
		suite.addTestSuite(ReadOnlyIterableTests.class);
		suite.addTestSuite(SingleElementIterableTests.class);
		suite.addTestSuite(StaticCloneIterableTests.class);
		suite.addTestSuite(TransformationIterableTests.class);
		suite.addTestSuite(TreeIterableTests.class);

		return suite;
	}

	private JptUtilityIterablesTests() {
		super();
		throw new UnsupportedOperationException();
	}

}

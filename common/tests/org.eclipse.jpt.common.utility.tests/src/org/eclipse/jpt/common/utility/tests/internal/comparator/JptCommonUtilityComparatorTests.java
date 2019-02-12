/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.comparator;

import junit.framework.Test;
import junit.framework.TestSuite;

public class JptCommonUtilityComparatorTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(JptCommonUtilityComparatorTests.class.getPackage().getName());

		suite.addTestSuite(BooleanComparatorTests.class);
		suite.addTestSuite(ComparatorToolsTests.class);
		suite.addTestSuite(ReverseComparatorTests.class);
		suite.addTestSuite(VersionComparatorTests.class);

		return suite;
	}

	private JptCommonUtilityComparatorTests() {
		super();
		throw new UnsupportedOperationException();
	}
}

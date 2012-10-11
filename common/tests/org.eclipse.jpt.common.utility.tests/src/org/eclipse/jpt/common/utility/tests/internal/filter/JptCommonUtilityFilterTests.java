/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.filter;

import junit.framework.Test;
import junit.framework.TestSuite;

public class JptCommonUtilityFilterTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(JptCommonUtilityFilterTests.class.getPackage().getName());

		suite.addTestSuite(ANDFilterTests.class);
		suite.addTestSuite(FilterTests.class);
		suite.addTestSuite(NOTFilterTests.class);
		suite.addTestSuite(NotNullFilterTests.class);
		suite.addTestSuite(ORFilterTests.class);
		suite.addTestSuite(XORFilterTests.class);

		return suite;
	}

	private JptCommonUtilityFilterTests() {
		super();
		throw new UnsupportedOperationException();
	}
}

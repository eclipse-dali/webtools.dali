/*******************************************************************************
 * Copyright (c) 2005, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.enumeration;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * decentralize test creation code
 */
public class JptCommonUtilityEnumerationTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(JptCommonUtilityEnumerationTests.class.getPackage().getName());

		suite.addTestSuite(EmptyEnumerationTests.class);
		suite.addTestSuite(EnumerationToolsTests.class);
		suite.addTestSuite(IteratorEnumerationTests.class);

		return suite;
	}

	private JptCommonUtilityEnumerationTests() {
		super();
		throw new UnsupportedOperationException();
	}
}

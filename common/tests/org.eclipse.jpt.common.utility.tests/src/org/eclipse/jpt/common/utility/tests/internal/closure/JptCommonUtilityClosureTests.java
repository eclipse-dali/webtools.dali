/*******************************************************************************
 * Copyright (c) 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.closure;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * decentralize test creation code
 */
public class JptCommonUtilityClosureTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(JptCommonUtilityClosureTests.class.getPackage().getName());

		suite.addTestSuite(BooleanClosureTests.class);
		suite.addTestSuite(NullableBooleanClosureTests.class);

		return suite;
	}

	private JptCommonUtilityClosureTests() {
		super();
		throw new UnsupportedOperationException();
	}
}

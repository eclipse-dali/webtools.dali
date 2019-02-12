/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.jdbc;

import junit.framework.Test;
import junit.framework.TestSuite;

public class JptCommonUtilityJDBCTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(JptCommonUtilityJDBCTests.class.getPackage().getName());

		suite.addTestSuite(JDBCTypeTests.class);

		return suite;
	}

	private JptCommonUtilityJDBCTests() {
		super();
		throw new UnsupportedOperationException();
	}
}

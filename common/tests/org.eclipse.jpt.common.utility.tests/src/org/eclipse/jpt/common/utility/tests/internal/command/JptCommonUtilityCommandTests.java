/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.command;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * decentralize test creation code
 */
public class JptCommonUtilityCommandTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(JptCommonUtilityCommandTests.class.getPackage().getName());

		suite.addTestSuite(AsynchronousCommandContextTests.class);
		suite.addTestSuite(AsynchronousRepeatingCommandWrapperTests.class);
		suite.addTestSuite(CommandContextTests.class);
		suite.addTestSuite(CommandRunnableTests.class);
		suite.addTestSuite(CommandTests.class);
		suite.addTestSuite(CompositeCommandTests.class);

		return suite;
	}

	private JptCommonUtilityCommandTests() {
		super();
		throw new UnsupportedOperationException();
	}
}

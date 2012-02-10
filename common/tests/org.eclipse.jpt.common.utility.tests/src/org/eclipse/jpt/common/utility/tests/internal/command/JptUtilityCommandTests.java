/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
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
public class JptUtilityCommandTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(JptUtilityCommandTests.class.getPackage().getName());

		suite.addTestSuite(AsynchronousCommandExecutorTests.class);
		suite.addTestSuite(AsynchronousRepeatingCommandWrapperTests.class);
		suite.addTestSuite(CommandExecutorTests.class);
		suite.addTestSuite(CommandRunnableTests.class);
		suite.addTestSuite(CommandTests.class);
		suite.addTestSuite(CompositeCommandTests.class);

		return suite;
	}

	private JptUtilityCommandTests() {
		super();
		throw new UnsupportedOperationException();
	}
}

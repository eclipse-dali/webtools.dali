/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.exception;

import junit.framework.Test;
import junit.framework.TestSuite;

public class JptCommonUtilityExceptionTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(JptCommonUtilityExceptionTests.class.getPackage().getName());

		suite.addTestSuite(CancelExceptionTests.class);
		suite.addTestSuite(CollectingExceptionHandlerTests.class);
		suite.addTestSuite(CompositeExceptionHandlerTests.class);
		suite.addTestSuite(CompositeExceptionTests.class);
		suite.addTestSuite(CompositeMultiThreadedExceptionHandlerTests.class);
		suite.addTestSuite(ExceptionHandlerTests.class);
		suite.addTestSuite(PrintStreamExceptionHandlerTests.class);
		suite.addTestSuite(PrintWriterExceptionHandlerTests.class);

		return suite;
	}

	private JptCommonUtilityExceptionTests() {
		super();
		throw new UnsupportedOperationException();
	}
}

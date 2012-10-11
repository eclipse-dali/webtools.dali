/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.io;

import junit.framework.Test;
import junit.framework.TestSuite;

public class JptCommonUtilityIOTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(JptCommonUtilityIOTests.class.getPackage().getName());

		suite.addTestSuite(FileToolsTests.class);
		suite.addTestSuite(IndentingPrintWriterTests.class);
		suite.addTestSuite(InvalidInputStreamTests.class);
		suite.addTestSuite(InvalidOutputStreamTests.class);
		suite.addTestSuite(InvalidReaderTests.class);
		suite.addTestSuite(InvalidWriterTests.class);
		suite.addTestSuite(NullInputStreamTests.class);
		suite.addTestSuite(NullOutputStreamTests.class);
		suite.addTestSuite(NullReaderTests.class);
		suite.addTestSuite(NullWriterTests.class);
		suite.addTestSuite(PipeTests.class);
		suite.addTestSuite(StringBufferWriterTests.class);
		suite.addTestSuite(StringBuilderWriterTests.class);
		suite.addTestSuite(CompositeOutputStreamTests.class);
		suite.addTestSuite(CompositeWriterTests.class);
		suite.addTestSuite(WriterToolsTests.class);

		return suite;
	}

	private JptCommonUtilityIOTests() {
		super();
		throw new UnsupportedOperationException();
	}
}

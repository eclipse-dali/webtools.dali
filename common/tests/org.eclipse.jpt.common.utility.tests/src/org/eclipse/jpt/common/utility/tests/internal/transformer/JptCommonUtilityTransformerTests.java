/*******************************************************************************
 * Copyright (c) 2012, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.transformer;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * decentralize test creation code
 */
public class JptCommonUtilityTransformerTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(JptCommonUtilityTransformerTests.class.getPackage().getName());

		suite.addTestSuite(FilteringTransformerTests.class);
		suite.addTestSuite(ListLastElementTransformerTests.class);
		suite.addTestSuite(ListLastElementTransformer_Tests.class);
		suite.addTestSuite(NullCheckTransformerTests.class);
		suite.addTestSuite(TransformerToolsTests.class);
		suite.addTestSuite(XMLStringDecoderTests.class);
		suite.addTestSuite(XMLStringEncoderTests.class);

		return suite;
	}

	private JptCommonUtilityTransformerTests() {
		super();
		throw new UnsupportedOperationException();
	}
}

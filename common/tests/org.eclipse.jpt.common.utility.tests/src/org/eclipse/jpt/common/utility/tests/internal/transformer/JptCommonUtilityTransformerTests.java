/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
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

		suite.addTestSuite(XMLStringDecoderTests.class);
		suite.addTestSuite(XMLStringEncoderTests.class);

		return suite;
	}

	private JptCommonUtilityTransformerTests() {
		super();
		throw new UnsupportedOperationException();
	}
}

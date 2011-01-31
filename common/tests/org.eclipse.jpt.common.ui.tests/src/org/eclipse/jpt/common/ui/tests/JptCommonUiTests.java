/*******************************************************************************
 * Copyright (c) 2006, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.tests;

import junit.framework.Test;
import junit.framework.TestSuite;
import org.eclipse.jpt.common.ui.tests.internal.swt.JptUiSWTTests;
import org.eclipse.jpt.common.ui.tests.internal.util.JptUiUtilTests;

/**
 * Runs all JPT UI Tests
 */
public class JptCommonUiTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(JptCommonUiTests.class.getPackage().getName());
		suite.addTest(JptUiSWTTests.suite());
		suite.addTest(JptUiUtilTests.suite());
		return suite;
	}

	private JptCommonUiTests() {
		super();
		throw new UnsupportedOperationException();
	}

}

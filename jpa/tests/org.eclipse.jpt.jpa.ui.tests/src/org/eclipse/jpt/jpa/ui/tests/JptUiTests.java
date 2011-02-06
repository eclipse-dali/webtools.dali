/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.tests;

import junit.framework.Test;
import junit.framework.TestSuite;
import org.eclipse.jpt.jpa.ui.tests.internal.platform.JptUiPlatformTests;

/**
 * Runs all JPT UI Tests
 */
public class JptUiTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(JptUiTests.class.getPackage().getName());
		suite.addTest(JptUiPlatformTests.suite());
		return suite;
	}

	private JptUiTests() {
		super();
		throw new UnsupportedOperationException();
	}

}

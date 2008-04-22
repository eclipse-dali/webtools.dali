/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.tests.internal.swt;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;
import junit.framework.TestSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@SuiteClasses
({
	CComboModelAdapterTest.class,
	ComboModelAdapterTest.class,
	SpinnerModelAdapterTest.class,
	TableModelAdapterTest.class
})
@RunWith(Suite.class)
public final class JptUiSWTTests {

	private JptUiSWTTests() {
		super();
		throw new UnsupportedOperationException();
	}

	public static Test suite() {
		TestSuite suite = new TestSuite();
		suite.addTest(new JUnit4TestAdapter(JptUiSWTTests.class));
		return suite;
	}
}

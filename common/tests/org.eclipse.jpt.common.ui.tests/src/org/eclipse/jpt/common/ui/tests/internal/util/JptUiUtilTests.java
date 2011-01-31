/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.tests.internal.util;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;
import junit.framework.TestSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@SuiteClasses
({
	ControlAlignerTest.class,
	ControlSwitcherTest.class,
	ControlEnablerTest.class,
	ControlVisibilityEnablerTest.class,
	LabeledButtonTest.class,
	LabeledLabelTest.class,
	LabeledControlUpdaterTest.class,
	PaneEnablerTest.class,
	PaneVisibilityEnablerTest.class,
})
@RunWith(Suite.class)
public final class JptUiUtilTests {

	private JptUiUtilTests() {
		super();
		throw new UnsupportedOperationException();
	}

	public static Test suite() {
		TestSuite suite = new TestSuite();
		suite.addTest(new JUnit4TestAdapter(JptUiUtilTests.class));
		return suite;
	}
}

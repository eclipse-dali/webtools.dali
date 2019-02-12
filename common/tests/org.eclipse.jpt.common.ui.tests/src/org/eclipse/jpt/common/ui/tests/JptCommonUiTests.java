/*******************************************************************************
 * Copyright (c) 2006, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.tests;

import junit.framework.Test;
import junit.framework.TestSuite;
import org.eclipse.jpt.common.core.tests.BundleActivatorTest;
import org.eclipse.jpt.common.ui.JptCommonUiImages;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.ui.tests.internal.swt.JptUiSWTTests;
import org.eclipse.jpt.common.ui.tests.internal.util.JptUiUtilTests;

public class JptCommonUiTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(JptCommonUiTests.class.getPackage().getName());
		suite.addTest(JptUiSWTTests.suite());
		suite.addTest(JptUiUtilTests.suite());
		suite.addTest(new BundleActivatorTest(WidgetFactory.class));
		suite.addTest(new ImageDescriptorTest(JptCommonUiImages.class));
		return suite;
	}

	private JptCommonUiTests() {
		super();
		throw new UnsupportedOperationException();
	}
}

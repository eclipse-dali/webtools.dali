/*******************************************************************************
 * Copyright (c) 2006, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.tests.internal;

import junit.framework.Test;
import junit.framework.TestSuite;
import org.eclipse.jpt.common.core.tests.BundleActivatorTest;
import org.eclipse.jpt.jpa.ui.JpaPlatformUi;
import org.eclipse.jpt.jpa.ui.tests.internal.platform.JptJpaUiPlatformTests;

// TODO we need to add BundleActivatorTests for:
//     o.e.jpt.dbws.eclipselink.ui
//     o.e.jpt.jaxb.ui
//     o.e.jpt.jaxb.eclipselink.ui
//     o.e.jpt.jpa.eclipselink.ui
public class JptJpaUiTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(JptJpaUiTests.class.getPackage().getName());
		suite.addTest(JptJpaUiPlatformTests.suite());
		suite.addTest(new BundleActivatorTest(JpaPlatformUi.class));
		return suite;
	}

	private JptJpaUiTests() {
		super();
		throw new UnsupportedOperationException();
	}
}

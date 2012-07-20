/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.tests.internal;

import org.eclipse.jpt.common.core.tests.BundleActivatorTest;
import org.eclipse.jpt.common.eclipselink.core.internal.libval.EclipseLinkLibValUtil;
import org.eclipse.jpt.jpa.eclipselink.core.EclipseLinkJpaProject;
import junit.framework.Test;
import junit.framework.TestSuite;

public class JptJpaEclipseLinkCoreMiscTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(JptJpaEclipseLinkCoreMiscTests.class.getPackage().getName());

		suite.addTestSuite(EclipseLinkJpaPreferencesTests.class);
		suite.addTest(new BundleActivatorTest(EclipseLinkJpaProject.class));
		suite.addTest(new BundleActivatorTest(EclipseLinkLibValUtil.class));  // verify common.eclipselink.core plug-in also

		return suite;
	}

	private JptJpaEclipseLinkCoreMiscTests() {
		super();
		throw new UnsupportedOperationException();
	}
}

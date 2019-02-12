/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.tests.internal;

import junit.framework.Test;
import junit.framework.TestSuite;
import org.eclipse.jpt.common.core.tests.BundleActivatorTest;
import org.eclipse.jpt.common.core.tests.ValidationMessageClassTest;
import org.eclipse.jpt.common.eclipselink.core.internal.libval.EclipseLinkLibraryValidatorTools;
import org.eclipse.jpt.jpa.eclipselink.core.EclipseLinkJpaProject;
import org.eclipse.jpt.jpa.eclipselink.core.validation.JptJpaEclipseLinkCoreValidationMessages;

public class JptJpaEclipseLinkCoreMiscTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(JptJpaEclipseLinkCoreMiscTests.class.getPackage().getName());

		suite.addTestSuite(EclipseLinkJpaPreferencesTests.class);
		suite.addTest(new BundleActivatorTest(EclipseLinkJpaProject.class));
		suite.addTest(new BundleActivatorTest(EclipseLinkLibraryValidatorTools.class));  // verify common.eclipselink.core plug-in also
		suite.addTest(new ValidationMessageClassTest(JptJpaEclipseLinkCoreValidationMessages.class));

		return suite;
	}

	private JptJpaEclipseLinkCoreMiscTests() {
		super();
		throw new UnsupportedOperationException();
	}
}

/*******************************************************************************
 * Copyright (c) 2007, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.tests.internal.context;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.eclipse.jpt.jpa.eclipselink.core.tests.internal.JptJpaEclipseLinkCoreTests;
import org.eclipse.jpt.jpa.eclipselink.core.tests.internal.v1_1.context.JptEclipseLink1_1CoreContextModelTests;
import org.eclipse.jpt.jpa.eclipselink.core.tests.internal.v1_2.context.JptEclipseLink1_2CoreContextModelTests;
import org.eclipse.jpt.jpa.eclipselink.core.tests.internal.v2_0.context.JptEclipseLink2_0CoreContextModelTests;
import org.eclipse.jpt.jpa.eclipselink.core.tests.internal.v2_3.context.JptEclipseLink2_3CoreContextModelTests;

/**
 * Required Java system property:
 *    -Dorg.eclipse.jpt.jpa.jar=<jpa.jar path>
 *    -Dorg.eclipse.jpt.eclipselink.jar=<eclipselink.jar path>
 */
public class JptJpaEclipseLinkCoreContextModelTests extends TestCase
{
	public static Test suite() {
		TestSuite suite = new TestSuite(JptJpaEclipseLinkCoreContextModelTests.class.getName());
		if(JptJpaEclipseLinkCoreTests.requiredJarsExists()) {
			suite.addTest(JptEclipseLink1_0CoreContextModelTests.suite());
			suite.addTest(JptEclipseLink1_1CoreContextModelTests.suite());
			suite.addTest(JptEclipseLink1_2CoreContextModelTests.suite());
			suite.addTest(JptEclipseLink2_0CoreContextModelTests.suite());
			suite.addTest(JptEclipseLink2_3CoreContextModelTests.suite());
		}
		else {
			suite.addTest(TestSuite.warning(JptJpaEclipseLinkCoreTests.buildMissingJarErrorMessage()));
		}
		return suite;
	}

	private JptJpaEclipseLinkCoreContextModelTests() {
		super();
		throw new UnsupportedOperationException();
	}
}

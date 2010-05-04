/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.tests.internal.context;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.eclipse.jpt.eclipselink.core.tests.internal.JptEclipseLinkCoreTests;
import org.eclipse.jpt.eclipselink1_1.core.tests.internal.context.JptEclipseLink1_1CoreContextModelTests;
import org.eclipse.jpt.eclipselink1_2.core.tests.internal.context.JptEclipseLink1_2CoreContextModelTests;
import org.eclipse.jpt.eclipselink2_0.core.tests.internal.context.JptEclipseLink2_0CoreContextModelTests;

/**
 * Required Java system property:
 *    -Dorg.eclipse.jpt.jpa.jar=<jpa.jar path>
 *    -Dorg.eclipse.jpt.eclipselink.jar=<eclipselink.jar path>
 */
public class JptEclipseLinkCoreContextModelTests extends TestCase
{
	public static Test suite() {
		TestSuite suite = new TestSuite(JptEclipseLinkCoreContextModelTests.class.getName());
		if(JptEclipseLinkCoreTests.requiredJarsExists()) {
			suite.addTest(JptEclipseLink1_0CoreContextModelTests.suite());
			suite.addTest(JptEclipseLink1_1CoreContextModelTests.suite());
			suite.addTest(JptEclipseLink1_2CoreContextModelTests.suite());
			suite.addTest(JptEclipseLink2_0CoreContextModelTests.suite());
		}
		else {
			suite.addTest(TestSuite.warning(JptEclipseLinkCoreTests.buildMissingJarErrorMessage()));
		}
		return suite;
	}

	private JptEclipseLinkCoreContextModelTests() {
		super();
		throw new UnsupportedOperationException();
	}
}

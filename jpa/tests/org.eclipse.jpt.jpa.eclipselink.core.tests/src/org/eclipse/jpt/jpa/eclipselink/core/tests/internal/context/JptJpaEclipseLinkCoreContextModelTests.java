/*******************************************************************************
 * Copyright (c) 2007, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.tests.internal.context;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.eclipse.jpt.jpa.eclipselink.core.tests.internal.JptJpaEclipseLinkCoreTests;
import org.eclipse.jpt.jpa.eclipselink.core.tests.internal.context.java.JptEclipseLinkCoreJavaContextModelTests;
import org.eclipse.jpt.jpa.eclipselink.core.tests.internal.context.orm.JptEclipseLinkCoreOrmContextModelTests;
import org.eclipse.jpt.jpa.eclipselink.core.tests.internal.context.persistence.JptEclipseLinkCorePersistenceContextModelTests;

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
			suite.addTestSuite(EclipseLinkJpaProjectTests.class);
			suite.addTestSuite(EclipseLink1_1JpaProjectTests.class);
			suite.addTestSuite(EclipseLink1_2JpaProjectTests.class);
			suite.addTest(JptEclipseLinkCorePersistenceContextModelTests.suite());
			suite.addTest(JptEclipseLinkCoreJavaContextModelTests.suite());
			suite.addTest(JptEclipseLinkCoreOrmContextModelTests.suite());
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

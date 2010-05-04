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
import org.eclipse.jpt.eclipselink.core.tests.internal.context.java.JptEclipseLinkCoreJavaContextModelTests;
import org.eclipse.jpt.eclipselink.core.tests.internal.context.orm.JptEclipseLinkCoreOrmContextModelTests;
import org.eclipse.jpt.eclipselink.core.tests.internal.context.persistence.JptEclipseLinkCorePersistenceContextModelTests;

public class JptEclipseLink1_0CoreContextModelTests extends TestCase
{
	public static Test suite() {
		TestSuite suite = new TestSuite(JptEclipseLink1_0CoreContextModelTests.class.getName());
		suite.addTestSuite(EclipseLinkJpaProjectTests.class);
		suite.addTest(JptEclipseLinkCorePersistenceContextModelTests.suite());
		suite.addTest(JptEclipseLinkCoreJavaContextModelTests.suite());
		suite.addTest(JptEclipseLinkCoreOrmContextModelTests.suite());
		return suite;
	}

	private JptEclipseLink1_0CoreContextModelTests() {
		super();
		throw new UnsupportedOperationException();
	}
}

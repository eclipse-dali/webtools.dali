/*******************************************************************************
* Copyright (c) 2011 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.tests.internal.v2_3.context;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.eclipse.jpt.jpa.eclipselink.core.tests.internal.v2_3.context.java.JptEclipseLink2_3JavaContextModelTests;
import org.eclipse.jpt.jpa.eclipselink.core.tests.internal.v2_3.context.orm.JptEclipseLink2_3OrmContextModelTests;

/**
 *  JptEclipseLink2_3CoreContextModelTests
 */
public class JptEclipseLink2_3CoreContextModelTests extends TestCase
{
	public static Test suite() {
		TestSuite suite = new TestSuite(JptEclipseLink2_3CoreContextModelTests.class.getName());

		suite.addTest(JptEclipseLink2_3JavaContextModelTests.suite());
		suite.addTest(JptEclipseLink2_3OrmContextModelTests.suite());

		return suite;
	}

	private JptEclipseLink2_3CoreContextModelTests() {
		super();
		throw new UnsupportedOperationException();
	}
}
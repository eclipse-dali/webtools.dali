/*******************************************************************************
* Copyright (c) 2009 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.eclipselink2_0.core.tests.internal.context;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.eclipse.jpt.eclipselink2_0.core.tests.internal.context.persistence.JptEclipseLink2_0CorePersistenceContextModelTests;

/**
 *  JptEclipseLink2_0CoreContextModelTests
 */
public class JptEclipseLink2_0CoreContextModelTests extends TestCase
{
	public static Test suite() {
		TestSuite suite = new TestSuite(JptEclipseLink2_0CoreContextModelTests.class.getName());

		suite.addTest(JptEclipseLink2_0CorePersistenceContextModelTests.suite());

		return suite;
	}

	private JptEclipseLink2_0CoreContextModelTests() {
		super();
		throw new UnsupportedOperationException();
	}
}	
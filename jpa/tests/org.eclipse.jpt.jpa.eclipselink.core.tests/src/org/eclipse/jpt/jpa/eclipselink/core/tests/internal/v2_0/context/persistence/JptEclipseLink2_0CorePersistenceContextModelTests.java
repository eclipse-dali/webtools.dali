/*******************************************************************************
* Copyright (c) 2009, 2010 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.tests.internal.v2_0.context.persistence;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * decentralize test creation code
 */
public class JptEclipseLink2_0CorePersistenceContextModelTests
{
	public static Test suite() {
		TestSuite suite = new TestSuite(JptEclipseLink2_0CorePersistenceContextModelTests.class.getPackage().getName());

		suite.addTestSuite(EclipseLink2_0ConnectionTests.class);
		suite.addTestSuite(EclipseLink2_0LoggingTests.class);
		suite.addTestSuite(EclipseLink2_0OptionsTests.class);
		
		return suite;
	}

	private JptEclipseLink2_0CorePersistenceContextModelTests() {
		super();
		throw new UnsupportedOperationException();
	}
}

/*******************************************************************************
* Copyright (c) 2009 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.eclipselink2_0.core.tests.internal.context.persistence;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * decentralize test creation code
 */
public class JptEclipseLink2_0CorePersistenceContextModelTests
{
	public static Test suite() {
		TestSuite suite = new TestSuite(JptEclipseLink2_0CorePersistenceContextModelTests.class.getPackage().getName());

		suite.addTestSuite(EclipseLinkOptions2_0Tests.class);
		
		return suite;
	}

	private JptEclipseLink2_0CorePersistenceContextModelTests() {
		super();
		throw new UnsupportedOperationException();
	}
}

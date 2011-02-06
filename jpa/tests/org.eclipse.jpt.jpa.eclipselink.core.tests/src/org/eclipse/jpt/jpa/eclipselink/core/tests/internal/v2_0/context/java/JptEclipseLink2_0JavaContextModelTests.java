/*******************************************************************************
* Copyright (c) 2009, 2010 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.tests.internal.v2_0.context.java;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class JptEclipseLink2_0JavaContextModelTests extends TestCase
{
	public static Test suite() {
		TestSuite suite = new TestSuite(JptEclipseLink2_0JavaContextModelTests.class.getName());
		suite.addTestSuite(EclipseLink2_0JavaCollectionTableTests.class);
		suite.addTestSuite(EclipseLink2_0JavaElementCollectionMappingTests.class);
		suite.addTestSuite(EclipseLink2_0JavaEntityTests.class);
		suite.addTestSuite(EclipseLink2_0JavaManyToManyMappingTests.class);
		suite.addTestSuite(EclipseLink2_0JavaManyToOneMappingTests.class);
		suite.addTestSuite(EclipseLink2_0JavaMappedSuperclassTests.class);
		suite.addTestSuite(EclipseLink2_0JavaOneToManyMappingTests.class);
		suite.addTestSuite(EclipseLink2_0JavaOneToOneMappingTests.class);
		return suite;
	}

	private JptEclipseLink2_0JavaContextModelTests() {
		super();
		throw new UnsupportedOperationException();
	}
}

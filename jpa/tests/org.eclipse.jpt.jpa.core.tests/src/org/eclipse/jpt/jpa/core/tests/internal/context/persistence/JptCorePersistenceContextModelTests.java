/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.context.persistence;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class JptCorePersistenceContextModelTests
	extends TestCase
{
	public static Test suite() {
		TestSuite suite = new TestSuite(JptCorePersistenceContextModelTests.class.getPackage().getName());
		suite.addTestSuite(RootContextNodeTests.class);
		suite.addTestSuite(PersistenceXmlTests.class);
		suite.addTestSuite(PersistenceTests.class);
		suite.addTestSuite(PersistenceUnitTests.class);
		suite.addTestSuite(MappingFileRefTests.class);
		suite.addTestSuite(ClassRefTests.class);
		return suite;
	}
	
	private JptCorePersistenceContextModelTests() {
		throw new UnsupportedOperationException();
	}
}

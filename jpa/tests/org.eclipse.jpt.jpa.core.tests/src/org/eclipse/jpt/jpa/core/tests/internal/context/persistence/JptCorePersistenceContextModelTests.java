/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
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
		suite.addTestSuite(JpaContextRootTests.class);
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

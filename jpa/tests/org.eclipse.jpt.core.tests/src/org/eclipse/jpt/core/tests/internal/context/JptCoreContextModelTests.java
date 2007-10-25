/*******************************************************************************
 *  Copyright (c) 2007 Oracle. 
 *  All rights reserved.  This program and the accompanying materials 
 *  are made available under the terms of the Eclipse Public License v1.0 
 *  which accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.tests.internal.context;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class JptCoreContextModelTests extends TestCase
{
	public static Test suite() {
		return suite(true);
	}
	
	public static Test suite(boolean all) {
		TestSuite suite = new TestSuite(JptCoreContextModelTests.class.getName());
		suite.addTestSuite(BaseJpaContentTests.class);
		suite.addTestSuite(PersistenceXmlTests.class);
		suite.addTestSuite(PersistenceTests.class);
		suite.addTestSuite(PersistenceUnitTests.class);
		return suite;
	}

	private JptCoreContextModelTests() {
		super();
		throw new UnsupportedOperationException();
	}
}

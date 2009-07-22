/*******************************************************************************
 *  Copyright (c) 2007, 2009 Oracle. 
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
		TestSuite suite = new TestSuite(JptCoreContextModelTests.class.getName());
//		suite.addTestSuite(JpaProjectTests.class);//TODO use this for testing the build test failures	
//		suite.addTestSuite(JpaFileTests.class);
//		suite.addTest(JptCorePersistenceContextModelTests.suite());
//		suite.addTest(JptCoreOrmContextModelTests.suite());
//		suite.addTest(JptCoreContextJavaModelTests.suite());
//		suite.addTest(Generic2_0JavaContextModelTests.suite());
//		suite.addTest(Generic2_0OrmContextModelTests.suite());
		return suite;
	}

	private JptCoreContextModelTests() {
		super();
		throw new UnsupportedOperationException();
	}
}

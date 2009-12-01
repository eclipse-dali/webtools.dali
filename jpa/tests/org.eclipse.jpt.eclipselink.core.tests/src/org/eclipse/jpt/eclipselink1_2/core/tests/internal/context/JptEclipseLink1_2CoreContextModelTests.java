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
package org.eclipse.jpt.eclipselink1_2.core.tests.internal.context;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class JptEclipseLink1_2CoreContextModelTests extends TestCase
{
	public static Test suite() {
		TestSuite suite = new TestSuite(JptEclipseLink1_2CoreContextModelTests.class.getName());
		suite.addTestSuite(EclipseLink1_2JpaProjectTests.class);
	//	suite.addTest(JptEclipseLink1_2CoreOrmContextModelTests.suite());
		return suite;
	}

	private JptEclipseLink1_2CoreContextModelTests() {
		super();
		throw new UnsupportedOperationException();
	}
}

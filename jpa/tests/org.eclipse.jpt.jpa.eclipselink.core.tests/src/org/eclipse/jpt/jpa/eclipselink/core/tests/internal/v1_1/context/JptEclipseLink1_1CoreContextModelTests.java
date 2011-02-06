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
package org.eclipse.jpt.jpa.eclipselink.core.tests.internal.v1_1.context;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.eclipse.jpt.jpa.eclipselink.core.tests.internal.v1_1.context.orm.JptEclipseLink1_1CoreOrmContextModelTests;

public class JptEclipseLink1_1CoreContextModelTests extends TestCase
{
	public static Test suite() {
		TestSuite suite = new TestSuite(JptEclipseLink1_1CoreContextModelTests.class.getName());
		suite.addTestSuite(EclipseLink1_1JpaProjectTests.class);
		suite.addTest(JptEclipseLink1_1CoreOrmContextModelTests.suite());
		return suite;
	}

	private JptEclipseLink1_1CoreContextModelTests() {
		super();
		throw new UnsupportedOperationException();
	}
}

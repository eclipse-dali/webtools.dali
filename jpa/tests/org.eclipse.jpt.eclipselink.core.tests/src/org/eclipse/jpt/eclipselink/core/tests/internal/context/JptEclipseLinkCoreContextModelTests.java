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
package org.eclipse.jpt.eclipselink.core.tests.internal.context;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.eclipse.jpt.eclipselink.core.tests.internal.context.java.JptEclipseLinkCoreJavaContextModelTests;
import org.eclipse.jpt.eclipselink.core.tests.internal.context.orm.JptEclipseLinkCoreOrmContextModelTests;
import org.eclipse.jpt.eclipselink.core.tests.internal.context.persistence.JptEclipseLinkCorePersistenceContextModelTests;

public class JptEclipseLinkCoreContextModelTests extends TestCase
{
	public static Test suite() {
		return suite(true);
	}
	
	public static Test suite(boolean all) {
		TestSuite suite = new TestSuite(JptEclipseLinkCoreContextModelTests.class.getName());
		suite.addTestSuite(JpaProjectTests.class);
		suite.addTest(JptEclipseLinkCorePersistenceContextModelTests.suite(all));
		suite.addTest(JptEclipseLinkCoreJavaContextModelTests.suite(all));
		suite.addTest(JptEclipseLinkCoreOrmContextModelTests.suite(all));
		return suite;
	}

	private JptEclipseLinkCoreContextModelTests() {
		super();
		throw new UnsupportedOperationException();
	}
}

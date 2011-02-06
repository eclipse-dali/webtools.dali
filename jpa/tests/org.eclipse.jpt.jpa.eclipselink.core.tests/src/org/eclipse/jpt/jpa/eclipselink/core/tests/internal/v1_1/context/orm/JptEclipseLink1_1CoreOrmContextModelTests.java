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
package org.eclipse.jpt.jpa.eclipselink.core.tests.internal.v1_1.context.orm;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class JptEclipseLink1_1CoreOrmContextModelTests extends TestCase
{
	public static Test suite() {
		TestSuite suite = new TestSuite(JptEclipseLink1_1CoreOrmContextModelTests.class.getName());
		
		suite.addTestSuite(EclipseLink1_1OrmPersistentAttributeTests.class);		
		suite.addTestSuite(EclipseLink1_1OrmPersistentTypeTests.class);	
		suite.addTestSuite(EclipseLink1_1OrmTransientMappingTests.class);	
		
		return suite;
	}
	
	
	private JptEclipseLink1_1CoreOrmContextModelTests() {
		throw new UnsupportedOperationException();
	}
}

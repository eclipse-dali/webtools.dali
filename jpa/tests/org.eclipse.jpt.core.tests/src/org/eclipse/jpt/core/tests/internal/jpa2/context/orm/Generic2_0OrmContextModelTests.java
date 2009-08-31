/*******************************************************************************
 *  Copyright (c) 2009 Oracle. 
 *  All rights reserved.  This program and the accompanying materials 
 *  are made available under the terms of the Eclipse Public License v1.0 
 *  which accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.tests.internal.jpa2.context.orm;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class Generic2_0OrmContextModelTests extends TestCase
{

	public static Test suite() {
		TestSuite suite = new TestSuite(Generic2_0OrmContextModelTests.class.getName());
		suite.addTestSuite(GenericOrmPersistentType2_0Tests.class);
		suite.addTestSuite(GenericOrmPersistentAttribute2_0Tests.class);
		suite.addTestSuite(GenericOrmEntity2_0Tests.class);
		suite.addTestSuite(GenericOrmAssociationOverride2_0Tests.class);
		suite.addTestSuite(GenericOrmSequenceGenerator2_0Tests.class);
		return suite;
	}

	private Generic2_0OrmContextModelTests() {
		super();
		throw new UnsupportedOperationException();
	}
}

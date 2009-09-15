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
package org.eclipse.jpt.core.tests.internal.jpa2.context.java;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class Generic2_0JavaContextModelTests extends TestCase
{
	public static Test suite() {
		TestSuite suite = new TestSuite(Generic2_0JavaContextModelTests.class.getName());
		suite.addTestSuite(GenericJavaAssociationOverride2_0Tests.class);
		suite.addTestSuite(GenericJavaEntity2_0Tests.class);
		suite.addTestSuite(GenericJavaEmbeddedMapping2_0Tests.class);
		suite.addTestSuite(GenericJavaManyToOneMapping2_0Tests.class);
		suite.addTestSuite(GenericJavaOneToOneMapping2_0Tests.class);
		suite.addTestSuite(GenericJavaPersistentAttribute2_0Tests.class);
		suite.addTestSuite(GenericJavaPersistentType2_0Tests.class);
		suite.addTestSuite(GenericJavaSequenceGenerator2_0Tests.class);
		return suite;
	}

	private Generic2_0JavaContextModelTests() {
		super();
		throw new UnsupportedOperationException();
	}
}

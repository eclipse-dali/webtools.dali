/*******************************************************************************
 *  Copyright (c) 2010 Oracle. 
 *  All rights reserved.  This program and the accompanying materials 
 *  are made available under the terms of the Eclipse Public License v1.0 
 *  which accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core.tests.internal.resource;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.eclipse.jpt.jaxb.core.tests.internal.resource.java.JaxbJavaResourceModelTests;


public class JaxbCoreResourceModelTests extends TestCase
{
	public static Test suite() {
		TestSuite suite = new TestSuite(JaxbCoreResourceModelTests.class.getName());

		suite.addTestSuite(JaxbIndexResourceTests.class);
		suite.addTestSuite(JaxbPropertiesResourceTests.class);
		suite.addTest(JaxbJavaResourceModelTests.suite());
		return suite;
	}

	private JaxbCoreResourceModelTests() {
		super();
		throw new UnsupportedOperationException();
	}
}

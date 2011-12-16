/*******************************************************************************
 *  Copyright (c) 2007, 2010 Oracle. 
 *  All rights reserved.  This program and the accompanying materials 
 *  are made available under the terms of the Eclipse Public License v1.0 
 *  which accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core.tests.internal.context;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.eclipse.jpt.jaxb.core.tests.internal.JaxbCoreTests;
import org.eclipse.jpt.jaxb.core.tests.internal.context.java.JaxbCoreJavaContextModelTests;

public class JaxbCoreContextModelTests
		extends TestCase {
	
	public static Test suite() {
		TestSuite suite = new TestSuite(JaxbCoreContextModelTests.class.getName());
		
		if (JaxbCoreTests.requiredJarsExists()) {
			suite.addTestSuite(GenericContextRootTests.class);
			suite.addTest(JaxbCoreJavaContextModelTests.suite());
		}
		else {
			suite.addTest(TestSuite.warning(JaxbCoreTests.buildMissingJarErrorMessage()));
		}
		return suite;
	}
	
	
	private JaxbCoreContextModelTests() {
		super();
		throw new UnsupportedOperationException();
	}
}

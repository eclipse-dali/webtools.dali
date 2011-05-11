/*******************************************************************************
 *  Copyright (c) 2011  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.tests.internal.resource;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.eclipse.jpt.jaxb.eclipselink.core.tests.internal.resource.java.ELJaxbJavaResourceModelTests;


public class JaxbEclipseLinkCoreResourceModelTests 
		extends TestCase {
	
	public static Test suite() {
		TestSuite suite = new TestSuite(JaxbEclipseLinkCoreResourceModelTests.class.getName());

		suite.addTest(ELJaxbJavaResourceModelTests.suite());
		return suite;
	}
	
	
	private JaxbEclipseLinkCoreResourceModelTests() {
		super();
		throw new UnsupportedOperationException();
	}
}

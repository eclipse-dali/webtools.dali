/*******************************************************************************
 *  Copyright (c) 2012  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.tests.internal.context.oxm;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class ELJaxbCoreOxmContextModelTests
		extends TestCase {
	
	public static Test suite() {
		TestSuite suite = new TestSuite(ELJaxbCoreOxmContextModelTests.class.getName());
		suite.addTestSuite(OxmJavaTypeTests.class);
		suite.addTestSuite(OxmXmlBindingsTests.class);
		suite.addTestSuite(OxmXmlSchemaTests.class);
		return suite;
	}
	
	
	private ELJaxbCoreOxmContextModelTests() {
		super();
		throw new UnsupportedOperationException();
	}
}

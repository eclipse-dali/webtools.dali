/*******************************************************************************
 *  Copyright (c) 2012, 2013  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License 2.0, which accompanies this distribution
 *  and is available at https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
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
		suite.addTestSuite(OxmXmlEnumTests.class);
		suite.addTestSuite(OxmXmlSchemaTests.class);
		suite.addTestSuite(OxmXmlSeeAlsoTests.class);
		return suite;
	}
	
	
	private ELJaxbCoreOxmContextModelTests() {
		super();
		throw new UnsupportedOperationException();
	}
}

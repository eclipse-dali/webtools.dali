/*******************************************************************************
 *  Copyright (c) 2011, 2012  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.tests.internal.context.java;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


public class ELJaxbCoreJavaContextModelTests
		extends TestCase {
	
	public static Test suite() {
		TestSuite suite = new TestSuite(ELJaxbCoreJavaContextModelTests.class.getName());
		suite.addTestSuite(ELJavaClassMappingTests.class);
		suite.addTestSuite(ELJavaXmlAnyAttributeMappingTests.class);
		suite.addTestSuite(ELJavaXmlAnyElementMappingTests.class);
		suite.addTestSuite(ELJavaXmlAttributeMappingTests.class);
		suite.addTestSuite(ELJavaXmlDiscriminatorNodeTests.class);
		suite.addTestSuite(ELJavaXmlDiscriminatorValueTests.class);
		suite.addTestSuite(ELJavaXmlElementMappingTests.class);
		suite.addTestSuite(ELJavaXmlElementsMappingTests.class);
		suite.addTestSuite(ELJavaXmlInverseReferenceMappingTests.class);
		suite.addTestSuite(ELJavaXmlPathTests.class);
		return suite;
	}
	
	
	private ELJaxbCoreJavaContextModelTests() {
		super();
		throw new UnsupportedOperationException();
	}
}

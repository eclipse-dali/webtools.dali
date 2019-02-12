/*******************************************************************************
 *  Copyright (c) 2011, 2012  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License 2.0, which accompanies this distribution
 *  and is available at https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
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
		suite.addTestSuite(ELJavaXmlJoinNodesMappingTests.class);
		suite.addTestSuite(ELJavaXmlJoinNodeTests.class);
		suite.addTestSuite(ELJavaXmlPathTests.class);
		suite.addTestSuite(ELJavaXmlValueMappingTests.class);
		return suite;
	}
	
	
	private ELJaxbCoreJavaContextModelTests() {
		super();
		throw new UnsupportedOperationException();
	}
}

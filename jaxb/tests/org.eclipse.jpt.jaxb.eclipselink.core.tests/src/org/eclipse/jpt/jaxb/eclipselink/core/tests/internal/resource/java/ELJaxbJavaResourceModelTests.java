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
package org.eclipse.jpt.jaxb.eclipselink.core.tests.internal.resource.java;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


public class ELJaxbJavaResourceModelTests
		extends TestCase {
	
	public static Test suite() {
		TestSuite suite = new TestSuite(ELJaxbJavaResourceModelTests.class.getName());
		suite.addTestSuite(XmlCDATAAnnotationTests.class);
		suite.addTestSuite(XmlDiscriminatorNodeAnnotationTests.class);
		suite.addTestSuite(XmlDiscriminatorValueAnnotationTests.class);
		suite.addTestSuite(XmlInverseReferenceAnnotationTests.class);
		suite.addTestSuite(XmlJoinNodeAnnotationTests.class);
		suite.addTestSuite(XmlKeyAnnotationTests.class);
		suite.addTestSuite(XmlPathAnnotationTests.class);
		suite.addTestSuite(XmlTransformationAnnotationTests.class);
		
		return suite;
	}
	
	
	private ELJaxbJavaResourceModelTests() {
		super();
		throw new UnsupportedOperationException();
	}
}

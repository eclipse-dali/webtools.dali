/*******************************************************************************
 *  Copyright (c) 2011  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
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
		suite.addTestSuite(XmlInverseReferenceAnnotationTests.class);
		suite.addTestSuite(XmlTransformationAnnotationTests.class);
		
		return suite;
	}
	
	
	private ELJaxbJavaResourceModelTests() {
		super();
		throw new UnsupportedOperationException();
	}
}

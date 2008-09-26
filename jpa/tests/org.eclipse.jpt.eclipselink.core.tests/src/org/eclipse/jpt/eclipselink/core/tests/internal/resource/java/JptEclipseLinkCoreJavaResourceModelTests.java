/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.tests.internal.resource.java;

import junit.framework.Test;
import junit.framework.TestSuite;

public class JptEclipseLinkCoreJavaResourceModelTests {

	public static Test suite() {
		return suite(true);
	}
	
	public static Test suite(boolean all) {
		TestSuite suite = new TestSuite(JptEclipseLinkCoreJavaResourceModelTests.class.getName());
		
		suite.addTestSuite(CacheTests.class);
		suite.addTestSuite(ConversionValueAnnotationTests.class);
		suite.addTestSuite(ConvertAnnotationTests.class);
		suite.addTestSuite(ConverterAnnotationTests.class);
		suite.addTestSuite(ExistenceCheckingTests.class);
		suite.addTestSuite(JoinFetchTests.class);
		suite.addTestSuite(MutableAnnotationTests.class);
		suite.addTestSuite(ObjectTypeConverterAnnotationTests.class);
		suite.addTestSuite(PrivateOwnedTests.class);
		suite.addTestSuite(ReadTransformerAnnotationTests.class);
		suite.addTestSuite(StructConverterAnnotationTests.class);
		suite.addTestSuite(TimeOfDayTests.class);
		suite.addTestSuite(TransformationAnnotationTests.class);
		suite.addTestSuite(TypeConverterAnnotationTests.class);
		suite.addTestSuite(WriteTransformerAnnotationTests.class);
		
		return suite;
	}

	private JptEclipseLinkCoreJavaResourceModelTests() {
		super();
		throw new UnsupportedOperationException();
	}

}

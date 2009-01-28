/*******************************************************************************
 *  Copyright (c) 2008 Oracle. 
 *  All rights reserved.  This program and the accompanying materials 
 *  are made available under the terms of the Eclipse Public License v1.0 
 *  which accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.tests.internal.context.java;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class JptEclipseLinkCoreJavaContextModelTests extends TestCase
{
	public static Test suite() {
		return suite(true);
	}

	public static Test suite(boolean all) {
		TestSuite suite = new TestSuite(JptEclipseLinkCoreJavaContextModelTests.class.getName());
		suite.addTestSuite(EclipseLink1_1JavaPersistentTypeTests.class);
		suite.addTestSuite(EclipseLinkJavaBasicMappingTests.class);
		suite.addTestSuite(EclipseLinkJavaCachingTests.class);
		suite.addTestSuite(EclipseLinkJavaConvertTests.class);
		suite.addTestSuite(EclipseLinkJavaConverterTests.class);
		suite.addTestSuite(EclipseLinkJavaEmbeddableTests.class);
		suite.addTestSuite(EclipseLinkJavaEntityTests.class);
		suite.addTestSuite(EclipseLinkJavaIdMappingTests.class);
		suite.addTestSuite(EclipseLinkJavaMappedSuperclassTests.class);
		suite.addTestSuite(EclipseLinkJavaObjectTypeConverterTests.class);
		suite.addTestSuite(EclipseLinkJavaOneToManyMappingTests.class);
		suite.addTestSuite(EclipseLinkJavaOneToOneMappingTests.class);		
		suite.addTestSuite(EclipseLinkJavaManyToManyMappingTests.class);		
		suite.addTestSuite(EclipseLinkJavaManyToOneMappingTests.class);		
		suite.addTestSuite(EclipseLinkJavaStructConverterTests.class);
		suite.addTestSuite(EclipseLinkJavaTypeConverterTests.class);
		suite.addTestSuite(EclipseLinkJavaVersionMappingTests.class);
		return suite;
	}

	private JptEclipseLinkCoreJavaContextModelTests() {
		super();
		throw new UnsupportedOperationException();
	}
}

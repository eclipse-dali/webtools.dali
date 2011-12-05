/*******************************************************************************
 * Copyright (c) 2008, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.tests.internal.context.java;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class JptEclipseLinkCoreJavaContextModelTests extends TestCase
{
	public static Test suite() {
		TestSuite suite = new TestSuite(JptEclipseLinkCoreJavaContextModelTests.class.getName());	
		suite.addTestSuite(EclipseLinkJavaBasicMappingTests.class);
		suite.addTestSuite(EclipseLinkJavaCachingTests.class);
		suite.addTestSuite(EclipseLinkJavaConverterTests.class);
		suite.addTestSuite(EclipseLinkJavaConvertTests.class);
		suite.addTestSuite(EclipseLinkJavaEmbeddableTests.class);
		suite.addTestSuite(EclipseLinkJavaEntityTests.class);
		suite.addTestSuite(EclipseLinkJavaIdMappingTests.class);
		suite.addTestSuite(EclipseLinkJavaManyToManyMappingTests.class);		
		suite.addTestSuite(EclipseLinkJavaManyToOneMappingTests.class);		
		suite.addTestSuite(EclipseLinkJavaMappedSuperclassTests.class);
		suite.addTestSuite(EclipseLinkJavaObjectTypeConverterTests.class);
		suite.addTestSuite(EclipseLinkJavaOneToManyMappingTests.class);
		suite.addTestSuite(EclipseLinkJavaOneToOneMappingTests.class);		
		suite.addTestSuite(EclipseLinkJavaStructConverterTests.class);
		suite.addTestSuite(EclipseLinkJavaTypeConverterTests.class);
		suite.addTestSuite(EclipseLinkJavaVersionMappingTests.class);
		suite.addTestSuite(EclipseLink2_0JavaCollectionTableTests.class);
		suite.addTestSuite(EclipseLink2_0JavaElementCollectionMappingTests.class);
		suite.addTestSuite(EclipseLink2_0JavaEntityTests.class);
		suite.addTestSuite(EclipseLink2_0JavaManyToManyMappingTests.class);
		suite.addTestSuite(EclipseLink2_0JavaManyToOneMappingTests.class);
		suite.addTestSuite(EclipseLink2_0JavaMappedSuperclassTests.class);
		suite.addTestSuite(EclipseLink2_0JavaOneToManyMappingTests.class);
		suite.addTestSuite(EclipseLink2_0JavaOneToOneMappingTests.class);
		suite.addTestSuite(EclipseLink2_3JavaMultitenancyTests.class);
		suite.addTestSuite(EclipseLink2_2JavaEntityTests.class);
		suite.addTestSuite(EclipseLink2_2JavaMappedSuperclassTests.class);
		return suite;
	}

	private JptEclipseLinkCoreJavaContextModelTests() {
		super();
		throw new UnsupportedOperationException();
	}
}

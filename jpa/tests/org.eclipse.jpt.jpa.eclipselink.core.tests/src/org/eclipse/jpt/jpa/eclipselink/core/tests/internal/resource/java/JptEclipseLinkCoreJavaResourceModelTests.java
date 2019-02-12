/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.tests.internal.resource.java;

import junit.framework.Test;
import junit.framework.TestSuite;

public class JptEclipseLinkCoreJavaResourceModelTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(JptEclipseLinkCoreJavaResourceModelTests.class.getName());
		suite.addTestSuite(CacheTests.class);
		suite.addTestSuite(ChangeTrackingTests.class);
		suite.addTestSuite(ConversionValueAnnotationTests.class);
		suite.addTestSuite(ConvertAnnotationTests.class);
		suite.addTestSuite(ConverterAnnotationTests.class);
		suite.addTestSuite(CustomizerAnnotationTests.class);
		suite.addTestSuite(ExistenceCheckingTests.class);
		suite.addTestSuite(JoinFetchTests.class);
		suite.addTestSuite(MutableAnnotationTests.class);
		suite.addTestSuite(ObjectTypeConverterAnnotationTests.class);
		suite.addTestSuite(EclipseLinkPrimaryKeyAnnotationTests.class);
		suite.addTestSuite(PrivateOwnedTests.class);
		suite.addTestSuite(ReadOnlyTests.class);
		suite.addTestSuite(ReadTransformerAnnotationTests.class);
		suite.addTestSuite(StructConverterAnnotationTests.class);
		suite.addTestSuite(TimeOfDayTests.class);
		suite.addTestSuite(TransformationAnnotationTests.class);
		suite.addTestSuite(TypeConverterAnnotationTests.class);
		suite.addTestSuite(WriteTransformerAnnotationTests.class);
		suite.addTestSuite(MapKeyConvertAnnotation2_0Tests.class);
		suite.addTestSuite(EclipseLinkMultitenantAnnotation2_3Tests.class);
		suite.addTestSuite(EclipseLinkTenantDiscriminatorColumnAnnotation2_3Tests.class);
		
		return suite;
	}

	private JptEclipseLinkCoreJavaResourceModelTests() {
		super();
		throw new UnsupportedOperationException();
	}

}

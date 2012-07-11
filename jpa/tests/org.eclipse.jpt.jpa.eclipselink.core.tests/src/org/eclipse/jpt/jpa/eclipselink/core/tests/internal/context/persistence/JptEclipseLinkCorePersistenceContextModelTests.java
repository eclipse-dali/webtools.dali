/*******************************************************************************
 *  Copyright (c) 2010, 2011  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.tests.internal.context.persistence;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class JptEclipseLinkCorePersistenceContextModelTests extends TestCase
{
	public static Test suite() {
		TestSuite suite = new TestSuite(JptEclipseLinkCorePersistenceContextModelTests.class.getName());
		suite.addTestSuite(EclipseLinkPersistenceUnitTests.class);
		suite.addTestSuite(GeneralPropertiesValueModelTests.class);
		suite.addTestSuite(GeneralPropertiesAdapterTests.class);
//		suite.addTestSuite(ConnectionValueModelTests.class);
		suite.addTestSuite(EclipseLinkConnectionTests.class);
		suite.addTestSuite(CustomizationValueModelTests.class);
		suite.addTestSuite(EclipseLinkCustomizationTests.class);
		suite.addTestSuite(CachingValueModelTests.class);
		suite.addTestSuite(CachingAdapterTests.class);
		suite.addTestSuite(LoggingValueModelTests.class);
		suite.addTestSuite(LoggingAdapterTests.class);
		suite.addTestSuite(OptionsValueModelTests.class);
		suite.addTestSuite(OptionsAdapterTests.class);
		suite.addTestSuite(SchemaGenerationValueModelTests.class);
		suite.addTestSuite(SchemaGenerationBasicAdapterTests.class);
		suite.addTestSuite(SchemaGenerationAdapterTests.class);
		suite.addTestSuite(EclipseLink2_0ConnectionTests.class);
		suite.addTestSuite(EclipseLink2_0LoggingTests.class);
		suite.addTestSuite(EclipseLink2_0OptionsTests.class);
		suite.addTestSuite(EclipseLink2_2JpaMetadataConversionTests.class);
		suite.addTestSuite(EclipseLink2_4LoggingTests.class);
		return suite;
	}

	private JptEclipseLinkCorePersistenceContextModelTests() {
		super();
		throw new UnsupportedOperationException();
	}
}

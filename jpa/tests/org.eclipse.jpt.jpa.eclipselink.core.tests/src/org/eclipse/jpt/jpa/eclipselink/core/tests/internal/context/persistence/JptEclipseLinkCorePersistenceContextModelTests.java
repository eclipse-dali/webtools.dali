/*******************************************************************************
 *  Copyright (c) 2010  Oracle. 
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
import org.eclipse.jpt.jpa.eclipselink.core.tests.internal.context.persistence.caching.JptEclipseLinkPersistenceCachingTests;
import org.eclipse.jpt.jpa.eclipselink.core.tests.internal.context.persistence.connection.JptEclipseLinkPersistenceConnectionTests;
import org.eclipse.jpt.jpa.eclipselink.core.tests.internal.context.persistence.customization.JptEclipseLinkPersistenceCustomizationTests;
import org.eclipse.jpt.jpa.eclipselink.core.tests.internal.context.persistence.general.JptEclipseLinkPersistenceGeneralTests;
import org.eclipse.jpt.jpa.eclipselink.core.tests.internal.context.persistence.logging.JptEclipseLinkPersistenceLoggingTests;
import org.eclipse.jpt.jpa.eclipselink.core.tests.internal.context.persistence.options.JptEclipseLinkPersistenceOptionsTests;
import org.eclipse.jpt.jpa.eclipselink.core.tests.internal.context.persistence.schema.generation.JptEclipseLinkPersistenceSchemaGenerationTests;

public class JptEclipseLinkCorePersistenceContextModelTests extends TestCase
{
	public static Test suite() {
		TestSuite suite = new TestSuite(JptEclipseLinkCorePersistenceContextModelTests.class.getName());
		suite.addTestSuite(EclipseLinkPersistenceUnitTests.class);
		suite.addTest(JptEclipseLinkPersistenceGeneralTests.suite());
		suite.addTest(JptEclipseLinkPersistenceConnectionTests.suite());
		suite.addTest(JptEclipseLinkPersistenceCustomizationTests.suite());
		suite.addTest(JptEclipseLinkPersistenceCachingTests.suite());
		suite.addTest(JptEclipseLinkPersistenceLoggingTests.suite());
		suite.addTest(JptEclipseLinkPersistenceOptionsTests.suite());
		suite.addTest(JptEclipseLinkPersistenceSchemaGenerationTests.suite());
		return suite;
	}

	private JptEclipseLinkCorePersistenceContextModelTests() {
		super();
		throw new UnsupportedOperationException();
	}
}

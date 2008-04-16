/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.tests.internal;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.eclipse.jpt.eclipselink.core.tests.internal.caching.JptEclipseLinkCoreCachingTests;
import org.eclipse.jpt.eclipselink.core.tests.internal.connection.JptEclipseLinkCoreConnectionTests;
import org.eclipse.jpt.eclipselink.core.tests.internal.customization.JptEclipseLinkCoreCustomizationTests;
import org.eclipse.jpt.eclipselink.core.tests.internal.logging.JptEclipseLinkCoreLoggingTests;
import org.eclipse.jpt.eclipselink.core.tests.internal.options.JptEclipseLinkCoreOptionsTests;
import org.eclipse.jpt.eclipselink.core.tests.internal.schema.generation.JptEclipseLinkCoreSchemaGenerationTests;

/**
 * decentralize test creation code
 */
public class JptEclipseLinkCoreTests
{
	public static Test suite() {
		TestSuite suite = new TestSuite(JptEclipseLinkCoreTests.class.getPackage().getName());
		
		suite.addTest(JptEclipseLinkCoreConnectionTests.suite());
		suite.addTest(JptEclipseLinkCoreOptionsTests.suite());
		suite.addTest(JptEclipseLinkCoreLoggingTests.suite());
		suite.addTest(JptEclipseLinkCoreCustomizationTests.suite());
		suite.addTest(JptEclipseLinkCoreCachingTests.suite());
		suite.addTest(JptEclipseLinkCoreSchemaGenerationTests.suite());
		
		return suite;
	}

	private JptEclipseLinkCoreTests() {
		super();
		throw new UnsupportedOperationException();
	}
}

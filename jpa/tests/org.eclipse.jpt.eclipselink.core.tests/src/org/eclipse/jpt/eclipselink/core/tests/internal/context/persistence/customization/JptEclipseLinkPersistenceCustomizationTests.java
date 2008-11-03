/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.tests.internal.context.persistence.customization;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * decentralize test creation code
 */
public class JptEclipseLinkPersistenceCustomizationTests
{
	public static Test suite() {
		TestSuite suite = new TestSuite(JptEclipseLinkPersistenceCustomizationTests.class.getPackage().getName());
		
		suite.addTestSuite(CustomizationValueModelTests.class);
		suite.addTestSuite(CustomizationAdapterTests.class);
		
		return suite;
	}

	private JptEclipseLinkPersistenceCustomizationTests() {
		super();
		throw new UnsupportedOperationException();
	}
}

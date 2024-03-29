/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.tests.internal.resource;

import junit.framework.Test;
import junit.framework.TestSuite;
import org.eclipse.jpt.jaxb.core.tests.internal.JaxbCoreTests;
import org.eclipse.jpt.jaxb.core.tests.internal.resource.java.JaxbJavaResourceModelTests;

public class JaxbCoreResourceModelTests
{
	public static Test suite() {
		TestSuite suite = new TestSuite(JaxbCoreResourceModelTests.class.getName());

		if (JaxbCoreTests.requiredJarsExists()) {
			suite.addTestSuite(JaxbIndexResourceTests.class);
			suite.addTestSuite(JaxbPropertiesResourceTests.class);
			suite.addTest(JaxbJavaResourceModelTests.suite());
		}
		else {
			suite.addTest(TestSuite.warning(JaxbCoreTests.buildMissingJarErrorMessage()));
		}
		return suite;
	}

	private JaxbCoreResourceModelTests() {
		super();
		throw new UnsupportedOperationException();
	}
}

/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.tests.internal.resource;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.eclipse.jpt.core.tests.internal.JptCoreTests;
import org.eclipse.jpt.core.tests.internal.jpa2.resource.java.JavaResource2_0Tests;
import org.eclipse.jpt.core.tests.internal.resource.java.JptJavaResourceTests;

/**
 * Required Java system property:
 *   -Dorg.eclipse.jpt.jpa.jar=<jpa.jar path>
 */
public class JptCoreResourceModelTests
	extends TestCase
{
	public static Test suite() {
		TestSuite suite = new TestSuite(JptCoreResourceModelTests.class.getPackage().getName());

		if (JptCoreTests.requiredJarsExists()) {
			suite.addTest(JptJavaResourceTests.suite());
			suite.addTest(JavaResource2_0Tests.suite());
		} else {
			suite.addTest(TestSuite.warning(JptCoreTests.buildMissingJarErrorMessage()));
		}
		return suite;
	}

	private JptCoreResourceModelTests() {
		super();
		throw new UnsupportedOperationException();
	}
}

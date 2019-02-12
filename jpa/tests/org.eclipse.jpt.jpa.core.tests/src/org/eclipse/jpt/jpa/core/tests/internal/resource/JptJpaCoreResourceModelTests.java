/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.resource;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.eclipse.jpt.jpa.core.tests.internal.JptJpaCoreTests;
import org.eclipse.jpt.jpa.core.tests.internal.jpa2.resource.java.JavaResource2_0Tests;
import org.eclipse.jpt.jpa.core.tests.internal.jpa2_1.resource.java.JavaResource2_1Tests;
import org.eclipse.jpt.jpa.core.tests.internal.resource.java.JptJavaResourceTests;

/**
 * Required Java system property:
 *   -Dorg.eclipse.jpt.jpa.jar=<jpa.jar path>
 */
public class JptJpaCoreResourceModelTests
	extends TestCase
{
	public static Test suite() {
		TestSuite suite = new TestSuite(JptJpaCoreResourceModelTests.class.getPackage().getName());

		if (JptJpaCoreTests.requiredJarsExists()) {
			suite.addTest(JptJavaResourceTests.suite());
			suite.addTest(JavaResource2_0Tests.suite());
			suite.addTest(JavaResource2_1Tests.suite());
		} else {
			suite.addTest(TestSuite.warning(JptJpaCoreTests.buildMissingJarErrorMessage()));
		}
		return suite;
	}

	private JptJpaCoreResourceModelTests() {
		super();
		throw new UnsupportedOperationException();
	}
}

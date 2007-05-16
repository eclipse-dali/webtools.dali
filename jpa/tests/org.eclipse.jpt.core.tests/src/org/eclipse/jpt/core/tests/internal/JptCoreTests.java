/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.tests.internal;

import junit.framework.Test;
import junit.framework.TestSuite;
import org.eclipse.jpt.core.tests.internal.content.java.mappings.JptCoreContentJavaMappingsTests;
import org.eclipse.jpt.core.tests.internal.jdtutility.JptCoreJdtUtilityTests;
import org.eclipse.jpt.core.tests.internal.model.JptCoreModelTests;
import org.eclipse.jpt.core.tests.internal.platform.JptCorePlatformTests;

/**
 * Runs all JPT Core Tests
 */
public class JptCoreTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(JptCoreTests.class.getName());
		suite.addTest(JptCoreContentJavaMappingsTests.suite());
		suite.addTest(JptCoreModelTests.suite());
		suite.addTest(JptCoreJdtUtilityTests.suite());
		suite.addTest(JptCorePlatformTests.suite());
		return suite;
	}
	
	private JptCoreTests() {
		super();
		throw new UnsupportedOperationException();
	}

}

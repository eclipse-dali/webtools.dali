/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.tests.internal.platform;

import junit.framework.Test;
import junit.framework.TestSuite;

public class JptCorePlatformTests {

	public static Test suite() {
		return suite(true);
	}

	public static Test suite(boolean all) {
		TestSuite suite = new TestSuite(JptCorePlatformTests.class.getName());
		suite.addTestSuite(JpaPlatformExtensionTests.class);
		if (all) {
			suite.addTestSuite(BaseJpaPlatformTests.class);
			suite.addTestSuite(JpaPlatformTests.class);
		}
		return suite;
	}

	private JptCorePlatformTests() {
		super();
		throw new UnsupportedOperationException();
	}
}

/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.tests.internal.utility;

import junit.framework.Test;
import junit.framework.TestSuite;

public class JptCommonCoreUtilityTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(JptCommonCoreUtilityTests.class.getPackage().getName());
		suite.addTestSuite(MessageLoaderTests.class);
		return suite;
	}

	private JptCommonCoreUtilityTests() {
		super();
		throw new UnsupportedOperationException();
	}
}

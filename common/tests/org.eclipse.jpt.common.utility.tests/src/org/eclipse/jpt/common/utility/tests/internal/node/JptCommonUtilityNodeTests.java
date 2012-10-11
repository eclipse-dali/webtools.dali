/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.node;

import junit.framework.Test;
import junit.framework.TestSuite;

public class JptCommonUtilityNodeTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(JptCommonUtilityNodeTests.class.getPackage().getName());

		suite.addTestSuite(AbstractNodeTests.class);

		return suite;
	}

	private JptCommonUtilityNodeTests() {
		super();
	}
}

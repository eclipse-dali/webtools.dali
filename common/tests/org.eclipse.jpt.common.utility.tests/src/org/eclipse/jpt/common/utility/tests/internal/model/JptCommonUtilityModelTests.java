/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.model;

import junit.framework.Test;
import junit.framework.TestSuite;
import org.eclipse.jpt.common.utility.tests.internal.model.listener.JptUtilityModelListenerTests;
import org.eclipse.jpt.common.utility.tests.internal.model.value.JptUtilityModelValueTests;

public class JptCommonUtilityModelTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(JptCommonUtilityModelTests.class.getPackage().getName());

		suite.addTest(JptUtilityModelListenerTests.suite());
		suite.addTest(JptUtilityModelValueTests.suite());

		suite.addTestSuite(ChangeSupportTests.class);
		suite.addTestSuite(NewEventTests.class);
		suite.addTestSuite(SingleAspectChangeSupportTests.class);

		return suite;
	}

	private JptCommonUtilityModelTests() {
		super();
		throw new UnsupportedOperationException();
	}
}

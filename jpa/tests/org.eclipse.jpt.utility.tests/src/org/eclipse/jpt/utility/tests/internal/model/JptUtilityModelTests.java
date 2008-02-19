/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.tests.internal.model;

import org.eclipse.jpt.utility.tests.internal.model.listener.JptUtilityModelListenerTests;
import org.eclipse.jpt.utility.tests.internal.model.value.JptUtilityModelValueTests;

import junit.framework.Test;
import junit.framework.TestSuite;

public class JptUtilityModelTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(JptUtilityModelTests.class.getPackage().getName());

		suite.addTest(JptUtilityModelListenerTests.suite());
		suite.addTest(JptUtilityModelValueTests.suite());

		suite.addTestSuite(AbstractModelTests.class);
		suite.addTestSuite(NewEventTests.class);

		return suite;
	}

	private JptUtilityModelTests() {
		super();
		throw new UnsupportedOperationException();
	}

}

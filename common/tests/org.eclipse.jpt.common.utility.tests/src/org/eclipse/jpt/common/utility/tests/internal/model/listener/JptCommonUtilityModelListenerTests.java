/*******************************************************************************
 * Copyright (c) 2007, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.model.listener;

import junit.framework.Test;
import junit.framework.TestSuite;

public class JptCommonUtilityModelListenerTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(JptCommonUtilityModelListenerTests.class.getPackage().getName());

		suite.addTestSuite(ReflectiveCollectionChangeListenerTests.class);
		suite.addTestSuite(ReflectiveListChangeListenerTests.class);
		suite.addTestSuite(ReflectivePropertyChangeListenerTests.class);
		suite.addTestSuite(ReflectiveStateChangeListenerTests.class);

		return suite;
	}

	private JptCommonUtilityModelListenerTests() {
		super();
		throw new UnsupportedOperationException();
	}
}

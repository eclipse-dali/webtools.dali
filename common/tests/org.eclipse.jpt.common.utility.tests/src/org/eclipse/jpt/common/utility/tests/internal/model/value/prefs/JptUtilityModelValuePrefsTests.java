/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.model.value.prefs;

import junit.framework.Test;
import junit.framework.TestSuite;

public class JptUtilityModelValuePrefsTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(JptUtilityModelValuePrefsTests.class.getPackage().getName());

		suite.addTestSuite(PreferencesCollectionValueModelTests.class);
		suite.addTestSuite(PreferencePropertyValueModelTests.class);

		return suite;
	}

	private JptUtilityModelValuePrefsTests() {
		super();
		throw new UnsupportedOperationException();
	}
}

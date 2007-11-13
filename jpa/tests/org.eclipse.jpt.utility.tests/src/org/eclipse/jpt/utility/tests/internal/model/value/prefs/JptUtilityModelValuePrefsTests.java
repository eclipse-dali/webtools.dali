/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.tests.internal.model.value.prefs;

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

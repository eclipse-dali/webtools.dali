/*******************************************************************************
 * Copyright (c) 2012, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.reference;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * decentralize test creation code
 */
public class JptCommonUtilityReferenceTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(JptCommonUtilityReferenceTests.class.getPackage().getName());

		suite.addTestSuite(AbstractBooleanReferenceTests.class);
		suite.addTestSuite(AbstractIntReferenceTests.class);
		suite.addTestSuite(AbstractModifiableBooleanReferenceTests.class);
		suite.addTestSuite(AbstractModifiableIntReferenceTests.class);
		suite.addTestSuite(AbstractModifiableObjectReferenceTests.class);
		suite.addTestSuite(AbstractObjectReferenceTests.class);
		suite.addTestSuite(FalseBooleanReferenceTests.class);
		suite.addTestSuite(FlaggedObjectReferenceTests.class);
		suite.addTestSuite(LazyObjectReferenceTests.class);
		suite.addTestSuite(ReferenceToolsTests.class);
		suite.addTestSuite(SimpleBooleanReferenceTests.class);
		suite.addTestSuite(SimpleIntReferenceTests.class);
		suite.addTestSuite(SimpleObjectReferenceTests.class);
		suite.addTestSuite(SynchronizedBooleanTests.class);
		suite.addTestSuite(SynchronizedIntTests.class);
		suite.addTestSuite(SynchronizedObjectTests.class);
		suite.addTestSuite(TrueBooleanReferenceTests.class);

		return suite;
	}

	private JptCommonUtilityReferenceTests() {
		super();
		throw new UnsupportedOperationException();
	}
}

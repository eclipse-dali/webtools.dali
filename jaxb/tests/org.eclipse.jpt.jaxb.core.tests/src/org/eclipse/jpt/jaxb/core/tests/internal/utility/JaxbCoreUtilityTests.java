/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.tests.internal.utility;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * decentralize test creation code
 */
public class JaxbCoreUtilityTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(JaxbCoreUtilityTests.class.getPackage().getName());

		suite.addTestSuite(SynchronizerTests.class);
		suite.addTestSuite(SynchronousSynchronizerTests.class);

		return suite;
	}

	private JaxbCoreUtilityTests() {
		super();
		throw new UnsupportedOperationException();
	}
}

/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.tests.internal.synchronizers;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * decentralize test creation code
 */
public class JptUtilitySynchronizersTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(JptUtilitySynchronizersTests.class.getPackage().getName());

		suite.addTestSuite(AsynchronousSynchronizerTests.class);
		suite.addTestSuite(SynchronizerTests.class);
		suite.addTestSuite(SynchronousSynchronizerTests.class);

		return suite;
	}

	private JptUtilitySynchronizersTests() {
		super();
		throw new UnsupportedOperationException();
	}

}

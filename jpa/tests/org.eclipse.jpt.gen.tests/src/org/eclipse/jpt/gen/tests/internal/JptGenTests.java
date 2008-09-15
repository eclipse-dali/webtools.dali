/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.gen.tests.internal;

import junit.framework.Test;
import junit.framework.TestSuite;
import org.eclipse.jpt.gen.tests.internal.EntityGenToolsTests;

/**
 * 
 */
public class JptGenTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("JPT Entity Generation Tests"); //$NON-NLS-1$
		suite.addTestSuite(EntityGenToolsTests.class);
		return suite;
	}

	private JptGenTests() {
		super();
		throw new UnsupportedOperationException();
	}

}

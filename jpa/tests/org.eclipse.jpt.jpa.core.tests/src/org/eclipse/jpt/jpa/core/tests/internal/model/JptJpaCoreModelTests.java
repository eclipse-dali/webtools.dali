/*******************************************************************************
 * Copyright (c) 2007, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.model;

import junit.framework.Test;
import junit.framework.TestSuite;

public class JptJpaCoreModelTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(JptJpaCoreModelTests.class.getPackage().getName());
		suite.addTestSuite(JpaProjectManagerTests.class);
		return suite;
	}

	private JptJpaCoreModelTests() {
		super();
		throw new UnsupportedOperationException();
	}

}

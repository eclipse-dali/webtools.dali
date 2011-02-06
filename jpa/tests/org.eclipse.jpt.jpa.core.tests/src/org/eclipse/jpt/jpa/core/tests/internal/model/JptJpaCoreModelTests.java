/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
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

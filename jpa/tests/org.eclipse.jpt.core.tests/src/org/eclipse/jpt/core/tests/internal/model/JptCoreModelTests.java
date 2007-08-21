/*******************************************************************************
 *  Copyright (c) 2007 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.tests.internal.model;

import junit.framework.Test;
import junit.framework.TestSuite;

public class JptCoreModelTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(JptCoreModelTests.class.getPackage().getName());
		suite.addTestSuite(ModelInitializationTests.class);
		return suite;
	}

	private JptCoreModelTests() {
		super();
		throw new UnsupportedOperationException();
	}

}

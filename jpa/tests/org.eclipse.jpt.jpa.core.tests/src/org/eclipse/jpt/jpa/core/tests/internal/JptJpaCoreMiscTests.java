/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal;

import org.eclipse.jpt.common.core.tests.BundleActivatorTest;
import org.eclipse.jpt.jpa.core.JpaProject;
import junit.framework.Test;
import junit.framework.TestSuite;

public class JptJpaCoreMiscTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(JptJpaCoreMiscTests.class.getPackage().getName());
		suite.addTestSuite(JpaPreferencesTests.class);
		suite.addTest(new BundleActivatorTest(JpaProject.class));
		return suite;
	}

	private JptJpaCoreMiscTests() {
		super();
		throw new UnsupportedOperationException();
	}
}

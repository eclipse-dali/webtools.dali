/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.tests.internal;

import junit.framework.Test;
import junit.framework.TestSuite;
import org.eclipse.jpt.common.core.tests.BundleActivatorTest;
import org.eclipse.jpt.jaxb.core.JaxbProject;

public class JaxbCoreMiscTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(JaxbCoreMiscTests.class.getName());

		suite.addTestSuite(SchemaLibraryTests.class);
		suite.addTest(new BundleActivatorTest(JaxbProject.class));

		return suite;
	}

	private JaxbCoreMiscTests() {
		super();
		throw new UnsupportedOperationException();
	}
}

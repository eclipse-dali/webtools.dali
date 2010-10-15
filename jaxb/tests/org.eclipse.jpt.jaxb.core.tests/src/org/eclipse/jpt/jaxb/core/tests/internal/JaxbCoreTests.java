/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
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
import org.eclipse.jpt.jaxb.core.tests.internal.resource.JaxbCoreResourceModelTests;

public class JaxbCoreTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(JaxbCoreTests.class.getPackage().getName());
		suite.addTest(JaxbCoreResourceModelTests.suite());
		return suite;
	}
	
	private JaxbCoreTests() {
		super();
		throw new UnsupportedOperationException();
	}
}

/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt2_0.core.tests.internal.resource.java;

import junit.framework.Test;
import junit.framework.TestSuite;

public class Generic2_0JavaResourceTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(Generic2_0JavaResourceTests.class.getName());
		
		suite.addTestSuite(AccessAnnotationTests.class);
			
		return suite;
	}

	private Generic2_0JavaResourceTests() {
		super();
		throw new UnsupportedOperationException();
	}

}

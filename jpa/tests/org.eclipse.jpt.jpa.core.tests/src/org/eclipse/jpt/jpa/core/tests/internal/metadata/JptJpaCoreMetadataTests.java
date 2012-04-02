/*******************************************************************************
* Copyright (c) 2012 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.metadata;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 *  JptJpaCoreMetadataTests
 */
public class JptJpaCoreMetadataTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(JptJpaCoreMetadataTests.class.getPackage().getName());
		suite.addTestSuite(JpaEntityGenMetadataTests.class);
		suite.addTestSuite(JpaJpqlMetadataTests.class);
		suite.addTestSuite(JpaValidationMetadataTests.class);
		return suite;
	}

	private JptJpaCoreMetadataTests() {
		super();
		throw new UnsupportedOperationException();
	}

}
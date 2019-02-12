/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.jpa2.context.persistence;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class Generic2_0PersistenceContextModelTests
	extends TestCase
{

	public static Test suite() {
		TestSuite suite = new TestSuite(Generic2_0PersistenceContextModelTests.class.getPackage().getName());
		suite.addTestSuite(Generic2_0ConnectionTests.class);
		suite.addTestSuite(Generic2_0OptionsTests.class);
		suite.addTestSuite(Generic2_0JpaMetadataConversionTests.class);
		return suite;
	}

	private Generic2_0PersistenceContextModelTests() {
		super();
		throw new UnsupportedOperationException();
	}
}

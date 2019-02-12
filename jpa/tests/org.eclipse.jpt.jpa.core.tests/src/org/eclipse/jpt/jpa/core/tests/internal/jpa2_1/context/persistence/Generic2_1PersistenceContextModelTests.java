/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.jpa2_1.context.persistence;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 *  Generic2_1PersistenceContextModelTests
 */
public class Generic2_1PersistenceContextModelTests extends TestCase
{

	public static Test suite() {
		TestSuite suite = new TestSuite(Generic2_1PersistenceContextModelTests.class.getPackage().getName());
		suite.addTestSuite(Generic2_1SchemaGenerationTests.class);
		return suite;
	}

private Generic2_1PersistenceContextModelTests() {
	super();
	throw new UnsupportedOperationException();
}
}

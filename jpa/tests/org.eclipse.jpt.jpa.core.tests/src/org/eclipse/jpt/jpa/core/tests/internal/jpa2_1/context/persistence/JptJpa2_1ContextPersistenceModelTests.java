/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.jpa2_1.context.persistence;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class JptJpa2_1ContextPersistenceModelTests
	extends TestCase
{
	public static Test suite() {
		TestSuite suite = new TestSuite(JptJpa2_1ContextPersistenceModelTests.class.getPackage().getName());
		suite.addTestSuite(ClassRef2_1Tests.class);
		suite.addTestSuite(PersistenceUnit2_1Tests.class);
		return suite;
	}

	private JptJpa2_1ContextPersistenceModelTests() {
		throw new UnsupportedOperationException();
	}
}

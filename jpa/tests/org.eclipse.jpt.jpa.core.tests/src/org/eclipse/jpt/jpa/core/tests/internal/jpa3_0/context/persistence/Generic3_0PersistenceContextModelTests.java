/*******************************************************************************
 * Copyright (c) 2024 Lakshminarayana Nekkanti. All rights reserved.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Lakshminarayana Nekkanti - initial API and implementation
 *
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.jpa3_0.context.persistence;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * JPA 3.0 persistence context model tests suite.
 * <p>
 * Covers schema-generation properties available on a JPA 3.0 persistence unit.
 */
public class Generic3_0PersistenceContextModelTests extends TestCase
{
	public static Test suite() {
		TestSuite suite = new TestSuite(Generic3_0PersistenceContextModelTests.class.getPackage().getName());
		suite.addTestSuite(Generic3_0SchemaGenerationTests.class);
		return suite;
	}

	private Generic3_0PersistenceContextModelTests() {
		super();
		throw new UnsupportedOperationException();
	}
}

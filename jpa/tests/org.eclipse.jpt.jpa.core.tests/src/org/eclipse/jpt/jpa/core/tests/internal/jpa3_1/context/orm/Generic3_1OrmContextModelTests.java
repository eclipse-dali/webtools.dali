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
package org.eclipse.jpt.jpa.core.tests.internal.jpa3_1.context.orm;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * JPA 3.1 ORM context model test suite.
 * <p>
 * JPA 3.1 inherits all ORM context model behaviour from JPA 3.0.
 * This suite provides a registration point for any 3.1-specific ORM context
 * model tests added in the future.
 */
public class Generic3_1OrmContextModelTests extends TestCase
{
	public static Test suite() {
		TestSuite suite = new TestSuite(Generic3_1OrmContextModelTests.class.getPackage().getName());
		// Placeholder: add 3.1-specific ORM context model test classes here.
		return suite;
	}

	private Generic3_1OrmContextModelTests() {
		super();
		throw new UnsupportedOperationException();
	}
}

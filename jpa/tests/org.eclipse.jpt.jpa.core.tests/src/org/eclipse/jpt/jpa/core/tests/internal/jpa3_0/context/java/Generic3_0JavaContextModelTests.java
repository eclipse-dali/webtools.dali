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
package org.eclipse.jpt.jpa.core.tests.internal.jpa3_0.context.java;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * JPA 3.0 Java context model test suite.
 * <p>
 * JPA 3.0 does not introduce new Java context model types beyond JPA 2.1;
 * the primary change is the {@code jakarta.persistence} annotation namespace.
 * Java-mapping tests that exercise namespace-agnostic behaviour are inherited
 * from the 2.x suites.  This suite provides a registration point for any
 * 3.0-specific Java context model tests added in the future.
 */
public class Generic3_0JavaContextModelTests extends TestCase
{
	public static Test suite() {
		TestSuite suite = new TestSuite(Generic3_0JavaContextModelTests.class.getPackage().getName());
		// Placeholder: add 3.0-specific Java context model test classes here.
		return suite;
	}

	private Generic3_0JavaContextModelTests() {
		super();
		throw new UnsupportedOperationException();
	}
}

/*******************************************************************************
 * Copyright (c) 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.closure;

import org.eclipse.jpt.common.utility.closure.Closure;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.closure.BooleanClosure;
import org.eclipse.jpt.common.utility.internal.closure.ClosureTools;
import junit.framework.TestCase;

@SuppressWarnings("nls")
public class BooleanClosureTests
	extends TestCase
{
	public BooleanClosureTests(String name) {
		super(name);
	}

	public void testExecute() {
		Adapter adapter = new Adapter();
		assertNull(adapter.value);
		Closure<Boolean> closure = ClosureTools.booleanClosure(adapter);
		closure.execute(Boolean.TRUE);
		assertEquals("foo", adapter.value);
		closure.execute(Boolean.FALSE);
		assertEquals("bar", adapter.value);
	}

	public void testExecute_NPE() {
		Adapter adapter = new Adapter();
		assertNull(adapter.value);
		Closure<Boolean> closure = ClosureTools.booleanClosure(adapter);
		closure.execute(Boolean.TRUE);
		assertEquals("foo", adapter.value);
		boolean exCaught = false;
		try {
			closure.execute(null);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testCtor_NPE() {
		boolean exCaught = false;
		try {
			Closure<Boolean> closure = ClosureTools.booleanClosure(null);
			fail("bogus closure: " + closure);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testToString() {
		Adapter adapter = new Adapter();
		assertNull(adapter.value);
		Closure<Boolean> closure = ClosureTools.booleanClosure(adapter);
		assertTrue(closure.toString().indexOf("Adapter") != -1);
	}

	public static class Adapter
		implements BooleanClosure.Adapter
	{
		String value = null;
		public void execute(boolean argument) {
			this.value = (argument ? "foo" : "bar");
		}
		@Override
		public String toString() {
			return ObjectTools.toString(this);
		}
	}
}

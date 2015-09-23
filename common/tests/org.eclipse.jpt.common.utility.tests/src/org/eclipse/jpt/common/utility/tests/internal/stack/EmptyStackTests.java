/*******************************************************************************
 * Copyright (c) 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.stack;

import java.util.EmptyStackException;
import org.eclipse.jpt.common.utility.internal.stack.EmptyStack;
import org.eclipse.jpt.common.utility.internal.stack.StackTools;
import org.eclipse.jpt.common.utility.stack.Stack;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;
import junit.framework.TestCase;

@SuppressWarnings("nls")
public class EmptyStackTests
	extends TestCase
{
	public EmptyStackTests(String name) {
		super(name);
	}

	public void testPush() {
		Stack<String> stack = StackTools.emptyStack();
		boolean exCaught = false;
		try {
			stack.push("junk");
			fail();
		} catch (UnsupportedOperationException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testPop() {
		Stack<String> stack = EmptyStack.<String>instance();
		boolean exCaught = false;
		try {
			String bogus = stack.pop();
			fail(bogus);
		} catch (EmptyStackException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testPeek() {
		Stack<String> stack = EmptyStack.<String>instance();
		boolean exCaught = false;
		try {
			String bogus = stack.peek();
			fail(bogus);
		} catch (EmptyStackException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testIsEmpty() {
		Stack<String> stack = EmptyStack.<String>instance();
		assertTrue(stack.isEmpty());
	}

	public void testToString() {
		Stack<String> stack = EmptyStack.<String>instance();
		assertEquals("[]", stack.toString());
	}

	public void testSerialization() throws Exception {
		Stack<String> stack = EmptyStack.<String>instance();
		assertSame(stack, TestTools.serialize(stack));
	}
}

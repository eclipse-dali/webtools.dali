/*******************************************************************************
 * Copyright (c) 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.stack;

import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.stack.StackTools;
import org.eclipse.jpt.common.utility.stack.Stack;

@SuppressWarnings("nls")
public class ConcurrentStackTests
	extends StackTests
{
	public ConcurrentStackTests(String name) {
		super(name);
	}

	@Override
	Stack<String> buildStack() {
		return StackTools.concurrentStack();
	}

	@Override
	Stack<Integer> buildConcurrentStack() {
		return StackTools.concurrentStack();
	}

	public void testNodeToString() {
		Stack<String> stack = this.buildStack();
		String first = "first";
		String second = "second";
		String third = "third";
		stack.push(first);
		stack.push(second);
		stack.push(third);

		Object headRef = ObjectTools.get(stack, "headRef");
		Object head = ObjectTools.get(headRef, "value");
		assertTrue(head.toString().startsWith("ConcurrentStack.Node"));
		assertTrue(head.toString().endsWith("(third)"));
	}

	public void testPush_npe() {
		Stack<String> stack = this.buildStack();
		boolean exCaught = false;
		try {
			stack.push(null);
			fail();
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	@Override
	public void testEmptyStackExceptionPeek() {
		// different behavior
	}

	public void testEmptyStackPeek() {
		Stack<String> stack = this.buildStack();
		String first = "first";
		String second = "second";

		stack.push(first);
		stack.push(second);
		assertEquals(second, stack.peek());
		assertEquals(second, stack.pop());
		assertEquals(first, stack.peek());
		assertEquals(first, stack.pop());

		assertNull(stack.peek());
	}

	@Override
	public void testEmptyStackExceptionPop() {
		// different behavior
	}

	public void testEmptyStackPop() {
		Stack<String> stack = this.buildStack();
		String first = "first";
		String second = "second";

		stack.push(first);
		stack.push(second);
		assertEquals(second, stack.peek());
		assertEquals(second, stack.pop());
		assertEquals(first, stack.peek());
		assertEquals(first, stack.pop());

		assertNull(stack.pop());
	}

	@Override
	public void testSerialization() throws Exception {
		// unsupported
	}

	@Override
	public void testSerialization_empty() throws Exception {
		// unsupported
	}

	@Override
	public void testClone() {
		// unsupported
	}
}

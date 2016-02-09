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
public class CachingConcurrentStackTests
	extends StackTests
{
	public CachingConcurrentStackTests(String name) {
		super(name);
	}

	@Override
	Stack<String> buildStack() {
		return StackTools.cachingConcurrentStack();
	}

	@Override
	Stack<Integer> buildConcurrentStack() {
		return StackTools.cachingConcurrentStack();
	}

	public void testCache() {
		Stack<String> stack = this.buildStack();
		String first = "first";
		String second = "second";
		String third = "third";
		String fourth = "fourth";
		String fifth = "fifth";

		this.verifyNodeCache(0, stack);
		stack.push(first);
		this.verifyNodeCache(0, stack);
		stack.push(second);
		stack.push(third);
		stack.push(fourth);
		stack.push(fifth);
		this.verifyNodeCache(0, stack);
		Object headRef = ObjectTools.get(stack, "cacheHeadRef");
		Object pair = ObjectTools.get(headRef, "pair");
		assertNull(ObjectTools.get(pair, "reference"));

		stack.pop();
		this.verifyNodeCache(1, stack);
		stack.pop();
		this.verifyNodeCache(2, stack);
		stack.pop();
		this.verifyNodeCache(3, stack);
		stack.pop();
		this.verifyNodeCache(4, stack);
		stack.pop();
		this.verifyNodeCache(5, stack);
		stack.push(first);
		this.verifyNodeCache(4, stack);
		stack.push(second);
		this.verifyNodeCache(3, stack);
		stack.push(third);
		this.verifyNodeCache(2, stack);
		stack.push(fourth);
		this.verifyNodeCache(1, stack);
		stack.push(fifth);
		this.verifyNodeCache(0, stack);
	}

	public void verifyNodeCache(int size, Object stack) {
		int nodeCount = 0;
		Object headRef = ObjectTools.get(stack, "cacheHeadRef");
		Object pair = ObjectTools.get(headRef, "pair");
		for (Object node = ObjectTools.get(pair, "reference"); node != null; node = ObjectTools.get(node, "next")) {
			nodeCount++;
		}
		assertEquals(size, nodeCount);
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
		Object pair = ObjectTools.get(headRef, "pair");
		Object head = ObjectTools.get(pair, "reference");
		assertTrue(head.toString().startsWith("CachingConcurrentStack.Node"));
		assertTrue(head.toString().endsWith("(third)"));
	}

	public void testPushNullException() {
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

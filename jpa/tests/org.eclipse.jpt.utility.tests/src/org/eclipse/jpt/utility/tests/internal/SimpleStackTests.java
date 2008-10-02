/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.tests.internal;

import java.util.EmptyStackException;
import junit.framework.TestCase;
import org.eclipse.jpt.utility.internal.SimpleStack;
import org.eclipse.jpt.utility.internal.Stack;

public class SimpleStackTests extends TestCase {

	public SimpleStackTests(String name) {
		super(name);
	}

	public void testIsEmpty() {
		Stack<String> stack = new SimpleStack<String>();
		assertTrue(stack.isEmpty());
		stack.push("first");
		assertFalse(stack.isEmpty());
		stack.push("second");
		assertFalse(stack.isEmpty());
		stack.pop();
		assertFalse(stack.isEmpty());
		stack.pop();
		assertTrue(stack.isEmpty());
	}

	public void testPushAndPop() {
		Stack<String> stack = new SimpleStack<String>();
		String first = "first";
		String second = "second";

		stack.push(first);
		stack.push(second);
		assertEquals(second, stack.pop());
		assertEquals(first, stack.pop());
	}

	public void testPushAndPeek() {
		Stack<String> stack = new SimpleStack<String>();
		String first = "first";
		String second = "second";

		stack.push(first);
		stack.push(second);
		assertEquals(second, stack.peek());
		assertEquals(second, stack.peek());
		assertEquals(second, stack.pop());
		assertEquals(first, stack.peek());
		assertEquals(first, stack.peek());
		assertEquals(first, stack.pop());
	}

	public void testEmptyStackExceptionPeek() {
		Stack<String> stack = new SimpleStack<String>();
		String first = "first";
		String second = "second";

		stack.push(first);
		stack.push(second);
		assertEquals(second, stack.peek());
		assertEquals(second, stack.pop());
		assertEquals(first, stack.peek());
		assertEquals(first, stack.pop());

		boolean exCaught = false;
		try {
			stack.peek();
		} catch (EmptyStackException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testEmptyStackExceptionPop() {
		Stack<String> stack = new SimpleStack<String>();
		String first = "first";
		String second = "second";

		stack.push(first);
		stack.push(second);
		assertEquals(second, stack.peek());
		assertEquals(second, stack.pop());
		assertEquals(first, stack.peek());
		assertEquals(first, stack.pop());

		boolean exCaught = false;
		try {
			stack.pop();
		} catch (EmptyStackException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testClone() {
		SimpleStack<String> stack = new SimpleStack<String>();
		stack.push("first");
		stack.push("second");
		stack.push("third");

		this.verifyClone(stack, stack.clone());
	}

	public void testSerialization() throws Exception {
		SimpleStack<String> stack = new SimpleStack<String>();
		stack.push("first");
		stack.push("second");
		stack.push("third");

		this.verifyClone(stack, TestTools.serialize(stack));
	}

	private void verifyClone(Stack<String> original, Stack<String> clone) {
		assertNotSame(original, clone);
		assertEquals(original.peek(), clone.peek());
		assertEquals(original.pop(), clone.pop());
		assertEquals(original.peek(), clone.peek());
		assertEquals(original.pop(), clone.pop());
		assertEquals(original.isEmpty(), clone.isEmpty());
		assertEquals(original.peek(), clone.peek());
		assertEquals(original.pop(), clone.pop());
		assertTrue(original.isEmpty());
		assertEquals(original.isEmpty(), clone.isEmpty());

		original.push("fourth");
		assertFalse(original.isEmpty());
		// clone should still be empty
		assertTrue(clone.isEmpty());
	}

}

/*******************************************************************************
 * Copyright (c) 2007, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.stack;

import java.util.EmptyStackException;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.stack.Stack;
import org.eclipse.jpt.common.utility.tests.internal.MultiThreadedTestCase;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;

//subclass MultiThreadedTestCase for subclasses of this class
@SuppressWarnings("nls")
public abstract class StackTests
	extends MultiThreadedTestCase
{
	public StackTests(String name) {
		super(name);
	}

	abstract Stack<String> buildStack();

	public void testIsEmpty() {
		Stack<String> stack = this.buildStack();
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
		Stack<String> stack = this.buildStack();
		String first = "first";
		String second = "second";

		stack.push(first);
		stack.push(second);
		assertEquals(second, stack.pop());
		assertEquals(first, stack.pop());
	}

	public void testPushAndPeek() {
		Stack<String> stack = this.buildStack();
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
		Stack<String> stack = this.buildStack();
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
			fail();
		} catch (EmptyStackException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testEmptyStackExceptionPop() {
		Stack<String> stack = this.buildStack();
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
			fail();
		} catch (EmptyStackException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testClone() {
		Stack<String> stack = this.buildStack();
		stack.push("first");
		stack.push("second");
		stack.push("third");

		@SuppressWarnings("unchecked")
		Stack<String> clone = (Stack<String>) ObjectTools.execute(stack, "clone");
		this.verifyClone(stack, clone);
	}

	public void testSerialization() throws Exception {
		Stack<String> stack = this.buildStack();
		stack.push("first");
		stack.push("second");
		stack.push("third");

		this.verifyClone(stack, TestTools.serialize(stack));
	}

	protected void verifyClone(Stack<String> original, Stack<String> clone) {
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

	public void testSerialization_empty() throws Exception {
		Stack<String> stack = this.buildStack();
		Stack<String> clone = TestTools.serialize(stack);
		assertNotSame(stack, clone);
		assertTrue(clone.isEmpty());
		stack.push("fourth");
		assertFalse(stack.isEmpty());
		// clone should still be empty
		assertTrue(clone.isEmpty());
	}

	public void testToString() throws Exception {
		Stack<String> stack = this.buildStack();
		stack.push("first");
		stack.push("second");
		stack.push("third");

		assertEquals("[third, second, first]", stack.toString());
	}
}

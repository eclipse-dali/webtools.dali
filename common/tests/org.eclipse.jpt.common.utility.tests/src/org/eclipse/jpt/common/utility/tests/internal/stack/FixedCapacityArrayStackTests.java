/*******************************************************************************
 * Copyright (c) 2015, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.stack;

import java.util.ArrayList;
import org.eclipse.jpt.common.utility.internal.stack.FixedCapacityArrayStack;
import org.eclipse.jpt.common.utility.internal.stack.StackTools;
import org.eclipse.jpt.common.utility.stack.Stack;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;

@SuppressWarnings("nls")
public class FixedCapacityArrayStackTests
	extends StackTests
{
	public FixedCapacityArrayStackTests(String name) {
		super(name);
	}

	@Override
	FixedCapacityArrayStack<String> buildStack() {
		return StackTools.fixedCapacityArrayStack(10);
	}

	public void testCollectionConstructor() {
		ArrayList<String> c = new ArrayList<>();
		c.add("first");
		c.add("second");
		c.add("third");
		c.add("fourth");
		c.add("fifth");
		c.add("sixth");
		c.add("seventh");
		c.add("eighth");
		c.add("ninth");
		c.add("tenth");
		Stack<String> stack = StackTools.fixedCapacityArrayStack(c);

		assertFalse(stack.isEmpty());
		assertEquals("tenth", stack.peek());
		assertEquals("tenth", stack.pop());
		assertEquals("ninth", stack.pop());
		assertFalse(stack.isEmpty());
		assertEquals("eighth", stack.peek());
		assertEquals("eighth", stack.pop());
		assertEquals("seventh", stack.pop());
		assertEquals("sixth", stack.pop());
		assertEquals("fifth", stack.pop());
		assertEquals("fourth", stack.pop());
		assertEquals("third", stack.pop());
		assertEquals("second", stack.pop());
		assertEquals("first", stack.pop());
		assertTrue(stack.isEmpty());
	}

	public void testIsFull() {
		FixedCapacityArrayStack<String> stack = this.buildStack();
		assertFalse(stack.isFull());
		stack.push("first");
		assertFalse(stack.isFull());
		stack.push("second");
		assertFalse(stack.isFull());
		stack.push("third");
		stack.push("fourth");
		stack.push("fifth");
		stack.push("sixth");
		stack.push("seventh");
		stack.push("eighth");
		stack.push("ninth");
		stack.push("tenth");
		assertTrue(stack.isFull());

		stack.pop();
		assertFalse(stack.isEmpty());
		stack.pop();
		stack.pop();
		stack.pop();
		stack.pop();
		stack.pop();
		stack.pop();
		stack.pop();
		assertFalse(stack.isFull());
	}

	public void testArrayCapacityExceeded() {
		Stack<String> stack = this.buildStack();
		assertTrue(stack.isEmpty());
		stack.push("first");
		assertFalse(stack.isEmpty());
		stack.push("second");
		assertFalse(stack.isEmpty());
		stack.push("third");
		stack.push("fourth");
		stack.push("fifth");
		stack.push("sixth");
		stack.push("seventh");
		stack.push("eighth");
		stack.push("ninth");
		stack.push("tenth");

		boolean exCaught = false;
		try {
			stack.push("eleventh");
			fail("bogus");
		} catch (IllegalStateException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);

		assertFalse(stack.isEmpty());
		assertEquals("tenth", stack.peek());
		assertEquals("tenth", stack.pop());
		assertEquals("ninth", stack.pop());
		assertFalse(stack.isEmpty());
		assertEquals("eighth", stack.peek());
		assertEquals("eighth", stack.pop());
		assertEquals("seventh", stack.pop());
		assertEquals("sixth", stack.pop());
		assertEquals("fifth", stack.pop());
		assertEquals("fourth", stack.pop());
		assertEquals("third", stack.pop());
		assertEquals("second", stack.pop());
		assertEquals("first", stack.pop());
		assertTrue(stack.isEmpty());
	}

	public void testSerialization_fullArray() throws Exception {
		Stack<String> stack = new FixedCapacityArrayStack<>(3);
		stack.push("first");
		stack.push("second");
		stack.push("third");

		this.verifyClone(stack, TestTools.serialize(stack));
	}

	public void testConstructorInt_IAE() throws Exception {
		boolean exCaught = false;
		try {
			Stack<String> stack = StackTools.fixedCapacityArrayStack(-3);
			fail("bogus stack: " + stack);
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testToString_empty() throws Exception {
		Stack<String> stack = this.buildStack();
		assertEquals("[]", stack.toString());
	}
}

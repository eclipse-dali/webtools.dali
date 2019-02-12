/*******************************************************************************
 * Copyright (c) 2012, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.stack;

import java.util.ArrayList;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.stack.ArrayStack;
import org.eclipse.jpt.common.utility.internal.stack.StackTools;
import org.eclipse.jpt.common.utility.stack.Stack;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;

@SuppressWarnings("nls")
public class ArrayStackTests
	extends StackTests
{
	public ArrayStackTests(String name) {
		super(name);
	}

	@Override
	Stack<String> buildStack() {
		return new ArrayStack<String>();
	}

	public void testCollectionConstructor() {
		ArrayList<String> c = new ArrayList<String>();
		c.add("first");
		c.add("second");
		c.add("third");
		c.add("fourth");
		c.add("fifth");
		c.add("sixth");
		c.add("seventh");
		c.add("eighth");
		c.add("ninth");
		c.add("tenth"); // force some free space
		Stack<String> stack = StackTools.arrayStack(c);

		assertFalse(stack.isEmpty());
		assertEquals("tenth", stack.peek());
		stack.push("eleventh");
		stack.push("twelfth");

		assertEquals("twelfth", stack.peek());
		assertEquals("twelfth", stack.pop());
		assertEquals("eleventh", stack.pop());
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
		Stack<String> stack = new ArrayStack<String>(3);
		stack.push("first");
		stack.push("second");
		stack.push("third");

		this.verifyClone(stack, TestTools.serialize(stack));
	}

	public void testEnsureCapacity() {
		ArrayStack<String> queue = StackTools.arrayStack(0);
		queue.ensureCapacity(7);
		assertEquals(7, ((Object[]) ObjectTools.get(queue, "elements")).length);
	}

	public void testTrimToSize() throws Exception {
		ArrayStack<String> stack = new ArrayStack<String>(5);
		stack.push("first");
		stack.push("second");
		stack.push("third");

		Object[] elements = (Object[]) ObjectTools.get(stack, "elements");
		assertEquals(5, elements.length);

		stack.trimToSize();
		elements = (Object[]) ObjectTools.get(stack, "elements");
		assertEquals(3, elements.length);
	}

	public void testTrimToSize_noChange() throws Exception {
		ArrayStack<String> stack = new ArrayStack<String>(3);
		stack.push("first");
		stack.push("second");
		stack.push("third");

		Object[] elements = (Object[]) ObjectTools.get(stack, "elements");
		assertEquals(3, elements.length);

		stack.trimToSize();
		elements = (Object[]) ObjectTools.get(stack, "elements");
		assertEquals(3, elements.length);
	}

	public void testConstructorInt_IAE() throws Exception {
		boolean exCaught = false;
		try {
			Stack<String> stack = StackTools.arrayStack(-3);
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

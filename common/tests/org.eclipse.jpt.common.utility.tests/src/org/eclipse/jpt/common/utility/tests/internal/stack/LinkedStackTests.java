/*******************************************************************************
 * Copyright (c) 2012, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.stack;

import java.util.Arrays;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.stack.LinkedStack;
import org.eclipse.jpt.common.utility.internal.stack.StackTools;
import org.eclipse.jpt.common.utility.stack.Stack;

@SuppressWarnings("nls")
public class LinkedStackTests
	extends StackTests
{
	public LinkedStackTests(String name) {
		super(name);
	}

	@Override
	Stack<String> buildStack() {
		return StackTools.linkedStack();
	}

	public void testConstructorInt_IAE() throws Exception {
		boolean exCaught = false;
		try {
			Stack<String> stack = StackTools.linkedStack(-3);
			fail("bogus stack: " + stack);
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testSize() {
		Stack<String> stack = this.buildStack();
		String first = "first";
		String second = "second";
		String third = "third";

		assertEquals(0, ((Integer) ObjectTools.execute(stack, "size")).intValue());
		stack.push(first);
		stack.push(second);
		assertEquals(2, ((Integer) ObjectTools.execute(stack, "size")).intValue());
		stack.push(third);
		assertEquals(3, ((Integer) ObjectTools.execute(stack, "size")).intValue());
		stack.pop();
		assertEquals(2, ((Integer) ObjectTools.execute(stack, "size")).intValue());
		stack.pop();
		stack.pop();
		assertEquals(0, ((Integer) ObjectTools.execute(stack, "size")).intValue());
	}

	public void testBuildElements() {
		Stack<String> stack = this.buildStack();
		String first = "first";
		String second = "second";
		String third = "third";
		stack.push(first);
		stack.push(second);
		stack.push(third);

		Object[] elements = new Object[] { third, second, first };
		assertTrue(Arrays.equals(elements, ((Object[]) ObjectTools.execute(stack, "buildElements"))));
	}

	public void testNodeCache_max() {
		Stack<String> stack = new LinkedStack<>(2);
		String first = "first";
		String second = "second";
		String third = "third";
		String fourth = "fourth";
		String fifth = "fifth";

		Object factory = ObjectTools.get(stack, "nodeFactory");

		this.verifyNodeCache(0, factory);
		stack.push(first);
		this.verifyNodeCache(0, factory);
		stack.push(second);
		stack.push(third);
		stack.push(fourth);
		stack.push(fifth);
		this.verifyNodeCache(0, factory);
		assertNull(ObjectTools.get(factory, "cacheHead"));

		stack.pop();
		this.verifyNodeCache(1, factory);
		stack.pop();
		this.verifyNodeCache(2, factory);
		stack.pop();
		this.verifyNodeCache(2, factory);
		stack.pop();
		this.verifyNodeCache(2, factory);
		stack.pop();
		this.verifyNodeCache(2, factory);
		stack.push(first);
		this.verifyNodeCache(1, factory);
		stack.push(second);
		this.verifyNodeCache(0, factory);
		stack.push(third);
		this.verifyNodeCache(0, factory);
	}

	public void testNodeCache_unlimited() {
		Stack<String> stack = new LinkedStack<>(-1);
		String first = "first";
		String second = "second";
		String third = "third";
		String fourth = "fourth";
		String fifth = "fifth";

		Object factory = ObjectTools.get(stack, "nodeFactory");

		this.verifyNodeCache(0, factory);
		stack.push(first);
		this.verifyNodeCache(0, factory);
		stack.push(second);
		stack.push(third);
		stack.push(fourth);
		stack.push(fifth);
		this.verifyNodeCache(0, factory);
		assertNull(ObjectTools.get(factory, "cacheHead"));

		stack.pop();
		this.verifyNodeCache(1, factory);
		stack.pop();
		this.verifyNodeCache(2, factory);
		stack.pop();
		this.verifyNodeCache(3, factory);
		stack.pop();
		this.verifyNodeCache(4, factory);
		stack.pop();
		this.verifyNodeCache(5, factory);
		stack.push(first);
		this.verifyNodeCache(4, factory);
		stack.push(second);
		this.verifyNodeCache(3, factory);
		stack.push(third);
		this.verifyNodeCache(2, factory);
		stack.push(fourth);
		this.verifyNodeCache(1, factory);
		stack.push(fifth);
		this.verifyNodeCache(0, factory);
	}

	public void verifyNodeCache(int size, Object factory) {
		assertEquals(size, ((Integer) ObjectTools.get(factory, "cacheSize")).intValue());
		int nodeCount = 0;
		for (Object node = ObjectTools.get(factory, "cacheHead"); node != null; node = ObjectTools.get(node, "next")) {
			nodeCount++;
		}
		assertEquals(size, nodeCount);
	}

	public void testNodeToString() {
		Stack<String> queue = StackTools.linkedStack();
		String first = "first";
		String second = "second";
		String third = "third";
		queue.push(first);
		queue.push(second);
		queue.push(third);

		Object head = ObjectTools.get(queue, "head");
		assertTrue(head.toString().startsWith("LinkedStack.Node"));
		assertTrue(head.toString().endsWith("(third)"));
	}

	public void testToString_empty() throws Exception {
		Stack<String> stack = this.buildStack();
		assertEquals("[]", stack.toString());
	}

	public void testSimpleNodeFactoryToString() {
		Stack<String> queue = StackTools.linkedStack();
		Object factory = ObjectTools.get(queue, "nodeFactory");
		assertEquals("LinkedStack.SimpleNodeFactory", factory.toString());
	}

	public void testCachingNodeFactoryToString() {
		Stack<String> queue = StackTools.linkedStack(20);
		Object factory = ObjectTools.get(queue, "nodeFactory");
		assertTrue(factory.toString().startsWith("LinkedStack.CachingNodeFactory"));
		assertTrue(factory.toString().endsWith("(0)"));
	}

	public void testClone_caching() throws Exception {
		LinkedStack<String> original = StackTools.linkedStack(20);
		original.push("first");

		LinkedStack<String> clone = original.clone();
		assertEquals(original.peek(), clone.peek());
		assertEquals(original.pop(), clone.pop());
		assertNotSame(original, clone);
		assertTrue(original.isEmpty());
		assertEquals(original.isEmpty(), clone.isEmpty());

		original.push("second");
		assertFalse(original.isEmpty());
		// clone should still be empty
		assertTrue(clone.isEmpty());

		Object factory = ObjectTools.get(original, "nodeFactory");
		assertTrue(factory.toString().startsWith("LinkedStack.CachingNodeFactory"));
	}
}

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
import org.eclipse.jpt.common.utility.stack.Stack;

@SuppressWarnings("nls")
public class CachingLinkedStackTests
	extends StackTests
{
	public CachingLinkedStackTests(String name) {
		super(name);
	}

	@Override
	Stack<String> buildStack() {
		return new CachingLinkedStack<>();
	}

	public void testConstructorInt_IAE() throws Exception {
		boolean exCaught = false;
		try {
			Stack<String> stack = new CachingLinkedStack<>(-3);
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
		Stack<String> stack = new CachingLinkedStack<>(2);
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
		Stack<String> stack = new CachingLinkedStack<>(-1);
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
		Stack<String> queue = new CachingLinkedStack<>();
		String first = "first";
		String second = "second";
		String third = "third";
		queue.push(first);
		queue.push(second);
		queue.push(third);

		Object head = ObjectTools.get(queue, "head");
		assertTrue(head.toString().startsWith("CachingLinkedStack.Node"));
		assertTrue(head.toString().endsWith("(third)"));
	}

	public void testToString_empty() throws Exception {
		Stack<String> stack = this.buildStack();
		assertEquals("[]", stack.toString());
	}

	public void testSimpleNodeFactoryToString() {
		Stack<String> queue = new CachingLinkedStack<>();
		Object factory = ObjectTools.get(queue, "nodeFactory");
		assertEquals("CachingLinkedStack.SimpleNodeFactory", factory.toString());
	}

	public void testCachingNodeFactoryToString() {
		Stack<String> queue = new CachingLinkedStack<>(20);
		Object factory = ObjectTools.get(queue, "nodeFactory");
		assertTrue(factory.toString().startsWith("CachingLinkedStack.CachingNodeFactory"));
		assertTrue(factory.toString().endsWith("(0)"));
	}

	public void testClone_caching() throws Exception {
		CachingLinkedStack<String> original = new CachingLinkedStack<>(20);
		original.push("first");

		CachingLinkedStack<String> clone = original.clone();
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
		assertTrue(factory.toString().startsWith("CachingLinkedStack.CachingNodeFactory"));
	}

//	public void testPerformance() throws InterruptedException {
//		Stack<Integer> stack;
//		String msg;
//
//		stack = new CachingLinkedStack<>(0);
//		msg = "no cache";
//		this.verifyPerformance(stack, msg);
//
//		stack = new CachingLinkedStack<>(10);
//		msg = "cache 10";
//		this.verifyPerformance(stack, msg);
//
//		stack = new CachingLinkedStack<>(100);
//		msg = "cache 100";
//		this.verifyPerformance(stack, msg);
//
//		stack = new CachingLinkedStack<>(-1);
//		msg = "unlimited cache";
//		this.verifyPerformance(stack, msg);
//	}
//
//	public void verifyPerformance(Stack<Integer> stack, String msg) throws InterruptedException {
//		for (int i = 0; i < 3; i++) {
//			long begin = System.currentTimeMillis();
//			this.verifyPerformance(stack);
//			long end = System.currentTimeMillis();
//			double elapsed = 1.0 * (end - begin) / 1000;
//			System.out.println(msg + ": " + elapsed);
//			Thread.sleep(1000);
//		}
//	}
//
//	public void verifyPerformance(Stack<Integer> stack) {
//		for (int i = 0; i < 500; i++) {
//			stack.push(Integer.valueOf(i));
//		}
//		for (int i = 0; i < 500000000; i++) {
//			if (RANDOM.nextBoolean()) {
//				stack.push(Integer.valueOf(i));
//			} else {
//				if ( ! stack.isEmpty()) {
//					stack.pop();
//				}
//			}
//		}
//	}
//
//	private static final java.util.Random RANDOM = new java.util.Random();
}

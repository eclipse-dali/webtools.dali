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

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.jpt.common.utility.internal.ClassTools;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.queue.ArrayQueue;
import org.eclipse.jpt.common.utility.internal.queue.QueueTools;
import org.eclipse.jpt.common.utility.internal.stack.ArrayStack;
import org.eclipse.jpt.common.utility.internal.stack.LinkedStack;
import org.eclipse.jpt.common.utility.internal.stack.StackTools;
import org.eclipse.jpt.common.utility.internal.stack.SynchronizedStack;
import org.eclipse.jpt.common.utility.stack.Stack;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import junit.framework.TestCase;

@SuppressWarnings("nls")
public class StackToolsTests
	extends TestCase
{
	public StackToolsTests(String name) {
		super(name);
	}

	// ********** push all **********

	public void testPushAllIterable() {
		ArrayList<String> iterable = new ArrayList<String>();
		iterable.add("one");
		iterable.add("two");
		iterable.add("three");
		Stack<String> stack = StackTools.arrayStack();
		assertTrue(StackTools.pushAll(stack, iterable));
		assertEquals("three", stack.pop());
		assertEquals("two", stack.pop());
		assertEquals("one", stack.pop());
	}

	public void testPushAllIterable_empty() {
		ArrayList<String> iterable = new ArrayList<String>();
		Stack<String> stack = StackTools.arrayStack();
		assertFalse(StackTools.pushAll(stack, iterable));
		assertTrue(stack.isEmpty());
	}

	public void testPushAllIterator() {
		ArrayList<String> iterable = new ArrayList<String>();
		iterable.add("one");
		iterable.add("two");
		iterable.add("three");
		Stack<String> stack = StackTools.arrayStack();
		assertTrue(StackTools.pushAll(stack, iterable.iterator()));
		assertEquals("three", stack.pop());
		assertEquals("two", stack.pop());
		assertEquals("one", stack.pop());
	}

	public void testPushAllArray() {
		ArrayList<String> iterable = new ArrayList<String>();
		iterable.add("one");
		iterable.add("two");
		iterable.add("three");
		Stack<String> stack = StackTools.arrayStack();
		assertTrue(StackTools.pushAll(stack, iterable.toArray(StringTools.EMPTY_STRING_ARRAY)));
		assertEquals("three", stack.pop());
		assertEquals("two", stack.pop());
		assertEquals("one", stack.pop());
	}

	public void testPushAllArray_empty() {
		ArrayList<String> iterable = new ArrayList<String>();
		Stack<String> stack = StackTools.arrayStack();
		assertFalse(StackTools.pushAll(stack, iterable.toArray(StringTools.EMPTY_STRING_ARRAY)));
		assertTrue(stack.isEmpty());
	}


	// ********** pop all **********

	public void testPopAll() {
		ArrayStack<String> stack = StackTools.arrayStack();
		stack.push("one");
		stack.push("two");
		stack.push("three");
		ArrayList<String> list = StackTools.popAll(stack);
		assertEquals("three", list.get(0));
		assertEquals("two", list.get(1));
		assertEquals("one", list.get(2));
	}

	public void testPopAllToCollection() {
		Stack<String> stack = StackTools.arrayStack();
		stack.push("one");
		stack.push("two");
		stack.push("three");
		ArrayList<String> list = new ArrayList<String>();
		assertTrue(StackTools.popAllTo(stack, list));
		assertEquals("three", list.get(0));
		assertEquals("two", list.get(1));
		assertEquals("one", list.get(2));
	}

	public void testPopAllToCollection_empty() {
		ArrayStack<String> stack = StackTools.arrayStack();
		ArrayList<String> list = new ArrayList<String>();
		assertFalse(StackTools.popAllTo(stack, list));
		assertTrue(list.isEmpty());
	}

	public void testPopAllToListIndex() {
		ArrayList<String> list = new ArrayList<String>();
		list.add("one");
		list.add("two");
		list.add("three");
		Stack<String> stack = StackTools.arrayStack();
		stack.push("aaa");
		stack.push("bbb");
		stack.push("ccc");
		assertTrue(StackTools.popAllTo(stack, list, 2));
		assertEquals("one", list.get(0));
		assertEquals("two", list.get(1));
		assertEquals("ccc", list.get(2));
		assertEquals("bbb", list.get(3));
		assertEquals("aaa", list.get(4));
		assertEquals("three", list.get(5));
	}

	public void testPopAllToListIndex_end() {
		ArrayList<String> list = new ArrayList<String>();
		list.add("one");
		list.add("two");
		list.add("three");
		Stack<String> stack = StackTools.arrayStack();
		stack.push("aaa");
		stack.push("bbb");
		stack.push("ccc");
		assertTrue(StackTools.popAllTo(stack, list, 3));
		assertEquals("one", list.get(0));
		assertEquals("two", list.get(1));
		assertEquals("three", list.get(2));
		assertEquals("ccc", list.get(3));
		assertEquals("bbb", list.get(4));
		assertEquals("aaa", list.get(5));
	}

	public void testPopAllToListIndex_empty() {
		ArrayList<String> list = new ArrayList<String>();
		list.add("one");
		list.add("two");
		list.add("three");
		Stack<String> stack = StackTools.arrayStack();
		assertFalse(StackTools.popAllTo(stack, list, 3));
		assertEquals("one", list.get(0));
		assertEquals("two", list.get(1));
		assertEquals("three", list.get(2));
	}

	public void testPopAllToStack() {
		ArrayStack<String> stack = StackTools.arrayStack();
		stack.push("one");
		stack.push("two");
		stack.push("three");
		ArrayStack<String> stack2 = StackTools.arrayStack();
		assertTrue(StackTools.popAllTo(stack, stack2));
		assertEquals("one", stack2.pop());
		assertEquals("two", stack2.pop());
		assertEquals("three", stack2.pop());
	}

	public void testPopAllToStack_empty() {
		ArrayStack<String> stack = StackTools.arrayStack();
		ArrayStack<String> stack2 = StackTools.arrayStack();
		assertFalse(StackTools.popAllTo(stack, stack2));
		assertTrue(stack2.isEmpty());
	}

	public void testPopAllToQueue() {
		ArrayStack<String> stack = StackTools.arrayStack();
		stack.push("one");
		stack.push("two");
		stack.push("three");
		ArrayQueue<String> queue = QueueTools.arrayQueue();
		assertTrue(StackTools.popAllTo(stack, queue));
		assertEquals("three", queue.dequeue());
		assertEquals("two", queue.dequeue());
		assertEquals("one", queue.dequeue());
	}

	public void testPopAllToQueue_empty() {
		ArrayStack<String> stack = StackTools.arrayStack();
		ArrayQueue<String> queue = QueueTools.arrayQueue();
		assertFalse(StackTools.popAllTo(stack, queue));
		assertTrue(queue.isEmpty());
	}

	public void testPopAllToMapTransformer() {
		ArrayStack<String> stack = StackTools.arrayStack();
		stack.push("zero");
		stack.push("one");
		stack.push("two");
		Map<String, String>map = new HashMap<String, String>();
		assertTrue(StackTools.popAllTo(stack, map, FIRST_LETTER_TRANSFORMER));
		assertEquals("one", map.get("o"));
		assertEquals("two", map.get("t"));
		assertEquals("zero", map.get("z"));
	}

	public void testPopAllToMapTransformer_empty() {
		ArrayStack<String> stack = StackTools.arrayStack();
		Map<String, String>map = new HashMap<String, String>();
		assertFalse(StackTools.popAllTo(stack, map, FIRST_LETTER_TRANSFORMER));
		assertTrue(map.isEmpty());
	}

	public void testPopAllToMapTransformerTransformer() {
		ArrayStack<String> stack = StackTools.arrayStack();
		stack.push("zero");
		stack.push("one");
		stack.push("two");
		Map<String, String>map = new HashMap<String, String>();
		assertTrue(StackTools.popAllTo(stack, map, FIRST_LETTER_TRANSFORMER, EMPHASIZER));
		assertEquals("*one*", map.get("o"));
		assertEquals("*two*", map.get("t"));
		assertEquals("*zero*", map.get("z"));
	}

	public void testPopAllToMapTransformerTransformer_empty() {
		ArrayStack<String> stack = StackTools.arrayStack();
		Map<String, String>map = new HashMap<String, String>();
		assertFalse(StackTools.popAllTo(stack, map, FIRST_LETTER_TRANSFORMER, EMPHASIZER));
		assertTrue(map.isEmpty());
	}

	public static final Transformer<String, String> FIRST_LETTER_TRANSFORMER = new FirstLetterTransformer();

	/* CU private */ static class FirstLetterTransformer
		implements Transformer<String, String>
	{
		public String transform(String string) {
			return string.substring(0, 1);
		}
		@Override
		public String toString() {
			return this.getClass().getSimpleName();
		}
	}

	public static final Transformer<String, String> EMPHASIZER = new StringTools.CharDelimiter('*');


	// ********** array stack **********

	public void testArrayStack() {
		ArrayStack<String> d = StackTools.arrayStack();
		assertTrue(d.isEmpty());
	}

	public void testArrayStackInt() {
		ArrayStack<String> d = StackTools.arrayStack(20);
		assertTrue(d.isEmpty());
		assertEquals(20, ((Object[]) ObjectTools.get(d, "elements")).length);
	}

	public void testArrayStackIterable() {
		ArrayList<String> iterable = new ArrayList<String>();
		iterable.add("one");
		iterable.add("two");
		iterable.add("three");
		Stack<String> stack = StackTools.arrayStack(iterable);
		assertEquals("three", stack.pop());
		assertEquals("two", stack.pop());
		assertEquals("one", stack.pop());
	}

	public void testArrayStackIterableInt() {
		ArrayList<String> iterable = new ArrayList<String>();
		iterable.add("one");
		iterable.add("two");
		iterable.add("three");
		Stack<String> stack = StackTools.arrayStack(iterable, 5);
		assertEquals("three", stack.pop());
		assertEquals("two", stack.pop());
		assertEquals("one", stack.pop());
	}

	public void testArrayStackIterator() {
		ArrayList<String> iterable = new ArrayList<String>();
		iterable.add("one");
		iterable.add("two");
		iterable.add("three");
		Stack<String> stack = StackTools.arrayStack(iterable.iterator());
		assertEquals("three", stack.pop());
		assertEquals("two", stack.pop());
		assertEquals("one", stack.pop());
	}

	public void testArrayStackIteratorInt() {
		ArrayList<String> iterable = new ArrayList<String>();
		iterable.add("one");
		iterable.add("two");
		iterable.add("three");
		Stack<String> stack = StackTools.arrayStack(iterable.iterator(), 5);
		assertEquals("three", stack.pop());
		assertEquals("two", stack.pop());
		assertEquals("one", stack.pop());
	}

	public void testArrayStackArray() {
		ArrayList<String> iterable = new ArrayList<String>();
		iterable.add("one");
		iterable.add("two");
		iterable.add("three");
		Stack<String> stack = StackTools.arrayStack(iterable.toArray(StringTools.EMPTY_STRING_ARRAY));
		assertEquals("three", stack.pop());
		assertEquals("two", stack.pop());
		assertEquals("one", stack.pop());
	}

	// ********** linked stack **********

	public void testLinkedStack() {
		LinkedStack<String> d = StackTools.linkedStack();
		assertTrue(d.isEmpty());
	}

	public void testLinkedStackInt() {
		LinkedStack<String> d = StackTools.linkedStack(20);
		assertTrue(d.isEmpty());
	}

	public void testLinkedStackIterable() {
		ArrayList<String> iterable = new ArrayList<String>();
		iterable.add("one");
		iterable.add("two");
		iterable.add("three");
		Stack<String> stack = StackTools.linkedStack(iterable);
		assertEquals("three", stack.pop());
		assertEquals("two", stack.pop());
		assertEquals("one", stack.pop());
	}

	public void testLinkedStackIterableInt() {
		ArrayList<String> iterable = new ArrayList<String>();
		iterable.add("one");
		iterable.add("two");
		iterable.add("three");
		Stack<String> stack = StackTools.linkedStack(iterable, 5);
		assertEquals("three", stack.pop());
		assertEquals("two", stack.pop());
		assertEquals("one", stack.pop());
	}

	public void testLinkedStackIterator() {
		ArrayList<String> iterable = new ArrayList<String>();
		iterable.add("one");
		iterable.add("two");
		iterable.add("three");
		Stack<String> stack = StackTools.linkedStack(iterable.iterator());
		assertEquals("three", stack.pop());
		assertEquals("two", stack.pop());
		assertEquals("one", stack.pop());
	}

	public void testLinkedStackIteratorInt() {
		ArrayList<String> iterable = new ArrayList<String>();
		iterable.add("one");
		iterable.add("two");
		iterable.add("three");
		Stack<String> stack = StackTools.linkedStack(iterable.iterator(), 5);
		assertEquals("three", stack.pop());
		assertEquals("two", stack.pop());
		assertEquals("one", stack.pop());
	}

	public void testLinkedStackArray() {
		ArrayList<String> iterable = new ArrayList<String>();
		iterable.add("one");
		iterable.add("two");
		iterable.add("three");
		Stack<String> stack = StackTools.linkedStack(iterable.toArray(StringTools.EMPTY_STRING_ARRAY));
		assertEquals("three", stack.pop());
		assertEquals("two", stack.pop());
		assertEquals("one", stack.pop());
	}

	public void testLinkedStackArrayInt() {
		ArrayList<String> iterable = new ArrayList<String>();
		iterable.add("one");
		iterable.add("two");
		iterable.add("three");
		Stack<String> stack = StackTools.linkedStack(iterable.toArray(StringTools.EMPTY_STRING_ARRAY), 2);
		assertEquals("three", stack.pop());
		assertEquals("two", stack.pop());
		assertEquals("one", stack.pop());
	}

	// ********** fixed-capacity array stack **********

	public void testFixedCapacityArrayStackCollection() {
		ArrayList<String> iterable = new ArrayList<String>();
		iterable.add("one");
		iterable.add("two");
		iterable.add("three");
		Stack<String> stack = StackTools.fixedCapacityArrayStack(iterable);
		assertEquals("three", stack.pop());
		assertEquals("two", stack.pop());
		assertEquals("one", stack.pop());
	}

	// ********** misc **********

	public void testSynchronizedStackObject() {
		Object lock = new Object();
		SynchronizedStack<String> stack = StackTools.synchronizedStack(lock);
		String first = "first";
		String second = "second";

		stack.push(first);
		stack.push(second);
		assertEquals(second, stack.pop());
		assertEquals(first, stack.pop());
		assertEquals(lock, stack.getMutex());
	}

	public void testSynchronizedStackStackObject() {
		Object lock = new Object();
		Stack<String> innerStack = StackTools.arrayStack();
		String first = "first";
		String second = "second";
		innerStack.push(first);
		innerStack.push(second);

		SynchronizedStack<String> stack = StackTools.synchronizedStack(innerStack, lock);
		assertEquals(second, stack.pop());
		assertEquals(first, stack.pop());
		assertEquals(lock, stack.getMutex());
	}

	public void testConstructor() {
		boolean exCaught = false;
		try {
			Object at = ClassTools.newInstance(StackTools.class);
			fail("bogus: " + at);
		} catch (RuntimeException ex) {
			if (ex.getCause() instanceof InvocationTargetException) {
				if (ex.getCause().getCause() instanceof UnsupportedOperationException) {
					exCaught = true;
				}
			}
		}
		assertTrue(exCaught);
	}
}

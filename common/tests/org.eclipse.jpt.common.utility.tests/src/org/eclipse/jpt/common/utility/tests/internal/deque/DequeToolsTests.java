/*******************************************************************************
 * Copyright (c) 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.deque;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.jpt.common.utility.deque.Deque;
import org.eclipse.jpt.common.utility.internal.ClassTools;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.comparator.ComparatorTools;
import org.eclipse.jpt.common.utility.internal.deque.ArrayDeque;
import org.eclipse.jpt.common.utility.internal.deque.DequeTools;
import org.eclipse.jpt.common.utility.internal.deque.LinkedDeque;
import org.eclipse.jpt.common.utility.internal.deque.PriorityDeque;
import org.eclipse.jpt.common.utility.internal.deque.SynchronizedDeque;
import org.eclipse.jpt.common.utility.internal.queue.ArrayQueue;
import org.eclipse.jpt.common.utility.internal.queue.QueueTools;
import org.eclipse.jpt.common.utility.internal.stack.ArrayStack;
import org.eclipse.jpt.common.utility.internal.stack.StackTools;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import junit.framework.TestCase;

@SuppressWarnings("nls")
public class DequeToolsTests
	extends TestCase
{
	public DequeToolsTests(String name) {
		super(name);
	}

	// ********** enqueue all **********

	public void testEnqueueTailAllIterable() {
		ArrayList<String> iterable = new ArrayList<String>();
		iterable.add("one");
		iterable.add("two");
		iterable.add("three");
		Deque<String> deque = DequeTools.arrayDeque();
		assertTrue(DequeTools.enqueueTailAll(deque, iterable));
		assertEquals("one", deque.dequeueHead());
		assertEquals("two", deque.dequeueHead());
		assertEquals("three", deque.dequeueHead());
	}

	public void testEnqueueTailAllIterable_empty() {
		ArrayList<String> iterable = new ArrayList<String>();
		Deque<String> deque = DequeTools.arrayDeque();
		assertFalse(DequeTools.enqueueTailAll(deque, iterable));
		assertTrue(deque.isEmpty());
	}

	public void testEnqueueHeadAllIterable() {
		ArrayList<String> iterable = new ArrayList<String>();
		iterable.add("one");
		iterable.add("two");
		iterable.add("three");
		Deque<String> deque = DequeTools.arrayDeque();
		assertTrue(DequeTools.enqueueHeadAll(deque, iterable));
		assertEquals("one", deque.dequeueTail());
		assertEquals("two", deque.dequeueTail());
		assertEquals("three", deque.dequeueTail());
	}

	public void testEnqueueHeadAllIterable_empty() {
		ArrayList<String> iterable = new ArrayList<String>();
		Deque<String> deque = DequeTools.arrayDeque();
		assertFalse(DequeTools.enqueueHeadAll(deque, iterable));
		assertTrue(deque.isEmpty());
	}

	public void testEnqueueTailAllIterator() {
		ArrayList<String> iterable = new ArrayList<String>();
		iterable.add("one");
		iterable.add("two");
		iterable.add("three");
		Deque<String> deque = DequeTools.arrayDeque();
		assertTrue(DequeTools.enqueueTailAll(deque, iterable.iterator()));
		assertEquals("one", deque.dequeueHead());
		assertEquals("two", deque.dequeueHead());
		assertEquals("three", deque.dequeueHead());
	}

	public void testEnqueueHeadAllIterator() {
		ArrayList<String> iterable = new ArrayList<String>();
		iterable.add("one");
		iterable.add("two");
		iterable.add("three");
		Deque<String> deque = DequeTools.arrayDeque();
		assertTrue(DequeTools.enqueueHeadAll(deque, iterable.iterator()));
		assertEquals("one", deque.dequeueTail());
		assertEquals("two", deque.dequeueTail());
		assertEquals("three", deque.dequeueTail());
	}

	public void testEnqueueTailAllArray() {
		ArrayList<String> iterable = new ArrayList<String>();
		iterable.add("one");
		iterable.add("two");
		iterable.add("three");
		Deque<String> deque = DequeTools.arrayDeque();
		assertTrue(DequeTools.enqueueTailAll(deque, iterable.toArray(StringTools.EMPTY_STRING_ARRAY)));
		assertEquals("one", deque.dequeueHead());
		assertEquals("two", deque.dequeueHead());
		assertEquals("three", deque.dequeueHead());
	}

	public void testEnqueueTailAllArray_empty() {
		ArrayList<String> iterable = new ArrayList<String>();
		Deque<String> deque = DequeTools.arrayDeque();
		assertFalse(DequeTools.enqueueTailAll(deque, iterable.toArray(StringTools.EMPTY_STRING_ARRAY)));
		assertTrue(deque.isEmpty());
	}

	public void testEnqueueHeadAllArray() {
		ArrayList<String> iterable = new ArrayList<String>();
		iterable.add("one");
		iterable.add("two");
		iterable.add("three");
		Deque<String> deque = DequeTools.arrayDeque();
		assertTrue(DequeTools.enqueueHeadAll(deque, iterable.toArray(StringTools.EMPTY_STRING_ARRAY)));
		assertEquals("one", deque.dequeueTail());
		assertEquals("two", deque.dequeueTail());
		assertEquals("three", deque.dequeueTail());
	}

	public void testEnqueueHeadAllArray_empty() {
		ArrayList<String> iterable = new ArrayList<String>();
		Deque<String> deque = DequeTools.arrayDeque();
		assertFalse(DequeTools.enqueueHeadAll(deque, iterable.toArray(StringTools.EMPTY_STRING_ARRAY)));
		assertTrue(deque.isEmpty());
	}


	// ********** drain **********

	public void testDrainHead() {
		ArrayDeque<String> d = DequeTools.arrayDeque();
		d.enqueueTail("one");
		d.enqueueTail("two");
		d.enqueueTail("three");
		ArrayList<String> list = DequeTools.drainHead(d);
		assertEquals("one", list.get(0));
		assertEquals("two", list.get(1));
		assertEquals("three", list.get(2));
	}

	public void testDrainTail() {
		ArrayDeque<String> d = DequeTools.arrayDeque();
		d.enqueueTail("one");
		d.enqueueTail("two");
		d.enqueueTail("three");
		ArrayList<String> list = DequeTools.drainTail(d);
		assertEquals("one", list.get(2));
		assertEquals("two", list.get(1));
		assertEquals("three", list.get(0));
	}

	public void testDrainHeadToCollection() {
		ArrayDeque<String> d = DequeTools.arrayDeque();
		d.enqueueTail("one");
		d.enqueueTail("two");
		d.enqueueTail("three");
		ArrayList<String> list = new ArrayList<String>();
		assertTrue(DequeTools.drainHeadTo(d, list));
		assertEquals("one", list.get(0));
		assertEquals("two", list.get(1));
		assertEquals("three", list.get(2));
	}

	public void testDrainHeadToCollection_empty() {
		ArrayDeque<String> d = DequeTools.arrayDeque();
		ArrayList<String> list = new ArrayList<String>();
		assertFalse(DequeTools.drainHeadTo(d, list));
		assertTrue(list.isEmpty());
	}

	public void testDrainTailToCollection() {
		ArrayDeque<String> d = DequeTools.arrayDeque();
		d.enqueueTail("one");
		d.enqueueTail("two");
		d.enqueueTail("three");
		ArrayList<String> list = new ArrayList<String>();
		assertTrue(DequeTools.drainTailTo(d, list));
		assertEquals("one", list.get(2));
		assertEquals("two", list.get(1));
		assertEquals("three", list.get(0));
	}

	public void testDrainTailToCollection_empty() {
		ArrayDeque<String> d = DequeTools.arrayDeque();
		ArrayList<String> list = new ArrayList<String>();
		assertFalse(DequeTools.drainTailTo(d, list));
		assertTrue(list.isEmpty());
	}

	public void testDrainHeadToListIndex() {
		ArrayList<String> list = new ArrayList<String>();
		list.add("one");
		list.add("two");
		list.add("three");
		Deque<String> deque = DequeTools.arrayDeque();
		deque.enqueueTail("aaa");
		deque.enqueueTail("bbb");
		deque.enqueueTail("ccc");
		assertTrue(DequeTools.drainHeadTo(deque, list, 2));
		assertEquals("one", list.get(0));
		assertEquals("two", list.get(1));
		assertEquals("aaa", list.get(2));
		assertEquals("bbb", list.get(3));
		assertEquals("ccc", list.get(4));
		assertEquals("three", list.get(5));
	}

	public void testDrainHeadToListIndex_end() {
		ArrayList<String> list = new ArrayList<String>();
		list.add("one");
		list.add("two");
		list.add("three");
		Deque<String> deque = DequeTools.arrayDeque();
		deque.enqueueTail("aaa");
		deque.enqueueTail("bbb");
		deque.enqueueTail("ccc");
		assertTrue(DequeTools.drainHeadTo(deque, list, 3));
		assertEquals("one", list.get(0));
		assertEquals("two", list.get(1));
		assertEquals("three", list.get(2));
		assertEquals("aaa", list.get(3));
		assertEquals("bbb", list.get(4));
		assertEquals("ccc", list.get(5));
	}

	public void testDrainHeadToListIndex_empty() {
		ArrayList<String> list = new ArrayList<String>();
		list.add("one");
		list.add("two");
		list.add("three");
		Deque<String> deque = DequeTools.arrayDeque();
		assertFalse(DequeTools.drainHeadTo(deque, list, 3));
		assertEquals("one", list.get(0));
		assertEquals("two", list.get(1));
		assertEquals("three", list.get(2));
	}

	public void testDrainTailToListIndex() {
		ArrayList<String> list = new ArrayList<String>();
		list.add("one");
		list.add("two");
		list.add("three");
		Deque<String> deque = DequeTools.arrayDeque();
		deque.enqueueTail("aaa");
		deque.enqueueTail("bbb");
		deque.enqueueTail("ccc");
		assertTrue(DequeTools.drainTailTo(deque, list, 2));
		assertEquals("one", list.get(0));
		assertEquals("two", list.get(1));
		assertEquals("ccc", list.get(2));
		assertEquals("bbb", list.get(3));
		assertEquals("aaa", list.get(4));
		assertEquals("three", list.get(5));
	}

	public void testDrainTailToListIndex_end() {
		ArrayList<String> list = new ArrayList<String>();
		list.add("one");
		list.add("two");
		list.add("three");
		Deque<String> deque = DequeTools.arrayDeque();
		deque.enqueueTail("aaa");
		deque.enqueueTail("bbb");
		deque.enqueueTail("ccc");
		assertTrue(DequeTools.drainTailTo(deque, list, 3));
		assertEquals("one", list.get(0));
		assertEquals("two", list.get(1));
		assertEquals("three", list.get(2));
		assertEquals("ccc", list.get(3));
		assertEquals("bbb", list.get(4));
		assertEquals("aaa", list.get(5));
	}

	public void testDrainTailToListIndex_empty() {
		ArrayList<String> list = new ArrayList<String>();
		list.add("one");
		list.add("two");
		list.add("three");
		Deque<String> deque = DequeTools.arrayDeque();
		assertFalse(DequeTools.drainTailTo(deque, list, 3));
		assertEquals("one", list.get(0));
		assertEquals("two", list.get(1));
		assertEquals("three", list.get(2));
	}

	public void testDrainHeadToStack() {
		ArrayDeque<String> d = DequeTools.arrayDeque();
		d.enqueueTail("one");
		d.enqueueTail("two");
		d.enqueueTail("three");
		ArrayStack<String> stack = StackTools.arrayStack();
		assertTrue(DequeTools.drainHeadTo(d, stack));
		assertEquals("three", stack.pop());
		assertEquals("two", stack.pop());
		assertEquals("one", stack.pop());
	}

	public void testDrainHeadToStack_empty() {
		ArrayDeque<String> d = DequeTools.arrayDeque();
		ArrayStack<String> stack = StackTools.arrayStack();
		assertFalse(DequeTools.drainHeadTo(d, stack));
		assertTrue(stack.isEmpty());
	}

	public void testDrainTailToStack() {
		ArrayDeque<String> d = DequeTools.arrayDeque();
		d.enqueueTail("one");
		d.enqueueTail("two");
		d.enqueueTail("three");
		ArrayStack<String> stack = StackTools.arrayStack();
		assertTrue(DequeTools.drainTailTo(d, stack));
		assertEquals("one", stack.pop());
		assertEquals("two", stack.pop());
		assertEquals("three", stack.pop());
	}

	public void testDrainTailToStack_empty() {
		ArrayDeque<String> d = DequeTools.arrayDeque();
		ArrayStack<String> stack = StackTools.arrayStack();
		assertFalse(DequeTools.drainTailTo(d, stack));
		assertTrue(stack.isEmpty());
	}

	public void testDrainHeadToQueue() {
		ArrayDeque<String> d = DequeTools.arrayDeque();
		d.enqueueTail("one");
		d.enqueueTail("two");
		d.enqueueTail("three");
		ArrayQueue<String> queue = QueueTools.arrayQueue();
		assertTrue(DequeTools.drainHeadTo(d, queue));
		assertEquals("one", queue.dequeue());
		assertEquals("two", queue.dequeue());
		assertEquals("three", queue.dequeue());
	}

	public void testDrainHeadToQueue_empty() {
		ArrayDeque<String> d = DequeTools.arrayDeque();
		ArrayQueue<String> queue = QueueTools.arrayQueue();
		assertFalse(DequeTools.drainHeadTo(d, queue));
		assertTrue(queue.isEmpty());
	}

	public void testDrainTailToQueue() {
		ArrayDeque<String> d = DequeTools.arrayDeque();
		d.enqueueTail("one");
		d.enqueueTail("two");
		d.enqueueTail("three");
		ArrayQueue<String> queue = QueueTools.arrayQueue();
		assertTrue(DequeTools.drainTailTo(d, queue));
		assertEquals("three", queue.dequeue());
		assertEquals("two", queue.dequeue());
		assertEquals("one", queue.dequeue());
	}

	public void testDrainTailToQueue_empty() {
		ArrayDeque<String> d = DequeTools.arrayDeque();
		ArrayQueue<String> queue = QueueTools.arrayQueue();
		assertFalse(DequeTools.drainTailTo(d, queue));
		assertTrue(queue.isEmpty());
	}

	public void testDrainHeadToDeque() {
		ArrayDeque<String> d = DequeTools.arrayDeque();
		d.enqueueTail("one");
		d.enqueueTail("two");
		d.enqueueTail("three");
		ArrayDeque<String> d2 = DequeTools.arrayDeque();
		assertTrue(DequeTools.drainHeadTo(d, d2));
		assertEquals("one", d2.dequeueHead());
		assertEquals("two", d2.dequeueHead());
		assertEquals("three", d2.dequeueHead());
	}

	public void testDrainHeadToDeque_empty() {
		ArrayDeque<String> d = DequeTools.arrayDeque();
		ArrayDeque<String> d2 = DequeTools.arrayDeque();
		assertFalse(DequeTools.drainHeadTo(d, d2));
		assertTrue(d2.isEmpty());
	}

	public void testDrainTailToDeque() {
		ArrayDeque<String> d = DequeTools.arrayDeque();
		d.enqueueTail("one");
		d.enqueueTail("two");
		d.enqueueTail("three");
		ArrayDeque<String> d2 = DequeTools.arrayDeque();
		assertTrue(DequeTools.drainTailTo(d, d2));
		assertEquals("one", d2.dequeueHead());
		assertEquals("two", d2.dequeueHead());
		assertEquals("three", d2.dequeueHead());
	}

	public void testDrainTailToDeque_empty() {
		ArrayDeque<String> d = DequeTools.arrayDeque();
		ArrayDeque<String> d2 = DequeTools.arrayDeque();
		assertFalse(DequeTools.drainTailTo(d, d2));
		assertTrue(d2.isEmpty());
	}

	public void testDrainHeadToMapTransformer() {
		ArrayDeque<String> d = DequeTools.arrayDeque();
		d.enqueueTail("zero");
		d.enqueueTail("one");
		d.enqueueTail("two");
		Map<String, String>map = new HashMap<String, String>();
		assertTrue(DequeTools.drainHeadTo(d, map, FIRST_LETTER_TRANSFORMER));
		assertEquals("one", map.get("o"));
		assertEquals("two", map.get("t"));
		assertEquals("zero", map.get("z"));
	}

	public void testDrainHeadToMapTransformer_empty() {
		ArrayDeque<String> d = DequeTools.arrayDeque();
		Map<String, String>map = new HashMap<String, String>();
		assertFalse(DequeTools.drainHeadTo(d, map, FIRST_LETTER_TRANSFORMER));
		assertTrue(map.isEmpty());
	}

	public void testDrainTailToMapTransformer() {
		ArrayDeque<String> d = DequeTools.arrayDeque();
		d.enqueueTail("zero");
		d.enqueueTail("one");
		d.enqueueTail("two");
		Map<String, String>map = new HashMap<String, String>();
		assertTrue(DequeTools.drainTailTo(d, map, FIRST_LETTER_TRANSFORMER));
		assertEquals("one", map.get("o"));
		assertEquals("two", map.get("t"));
		assertEquals("zero", map.get("z"));
	}

	public void testDrainTailToMapTransformer_empty() {
		ArrayDeque<String> d = DequeTools.arrayDeque();
		Map<String, String>map = new HashMap<String, String>();
		assertFalse(DequeTools.drainTailTo(d, map, FIRST_LETTER_TRANSFORMER));
		assertTrue(map.isEmpty());
	}

	public void testDrainHeadToMapTransformerTransformer() {
		ArrayDeque<String> d = DequeTools.arrayDeque();
		d.enqueueTail("zero");
		d.enqueueTail("one");
		d.enqueueTail("two");
		Map<String, String>map = new HashMap<String, String>();
		assertTrue(DequeTools.drainHeadTo(d, map, FIRST_LETTER_TRANSFORMER, EMPHASIZER));
		assertEquals("*one*", map.get("o"));
		assertEquals("*two*", map.get("t"));
		assertEquals("*zero*", map.get("z"));
	}

	public void testDrainHeadToMapTransformerTransformer_empty() {
		ArrayDeque<String> d = DequeTools.arrayDeque();
		Map<String, String>map = new HashMap<String, String>();
		assertFalse(DequeTools.drainHeadTo(d, map, FIRST_LETTER_TRANSFORMER, EMPHASIZER));
		assertTrue(map.isEmpty());
	}

	public void testDrainTailToMapTransformerTransformer() {
		ArrayDeque<String> d = DequeTools.arrayDeque();
		d.enqueueTail("zero");
		d.enqueueTail("one");
		d.enqueueTail("two");
		Map<String, String>map = new HashMap<String, String>();
		assertTrue(DequeTools.drainTailTo(d, map, FIRST_LETTER_TRANSFORMER, EMPHASIZER));
		assertEquals("*one*", map.get("o"));
		assertEquals("*two*", map.get("t"));
		assertEquals("*zero*", map.get("z"));
	}

	public void testDrainTailToMapTransformerTransformer_empty() {
		ArrayDeque<String> d = DequeTools.arrayDeque();
		Map<String, String>map = new HashMap<String, String>();
		assertFalse(DequeTools.drainTailTo(d, map, FIRST_LETTER_TRANSFORMER, EMPHASIZER));
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


	// ********** array deque **********

	public void testArrayDeque() {
		ArrayDeque<String> d = DequeTools.arrayDeque();
		assertTrue(d.isEmpty());
	}

	public void testArrayDequeInt() {
		ArrayDeque<String> d = DequeTools.arrayDeque(20);
		assertTrue(d.isEmpty());
		assertEquals(20, ((Object[]) ObjectTools.get(d, "elements")).length);
	}

	public void testArrayDequeIterable() {
		ArrayList<String> iterable = new ArrayList<String>();
		iterable.add("one");
		iterable.add("two");
		iterable.add("three");
		Deque<String> deque = DequeTools.arrayDeque(iterable);
		assertEquals("one", deque.dequeueHead());
		assertEquals("two", deque.dequeueHead());
		assertEquals("three", deque.dequeueHead());
	}

	public void testArrayDequeIterableInt() {
		ArrayList<String> iterable = new ArrayList<String>();
		iterable.add("one");
		iterable.add("two");
		iterable.add("three");
		Deque<String> deque = DequeTools.arrayDeque(iterable, 5);
		assertEquals("one", deque.dequeueHead());
		assertEquals("two", deque.dequeueHead());
		assertEquals("three", deque.dequeueHead());
	}

	public void testReverseArrayDequeIterable() {
		ArrayList<String> iterable = new ArrayList<String>();
		iterable.add("one");
		iterable.add("two");
		iterable.add("three");
		Deque<String> deque = DequeTools.reverseArrayDeque(iterable);
		assertEquals("one", deque.dequeueTail());
		assertEquals("two", deque.dequeueTail());
		assertEquals("three", deque.dequeueTail());
	}

	public void testReverseArrayDequeIterableInt() {
		ArrayList<String> iterable = new ArrayList<String>();
		iterable.add("one");
		iterable.add("two");
		iterable.add("three");
		Deque<String> deque = DequeTools.reverseArrayDeque(iterable, 77);
		assertEquals("one", deque.dequeueTail());
		assertEquals("two", deque.dequeueTail());
		assertEquals("three", deque.dequeueTail());
	}

	public void testArrayDequeIterator() {
		ArrayList<String> iterable = new ArrayList<String>();
		iterable.add("one");
		iterable.add("two");
		iterable.add("three");
		Deque<String> deque = DequeTools.arrayDeque(iterable.iterator());
		assertEquals("one", deque.dequeueHead());
		assertEquals("two", deque.dequeueHead());
		assertEquals("three", deque.dequeueHead());
	}

	public void testArrayDequeIteratorInt() {
		ArrayList<String> iterable = new ArrayList<String>();
		iterable.add("one");
		iterable.add("two");
		iterable.add("three");
		Deque<String> deque = DequeTools.arrayDeque(iterable.iterator(), 5);
		assertEquals("one", deque.dequeueHead());
		assertEquals("two", deque.dequeueHead());
		assertEquals("three", deque.dequeueHead());
	}

	public void testReverseArrayDequeIterator() {
		ArrayList<String> iterable = new ArrayList<String>();
		iterable.add("one");
		iterable.add("two");
		iterable.add("three");
		Deque<String> deque = DequeTools.reverseArrayDeque(iterable.iterator());
		assertEquals("one", deque.dequeueTail());
		assertEquals("two", deque.dequeueTail());
		assertEquals("three", deque.dequeueTail());
	}

	public void testReverseArrayDequeIteratorInt() {
		ArrayList<String> iterable = new ArrayList<String>();
		iterable.add("one");
		iterable.add("two");
		iterable.add("three");
		Deque<String> deque = DequeTools.reverseArrayDeque(iterable.iterator(), 42);
		assertEquals("one", deque.dequeueTail());
		assertEquals("two", deque.dequeueTail());
		assertEquals("three", deque.dequeueTail());
	}

	public void testArrayDequeArray() {
		ArrayList<String> iterable = new ArrayList<String>();
		iterable.add("one");
		iterable.add("two");
		iterable.add("three");
		Deque<String> deque = DequeTools.arrayDeque(iterable.toArray(StringTools.EMPTY_STRING_ARRAY));
		assertEquals("one", deque.dequeueHead());
		assertEquals("two", deque.dequeueHead());
		assertEquals("three", deque.dequeueHead());
	}

	public void testReverseArrayDequeArray() {
		ArrayList<String> iterable = new ArrayList<String>();
		iterable.add("one");
		iterable.add("two");
		iterable.add("three");
		Deque<String> deque = DequeTools.reverseArrayDeque(iterable.toArray(StringTools.EMPTY_STRING_ARRAY));
		assertEquals("one", deque.dequeueTail());
		assertEquals("two", deque.dequeueTail());
		assertEquals("three", deque.dequeueTail());
	}

	// ********** linked deque **********

	public void testLinkedDeque() {
		LinkedDeque<String> d = DequeTools.linkedDeque();
		assertTrue(d.isEmpty());
	}

	public void testLinkedDequeInt() {
		LinkedDeque<String> d = DequeTools.linkedDeque(20);
		assertTrue(d.isEmpty());
	}

	public void testLinkedDequeIterable() {
		ArrayList<String> iterable = new ArrayList<String>();
		iterable.add("one");
		iterable.add("two");
		iterable.add("three");
		Deque<String> deque = DequeTools.linkedDeque(iterable);
		assertEquals("one", deque.dequeueHead());
		assertEquals("two", deque.dequeueHead());
		assertEquals("three", deque.dequeueHead());
	}

	public void testLinkedDequeIterableInt() {
		ArrayList<String> iterable = new ArrayList<String>();
		iterable.add("one");
		iterable.add("two");
		iterable.add("three");
		Deque<String> deque = DequeTools.linkedDeque(iterable, 5);
		assertEquals("one", deque.dequeueHead());
		assertEquals("two", deque.dequeueHead());
		assertEquals("three", deque.dequeueHead());
	}

	public void testReverseLinkedDequeIterable() {
		ArrayList<String> iterable = new ArrayList<String>();
		iterable.add("one");
		iterable.add("two");
		iterable.add("three");
		Deque<String> deque = DequeTools.reverseLinkedDeque(iterable);
		assertEquals("one", deque.dequeueTail());
		assertEquals("two", deque.dequeueTail());
		assertEquals("three", deque.dequeueTail());
	}

	public void testReverseLinkedDequeIterableInt() {
		ArrayList<String> iterable = new ArrayList<String>();
		iterable.add("one");
		iterable.add("two");
		iterable.add("three");
		Deque<String> deque = DequeTools.reverseLinkedDeque(iterable, 77);
		assertEquals("one", deque.dequeueTail());
		assertEquals("two", deque.dequeueTail());
		assertEquals("three", deque.dequeueTail());
	}

	public void testLinkedDequeIterator() {
		ArrayList<String> iterable = new ArrayList<String>();
		iterable.add("one");
		iterable.add("two");
		iterable.add("three");
		Deque<String> deque = DequeTools.linkedDeque(iterable.iterator());
		assertEquals("one", deque.dequeueHead());
		assertEquals("two", deque.dequeueHead());
		assertEquals("three", deque.dequeueHead());
	}

	public void testLinkedDequeIteratorInt() {
		ArrayList<String> iterable = new ArrayList<String>();
		iterable.add("one");
		iterable.add("two");
		iterable.add("three");
		Deque<String> deque = DequeTools.linkedDeque(iterable.iterator(), 5);
		assertEquals("one", deque.dequeueHead());
		assertEquals("two", deque.dequeueHead());
		assertEquals("three", deque.dequeueHead());
	}

	public void testReverseLinkedDequeIterator() {
		ArrayList<String> iterable = new ArrayList<String>();
		iterable.add("one");
		iterable.add("two");
		iterable.add("three");
		Deque<String> deque = DequeTools.reverseLinkedDeque(iterable.iterator());
		assertEquals("one", deque.dequeueTail());
		assertEquals("two", deque.dequeueTail());
		assertEquals("three", deque.dequeueTail());
	}

	public void testReverseLinkedDequeIteratorInt() {
		ArrayList<String> iterable = new ArrayList<String>();
		iterable.add("one");
		iterable.add("two");
		iterable.add("three");
		Deque<String> deque = DequeTools.reverseLinkedDeque(iterable.iterator(), 42);
		assertEquals("one", deque.dequeueTail());
		assertEquals("two", deque.dequeueTail());
		assertEquals("three", deque.dequeueTail());
	}

	public void testLinkedDequeArray() {
		ArrayList<String> iterable = new ArrayList<String>();
		iterable.add("one");
		iterable.add("two");
		iterable.add("three");
		Deque<String> deque = DequeTools.linkedDeque(iterable.toArray(StringTools.EMPTY_STRING_ARRAY));
		assertEquals("one", deque.dequeueHead());
		assertEquals("two", deque.dequeueHead());
		assertEquals("three", deque.dequeueHead());
	}

	public void testReverseLinkedDequeArray() {
		ArrayList<String> iterable = new ArrayList<String>();
		iterable.add("one");
		iterable.add("two");
		iterable.add("three");
		Deque<String> deque = DequeTools.reverseLinkedDeque(iterable.toArray(StringTools.EMPTY_STRING_ARRAY));
		assertEquals("one", deque.dequeueTail());
		assertEquals("two", deque.dequeueTail());
		assertEquals("three", deque.dequeueTail());
	}

	// ********** fixed-capacity array deque **********

	public void testFixedCapacityArrayDequeCollection() {
		ArrayList<String> iterable = new ArrayList<String>();
		iterable.add("one");
		iterable.add("two");
		iterable.add("three");
		Deque<String> deque = DequeTools.fixedCapacityArrayDeque(iterable);
		assertEquals("one", deque.dequeueHead());
		assertEquals("two", deque.dequeueHead());
		assertEquals("three", deque.dequeueHead());
	}

	public void testReverseFixedCapacityArrayDequeCollection() {
		ArrayList<String> iterable = new ArrayList<String>();
		iterable.add("one");
		iterable.add("two");
		iterable.add("three");
		Deque<String> deque = DequeTools.reverseFixedCapacityArrayDeque(iterable);
		assertEquals("three", deque.dequeueHead());
		assertEquals("two", deque.dequeueHead());
		assertEquals("one", deque.dequeueHead());
	}

	// ********** misc **********

	public void testPriorityDequeComparator() {
		PriorityDeque<String> deque = DequeTools.priorityDeque(ComparatorTools.<String>reverseComparator());
		String first = "first";
		String second = "second";

		deque.enqueue(first);
		deque.enqueueTail(second);
		assertEquals(second, deque.dequeueHead());
		assertEquals(first, deque.dequeueHead());
	}

	public void testSynchronizedDequeObject() {
		Object lock = new Object();
		SynchronizedDeque<String> deque = DequeTools.synchronizedDeque(lock);
		String first = "first";
		String second = "second";

		deque.enqueueTail(first);
		deque.enqueueTail(second);
		assertEquals(first, deque.dequeueHead());
		assertEquals(second, deque.dequeueHead());
		assertEquals(lock, deque.getMutex());
	}

	public void testSynchronizedDequeDequeObject() {
		Object lock = new Object();
		Deque<String> innerDeque = DequeTools.arrayDeque();
		String first = "first";
		String second = "second";
		innerDeque.enqueueTail(first);
		innerDeque.enqueueTail(second);

		SynchronizedDeque<String> deque = DequeTools.synchronizedDeque(innerDeque, lock);
		assertEquals(first, deque.dequeueHead());
		assertEquals(second, deque.dequeueHead());
		assertEquals(lock, deque.getMutex());
	}

	public void testConstructor() {
		boolean exCaught = false;
		try {
			Object at = ClassTools.newInstance(DequeTools.class);
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

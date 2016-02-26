/*******************************************************************************
 * Copyright (c) 2015, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.queue;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.jpt.common.utility.internal.ClassTools;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.comparator.ComparatorTools;
import org.eclipse.jpt.common.utility.internal.queue.ArrayQueue;
import org.eclipse.jpt.common.utility.internal.queue.ConcurrentQueue;
import org.eclipse.jpt.common.utility.internal.queue.LinkedQueue;
import org.eclipse.jpt.common.utility.internal.queue.ListQueue;
import org.eclipse.jpt.common.utility.internal.queue.PriorityQueue;
import org.eclipse.jpt.common.utility.internal.queue.QueueTools;
import org.eclipse.jpt.common.utility.internal.queue.SynchronizedQueue;
import org.eclipse.jpt.common.utility.internal.stack.ArrayStack;
import org.eclipse.jpt.common.utility.internal.stack.StackTools;
import org.eclipse.jpt.common.utility.queue.Queue;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import junit.framework.TestCase;

@SuppressWarnings("nls")
public class QueueToolsTests
	extends TestCase
{
	public QueueToolsTests(String name) {
		super(name);
	}

	// ********** enqueue all **********

	public void testEnqueueAllIterable() {
		ArrayList<String> iterable = new ArrayList<>();
		iterable.add("one");
		iterable.add("two");
		iterable.add("three");
		Queue<String> queue = QueueTools.arrayQueue();
		assertTrue(QueueTools.enqueueAll(queue, iterable));
		assertEquals("one", queue.dequeue());
		assertEquals("two", queue.dequeue());
		assertEquals("three", queue.dequeue());
	}

	public void testEnqueueAllIterable_empty() {
		ArrayList<String> iterable = new ArrayList<>();
		Queue<String> queue = QueueTools.arrayQueue();
		assertFalse(QueueTools.enqueueAll(queue, iterable));
		assertTrue(queue.isEmpty());
	}

	public void testEnqueueAllIterator() {
		ArrayList<String> iterable = new ArrayList<>();
		iterable.add("one");
		iterable.add("two");
		iterable.add("three");
		Queue<String> queue = QueueTools.arrayQueue();
		assertTrue(QueueTools.enqueueAll(queue, iterable.iterator()));
		assertEquals("one", queue.dequeue());
		assertEquals("two", queue.dequeue());
		assertEquals("three", queue.dequeue());
	}

	public void testEnqueueAllArray() {
		ArrayList<String> iterable = new ArrayList<>();
		iterable.add("one");
		iterable.add("two");
		iterable.add("three");
		Queue<String> queue = QueueTools.arrayQueue();
		assertTrue(QueueTools.enqueueAll(queue, iterable.toArray(StringTools.EMPTY_STRING_ARRAY)));
		assertEquals("one", queue.dequeue());
		assertEquals("two", queue.dequeue());
		assertEquals("three", queue.dequeue());
	}

	public void testEnqueueAllArray_empty() {
		ArrayList<String> iterable = new ArrayList<>();
		Queue<String> queue = QueueTools.arrayQueue();
		assertFalse(QueueTools.enqueueAll(queue, iterable.toArray(StringTools.EMPTY_STRING_ARRAY)));
		assertTrue(queue.isEmpty());
	}


	// ********** drain **********

	public void testDrain() {
		ArrayQueue<String> queue = QueueTools.arrayQueue();
		queue.enqueue("one");
		queue.enqueue("two");
		queue.enqueue("three");
		ArrayList<String> list = QueueTools.drain(queue);
		assertEquals("one", list.get(0));
		assertEquals("two", list.get(1));
		assertEquals("three", list.get(2));
	}

	public void testDrainToCollection() {
		ArrayQueue<String> queue = QueueTools.arrayQueue();
		queue.enqueue("one");
		queue.enqueue("two");
		queue.enqueue("three");
		ArrayList<String> list = new ArrayList<>();
		assertTrue(QueueTools.drainTo(queue, list));
		assertEquals("one", list.get(0));
		assertEquals("two", list.get(1));
		assertEquals("three", list.get(2));
	}

	public void testDrainToCollection_empty() {
		ArrayQueue<String> queue = QueueTools.arrayQueue();
		ArrayList<String> list = new ArrayList<>();
		assertFalse(QueueTools.drainTo(queue, list));
		assertTrue(list.isEmpty());
	}

	public void testDrainToListIndex() {
		ArrayList<String> list = new ArrayList<>();
		list.add("one");
		list.add("two");
		list.add("three");
		Queue<String> queue = QueueTools.arrayQueue();
		queue.enqueue("aaa");
		queue.enqueue("bbb");
		queue.enqueue("ccc");
		assertTrue(QueueTools.drainTo(queue, list, 2));
		assertEquals("one", list.get(0));
		assertEquals("two", list.get(1));
		assertEquals("aaa", list.get(2));
		assertEquals("bbb", list.get(3));
		assertEquals("ccc", list.get(4));
		assertEquals("three", list.get(5));
	}

	public void testDrainToListIndex_end() {
		ArrayList<String> list = new ArrayList<>();
		list.add("one");
		list.add("two");
		list.add("three");
		Queue<String> queue = QueueTools.arrayQueue();
		queue.enqueue("aaa");
		queue.enqueue("bbb");
		queue.enqueue("ccc");
		assertTrue(QueueTools.drainTo(queue, list, 3));
		assertEquals("one", list.get(0));
		assertEquals("two", list.get(1));
		assertEquals("three", list.get(2));
		assertEquals("aaa", list.get(3));
		assertEquals("bbb", list.get(4));
		assertEquals("ccc", list.get(5));
	}

	public void testDrainToListIndex_empty() {
		ArrayList<String> list = new ArrayList<>();
		list.add("one");
		list.add("two");
		list.add("three");
		Queue<String> queue = QueueTools.arrayQueue();
		assertFalse(QueueTools.drainTo(queue, list, 3));
		assertEquals("one", list.get(0));
		assertEquals("two", list.get(1));
		assertEquals("three", list.get(2));
	}

	public void testDrainToStack() {
		ArrayQueue<String> queue = QueueTools.arrayQueue();
		queue.enqueue("one");
		queue.enqueue("two");
		queue.enqueue("three");
		ArrayStack<String> stack = StackTools.arrayStack();
		assertTrue(QueueTools.drainTo(queue, stack));
		assertEquals("three", stack.pop());
		assertEquals("two", stack.pop());
		assertEquals("one", stack.pop());
	}

	public void testDrainToStack_empty() {
		ArrayQueue<String> queue = QueueTools.arrayQueue();
		ArrayStack<String> stack = StackTools.arrayStack();
		assertFalse(QueueTools.drainTo(queue, stack));
		assertTrue(stack.isEmpty());
	}

	public void testDrainToQueue() {
		ArrayQueue<String> queue1 = QueueTools.arrayQueue();
		queue1.enqueue("one");
		queue1.enqueue("two");
		queue1.enqueue("three");
		ArrayQueue<String> queue2 = QueueTools.arrayQueue();
		assertTrue(QueueTools.drainTo(queue1, queue2));
		assertEquals("one", queue2.dequeue());
		assertEquals("two", queue2.dequeue());
		assertEquals("three", queue2.dequeue());
	}

	public void testDrainToQueue_empty() {
		ArrayQueue<String> queue1 = QueueTools.arrayQueue();
		ArrayQueue<String> queue2 = QueueTools.arrayQueue();
		assertFalse(QueueTools.drainTo(queue1, queue2));
		assertTrue(queue2.isEmpty());
	}

	public void testDrainToMapTransformer() {
		ArrayQueue<String> queue = QueueTools.arrayQueue();
		queue.enqueue("zero");
		queue.enqueue("one");
		queue.enqueue("two");
		Map<String, String>map = new HashMap<>();
		assertTrue(QueueTools.drainTo(queue, map, FIRST_LETTER_TRANSFORMER));
		assertEquals("one", map.get("o"));
		assertEquals("two", map.get("t"));
		assertEquals("zero", map.get("z"));
	}

	public void testDrainToMapTransformer_empty() {
		ArrayQueue<String> queue = QueueTools.arrayQueue();
		Map<String, String>map = new HashMap<>();
		assertFalse(QueueTools.drainTo(queue, map, FIRST_LETTER_TRANSFORMER));
		assertTrue(map.isEmpty());
	}

	public void testDrainToMapTransformerTransformer() {
		ArrayQueue<String> queue = QueueTools.arrayQueue();
		queue.enqueue("zero");
		queue.enqueue("one");
		queue.enqueue("two");
		Map<String, String>map = new HashMap<>();
		assertTrue(QueueTools.drainTo(queue, map, FIRST_LETTER_TRANSFORMER, EMPHASIZER));
		assertEquals("*one*", map.get("o"));
		assertEquals("*two*", map.get("t"));
		assertEquals("*zero*", map.get("z"));
	}

	public void testDrainToMapTransformerTransformer_empty() {
		ArrayQueue<String> queue = QueueTools.arrayQueue();
		Map<String, String>map = new HashMap<>();
		assertFalse(QueueTools.drainTo(queue, map, FIRST_LETTER_TRANSFORMER, EMPHASIZER));
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


	// ********** array queue **********

	public void testArrayQueue() {
		ArrayQueue<String> queue = QueueTools.arrayQueue();
		assertTrue(queue.isEmpty());
	}

	public void testArrayQueueInt() {
		ArrayQueue<String> queue = QueueTools.arrayQueue(20);
		assertTrue(queue.isEmpty());
		assertEquals(20, ((Object[]) ObjectTools.get(queue, "elements")).length);
	}

	public void testArrayQueueIterable() {
		ArrayList<String> iterable = new ArrayList<>();
		iterable.add("one");
		iterable.add("two");
		iterable.add("three");
		Queue<String> queue = QueueTools.arrayQueue(iterable);
		assertEquals("one", queue.dequeue());
		assertEquals("two", queue.dequeue());
		assertEquals("three", queue.dequeue());
	}

	public void testArrayQueueIterableInt() {
		ArrayList<String> iterable = new ArrayList<>();
		iterable.add("one");
		iterable.add("two");
		iterable.add("three");
		Queue<String> queue = QueueTools.arrayQueue(iterable, 5);
		assertEquals("one", queue.dequeue());
		assertEquals("two", queue.dequeue());
		assertEquals("three", queue.dequeue());
	}

	public void testArrayQueueIterator() {
		ArrayList<String> iterable = new ArrayList<>();
		iterable.add("one");
		iterable.add("two");
		iterable.add("three");
		Queue<String> queue = QueueTools.arrayQueue(iterable.iterator());
		assertEquals("one", queue.dequeue());
		assertEquals("two", queue.dequeue());
		assertEquals("three", queue.dequeue());
	}

	public void testArrayQueueIteratorInt() {
		ArrayList<String> iterable = new ArrayList<>();
		iterable.add("one");
		iterable.add("two");
		iterable.add("three");
		Queue<String> queue = QueueTools.arrayQueue(iterable.iterator(), 5);
		assertEquals("one", queue.dequeue());
		assertEquals("two", queue.dequeue());
		assertEquals("three", queue.dequeue());
	}

	public void testArrayQueueArray() {
		ArrayList<String> iterable = new ArrayList<>();
		iterable.add("one");
		iterable.add("two");
		iterable.add("three");
		Queue<String> queue = QueueTools.arrayQueue(iterable.toArray(StringTools.EMPTY_STRING_ARRAY));
		assertEquals("one", queue.dequeue());
		assertEquals("two", queue.dequeue());
		assertEquals("three", queue.dequeue());
	}

	// ********** linked queue **********

	public void testLinkedQueue() {
		LinkedQueue<String> queue = QueueTools.linkedQueue();
		assertTrue(queue.isEmpty());
	}

	public void testLinkedQueueIterable() {
		ArrayList<String> iterable = new ArrayList<>();
		iterable.add("one");
		iterable.add("two");
		iterable.add("three");
		Queue<String> queue = QueueTools.linkedQueue(iterable);
		assertEquals("one", queue.dequeue());
		assertEquals("two", queue.dequeue());
		assertEquals("three", queue.dequeue());
	}

	public void testLinkedQueueIterator() {
		ArrayList<String> iterable = new ArrayList<>();
		iterable.add("one");
		iterable.add("two");
		iterable.add("three");
		Queue<String> queue = QueueTools.linkedQueue(iterable.iterator());
		assertEquals("one", queue.dequeue());
		assertEquals("two", queue.dequeue());
		assertEquals("three", queue.dequeue());
	}

	public void testLinkedQueueArray() {
		ArrayList<String> iterable = new ArrayList<>();
		iterable.add("one");
		iterable.add("two");
		iterable.add("three");
		Queue<String> queue = QueueTools.linkedQueue(iterable.toArray(StringTools.EMPTY_STRING_ARRAY));
		assertEquals("one", queue.dequeue());
		assertEquals("two", queue.dequeue());
		assertEquals("three", queue.dequeue());
	}

	// ********** concurrent queue **********

	public void testConcurrentQueue() {
		ConcurrentQueue<String> queue = QueueTools.concurrentQueue();
		assertTrue(queue.isEmpty());
	}

	public void testConcurrentQueueIterable() {
		ArrayList<String> iterable = new ArrayList<>();
		iterable.add("one");
		iterable.add("two");
		iterable.add("three");
		Queue<String> queue = QueueTools.concurrentQueue(iterable);
		assertEquals("one", queue.dequeue());
		assertEquals("two", queue.dequeue());
		assertEquals("three", queue.dequeue());
	}

	public void testConcurrentQueueIterator() {
		ArrayList<String> iterable = new ArrayList<>();
		iterable.add("one");
		iterable.add("two");
		iterable.add("three");
		Queue<String> queue = QueueTools.concurrentQueue(iterable.iterator());
		assertEquals("one", queue.dequeue());
		assertEquals("two", queue.dequeue());
		assertEquals("three", queue.dequeue());
	}

	public void testConcurrentQueueArray() {
		ArrayList<String> iterable = new ArrayList<>();
		iterable.add("one");
		iterable.add("two");
		iterable.add("three");
		Queue<String> queue = QueueTools.concurrentQueue(iterable.toArray(StringTools.EMPTY_STRING_ARRAY));
		assertEquals("one", queue.dequeue());
		assertEquals("two", queue.dequeue());
		assertEquals("three", queue.dequeue());
	}

	// ********** fixed-capacity array queue **********

	public void testFixedCapacityArrayQueueCollection() {
		ArrayList<String> iterable = new ArrayList<>();
		iterable.add("one");
		iterable.add("two");
		iterable.add("three");
		Queue<String> queue = QueueTools.fixedCapacityArrayQueue(iterable);
		assertEquals("one", queue.dequeue());
		assertEquals("two", queue.dequeue());
		assertEquals("three", queue.dequeue());
	}

	// ********** misc **********

	public void testPriorityQueueComparator() {
		PriorityQueue<String> queue = QueueTools.priorityQueue(ComparatorTools.<String>reverseComparator());
		String first = "first";
		String second = "second";

		queue.enqueue(first);
		queue.enqueue(second);
		assertEquals(second, queue.dequeue());
		assertEquals(first, queue.dequeue());
	}

	public void testPriorityQueueObjectArray() {
		String first = "first";
		String second = "second";
		String third = "third";
		String[] array = new String[] { null, second, first, third };
		PriorityQueue<String> queue = QueueTools.priorityQueue(array);
		assertEquals(first, queue.dequeue());
		assertEquals(second, queue.dequeue());
		assertEquals(third, queue.dequeue());
	}

	public void testPriorityQueueComparatorObjectArray() {
		String first = "first";
		String second = "second";
		String third = "third";
		String[] array = new String[] { null, first, second, third };
		PriorityQueue<String> queue = QueueTools.priorityQueue(ComparatorTools.<String>reverseComparator(), array);
		assertEquals(third, queue.dequeue());
		assertEquals(second, queue.dequeue());
		assertEquals(first, queue.dequeue());
	}

	public void testSynchronizedQueueObject() {
		Object lock = new Object();
		SynchronizedQueue<String> queue = QueueTools.synchronizedQueue(lock);
		String first = "first";
		String second = "second";

		queue.enqueue(first);
		queue.enqueue(second);
		assertEquals(first, queue.dequeue());
		assertEquals(second, queue.dequeue());
		assertEquals(lock, queue.getMutex());
	}

	public void testSynchronizedQueueQueueObject() {
		Object lock = new Object();
		Queue<String> innerQueue = QueueTools.arrayQueue();
		String first = "first";
		String second = "second";
		innerQueue.enqueue(first);
		innerQueue.enqueue(second);

		SynchronizedQueue<String> queue = QueueTools.synchronizedQueue(innerQueue, lock);
		assertEquals(first, queue.dequeue());
		assertEquals(second, queue.dequeue());
		assertEquals(lock, queue.getMutex());
	}

	public void testAdaptList() {
		ArrayList<String> list = new ArrayList<>();
		list.add("one");
		list.add("two");
		list.add("three");
		ListQueue<String> queue = QueueTools.adapt(list);
		assertEquals("one", queue.dequeue());
		assertEquals("two", queue.dequeue());
		assertEquals("three", queue.dequeue());
	}

	public void testEmptyQueue() {
		Queue<String> queue = QueueTools.emptyQueue();
		assertTrue(queue.isEmpty());
	}

	public void testConstructor() {
		boolean exCaught = false;
		try {
			Object at = ClassTools.newInstance(QueueTools.class);
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

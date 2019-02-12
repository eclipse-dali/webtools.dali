/*******************************************************************************
 * Copyright (c) 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.queue;

import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Random;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.queue.PriorityQueue;
import org.eclipse.jpt.common.utility.internal.queue.QueueTools;
import org.eclipse.jpt.common.utility.queue.Queue;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;
import junit.framework.TestCase;

@SuppressWarnings("nls")
public class PriorityQueueTests
	extends TestCase
{
	public PriorityQueueTests(String name) {
		super(name);
	}

	private Queue<String> buildQueue() {
		return QueueTools.priorityQueue();
	}

	public void testIsEmpty() {
		Queue<String> queue = this.buildQueue();
		assertTrue(queue.isEmpty());
		queue.enqueue("first");
		assertFalse(queue.isEmpty());
		queue.enqueue("second");
		assertFalse(queue.isEmpty());
		queue.dequeue();
		assertFalse(queue.isEmpty());
		queue.dequeue();
		assertTrue(queue.isEmpty());
	}

	public void testEnqueueAndDequeue() {
		Queue<String> queue = this.buildQueue();
		String first = "first";
		String second = "second";

		queue.enqueue(first);
		queue.enqueue(second);
		assertEquals(first, queue.dequeue());
		assertEquals(second, queue.dequeue());
	}

	public void testEnqueueAndPeek() {
		Queue<String> queue = this.buildQueue();
		String first = "first";
		String second = "second";

		queue.enqueue(first);
		queue.enqueue(second);
		assertEquals(first, queue.peek());
		assertEquals(first, queue.peek());
		assertEquals(first, queue.dequeue());
		assertEquals(second, queue.peek());
		assertEquals(second, queue.peek());
		assertEquals(second, queue.dequeue());
	}

	public void testEmptyQueueExceptionPeek() {
		Queue<String> queue = this.buildQueue();
		String first = "first";
		String second = "second";

		queue.enqueue(first);
		queue.enqueue(second);
		assertEquals(first, queue.peek());
		assertEquals(first, queue.dequeue());
		assertEquals(second, queue.peek());
		assertEquals(second, queue.dequeue());

		boolean exCaught = false;
		try {
			queue.peek();
			fail();
		} catch (NoSuchElementException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testEmptyQueueExceptionDequeue() {
		Queue<String> queue = this.buildQueue();
		String first = "first";
		String second = "second";

		queue.enqueue(first);
		queue.enqueue(second);
		assertEquals(first, queue.peek());
		assertEquals(first, queue.dequeue());
		assertEquals(second, queue.peek());
		assertEquals(second, queue.dequeue());

		boolean exCaught = false;
		try {
			queue.dequeue();
			fail();
		} catch (NoSuchElementException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testClone() {
		Queue<String> queue = this.buildQueue();
		queue.enqueue("first");
		queue.enqueue("second");
		queue.enqueue("third");

		@SuppressWarnings("unchecked")
		Queue<String> clone = (Queue<String>) ObjectTools.execute(queue, "clone");
		this.verifyClone(queue, clone);
	}

	public void testSerialization() throws Exception {
		Queue<String> queue = this.buildQueue();
		queue.enqueue("first");
		queue.enqueue("second");
		queue.enqueue("third");

		this.verifyClone(queue, TestTools.serialize(queue));
	}

	protected void verifyClone(Queue<String> original, Queue<String> clone) {
		assertNotSame(original, clone);
		assertEquals(original.peek(), clone.peek());
		assertEquals(original.dequeue(), clone.dequeue());
		assertEquals(original.peek(), clone.peek());
		assertEquals(original.dequeue(), clone.dequeue());
		assertEquals(original.isEmpty(), clone.isEmpty());
		assertEquals(original.peek(), clone.peek());
		assertEquals(original.dequeue(), clone.dequeue());
		assertTrue(original.isEmpty());
		assertEquals(original.isEmpty(), clone.isEmpty());

		original.enqueue("fourth");
		assertFalse(original.isEmpty());
		// clone should still be empty
		assertTrue(clone.isEmpty());
	}

	public void testToString() throws Exception {
		Queue<String> queue = this.buildQueue();
		assertEquals("[]", queue.toString());
		queue.enqueue("first");
		assertEquals("[first]", queue.toString());
		queue.enqueue("second");
		assertEquals("[first, second]", queue.toString());
		queue.enqueue("third");
		assertEquals("[first, second, third]", queue.toString());
	}

	public void testArrayCapacityExceeded() {
		Queue<Integer> queue = QueueTools.priorityQueue();
		assertTrue(queue.isEmpty());
		queue.enqueue(Integer.valueOf(1));
		assertFalse(queue.isEmpty());
		queue.enqueue(Integer.valueOf(2));
		assertFalse(queue.isEmpty());
		queue.enqueue(Integer.valueOf(3));
		queue.enqueue(Integer.valueOf(10));
		queue.enqueue(Integer.valueOf(11));
		queue.enqueue(Integer.valueOf(12));
		queue.enqueue(Integer.valueOf(4));
		queue.enqueue(Integer.valueOf(4));
		queue.enqueue(Integer.valueOf(7));
		queue.enqueue(Integer.valueOf(8));
		queue.enqueue(Integer.valueOf(9));
		queue.enqueue(Integer.valueOf(5));
		queue.enqueue(Integer.valueOf(6));
		queue.enqueue(Integer.valueOf(9));

		assertEquals(Integer.valueOf(1), queue.dequeue());
		assertFalse(queue.isEmpty());
		assertEquals(Integer.valueOf(2), queue.dequeue());
		assertFalse(queue.isEmpty());
		assertEquals(Integer.valueOf(3), queue.dequeue());
		assertEquals(Integer.valueOf(4), queue.dequeue());
		assertEquals(Integer.valueOf(4), queue.dequeue());
		assertEquals(Integer.valueOf(5), queue.dequeue());
		assertEquals(Integer.valueOf(6), queue.dequeue());
		assertEquals(Integer.valueOf(7), queue.dequeue());
		assertEquals(Integer.valueOf(8), queue.dequeue());
		assertEquals(Integer.valueOf(9), queue.dequeue());
		assertEquals(Integer.valueOf(9), queue.dequeue());
		assertEquals(Integer.valueOf(10), queue.dequeue());
		assertEquals(Integer.valueOf(11), queue.dequeue());
		assertEquals(Integer.valueOf(12), queue.dequeue());
		assertTrue(queue.isEmpty());
	}

	public void testMultipleSizes() throws Exception {
		int maxSize = 50;
		Random random = new Random();
		for (int size = 1; size <= maxSize; size++) {
			Queue<Integer> queue = QueueTools.priorityQueue(size);
			for (int i = size; i-- > 0; ) {
				queue.enqueue(Integer.valueOf(random.nextInt()));
			}
			Integer current = queue.dequeue();
			int i = 1;
			do {
				if ( ! queue.isEmpty()) {
					Integer next = queue.dequeue();
					i++;
					assertTrue(current.intValue() <= next.intValue());
					current = next;
				}
			} while ( ! queue.isEmpty());
			assertEquals(size, i);
		}
	}

	// instance variable access appears to be slightly faster(!)
//	public void testLocalVariablePerformance() throws Exception {
//		int minSize = 5000000;
//		int executionCount = 10;
//		Random random = new Random();
//		for (int x = 0; x < executionCount; x++) {
//			int size = minSize + random.nextInt(minSize / 2);
//			Integer[] values = new Integer[size];
//			for (int i = size; i-- > 0; ) {
//				values[i] = Integer.valueOf(random.nextInt());
//			}
//			PriorityQueue<Integer> queue;
//			queue = QueueTools.priorityQueue(size);
//			for (Integer value : values) {
//				queue.enqueue(value);
//			}
//			long start = System.currentTimeMillis();
//			while ( ! queue.isEmpty()) {
//				queue.dequeue();
//			}
//			long execTime = System.currentTimeMillis() - start;
//			System.out.println("execution time (local var):    " + (execTime / 1000.0));
//			assertTrue(queue.isEmpty());
//
//			queue = QueueTools.priorityQueue(size);
//			for (Integer value : values) {
//				queue.enqueue(value);
//			}
//			start = System.currentTimeMillis();
//			while ( ! queue.isEmpty()) {
//				queue.dequeueIV();
//			}
//			execTime = System.currentTimeMillis() - start;
//			System.out.println("execution time (instance var): " + (execTime / 1000.0));
//			System.out.println();
//			assertTrue(queue.isEmpty());
//		}
//	}
//
	public void testSomethingBig() throws Exception {
		int size = 500000;
		Queue<Integer> queue = QueueTools.priorityQueue(size);
		Random random = new Random();
		for (int i = size; i-- > 0; ) {
			queue.enqueue(Integer.valueOf(random.nextInt()));
		}
		Integer current = queue.dequeue();
		int i = 1;
		do {
			if ( ! queue.isEmpty()) {
				Integer next = queue.dequeue();
				i++;
				assertTrue(current.intValue() <= next.intValue());
				current = next;
			}
		} while ( ! queue.isEmpty());
		assertEquals(size, i);
	}

	public void testEnsureCapacity() throws Exception {
		PriorityQueue<String> queue = QueueTools.priorityQueue();
		queue.enqueue("b");
		queue.enqueue("c");
		queue.enqueue("a");
		assertEquals(11, ((Object[]) ObjectTools.get(queue, "elements")).length);
		queue.ensureCapacity(420);
		assertEquals(421, ((Object[]) ObjectTools.get(queue, "elements")).length);
		assertEquals("a", queue.dequeue());
		assertEquals("b", queue.dequeue());
		assertEquals("c", queue.dequeue());
		assertTrue(queue.isEmpty());
	}

	public void testTrimToSize() throws Exception {
		PriorityQueue<String> queue = QueueTools.priorityQueue();
		queue.enqueue("b");
		queue.enqueue("c");
		queue.enqueue("a");
		assertEquals(11, ((Object[]) ObjectTools.get(queue, "elements")).length);
		queue.trimToSize();
		assertEquals(4, ((Object[]) ObjectTools.get(queue, "elements")).length);
		assertEquals("a", queue.dequeue());
		assertEquals("b", queue.dequeue());
		assertEquals("c", queue.dequeue());
		assertTrue(queue.isEmpty());
	}

	public void testTrimToSize_nop() throws Exception {
		PriorityQueue<String> queue = QueueTools.priorityQueue(3);
		queue.enqueue("b");
		queue.enqueue("c");
		queue.enqueue("a");
		assertEquals(4, ((Object[]) ObjectTools.get(queue, "elements")).length);
		queue.trimToSize();
		assertEquals(4, ((Object[]) ObjectTools.get(queue, "elements")).length);
		assertEquals("a", queue.dequeue());
		assertEquals("b", queue.dequeue());
		assertEquals("c", queue.dequeue());
		assertTrue(queue.isEmpty());
	}

	public void testConstructor_nullComparator() throws Exception {
		boolean exCaught = false;
		try {
			Queue<String> queue = QueueTools.priorityQueue((Comparator<String>) null, 3);
			fail("bogus queue: " + queue);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testConstructor_negativeCapacity() throws Exception {
		boolean exCaught = false;
		try {
			Queue<String> queue = QueueTools.priorityQueue(-7);
			fail("bogus queue: " + queue);
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testSerialization_fullArray() throws Exception {
		Queue<String> queue = QueueTools.priorityQueue(3);
		queue.enqueue("first");
		queue.enqueue("second");
		queue.enqueue("third");

		this.verifyClone(queue, TestTools.serialize(queue));
	}

	public void testSerialization_empty() throws Exception {
		Queue<String> original = QueueTools.priorityQueue();
		Queue<String> clone = TestTools.serialize(original);
		assertNotSame(original, clone);
		assertTrue(original.isEmpty());
		assertEquals(original.isEmpty(), clone.isEmpty());

		original.enqueue("foo");
		assertFalse(original.isEmpty());
		// clone should still be empty
		assertTrue(clone.isEmpty());
	}

	public void testConstructorComparatorObjectArrayInt() throws Exception {
		int maxSize = 100;
		Integer[] array = new Integer[maxSize + 1];
		Random random = new Random();
		for (int size = 1; size <= maxSize; size++) {
			ArrayTools.fill(array, null);
			for (int i = size + 1; i-- > 1; ) {
				array[i] = Integer.valueOf(random.nextInt());
			}
			Queue<Integer> queue = QueueTools.priorityQueue(array, size);
			Integer current = queue.dequeue();
			int i = 1;
			do {
				if ( ! queue.isEmpty()) {
					Integer next = queue.dequeue();
					i++;
					assertTrue(current.intValue() <= next.intValue());
					current = next;
				}
			} while ( ! queue.isEmpty());
			assertEquals(size, i);
		}
	}

	public void testConstructorComparatorObjectArrayInt_nullComparator() throws Exception {
		String[] array = new String[5];
		boolean exCaught = false;
		try {
			Queue<String> queue = QueueTools.priorityQueue(null, array, 3);
			fail("bogus queue: " + queue);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testConstructorComparatorObjectArrayInt_nullArray() throws Exception {
		boolean exCaught = false;
		try {
			Queue<String> queue = QueueTools.priorityQueue((String[]) null, 3);
			fail("bogus queue: " + queue);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testConstructorComparatorObjectArrayInt_emptyArray() throws Exception {
		boolean exCaught = false;
		try {
			Queue<String> queue = QueueTools.priorityQueue(StringTools.EMPTY_STRING_ARRAY, 3);
			fail("bogus queue: " + queue);
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testConstructorComparatorObjectArrayInt_negativeSize() throws Exception {
		String[] array = new String[5];
		boolean exCaught = false;
		try {
			Queue<String> queue = QueueTools.priorityQueue(array, -7);
			fail("bogus queue: " + queue);
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

}

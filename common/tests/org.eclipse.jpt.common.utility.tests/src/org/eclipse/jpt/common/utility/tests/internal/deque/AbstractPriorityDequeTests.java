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

import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Random;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.deque.AbstractPriorityDeque;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;
import junit.framework.TestCase;

@SuppressWarnings("nls")
public abstract class AbstractPriorityDequeTests
	extends TestCase
{
	public AbstractPriorityDequeTests(String name) {
		super(name);
	}

	abstract <E extends Comparable<E>> AbstractPriorityDeque<E> buildDeque();

	abstract <E extends Comparable<E>> AbstractPriorityDeque<E> buildDeque(int capacity);

	abstract <E extends Comparable<E>> AbstractPriorityDeque<E> buildDeque(Comparator<E> comparator, int capacity);

	public void testIsEmpty_head() {
		AbstractPriorityDeque<String> deque = this.buildDeque();
		assertTrue(deque.isEmpty());
		deque.enqueue("first");
		assertFalse(deque.isEmpty());
		deque.enqueue("second");
		assertFalse(deque.isEmpty());
		deque.dequeueHead();
		assertFalse(deque.isEmpty());
		deque.dequeueHead();
		assertTrue(deque.isEmpty());
	}

	public void testIsEmpty_tail() {
		AbstractPriorityDeque<String> deque = this.buildDeque();
		assertTrue(deque.isEmpty());
		deque.enqueue("first");
		assertFalse(deque.isEmpty());
		deque.enqueue("second");
		assertFalse(deque.isEmpty());
		deque.dequeueTail();
		assertFalse(deque.isEmpty());
		deque.dequeueTail();
		assertTrue(deque.isEmpty());
	}

	public void testEnqueueAndDequeueHead() {
		AbstractPriorityDeque<String> deque = this.buildDeque();
		String first = "first";
		String second = "second";

		deque.enqueue(first);
		deque.enqueueTail(second);
		assertEquals(first, deque.dequeueHead());
		assertEquals(second, deque.dequeueHead());
	}

	public void testEnqueueAndDequeueTail() {
		AbstractPriorityDeque<String> deque = this.buildDeque();
		String first = "first";
		String second = "second";

		deque.enqueue(first);
		deque.enqueue(second);
		assertEquals(second, deque.dequeueTail());
		assertEquals(first, deque.dequeueTail());
	}

	public void testEnqueueAndPeekHead() {
		AbstractPriorityDeque<String> deque = this.buildDeque();
		String first = "first";
		String second = "second";

		deque.enqueue(first);
		deque.enqueue(second);
		assertEquals(first, deque.peekHead());
		assertEquals(first, deque.peekHead());
		assertEquals(first, deque.dequeueHead());
		assertEquals(second, deque.peekHead());
		assertEquals(second, deque.peekHead());
		assertEquals(second, deque.dequeueHead());
	}

	public void testEnqueueAndPeekTail() {
		AbstractPriorityDeque<String> deque = this.buildDeque();
		String first = "first";
		String second = "second";
		String third = "third";
		String zzzz = "zzzz";

		deque.enqueue(second);
		deque.enqueue(zzzz);
		deque.enqueue(third);
		deque.enqueue(first);
		assertEquals(zzzz, deque.peekTail());
		assertEquals(zzzz, deque.peekTail());
		assertEquals(zzzz, deque.dequeueTail());
		assertEquals(third, deque.peekTail());
		assertEquals(third, deque.peekTail());
		assertEquals(third, deque.dequeueTail());
		assertEquals(second, deque.peekTail());
		assertEquals(second, deque.peekTail());
		assertEquals(second, deque.dequeueTail());
		assertEquals(first, deque.peekTail());
		assertEquals(first, deque.peekTail());
		assertEquals(first, deque.dequeueTail());
	}

	public void testEmptyQueueExceptionPeekHead() {
		AbstractPriorityDeque<String> deque = this.buildDeque();
		String first = "first";
		String second = "second";

		deque.enqueue(first);
		deque.enqueue(second);
		assertEquals(first, deque.peekHead());
		assertEquals(first, deque.dequeueHead());
		assertEquals(second, deque.peekHead());
		assertEquals(second, deque.dequeueHead());

		boolean exCaught = false;
		try {
			deque.peekHead();
			fail();
		} catch (NoSuchElementException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testEmptyQueueExceptionPeekTail() {
		AbstractPriorityDeque<String> deque = this.buildDeque();
		String first = "first";
		String second = "second";

		deque.enqueue(first);
		deque.enqueue(second);
		assertEquals(second, deque.peekTail());
		assertEquals(second, deque.dequeueTail());
		assertEquals(first, deque.peekTail());
		assertEquals(first, deque.dequeueTail());

		boolean exCaught = false;
		try {
			deque.peekTail();
			fail();
		} catch (NoSuchElementException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testEmptyQueueExceptionDequeueHead() {
		AbstractPriorityDeque<String> deque = this.buildDeque();
		String first = "first";
		String second = "second";

		deque.enqueue(first);
		deque.enqueue(second);
		assertEquals(first, deque.peekHead());
		assertEquals(first, deque.dequeueHead());
		assertEquals(second, deque.peekHead());
		assertEquals(second, deque.dequeueHead());

		boolean exCaught = false;
		try {
			deque.dequeueHead();
			fail();
		} catch (NoSuchElementException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testEmptyQueueExceptionDequeueTail() {
		AbstractPriorityDeque<String> deque = this.buildDeque();
		String first = "first";
		String second = "second";

		deque.enqueue(first);
		deque.enqueue(second);
		assertEquals(second, deque.peekTail());
		assertEquals(second, deque.dequeueTail());
		assertEquals(first, deque.peekTail());
		assertEquals(first, deque.dequeueTail());

		boolean exCaught = false;
		try {
			deque.dequeueTail();
			fail();
		} catch (NoSuchElementException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testClone() {
		AbstractPriorityDeque<String> deque = this.buildDeque();
		deque.enqueue("first");
		deque.enqueue("second");
		deque.enqueue("third");

		@SuppressWarnings("unchecked")
		AbstractPriorityDeque<String> clone = (AbstractPriorityDeque<String>) ObjectTools.execute(deque, "clone");
		this.verifyClone(deque, clone);
	}

	public void testSerialization() throws Exception {
		AbstractPriorityDeque<String> deque = this.buildDeque();
		deque.enqueue("first");
		deque.enqueue("second");
		deque.enqueue("third");

		this.verifyClone(deque, TestTools.serialize(deque));
	}

	protected void verifyClone(AbstractPriorityDeque<String> original, AbstractPriorityDeque<String> clone) {
		assertNotSame(original, clone);
		assertEquals(original.peekHead(), clone.peekHead());
		assertEquals(original.dequeueHead(), clone.dequeueHead());
		assertEquals(original.peekTail(), clone.peekTail());
		assertEquals(original.dequeueTail(), clone.dequeueTail());
		assertEquals(original.isEmpty(), clone.isEmpty());
		assertEquals(original.peekHead(), clone.peekHead());
		assertEquals(original.dequeueHead(), clone.dequeueHead());
		assertTrue(original.isEmpty());
		assertEquals(original.isEmpty(), clone.isEmpty());

		original.enqueue("fourth");
		assertFalse(original.isEmpty());
		// clone should still be empty
		assertTrue(clone.isEmpty());
	}

	public void testToString() throws Exception {
		AbstractPriorityDeque<String> deque = this.buildDeque();
		assertEquals("[]", deque.toString());
		deque.enqueue("first");
		assertEquals("[first]", deque.toString());
		deque.enqueue("second");
		assertEquals("[first, second]", deque.toString());
		deque.enqueue("third");
		assertEquals("[first, second, third]", deque.toString());
	}

	public void testMultipleSizes() throws Exception {
		int maxSize = 50;
		Random random = new Random();
		for (int size = 1; size <= maxSize; size++) {
			int[] values = new int[size];
			for (int i = 0; i < values.length; i++) {
				values[i] = random.nextInt();
			}
			AbstractPriorityDeque<Integer> deque = this.buildDeque(size);
			for (int value : values) {
				deque.enqueue(Integer.valueOf(value));
			}
			Integer currentHead = deque.dequeueHead();
			int i = 1;
			Integer currentTail = null;
			if ( ! deque.isEmpty()) {
				currentTail = deque.dequeueTail();
				i++;
			}
			do {
				if ( ! deque.isEmpty()) {
					Integer nextHead = deque.dequeueHead();
					i++;
					assertTrue(currentHead.intValue() <= nextHead.intValue());
					currentHead = nextHead;
				}
				if ( ! deque.isEmpty()) {
					Integer nextTail = deque.dequeueTail();
					i++;
					assertTrue((currentTail != null) && (currentTail.intValue() >= nextTail.intValue()));
					currentTail = nextTail;
				}
			} while ( ! deque.isEmpty());
			assertEquals(size, i);
		}
	}

	public void testSomethingBig() throws Exception {
		int size = 500000;
		AbstractPriorityDeque<Integer> deque = this.buildDeque(size);
		Random random = new Random();
		for (int i = size; i-- > 0; ) {
			deque.enqueue(Integer.valueOf(random.nextInt()));
		}
		Integer currentHead = deque.dequeueHead();
		int i = 1;
		Integer currentTail = null;
		if ( ! deque.isEmpty()) {
			currentTail = deque.dequeueTail();
			i++;
		}
		do {
			if ( ! deque.isEmpty()) {
				Integer nextHead = deque.dequeueHead();
				i++;
				assertTrue(currentHead.intValue() <= nextHead.intValue());
				currentHead = nextHead;
			}
			if ( ! deque.isEmpty()) {
				Integer nextTail = deque.dequeueTail();
				i++;
				assertTrue((currentTail != null) && (currentTail.intValue() >= nextTail.intValue()));
				currentTail = nextTail;
			}
		} while ( ! deque.isEmpty());
		assertEquals(size, i);
	}

	public void testConstructor_nullComparator() throws Exception {
		boolean exCaught = false;
		try {
			AbstractPriorityDeque<String> deque = this.buildDeque(null, 3);
			fail("bogus deque: " + deque);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testConstructor_negativeCapacity() throws Exception {
		boolean exCaught = false;
		try {
			AbstractPriorityDeque<String> deque = this.buildDeque(-7);
			fail("bogus deque: " + deque);
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testSerialization_fullArray() throws Exception {
		AbstractPriorityDeque<String> deque = this.buildDeque(3);
		deque.enqueue("first");
		deque.enqueue("second");
		deque.enqueue("third");

		this.verifyClone(deque, TestTools.serialize(deque));
	}

	public void testSerialization_empty() throws Exception {
		AbstractPriorityDeque<String> original = this.buildDeque();
		AbstractPriorityDeque<String> clone = TestTools.serialize(original);
		assertNotSame(original, clone);
		assertTrue(original.isEmpty());
		assertEquals(original.isEmpty(), clone.isEmpty());

		original.enqueue("foo");
		assertFalse(original.isEmpty());
		// clone should still be empty
		assertTrue(clone.isEmpty());
	}
}

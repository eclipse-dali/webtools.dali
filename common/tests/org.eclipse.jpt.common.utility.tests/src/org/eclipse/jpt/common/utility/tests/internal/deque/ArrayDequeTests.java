/*******************************************************************************
 * Copyright (c) 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.deque;

import java.util.ArrayList;
import org.eclipse.jpt.common.utility.deque.Deque;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.deque.ArrayDeque;
import org.eclipse.jpt.common.utility.internal.deque.DequeTools;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;

@SuppressWarnings("nls")
public class ArrayDequeTests
	extends DequeTests
{
	public ArrayDequeTests(String name) {
		super(name);
	}

	@Override
	Deque<String> buildDeque() {
		return DequeTools.arrayDeque();
	}

	public void testConstructor_IAE() {
		boolean exCaught = false;
		try {
			Deque<String> queue = DequeTools.arrayDeque(-1);
			fail("bogus deque: " + queue);
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testEnsureCapacity() {
		ArrayDeque<String> queue = DequeTools.arrayDeque(0);
		queue.ensureCapacity(7);
		assertEquals(7, ((Object[]) ObjectTools.get(queue, "elements")).length);
	}

	public void testTrimToSize() {
		ArrayDeque<String> queue = DequeTools.arrayDeque(10);
		queue.enqueueTail("foo");
		queue.enqueueTail("bar");
		queue.trimToSize();
		assertEquals(2, ((Object[]) ObjectTools.get(queue, "elements")).length);
	}

	public void testTrimToSize_noChange() {
		ArrayDeque<String> queue = DequeTools.arrayDeque(2);
		queue.enqueueTail("foo");
		queue.enqueueTail("bar");
		queue.trimToSize();
		assertEquals(2, ((Object[]) ObjectTools.get(queue, "elements")).length);
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
		Deque<String> queue = DequeTools.arrayDeque(c);

		assertFalse(queue.isEmpty());
		assertEquals("first", queue.peekHead());
		queue.enqueueTail("eleventh");
		queue.enqueueTail("twelfth");

		assertEquals("first", queue.peekHead());
		assertEquals("first", queue.dequeueHead());
		assertEquals("second", queue.dequeueHead());
		assertFalse(queue.isEmpty());
		assertEquals("third", queue.peekHead());
		assertEquals("third", queue.dequeueHead());
		assertEquals("fourth", queue.dequeueHead());
		assertEquals("fifth", queue.dequeueHead());
		assertEquals("sixth", queue.dequeueHead());
		assertEquals("seventh", queue.dequeueHead());
		assertEquals("eighth", queue.dequeueHead());
		assertEquals("ninth", queue.dequeueHead());
		assertEquals("tenth", queue.dequeueHead());
		assertEquals("eleventh", queue.dequeueHead());
		assertEquals("twelfth", queue.dequeueHead());
		assertTrue(queue.isEmpty());
	}

	public void testWrappedElementsTail() {
		Deque<String> queue = this.buildDeque();
		assertTrue(queue.isEmpty());
		queue.enqueueTail("first");
		assertFalse(queue.isEmpty());
		queue.enqueueTail("second");
		assertFalse(queue.isEmpty());
		queue.enqueueTail("third");
		queue.enqueueTail("fourth");
		queue.enqueueTail("fifth");
		queue.enqueueTail("sixth");

		// make room for 11 and 12
		assertEquals("first", queue.dequeueHead());
		assertFalse(queue.isEmpty());
		assertEquals("second", queue.dequeueHead());
		assertFalse(queue.isEmpty());
		assertEquals("third", queue.dequeueHead());

		queue.enqueueTail("seventh");
		queue.enqueueTail("eighth");
		queue.enqueueTail("ninth");
		queue.enqueueTail("tenth");
		queue.enqueueTail("eleventh");
		queue.enqueueTail("twelfth");

		assertEquals("fourth", queue.dequeueHead());
		assertEquals("fifth", queue.dequeueHead());
		assertEquals("sixth", queue.dequeueHead());
		assertEquals("seventh", queue.dequeueHead());
		assertEquals("eighth", queue.dequeueHead());
		assertEquals("ninth", queue.dequeueHead());
		assertEquals("tenth", queue.dequeueHead());
		assertEquals("eleventh", queue.dequeueHead());
		assertEquals("twelfth", queue.dequeueHead());
		assertTrue(queue.isEmpty());
	}

	public void testWrappedElementsHead() {
		Deque<String> queue = this.buildDeque();
		assertTrue(queue.isEmpty());
		queue.enqueueHead("first");
		assertFalse(queue.isEmpty());
		queue.enqueueHead("second");
		assertFalse(queue.isEmpty());
		queue.enqueueHead("third");
		queue.enqueueHead("fourth");
		queue.enqueueHead("fifth");
		queue.enqueueHead("sixth");

		// make room for 11 and 12
		assertEquals("first", queue.dequeueTail());
		assertFalse(queue.isEmpty());
		assertEquals("second", queue.dequeueTail());
		assertFalse(queue.isEmpty());
		assertEquals("third", queue.dequeueTail());

		queue.enqueueHead("seventh");
		queue.enqueueHead("eighth");
		queue.enqueueHead("ninth");
		queue.enqueueHead("tenth");
		queue.enqueueHead("eleventh");
		queue.enqueueHead("twelfth");

		assertEquals("fourth", queue.dequeueTail());
		assertEquals("fifth", queue.dequeueTail());
		assertEquals("sixth", queue.dequeueTail());
		assertEquals("seventh", queue.dequeueTail());
		assertEquals("eighth", queue.dequeueTail());
		assertEquals("ninth", queue.dequeueTail());
		assertEquals("tenth", queue.dequeueTail());
		assertEquals("eleventh", queue.dequeueTail());
		assertEquals("twelfth", queue.dequeueTail());
		assertTrue(queue.isEmpty());
	}

	public void testArrayCapacityExceededTail() {
		Deque<String> queue = this.buildDeque();
		assertTrue(queue.isEmpty());
		queue.enqueueTail("first");
		assertFalse(queue.isEmpty());
		queue.enqueueTail("second");
		assertFalse(queue.isEmpty());
		queue.enqueueTail("third");
		queue.enqueueTail("fourth");
		queue.enqueueTail("fifth");
		queue.enqueueTail("sixth");
		queue.enqueueTail("seventh");
		queue.enqueueTail("eighth");
		queue.enqueueTail("ninth");
		queue.enqueueTail("tenth");
		queue.enqueueTail("eleventh");
		queue.enqueueTail("twelfth");

		assertEquals("first", queue.dequeueHead());
		assertFalse(queue.isEmpty());
		assertEquals("second", queue.dequeueHead());
		assertFalse(queue.isEmpty());
		assertEquals("third", queue.dequeueHead());
		assertEquals("fourth", queue.dequeueHead());
		assertEquals("fifth", queue.dequeueHead());
		assertEquals("sixth", queue.dequeueHead());
		assertEquals("seventh", queue.dequeueHead());
		assertEquals("eighth", queue.dequeueHead());
		assertEquals("ninth", queue.dequeueHead());
		assertEquals("tenth", queue.dequeueHead());
		assertEquals("eleventh", queue.dequeueHead());
		assertEquals("twelfth", queue.dequeueHead());
		assertTrue(queue.isEmpty());
	}

	public void testArrayCapacityExceededHead() {
		Deque<String> queue = this.buildDeque();
		assertTrue(queue.isEmpty());
		queue.enqueueHead("first");
		assertFalse(queue.isEmpty());
		queue.enqueueHead("second");
		assertFalse(queue.isEmpty());
		queue.enqueueHead("third");
		queue.enqueueHead("fourth");
		queue.enqueueHead("fifth");
		queue.enqueueHead("sixth");
		queue.enqueueHead("seventh");
		queue.enqueueHead("eighth");
		queue.enqueueHead("ninth");
		queue.enqueueHead("tenth");
		queue.enqueueHead("eleventh");
		queue.enqueueHead("twelfth");

		assertEquals("first", queue.dequeueTail());
		assertFalse(queue.isEmpty());
		assertEquals("second", queue.dequeueTail());
		assertFalse(queue.isEmpty());
		assertEquals("third", queue.dequeueTail());
		assertEquals("fourth", queue.dequeueTail());
		assertEquals("fifth", queue.dequeueTail());
		assertEquals("sixth", queue.dequeueTail());
		assertEquals("seventh", queue.dequeueTail());
		assertEquals("eighth", queue.dequeueTail());
		assertEquals("ninth", queue.dequeueTail());
		assertEquals("tenth", queue.dequeueTail());
		assertEquals("eleventh", queue.dequeueTail());
		assertEquals("twelfth", queue.dequeueTail());
		assertTrue(queue.isEmpty());
	}

	public void testArrayCapacityExceededWithWrappedElementsTail() {
		Deque<String> queue = this.buildDeque();
		assertTrue(queue.isEmpty());
		queue.enqueueTail("first");
		assertFalse(queue.isEmpty());
		queue.enqueueTail("second");
		assertFalse(queue.isEmpty());
		queue.enqueueTail("third");
		queue.enqueueTail("fourth");
		queue.enqueueTail("fifth");
		queue.enqueueTail("sixth");

		assertEquals("first", queue.dequeueHead());
		assertFalse(queue.isEmpty());
		assertEquals("second", queue.dequeueHead());
		assertFalse(queue.isEmpty());
		assertEquals("third", queue.dequeueHead());

		queue.enqueueTail("seventh");
		queue.enqueueTail("eighth");
		queue.enqueueTail("ninth");
		queue.enqueueTail("tenth");
		queue.enqueueTail("eleventh");
		queue.enqueueTail("twelfth");
		queue.enqueueTail("thirteenth");
		queue.enqueueTail("fourteenth");
		queue.enqueueTail("fifteenth");

		assertEquals("fourth", queue.dequeueHead());
		assertEquals("fifth", queue.dequeueHead());
		assertEquals("sixth", queue.dequeueHead());
		assertEquals("seventh", queue.dequeueHead());
		assertEquals("eighth", queue.dequeueHead());
		assertEquals("ninth", queue.dequeueHead());
		assertEquals("tenth", queue.dequeueHead());
		assertEquals("eleventh", queue.dequeueHead());
		assertEquals("twelfth", queue.dequeueHead());
		assertEquals("thirteenth", queue.dequeueHead());
		assertEquals("fourteenth", queue.dequeueHead());
		assertEquals("fifteenth", queue.dequeueHead());
		assertTrue(queue.isEmpty());
	}

	public void testArrayCapacityExceededWithWrappedElementsHead() {
		Deque<String> queue = this.buildDeque();
		assertTrue(queue.isEmpty());
		queue.enqueueHead("first");
		assertFalse(queue.isEmpty());
		queue.enqueueHead("second");
		assertFalse(queue.isEmpty());
		queue.enqueueHead("third");
		queue.enqueueHead("fourth");
		queue.enqueueHead("fifth");
		queue.enqueueHead("sixth");

		assertEquals("first", queue.dequeueTail());
		assertFalse(queue.isEmpty());
		assertEquals("second", queue.dequeueTail());
		assertFalse(queue.isEmpty());
		assertEquals("third", queue.dequeueTail());

		queue.enqueueHead("seventh");
		queue.enqueueHead("eighth");
		queue.enqueueHead("ninth");
		queue.enqueueHead("tenth");
		queue.enqueueHead("eleventh");
		queue.enqueueHead("twelfth");
		queue.enqueueHead("thirteenth");
		queue.enqueueHead("fourteenth");
		queue.enqueueHead("fifteenth");

		assertEquals("fourth", queue.dequeueTail());
		assertEquals("fifth", queue.dequeueTail());
		assertEquals("sixth", queue.dequeueTail());
		assertEquals("seventh", queue.dequeueTail());
		assertEquals("eighth", queue.dequeueTail());
		assertEquals("ninth", queue.dequeueTail());
		assertEquals("tenth", queue.dequeueTail());
		assertEquals("eleventh", queue.dequeueTail());
		assertEquals("twelfth", queue.dequeueTail());
		assertEquals("thirteenth", queue.dequeueTail());
		assertEquals("fourteenth", queue.dequeueTail());
		assertEquals("fifteenth", queue.dequeueTail());
		assertTrue(queue.isEmpty());
	}

	public void testSerialization_empty() throws Exception {
		Deque<String> original = new ArrayDeque<String>(3);
		Deque<String> clone = TestTools.serialize(original);
		assertNotSame(original, clone);
		assertTrue(original.isEmpty());
		assertEquals(original.isEmpty(), clone.isEmpty());

		original.enqueueTail("fourth");
		assertFalse(original.isEmpty());
		// clone should still be empty
		assertTrue(clone.isEmpty());
	}

	public void testSerialization_fullArray() throws Exception {
		Deque<String> queue = new ArrayDeque<String>(3);
		queue.enqueueTail("first");
		queue.enqueueTail("second");
		queue.enqueueTail("third");

		this.verifyClone(queue, TestTools.serialize(queue));
	}

	public void testSerialization_wrappedArray() throws Exception {
		Deque<String> queue = new ArrayDeque<String>(3);
		queue.enqueueTail("first");
		queue.enqueueTail("second");
		queue.enqueueTail("third");
		queue.dequeueHead();
		queue.enqueueTail("fourth");

		this.verifyClone(queue, TestTools.serialize(queue));
	}
}

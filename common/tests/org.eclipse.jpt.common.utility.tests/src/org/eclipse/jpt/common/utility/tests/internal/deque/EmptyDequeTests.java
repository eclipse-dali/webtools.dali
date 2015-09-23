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

import java.util.NoSuchElementException;
import org.eclipse.jpt.common.utility.deque.Deque;
import org.eclipse.jpt.common.utility.internal.deque.DequeTools;
import org.eclipse.jpt.common.utility.internal.deque.EmptyDeque;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;
import junit.framework.TestCase;

@SuppressWarnings("nls")
public class EmptyDequeTests
	extends TestCase
{
	public EmptyDequeTests(String name) {
		super(name);
	}

	public void testEnqueueTail() {
		Deque<String> deque = DequeTools.emptyDeque();
		boolean exCaught = false;
		try {
			deque.enqueueTail("junk");
			fail();
		} catch (UnsupportedOperationException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testEnqueueHead() {
		Deque<String> deque = EmptyDeque.<String>instance();
		boolean exCaught = false;
		try {
			deque.enqueueHead("junk");
			fail();
		} catch (UnsupportedOperationException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testDequeueHead() {
		Deque<String> deque = EmptyDeque.<String>instance();
		boolean exCaught = false;
		try {
			String bogus = deque.dequeueHead();
			fail(bogus);
		} catch (NoSuchElementException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testDequeueTail() {
		Deque<String> deque = EmptyDeque.<String>instance();
		boolean exCaught = false;
		try {
			String bogus = deque.dequeueTail();
			fail(bogus);
		} catch (NoSuchElementException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testPeekHead() {
		Deque<String> deque = EmptyDeque.<String>instance();
		boolean exCaught = false;
		try {
			String bogus = deque.peekHead();
			fail(bogus);
		} catch (NoSuchElementException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testPeekTail() {
		Deque<String> deque = EmptyDeque.<String>instance();
		boolean exCaught = false;
		try {
			String bogus = deque.peekTail();
			fail(bogus);
		} catch (NoSuchElementException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testIsEmpty() {
		Deque<String> deque = EmptyDeque.<String>instance();
		assertTrue(deque.isEmpty());
	}

	public void testToString() {
		Deque<String> deque = EmptyDeque.<String>instance();
		assertEquals("[]", deque.toString());
	}

	public void testSerialization() throws Exception {
		Deque<String> deque = EmptyDeque.<String>instance();
		assertSame(deque, TestTools.serialize(deque));
	}
}

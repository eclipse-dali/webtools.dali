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

import java.util.NoSuchElementException;
import org.eclipse.jpt.common.utility.internal.queue.EmptyQueue;
import org.eclipse.jpt.common.utility.queue.Queue;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;
import junit.framework.TestCase;

@SuppressWarnings("nls")
public class EmptyQueueTests
	extends TestCase
{
	public EmptyQueueTests(String name) {
		super(name);
	}

	public void testEnqueue() {
		Queue<String> queue = EmptyQueue.<String>instance();
		boolean exCaught = false;
		try {
			queue.enqueue("junk");
			fail();
		} catch (UnsupportedOperationException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testDequeue() {
		Queue<String> queue = EmptyQueue.<String>instance();
		boolean exCaught = false;
		try {
			String bogus = queue.dequeue();
			fail(bogus);
		} catch (NoSuchElementException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testPeek() {
		Queue<String> queue = EmptyQueue.<String>instance();
		boolean exCaught = false;
		try {
			String bogus = queue.peek();
			fail(bogus);
		} catch (NoSuchElementException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testIsEmpty() {
		Queue<String> queue = EmptyQueue.<String>instance();
		assertTrue(queue.isEmpty());
	}

	public void testToString() {
		Queue<String> queue = EmptyQueue.<String>instance();
		assertEquals("[]", queue.toString());
	}

	public void testSerialization() throws Exception {
		Queue<String> queue = EmptyQueue.<String>instance();
		assertSame(queue, TestTools.serialize(queue));
	}
}

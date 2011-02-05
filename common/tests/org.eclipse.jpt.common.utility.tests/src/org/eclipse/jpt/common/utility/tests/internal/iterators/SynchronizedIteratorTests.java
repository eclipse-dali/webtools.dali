/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.iterators;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterators.SynchronizedIterator;
import org.eclipse.jpt.common.utility.tests.internal.MultiThreadedTestCase;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;

@SuppressWarnings("nls")
public class SynchronizedIteratorTests
	extends MultiThreadedTestCase
{
	public SynchronizedIteratorTests(String name) {
		super(name);
	}

	/**
	 * test that an unsynchronized iterator will produce corrupt output;
	 * thread 1 will read the first element from the iterator
	 * and then sleep for a bit, allowing thread 2 to sneak in and
	 * read the same element from the iterator
	 */
	public void testUnsynchronizedNext() throws Exception {
		TestIterator<String> iterator = this.buildTestIterator(TWO_TICKS);

		NextTestRunnable<String> runnable1 = new NextTestRunnable<String>(iterator);
		Thread thread1 = this.buildThread(runnable1);
		NextTestRunnable<String> runnable2 = new NextTestRunnable<String>(iterator);
		Thread thread2 = this.buildThread(runnable2);
		iterator.slowThread = thread1;

		thread1.start();

		// allow thread 1 to read the first element and get bogged down
		this.sleep(TICK);
		thread2.start();

		// wait for the threads to finish
		thread1.join();
		thread2.join();

		// both threads should have read the same element from the iterator :-(
		assertEquals("foo", runnable1.next);
		assertEquals("foo", runnable2.next);
	}

	/**
	 * test that a synchronized iterator will produce valid output;
	 * thread 1 will read the first element from the iterator
	 * and then sleep for a bit, but thread 2 will be locked out and
	 * wait to read the second element from the iterator
	 */
	public void testSynchronizedNext() throws Exception {
		TestIterator<String> nestedIterator = this.buildTestIterator(TWO_TICKS);
		Iterator<String> iterator = this.buildSynchronizedIterator(nestedIterator);

		NextTestRunnable<String> runnable1 = new NextTestRunnable<String>(iterator);
		Thread thread1 = this.buildThread(runnable1);
		NextTestRunnable<String> runnable2 = new NextTestRunnable<String>(iterator);
		Thread thread2 = this.buildThread(runnable2);
		nestedIterator.slowThread = thread1;

		thread1.start();

		// allow thread 1 to read the first element and get bogged down
		this.sleep(TICK);
		thread2.start();

		// wait for the threads to finish
		thread1.join();
		thread2.join();

		// the threads should have read the correct elements from the iterator :-)
		assertEquals("foo", runnable1.next);
		assertEquals("bar", runnable2.next);
	}

	public void testUnsynchronizedHasNext() throws Exception {
		TestIterator<String> iterator = this.buildTestIterator(TWO_TICKS);
		iterator.next();
		iterator.next();

		NextTestRunnable<String> runnable1 = new NextTestRunnable<String>(iterator);
		Thread thread1 = this.buildThread(runnable1);
		HasNextTestRunnable<String> runnable2 = new HasNextTestRunnable<String>(iterator);
		Thread thread2 = this.buildThread(runnable2);
		iterator.slowThread = thread1;

		thread1.start();

		// allow thread 1 to read the first element and get bogged down
		this.sleep(TICK);
		thread2.start();

		// wait for the threads to finish
		thread1.join();
		thread2.join();

		// thread 1 will have the last element,
		// but thread 2 will think there are more elements on the iterator :-(
		assertEquals("baz", runnable1.next);
		assertEquals(true, runnable2.hasNext);
	}

	public void testSynchronizedHasNext() throws Exception {
		TestIterator<String> nestedIterator = this.buildTestIterator(TWO_TICKS);
		Iterator<String> iterator = this.buildSynchronizedIterator(nestedIterator);
		iterator.next();
		iterator.next();

		NextTestRunnable<String> runnable1 = new NextTestRunnable<String>(iterator);
		Thread thread1 = this.buildThread(runnable1);
		HasNextTestRunnable<String> runnable2 = new HasNextTestRunnable<String>(iterator);
		Thread thread2 = this.buildThread(runnable2);
		nestedIterator.slowThread = thread1;

		thread1.start();

		// allow thread 1 to read the first element and get bogged down
		this.sleep(TICK);
		thread2.start();

		// wait for the threads to finish
		thread1.join();
		thread2.join();

		// thread 1 will have the last element,
		// and thread 2 will think there are no more elements on the iterator :-)
		assertEquals("baz", runnable1.next);
		assertEquals(false, runnable2.hasNext);
	}

	public void testUnsynchronizedRemove() throws Exception {
		TestIterator<String> iterator = this.buildTestIterator(TWO_TICKS);
		iterator.next();

		NextTestRunnable<String> runnable1 = new NextTestRunnable<String>(iterator);
		Thread thread1 = this.buildThread(runnable1);
		RemoveTestRunnable<String> runnable2 = new RemoveTestRunnable<String>(iterator);
		Thread thread2 = this.buildThread(runnable2);
		iterator.slowThread = thread1;

		thread1.start();

		// allow thread 1 to read the first element and get bogged down
		thread2.start();

		// wait for the threads to finish
		thread1.join();
		thread2.join();

		// the wrong element was removed :-(
		assertEquals("bar", runnable1.next);
		assertFalse(iterator.list.contains("foo"));
		assertTrue(iterator.list.contains("bar"));
		assertTrue(iterator.list.contains("baz"));
	}

	public void testSynchronizedRemove() throws Exception {
		TestIterator<String> nestedIterator = this.buildTestIterator(TWO_TICKS);
		Iterator<String> iterator = this.buildSynchronizedIterator(nestedIterator);
		iterator.next();

		NextTestRunnable<String> runnable1 = new NextTestRunnable<String>(iterator);
		Thread thread1 = this.buildThread(runnable1);
		RemoveTestRunnable<String> runnable2 = new RemoveTestRunnable<String>(iterator);
		Thread thread2 = this.buildThread(runnable2);
		nestedIterator.slowThread = thread1;

		thread1.start();

		// allow thread 1 to read the first element and get bogged down
		this.sleep(TICK);
		thread2.start();

		// wait for the threads to finish
		thread1.join();
		thread2.join();

		// the correct element was removed :-)
		assertEquals("bar", runnable1.next);
		assertTrue(nestedIterator.list.contains("foo"));
		assertFalse(nestedIterator.list.contains("bar"));
		assertTrue(nestedIterator.list.contains("baz"));
	}

	TestIterator<String> buildTestIterator(long delay) {
		return new TestIterator<String>(delay, this.buildArray());
	}

	String[] buildArray() {
		return new String[] {"foo", "bar", "baz"};
	}

	Iterator<String> buildSynchronizedIterator(Iterator<String> nestedIterator) {
		return new SynchronizedIterator<String>(nestedIterator);
	}


	/**
	 * next runnable
	 */
	class NextTestRunnable<E> implements Runnable {
		final Iterator<E> iterator;
		E next;

		NextTestRunnable(Iterator<E> iterator) {
			super();
			this.iterator = iterator;
		}

		public void run() {
			this.next = this.iterator.next();
		}

	}

	/**
	 * has next runnable
	 */
	class HasNextTestRunnable<E> implements Runnable {
		final Iterator<E> iterator;
		boolean hasNext;

		HasNextTestRunnable(Iterator<E> iterator) {
			super();
			this.iterator = iterator;
		}

		public void run() {
			this.hasNext = this.iterator.hasNext();
		}

	}

	/**
	 * remove runnable
	 */
	class RemoveTestRunnable<E> implements Runnable {
		final Iterator<E> iterator;

		RemoveTestRunnable(Iterator<E> iterator) {
			super();
			this.iterator = iterator;
		}

		public void run() {
			this.iterator.remove();
		}

	}

	/**
	 * Test iterator: If {@link #next()} is called while executing on the
	 * {@link slowThread}, the iterator will delay for the configured time.
	 */
	static class TestIterator<E> implements Iterator<E> {
		final long delay;
		final ArrayList<E> list = new ArrayList<E>();
		int nextIndex = 0;
		int lastIndex = -1;
		Thread slowThread;

		TestIterator(long delay, E... array) {
			super();
			this.delay = delay;
			CollectionTools.addAll(this.list, array);
		}

		public boolean hasNext() {
			return this.nextIndex != this.list.size();
		}

		public E next() {
			if (this.hasNext()) {
				E next = this.list.get(this.nextIndex);
				if (Thread.currentThread() == this.slowThread) {
					TestTools.sleep(this.delay);
				}
				this.lastIndex = this.nextIndex++;
				return next;
			}
			throw new NoSuchElementException();
		}

		public void remove() {
			if (this.lastIndex == -1) {
				throw new IllegalStateException();
			}
			this.list.remove(this.lastIndex);
			if (this.lastIndex < this.nextIndex) {  // check necessary for ListIterator
				this.nextIndex--;
			}
			this.lastIndex = -1;
		}
	}

}

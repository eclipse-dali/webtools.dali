/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.tests.internal.iterators;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

import junit.framework.TestCase;

import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.SynchronizedIterator;

@SuppressWarnings("nls")
public class SynchronizedIteratorTests extends TestCase {

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
		TestIterator<String> iterator = this.buildNestedIterator();

		NextTestThread<String> thread1 = new NextTestThread<String>(iterator);
		NextTestThread<String> thread2 = new NextTestThread<String>(iterator);
		iterator.slowThread = thread1;

		thread1.start();

		// allow thread 1 to read the first element and get bogged down
		sleep(100);
		thread2.start();

		// wait for the threads to finish
		thread1.join();
		thread2.join();

		// both threads should have read the same element from the iterator :-(
		assertEquals("foo", thread1.next);
		assertEquals("foo", thread2.next);
	}

	/**
	 * test that a synchronized iterator will produce valid output;
	 * thread 1 will read the first element from the iterator
	 * and then sleep for a bit, but thread 2 will be locked out and
	 * wait to read the second element from the iterator
	 */
	public void testSynchronizedNext() throws Exception {
		TestIterator<String> nestedIterator = this.buildNestedIterator();
		Iterator<String> iterator = this.buildSynchronizedIterator(nestedIterator);

		NextTestThread<String> thread1 = new NextTestThread<String>(iterator);
		NextTestThread<String> thread2 = new NextTestThread<String>(iterator);
		nestedIterator.slowThread = thread1;

		thread1.start();

		// allow thread 1 to read the first element and get bogged down
		sleep(100);
		thread2.start();

		// wait for the threads to finish
		thread1.join();
		thread2.join();

		// the threads should have read the correct elements from the iterator :-)
		assertEquals("foo", thread1.next);
		assertEquals("bar", thread2.next);
	}

	public void testUnsynchronizedHasNext() throws Exception {
		TestIterator<String> iterator = this.buildNestedIterator();
		iterator.next();
		iterator.next();

		NextTestThread<String> thread1 = new NextTestThread<String>(iterator);
		HasNextTestThread<String> thread2 = new HasNextTestThread<String>(iterator);
		iterator.slowThread = thread1;

		thread1.start();

		// allow thread 1 to read the first element and get bogged down
		sleep(100);
		thread2.start();

		// wait for the threads to finish
		thread1.join();
		thread2.join();

		// thread 1 will have the last element,
		// but thread 2 will think there are more elements on the iterator :-(
		assertEquals("baz", thread1.next);
		assertEquals(true, thread2.hasNext);
	}

	public void testSynchronizedHasNext() throws Exception {
		TestIterator<String> nestedIterator = this.buildNestedIterator();
		Iterator<String> iterator = this.buildSynchronizedIterator(nestedIterator);
		iterator.next();
		iterator.next();

		NextTestThread<String> thread1 = new NextTestThread<String>(iterator);
		HasNextTestThread<String> thread2 = new HasNextTestThread<String>(iterator);
		nestedIterator.slowThread = thread1;

		thread1.start();

		// allow thread 1 to read the first element and get bogged down
		sleep(100);
		thread2.start();

		// wait for the threads to finish
		thread1.join();
		thread2.join();

		// thread 1 will have the last element,
		// and thread 2 will think there are no more elements on the iterator :-)
		assertEquals("baz", thread1.next);
		assertEquals(false, thread2.hasNext);
	}

	public void testUnsynchronizedRemove() throws Exception {
		TestIterator<String> iterator = this.buildNestedIterator();
		iterator.next();

		NextTestThread<String> thread1 = new NextTestThread<String>(iterator);
		RemoveTestThread<String> thread2 = new RemoveTestThread<String>(iterator);
		iterator.slowThread = thread1;

		thread1.start();

		// allow thread 1 to read the first element and get bogged down
		sleep(100);
		thread2.start();

		// wait for the threads to finish
		thread1.join();
		thread2.join();

		// the wrong element was removed :-(
		assertEquals("bar", thread1.next);
		assertFalse(iterator.list.contains("foo"));
		assertTrue(iterator.list.contains("bar"));
		assertTrue(iterator.list.contains("baz"));
	}

	public void testSynchronizedRemove() throws Exception {
		TestIterator<String> nestedIterator = this.buildNestedIterator();
		Iterator<String> iterator = this.buildSynchronizedIterator(nestedIterator);
		iterator.next();

		NextTestThread<String> thread1 = new NextTestThread<String>(iterator);
		RemoveTestThread<String> thread2 = new RemoveTestThread<String>(iterator);
		nestedIterator.slowThread = thread1;

		thread1.start();

		// allow thread 1 to read the first element and get bogged down
		sleep(100);
		thread2.start();

		// wait for the threads to finish
		thread1.join();
		thread2.join();

		// the wrong element was removed :-(
		assertEquals("bar", thread1.next);
		assertTrue(nestedIterator.list.contains("foo"));
		assertFalse(nestedIterator.list.contains("bar"));
		assertTrue(nestedIterator.list.contains("baz"));
	}

	TestIterator<String> buildNestedIterator() {
		return new TestIterator<String>(this.buildArray());
	}

	String[] buildArray() {
		return new String[] {"foo", "bar", "baz"};
	}

	Iterator<String> buildSynchronizedIterator(Iterator<String> nestedIterator) {
		return new SynchronizedIterator<String>(nestedIterator);
	}


	/**
	 * next thread
	 */
	class NextTestThread<E> extends Thread {
		final Iterator<E> iterator;
		E next;

		NextTestThread(Iterator<E> iterator) {
			super();
			this.iterator = iterator;
		}

		@Override
		public void run() {
			this.next = this.iterator.next();
		}

	}

	/**
	 * has next thread
	 */
	class HasNextTestThread<E> extends Thread {
		final Iterator<E> iterator;
		boolean hasNext;

		HasNextTestThread(Iterator<E> iterator) {
			super();
			this.iterator = iterator;
		}

		@Override
		public void run() {
			this.hasNext = this.iterator.hasNext();
		}

	}

	/**
	 * remove thread
	 */
	class RemoveTestThread<E> extends Thread {
		final Iterator<E> iterator;

		RemoveTestThread(Iterator<E> iterator) {
			super();
			this.iterator = iterator;
		}

		@Override
		public void run() {
			this.iterator.remove();
		}

	}

	/**
	 * test iterator
	 */
	class TestIterator<E> implements Iterator<E> {
		final ArrayList<E> list;
		int nextIndex;
		int lastIndex = -1;
		Thread slowThread;

		TestIterator(E... array) {
			super();
			this.list = new ArrayList<E>();
			CollectionTools.addAll(this.list, array);
			this.nextIndex = 0;
		}

		public boolean hasNext() {
			return this.nextIndex != this.list.size();
		}

		public E next() {
			if (this.hasNext()) {
				E next = this.list.get(this.nextIndex);
				this.sleep();
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

		void sleep() {
			if (Thread.currentThread() == this.slowThread) {
				SynchronizedIteratorTests.sleep(200);
			}
		}

	}

	static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException ex) {
			throw new RuntimeException(ex);
		}
	}

}

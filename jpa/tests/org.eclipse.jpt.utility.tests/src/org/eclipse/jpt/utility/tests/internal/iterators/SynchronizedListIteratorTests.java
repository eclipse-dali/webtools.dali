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

import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

import org.eclipse.jpt.utility.internal.iterators.SynchronizedListIterator;

@SuppressWarnings("nls")
public class SynchronizedListIteratorTests extends SynchronizedIteratorTests {

	public SynchronizedListIteratorTests(String name) {
		super(name);
	}

	public void testUnsynchronizedPrevious() throws Exception {
		TestListIterator<String> iterator = this.buildNestedIterator();
		iterator.next();
		iterator.next();

		PreviousTestThread<String> thread1 = new PreviousTestThread<String>(iterator);
		PreviousTestThread<String> thread2 = new PreviousTestThread<String>(iterator);
		iterator.slowThread = thread1;

		thread1.start();

		// allow thread 1 to read the first element and get bogged down
		sleep(100);
		thread2.start();

		// wait for the threads to finish
		thread1.join();
		thread2.join();

		// both threads should have read the same element from the iterator :-(
		assertEquals("bar", thread1.previous);
		assertEquals("bar", thread2.previous);
	}

	public void testSynchronizedPrevious() throws Exception {
		TestListIterator<String> nestedIterator = this.buildNestedIterator();
		ListIterator<String> iterator = this.buildSynchronizedIterator(nestedIterator);
		iterator.next();
		iterator.next();

		PreviousTestThread<String> thread1 = new PreviousTestThread<String>(iterator);
		PreviousTestThread<String> thread2 = new PreviousTestThread<String>(iterator);
		nestedIterator.slowThread = thread1;

		thread1.start();

		// allow thread 1 to read the first element and get bogged down
		sleep(100);
		thread2.start();

		// wait for the threads to finish
		thread1.join();
		thread2.join();

		// the threads should have read the correct elements from the iterator :-)
		assertEquals("bar", thread1.previous);
		assertEquals("foo", thread2.previous);
	}

	public void testUnsynchronizedHasPrevious() throws Exception {
		TestListIterator<String> iterator = this.buildNestedIterator();
		iterator.next();

		PreviousTestThread<String> thread1 = new PreviousTestThread<String>(iterator);
		HasPreviousTestThread<String> thread2 = new HasPreviousTestThread<String>(iterator);
		iterator.slowThread = thread1;

		thread1.start();

		// allow thread 1 to read the first element and get bogged down
		sleep(100);
		thread2.start();

		// wait for the threads to finish
		thread1.join();
		thread2.join();

		// thread 1 will have the first element,
		// but thread 2 will think there are more "previous" elements on the iterator :-(
		assertEquals("foo", thread1.previous);
		assertEquals(true, thread2.hasPrevious);
	}

	public void testSynchronizedHasPrevious() throws Exception {
		TestListIterator<String> nestedIterator = this.buildNestedIterator();
		ListIterator<String> iterator = this.buildSynchronizedIterator(nestedIterator);
		iterator.next();

		PreviousTestThread<String> thread1 = new PreviousTestThread<String>(iterator);
		HasPreviousTestThread<String> thread2 = new HasPreviousTestThread<String>(iterator);
		nestedIterator.slowThread = thread1;

		thread1.start();

		// allow thread 1 to read the first element and get bogged down
		sleep(100);
		thread2.start();

		// wait for the threads to finish
		thread1.join();
		thread2.join();

		// thread 1 will have the first element,
		// and thread 2 will think there are no more "previous" elements on the iterator :-)
		assertEquals("foo", thread1.previous);
		assertEquals(false, thread2.hasPrevious);
	}

	public void testUnsynchronizedNextIndex() throws Exception {
		TestListIterator<String> iterator = this.buildNestedIterator();

		NextTestThread<String> thread1 = new NextTestThread<String>(iterator);
		NextIndexTestThread<String> thread2 = new NextIndexTestThread<String>(iterator);
		iterator.slowThread = thread1;

		thread1.start();

		// allow thread 1 to read the first element and get bogged down
		sleep(100);
		thread2.start();

		// wait for the threads to finish
		thread1.join();
		thread2.join();

		// thread 1 will have the first element,
		// but thread 2 will think the next index is still 0 :-(
		assertEquals("foo", thread1.next);
		assertEquals(0, thread2.nextIndex);
	}

	public void testSynchronizedNextIndex() throws Exception {
		TestListIterator<String> nestedIterator = this.buildNestedIterator();
		ListIterator<String> iterator = this.buildSynchronizedIterator(nestedIterator);

		NextTestThread<String> thread1 = new NextTestThread<String>(iterator);
		NextIndexTestThread<String> thread2 = new NextIndexTestThread<String>(iterator);
		nestedIterator.slowThread = thread1;

		thread1.start();

		// allow thread 1 to read the first element and get bogged down
		sleep(100);
		thread2.start();

		// wait for the threads to finish
		thread1.join();
		thread2.join();

		// thread 1 will have the first element,
		// and thread 2 will think the next index is 1 :-)
		assertEquals("foo", thread1.next);
		assertEquals(1, thread2.nextIndex);
	}

	public void testUnsynchronizedPreviousIndex() throws Exception {
		TestListIterator<String> iterator = this.buildNestedIterator();
		iterator.next();

		PreviousTestThread<String> thread1 = new PreviousTestThread<String>(iterator);
		PreviousIndexTestThread<String> thread2 = new PreviousIndexTestThread<String>(iterator);
		iterator.slowThread = thread1;

		thread1.start();

		// allow thread 1 to read the first element and get bogged down
		sleep(100);
		thread2.start();

		// wait for the threads to finish
		thread1.join();
		thread2.join();

		// thread 1 will have the first element,
		// but thread 2 will think the next index is still 0 :-(
		assertEquals("foo", thread1.previous);
		assertEquals(0, thread2.previousIndex);
	}

	public void testSynchronizedPreviousIndex() throws Exception {
		TestListIterator<String> nestedIterator = this.buildNestedIterator();
		ListIterator<String> iterator = this.buildSynchronizedIterator(nestedIterator);
		iterator.next();

		PreviousTestThread<String> thread1 = new PreviousTestThread<String>(iterator);
		PreviousIndexTestThread<String> thread2 = new PreviousIndexTestThread<String>(iterator);
		nestedIterator.slowThread = thread1;

		thread1.start();

		// allow thread 1 to read the first element and get bogged down
		sleep(100);
		thread2.start();

		// wait for the threads to finish
		thread1.join();
		thread2.join();

		// thread 1 will have the first element,
		// and thread 2 will think the next index is -1 :-)
		assertEquals("foo", thread1.previous);
		assertEquals(-1, thread2.previousIndex);
	}

	public void testUnsynchronizedSet() throws Exception {
		TestListIterator<String> iterator = this.buildNestedIterator();
		iterator.next();

		NextTestThread<String> thread1 = new NextTestThread<String>(iterator);
		SetTestThread<String> thread2 = new SetTestThread<String>(iterator, "xxx");
		iterator.slowThread = thread1;

		thread1.start();

		// allow thread 1 to read the first element and get bogged down
		sleep(100);
		thread2.start();

		// wait for the threads to finish
		thread1.join();
		thread2.join();

		// the wrong element was set :-(
		assertEquals("bar", thread1.next);
		assertFalse(iterator.list.contains("foo"));
		assertTrue(iterator.list.contains("xxx"));
		assertTrue(iterator.list.contains("bar"));
		assertTrue(iterator.list.contains("baz"));
	}

	public void testSynchronizedSet() throws Exception {
		TestListIterator<String> nestedIterator = this.buildNestedIterator();
		ListIterator<String> iterator = this.buildSynchronizedIterator(nestedIterator);
		iterator.next();

		NextTestThread<String> thread1 = new NextTestThread<String>(iterator);
		SetTestThread<String> thread2 = new SetTestThread<String>(iterator, "xxx");
		nestedIterator.slowThread = thread1;

		thread1.start();

		// allow thread 1 to read the first element and get bogged down
		sleep(100);
		thread2.start();

		// wait for the threads to finish
		thread1.join();
		thread2.join();

		// the right element was set :-)
		assertEquals("bar", thread1.next);
		assertTrue(nestedIterator.list.contains("foo"));
		assertFalse(nestedIterator.list.contains("bar"));
		assertTrue(nestedIterator.list.contains("xxx"));
		assertTrue(nestedIterator.list.contains("baz"));
	}

	public void testUnsynchronizedAdd() throws Exception {
		TestListIterator<String> iterator = this.buildNestedIterator();
		iterator.next();

		NextTestThread<String> thread1 = new NextTestThread<String>(iterator);
		AddTestThread<String> thread2 = new AddTestThread<String>(iterator, "xxx");
		iterator.slowThread = thread1;

		thread1.start();

		// allow thread 1 to read the first element and get bogged down
		sleep(100);
		thread2.start();

		// wait for the threads to finish
		thread1.join();
		thread2.join();

		// the element was added at the wrong index :-(
		assertEquals("bar", thread1.next);
		assertTrue(iterator.list.contains("foo"));
		assertEquals(0, iterator.list.indexOf("xxx"));
		assertTrue(iterator.list.contains("xxx"));
		assertTrue(iterator.list.contains("bar"));
		assertTrue(iterator.list.contains("baz"));
	}

	public void testSynchronizedAdd() throws Exception {
		TestListIterator<String> nestedIterator = this.buildNestedIterator();
		ListIterator<String> iterator = this.buildSynchronizedIterator(nestedIterator);
		iterator.next();

		NextTestThread<String> thread1 = new NextTestThread<String>(iterator);
		AddTestThread<String> thread2 = new AddTestThread<String>(iterator, "xxx");
		nestedIterator.slowThread = thread1;

		thread1.start();

		// allow thread 1 to read the first element and get bogged down
		sleep(100);
		thread2.start();

		// wait for the threads to finish
		thread1.join();
		thread2.join();

		// the element was added at the correct index :-)
		assertEquals("bar", thread1.next);
		assertTrue(nestedIterator.list.contains("foo"));
		assertEquals(1, nestedIterator.list.indexOf("xxx"));
		assertTrue(nestedIterator.list.contains("xxx"));
		assertTrue(nestedIterator.list.contains("bar"));
		assertTrue(nestedIterator.list.contains("baz"));
	}

	@Override
	ListIterator<String> buildSynchronizedIterator(Iterator<String> nestedIterator) {
		return new SynchronizedListIterator<String>((ListIterator<String>) nestedIterator);
	}

	@Override
	TestListIterator<String> buildNestedIterator() {
		return new TestListIterator<String>(this.buildArray());
	}

	/**
	 * previous thread
	 */
	class PreviousTestThread<E> extends Thread {
		final ListIterator<E> iterator;
		E previous;

		PreviousTestThread(ListIterator<E> iterator) {
			super();
			this.iterator = iterator;
		}

		@Override
		public void run() {
			this.previous = this.iterator.previous();
		}

	}

	/**
	 * has previous thread
	 */
	class HasPreviousTestThread<E> extends Thread {
		final ListIterator<E> iterator;
		boolean hasPrevious;

		HasPreviousTestThread(ListIterator<E> iterator) {
			super();
			this.iterator = iterator;
		}

		@Override
		public void run() {
			this.hasPrevious = this.iterator.hasPrevious();
		}

	}

	/**
	 * next index thread
	 */
	class NextIndexTestThread<E> extends Thread {
		final ListIterator<E> iterator;
		int nextIndex;

		NextIndexTestThread(ListIterator<E> iterator) {
			super();
			this.iterator = iterator;
		}

		@Override
		public void run() {
			this.nextIndex = this.iterator.nextIndex();
		}

	}

	/**
	 * previous index thread
	 */
	class PreviousIndexTestThread<E> extends Thread {
		final ListIterator<E> iterator;
		int previousIndex;

		PreviousIndexTestThread(ListIterator<E> iterator) {
			super();
			this.iterator = iterator;
		}

		@Override
		public void run() {
			this.previousIndex = this.iterator.previousIndex();
		}

	}

	/**
	 * set thread
	 */
	class SetTestThread<E> extends Thread {
		final ListIterator<E> iterator;
		final E element;

		SetTestThread(ListIterator<E> iterator, E element) {
			super();
			this.iterator = iterator;
			this.element = element;
		}

		@Override
		public void run() {
			this.iterator.set(this.element);
		}

	}

	/**
	 * add thread
	 */
	class AddTestThread<E> extends Thread {
		final ListIterator<E> iterator;
		final E element;

		AddTestThread(ListIterator<E> iterator, E element) {
			super();
			this.iterator = iterator;
			this.element = element;
		}

		@Override
		public void run() {
			this.iterator.add(this.element);
		}

	}

	/**
	 * test list iterator
	 */
	class TestListIterator<E> extends TestIterator<E> implements ListIterator<E> {

		TestListIterator(E... array) {
			super(array);
		}

		public int nextIndex() {
			return this.nextIndex;
		}

		public boolean hasPrevious() {
			return this.nextIndex != 0;
		}

		public E previous() {
			if (this.hasPrevious()) {
				E previous = this.list.get(this.previousIndex());
				sleep();
				this.nextIndex--;
				this.lastIndex = this.nextIndex;
				return previous;
			}
			throw new NoSuchElementException();
		}

		public int previousIndex() {
			return this.nextIndex - 1;
		}

		public void set(E e) {
			if (this.lastIndex == -1) {
				throw new IllegalStateException();
			}
			this.list.set(this.lastIndex, e);
		}

		public void add(E e) {
			this.list.add(this.lastIndex, e);
			this.lastIndex++;
			this.lastIndex = -1;
		}

	}

}

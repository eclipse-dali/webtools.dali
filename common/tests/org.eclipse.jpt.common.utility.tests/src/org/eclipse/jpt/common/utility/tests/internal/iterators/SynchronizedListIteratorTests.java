/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.iterators;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

import org.eclipse.jpt.common.utility.internal.iterators.SynchronizedListIterator;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;

@SuppressWarnings("nls")
public class SynchronizedListIteratorTests
	extends SynchronizedIteratorTests
{
	public SynchronizedListIteratorTests(String name) {
		super(name);
	}

	public void testUnsynchronizedPrevious() throws Exception {
		TestListIterator<String> iterator = this.buildTestIterator(TWO_TICKS);
		iterator.next();
		iterator.next();

		PreviousTestRunnable<String> runnable1 = new PreviousTestRunnable<String>(iterator);
		Thread thread1 = this.buildThread(runnable1);
		PreviousTestRunnable<String> runnable2 = new PreviousTestRunnable<String>(iterator);
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
		assertEquals("bar", runnable1.previous);
		assertEquals("bar", runnable2.previous);
	}

	public void testSynchronizedPrevious() throws Exception {
		TestListIterator<String> nestedIterator = this.buildTestIterator(TWO_TICKS);
		ListIterator<String> iterator = this.buildSynchronizedIterator(nestedIterator);
		iterator.next();
		iterator.next();

		PreviousTestRunnable<String> runnable1 = new PreviousTestRunnable<String>(iterator);
		Thread thread1 = this.buildThread(runnable1);
		PreviousTestRunnable<String> runnable2 = new PreviousTestRunnable<String>(iterator);
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
		assertEquals("bar", runnable1.previous);
		assertEquals("foo", runnable2.previous);
	}

	public void testUnsynchronizedHasPrevious() throws Exception {
		TestListIterator<String> iterator = this.buildTestIterator(TWO_TICKS);
		iterator.next();

		PreviousTestRunnable<String> runnable1 = new PreviousTestRunnable<String>(iterator);
		Thread thread1 = this.buildThread(runnable1);
		HasPreviousTestRunnable<String> runnable2 = new HasPreviousTestRunnable<String>(iterator);
		Thread thread2 = this.buildThread(runnable2);
		iterator.slowThread = thread1;

		thread1.start();

		// allow thread 1 to read the first element and get bogged down
		this.sleep(TICK);
		thread2.start();

		// wait for the threads to finish
		thread1.join();
		thread2.join();

		// thread 1 will have the first element,
		// but thread 2 will think there are more "previous" elements on the iterator :-(
		assertEquals("foo", runnable1.previous);
		assertEquals(true, runnable2.hasPrevious);
	}

	public void testSynchronizedHasPrevious() throws Exception {
		TestListIterator<String> nestedIterator = this.buildTestIterator(TWO_TICKS);
		ListIterator<String> iterator = this.buildSynchronizedIterator(nestedIterator);
		iterator.next();

		PreviousTestRunnable<String> runnable1 = new PreviousTestRunnable<String>(iterator);
		Thread thread1 = this.buildThread(runnable1);
		HasPreviousTestRunnable<String> runnable2 = new HasPreviousTestRunnable<String>(iterator);
		Thread thread2 = this.buildThread(runnable2);
		nestedIterator.slowThread = thread1;

		thread1.start();

		// allow thread 1 to read the first element and get bogged down
		this.sleep(TICK);
		thread2.start();

		// wait for the threads to finish
		thread1.join();
		thread2.join();

		// thread 1 will have the first element,
		// and thread 2 will think there are no more "previous" elements on the iterator :-)
		assertEquals("foo", runnable1.previous);
		assertEquals(false, runnable2.hasPrevious);
	}

	public void testUnsynchronizedNextIndex() throws Exception {
		TestListIterator<String> iterator = this.buildTestIterator(TWO_TICKS);

		NextTestRunnable<String> runnable1 = new NextTestRunnable<String>(iterator);
		Thread thread1 = this.buildThread(runnable1);
		NextIndexTestRunnable<String> runnable2 = new NextIndexTestRunnable<String>(iterator);
		Thread thread2 = this.buildThread(runnable2);
		iterator.slowThread = thread1;

		thread1.start();

		// allow thread 1 to read the first element and get bogged down
		this.sleep(TICK);
		thread2.start();

		// wait for the threads to finish
		thread1.join();
		thread2.join();

		// thread 1 will have the first element,
		// but thread 2 will think the next index is still 0 :-(
		assertEquals("foo", runnable1.next);
		assertEquals(0, runnable2.nextIndex);
	}

	public void testSynchronizedNextIndex() throws Exception {
		TestListIterator<String> nestedIterator = this.buildTestIterator(TWO_TICKS);
		ListIterator<String> iterator = this.buildSynchronizedIterator(nestedIterator);

		NextTestRunnable<String> runnable1 = new NextTestRunnable<String>(iterator);
		Thread thread1 = this.buildThread(runnable1);
		NextIndexTestRunnable<String> runnable2 = new NextIndexTestRunnable<String>(iterator);
		Thread thread2 = this.buildThread(runnable2);
		nestedIterator.slowThread = thread1;

		thread1.start();

		// allow thread 1 to read the first element and get bogged down
		this.sleep(TICK);
		thread2.start();

		// wait for the threads to finish
		thread1.join();
		thread2.join();

		// thread 1 will have the first element,
		// and thread 2 will think the next index is 1 :-)
		assertEquals("foo", runnable1.next);
		assertEquals(1, runnable2.nextIndex);
	}

	public void testUnsynchronizedPreviousIndex() throws Exception {
		TestListIterator<String> iterator = this.buildTestIterator(TWO_TICKS);
		iterator.next();

		PreviousTestRunnable<String> runnable1 = new PreviousTestRunnable<String>(iterator);
		Thread thread1 = this.buildThread(runnable1);
		PreviousIndexTestRunnable<String> runnable2 = new PreviousIndexTestRunnable<String>(iterator);
		Thread thread2 = this.buildThread(runnable2);
		iterator.slowThread = thread1;

		thread1.start();

		// allow thread 1 to read the first element and get bogged down
		this.sleep(TICK);
		thread2.start();

		// wait for the threads to finish
		thread1.join();
		thread2.join();

		// thread 1 will have the first element,
		// but thread 2 will think the next index is still 0 :-(
		assertEquals("foo", runnable1.previous);
		assertEquals(0, runnable2.previousIndex);
	}

	public void testSynchronizedPreviousIndex() throws Exception {
		TestListIterator<String> nestedIterator = this.buildTestIterator(TWO_TICKS);
		ListIterator<String> iterator = this.buildSynchronizedIterator(nestedIterator);
		iterator.next();

		PreviousTestRunnable<String> runnable1 = new PreviousTestRunnable<String>(iterator);
		Thread thread1 = this.buildThread(runnable1);
		PreviousIndexTestRunnable<String> runnable2 = new PreviousIndexTestRunnable<String>(iterator);
		Thread thread2 = this.buildThread(runnable2);
		nestedIterator.slowThread = thread1;

		thread1.start();

		// allow thread 1 to read the first element and get bogged down
		this.sleep(TICK);
		thread2.start();

		// wait for the threads to finish
		thread1.join();
		thread2.join();

		// thread 1 will have the first element,
		// and thread 2 will think the next index is -1 :-)
		assertEquals("foo", runnable1.previous);
		assertEquals(-1, runnable2.previousIndex);
	}

	public void testUnsynchronizedSet() throws Exception {
		TestListIterator<String> iterator = this.buildTestIterator(TWO_TICKS);
		iterator.next();

		NextTestRunnable<String> runnable1 = new NextTestRunnable<String>(iterator);
		Thread thread1 = this.buildThread(runnable1);
		SetTestRunnable<String> runnable2 = new SetTestRunnable<String>(iterator, "xxx");
		Thread thread2 = this.buildThread(runnable2);
		iterator.slowThread = thread1;

		thread1.start();

		// allow thread 1 to read the first element and get bogged down
		this.sleep(TICK);
		thread2.start();

		// wait for the threads to finish
		thread1.join();
		thread2.join();

		// the wrong element was set :-(
		assertEquals("bar", runnable1.next);
		assertFalse(iterator.list.contains("foo"));
		assertTrue(iterator.list.contains("xxx"));
		assertTrue(iterator.list.contains("bar"));
		assertTrue(iterator.list.contains("baz"));
	}

	public void testSynchronizedSet() throws Exception {
		TestListIterator<String> nestedIterator = this.buildTestIterator(TWO_TICKS);
		ListIterator<String> iterator = this.buildSynchronizedIterator(nestedIterator);
		iterator.next();

		NextTestRunnable<String> runnable1 = new NextTestRunnable<String>(iterator);
		Thread thread1 = this.buildThread(runnable1);
		SetTestRunnable<String> runnable2 = new SetTestRunnable<String>(iterator, "xxx");
		Thread thread2 = this.buildThread(runnable2);
		nestedIterator.slowThread = thread1;

		thread1.start();

		// allow thread 1 to read the first element and get bogged down
		this.sleep(TICK);
		thread2.start();

		// wait for the threads to finish
		thread1.join();
		thread2.join();

		// the right element was set :-)
		assertEquals("bar", runnable1.next);
		assertTrue(nestedIterator.list.contains("foo"));
		assertFalse(nestedIterator.list.contains("bar"));
		assertTrue(nestedIterator.list.contains("xxx"));
		assertTrue(nestedIterator.list.contains("baz"));
	}

	public void testUnsynchronizedAdd() throws Exception {
		TestListIterator<String> iterator = this.buildTestIterator(TWO_TICKS);
		iterator.next();

		NextTestRunnable<String> runnable1 = new NextTestRunnable<String>(iterator);
		Thread thread1 = this.buildThread(runnable1);
		AddTestRunnable<String> runnable2 = new AddTestRunnable<String>(iterator, "xxx");
		Thread thread2 = this.buildThread(runnable2);
		iterator.slowThread = thread1;

		thread1.start();

		// allow thread 1 to read the first element and get bogged down
		this.sleep(TICK);
		thread2.start();

		// wait for the threads to finish
		thread1.join();
		thread2.join();

		// the element was added at the wrong index :-(
		assertEquals("bar", runnable1.next);
		assertTrue(iterator.list.contains("foo"));
		assertEquals(0, iterator.list.indexOf("xxx"));
		assertTrue(iterator.list.contains("xxx"));
		assertTrue(iterator.list.contains("bar"));
		assertTrue(iterator.list.contains("baz"));
	}

	public void testSynchronizedAdd() throws Exception {
		TestListIterator<String> nestedIterator = this.buildTestIterator(TWO_TICKS);
		ListIterator<String> iterator = this.buildSynchronizedIterator(nestedIterator);
		iterator.next();

		NextTestRunnable<String> runnable1 = new NextTestRunnable<String>(iterator);
		Thread thread1 = this.buildThread(runnable1);
		AddTestRunnable<String> runnable2 = new AddTestRunnable<String>(iterator, "xxx");
		Thread thread2 = this.buildThread(runnable2);
		nestedIterator.slowThread = thread1;

		thread1.start();

		// allow thread 1 to read the first element and get bogged down
		this.sleep(TICK);
		thread2.start();

		// wait for the threads to finish
		thread1.join();
		thread2.join();

		// the element was added at the correct index :-)
		assertEquals("bar", runnable1.next);
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
	TestListIterator<String> buildTestIterator(long delay) {
		return new TestListIterator<String>(delay, this.buildArray());
	}

	/**
	 * previous runnable
	 */
	class PreviousTestRunnable<E> implements Runnable {
		final ListIterator<E> iterator;
		E previous;

		PreviousTestRunnable(ListIterator<E> iterator) {
			super();
			this.iterator = iterator;
		}

		public void run() {
			this.previous = this.iterator.previous();
		}

	}

	/**
	 * has previous runnable
	 */
	class HasPreviousTestRunnable<E> implements Runnable {
		final ListIterator<E> iterator;
		boolean hasPrevious;

		HasPreviousTestRunnable(ListIterator<E> iterator) {
			super();
			this.iterator = iterator;
		}

		public void run() {
			this.hasPrevious = this.iterator.hasPrevious();
		}

	}

	/**
	 * next index runnable
	 */
	class NextIndexTestRunnable<E> implements Runnable {
		final ListIterator<E> iterator;
		int nextIndex;

		NextIndexTestRunnable(ListIterator<E> iterator) {
			super();
			this.iterator = iterator;
		}

		public void run() {
			this.nextIndex = this.iterator.nextIndex();
		}

	}

	/**
	 * previous index runnable
	 */
	class PreviousIndexTestRunnable<E> implements Runnable {
		final ListIterator<E> iterator;
		int previousIndex;

		PreviousIndexTestRunnable(ListIterator<E> iterator) {
			super();
			this.iterator = iterator;
		}

		public void run() {
			this.previousIndex = this.iterator.previousIndex();
		}

	}

	/**
	 * set runnable
	 */
	class SetTestRunnable<E> implements Runnable {
		final ListIterator<E> iterator;
		final E element;

		SetTestRunnable(ListIterator<E> iterator, E element) {
			super();
			this.iterator = iterator;
			this.element = element;
		}

		public void run() {
			this.iterator.set(this.element);
		}

	}

	/**
	 * add runnable
	 */
	class AddTestRunnable<E> implements Runnable {
		final ListIterator<E> iterator;
		final E element;

		AddTestRunnable(ListIterator<E> iterator, E element) {
			super();
			this.iterator = iterator;
			this.element = element;
		}

		public void run() {
			this.iterator.add(this.element);
		}

	}

	/**
	 * Test iterator: If {@link #next()} or {@link #previous()} is called
	 * while executing on the {@link slowThread}, the iterator will delay
	 * for the configured time.
	 */
	static class TestListIterator<E> extends TestIterator<E> implements ListIterator<E> {

		TestListIterator(long delay, E... array) {
			super(delay, array);
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
				if (Thread.currentThread() == this.slowThread) {
					TestTools.sleep(this.delay);
				}
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

/*******************************************************************************
 * Copyright (c) 2005, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.iterators;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

import org.eclipse.jpt.common.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.common.utility.tests.internal.MultiThreadedTestCase;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;

@SuppressWarnings("nls")
public class CloneListIteratorTests
	extends MultiThreadedTestCase
{
	List<String> originalList;

	private List<String> concurrentList;

	public CloneListIteratorTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.originalList = this.buildList();
	}

	public void testHasNext() {
		int originalSize = this.originalList.size();
		int i = 0;
		for (ListIterator<String> stream = this.buildCloneListIterator(); stream.hasNext();) {
			stream.next();
			// should allow concurrent modification
			this.originalList.add("foo");
			i++;
		}
		assertTrue(originalSize != this.originalList.size());
		assertEquals(originalSize, i);
	}

	public void testNext() {
		ListIterator<String> nestedListIterator = this.buildNestedListIterator();
		for (ListIterator<String> stream = this.buildCloneListIterator(); stream.hasNext();) {
			assertEquals("bogus element", nestedListIterator.next(), stream.next());
		}
	}

	public void testIndex() {
		ListIterator<String> cloneListIterator = this.buildCloneListIterator();
		ListIterator<String> nestedListIterator = this.buildNestedListIterator();
		for (int i = 0; i < 7; i++) {
			nestedListIterator.next();
			cloneListIterator.next();
			assertEquals("bogus index", nestedListIterator.nextIndex(), cloneListIterator.nextIndex());
			assertEquals("bogus index", nestedListIterator.previousIndex(), cloneListIterator.previousIndex());
		}

		for (int i = 0; i < 3; i++) {
			nestedListIterator.previous();
			cloneListIterator.previous();
			assertEquals("bogus index", nestedListIterator.nextIndex(), cloneListIterator.nextIndex());
			assertEquals("bogus index", nestedListIterator.previousIndex(), cloneListIterator.previousIndex());
		}

		while (nestedListIterator.hasNext()) {
			nestedListIterator.next();
			cloneListIterator.next();
			assertEquals("bogus index", nestedListIterator.nextIndex(), cloneListIterator.nextIndex());
			assertEquals("bogus index", nestedListIterator.previousIndex(), cloneListIterator.previousIndex());
		}
	}

	public void testHasPrevious() {
		int originalSize = this.originalList.size();
		int i = 0;
		ListIterator<String> stream = this.buildCloneListIterator();
		while (stream.hasNext()) {
			stream.next();
			this.originalList.add("foo");
			i++;
		}
		assertTrue(originalSize != this.originalList.size());
		originalSize = this.originalList.size();
		while (stream.hasPrevious()) {
			stream.previous();
			// should allow concurrent modification
			this.originalList.add("bar");
			i--;
		}
		assertTrue(originalSize != this.originalList.size());
		assertEquals(0, i);
	}

	public void testPrevious() {
		ListIterator<String> nestedListIterator = this.buildNestedListIterator();
		ListIterator<String> stream = this.buildCloneListIterator();
		while (stream.hasNext()) {
			nestedListIterator.next();
			stream.next();
		}
		while (stream.hasPrevious()) {
			assertEquals("bogus element", nestedListIterator.previous(), stream.previous());
		}
	}

	public void testNoSuchElementException() {
		boolean exCaught = false;
		ListIterator<String> stream = this.buildCloneListIterator();
		String string = null;
		while (stream.hasNext()) {
			string = stream.next();
		}
		try {
			string = stream.next();
		} catch (NoSuchElementException ex) {
			exCaught = true;
		}
		assertTrue("NoSuchElementException not thrown: " + string, exCaught);

		exCaught = false;
		while (stream.hasPrevious()) {
			string = stream.previous();
		}
		try {
			string = stream.previous();
		} catch (NoSuchElementException ex) {
			exCaught = true;
		}
		assertTrue("NoSuchElementException not thrown: " + string, exCaught);
	}

	public void testModifyDefault() {
		boolean exCaught = false;
		for (ListIterator<String> stream = this.buildCloneListIterator(); stream.hasNext();) {
			if (stream.next().equals("three")) {
				try {
					stream.remove();
				} catch (UnsupportedOperationException ex) {
					exCaught = true;
				}
			}
		}
		assertTrue("UnsupportedOperationException not thrown", exCaught);

		exCaught = false;
		for (ListIterator<String> stream = this.buildCloneListIterator(); stream.hasNext();) {
			if (stream.next().equals("three")) {
				try {
					stream.add("three and a half");
				} catch (UnsupportedOperationException ex) {
					exCaught = true;
				}
			}
		}
		assertTrue("UnsupportedOperationException not thrown", exCaught);

		exCaught = false;
		for (ListIterator<String> stream = this.buildCloneListIterator(); stream.hasNext();) {
			if (stream.next().equals("three")) {
				try {
					stream.set("another three");
				} catch (UnsupportedOperationException ex) {
					exCaught = true;
				}
			}
		}
		assertTrue("UnsupportedOperationException not thrown", exCaught);
	}

	public void testModifyMutatorNext() {
		this.verifyModifyNext(new CloneListIterator<String>(this.originalList, this.buildMutator()));
	}

	public void testModifyMutatorPrevious() {
		this.verifyModifyPrevious(new CloneListIterator<String>(this.originalList, this.buildMutator()));
	}

	private CloneListIterator.Mutator<String> buildMutator() {
		return new CloneListIterator.Mutator<String>() {
			public void add(int index, String o) {
				CloneListIteratorTests.this.originalList.add(index, o);
			}

			public void remove(int index) {
				CloneListIteratorTests.this.originalList.remove(index);
			}

			public void set(int index, String o) {
				CloneListIteratorTests.this.originalList.set(index, o);
			}
		};
	}

	public void testModifySubclassNext() {
		this.verifyModifyNext(this.buildSubclass());
	}

	public void testModifySubclassPrevious() {
		this.verifyModifyPrevious(this.buildSubclass());
	}

	private ListIterator<String> buildSubclass() {
		return new CloneListIterator<String>(this.originalList) {
			@Override
			protected void add(int currentIndex, String o) {
				CloneListIteratorTests.this.originalList.add(currentIndex, o);
			}

			@Override
			protected void remove(int currentIndex) {
				CloneListIteratorTests.this.originalList.remove(currentIndex);
			}

			@Override
			protected void set(int currentIndex, String o) {
				CloneListIteratorTests.this.originalList.set(currentIndex, o);
			}
		};
	}

	private void verifyModifyNext(ListIterator<String> iterator) {
		String removed = "three";
		String addedAfter = "five";
		String added = "five and a half";
		String replaced = "seven";
		String replacement = "another seven";
		assertTrue(this.originalList.contains(removed));
		assertTrue(this.originalList.contains(addedAfter));
		assertTrue(this.originalList.contains(replaced));
		// try to remove before calling #next()
		boolean exCaught = false;
		try {
			iterator.remove();
		} catch (IllegalStateException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
		while (iterator.hasNext()) {
			String next = iterator.next();
			if (next.equals(addedAfter)) {
				iterator.add(added);
			}
			if (next.equals(removed)) {
				iterator.remove();
				// try to remove twice
				exCaught = false;
				try {
					iterator.remove();
				} catch (IllegalStateException ex) {
					exCaught = true;
				}
				assertTrue(exCaught);
			}
			if (next.equals(replaced)) {
				iterator.set(replacement);
			}
		}
		assertTrue(this.originalList.contains(added));
		assertFalse(this.originalList.contains(removed));
		assertFalse(this.originalList.contains(replaced));
		assertTrue(this.originalList.contains(replacement));
	}

	private void verifyModifyPrevious(ListIterator<String> iterator) {
		String removed = "three";
		String addedBefore = "five";
		String added = "four and a half";
		String replaced = "seven";
		String replacement = "another seven";
		assertTrue(this.originalList.contains(removed));
		assertTrue(this.originalList.contains(addedBefore));
		assertTrue(this.originalList.contains(replaced));
		while (iterator.hasNext()) {
			iterator.next();
		}
		while (iterator.hasPrevious()) {
			Object previous = iterator.previous();
			if (previous.equals(addedBefore)) {
				iterator.add(added);
			}
			if (previous.equals(removed)) {
				iterator.remove();
				// try to remove twice
				boolean exCaught = false;
				try {
					iterator.remove();
				} catch (IllegalStateException ex) {
					exCaught = true;
				}
				assertTrue("IllegalStateException not thrown", exCaught);
			}
			if (previous.equals(replaced)) {
				iterator.set(replacement);
			}
		}
		assertTrue(this.originalList.contains(added));
		assertFalse(this.originalList.contains(removed));
		assertFalse(this.originalList.contains(replaced));
		assertTrue(this.originalList.contains(replacement));
	}

	private ListIterator<String> buildCloneListIterator() {
		return this.buildCloneListIterator(this.originalList);
	}

	private ListIterator<String> buildCloneListIterator(List<String> list) {
		return new CloneListIterator<String>(list);
	}

	private ListIterator<String> buildNestedListIterator() {
		return this.originalList.listIterator();
	}

	private List<String> buildList() {
		List<String> list = this.buildEmptyList();
		this.populateList(list);
		return list;
	}

	private void populateList(List<String> list) {
		list.add("zero");
		list.add("one");
		list.add("two");
		list.add("three");
		list.add("four");
		list.add("five");
		list.add("six");
		list.add("seven");
		list.add("eight");
		list.add("nine");
	}

	protected List<String> buildEmptyList() {
		return new ArrayList<String>();
	}

	/**
	 * Test concurrent access: First build a clone iterator in a separate thread
	 * that hangs momentarily during its construction; then modify the shared
	 * collection in this thread. This would cause a
	 * ConcurrentModificationException in the other thread if the clone iterator
	 * were not synchronized on the original collection.
	 */
	public void testConcurrentAccess() throws Exception {
		CloneIteratorTests.SlowCollection<String> slow = new CloneIteratorTests.SlowCollection<String>();
		this.populateList(slow);
		// using the unsynchronized list will cause the test to fail
		// this.originalList = slow;
		this.originalList = Collections.synchronizedList(slow);

		this.concurrentList = new ArrayList<String>();
		Thread thread = this.buildThread(this.buildRunnable());
		thread.start();
		while ( ! slow.hasStartedClone()) {
			// wait for the other thread to start the clone...
			Thread.yield();
		}
		// ...then sneak in an extra element
		this.originalList.add("seventeen");
		thread.join();
		List<String> expected = new ArrayList<String>();
		this.populateList(expected);
		assertEquals(expected, this.concurrentList);
	}

	private Runnable buildRunnable() {
		return new TestRunnable() {
			@Override
			protected void run_() throws Throwable {
				CloneListIteratorTests.this.loopWithCloneListIterator();
			}
		};
	}

	/**
	 * use a clone iterator to loop over the "slow" collection and copy its
	 * contents to the concurrent collection
	 */
	void loopWithCloneListIterator() {
		for (ListIterator<String> stream = this.buildCloneListIterator(); stream.hasNext();) {
			this.concurrentList.add(stream.next());
		}
	}

}

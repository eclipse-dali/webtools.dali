/*******************************************************************************
 * Copyright (c) 2005, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.tests.internal.iterators;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;
import junit.framework.TestCase;
import org.eclipse.jpt.utility.internal.iterators.CloneIterator;
import org.eclipse.jpt.utility.tests.internal.TestTools;

@SuppressWarnings("nls")
public class CloneIteratorTests extends TestCase {
	Collection<String> originalCollection;

	private boolean concurrentProblem;
	private Collection<String> concurrentCollection;

	public CloneIteratorTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.originalCollection = this.buildCollection();
	}

	@Override
	protected void tearDown() throws Exception {
		TestTools.clear(this);
		super.tearDown();
	}

	public void testHasNext() {
		int originalSize = this.originalCollection.size();
		int i = 0;
		for (Iterator<String> stream = this.buildCloneIterator(); stream.hasNext();) {
			stream.next();
			// should allow concurrent modification
			this.originalCollection.add("foo");
			i++;
		}
		assertTrue(originalSize != this.originalCollection.size());
		assertEquals(originalSize, i);
	}

	public void testNext() {
		Iterator<String> nestedIterator = this.originalCollection.iterator();
		for (Iterator<String> stream = this.buildCloneIterator(); stream.hasNext();) {
			assertEquals("bogus element", nestedIterator.next(), stream.next());
		}
	}

	public void testNoSuchElementException() {
		boolean exCaught = false;
		Iterator<String> stream = this.buildCloneIterator();
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
	}

	public void testRemoveDefault() {
		boolean exCaught = false;
		for (Iterator<String> stream = this.buildCloneIterator(); stream.hasNext();) {
			if (stream.next().equals("three")) {
				try {
					stream.remove();
				} catch (UnsupportedOperationException ex) {
					exCaught = true;
				}
			}
		}
		assertTrue("UnsupportedOperationException not thrown", exCaught);
	}

	public void testRemoveEliminator() {
		CloneIterator.Remover<String> eliminator = new CloneIterator.Remover<String>() {
			public void remove(String element) {
				CloneIteratorTests.this.originalCollection.remove(element);
			}
		};
		this.verifyRemove(new CloneIterator<String>(this.originalCollection, eliminator));
	}

	public void testRemoveSubclass() {
		this.verifyRemove(new CloneIterator<String>(this.originalCollection) {
			@Override
			protected void remove(String current) {
				CloneIteratorTests.this.originalCollection.remove(current);
			}
		});
	}

	/**
	 * Test concurrent access: First build a clone iterator in a separate thread
	 * that hangs momentarily during its construction; then modify the shared
	 * collection in this thread. This would cause a
	 * ConcurrentModificationException in the other thread if the clone iterator
	 * were not synchronized on the original collection.
	 */
	public void testConcurrentAccess() throws Exception {
		SlowCollection<String> slow = new SlowCollection<String>();
		this.populateCollection(slow);
		// using the unsynchronized collection will cause the test to fail
		//		this.originalCollection = slow;
		this.originalCollection = Collections.synchronizedCollection(slow);

		this.concurrentProblem = false;
		this.concurrentCollection = new ArrayList<String>();
		Thread thread = new Thread(this.buildRunnable());
		thread.start();
		while (!slow.hasStartedClone()) {
			// wait for the other thread to start the clone...
			Thread.yield();
		}
		// ...then sneak in an extra element
		this.originalCollection.add("seventeen");
		while (thread.isAlive()) {
			// wait for the other thread to finish
			Thread.yield();
		}
		assertFalse(this.concurrentProblem);
		Collection<String> expected = new ArrayList<String>();
		this.populateCollection(expected);
		assertEquals(expected, this.concurrentCollection);
	}

	private Runnable buildRunnable() {
		return new Runnable() {
			public void run() {
				CloneIteratorTests.this.loopWithCloneIterator();
			}
		};
	}

	/**
	 * use a clone iterator to loop over the "slow" collection and copy its
	 * contents to the concurrent collection
	 */
	void loopWithCloneIterator() {
		try {
			for (Iterator<String> stream = this.buildCloneIterator(); stream.hasNext();) {
				this.concurrentCollection.add(stream.next());
			}
		} catch (Throwable t) {
			this.concurrentProblem = true;
		}
	}

	private void verifyRemove(Iterator<String> iterator) {
		Object removed = "three";
		assertTrue(this.originalCollection.contains(removed));
		// try to remove before calling #next()
		boolean exCaught = false;
		try {
			iterator.remove();
		} catch (IllegalStateException ex) {
			exCaught = true;
		}
		assertTrue("IllegalStateException not thrown", exCaught);
		while (iterator.hasNext()) {
			if (iterator.next().equals(removed)) {
				iterator.remove();
				// try to remove twice
				exCaught = false;
				try {
					iterator.remove();
				} catch (IllegalStateException ex) {
					exCaught = true;
				}
				assertTrue("IllegalStateException not thrown", exCaught);
			}
		}
		assertFalse(this.originalCollection.contains(removed));
	}

	private Iterator<String> buildCloneIterator() {
		return this.buildCloneIterator(this.originalCollection);
	}

	private Iterator<String> buildCloneIterator(Collection<String> c) {
		return new CloneIterator<String>(c);
	}

	private Collection<String> buildCollection() {
		Collection<String> c = this.buildEmptyCollection();
		this.populateCollection(c);
		return c;
	}

	protected Collection<String> buildEmptyCollection() {
		return new ArrayList<String>();
	}

	private void populateCollection(Collection<String> c) {
		c.add("one");
		c.add("two");
		c.add("three");
		c.add("four");
		c.add("five");
		c.add("six");
		c.add("seven");
		c.add("eight");
	}

	// ********** custom collection **********
	static class SlowCollection<E> extends ArrayList<E> {
		private static final long serialVersionUID = 1L;
		private boolean hasStartedClone = false;

		public SlowCollection() {
			super();
		}

		@Override
		public Object[] toArray() {
			this.setHasStartedClone(true);
			// take a little snooze before returning the array
			try {
				Thread.sleep(100);
			} catch (InterruptedException ex) {
				throw new RuntimeException(ex);
			}
			return super.toArray();
		}

		synchronized void setHasStartedClone(boolean hasStartedClone) {
			this.hasStartedClone = hasStartedClone;
		}

		synchronized boolean hasStartedClone() {
			return this.hasStartedClone;
		}
	}

}

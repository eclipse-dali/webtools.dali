/*******************************************************************************
 * Copyright (c) 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import org.eclipse.jpt.common.utility.internal.collection.CollectionTools;
import org.eclipse.jpt.common.utility.internal.collection.ReadWriteLockCollectionWrapper;
import junit.framework.TestCase;

@SuppressWarnings("nls")
public class ReadWriteLockCollectionWrapperTests
	extends TestCase
{
	private Collection<String> collection;
	private ReadWriteLockCollectionWrapper<String> wrapper;

	public ReadWriteLockCollectionWrapperTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.collection = this.buildCollection();
		this.wrapper = this.buildWrapper(this.collection);
	}

	protected Collection<String> buildCollection() {
		return new ArrayList<>();
	}

	protected ReadWriteLockCollectionWrapper<String> buildWrapper(Collection<String> c) {
		return CollectionTools.readWriteLockWrapper(c);
	}

	public void testSize() throws Exception {
		assertEquals(this.collection.size(), this.wrapper.size());
		this.collection.add("foo");
		assertEquals(this.collection.size(), this.wrapper.size());
		this.collection.add("bar");
		assertEquals(this.collection.size(), this.wrapper.size());
		this.collection.add("baz");
		assertEquals(this.collection.size(), this.wrapper.size());
	}

	public void testIsEmpty() throws Exception {
		assertEquals(this.collection.isEmpty(), this.wrapper.isEmpty());
		this.collection.add("foo");
		assertEquals(this.collection.isEmpty(), this.wrapper.isEmpty());
		this.collection.add("bar");
		assertEquals(this.collection.isEmpty(), this.wrapper.isEmpty());
		this.collection.add("baz");
		assertEquals(this.collection.isEmpty(), this.wrapper.isEmpty());
		this.collection.remove("bar");
		assertEquals(this.collection.isEmpty(), this.wrapper.isEmpty());
		this.collection.remove("baz");
		this.collection.remove("foo");
		assertEquals(this.collection.isEmpty(), this.wrapper.isEmpty());
	}

	public void testContains() throws Exception {
		assertEquals(this.collection.contains("foo"), this.wrapper.contains("foo"));
		assertEquals(this.collection.contains("bar"), this.wrapper.contains("bar"));

		this.collection.add("foo");
		assertEquals(this.collection.contains("foo"), this.wrapper.contains("foo"));
		assertEquals(this.collection.contains("bar"), this.wrapper.contains("bar"));

		this.collection.add("bar");
		assertEquals(this.collection.contains("foo"), this.wrapper.contains("foo"));
		assertEquals(this.collection.contains("bar"), this.wrapper.contains("bar"));
		assertEquals(this.collection.contains("baz"), this.wrapper.contains("baz"));

		this.collection.add("baz");
		assertEquals(this.collection.contains("foo"), this.wrapper.contains("foo"));
		assertEquals(this.collection.contains("bar"), this.wrapper.contains("bar"));
		assertEquals(this.collection.contains("baz"), this.wrapper.contains("baz"));
	}

	public void testContainsAll() throws Exception {
		Collection<String> c2 = new ArrayList<>();
		c2.add("foo");
		c2.add("bar");
		c2.add("baz");
		assertEquals(this.collection.containsAll(c2), this.wrapper.containsAll(c2));

		this.collection.add("foo");
		assertEquals(this.collection.containsAll(c2), this.wrapper.containsAll(c2));

		this.collection.add("bar");
		assertEquals(this.collection.containsAll(c2), this.wrapper.containsAll(c2));

		this.collection.add("baz");
		assertEquals(this.collection.containsAll(c2), this.wrapper.containsAll(c2));
	}

	public void testToArray() throws Exception {
		assertTrue(Arrays.equals(this.collection.toArray(), this.wrapper.toArray()));

		this.collection.add("foo");
		assertTrue(Arrays.equals(this.collection.toArray(), this.wrapper.toArray()));

		this.collection.add("bar");
		assertTrue(Arrays.equals(this.collection.toArray(), this.wrapper.toArray()));

		this.collection.add("baz");
		assertTrue(Arrays.equals(this.collection.toArray(), this.wrapper.toArray()));
	}

	public void testToArrayObjectArray() throws Exception {
		String[] stringArray = new String[0];
		assertTrue(Arrays.equals(this.collection.toArray(stringArray), this.wrapper.toArray(stringArray)));

		this.collection.add("foo");
		assertTrue(Arrays.equals(this.collection.toArray(stringArray), this.wrapper.toArray(stringArray)));

		this.collection.add("bar");
		assertTrue(Arrays.equals(this.collection.toArray(stringArray), this.wrapper.toArray(stringArray)));

		this.collection.add("baz");
		assertTrue(Arrays.equals(this.collection.toArray(stringArray), this.wrapper.toArray(stringArray)));
	}

	public void testToString() throws Exception {
		assertEquals(this.collection.toString(), this.wrapper.toString());

		this.collection.add("foo");
		assertEquals(this.collection.toString(), this.wrapper.toString());

		this.collection.add("bar");
		assertEquals(this.collection.toString(), this.wrapper.toString());

		this.collection.add("baz");
		assertEquals(this.collection.toString(), this.wrapper.toString());
	}

	public void testAdd() throws Exception {
		assertEquals(this.collection.contains("foo"), this.wrapper.contains("foo"));
		assertEquals(this.collection.contains("bar"), this.wrapper.contains("bar"));
		assertEquals(this.collection.add("foo"), this.wrapper.add("foo"));
		assertEquals(this.collection.contains("foo"), this.wrapper.contains("foo"));
		assertEquals(this.collection.contains("bar"), this.wrapper.contains("bar"));
		assertEquals(this.collection.add("bar"), this.wrapper.add("bar"));
		assertEquals(this.collection.contains("foo"), this.wrapper.contains("foo"));
		assertEquals(this.collection.contains("bar"), this.wrapper.contains("bar"));
	}

	public void testRemove() throws Exception {
		assertEquals(this.collection.contains("foo"), this.wrapper.contains("foo"));
		assertEquals(this.collection.contains("bar"), this.wrapper.contains("bar"));
		assertEquals(this.collection.add("foo"), this.wrapper.add("foo"));
		assertEquals(this.collection.contains("foo"), this.wrapper.contains("foo"));
		assertEquals(this.collection.contains("bar"), this.wrapper.contains("bar"));
		assertEquals(this.collection.add("bar"), this.wrapper.add("bar"));
		assertEquals(this.collection.contains("foo"), this.wrapper.contains("foo"));
		assertEquals(this.collection.contains("bar"), this.wrapper.contains("bar"));

		assertEquals(this.collection.remove("foo"), this.wrapper.remove("foo"));
		assertEquals(this.collection.contains("foo"), this.wrapper.contains("foo"));
		assertEquals(this.collection.contains("bar"), this.wrapper.contains("bar"));
		assertEquals(this.collection.remove("bar"), this.wrapper.remove("bar"));
		assertEquals(this.collection.contains("foo"), this.wrapper.contains("foo"));
		assertEquals(this.collection.contains("bar"), this.wrapper.contains("bar"));
		assertEquals(this.collection.remove("bar"), this.wrapper.remove("bar"));
		assertEquals(this.collection.contains("foo"), this.wrapper.contains("foo"));
		assertEquals(this.collection.contains("bar"), this.wrapper.contains("bar"));
	}

	public void testAddAll() throws Exception {
		Collection<String> c2 = new ArrayList<>();
		c2.add("foo");
		c2.add("bar");
		c2.add("baz");
		assertTrue(this.wrapper.addAll(c2));
		assertTrue(this.wrapper.contains("foo"));
		assertTrue(this.collection.contains("foo"));
		assertTrue(this.wrapper.contains("bar"));
		assertTrue(this.collection.contains("bar"));
		assertTrue(this.wrapper.contains("baz"));
		assertTrue(this.collection.contains("baz"));
	}

	public void testRemoveAll() throws Exception {
		Collection<String> c2 = new ArrayList<>();
		c2.add("foo");
		c2.add("bar");
		c2.add("baz");
		this.wrapper.addAll(c2);
		assertTrue(this.wrapper.contains("foo"));
		assertTrue(this.collection.contains("foo"));
		assertTrue(this.wrapper.contains("bar"));
		assertTrue(this.collection.contains("bar"));
		assertTrue(this.wrapper.contains("baz"));
		assertTrue(this.collection.contains("baz"));

		Collection<String> c3 = new ArrayList<>(c2);
		c3.remove("baz");
		assertTrue(this.wrapper.removeAll(c3));
		assertFalse(this.wrapper.contains("foo"));
		assertFalse(this.collection.contains("foo"));
		assertFalse(this.wrapper.contains("bar"));
		assertFalse(this.collection.contains("bar"));
		assertTrue(this.wrapper.contains("baz"));
		assertTrue(this.collection.contains("baz"));

		assertFalse(this.wrapper.removeAll(c3));
	}

	public void testRetainAll() throws Exception {
		Collection<String> c2 = new ArrayList<>();
		c2.add("foo");
		c2.add("bar");
		c2.add("baz");
		this.wrapper.addAll(c2);
		assertTrue(this.wrapper.contains("foo"));
		assertTrue(this.collection.contains("foo"));
		assertTrue(this.wrapper.contains("bar"));
		assertTrue(this.collection.contains("bar"));
		assertTrue(this.wrapper.contains("baz"));
		assertTrue(this.collection.contains("baz"));

		Collection<String> c3 = new ArrayList<>(c2);
		c3.remove("baz");
		assertTrue(this.wrapper.retainAll(c3));
		assertTrue(this.wrapper.contains("foo"));
		assertTrue(this.collection.contains("foo"));
		assertTrue(this.wrapper.contains("bar"));
		assertTrue(this.collection.contains("bar"));
		assertFalse(this.wrapper.contains("baz"));
		assertFalse(this.collection.contains("baz"));

		assertFalse(this.wrapper.retainAll(c3));
	}

	public void testClear() throws Exception {
		this.wrapper.add("foo");
		this.wrapper.add("bar");
		this.wrapper.add("baz");
		assertEquals(this.collection.contains("foo"), this.wrapper.contains("foo"));
		assertEquals(this.collection.contains("bar"), this.wrapper.contains("bar"));
		assertEquals(this.collection.contains("baz"), this.wrapper.contains("baz"));

		this.wrapper.clear();
		assertTrue(this.wrapper.isEmpty());
		assertTrue(this.collection.isEmpty());
		assertEquals(this.collection.contains("foo"), this.wrapper.contains("foo"));
		assertEquals(this.collection.contains("bar"), this.wrapper.contains("bar"));
		assertEquals(this.collection.contains("baz"), this.wrapper.contains("baz"));
	}

	public void testCtor_NPE_collection() throws Exception {
		boolean exCaught = false;
		try {
			ReadWriteLockCollectionWrapper<String> w = CollectionTools.readWriteLockWrapper(null);
			fail("bogus: " + w);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testCtor_NPE_lock() throws Exception {
		boolean exCaught = false;
		try {
			ReadWriteLockCollectionWrapper<String> w = CollectionTools.readWriteLockWrapper(this.collection, null);
			fail("bogus: " + w);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}
}

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
import java.util.List;
import org.eclipse.jpt.common.utility.internal.collection.ListTools;
import org.eclipse.jpt.common.utility.internal.collection.ReadWriteLockListWrapper;
import junit.framework.TestCase;

@SuppressWarnings("nls")
public class ReadWriteLockListWrapperTests
	extends TestCase
{
	private List<String> list;
	private ReadWriteLockListWrapper<String> wrapper;

	public ReadWriteLockListWrapperTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.list = this.buildList();
		this.wrapper = this.buildWrapper(this.list);
	}

	protected List<String> buildList() {
		return new ArrayList<>();
	}

	protected ReadWriteLockListWrapper<String> buildWrapper(List<String> c) {
		return ListTools.readWriteLockWrapper(c);
	}

	public void testSize() throws Exception {
		assertEquals(this.list.size(), this.wrapper.size());
		this.list.add("foo");
		assertEquals(this.list.size(), this.wrapper.size());
		this.list.add("bar");
		assertEquals(this.list.size(), this.wrapper.size());
		this.list.add("baz");
		assertEquals(this.list.size(), this.wrapper.size());
	}

	public void testIsEmpty() throws Exception {
		assertEquals(this.list.isEmpty(), this.wrapper.isEmpty());
		this.list.add("foo");
		assertEquals(this.list.isEmpty(), this.wrapper.isEmpty());
		this.list.add("bar");
		assertEquals(this.list.isEmpty(), this.wrapper.isEmpty());
		this.list.add("baz");
		assertEquals(this.list.isEmpty(), this.wrapper.isEmpty());
		this.list.remove("bar");
		assertEquals(this.list.isEmpty(), this.wrapper.isEmpty());
		this.list.remove("baz");
		this.list.remove("foo");
		assertEquals(this.list.isEmpty(), this.wrapper.isEmpty());
	}

	public void testContains() throws Exception {
		assertEquals(this.list.contains("foo"), this.wrapper.contains("foo"));
		assertEquals(this.list.contains("bar"), this.wrapper.contains("bar"));

		this.list.add("foo");
		assertEquals(this.list.contains("foo"), this.wrapper.contains("foo"));
		assertEquals(this.list.contains("bar"), this.wrapper.contains("bar"));

		this.list.add("bar");
		assertEquals(this.list.contains("foo"), this.wrapper.contains("foo"));
		assertEquals(this.list.contains("bar"), this.wrapper.contains("bar"));
		assertEquals(this.list.contains("baz"), this.wrapper.contains("baz"));

		this.list.add("baz");
		assertEquals(this.list.contains("foo"), this.wrapper.contains("foo"));
		assertEquals(this.list.contains("bar"), this.wrapper.contains("bar"));
		assertEquals(this.list.contains("baz"), this.wrapper.contains("baz"));
	}

	public void testContainsAll() throws Exception {
		List<String> c2 = new ArrayList<>();
		c2.add("foo");
		c2.add("bar");
		c2.add("baz");
		assertEquals(this.list.containsAll(c2), this.wrapper.containsAll(c2));

		this.list.add("foo");
		assertEquals(this.list.containsAll(c2), this.wrapper.containsAll(c2));

		this.list.add("bar");
		assertEquals(this.list.containsAll(c2), this.wrapper.containsAll(c2));

		this.list.add("baz");
		assertEquals(this.list.containsAll(c2), this.wrapper.containsAll(c2));
	}

	public void testGet() throws Exception {
		this.list.add("foo");
		assertEquals(this.list.get(0), this.wrapper.get(0));

		this.list.add("bar");
		assertEquals(this.list.get(0), this.wrapper.get(0));
		assertEquals(this.list.get(1), this.wrapper.get(1));

		this.list.add("baz");
		assertEquals(this.list.get(0), this.wrapper.get(0));
		assertEquals(this.list.get(1), this.wrapper.get(1));
		assertEquals(this.list.get(2), this.wrapper.get(2));
	}

	public void testIndexOf() throws Exception {
		this.list.add("foo");
		assertEquals(this.list.indexOf("foo"), this.wrapper.indexOf("foo"));
		assertEquals(this.list.indexOf("bar"), this.wrapper.indexOf("bar"));
		assertEquals(this.list.indexOf("baz"), this.wrapper.indexOf("baz"));

		this.list.add("bar");
		assertEquals(this.list.indexOf("foo"), this.wrapper.indexOf("foo"));
		assertEquals(this.list.indexOf("bar"), this.wrapper.indexOf("bar"));
		assertEquals(this.list.indexOf("baz"), this.wrapper.indexOf("baz"));

		this.list.add("baz");
		assertEquals(this.list.indexOf("foo"), this.wrapper.indexOf("foo"));
		assertEquals(this.list.indexOf("bar"), this.wrapper.indexOf("bar"));
		assertEquals(this.list.indexOf("baz"), this.wrapper.indexOf("baz"));
	}

	public void testLastIndexOf() throws Exception {
		this.list.add("foo");
		assertEquals(this.list.lastIndexOf("foo"), this.wrapper.lastIndexOf("foo"));
		assertEquals(this.list.lastIndexOf("bar"), this.wrapper.lastIndexOf("bar"));
		assertEquals(this.list.lastIndexOf("baz"), this.wrapper.lastIndexOf("baz"));

		this.list.add("bar");
		assertEquals(this.list.lastIndexOf("foo"), this.wrapper.lastIndexOf("foo"));
		assertEquals(this.list.lastIndexOf("bar"), this.wrapper.lastIndexOf("bar"));
		assertEquals(this.list.lastIndexOf("baz"), this.wrapper.lastIndexOf("baz"));

		this.list.add("baz");
		assertEquals(this.list.lastIndexOf("foo"), this.wrapper.lastIndexOf("foo"));
		assertEquals(this.list.lastIndexOf("bar"), this.wrapper.lastIndexOf("bar"));
		assertEquals(this.list.lastIndexOf("baz"), this.wrapper.lastIndexOf("baz"));
	}

	public void testToArray() throws Exception {
		assertTrue(Arrays.equals(this.list.toArray(), this.wrapper.toArray()));

		this.list.add("foo");
		assertTrue(Arrays.equals(this.list.toArray(), this.wrapper.toArray()));

		this.list.add("bar");
		assertTrue(Arrays.equals(this.list.toArray(), this.wrapper.toArray()));

		this.list.add("baz");
		assertTrue(Arrays.equals(this.list.toArray(), this.wrapper.toArray()));
	}

	public void testToArrayObjectArray() throws Exception {
		String[] stringArray = new String[0];
		assertTrue(Arrays.equals(this.list.toArray(stringArray), this.wrapper.toArray(stringArray)));

		this.list.add("foo");
		assertTrue(Arrays.equals(this.list.toArray(stringArray), this.wrapper.toArray(stringArray)));

		this.list.add("bar");
		assertTrue(Arrays.equals(this.list.toArray(stringArray), this.wrapper.toArray(stringArray)));

		this.list.add("baz");
		assertTrue(Arrays.equals(this.list.toArray(stringArray), this.wrapper.toArray(stringArray)));
	}

	public void testToString() throws Exception {
		assertEquals(this.list.toString(), this.wrapper.toString());

		this.list.add("foo");
		assertEquals(this.list.toString(), this.wrapper.toString());

		this.list.add("bar");
		assertEquals(this.list.toString(), this.wrapper.toString());

		this.list.add("baz");
		assertEquals(this.list.toString(), this.wrapper.toString());
	}

	public void testAdd() throws Exception {
		assertFalse(this.list.contains("foo"));
		assertFalse(this.wrapper.contains("foo"));
		assertFalse(this.list.contains("bar"));
		assertFalse(this.wrapper.contains("bar"));

		assertTrue(this.list.add("foo"));
		assertTrue(this.list.contains("foo"));
		assertTrue(this.wrapper.contains("foo"));
		assertFalse(this.list.contains("bar"));
		assertFalse(this.wrapper.contains("bar"));

		assertTrue(this.list.add("bar"));
		assertTrue(this.list.contains("foo"));
		assertTrue(this.wrapper.contains("foo"));
		assertTrue(this.list.contains("bar"));
		assertTrue(this.wrapper.contains("bar"));
	}

	public void testRemove() throws Exception {
		assertFalse(this.list.contains("foo"));
		assertFalse(this.wrapper.contains("foo"));
		assertFalse(this.list.contains("bar"));
		assertFalse(this.wrapper.contains("bar"));

		this.wrapper.add("foo");
		assertTrue(this.list.contains("foo"));
		assertTrue(this.wrapper.contains("foo"));
		assertFalse(this.list.contains("bar"));
		assertFalse(this.wrapper.contains("bar"));

		assertTrue(this.list.add("bar"));
		assertTrue(this.list.contains("foo"));
		assertTrue(this.wrapper.contains("foo"));
		assertTrue(this.list.contains("bar"));
		assertTrue(this.wrapper.contains("bar"));

		assertTrue(this.wrapper.remove("foo"));
		assertFalse(this.list.contains("foo"));
		assertTrue(this.list.contains("bar"));
		assertTrue(this.wrapper.contains("bar"));
		assertFalse(this.wrapper.contains("foo"));

		assertTrue(this.wrapper.remove("bar"));
		assertFalse(this.list.contains("foo"));
		assertFalse(this.wrapper.contains("foo"));
		assertFalse(this.list.contains("bar"));
		assertFalse(this.wrapper.contains("bar"));

		assertFalse(this.wrapper.remove("bar"));
		assertFalse(this.list.contains("foo"));
		assertFalse(this.wrapper.contains("foo"));
		assertFalse(this.list.contains("bar"));
		assertFalse(this.wrapper.contains("bar"));
	}

	public void testAddAll() throws Exception {
		List<String> c2 = new ArrayList<>();
		c2.add("foo");
		c2.add("bar");
		c2.add("baz");
		assertTrue(this.wrapper.addAll(c2));
		assertTrue(this.wrapper.contains("foo"));
		assertTrue(this.list.contains("foo"));
		assertTrue(this.wrapper.contains("bar"));
		assertTrue(this.list.contains("bar"));
		assertTrue(this.wrapper.contains("baz"));
		assertTrue(this.list.contains("baz"));
	}

	public void testRemoveAll() throws Exception {
		List<String> c2 = new ArrayList<>();
		c2.add("foo");
		c2.add("bar");
		c2.add("baz");
		this.wrapper.addAll(c2);
		assertTrue(this.wrapper.contains("foo"));
		assertTrue(this.list.contains("foo"));
		assertTrue(this.wrapper.contains("bar"));
		assertTrue(this.list.contains("bar"));
		assertTrue(this.wrapper.contains("baz"));
		assertTrue(this.list.contains("baz"));

		List<String> c3 = new ArrayList<>(c2);
		c3.remove("baz");
		assertTrue(this.wrapper.removeAll(c3));
		assertFalse(this.wrapper.contains("foo"));
		assertFalse(this.list.contains("foo"));
		assertFalse(this.wrapper.contains("bar"));
		assertFalse(this.list.contains("bar"));
		assertTrue(this.wrapper.contains("baz"));
		assertTrue(this.list.contains("baz"));

		assertFalse(this.wrapper.removeAll(c3));
	}

	public void testRetainAll() throws Exception {
		List<String> c2 = new ArrayList<>();
		c2.add("foo");
		c2.add("bar");
		c2.add("baz");
		this.wrapper.addAll(c2);
		assertTrue(this.wrapper.contains("foo"));
		assertTrue(this.list.contains("foo"));
		assertTrue(this.wrapper.contains("bar"));
		assertTrue(this.list.contains("bar"));
		assertTrue(this.wrapper.contains("baz"));
		assertTrue(this.list.contains("baz"));

		List<String> c3 = new ArrayList<>(c2);
		c3.remove("baz");
		assertTrue(this.wrapper.retainAll(c3));
		assertTrue(this.wrapper.contains("foo"));
		assertTrue(this.list.contains("foo"));
		assertTrue(this.wrapper.contains("bar"));
		assertTrue(this.list.contains("bar"));
		assertFalse(this.wrapper.contains("baz"));
		assertFalse(this.list.contains("baz"));

		assertFalse(this.wrapper.retainAll(c3));
	}

	public void testClear() throws Exception {
		this.wrapper.add("foo");
		this.wrapper.add("bar");
		this.wrapper.add("baz");
		assertEquals(this.list.contains("foo"), this.wrapper.contains("foo"));
		assertEquals(this.list.contains("bar"), this.wrapper.contains("bar"));
		assertEquals(this.list.contains("baz"), this.wrapper.contains("baz"));

		this.wrapper.clear();
		assertTrue(this.wrapper.isEmpty());
		assertTrue(this.list.isEmpty());
		assertEquals(this.list.contains("foo"), this.wrapper.contains("foo"));
		assertEquals(this.list.contains("bar"), this.wrapper.contains("bar"));
		assertEquals(this.list.contains("baz"), this.wrapper.contains("baz"));
	}

	public void testAddAllIndex() throws Exception {
		List<String> c2 = new ArrayList<>();
		c2.add("foo");
		c2.add("bar");
		c2.add("baz");
		assertTrue(this.wrapper.addAll(0, c2));
		assertTrue(this.wrapper.contains("foo"));
		assertTrue(this.list.contains("foo"));
		assertTrue(this.wrapper.contains("bar"));
		assertTrue(this.list.contains("bar"));
		assertTrue(this.wrapper.contains("baz"));
		assertTrue(this.list.contains("baz"));

		List<String> c3 = new ArrayList<>();
		c3.add("joo");
		c3.add("jar");
		c3.add("jaz");
		assertTrue(this.wrapper.addAll(1, c3));
}

	public void testSet() throws Exception {
		this.wrapper.add("foo");
		this.wrapper.add("bar");
		this.wrapper.add("baz");
		assertEquals("foo", this.wrapper.set(0, "joo"));
		assertEquals("joo", this.wrapper.get(0));
		assertEquals("joo", this.list.get(0));

		assertEquals("baz", this.wrapper.set(2, "jaz"));
		assertEquals("jaz", this.wrapper.get(2));
		assertEquals("jaz", this.list.get(2));
	}

	public void testAddIndex() throws Exception {
		assertFalse(this.list.contains("foo"));
		assertFalse(this.wrapper.contains("foo"));
		assertFalse(this.list.contains("bar"));
		assertFalse(this.wrapper.contains("bar"));

		this.wrapper.add(0, "foo");
		assertTrue(this.list.contains("foo"));
		assertTrue(this.wrapper.contains("foo"));
		assertFalse(this.list.contains("bar"));
		assertFalse(this.wrapper.contains("bar"));

		this.wrapper.add(0, "bar");
		assertTrue(this.list.contains("foo"));
		assertTrue(this.wrapper.contains("foo"));
		assertTrue(this.list.contains("bar"));
		assertTrue(this.wrapper.contains("bar"));
	}

	public void testRemoveIndex() throws Exception {
		assertFalse(this.list.contains("foo"));
		assertFalse(this.wrapper.contains("foo"));
		assertFalse(this.list.contains("bar"));
		assertFalse(this.wrapper.contains("bar"));

		this.wrapper.add("foo");
		this.wrapper.add("bar");

		assertEquals("foo", this.wrapper.remove(0));
		assertFalse(this.list.contains("foo"));
		assertFalse(this.wrapper.contains("foo"));
		assertTrue(this.list.contains("bar"));
		assertTrue(this.wrapper.contains("bar"));

		assertEquals("bar", this.wrapper.remove(0));
		assertFalse(this.list.contains("foo"));
		assertFalse(this.wrapper.contains("foo"));
		assertFalse(this.list.contains("bar"));
		assertFalse(this.wrapper.contains("bar"));
	}

	public void testCtor_NPE_list() throws Exception {
		boolean exCaught = false;
		try {
			ReadWriteLockListWrapper<String> w = ListTools.readWriteLockWrapper(null);
			fail("bogus: " + w);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testCtor_NPE_lock() throws Exception {
		boolean exCaught = false;
		try {
			ReadWriteLockListWrapper<String> w = ListTools.readWriteLockWrapper(this.list, null);
			fail("bogus: " + w);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}
}

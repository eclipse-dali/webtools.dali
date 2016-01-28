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

import java.util.HashMap;
import java.util.Map;
import org.eclipse.jpt.common.utility.internal.collection.ReadWriteLockMapWrapper;
import org.eclipse.jpt.common.utility.internal.collection.MapTools;
import junit.framework.TestCase;

@SuppressWarnings("nls")
public class ReadWriteLockMapWrapperTests
	extends TestCase
{
	private Map<String, String> map;
	private ReadWriteLockMapWrapper<String, String> wrapper;

	public ReadWriteLockMapWrapperTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.map = this.buildMap();
		this.wrapper = this.buildWrapper(this.map);
	}

	protected Map<String, String> buildMap() {
		return new HashMap<>();
	}

	protected ReadWriteLockMapWrapper<String, String> buildWrapper(Map<String, String> m) {
		return MapTools.readWriteLockWrapper(m);
	}

	public void testSize() throws Exception {
		assertEquals(this.map.size(), this.wrapper.size());
		this.map.put("foo", "foo value 1");
		assertEquals(this.map.size(), this.wrapper.size());
		this.map.put("foo", "foo value 2");
		assertEquals(this.map.size(), this.wrapper.size());
		this.map.put("bar", "bar value");
		assertEquals(this.map.size(), this.wrapper.size());
		this.map.put("baz", "baz value");
		assertEquals(this.map.size(), this.wrapper.size());
	}

	public void testIsEmpty() throws Exception {
		assertEquals(this.map.isEmpty(), this.wrapper.isEmpty());
		this.map.put("foo", "foo value 1");
		assertEquals(this.map.isEmpty(), this.wrapper.isEmpty());
		this.map.put("foo", "foo value 2");
		assertEquals(this.map.isEmpty(), this.wrapper.isEmpty());
		this.map.put("bar", "bar value");
		assertEquals(this.map.isEmpty(), this.wrapper.isEmpty());
		this.map.put("baz", "baz value");
		assertEquals(this.map.isEmpty(), this.wrapper.isEmpty());
	}

	public void testContainsValue() throws Exception {
		assertEquals(this.map.containsValue("foo value 1"), this.wrapper.containsValue("foo value 1"));
		assertEquals(this.map.containsValue("foo value 2"), this.wrapper.containsValue("foo value 2"));

		this.map.put("foo", "foo value 1");
		assertEquals(this.map.containsValue("foo value 1"), this.wrapper.containsValue("foo value 1"));
		assertEquals(this.map.containsValue("foo value 2"), this.wrapper.containsValue("foo value 2"));

		this.map.put("foo", "foo value 2");
		assertEquals(this.map.containsValue("foo value 1"), this.wrapper.containsValue("foo value 1"));
		assertEquals(this.map.containsValue("foo value 2"), this.wrapper.containsValue("foo value 2"));

		this.map.put("bar", "bar value");
		assertEquals(this.map.containsValue("foo value 1"), this.wrapper.containsValue("foo value 1"));
		assertEquals(this.map.containsValue("foo value 2"), this.wrapper.containsValue("foo value 2"));
		assertEquals(this.map.containsValue("bar value"), this.wrapper.containsValue("bar value"));

		this.map.put("baz", "baz value");
		assertEquals(this.map.containsValue("foo value 2"), this.wrapper.containsValue("foo value 2"));
		assertEquals(this.map.containsValue("foo value 1"), this.wrapper.containsValue("foo value 1"));
		assertEquals(this.map.containsValue("bar value"), this.wrapper.containsValue("bar value"));
		assertEquals(this.map.containsValue("baz value"), this.wrapper.containsValue("baz value"));
	}

	public void testContainsKey() throws Exception {
		assertEquals(this.map.containsKey("foo"), this.wrapper.containsKey("foo"));
		assertEquals(this.map.containsKey("bar"), this.wrapper.containsKey("bar"));

		this.map.put("foo", "foo value 1");
		assertEquals(this.map.containsKey("foo"), this.wrapper.containsKey("foo"));
		assertEquals(this.map.containsKey("bar"), this.wrapper.containsKey("bar"));

		this.map.put("foo", "foo value 2");
		assertEquals(this.map.containsKey("foo"), this.wrapper.containsKey("foo"));
		assertEquals(this.map.containsKey("bar"), this.wrapper.containsKey("bar"));

		this.map.put("bar", "bar value");
		assertEquals(this.map.containsKey("foo"), this.wrapper.containsKey("foo"));
		assertEquals(this.map.containsKey("bar"), this.wrapper.containsKey("bar"));
		assertEquals(this.map.containsKey("baz"), this.wrapper.containsKey("baz"));

		this.map.put("baz", "baz value");
		assertEquals(this.map.containsKey("foo"), this.wrapper.containsKey("foo"));
		assertEquals(this.map.containsKey("bar"), this.wrapper.containsKey("bar"));
		assertEquals(this.map.containsKey("baz"), this.wrapper.containsKey("baz"));
	}

	public void testGet() throws Exception {
		assertEquals(this.map.get("foo"), this.wrapper.get("foo"));
		assertEquals(this.map.get("bar"), this.wrapper.get("bar"));

		this.map.put("foo", "foo value 1");
		assertEquals(this.map.get("foo"), this.wrapper.get("foo"));
		assertEquals(this.map.get("bar"), this.wrapper.get("bar"));

		this.map.put("foo", "foo value 2");
		assertEquals(this.map.get("foo"), this.wrapper.get("foo"));
		assertEquals(this.map.get("bar"), this.wrapper.get("bar"));

		this.map.put("bar", "bar value");
		assertEquals(this.map.get("foo"), this.wrapper.get("foo"));
		assertEquals(this.map.get("bar"), this.wrapper.get("bar"));
		assertEquals(this.map.get("baz"), this.wrapper.get("baz"));

		this.map.put("baz", "baz value");
		assertEquals(this.map.get("foo"), this.wrapper.get("foo"));
		assertEquals(this.map.get("bar"), this.wrapper.get("bar"));
		assertEquals(this.map.get("baz"), this.wrapper.get("baz"));
	}

	public void testToString() throws Exception {
		assertEquals(this.map.toString(), this.wrapper.toString());

		this.map.put("foo", "foo value 1");
		assertEquals(this.map.toString(), this.wrapper.toString());

		this.map.put("foo", "foo value 2");
		assertEquals(this.map.toString(), this.wrapper.toString());

		this.map.put("bar", "bar value");
		assertEquals(this.map.toString(), this.wrapper.toString());

		this.map.put("baz", "baz value");
		assertEquals(this.map.toString(), this.wrapper.toString());
	}

	public void testPut() throws Exception {
		assertEquals(this.map.get("foo"), this.wrapper.get("foo"));
		assertEquals(this.map.get("bar"), this.wrapper.get("bar"));

		assertEquals(null, this.wrapper.put("foo", "foo value 1"));
		assertEquals(this.map.get("foo"), this.wrapper.get("foo"));
		assertEquals(this.map.get("bar"), this.wrapper.get("bar"));

		assertEquals("foo value 1", this.wrapper.put("foo", "foo value 2"));
		assertEquals(this.map.get("foo"), this.wrapper.get("foo"));
		assertEquals(this.map.get("bar"), this.wrapper.get("bar"));

		assertEquals(null, this.wrapper.put("bar", "bar value"));
		assertEquals(this.map.get("foo"), this.wrapper.get("foo"));
		assertEquals(this.map.get("bar"), this.wrapper.get("bar"));
		assertEquals(this.map.get("baz"), this.wrapper.get("baz"));

		assertEquals(null, this.wrapper.put("baz", "baz value"));
		assertEquals(this.map.get("foo"), this.wrapper.get("foo"));
		assertEquals(this.map.get("bar"), this.wrapper.get("bar"));
		assertEquals(this.map.get("baz"), this.wrapper.get("baz"));
	}

	public void testRemove() throws Exception {
		this.wrapper.put("foo", "foo value 1");
		this.wrapper.put("foo", "foo value 2");
		this.wrapper.put("bar", "bar value");
		this.wrapper.put("baz", "baz value");
		assertEquals(this.map.get("foo"), this.wrapper.get("foo"));
		assertEquals(this.map.get("bar"), this.wrapper.get("bar"));
		assertEquals(this.map.get("baz"), this.wrapper.get("baz"));

		assertEquals("baz value", this.wrapper.remove("baz"));
		assertEquals(this.map.get("foo"), this.wrapper.get("foo"));
		assertEquals(this.map.get("bar"), this.wrapper.get("bar"));
		assertEquals(this.map.get("baz"), this.wrapper.get("baz"));

		assertEquals("bar value", this.wrapper.remove("bar"));
		assertEquals(this.map.get("foo"), this.wrapper.get("foo"));
		assertEquals(this.map.get("bar"), this.wrapper.get("bar"));
		assertEquals(this.map.get("baz"), this.wrapper.get("baz"));

		assertEquals("foo value 2", this.wrapper.remove("foo"));
		assertEquals(this.map.get("foo"), this.wrapper.get("foo"));
		assertEquals(this.map.get("bar"), this.wrapper.get("bar"));
		assertEquals(this.map.get("baz"), this.wrapper.get("baz"));

		assertEquals(null, this.wrapper.remove("foo"));
		assertEquals(this.map.get("foo"), this.wrapper.get("foo"));
		assertEquals(this.map.get("bar"), this.wrapper.get("bar"));
		assertEquals(this.map.get("baz"), this.wrapper.get("baz"));
	}

	public void testPutAll() throws Exception {
		Map<String, String> map2 = new HashMap<>();
		map2.put("foo", "foo value 1");
		map2.put("foo", "foo value 2");
		map2.put("bar", "bar value");
		map2.put("baz", "baz value");
		this.wrapper.putAll(map2);
		assertEquals("foo value 2", this.map.get("foo"));
		assertEquals("bar value", this.map.get("bar"));
		assertEquals("baz value", this.map.get("baz"));
		assertEquals(this.map.get("foo"), this.wrapper.get("foo"));
		assertEquals(this.map.get("bar"), this.wrapper.get("bar"));
		assertEquals(this.map.get("baz"), this.wrapper.get("baz"));
	}

	public void testClear() throws Exception {
		this.wrapper.put("foo", "foo value 1");
		this.wrapper.put("foo", "foo value 2");
		this.wrapper.put("bar", "bar value");
		this.wrapper.put("baz", "baz value");
		assertEquals(this.map.get("foo"), this.wrapper.get("foo"));
		assertEquals(this.map.get("bar"), this.wrapper.get("bar"));
		assertEquals(this.map.get("baz"), this.wrapper.get("baz"));

		this.wrapper.clear();
		assertTrue(this.wrapper.isEmpty());
		assertEquals(this.map.get("foo"), this.wrapper.get("foo"));
		assertEquals(this.map.get("bar"), this.wrapper.get("bar"));
		assertEquals(this.map.get("baz"), this.wrapper.get("baz"));
	}

	public void testCtor_NPE_map() throws Exception {
		boolean exCaught = false;
		try {
			ReadWriteLockMapWrapper<String, String> w = MapTools.readWriteLockWrapper(null);
			fail("bogus: " + w);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testCtor_NPE_lock() throws Exception {
		boolean exCaught = false;
		try {
			ReadWriteLockMapWrapper<String, String> w = MapTools.readWriteLockWrapper(this.map, null);
			fail("bogus: " + w);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}
}

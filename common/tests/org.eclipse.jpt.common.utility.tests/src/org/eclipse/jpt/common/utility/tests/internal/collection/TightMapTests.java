/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import junit.framework.TestCase;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.collection.CollectionTools;
import org.eclipse.jpt.common.utility.internal.collection.TightMap;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;

@SuppressWarnings("nls")
public class TightMapTests
	extends TestCase
{
	private TightMap<String, String> map;

	public TightMapTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.map = this.buildMap();
	}

	private TightMap<String, String> buildMap() {
		TightMap<String, String> m = new TightMap<String, String>();
		this.populateMap(m);
		return m;
	}

	private void populateMap(Map<? super String, ? super String> m) {
		m.put(null, "null");
		m.put("null", null);
		m.put("1", "one");
		m.put("2", "two");
		m.put("3", "three");
		m.put("4", "four");
	}

	@Override
	protected void tearDown() throws Exception {
		TestTools.clear(this);
		super.tearDown();
	}

	public void testSize() {
		assertEquals(6, this.map.size());

		this.map = new TightMap<String, String>();
		assertEquals(0, this.map.size());
	}

	public void testIsEmpty() {
		assertFalse(this.map.isEmpty());

		this.map = new TightMap<String, String>();
		assertTrue(this.map.isEmpty());
	}

	public void testContainsValue() {
		assertTrue(this.map.containsValue("null"));
		assertTrue(this.map.containsValue(null));
		assertTrue(this.map.containsValue("two"));
		assertFalse(this.map.containsValue("2"));
		assertFalse(this.map.containsValue("five"));

		this.map = new TightMap<String, String>();
		assertFalse(this.map.containsValue("null"));
		assertFalse(this.map.containsValue(null));
		assertFalse(this.map.containsValue("two"));
		assertFalse(this.map.containsValue("2"));
		assertFalse(this.map.containsValue("five"));
	}

	public void testContainsKey() {
		assertTrue(this.map.containsKey("null"));
		assertTrue(this.map.containsKey(null));
		assertFalse(this.map.containsKey("two"));
		assertTrue(this.map.containsKey("2"));
		assertFalse(this.map.containsKey("five"));

		this.map = new TightMap<String, String>();
		assertFalse(this.map.containsKey("null"));
		assertFalse(this.map.containsKey(null));
		assertFalse(this.map.containsKey("two"));
		assertFalse(this.map.containsKey("2"));
		assertFalse(this.map.containsKey("five"));
	}

	public void testGet() {
		assertEquals(null, this.map.get("null"));
		assertEquals("null", this.map.get(null));
		assertEquals(null, this.map.get("two"));
		assertEquals("two", this.map.get("2"));
		assertEquals(null, this.map.get("five"));

		this.map = new TightMap<String, String>();
		assertEquals(null, this.map.get("null"));
		assertEquals(null, this.map.get(null));
		assertEquals(null, this.map.get("two"));
		assertEquals(null, this.map.get("2"));
		assertEquals(null, this.map.get("five"));
	}

	public void testPut() {
		assertEquals(null, this.map.put("null", "seven"));
		assertEquals("seven", this.map.get("null"));
		assertEquals(6, this.map.size());
		assertTrue(this.map.containsKey("null"));
		assertTrue(this.map.containsValue("seven"));

		assertEquals("null", this.map.put(null, "eight"));
		assertEquals("eight", this.map.get(null));
		assertEquals(6, this.map.size());
		assertTrue(this.map.containsKey(null));
		assertTrue(this.map.containsValue("eight"));

		assertEquals(null, this.map.put("5", "five"));
		assertEquals("five", this.map.get("5"));
		assertEquals(7, this.map.size());
		assertTrue(this.map.containsKey("5"));
		assertTrue(this.map.containsValue("five"));

		this.map = new TightMap<String, String>();

		assertEquals(null, this.map.put("null", "seven"));
		assertEquals("seven", this.map.get("null"));
		assertEquals(1, this.map.size());
		assertTrue(this.map.containsKey("null"));
		assertTrue(this.map.containsValue("seven"));

		assertEquals(null, this.map.put(null, "eight"));
		assertEquals("eight", this.map.get(null));
		assertEquals(2, this.map.size());
		assertTrue(this.map.containsKey(null));
		assertTrue(this.map.containsValue("eight"));

		assertEquals(null, this.map.put("5", "five"));
		assertEquals("five", this.map.get("5"));
		assertEquals(3, this.map.size());
		assertTrue(this.map.containsKey("5"));
		assertTrue(this.map.containsValue("five"));
	}

	public void testRemove() {
		assertEquals(null, this.map.get("null"));
		assertTrue(this.map.containsKey("null"));
		assertEquals(6, this.map.size());
		assertTrue(this.map.containsValue(null));
		assertEquals(null, this.map.remove("null"));
		assertEquals(null, this.map.get("null"));
		assertFalse(this.map.containsKey("null"));
		assertEquals(5, this.map.size());
		assertFalse(this.map.containsValue(null));

		assertEquals("null", this.map.get(null));
		assertTrue(this.map.containsKey(null));
		assertTrue(this.map.containsValue("null"));
		assertEquals("null", this.map.remove(null));
		assertEquals(null, this.map.get(null));
		assertFalse(this.map.containsKey(null));
		assertEquals(4, this.map.size());
		assertFalse(this.map.containsValue("null"));

		assertEquals("three", this.map.get("3"));
		assertTrue(this.map.containsKey("3"));
		assertTrue(this.map.containsValue("three"));
		assertEquals("three", this.map.remove("3"));
		assertEquals(null, this.map.get("3"));
		assertFalse(this.map.containsKey("3"));
		assertEquals(3, this.map.size());
		assertFalse(this.map.containsValue("null"));

		assertEquals(null, this.map.get("7"));
		assertFalse(this.map.containsKey("3"));
		assertFalse(this.map.containsValue("seven"));
		assertEquals(null, this.map.remove("7"));
		assertEquals(null, this.map.get("7"));
		assertFalse(this.map.containsKey("7"));
		assertEquals(3, this.map.size());
		assertFalse(this.map.containsValue("seven"));
	}

	public void testPutAll() {
		assertEquals(6, this.map.size());
		HashMap<String, String> map2 = new HashMap<String, String>();
		map2.put("8", "eight");
		map2.put("9", "nine");
		map2.put("10", "ten");
		this.map.putAll(map2);
		assertEquals(9, this.map.size());
		assertEquals("eight", this.map.get("8"));
		assertEquals("nine", this.map.get("9"));
		assertEquals("ten", this.map.get("10"));
	}

	public void testPutAll_emptyMap() {
		assertEquals(6, this.map.size());
		HashMap<String, String> map2 = new HashMap<String, String>();
		this.map.putAll(map2);
		assertEquals(6, this.map.size());
	}

	public void testClear() {
		assertEquals(6, this.map.size());
		assertTrue(this.map.containsKey("1"));
		assertEquals("one", this.map.get("1"));
		this.map.clear();
		assertEquals(0, this.map.size());
		assertEquals(null, this.map.get("1"));
		assertFalse(this.map.containsKey("1"));
	}

	public void testKeySet_iterator_hasNext() {
		Set<String> keySet = this.map.keySet();
		assertEquals(6, keySet.size());
		assertEquals(6, IteratorTools.size(keySet.iterator()));
	}

	public void testKeySet_iterator_next1() {
		Set<String> keySet = this.map.keySet();
		HashMap<String, String> map2 = new HashMap<String, String>();
		this.populateMap(map2);
		for (String entry : map2.keySet()) {
			assertTrue(IteratorTools.contains(keySet.iterator(), entry));
		}
	}

	public void testKeySet_iterator_next2() {
		Set<String> keySet = this.map.keySet();
		HashMap<String, String> map2 = new HashMap<String, String>();
		this.populateMap(map2);
		Set<String> keySet2 = map2.keySet();
		for (Iterator<String> stream = keySet.iterator(); stream.hasNext(); ) {
			keySet2.contains(stream.next());
		}
	}

	public void testKeySet_iterator_next_CME() {
		Set<String> keySet = this.map.keySet();
		Iterator<String> stream = keySet.iterator();
		this.map.remove("1");
		boolean exCaught = false;
		try {
			stream.next();
		} catch (ConcurrentModificationException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testKeySet_iterator_next_NSEE() {
		Set<String> keySet = this.map.keySet();
		assertEquals(6, keySet.size());
		int size = 0;
		Iterator<String> stream = keySet.iterator();
		while (stream.hasNext()) {
			stream.next();
			size++;
		}
		assertEquals(6, size);
		boolean exCaught = false;
		try {
			stream.next();
		} catch (NoSuchElementException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testKeySet_iterator_remove1() {
		assertEquals(6, this.map.size());
		assertTrue(this.map.containsKey("2"));
		assertTrue(this.map.containsValue("two"));
		Set<String> keySet = this.map.keySet();
		assertEquals(6, keySet.size());

		Iterator<String> stream = keySet.iterator();
		assertTrue(stream.hasNext());
		String key = stream.next();
		assertEquals(null, key);
		assertTrue(stream.hasNext());
		key = stream.next();
		assertEquals("null", key);
		assertTrue(stream.hasNext());
		key = stream.next();
		assertEquals("1", key);
		assertTrue(stream.hasNext());
		key = stream.next();
		assertEquals("2", key);

		stream.remove();

		assertTrue(stream.hasNext());
		key = stream.next();
		assertEquals("3", key);
		assertTrue(stream.hasNext());
		key = stream.next();
		assertEquals("4", key);
		assertFalse(stream.hasNext());

		assertEquals(5, keySet.size());
		assertFalse(this.map.containsKey("2"));
		assertFalse(this.map.containsValue("two"));
		assertEquals(5, this.map.size());
	}

	public void testKeySet_iterator_remove2() {
		assertEquals(6, this.map.size());
		assertTrue(this.map.containsKey("2"));
		assertTrue(this.map.containsValue("two"));
		Set<String> keySet = this.map.keySet();
		assertEquals(6, keySet.size());
		for (Iterator<String> stream = keySet.iterator(); stream.hasNext(); ) {
			String key = stream.next();
			if (ObjectTools.equals(key, "2")) {
				stream.remove();
			}
		}
		assertEquals(5, keySet.size());
		assertFalse(this.map.containsKey("2"));
		assertFalse(this.map.containsValue("two"));
		assertEquals(5, this.map.size());
	}

	public void testKeySet_iterator_remove_CME() {
		Set<String> keySet = this.map.keySet();
		boolean exCaught = false;
		for (Iterator<String> stream = keySet.iterator(); stream.hasNext(); ) {
			String key = stream.next();
			if (ObjectTools.equals(key, "2")) {
				this.map.remove("1");
				try {
					stream.remove();
					fail();
				} catch (ConcurrentModificationException ex) {
					exCaught = true;
					break;  // expected - stop loop
				}
			}
		}
		assertTrue(exCaught);
	}

	public void testKeySet_iterator_remove_ISE() {
		Set<String> keySet = this.map.keySet();
		boolean exCaught = false;
		for (Iterator<String> stream = keySet.iterator(); stream.hasNext(); ) {
			String key = stream.next();
			if (ObjectTools.equals(key, "2")) {
				stream.remove();
				try {
					stream.remove();
					fail();
				} catch (IllegalStateException ex) {
					exCaught = true;
					break;  // expected - stop loop
				}
			}
		}
		assertTrue(exCaught);
	}

	public void testKeySet_size() {
		Set<String> keySet = this.map.keySet();
		assertEquals(6, keySet.size());
	}

	public void testKeySet_contains() {
		Set<String> keySet = this.map.keySet();
		HashMap<String, String> map2 = new HashMap<String, String>();
		this.populateMap(map2);
		for (String entry : map2.keySet()) {
			assertTrue(keySet.contains(entry));
		}
	}

	public void testKeySet_remove1() {
		Set<String> keySet = this.map.keySet();
		assertTrue(keySet.remove("2"));
		assertFalse(keySet.contains("2"));
		assertFalse(this.map.containsKey("2"));
	}

	public void testKeySet_remove2() {
		assertEquals(6, this.map.size());
		Set<String> keySet = this.map.keySet();
		assertEquals(6, keySet.size());
		assertFalse(keySet.remove("88"));
		assertEquals(6, keySet.size());
		assertEquals(6, this.map.size());
	}

	public void testKeySet_clear() {
		assertEquals(6, this.map.size());
		Set<String> keySet = this.map.keySet();
		assertEquals(6, keySet.size());
		keySet.clear();
		assertEquals(0, keySet.size());
		assertEquals(0, this.map.size());
	}

	public void testKeySet_equals() {
		Set<String> keySet = this.map.keySet();
		HashMap<String, String> map2 = new HashMap<String, String>();
		this.populateMap(map2);
		assertTrue(keySet.equals(map2.keySet()));
		assertTrue(map2.keySet().equals(keySet));
	}

	public void testKeySet_hashCode() {
		Set<String> keySet = this.map.keySet();
		HashMap<String, String> map2 = new HashMap<String, String>();
		this.populateMap(map2);
		assertEquals(keySet.hashCode(), map2.keySet().hashCode());
	}

	public void testKeySet_removeAll() {
		ArrayList<String> remove = new ArrayList<String>();
		remove.add(null);
		remove.add("1");

		Set<String> keySet = this.map.keySet();
		assertTrue(keySet.removeAll(remove));

		HashMap<String, String> map2 = new HashMap<String, String>();
		this.populateMap(map2);
		Set<String> keySet2 = map2.keySet();
		assertTrue(keySet2.removeAll(remove));

		assertEquals(keySet2, keySet);
	}

	public void testKeySet_add() {
		Set<String> keySet = this.map.keySet();
		boolean exCaught = false;
		try {
			keySet.add("88");
		} catch (UnsupportedOperationException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testKeySet_addAll() {
		ArrayList<String> add = new ArrayList<String>();
		add.add(null);
		add.add("1");

		Set<String> keySet = this.map.keySet();
		boolean exCaught = false;
		try {
			keySet.addAll(add);
		} catch (UnsupportedOperationException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testKeySet_containsAll() {
		ArrayList<String> keys = new ArrayList<String>();
		keys.add(null);
		keys.add("1");

		Set<String> keySet = this.map.keySet();
		assertTrue(keySet.containsAll(keys));
	}

	public void testKeySet_isEmpty() {
		Set<String> keySet = this.map.keySet();
		assertFalse(keySet.isEmpty());
		keySet.clear();
		assertTrue(keySet.isEmpty());
	}

	public void testKeySet_retainAll() {
		ArrayList<String> retain = new ArrayList<String>();
		retain.add(null);
		retain.add("1");

		Set<String> keySet = this.map.keySet();
		assertTrue(keySet.retainAll(retain));

		HashMap<String, String> map2 = new HashMap<String, String>();
		this.populateMap(map2);
		Set<String> keySet2 = map2.keySet();
		assertTrue(keySet2.retainAll(retain));

		assertEquals(keySet2, keySet);
	}

	public void testKeySet_toArray() {
		Set<String> keySet = this.map.keySet();
		Object[] keys = keySet.toArray();

		HashMap<String, String> map2 = new HashMap<String, String>();
		this.populateMap(map2);
		Set<String> keySet2 = map2.keySet();
		assertEquals(keys.length, keySet2.size());
		for (String entry : map2.keySet()) {
			assertTrue(ArrayTools.contains(keys, entry));
		}
		for (Object key : keys) {
			assertTrue(keySet2.contains(key));
		}
	}

	public void testKeySet_toArray2() {
		Set<String> keySet = this.map.keySet();
		String[] keys = keySet.toArray(StringTools.EMPTY_STRING_ARRAY);

		HashMap<String, String> map2 = new HashMap<String, String>();
		this.populateMap(map2);
		Set<String> keySet2 = map2.keySet();
		assertEquals(keys.length, keySet2.size());
		for (String entry : map2.keySet()) {
			assertTrue(ArrayTools.contains(keys, entry));
		}
		for (String key : keys) {
			assertTrue(keySet2.contains(key));
		}
	}

	public void testKeySet_toString() {
		String expected = "[null, null, 1, 2, 3, 4]";
		assertEquals(expected, this.map.keySet().toString());
	}

	public void testValues_iterator_hasNext() {
		Collection<String> values = this.map.values();
		assertEquals(6, values.size());
		assertEquals(6, IteratorTools.size(values.iterator()));
	}

	public void testValues_iterator_next1() {
		Collection<String> values = this.map.values();
		HashMap<String, String> map2 = new HashMap<String, String>();
		this.populateMap(map2);
		for (String entry : map2.values()) {
			assertTrue(IteratorTools.contains(values.iterator(), entry));
		}
	}

	public void testValues_iterator_next2() {
		Collection<String> values = this.map.values();
		HashMap<String, String> map2 = new HashMap<String, String>();
		this.populateMap(map2);
		Collection<String> values2 = map2.values();
		for (Iterator<String> stream = values.iterator(); stream.hasNext(); ) {
			values2.contains(stream.next());
		}
	}

	public void testValues_iterator_next_CME() {
		Collection<String> values = this.map.values();
		Iterator<String> stream = values.iterator();
		this.map.remove("1");
		boolean exCaught = false;
		try {
			stream.next();
		} catch (ConcurrentModificationException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testValues_iterator_next_NSEE() {
		Collection<String> values = this.map.values();
		assertEquals(6, values.size());
		int size = 0;
		Iterator<String> stream = values.iterator();
		while (stream.hasNext()) {
			stream.next();
			size++;
		}
		assertEquals(6, size);
		boolean exCaught = false;
		try {
			stream.next();
		} catch (NoSuchElementException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testValues_iterator_remove1() {
		assertEquals(6, this.map.size());
		assertTrue(this.map.containsKey("2"));
		assertTrue(this.map.containsValue("two"));
		Collection<String> values = this.map.values();
		assertEquals(6, values.size());

		Iterator<String> stream = values.iterator();
		assertTrue(stream.hasNext());
		String value = stream.next();
		assertEquals("null", value);
		assertTrue(stream.hasNext());
		value = stream.next();
		assertEquals(null, value);
		assertTrue(stream.hasNext());
		value = stream.next();
		assertEquals("one", value);
		assertTrue(stream.hasNext());
		value = stream.next();
		assertEquals("two", value);

		stream.remove();

		assertTrue(stream.hasNext());
		value = stream.next();
		assertEquals("three", value);
		assertTrue(stream.hasNext());
		value = stream.next();
		assertEquals("four", value);
		assertFalse(stream.hasNext());

		assertEquals(5, values.size());
		assertFalse(this.map.containsKey("2"));
		assertFalse(this.map.containsValue("two"));
		assertEquals(5, this.map.size());
	}

	public void testValues_iterator_remove2() {
		assertEquals(6, this.map.size());
		assertTrue(this.map.containsKey("2"));
		assertTrue(this.map.containsValue("two"));
		Collection<String> values = this.map.values();
		assertEquals(6, values.size());
		for (Iterator<String> stream = values.iterator(); stream.hasNext(); ) {
			String value = stream.next();
			if (ObjectTools.equals(value, "two")) {
				stream.remove();
			}
		}
		assertEquals(5, values.size());
		assertFalse(this.map.containsKey("2"));
		assertFalse(this.map.containsValue("two"));
		assertEquals(5, this.map.size());
	}

	public void testValues_iterator_remove_CME() {
		Collection<String> values = this.map.values();
		boolean exCaught = false;
		for (Iterator<String> stream = values.iterator(); stream.hasNext(); ) {
			String value = stream.next();
			if (ObjectTools.equals(value, "two")) {
				this.map.remove("1");
				try {
					stream.remove();
					fail();
				} catch (ConcurrentModificationException ex) {
					exCaught = true;
					break;  // expected - stop loop
				}
			}
		}
		assertTrue(exCaught);
	}

	public void testValues_iterator_remove_ISE() {
		Collection<String> values = this.map.values();
		boolean exCaught = false;
		for (Iterator<String> stream = values.iterator(); stream.hasNext(); ) {
			String value = stream.next();
			if (ObjectTools.equals(value, "two")) {
				stream.remove();
				try {
					stream.remove();
					fail();
				} catch (IllegalStateException ex) {
					exCaught = true;
					break;  // expected - stop loop
				}
			}
		}
		assertTrue(exCaught);
	}

	public void testValues_size() {
		Collection<String> values = this.map.values();
		assertEquals(6, values.size());
	}

	public void testValues_contains() {
		Collection<String> values = this.map.values();
		HashMap<String, String> map2 = new HashMap<String, String>();
		this.populateMap(map2);
		for (String entry : map2.values()) {
			assertTrue(values.contains(entry));
		}
	}

	public void testValues_remove1() {
		Collection<String> values = this.map.values();
		assertTrue(values.remove("two"));
		assertFalse(values.contains("two"));
		assertFalse(this.map.containsKey("2"));
	}

	public void testValues_remove2() {
		assertEquals(6, this.map.size());
		Collection<String> values = this.map.values();
		assertEquals(6, values.size());
		assertFalse(values.remove("eleventy"));
		assertEquals(6, values.size());
		assertEquals(6, this.map.size());
	}

	public void testValues_clear() {
		assertEquals(6, this.map.size());
		Collection<String> values = this.map.values();
		assertEquals(6, values.size());
		values.clear();
		assertEquals(0, values.size());
		assertEquals(0, this.map.size());
	}

	public void testValues_equals() {
		Collection<String> values = this.map.values();
		HashMap<String, String> map2 = new HashMap<String, String>();
		this.populateMap(map2);
		assertTrue(CollectionTools.bag(values).equals(CollectionTools.bag(map2.values())));
	}

	public void testValues_hashCode() {
		Collection<String> values = this.map.values();
		HashMap<String, String> map2 = new HashMap<String, String>();
		this.populateMap(map2);
		assertEquals(CollectionTools.bag(values).hashCode(), CollectionTools.bag(map2.values()).hashCode());
	}

	public void testValues_removeAll() {
		ArrayList<String> remove = new ArrayList<String>();
		remove.add(null);
		remove.add("two");

		Collection<String> values = this.map.values();
		assertTrue(values.removeAll(remove));

		HashMap<String, String> map2 = new HashMap<String, String>();
		this.populateMap(map2);
		Collection<String> values2 = map2.values();
		assertTrue(values2.removeAll(remove));

		assertEquals(CollectionTools.bag(values2), CollectionTools.bag(values));
	}

	public void testValues_add() {
		Collection<String> values = this.map.values();
		boolean exCaught = false;
		try {
			values.add("eleventy");
		} catch (UnsupportedOperationException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testValues_addAll() {
		ArrayList<String> add = new ArrayList<String>();
		add.add(null);
		add.add("one");

		Collection<String> values = this.map.values();
		boolean exCaught = false;
		try {
			values.addAll(add);
		} catch (UnsupportedOperationException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testValues_containsAll() {
		ArrayList<String> strings = new ArrayList<String>();
		strings.add(null);
		strings.add("two");

		Collection<String> values = this.map.values();
		assertTrue(values.containsAll(strings));
	}

	public void testValues_isEmpty() {
		Collection<String> values = this.map.values();
		assertFalse(values.isEmpty());
		values.clear();
		assertTrue(values.isEmpty());
	}

	public void testValues_retainAll() {
		ArrayList<String> retain = new ArrayList<String>();
		retain.add(null);
		retain.add("one");

		Collection<String> values = this.map.values();
		assertTrue(values.retainAll(retain));

		HashMap<String, String> map2 = new HashMap<String, String>();
		this.populateMap(map2);
		Collection<String> values2 = map2.values();
		assertTrue(values2.retainAll(retain));

		assertEquals(CollectionTools.bag(values2), CollectionTools.bag(values));
	}

	public void testValues_toArray() {
		Collection<String> values = this.map.values();
		Object[] strings = values.toArray();

		HashMap<String, String> map2 = new HashMap<String, String>();
		this.populateMap(map2);
		Collection<String> values2 = map2.values();
		assertEquals(strings.length, values2.size());
		for (String entry : map2.values()) {
			assertTrue(ArrayTools.contains(strings, entry));
		}
		for (Object string : strings) {
			assertTrue(values2.contains(string));
		}
	}

	public void testValues_toArray2() {
		Collection<String> values = this.map.values();
		String[] strings = values.toArray(StringTools.EMPTY_STRING_ARRAY);

		HashMap<String, String> map2 = new HashMap<String, String>();
		this.populateMap(map2);
		Collection<String> values2 = map2.values();
		assertEquals(strings.length, values2.size());
		for (String entry : map2.values()) {
			assertTrue(ArrayTools.contains(strings, entry));
		}
		for (String string : strings) {
			assertTrue(values2.contains(string));
		}
	}

	public void testValues_toString() {
		String expected = "[null, null, one, two, three, four]";
		assertEquals(expected, this.map.values().toString());
	}

	public void testEntrySet_iterator_hasNext() {
		Set<Map.Entry<String, String>> entrySet = this.map.entrySet();
		assertEquals(6, entrySet.size());
		assertEquals(6, IteratorTools.size(entrySet.iterator()));
	}

	public void testEntrySet_iterator_next1() {
		Set<Map.Entry<String, String>> entrySet = this.map.entrySet();
		HashMap<String, String> map2 = new HashMap<String, String>();
		this.populateMap(map2);
		for (Map.Entry<String, String> entry : map2.entrySet()) {
			assertTrue(IteratorTools.contains(entrySet.iterator(), entry));
		}
	}

	public void testEntrySet_iterator_next2() {
		Set<Map.Entry<String, String>> entrySet = this.map.entrySet();
		HashMap<String, String> map2 = new HashMap<String, String>();
		this.populateMap(map2);
		Set<Map.Entry<String, String>> entrySet2 = map2.entrySet();
		for (Iterator<Map.Entry<String, String>> stream = entrySet.iterator(); stream.hasNext(); ) {
			entrySet2.contains(stream.next());
		}
	}

	public void testEntrySet_iterator_next_CME() {
		Set<Map.Entry<String, String>> entrySet = this.map.entrySet();
		Iterator<Map.Entry<String, String>> stream = entrySet.iterator();
		this.map.remove("1");
		boolean exCaught = false;
		try {
			stream.next();
		} catch (ConcurrentModificationException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testEntrySet_iterator_next_NSEE() {
		Set<Map.Entry<String, String>> entrySet = this.map.entrySet();
		assertEquals(6, entrySet.size());
		int size = 0;
		Iterator<Map.Entry<String, String>> stream = entrySet.iterator();
		while (stream.hasNext()) {
			stream.next();
			size++;
		}
		assertEquals(6, size);
		boolean exCaught = false;
		try {
			stream.next();
		} catch (NoSuchElementException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testEntrySet_iterator_remove1() {
		assertEquals(6, this.map.size());
		assertTrue(this.map.containsKey("2"));
		assertTrue(this.map.containsValue("two"));
		Set<Map.Entry<String, String>> entrySet = this.map.entrySet();
		assertEquals(6, entrySet.size());

		Iterator<Map.Entry<String, String>> stream = entrySet.iterator();
		assertTrue(stream.hasNext());
		Map.Entry<String, String> entry = stream.next();
		assertEquals(null, entry.getKey());
		assertEquals("null", entry.getValue());
		assertTrue(stream.hasNext());
		entry = stream.next();
		assertEquals("null", entry.getKey());
		assertEquals(null, entry.getValue());
		assertTrue(stream.hasNext());
		entry = stream.next();
		assertEquals("1", entry.getKey());
		assertEquals("one", entry.getValue());
		assertTrue(stream.hasNext());
		entry = stream.next();
		assertEquals("2", entry.getKey());
		assertEquals("two", entry.getValue());

		stream.remove();

		assertTrue(stream.hasNext());
		entry = stream.next();
		assertEquals("3", entry.getKey());
		assertEquals("three", entry.getValue());
		assertTrue(stream.hasNext());
		entry = stream.next();
		assertEquals("4", entry.getKey());
		assertEquals("four", entry.getValue());
		assertFalse(stream.hasNext());

		assertEquals(5, entrySet.size());
		assertFalse(this.map.containsKey("2"));
		assertFalse(this.map.containsValue("two"));
		assertEquals(5, this.map.size());
	}

	public void testEntrySet_iterator_remove2() {
		assertEquals(6, this.map.size());
		assertTrue(this.map.containsKey("2"));
		assertTrue(this.map.containsValue("two"));
		Set<Map.Entry<String, String>> entrySet = this.map.entrySet();
		assertEquals(6, entrySet.size());
		for (Iterator<Map.Entry<String, String>> stream = entrySet.iterator(); stream.hasNext(); ) {
			Map.Entry<String, String> entry = stream.next();
			if (ObjectTools.equals(entry.getKey(), "2")) {
				stream.remove();
			}
		}
		assertEquals(5, entrySet.size());
		assertFalse(this.map.containsKey("2"));
		assertFalse(this.map.containsValue("two"));
		assertEquals(5, this.map.size());
	}

	public void testEntrySet_iterator_remove_CME() {
		Set<Map.Entry<String, String>> entrySet = this.map.entrySet();
		boolean exCaught = false;
		for (Iterator<Map.Entry<String, String>> stream = entrySet.iterator(); stream.hasNext(); ) {
			Map.Entry<String, String> entry = stream.next();
			if (ObjectTools.equals(entry.getKey(), "2")) {
				this.map.remove("1");
				try {
					stream.remove();
					fail();
				} catch (ConcurrentModificationException ex) {
					exCaught = true;
					break;  // expected - stop loop
				}
			}
		}
		assertTrue(exCaught);
	}

	public void testEntrySet_iterator_remove_ISE() {
		Set<Map.Entry<String, String>> entrySet = this.map.entrySet();
		boolean exCaught = false;
		for (Iterator<Map.Entry<String, String>> stream = entrySet.iterator(); stream.hasNext(); ) {
			Map.Entry<String, String> entry = stream.next();
			if (ObjectTools.equals(entry.getKey(), "2")) {
				stream.remove();
				try {
					stream.remove();
					fail();
				} catch (IllegalStateException ex) {
					exCaught = true;
					break;  // expected - stop loop
				}
			}
		}
		assertTrue(exCaught);
	}

	public void testEntrySet_size() {
		Set<Map.Entry<String, String>> entrySet = this.map.entrySet();
		assertEquals(6, entrySet.size());
	}

	public void testEntrySet_contains() {
		Set<Map.Entry<String, String>> entrySet = this.map.entrySet();
		HashMap<String, String> map2 = new HashMap<String, String>();
		this.populateMap(map2);
		for (Map.Entry<String, String> entry : map2.entrySet()) {
			assertTrue(entrySet.contains(entry));
		}
	}

	@SuppressWarnings("null")
	public void testEntrySet_remove1() {
		Set<Map.Entry<String, String>> entrySet = this.map.entrySet();

		Map.Entry<String, String> remove = null;
		HashMap<String, String> map2 = new HashMap<String, String>();
		this.populateMap(map2);
		for (Map.Entry<String, String> entry : map2.entrySet()) {
			if (ObjectTools.equals(entry.getKey(), "2")) {
				remove = entry;
			}
		}

		assertTrue(entrySet.contains(remove));
		assertTrue(this.map.containsKey(remove.getKey()));
		assertTrue(this.map.containsValue(remove.getValue()));

		assertTrue(entrySet.remove(remove));
		assertFalse(entrySet.contains(remove));
		assertFalse(this.map.containsKey(remove.getKey()));
		assertFalse(this.map.containsValue(remove.getValue()));
	}

	public void testEntrySet_remove2() {
		assertEquals(6, this.map.size());
		Set<Map.Entry<String, String>> entrySet = this.map.entrySet();
		assertEquals(6, entrySet.size());
		assertFalse(entrySet.remove("eleventy"));
		assertEquals(6, entrySet.size());
		assertEquals(6, this.map.size());
	}

	public void testEntrySet_clear() {
		assertEquals(6, this.map.size());
		Set<Map.Entry<String, String>> entrySet = this.map.entrySet();
		assertEquals(6, entrySet.size());
		entrySet.clear();
		assertEquals(0, entrySet.size());
		assertEquals(0, this.map.size());
	}

	public void testEntrySet_equals() {
		Set<Map.Entry<String, String>> entrySet = this.map.entrySet();
		HashMap<String, String> map2 = new HashMap<String, String>();
		this.populateMap(map2);
		assertTrue(CollectionTools.bag(entrySet).equals(CollectionTools.bag(map2.entrySet())));
	}

	public void testEntrySet_hashCode() {
		Set<Map.Entry<String, String>> entrySet = this.map.entrySet();
		HashMap<String, String> map2 = new HashMap<String, String>();
		this.populateMap(map2);
		assertEquals(CollectionTools.bag(entrySet).hashCode(), CollectionTools.bag(map2.entrySet()).hashCode());
	}

	public void testEntrySet_removeAll() {
		Set<Map.Entry<String, String>> entrySet = this.map.entrySet();

		HashMap<String, String> map2 = new HashMap<String, String>();
		this.populateMap(map2);
		Set<Map.Entry<String, String>> entrySet2 = map2.entrySet();

		ArrayList<Map.Entry<String, String>> remove = new ArrayList<Map.Entry<String, String>>();
		for (Map.Entry<String, String> entry : entrySet2) {
			if (ObjectTools.equals(entry.getKey(), null)) {
				remove.add(entry);
			}
			if (ObjectTools.equals(entry.getKey(), "2")) {
				remove.add(entry);
			}
		}

		assertTrue(entrySet.removeAll(remove));
		assertTrue(entrySet2.removeAll(remove));

		assertEquals(CollectionTools.bag(entrySet2), CollectionTools.bag(entrySet));
	}

	public void testEntrySet_add() {
		Map.Entry<String, String> add = null;
		HashMap<String, String> map2 = new HashMap<String, String>();
		this.populateMap(map2);
		for (Map.Entry<String, String> entry : map2.entrySet()) {
			if (ObjectTools.equals(entry.getKey(), "2")) {
				add = entry;
			}
		}

		Set<Map.Entry<String, String>> entrySet = this.map.entrySet();
		boolean exCaught = false;
		try {
			entrySet.add(add);
		} catch (UnsupportedOperationException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testEntrySet_addAll() {
		HashMap<String, String> map2 = new HashMap<String, String>();
		this.populateMap(map2);
		Set<Map.Entry<String, String>> entrySet2 = map2.entrySet();

		ArrayList<Map.Entry<String, String>> add = new ArrayList<Map.Entry<String, String>>();
		for (Map.Entry<String, String> entry : entrySet2) {
			if (ObjectTools.equals(entry.getKey(), null)) {
				add.add(entry);
			}
			if (ObjectTools.equals(entry.getKey(), "2")) {
				add.add(entry);
			}
		}

		Set<Map.Entry<String, String>> entrySet = this.map.entrySet();
		boolean exCaught = false;
		try {
			entrySet.addAll(add);
		} catch (UnsupportedOperationException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testEntrySet_containsAll() {
		HashMap<String, String> map2 = new HashMap<String, String>();
		this.populateMap(map2);
		Set<Map.Entry<String, String>> entrySet2 = map2.entrySet();

		ArrayList<Map.Entry<String, String>> strings = new ArrayList<Map.Entry<String, String>>();
		for (Map.Entry<String, String> entry : entrySet2) {
			if (ObjectTools.equals(entry.getKey(), null)) {
				strings.add(entry);
			}
			if (ObjectTools.equals(entry.getKey(), "2")) {
				strings.add(entry);
			}
		}

		Set<Map.Entry<String, String>> entrySet = this.map.entrySet();
		assertTrue(entrySet.containsAll(strings));
	}

	public void testEntrySet_isEmpty() {
		Set<Map.Entry<String, String>> entrySet = this.map.entrySet();
		assertFalse(entrySet.isEmpty());
		entrySet.clear();
		assertTrue(entrySet.isEmpty());
	}

	public void testEntrySet_retainAll() {
		HashMap<String, String> map2 = new HashMap<String, String>();
		this.populateMap(map2);
		Set<Map.Entry<String, String>> entrySet2 = map2.entrySet();

		ArrayList<Map.Entry<String, String>> retain = new ArrayList<Map.Entry<String, String>>();
		for (Map.Entry<String, String> entry : entrySet2) {
			if (ObjectTools.equals(entry.getKey(), null)) {
				retain.add(entry);
			}
			if (ObjectTools.equals(entry.getKey(), "2")) {
				retain.add(entry);
			}
		}

		Set<Map.Entry<String, String>> entrySet = this.map.entrySet();
		assertTrue(entrySet.retainAll(retain));
		assertTrue(entrySet2.retainAll(retain));
		assertEquals(CollectionTools.bag(entrySet2), CollectionTools.bag(entrySet));
	}

	public void testEntrySet_toArray() {
		Set<Map.Entry<String, String>> entrySet = this.map.entrySet();
		Object[] strings = entrySet.toArray();

		HashMap<String, String> map2 = new HashMap<String, String>();
		this.populateMap(map2);
		Set<Map.Entry<String, String>> entrySet2 = map2.entrySet();
		assertEquals(strings.length, entrySet2.size());
		for (Map.Entry<String, String> entry : map2.entrySet()) {
			assertTrue(ArrayTools.contains(strings, entry));
		}
		for (Object string : strings) {
			assertTrue(entrySet2.contains(string));
		}
	}

	public void testEntrySet_toArray2() {
		Set<Map.Entry<String, String>> entrySet = this.map.entrySet();
		@SuppressWarnings("rawtypes")
		Map.Entry[] entries = entrySet.toArray(new Map.Entry[0]);

		HashMap<String, String> map2 = new HashMap<String, String>();
		this.populateMap(map2);
		Set<Map.Entry<String, String>> entrySet2 = map2.entrySet();
		assertEquals(entries.length, entrySet2.size());
		for (Map.Entry<String, String> entry : map2.entrySet()) {
			assertTrue(ArrayTools.contains(entries, entry));
		}
		for (@SuppressWarnings("rawtypes") Map.Entry entry : entries) {
			assertTrue(entrySet2.contains(entry));
		}
	}

	public void testEntrySet_toString() {
		String expected = "[null=null, null=null, 1=one, 2=two, 3=three, 4=four]";
		assertEquals(expected, this.map.entrySet().toString());
	}

	public void testEquals0() {
		assertTrue(this.map.equals(this.map));
	}

	public void testEquals1() {
		assertFalse(this.map.equals("map2"));
	}

	public void testEquals2() {
		HashMap<String, String> map2 = new HashMap<String, String>();
		assertFalse(this.map.equals(map2));
	}

	public void testEquals3() {
		HashMap<String, String> map2 = new HashMap<String, String>();
		this.populateMap(map2);
		map2.remove("1");
		assertFalse(this.map.equals(map2));
	}

	public void testEquals4() {
		HashMap<String, Integer> map2 = new HashMap<String, Integer>();
		map2.put(null, Integer.valueOf(0));
		map2.put("null", Integer.valueOf(0));
		map2.put("1", Integer.valueOf(1));
		map2.put("2", Integer.valueOf(2));
		map2.put("3", Integer.valueOf(3));
		map2.put("4", Integer.valueOf(4));
		assertFalse(this.map.equals(map2));
	}

	public void testEquals5() {
		HashMap<Integer, String> map2 = new HashMap<Integer, String>();
		map2.put(Integer.valueOf(0), null);
		map2.put(Integer.valueOf(0), "null");
		map2.put(Integer.valueOf(1), "1");
		map2.put(Integer.valueOf(2), "2");
		map2.put(Integer.valueOf(3), "3");
		map2.put(Integer.valueOf(4), "4");
		assertFalse(this.map.equals(map2));
	}

	public void testEquals6() {
		HashMap<String, String> map2 = new HashMap<String, String>();
		this.populateMap(map2);
		assertTrue(this.map.equals(map2));
	}

	public void testHashCode1() {
		HashMap<String, String> map2 = new HashMap<String, String>();
		this.populateMap(map2);
		assertEquals(this.map.hashCode(), map2.hashCode());
	}

	public void testHashCode2() {
		LinkedHashMap<String, String> map2 = new LinkedHashMap<String, String>();
		this.populateMap(map2);
		assertEquals(this.map.hashCode(), map2.hashCode());
	}

	public void testToString1() {
		String expected = "{null=null, null=null, 1=one, 2=two, 3=three, 4=four}";
		assertEquals(expected, this.map.toString());
	}

	public void testToString2() {
		this.map.clear();
		String expected = "{}";
		assertEquals(expected, this.map.toString());
	}

	public void testToString3() {
		TightMap<Object, Object> map2 = new TightMap<Object, Object>();
		this.populateMap(map2);
		map2.put(map2, "this");
		String expected = "{null=null, null=null, 1=one, 2=two, 3=three, 4=four, (this Map)=this}";
		assertEquals(expected, map2.toString());
	}

	public void testToString4() {
		TightMap<Object, Object> map2 = new TightMap<Object, Object>();
		this.populateMap(map2);
		map2.put("this", map2);
		String expected = "{null=null, null=null, 1=one, 2=two, 3=three, 4=four, this=(this Map)}";
		assertEquals(expected, map2.toString());
	}
}

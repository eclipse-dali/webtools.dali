/*******************************************************************************
 * Copyright (c) 2013, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.collection;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.eclipse.jpt.common.utility.factory.Factory;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.ClassTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.collection.MapTools;
import org.eclipse.jpt.common.utility.internal.factory.FactoryTools;
import org.eclipse.jpt.common.utility.internal.predicate.PredicateAdapter;
import org.eclipse.jpt.common.utility.internal.queue.QueueTools;
import org.eclipse.jpt.common.utility.internal.stack.StackTools;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.queue.Queue;
import org.eclipse.jpt.common.utility.stack.Stack;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import junit.framework.TestCase;

@SuppressWarnings("nls")
public class MapToolsTests
	extends TestCase
{
	public MapToolsTests(String name) {
		super(name);
	}

	public void testGetMapObjectObject() {
		Map<String, String> map = this.buildMap();
		assertEquals("one", MapTools.get(map, "1", ""));
		assertEquals("", MapTools.get(map, "7", ""));
		assertEquals("", map.get("7"));
		map.put("7", null);
		assertEquals(null, MapTools.get(map, "7", ""));
	}

	public void testGet_MapObjectObject() {
		Map<String, String> map = this.buildMap();
		assertEquals("one", MapTools.get_(map, "1", ""));
		assertEquals("", MapTools.get_(map, "7", ""));
		assertEquals("", map.get("7"));
		map.put("7", null);
		assertEquals("", MapTools.get_(map, "7", ""));
	}

	public void testGetMapObjectFactory() {
		Factory<String> factory = FactoryTools.staticFactory("");
		Map<String, String> map = this.buildMap();
		assertEquals("one", MapTools.get(map, "1", factory));
		assertEquals("", MapTools.get(map, "7", factory));
		assertEquals("", map.get("7"));
		map.put("7", null);
		assertEquals(null, MapTools.get(map, "7", factory));
	}

	public void testGet_MapObjectFactory() {
		Factory<String> factory = FactoryTools.staticFactory("");
		Map<String, String> map = this.buildMap();
		assertEquals("one", MapTools.get_(map, "1", factory));
		assertEquals("", MapTools.get_(map, "7", factory));
		assertEquals("", map.get("7"));
		map.put("7", null);
		assertEquals("", MapTools.get_(map, "7", factory));
	}

	@SuppressWarnings("unchecked")
	public void testGetMapObjectClass() {
		ArrayList<String> list = new ArrayList<String>();
		Map<String, ArrayList<String>> map = this.buildListMap();
		assertEquals(list, MapTools.get(map, "1", ArrayList.class));
		assertEquals(list, MapTools.get(map, "7", ArrayList.class));
		map.put("7", null);
		assertEquals(null, MapTools.get(map, "7", ArrayList.class));
	}

	@SuppressWarnings("unchecked")
	public void testGet_MapObjectClass() {
		ArrayList<String> list = new ArrayList<String>();
		Map<String, ArrayList<String>> map = this.buildListMap();
		assertEquals(list, MapTools.get_(map, "1", ArrayList.class));
		assertEquals(list, MapTools.get_(map, "7", ArrayList.class));
		map.put("7", null);
		assertEquals(list, MapTools.get_(map, "7", ArrayList.class));
	}

	@SuppressWarnings("unchecked")
	public void testGetMapObjectClassClassObject() {
		ArrayList<String> list = new ArrayList<String>();
		Map<String, ArrayList<String>> map = this.buildListMap();
		assertEquals(list, MapTools.get(map, "1", ArrayList.class, Collection.class, new ArrayList<String>()));
		assertEquals(list, MapTools.get(map, "7", ArrayList.class, Collection.class, new ArrayList<String>()));
		map.put("7", null);
		assertEquals(null, MapTools.get(map, "7", ArrayList.class, Collection.class, new ArrayList<String>()));
	}

	@SuppressWarnings("unchecked")
	public void testGet_MapObjectClassClassObject() {
		ArrayList<String> list = new ArrayList<String>();
		Map<String, ArrayList<String>> map = this.buildListMap();
		assertEquals(list, MapTools.get_(map, "1", ArrayList.class, Collection.class, new ArrayList<String>()));
		assertEquals(list, MapTools.get_(map, "7", ArrayList.class, Collection.class, new ArrayList<String>()));
		map.put("7", null);
		assertEquals(new ArrayList<String>(), MapTools.get_(map, "7", ArrayList.class, Collection.class, new ArrayList<String>()));
	}

	public void testAddMapObjectTransformer() {
		Map<String, String> map = new HashMap<String, String>();
		assertEquals(null, MapTools.add(map, "one", REVERSE_STRING_TRANSFORMER));
		assertEquals("one", map.get("eno"));
	}

	public void testAddMapObjectTransformerTransformer() {
		Map<String, String> map = new HashMap<String, String>();
		assertEquals(null, MapTools.add(map, "cczzaa", REVERSE_STRING_TRANSFORMER, SORT_STRING_TRANSFORMER));
		assertEquals("aacczz", map.get("aazzcc"));
	}

	public void testAddAllMapIterableTransformer() {
		ArrayList<String> strings = new ArrayList<String>();
		strings.add("foo");
		strings.add("bar");
		strings.add("baz");
		Map<String, String> map = new HashMap<String, String>();
		MapTools.addAll(map, strings, REVERSE_STRING_TRANSFORMER);
		assertEquals("foo", map.get("oof"));
		assertEquals("bar", map.get("rab"));
		assertEquals("baz", map.get("zab"));
	}

	public void testAddAllMapQueueTransformer() {
		ArrayList<String> strings = new ArrayList<String>();
		strings.add("foo");
		strings.add("bar");
		strings.add("baz");
		Queue<String> queue = QueueTools.arrayQueue(strings);
		Map<String, String> map = new HashMap<String, String>();
		QueueTools.drainTo(queue, map, REVERSE_STRING_TRANSFORMER);
		assertEquals("foo", map.get("oof"));
		assertEquals("bar", map.get("rab"));
		assertEquals("baz", map.get("zab"));
	}

	public void testAddAllMapStackTransformer() {
		ArrayList<String> strings = new ArrayList<String>();
		strings.add("foo");
		strings.add("bar");
		strings.add("baz");
		Stack<String> stack = StackTools.arrayStack(strings);
		Map<String, String> map = new HashMap<String, String>();
		StackTools.popAllTo(stack, map, REVERSE_STRING_TRANSFORMER);
		assertEquals("foo", map.get("oof"));
		assertEquals("bar", map.get("rab"));
		assertEquals("baz", map.get("zab"));
	}

	public void testAddAllMapTransformerArray() {
		Map<String, String> map = new HashMap<String, String>();
		MapTools.addAll(map, REVERSE_STRING_TRANSFORMER, "foo", "bar", "baz");
		assertEquals("foo", map.get("oof"));
		assertEquals("bar", map.get("rab"));
		assertEquals("baz", map.get("zab"));
	}

	public void testAddAllMapIterableTransformerTransformer() {
		ArrayList<String> strings = new ArrayList<String>();
		strings.add("foo");
		strings.add("bar");
		strings.add("baz");
		Map<String, String> map = new HashMap<String, String>();
		MapTools.addAll(map, strings, REVERSE_STRING_TRANSFORMER, SORT_STRING_TRANSFORMER);
		assertEquals("foo", map.get("oof"));
		assertEquals("abr", map.get("rab"));
		assertEquals("abz", map.get("zab"));
	}

	public void testAddAllMapQueueTransformerTransformer() {
		ArrayList<String> strings = new ArrayList<String>();
		strings.add("foo");
		strings.add("bar");
		strings.add("baz");
		Queue<String> queue = QueueTools.arrayQueue(strings);
		Map<String, String> map = new HashMap<String, String>();
		QueueTools.drainTo(queue, map, REVERSE_STRING_TRANSFORMER, SORT_STRING_TRANSFORMER);
		assertEquals("foo", map.get("oof"));
		assertEquals("abr", map.get("rab"));
		assertEquals("abz", map.get("zab"));
	}

	public void testAddAllMapStackTransformerTransformer() {
		ArrayList<String> strings = new ArrayList<String>();
		strings.add("foo");
		strings.add("bar");
		strings.add("baz");
		Stack<String> stack = StackTools.arrayStack(strings);
		Map<String, String> map = new HashMap<String, String>();
		StackTools.popAllTo(stack, map, REVERSE_STRING_TRANSFORMER, SORT_STRING_TRANSFORMER);
		assertEquals("foo", map.get("oof"));
		assertEquals("abr", map.get("rab"));
		assertEquals("abz", map.get("zab"));
	}

	public void testAddAllMapTransformerArrayTransformer() {
		Map<String, String> map = new HashMap<String, String>();
		MapTools.addAll(map, REVERSE_STRING_TRANSFORMER, SORT_STRING_TRANSFORMER, "foo", "bar", "baz");
		assertEquals("foo", map.get("oof"));
		assertEquals("abr", map.get("rab"));
		assertEquals("abz", map.get("zab"));
	}

	public void testPutAllMapListList() {
		Map<String, String> map = new HashMap<String, String>();
		MapTools.putAll(map, this.buildKeys(), this.buildValues());
		assertEquals(this.buildMap(), map);
	}

	public void testPutAllMapListList_IAE() {
		Map<String, String> map = new HashMap<String, String>();
		boolean exCaught = false;
		try {
			List<String> values = this.buildValues();
			values.remove(0);
			MapTools.putAll(map, this.buildKeys(), values);
			fail("bogus: " + map);
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testPutAllMapArrayArray() {
		Map<String, String> map = new HashMap<String, String>();
		MapTools.putAll(map, this.buildKeys().toArray(StringTools.EMPTY_STRING_ARRAY), this.buildValues().toArray(StringTools.EMPTY_STRING_ARRAY));
		assertEquals(this.buildMap(), map);
	}

	public void testPutAllMapArrayArray_IAE() {
		Map<String, String> map = new HashMap<String, String>();
		boolean exCaught = false;
		try {
			String[] values = this.buildValues().toArray(StringTools.EMPTY_STRING_ARRAY);
			values = ArrayTools.removeElementAtIndex(values, 0);
			MapTools.putAll(map, this.buildKeys().toArray(StringTools.EMPTY_STRING_ARRAY), values);
			fail("bogus: " + map);
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testContainsAllKeysMapIterable() {
		Map<String, String> map = this.buildMap();
		Iterable<String> keys = this.buildKeys();
		assertTrue(MapTools.containsAllKeys(map, keys));
		map.remove("1");
		assertFalse(MapTools.containsAllKeys(map, keys));
	}

	public void testContainsAllKeysMapArray() {
		Map<String, String> map = this.buildMap();
		String[] keys = this.buildKeys().toArray(StringTools.EMPTY_STRING_ARRAY);
		assertTrue(MapTools.containsAllKeys(map, (Object[]) keys));
		map.remove("1");
		assertFalse(MapTools.containsAllKeys(map, (Object[]) keys));
	}

	public void testContainsAllValuesMapIterable() {
		Map<String, String> map = this.buildMap();
		Iterable<String> values = this.buildValues();
		assertTrue(MapTools.containsAllValues(map, values));
		map.remove("1");
		assertFalse(MapTools.containsAllValues(map, values));
	}

	public void testContainsAllValuesMapArray() {
		Map<String, String> map = this.buildMap();
		String[] values = this.buildValues().toArray(StringTools.EMPTY_STRING_ARRAY);
		assertTrue(MapTools.containsAllValues(map, (Object[]) values));
		map.remove("1");
		assertFalse(MapTools.containsAllValues(map, (Object[]) values));
	}

	public void testRemoveAllMapIterable() {
		Map<String, String> map = this.buildMap();
		Iterable<String> keys = this.buildKeys();
		assertFalse(map.isEmpty());
		MapTools.removeAll(map, keys);
		assertTrue(map.isEmpty());
	}

	public void testRemoveAllMapArray() {
		Map<String, String> map = this.buildMap();
		String[] keys = this.buildKeys().toArray(StringTools.EMPTY_STRING_ARRAY);
		assertFalse(map.isEmpty());
		MapTools.removeAll(map, (Object[]) keys);
		assertTrue(map.isEmpty());
	}

	public void testRetainAllMapCollection() {
		Map<String, String> map = this.buildMap();
		Collection<String> keys = this.buildKeys();
		assertFalse(map.isEmpty());
		MapTools.retainAll(map, keys);
		assertEquals(this.buildMap(), map);
		keys.remove("3");
		MapTools.retainAll(map, keys);
		assertFalse(map.containsKey("3"));
	}

	public void testRetainAllMapCollection_empty() {
		Map<String, String> map = this.buildMap();
		Collection<String> keys = new ArrayList<String>();
		assertFalse(map.isEmpty());
		MapTools.retainAll(map, keys);
		assertTrue(map.isEmpty());
	}

	public void testRetainAllMapIterable() {
		Map<String, String> map = this.buildMap();
		Iterable<String> keys = this.buildKeys();
		assertFalse(map.isEmpty());
		MapTools.retainAll(map, keys);
		assertEquals(this.buildMap(), map);
		Collection<String> temp = this.buildKeys();
		temp.remove("3");
		keys = temp;
		MapTools.retainAll(map, keys);
		assertFalse(map.containsKey("3"));
	}

	public void testRetainAllMapIterable_empty() {
		Map<String, String> map = this.buildMap();
		Iterable<String> keys = new ArrayList<String>();
		assertFalse(map.isEmpty());
		MapTools.retainAll(map, keys);
		assertTrue(map.isEmpty());
	}

	public void testRetainAllMapIterableInt() {
		Map<String, String> map = this.buildMap();
		Iterable<String> keys = this.buildKeys();
		assertFalse(map.isEmpty());
		MapTools.retainAll(map, keys, 77);
		assertEquals(this.buildMap(), map);
		Collection<String> temp = this.buildKeys();
		temp.remove("3");
		keys = temp;
		MapTools.retainAll(map, keys);
		assertFalse(map.containsKey("3"));
	}

	public void testRetainAllMapIterableInt_empty() {
		Map<String, String> map = this.buildMap();
		Iterable<String> keys = new ArrayList<String>();
		assertFalse(map.isEmpty());
		MapTools.retainAll(map, keys, 77);
		assertTrue(map.isEmpty());
	}

	public void testRetainAllMapArray() {
		Map<String, String> map = this.buildMap();
		String[] keys = this.buildKeys().toArray(StringTools.EMPTY_STRING_ARRAY);
		assertFalse(map.isEmpty());
		MapTools.retainAll(map, (Object[]) keys);
		assertEquals(this.buildMap(), map);
		keys = ArrayTools.remove(keys, "3");
		MapTools.retainAll(map, (Object[]) keys);
		assertFalse(map.containsKey("3"));
	}

	public void testRetainAllMapArray_empty() {
		Map<String, String> map = this.buildMap();
		String[] keys = StringTools.EMPTY_STRING_ARRAY;
		assertFalse(map.isEmpty());
		MapTools.retainAll(map, (Object[]) keys);
		assertTrue(map.isEmpty());
	}

	public void testInvertMap() {
		Map<String, String> map = this.buildMap();
		map = MapTools.invert(map);
		List<String> keys = this.buildValues();
		List<String> values = this.buildKeys();
		for (int i = 0; i < keys.size(); i++) {
			assertEquals(values.get(i), map.get(keys.get(i)));
		}
	}

	public void testFilterMapPredicate() {
		Map<String, String> map = this.buildMap();
		map = MapTools.filter(map, new StringLengthPredicate(3));
		assertEquals(null, map.get("0"));
		assertFalse(map.containsKey("0"));
		assertEquals("one", map.get("1"));
		assertEquals("two", map.get("2"));
		assertEquals(null, map.get("3"));
		assertFalse(map.containsKey("3"));
		assertEquals(null, map.get("4"));
		assertFalse(map.containsKey("4"));
	}

	public void testTransformMapTransformer() {
		Map<String, String> map = this.buildMap();
		map = MapTools.transform(map, REVERSE_STRING_TRANSFORMER);
		assertEquals("orez", map.get("0"));
		assertEquals("eno", map.get("1"));
		assertEquals("owt", map.get("2"));
		assertEquals("eerht", map.get("3"));
		assertEquals("ruof", map.get("4"));
	}

	public void testConstructor() {
		boolean exCaught = false;
		try {
			Object at = ClassTools.newInstance(MapTools.class);
			fail("bogus: " + at); //$NON-NLS-1$
		} catch (RuntimeException ex) {
			if (ex.getCause() instanceof InvocationTargetException) {
				if (ex.getCause().getCause() instanceof UnsupportedOperationException) {
					exCaught = true;
				}
			}
		}
		assertTrue(exCaught);
	}

	private List<String> buildKeys() {
		ArrayList<String> keys = new ArrayList<String>();
		keys.add("0");
		keys.add("1");
		keys.add("2");
		keys.add("3");
		keys.add("4");
		return keys;
	}

	private List<String> buildValues() {
		ArrayList<String> values = new ArrayList<String>();
		values.add("zero");
		values.add("one");
		values.add("two");
		values.add("three");
		values.add("four");
		return values;
	}

	private Map<String, String> buildMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("0", "zero");
		map.put("1", "one");
		map.put("2", "two");
		map.put("3", "three");
		map.put("4", "four");
		return map;
	}

	private Map<String, ArrayList<String>> buildListMap() {
		Map<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();
		map.put("0", new ArrayList<String>());
		map.put("1", new ArrayList<String>());
		map.put("2", new ArrayList<String>());
		map.put("3", new ArrayList<String>());
		map.put("4", new ArrayList<String>());
		return map;
	}

	public static final Transformer<String, String> REVERSE_STRING_TRANSFORMER = new ReverseStringTransformer();
	public static class ReverseStringTransformer
		extends TransformerAdapter<String, String>
	{
		@Override
		public String transform(String input) {
			return StringTools.reverse(input);
		}
	}

	public static final Transformer<String, String> SORT_STRING_TRANSFORMER = new SortStringTransformer();
	public static class SortStringTransformer
		extends TransformerAdapter<String, String>
	{
		@Override
		public String transform(String input) {
			return new String(ArrayTools.sort(input.toCharArray()));
		}
	}

	public static class StringLengthPredicate
		extends PredicateAdapter<String>
	{
		private final int length;
		public StringLengthPredicate(int length) {
			super();
			this.length = length;
		}
		@Override
		public boolean evaluate(String s) {
			return s.length() == this.length;
		}
	}
}

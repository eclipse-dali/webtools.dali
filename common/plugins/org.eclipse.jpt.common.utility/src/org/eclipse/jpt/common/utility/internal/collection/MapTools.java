/*******************************************************************************
 * Copyright (c) 2013, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.collection;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.eclipse.jpt.common.utility.factory.Factory;
import org.eclipse.jpt.common.utility.internal.ClassTools;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.predicate.Predicate;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * {@link Map} utility methods.
 */
public final class MapTools {

	// ********** get (with default value) **********

	/**
	 * Return the value mapped by the specified map to the specified key.
	 * If the specified key is not mapped, map the key to the specified default
	 * value and return it.
	 * @see Map#get(Object)
	 * @see Map#containsKey(Object)
	 */
	public static <K, V> V get(Map<K, V> map, K key, V defaultValue) {
		V value = map.get(key);
		if ((value != null) || map.containsKey(key)) {
			return value;
		}
		map.put(key, defaultValue);
		return defaultValue;
	}

	/**
	 * <em>Assume</em> the map does not contain any <code>null</code> values.
	 * @see #get(Map, Object, Object)
	 */
	public static <K, V> V get_(Map<K, V> map, K key, V defaultValue) {
		V value = map.get(key);
		if (value != null) {
			return value;
		}
		map.put(key, defaultValue);
		return defaultValue;
	}

	/**
	 * Return the value mapped by the specified map to the specified key.
	 * If the specified key is not mapped, map the key to the value returned by
	 * the specified factory and return it.
	 * @see Map#get(Object)
	 * @see Map#containsKey(Object)
	 */
	public static <K, V> V get(Map<K, V> map, K key, Factory<? extends V> factory) {
		V value = map.get(key);
		if ((value != null) || map.containsKey(key)) {
			return value;
		}
		V defaultValue = factory.create();
		map.put(key, defaultValue);
		return defaultValue;
	}

	/**
	 * <em>Assume</em> the map does not contain any <code>null</code> values.
	 * @see #get(Map, Object, Factory)
	 */
	public static <K, V> V get_(Map<K, V> map, K key, Factory<? extends V> factory) {
		V value = map.get(key);
		if (value != null) {
			return value;
		}
		V defaultValue = factory.create();
		map.put(key, defaultValue);
		return defaultValue;
	}

	/**
	 * Return the value mapped by the specified map to the specified key.
	 * If the specified key is not mapped, map the key to a new instance of the
	 * specified class, using the class's zero-argument constructor, and return
	 * it.
	 * @see Map#get(Object)
	 * @see Map#containsKey(Object)
	 */
	public static <K, V, C extends V> V get(Map<K, V> map, K key, Class<C> clazz) {
		return get(map, key, clazz, ClassTools.EMPTY_ARRAY, ObjectTools.EMPTY_OBJECT_ARRAY);
	}

	/**
	 * <em>Assume</em> the map does not contain any <code>null</code> values.
	 * @see #get(Map, Object, Class)
	 */
	public static <K, V, C extends V> V get_(Map<K, V> map, K key, Class<C> clazz) {
		return get_(map, key, clazz, ClassTools.EMPTY_ARRAY, ObjectTools.EMPTY_OBJECT_ARRAY);
	}

	/**
	 * Return the value mapped by the specified map to the specified key.
	 * If the specified key is not mapped, map the key to a new instance of the
	 * specified class, using the class's specified single-argument constructor,
	 * and return it.
	 * @see Map#get(Object)
	 * @see Map#containsKey(Object)
	 */
	public static <K, V, C extends V> V get(Map<K, V> map, K key, Class<C> clazz, Class<?> parameterType, Object argument) {
		return get(map, key, clazz, new Class[] {parameterType}, new Object[] {argument});
	}

	/**
	 * <em>Assume</em> the map does not contain any <code>null</code> values.
	 * @see #get(Map, Object, Class, Class, Object)
	 */
	public static <K, V, C extends V> V get_(Map<K, V> map, K key, Class<C> clazz, Class<?> parameterType, Object argument) {
		return get_(map, key, clazz, new Class[] {parameterType}, new Object[] {argument});
	}

	/**
	 * Return the value mapped by the specified map to the specified key.
	 * If the specified key is not mapped, map the key to a new instance of the
	 * specified class, using the class's specified constructor, and return it.
	 * @see Map#get(Object)
	 * @see Map#containsKey(Object)
	 */
	public static <K, V> V get(Map<K, V> map, K key, Class<? extends V> clazz, Class<?>[] parameterTypes, Object[] arguments) {
		V value = map.get(key);
		if ((value != null) || map.containsKey(key)) {
			return value;
		}
		V defaultValue = ClassTools.newInstance(clazz, parameterTypes, arguments);
		map.put(key, defaultValue);
		return defaultValue;
	}

	/**
	 * <em>Assume</em> the map does not contain any <code>null</code> values.
	 * @see #get(Map, Object, Class, Class[], Object[])
	 */
	public static <K, V> V get_(Map<K, V> map, K key, Class<? extends V> clazz, Class<?>[] parameterTypes, Object[] arguments) {
		V value = map.get(key);
		if (value != null) {
			return value;
		}
		V defaultValue = ClassTools.newInstance(clazz, parameterTypes, arguments);
		map.put(key, defaultValue);
		return defaultValue;
	}


	// ********** add **********

	/**
	 * With the specified map, map the specified value to the key generated by
	 * passing the value to the specified key transformer.
	 * Return the previous value associated with generated key.
	 * @see Map#put(Object, Object)
	 */
	public static <K, V, E extends V> V add(Map<K, V> map, E value, Transformer<? super E, ? extends K> keyTransformer) {
		return map.put(keyTransformer.transform(value), value);
	}

	/**
	 * With the specified map, map the value generated by passing the specified
	 * element to the specified value transformer to the key generated by
	 * passing the value to the specified key transformer.
	 * Return the previous value associated with generated key.
	 * @see Map#put(Object, Object)
	 */
	public static <K, V, E> V add(Map<K, V> map, E element, Transformer<? super E, ? extends K> keyTransformer, Transformer<? super E, ? extends V> valueTransformer) {
		return map.put(keyTransformer.transform(element), valueTransformer.transform(element));
	}


	// ********** add all **********

	/**
	 * With the specified map, map the specified values to the keys generated by
	 * passing the values to the specified key transformer.
	 * @see Map#putAll(Map)
	 */
	public static <K, V, E extends V> void addAll(Map<K, V> map, Iterable<E> values, Transformer<? super E, ? extends K> keyTransformer) {
		addAll(map, values.iterator(), keyTransformer);
	}

	/**
	 * With the specified map, map the specified values to the keys generated by
	 * passing the values to the specified key transformer.
	 * @see Map#putAll(Map)
	 */
	public static <K, V, E extends V> void addAll(Map<K, V> map, Iterator<E> values, Transformer<? super E, ? extends K> keyTransformer) {
		while (values.hasNext()) {
			add(map, values.next(), keyTransformer);
		}
	}

	/**
	 * With the specified map, map the specified values to the keys generated by
	 * passing the values to the specified key transformer.
	 * @see Map#putAll(Map)
	 */
	@SafeVarargs
	public static <K, V, E extends V> void addAll(Map<K, V> map, Transformer<? super E, ? extends K> keyTransformer, E... values) {
		for (E value : values) {
			add(map, value, keyTransformer);
		}
	}

	/**
	 * With the specified map, map the values generated by passing the specified
	 * elements to the specified value transformer to the key generated by
	 * passing the elements to the specified key transformer.
	 * @see Map#putAll(Map)
	 */
	public static <K, V, E> void addAll(Map<K, V> map, Iterable<E> elements, Transformer<? super E, ? extends K> keyTransformer, Transformer<? super E, ? extends V> valueTransformer) {
		addAll(map, elements.iterator(), keyTransformer, valueTransformer);
	}

	/**
	 * With the specified map, map the values generated by passing the specified
	 * elements to the specified value transformer to the key generated by
	 * passing the elements to the specified key transformer.
	 * @see Map#putAll(Map)
	 */
	public static <K, V, E> void addAll(Map<K, V> map, Iterator<E> elements, Transformer<? super E, ? extends K> keyTransformer, Transformer<? super E, ? extends V> valueTransformer) {
		while (elements.hasNext()) {
			add(map, elements.next(), keyTransformer, valueTransformer);
		}
	}

	/**
	 * With the specified map, map the values generated by passing the specified
	 * elements to the specified value transformer to the key generated by
	 * passing the elements to the specified key transformer.
	 * @see Map#putAll(Map)
	 */
	@SafeVarargs
	public static <K, V, E> void addAll(Map<K, V> map, Transformer<? super E, ? extends K> keyTransformer, Transformer<? super E, ? extends V> valueTransformer, E... elements) {
		for (E element : elements) {
			add(map, element, keyTransformer, valueTransformer);
		}
	}


	// ********** put all **********

	/**
	 * Map the specified keys and values in the specified map.
	 * The two lists must be the same size. The keys are matched up with
	 * the values by index. If there are duplicate keys, the last value in the
	 * list for a particular key will be the resulting value.
	 * @see Map#putAll(Map)
	 */
	public static <K, V> void putAll(Map<K, V> map, List<? extends K> keys, List<? extends V> values) {
		if (keys.size() != values.size()) {
			throw new IllegalArgumentException("unequal sizes - keys: " + keys.size() + " values: " + values.size()); //$NON-NLS-1$ //$NON-NLS-2$
		}
		for (int i = 0; i < keys.size(); i++) {
			map.put(keys.get(i), values.get(i));
		}
	}

	/**
	 * Map the specified keys and values in the specified map.
	 * The two arrays must be the same size. The keys are matched up with
	 * the values by index. If there are duplicate keys, the last value in the
	 * array for a particular key will be the resulting value.
	 * @see Map#putAll(Map)
	 */
	public static <K, V> void putAll(Map<K, V> map, K[] keys, V[] values) {
		if (keys.length != values.length) {
			throw new IllegalArgumentException("unequal lengths - keys: " + keys.length + " values: " + values.length); //$NON-NLS-1$ //$NON-NLS-2$
		}
		for (int i = 0; i < keys.length; i++) {
			map.put(keys[i], values[i]);
		}
	}


	// ********** contains all keys **********

	/**
	 * Return whether the specified map contains all of the
	 * keys in the specified iterable.
	 * @see Map#containsKey(Object)
	 */
	public static boolean containsAllKeys(Map<?, ?> map, Iterable<?> keys) {
		return containsAllKeys(map, keys.iterator());
	}

	/**
	 * Return whether the specified map contains all of the
	 * keys in the specified iterable, retrieving elements from the iterator
	 * until one is not found in the map.
	 * @see Map#containsKey(Object)
	 */
	public static boolean containsAllKeys(Map<?, ?> map, Iterator<?> keys) {
		while (keys.hasNext()) {
			if ( ! map.containsKey(keys.next())) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Return whether the specified map contains all of the
	 * keys in the specified array.
	 * @see Map#containsKey(Object)
	 */
	public static boolean containsAllKeys(Map<?, ?> map, Object... keys) {
		for (int i = keys.length; i-- > 0; ) {
			if ( ! map.containsKey(keys[i])) {
				return false;
			}
		}
		return true;
	}


	// ********** contains all values **********

	/**
	 * Return whether the specified map contains all of the
	 * values in the specified iterable.
	 * @see Map#containsValue(Object)
	 */
	public static boolean containsAllValues(Map<?, ?> map, Iterable<?> values) {
		return containsAllValues(map, values.iterator());
	}

	/**
	 * Return whether the specified map contains all of the
	 * values in the specified iterable, retrieving elements from the iterator
	 * until one is not found in the map.
	 * @see Map#containsValue(Object)
	 */
	public static boolean containsAllValues(Map<?, ?> map, Iterator<?> values) {
		while (values.hasNext()) {
			if ( ! map.containsValue(values.next())) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Return whether the specified map contains all of the
	 * values in the specified array.
	 * @see Map#containsValue(Object)
	 */
	public static boolean containsAllValues(Map<?, ?> map, Object... values) {
		for (int i = values.length; i-- > 0; ) {
			if ( ! map.containsValue(values[i])) {
				return false;
			}
		}
		return true;
	}


	// ********** remove all **********

	/**
	 * Remove from the specified map all of the
	 * keys in the specified iterable.
	 * @see Map#remove(Object)
	 */
	public static void removeAll(Map<?, ?> map, Iterable<?> keys) {
		removeAll(map, keys.iterator());
	}

	/**
	 * Remove from the specified map all of the
	 * keys returned by the specified iterator.
	 * @see Map#remove(Object)
	 */
	public static void removeAll(Map<?, ?> map, Iterator<?> keys) {
		while (keys.hasNext()) {
			map.remove(keys.next());
		}
	}

	/**
	 * Remove from the specified map all of the
	 * keys returned by the specified array.
	 * @see Map#remove(Object)
	 */
	public static void removeAll(Map<?, ?> map, Object... keys) {
		for (int i = keys.length; i-- > 0; ) {
			map.remove(keys[i]);
		}
	}


	// ********** retain all **********

	/**
	 * Retain in the specified map only the
	 * keys in the specified collection.
	 */
	public static void retainAll(Map<?, ?> map, Collection<?> keys) {
		if (keys.isEmpty()) {
			map.clear();
		} else {
			retainAll_(map, keys);
		}
	}

	/**
	 * Retain in the specified map only the
	 * keys in the specified iterable.
	 */
	public static void retainAll(Map<?, ?> map, Iterable<?> keys) {
		retainAll(map, keys.iterator());
	}

	/**
	 * Retain in the specified map only the
	 * keys in the specified iterable.
	 * The specified size is a performance hint.
	 */
	public static void retainAll(Map<?, ?> map, Iterable<?> keys, int keysSize) {
		retainAll(map, keys.iterator(), keysSize);
	}

	/**
	 * Retain in the specified map only the
	 * keys in the specified iterable.
	 */
	public static void retainAll(Map<?, ?> map, Iterator<?> keys) {
		if (keys.hasNext()) {
			retainAll_(map, CollectionTools.hashSet(keys));
		} else {
			map.clear();
		}
	}

	/**
	 * Retain in the specified map only the
	 * keys in the specified iterable.
	 * The specified size is a performance hint.
	 */
	public static void retainAll(Map<?, ?> map, Iterator<?> keys, int keysSize) {
		if (keys.hasNext()) {
			retainAll_(map, CollectionTools.hashSet(keys, keysSize));
		} else {
			map.clear();
		}
	}

	/**
	 * Retain in the specified map only the
	 * keys in the specified array.
	 */
	public static void retainAll(Map<?, ?> map, Object... keys) {
		if (keys.length > 0) {
			retainAll_(map, CollectionTools.hashSet(keys));
		} else {
			map.clear();
		}
	}

	/**
	 * no empty check
	 */
	private static void retainAll_(Map<?, ?> map, Collection<?> keys) {
		for (Iterator<? extends Map.Entry<?, ?>> stream = map.entrySet().iterator(); stream.hasNext(); ) {
			Map.Entry<?, ?> entry = stream.next();
			if ( ! keys.contains(entry.getKey())) {
				stream.remove();
			}
		}
	}


	// ********** invert **********

	/**
	 * Return a new map that is an <em>inversion</em> of the specified map
	 * (i.e. the old map's keys and values are inverted in the new map).
	 * If the old map contains any duplicate values, only a single mapping
	 * will remain in the new map.
	 */
	public static <K, V> HashMap<K, V> invert(Map<? extends V, ? extends K> map) {
		HashMap<K, V> result = new HashMap<>((int) (map.size() / 0.75));
		for (Map.Entry<? extends V, ? extends K> entry : map.entrySet()) {
			result.put(entry.getValue(), entry.getKey());
		}
		return result;
	}


	// ********** filter **********

	/**
	 * Return a new map with the filtered
	 * values of the specified map.
	 */
	public static <K, V> HashMap<K, V> filter(Map<? extends K, ? extends V> map, Predicate<? super V> filter) {
		HashMap<K, V> result = new HashMap<>((int) (map.size() / 0.75));
		for (Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
			V value = entry.getValue();
			if (filter.evaluate(value)) {
				result.put(entry.getKey(), value);
			}
		}
		return result;
	}


	// ********** transform **********

	/**
	 * Return a new map with transformations of the
	 * values of the specified map.
	 */
	public static <K, V1, V2> HashMap<K, V2> transform(Map<? extends K, V1> map, Transformer<? super V1, ? extends V2> transformer) {
		HashMap<K, V2> result = new HashMap<>((int) (map.size() / 0.75));
		for (Map.Entry<? extends K, ? extends V1> entry : map.entrySet()) {
			result.put(entry.getKey(), transformer.transform(entry.getValue()));
		}
		return result;
	}


	// ********** read/write lock wrapper **********

	/**
	 * Return a wrapper of the specified map that uses an nonfair
	 * reentrant read/write lock to control access to the map.
	 * The wrapper provides a simplified
	 * "map" interface (i.e. it does not implement the "view" methods).
	 * The wrapper uses blocking calls when acquiring the appropriate lock
	 * (see {@link Lock#lock()}).
	 * @param <K> the type of keys maintained by the map
	 * @param <V> the type of values maintained by the map
	 */
	public static <K, V> ReadWriteLockMapWrapper<K, V> readWriteLockWrapper(Map<K, V> map) {
		return readWriteLockWrapper(map, false);
	}

	/**
	 * Return a wrapper of the specified map that uses a
	 * reentrant read/write lock with the specified fairness policy
	 * to control access to the map.
	 * The wrapper provides a simplified
	 * "map" interface (i.e. it does not implement the "view" methods).
	 * The wrapper uses blocking calls when acquiring the appropriate lock
	 * (see {@link Lock#lock()}).
	 * @param <K> the type of keys maintained by the map
	 * @param <V> the type of values maintained by the map
	 */
	public static <K, V> ReadWriteLockMapWrapper<K, V> readWriteLockWrapper(Map<K, V> map, boolean fair) {
		return readWriteLockWrapper(map, new ReentrantReadWriteLock(fair));
	}

	/**
	 * Return a wrapper of the specified map that uses the specified
	 * read/write lock to control access to the map.
	 * The wrapper provides a simplified
	 * "map" interface (i.e. it does not implement the "view" methods).
	 * The wrapper uses blocking calls when acquiring the appropriate lock
	 * (see {@link Lock#lock()}).
	 * @param <K> the type of keys maintained by the map
	 * @param <V> the type of values maintained by the map
	 */
	public static <K, V> ReadWriteLockMapWrapper<K, V> readWriteLockWrapper(Map<K, V> map, ReadWriteLock lock) {
		return new ReadWriteLockMapWrapper<>(map, lock);
	}


	// ********** constructor **********

	/**
	 * Suppress default constructor, ensuring non-instantiability.
	 */
	private MapTools() {
		super();
		throw new UnsupportedOperationException();
	}
}

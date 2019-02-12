/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.collection;

import java.util.AbstractCollection;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * A {@link Map} implementation that uses the least amount of space possible,
 * at the expense of slower execution and increased object creation.
 * @param <K> the type of keys maintained by the map
 * @param <V> the type of values maintained by the map
 */
public class TightMap<K,V>
	implements Map<K,V>
{
	@SuppressWarnings("unchecked")
	K[] keys = (K[]) ObjectTools.EMPTY_OBJECT_ARRAY;
	@SuppressWarnings("unchecked")
	V[] values = (V[]) ObjectTools.EMPTY_OBJECT_ARRAY;


	/**
	 * Construct an empty map.
	 */
	public TightMap() {
		super();
	}

	/**
	 * Construct a map initialized with the entries from the specified map.
	 */
	public TightMap(Map<? extends K, ? extends V> map) {
		super();
		this.putAll(map);
	}

	public int size() {
		return this.keys.length;
	}

	public boolean isEmpty() {
		return this.keys.length == 0;
	}

	public boolean containsValue(Object value) {
		return ArrayTools.contains(this.values, value);
	}

	public boolean containsKey(Object key) {
		return ArrayTools.contains(this.keys, key);
	}

	public V get(Object key) {
		int index = this.indexOfKey(key);
		return (index == -1) ? null : this.values[index];
	}

	/* CU private */ int indexOfKey(Object key) {
		return ArrayTools.indexOf(this.keys, key);
	}

	public V put(K key, V value) {
		V prev = null;
		int index = this.indexOfKey(key);
		if (index == -1) {
			this.keys = ArrayTools.add(this.keys, key);
			this.values = ArrayTools.add(this.values, value);
		} else {
			prev = this.values[index];
			this.values[index] = value;
		}
		return prev;
	}

	public V remove(Object key) {
		int index = this.indexOfKey(key);
		return (index == -1) ? null : this.remove(index);
	}

	/**
	 * Pre-condition: specified key is present
	 */
	/* CU private */ V removeKey(Object key) {
		return this.remove(this.indexOfKey(key));
	}

	/**
	 * Pre-condition: specified value is present
	 */
	/* CU private */ void removeValue(Object value) {
		this.remove(this.indexOfValue(value));
	}

	/* CU private */ int indexOfValue(Object value) {
		return ArrayTools.indexOf(this.values, value);
	}

	/**
	 * Pre-condition: specified index is valid
	 */
	V remove(int index) {
		V prev = this.values[index];
		this.keys = ArrayTools.removeElementAtIndex(this.keys, index);
		this.values = ArrayTools.removeElementAtIndex(this.values, index);
		if (this.keys.length == 0) {
			// replace the global empty object array so we can detect a concurrent modification
			this.clear();
		}
		return prev;
	}

	public void putAll(Map<? extends K, ? extends V> map) {
		int mapSize = map.size();
		if (mapSize == 0) {
			return;
		}
		int index = this.keys.length;
		this.keys = ArrayTools.expand(this.keys, mapSize);
		this.values = ArrayTools.expand(this.values, mapSize);
		for (Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
			this.keys[index] = entry.getKey();
			this.values[index] = entry.getValue();
			index++;
		}
	}

	@SuppressWarnings("unchecked")
	public void clear() {
		// build *new* arrays here so we can detect a concurrent modification
		this.keys = (K[]) new Object[0];
		this.values = (V[]) new Object[0];
	}

	public Set<K> keySet() {
		return new KeySet();
	}

	public Collection<V> values() {
		return new ValueCollection();
	}

	public Set<Map.Entry<K, V>> entrySet() {
		return new EntrySet();
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if ( ! (o instanceof Map)) {
			return false;
		}
		@SuppressWarnings("unchecked")
		Map<K,V> other = (Map<K,V>) o;
		int size = this.keys.length;
		if (other.size() != size) {
			return false;
		}
		for (int i = size; i-- > 0; ) {
			K key = this.keys[i];
			V value = this.values[i];
			if (value == null) {
				if ( ! ((other.get(key) == null) && other.containsKey(key))) {
					return false;
				}
			} else {
				if ( ! value.equals(other.get(key))) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		for (int i = this.keys.length; i-- > 0; ) {
			hash += (ObjectTools.hashCode(this.keys[i]) ^ ObjectTools.hashCode(this.values[i]));
		}
		return hash;
	}

	@Override
	public String toString() {
		int len = this.keys.length;
		if (len == 0) {
			return EMPTY_MAP_STRING;
		}
		StringBuilder sb = new StringBuilder();
		sb.append('{');
		for (int i = 0; i < len; i++) {
			K key = this.keys[i];
			V value = this.values[i];
			if (key == this) {
				sb.append(THIS_MAP_STRING);
			} else {
				sb.append(key);
			}
			sb.append('=');
			if (value == this) {
				sb.append(THIS_MAP_STRING);
			} else {
				sb.append(value);
			}
			sb.append(", "); //$NON-NLS-1$
		}
		sb.setLength(sb.length() - 2);  // strip off extra comma
		sb.append('}');
		return sb.toString();
	}

	private static final String EMPTY_MAP_STRING = "{}"; //$NON-NLS-1$
	private static final String THIS_MAP_STRING = "(this Map)"; //$NON-NLS-1$


	// ********** array iterator **********

	private abstract static class ArrayIterator<E>
		implements Iterator<E>
	{
		E[] localArray;
		private E next;
		private boolean done = false;
		private int cursor = 0;
		private E current;
		private boolean currentRemoved = true;

		ArrayIterator() {
			super();
			this.localArray = this.getTightMapArray();
			this.loadNext();
		}
		abstract E[] getTightMapArray();
		public boolean hasNext() {
			return ! this.done;
		}
		public E next() {
			if (this.localArray != this.getTightMapArray()) {
				throw new ConcurrentModificationException();
			}
			if (this.done) {
				throw new NoSuchElementException();
			}
			E element = this.next;
			this.loadNext();
			this.current = element;
			this.currentRemoved = false;
			return element;
		}
		private void loadNext() {
			if (this.cursor < this.localArray.length) {
				this.next = this.localArray[this.cursor];
				this.cursor++;
			} else {
				this.next = null;
				this.done = true;
			}
		}
		public void remove() {
			if (this.currentRemoved) {
				throw new IllegalStateException();
			}
			if (this.localArray != this.getTightMapArray()) {
				throw new ConcurrentModificationException();
			}
			E element = this.current;
			this.current = null;
			this.currentRemoved = true;
			this.removeTightMapElement(element);
			this.localArray = this.getTightMapArray();  // point at new array
			this.cursor--;
		}
		abstract void removeTightMapElement(E e);
	}


	// ********** keys **********

	private class KeySet
		extends AbstractSet<K>
	{
		KeySet() {
			super();
		}
		@Override
		public Iterator<K> iterator() {
			return TightMap.this.buildKeyIterator();
		}
		@Override
		public int size() {
			return TightMap.this.keys.length;
		}
		@Override
		public boolean contains(Object o) {
			return TightMap.this.containsKey(o);
		}
		@Override
		public boolean remove(Object o) {
			int index = TightMap.this.indexOfKey(o);
			if (index == -1) {
				return false;
			}
			TightMap.this.remove(index);
			return true;
		}
		@Override
		public void clear() {
		    TightMap.this.clear();
		}
	}

	/* CU private */ Iterator<K> buildKeyIterator() {
		return new KeyIterator();
	}

	private class KeyIterator
		extends ArrayIterator<K>
	{
		KeyIterator() {
			super();
		}
		@Override
		K[] getTightMapArray() {
			return TightMap.this.keys;
		}
		@Override
		void removeTightMapElement(K key) {
			TightMap.this.removeKey(key);
		}
	}


	// ********** values **********

	private class ValueCollection
		extends AbstractCollection<V>
	{
		ValueCollection() {
			super();
		}
		@Override
		public Iterator<V> iterator() {
			return TightMap.this.buildValueIterator();
		}
		@Override
		public int size() {
			return TightMap.this.keys.length;
		}
		@Override
		public boolean contains(Object o) {
			return TightMap.this.containsValue(o);
		}
		@Override
		public boolean remove(Object o) {
			int index = TightMap.this.indexOfValue(o);
			if (index == -1) {
				return false;
			}
			TightMap.this.remove(index);
			return true;
		}
		@Override
		public void clear() {
		    TightMap.this.clear();
		}
	}

	/* CU private */ Iterator<V> buildValueIterator() {
		return new ValueIterator();
	}

	private class ValueIterator
		extends ArrayIterator<V>
	{
		ValueIterator() {
			super();
		}
		@Override
		V[] getTightMapArray() {
			return TightMap.this.values;
		}
		@Override
		void removeTightMapElement(V value) {
			TightMap.this.removeValue(value);
		}
	}


	// ********** entries **********

	private class EntrySet
		extends AbstractSet<Map.Entry<K, V>>
	{
		EntrySet() {
			super();
		}
		@Override
		public Iterator<Map.Entry<K, V>> iterator() {
			return TightMap.this.buildEntryIterator();
		}
		@Override
		public int size() {
			return TightMap.this.keys.length;
		}
		@Override
		public boolean contains(Object o) {
			return TightMap.this.containsEntry(o);
		}
		@Override
		public boolean remove(Object o) {
			return TightMap.this.removeEntry(o);
		}
		@Override
		public void clear() {
		    TightMap.this.clear();
		}
	}

	/* CU private */ Iterator<Map.Entry<K, V>> buildEntryIterator() {
		return new EntryIterator();
	}

	private class EntryIterator
		implements Iterator<Map.Entry<K, V>>
	{
		K[] localKeys;
		V[] localValues;
		private Entry next;
		private int cursor = 0;
		private Entry current;

		EntryIterator() {
			super();
			this.localKeys = TightMap.this.keys;
			this.localValues = TightMap.this.values;
			this.next = this.buildNext();
		}
		public boolean hasNext() {
			return this.next != null;
		}
		public Map.Entry<K, V> next() {
			if (this.localKeys != TightMap.this.keys) {
				throw new ConcurrentModificationException();
			}
			Entry entry = this.next;
			if (entry == null) {
				throw new NoSuchElementException();
			}
			this.next = this.buildNext();
			this.current = entry;
			return entry;
		}
		private Entry buildNext() {
			return (this.cursor < this.localKeys.length) ? new Entry(this.localKeys[this.cursor++]) : null;
		}
		public void remove() {
			if (this.current == null) {
				throw new IllegalStateException();
			}
			if (this.localKeys != TightMap.this.keys) {
				throw new ConcurrentModificationException();
			}
			Object key = this.current.getKey();
			this.current = null;
			TightMap.this.removeKey(key);
			this.localKeys = TightMap.this.keys;  // point at new arrays
			this.localValues = TightMap.this.values;
			this.cursor--;
		}

		private class Entry
			implements Map.Entry<K,V>
		{
			// we hold the key (instead of the index) because the key/value pair
			// can move when entries are removed via the iterator
			final K key;

			Entry(K key) {
				super();
				this.key = key;
			}
			public K getKey() {
				return this.key;
			}
			private int getIndex() {
				return ArrayTools.indexOf(EntryIterator.this.localKeys, this.key);
			}
			public V getValue() {
				return EntryIterator.this.localValues[this.getIndex()];
			}
			public V setValue(V value) {
				int index = this.getIndex();
				V old = EntryIterator.this.localValues[index];
				EntryIterator.this.localValues[index] = value;
				return old;
			}
			@Override
			public boolean equals(Object o) {
				if ( ! (o instanceof Map.Entry)) {
					return false;
				}
				@SuppressWarnings("unchecked")
				Map.Entry<K,V> other = (Map.Entry<K,V>) o;
				return ObjectTools.equals(this.getKey(), other.getKey()) &&
						ObjectTools.equals(this.getValue(), other.getValue());
			}
			@Override
			public int hashCode() {
				return ObjectTools.hashCode(this.getKey()) ^ ObjectTools.hashCode(this.getValue());
			}
			@Override
			public String toString() {
				return this.getKey() + "=" + this.getValue(); //$NON-NLS-1$
			}
		}
	}

	@SuppressWarnings("unchecked")
	/* CU private */ boolean containsEntry(Object o) {
		return (o instanceof Map.Entry) && this.containsEntry((Map.Entry<K,V>) o);
    }

	private boolean containsEntry(Map.Entry<K,V> entry) {
        int index = TightMap.this.indexOfKey(entry.getKey());
        return (index != -1) && ObjectTools.equals(this.values[index], entry.getValue());
	}

	@SuppressWarnings("unchecked")
	/* CU private */ boolean removeEntry(Object o) {
		return (o instanceof Map.Entry) && this.removeEntry((Map.Entry<K,V>) o);
    }

	private boolean removeEntry(Map.Entry<K,V> entry) {
        int index = TightMap.this.indexOfKey(entry.getKey());
        if (index == -1) {
        	return false;
        }
        if (ObjectTools.notEquals(this.values[index], entry.getValue())) {
        	return false;
        }
       	this.keys = ArrayTools.removeElementAtIndex(this.keys, index);
       	this.values = ArrayTools.removeElementAtIndex(this.values, index);
       	return true;
    }
}

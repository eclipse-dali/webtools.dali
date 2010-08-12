/*******************************************************************************
 *  Copyright (c) 2010  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.utility.internal;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * This class maintains a {@link Set} of items, and a {@link Map} of keys to those items.
 * An item may have multiple keys, but an item may have no keys and remain in the set.  Once an 
 * item's last key is removed, the item is also removed.
 */
public class KeyedSet<K, V> {
	
	private final Set<V> itemSet;
	private final Set<V> unmodifiableItemSet;
	private final Map<K,V> map;
	
	
	public KeyedSet() {
		this.itemSet = new HashSet<V>();
		this.unmodifiableItemSet = Collections.unmodifiableSet(this.itemSet);
		this.map = new HashMap<K,V>();
	}
	
	/**
	 * Return an unmodifiable representation of the set of items.
	 */
	public Set<V> getItemSet() {
		return this.unmodifiableItemSet;
	}
	
	/**
	 * Return the item stored under the given key.
	 */
	public V getItem(K key) {
		return this.map.get(key);
	}
	
	/**
	 * Return whether an item is stored under the given key.
	 */
	public boolean containsKey(K key) {
		return this.map.containsKey(key);
	}
	
	/**
	 * Return whether the item is stored under *any* key.
	 */
	public boolean containsItem(V item) {
		return this.itemSet.contains(item);
	}
	
	/**
	 * Add an item to be stored under the given key.  
	 * The item must not already be stored.
	 */
	public void addItem(K key, V item) {
		addItem(item);
		addKey(key, item);
	}
	
	private void addItem(V item) {
		if (item == null) {
			throw new IllegalArgumentException();
		}
		this.itemSet.add(item);
	}
	
	/** 
	 * Add an additional key to an item already stored under an alternate key.
	 */
	public void addKey(K key, V item) {
		if (key == null || item == null) {
			throw new IllegalArgumentException();
		}
		if (! this.itemSet.contains(item)) {
			throw new IllegalArgumentException();
		}
		this.map.put(key, item);
	}
	
	/**
	 * Remove the given item and remove any key-to-item mapping it may have.
	 */
	public boolean removeItem(V item) {
		if (this.itemSet.remove(item)) {
			for (Map.Entry<K,V> entry : CollectionTools.collection(this.map.entrySet())) {
				if (entry.getValue() == item) {
					map.remove(entry.getKey());
				}
			}
			return true;
		}
		return false;
	}
	
	/**
	 * Remove the key-to-item mapping for the given key.
	 * If it is the last key to the item, also remove the item.
	 */
	public boolean removeKey(K key) {
		final V item = this.map.get(key);
		if (item != null) {
			this.map.remove(key);
			boolean otherKey = false;
			for (Map.Entry<K,V> entry : CollectionTools.collection(this.map.entrySet())) {
				if (otherKey | entry.getValue() == item) {
					otherKey = true;
				}
			}
			if (! otherKey) {
				removeItem(item);
			}
			return true;
		}
		return false;
	}
}

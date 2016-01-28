/*******************************************************************************
 * Copyright (c) 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.collection;

import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;

/**
 * A {@link Map} wrapper that uses a read/write lock
 * to control concurrent access to the map. The wrapper provides a simplified
 * "map" interface (i.e. it does not implement the "view" methods).
 * The wrapper uses blocking calls when acquiring the appropriate lock
 * (see {@link Lock#lock()}).
 * 
 * @param <K> the type of keys maintained by the map
 * @param <V> the type of values maintained by the map
 */
public class ReadWriteLockMapWrapper<K, V> {
	private final Map<K, V> map;
	@SuppressWarnings("unused")
	private final ReadWriteLock lock;
	private final Lock readLock;
	private final Lock writeLock;


	/**
	 * Construct a wrapper for the specified map
	 * that uses the specified read/write lock.
	 */
	public ReadWriteLockMapWrapper(Map<K, V> map, ReadWriteLock lock) {
		super();
		if (map == null) {
			throw new NullPointerException();
		}
		this.map = map;
		if (lock == null) {
			throw new NullPointerException();
		}
		this.lock = lock;
		this.readLock = lock.readLock();
		this.writeLock = lock.writeLock();
	}


	// ********** read-only methods **********

	/**
	 * @see Map#size()
	 */
	public int size() {
		this.readLock.lock();
		try {
			return this.map.size();
		} finally {
			this.readLock.unlock();
		}
	}

	/**
	 * @see Map#isEmpty()
	 */
	public boolean isEmpty() {
		this.readLock.lock();
		try {
			return this.map.isEmpty();
		} finally {
			this.readLock.unlock();
		}
	}

	/**
	 * @see Map#containsValue(Object)
	 */
	public boolean containsValue(Object value) {
		this.readLock.lock();
		try {
			return this.map.containsValue(value);
		} finally {
			this.readLock.unlock();
		}
	}

	/**
	 * @see Map#containsKey(Object)
	 */
	public boolean containsKey(Object key) {
		this.readLock.lock();
		try {
			return this.map.containsKey(key);
		} finally {
			this.readLock.unlock();
		}
	}

	/**
	 * @see Map#get(Object)
	 */
	public V get(Object key) {
		this.readLock.lock();
		try {
			return this.map.get(key);
		} finally {
			this.readLock.unlock();
		}
	}

	@Override
	public String toString() {
		this.readLock.lock();
		try {
			return this.map.toString();
		} finally {
			this.readLock.unlock();
		}
	}


	// ********** write methods **********

	/**
	 * @see Map#put(Object, Object)
	 */
	public V put(K key, V value) {
		this.writeLock.lock();
		try {
			return this.map.put(key, value);
		} finally {
			this.writeLock.unlock();
		}
	}

	/**
	 * @see Map#remove(Object)
	 */
	public V remove(Object key) {
		this.writeLock.lock();
		try {
			return this.map.remove(key);
		} finally {
			this.writeLock.unlock();
		}
	}

	/**
	 * @see Map#putAll(Map)
	 */
	public void putAll(Map<? extends K, ? extends V> m) {
		this.writeLock.lock();
		try {
			this.map.putAll(m);
		} finally {
			this.writeLock.unlock();
		}
	}

	/**
	 * @see Map#clear()
	 */
	public void clear() {
		this.writeLock.lock();
		try {
			this.map.clear();
		} finally {
			this.writeLock.unlock();
		}
	}
}

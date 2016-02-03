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

import java.util.Collection;
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
 * @param <E> the type of elements maintained by the collection
 */
public class ReadWriteLockCollectionWrapper<E> {
	private final Collection<E> collection;
	@SuppressWarnings("unused")
	private final ReadWriteLock lock;
	private final Lock readLock;
	private final Lock writeLock;


	/**
	 * Construct a wrapper for the specified collection
	 * that uses the specified read/write lock.
	 */
	public ReadWriteLockCollectionWrapper(Collection<E> collection, ReadWriteLock lock) {
		super();
		if (collection == null) {
			throw new NullPointerException();
		}
		this.collection = collection;
		if (lock == null) {
			throw new NullPointerException();
		}
		this.lock = lock;
		this.readLock = lock.readLock();
		this.writeLock = lock.writeLock();
	}


	// ********** read-only methods **********


	/**
	 * @see Collection#size()
	 */
	public int size() {
		this.readLock.lock();
		try {
			return this.collection.size();
		} finally {
			this.readLock.unlock();
		}
	}

	/**
	 * @see Collection#isEmpty()
	 */
	public boolean isEmpty() {
		this.readLock.lock();
		try {
			return this.collection.isEmpty();
		} finally {
			this.readLock.unlock();
		}
	}

	/**
	 * @see Collection#contains(Object)
	 */
	public boolean contains(Object o) {
		this.readLock.lock();
		try {
			return this.collection.contains(o);
		} finally {
			this.readLock.unlock();
		}
	}

	/**
	 * @see Collection#containsAll(Collection)
	 */
	public boolean containsAll(Collection<?> c) {
		this.readLock.lock();
		try {
			return this.collection.containsAll(c);
		} finally {
			this.readLock.unlock();
		}
	}

	/**
	 * @see Collection#toArray()
	 */
	public Object[] toArray() {
		this.readLock.lock();
		try {
			return this.collection.toArray();
		} finally {
			this.readLock.unlock();
		}
	}

	/**
	 * @see Collection#toArray(Object[])
	 */
	public <T> T[] toArray(T[] a) {
		this.readLock.lock();
		try {
			return this.collection.toArray(a);
		} finally {
			this.readLock.unlock();
		}
	}

	@Override
	public String toString() {
		this.readLock.lock();
		try {
			return this.collection.toString();
		} finally {
			this.readLock.unlock();
		}
	}


	// ********** write methods **********

	/**
	 * @see Collection#add(Object)
	 */
	public boolean add(E e) {
		this.writeLock.lock();
		try {
			return this.collection.add(e);
		} finally {
			this.writeLock.unlock();
		}
	}

	/**
	 * @see Collection#remove(Object)
	 */
	public boolean remove(Object o) {
		this.writeLock.lock();
		try {
			return this.collection.remove(o);
		} finally {
			this.writeLock.unlock();
		}
	}

	/**
	 * @see Collection#addAll(Collection)
	 */
	public boolean addAll(Collection<? extends E> c) {
		this.writeLock.lock();
		try {
			return this.collection.addAll(c);
		} finally {
			this.writeLock.unlock();
		}
	}

	/**
	 * @see Collection#removeAll(Collection)
	 */
	public boolean removeAll(Collection<?> c) {
		this.writeLock.lock();
		try {
			return this.collection.removeAll(c);
		} finally {
			this.writeLock.unlock();
		}
	}

	/**
	 * @see Collection#retainAll(Collection)
	 */
	public boolean retainAll(Collection<?> c) {
		this.writeLock.lock();
		try {
			return this.collection.retainAll(c);
		} finally {
			this.writeLock.unlock();
		}
	}

	/**
	 * @see Collection#clear()
	 */
	public void clear() {
		this.writeLock.lock();
		try {
			this.collection.clear();
		} finally {
			this.writeLock.unlock();
		}
	}
}

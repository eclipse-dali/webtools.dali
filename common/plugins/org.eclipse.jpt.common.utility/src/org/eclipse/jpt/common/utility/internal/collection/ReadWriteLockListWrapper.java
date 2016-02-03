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
import java.util.List;
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
 * @param <E> the type of elements maintained by the list
 */
public class ReadWriteLockListWrapper<E> {
	private final List<E> list;
	@SuppressWarnings("unused")
	private final ReadWriteLock lock;
	private final Lock readLock;
	private final Lock writeLock;


	/**
	 * Construct a wrapper for the specified collection
	 * that uses the specified read/write lock.
	 */
	public ReadWriteLockListWrapper(List<E> list, ReadWriteLock lock) {
		super();
		if (list == null) {
			throw new NullPointerException();
		}
		this.list = list;
		if (lock == null) {
			throw new NullPointerException();
		}
		this.lock = lock;
		this.readLock = lock.readLock();
		this.writeLock = lock.writeLock();
	}


	// ********** read-only methods **********


	/**
	 * @see List#size()
	 */
	public int size() {
		this.readLock.lock();
		try {
			return this.list.size();
		} finally {
			this.readLock.unlock();
		}
	}

	/**
	 * @see List#isEmpty()
	 */
	public boolean isEmpty() {
		this.readLock.lock();
		try {
			return this.list.isEmpty();
		} finally {
			this.readLock.unlock();
		}
	}

	/**
	 * @see List#contains(Object)
	 */
	public boolean contains(Object o) {
		this.readLock.lock();
		try {
			return this.list.contains(o);
		} finally {
			this.readLock.unlock();
		}
	}

	/**
	 * @see List#containsAll(Collection)
	 */
	public boolean containsAll(Collection<?> c) {
		this.readLock.lock();
		try {
			return this.list.containsAll(c);
		} finally {
			this.readLock.unlock();
		}
	}

	/**
	 * @see List#get(int)
	 */
	public E get(int index) {
		this.readLock.lock();
		try {
			return this.list.get(index);
		} finally {
			this.readLock.unlock();
		}
	}

	/**
	 * @see List#indexOf(Object)
	 */
	public int indexOf(Object o) {
		this.readLock.lock();
		try {
			return this.list.indexOf(o);
		} finally {
			this.readLock.unlock();
		}
	}

	/**
	 * @see List#lastIndexOf(Object)
	 */
	public int lastIndexOf(Object o) {
		this.readLock.lock();
		try {
			return this.list.lastIndexOf(o);
		} finally {
			this.readLock.unlock();
		}
	}

	/**
	 * @see List#toArray()
	 */
	public Object[] toArray() {
		this.readLock.lock();
		try {
			return this.list.toArray();
		} finally {
			this.readLock.unlock();
		}
	}

	/**
	 * @see List#toArray(Object[])
	 */
	public <T> T[] toArray(T[] a) {
		this.readLock.lock();
		try {
			return this.list.toArray(a);
		} finally {
			this.readLock.unlock();
		}
	}

	@Override
	public String toString() {
		this.readLock.lock();
		try {
			return this.list.toString();
		} finally {
			this.readLock.unlock();
		}
	}


	// ********** write methods **********

	/**
	 * @see List#add(Object)
	 */
	public boolean add(E e) {
		this.writeLock.lock();
		try {
			return this.list.add(e);
		} finally {
			this.writeLock.unlock();
		}
	}

	/**
	 * @see List#remove(Object)
	 */
	public boolean remove(Object o) {
		this.writeLock.lock();
		try {
			return this.list.remove(o);
		} finally {
			this.writeLock.unlock();
		}
	}

	/**
	 * @see List#addAll(Collection)
	 */
	public boolean addAll(Collection<? extends E> c) {
		this.writeLock.lock();
		try {
			return this.list.addAll(c);
		} finally {
			this.writeLock.unlock();
		}
	}

	/**
	 * @see List#removeAll(Collection)
	 */
	public boolean removeAll(Collection<?> c) {
		this.writeLock.lock();
		try {
			return this.list.removeAll(c);
		} finally {
			this.writeLock.unlock();
		}
	}

	/**
	 * @see List#retainAll(Collection)
	 */
	public boolean retainAll(Collection<?> c) {
		this.writeLock.lock();
		try {
			return this.list.retainAll(c);
		} finally {
			this.writeLock.unlock();
		}
	}

	/**
	 * @see List#clear()
	 */
	public void clear() {
		this.writeLock.lock();
		try {
			this.list.clear();
		} finally {
			this.writeLock.unlock();
		}
	}

	/**
	 * @see List#addAll(int, Collection)
	 */
	public boolean addAll(int index, Collection<? extends E> c) {
		this.writeLock.lock();
		try {
			return this.list.addAll(index, c);
		} finally {
			this.writeLock.unlock();
		}
	}

	/**
	 * @see List#set(int, Object)
	 */
	public E set(int index, E element) {
		this.writeLock.lock();
		try {
			return this.list.set(index, element);
		} finally {
			this.writeLock.unlock();
		}
	}

	/**
	 * @see List#add(int, Object)
	 */
	public void add(int index, E element) {
		this.writeLock.lock();
		try {
			this.list.add(index, element);
		} finally {
			this.writeLock.unlock();
		}
	}

	/**
	 * @see List#remove(int)
	 */
	public E remove(int index) {
		this.writeLock.lock();
		try {
			return this.list.remove(index);
		} finally {
			this.writeLock.unlock();
		}
	}
}

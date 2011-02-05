/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

/**
 * Thread-safe implementation of the {@link Bag} interface.
 */
public class SynchronizedBag<E>
	implements Bag<E>, Serializable
{
	/** Backing bag. */
	private final Bag<E> bag;

	/** Object to synchronize on. */
	private final Object mutex;

	private static final long serialVersionUID = 1L;


	// ********** constructors **********

	/**
	 * Construct a synchronized bag that wraps the
	 * specified bag and locks on the specified mutex.
	 */
	public SynchronizedBag(Bag<E> bag, Object mutex) {
		super();
		if (bag == null) {
			throw new NullPointerException();
		}
		this.bag = bag;
		this.mutex = mutex;
	}

	/**
	 * Construct a synchronized bag that wraps the
	 * specified bag and locks on itself.
	 */
	public SynchronizedBag(Bag<E> bag) {
		super();
		if (bag == null) {
			throw new NullPointerException();
		}
		this.bag = bag;
		this.mutex = this;
	}

	/**
	 * Construct a synchronized bag that locks on the specified mutex.
	 */
	public SynchronizedBag(Object mutex) {
		this(new HashBag<E>(), mutex);
	}

	/**
	 * Construct a synchronized bag that locks on itself.
	 */
	public SynchronizedBag() {
		this(new HashBag<E>());
	}


	// ********** Bag implementation **********

	public boolean add(E o, int count) {
		synchronized (this.mutex) {
			return this.bag.add(o, count);
		}
	}

	public int count(Object o) {
		synchronized (this.mutex) {
			return this.bag.count(o);
		}
	}

	public Iterator<Bag.Entry<E>> entries() {
		synchronized (this.mutex) {
			return this.bag.entries();
		}
	}

	public boolean remove(Object o, int count) {
		synchronized (this.mutex) {
			return this.bag.remove(o, count);
		}
	}

	public int uniqueCount() {
		synchronized (this.mutex) {
			return this.bag.uniqueCount();
		}
	}

	public Iterator<E> uniqueIterator() {
		synchronized (this.mutex) {
			return this.bag.uniqueIterator();
		}
	}


	// ********** Collection implementation **********

	public boolean add(E e) {
		synchronized (this.mutex) {
			return this.bag.add(e);
		}
	}

	public boolean addAll(Collection<? extends E> c) {
		synchronized (this.mutex) {
			return this.bag.addAll(c);
		}
	}

	public void clear() {
		synchronized (this.mutex) {
			this.bag.clear();
		}
	}

	public boolean contains(Object o) {
		synchronized (this.mutex) {
			return this.bag.contains(o);
		}
	}

	public boolean containsAll(Collection<?> c) {
		synchronized (this.mutex) {
			return this.bag.containsAll(c);
		}
	}

	public boolean isEmpty() {
		synchronized (this.mutex) {
			return this.bag.isEmpty();
		}
	}

	public Iterator<E> iterator() {
		synchronized (this.mutex) {
			return this.bag.iterator();
		}
	}

	public boolean remove(Object o) {
		synchronized (this.mutex) {
			return this.bag.remove(o);
		}
	}

	public boolean removeAll(Collection<?> c) {
		synchronized (this.mutex) {
			return this.bag.removeAll(c);
		}
	}

	public boolean retainAll(Collection<?> c) {
		synchronized (this.mutex) {
			return this.bag.retainAll(c);
		}
	}

	public int size() {
		synchronized (this.mutex) {
			return this.bag.size();
		}
	}

	public Object[] toArray() {
		synchronized (this.mutex) {
			return this.bag.toArray();
		}
	}

	public <T> T[] toArray(T[] a) {
		synchronized (this.mutex) {
			return this.bag.toArray(a);
		}
	}


	// ********** additional public protocol **********

	/**
	 * Return the object the stack locks on while performing
	 * its operations.
	 */
	public Object getMutex() {
		return this.mutex;
	}


	// ********** Object overrides **********

	@Override
	public String toString() {
		synchronized (this.mutex) {
			return this.bag.toString();
		}
	}

	private void writeObject(java.io.ObjectOutputStream s) throws java.io.IOException {
		synchronized (this.mutex) {
			s.defaultWriteObject();
		}
	}

}

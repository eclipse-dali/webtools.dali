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

import java.util.AbstractSet;
import java.util.Collection;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * This class implements the {@link Set} interface with a hash table
 * (as implemented by {@link IdentityHashMap}), using
 * reference-equality in place of object-equality when comparing elements.
 * 
 * @param <E> the type of elements maintained by the bag
 * 
 * @see Collection
 * @see Set
 * @see java.util.HashSet
 * @see IdentityHashMap
 */
public class IdentityHashSet<E>
	extends AbstractSet<E>
	implements Cloneable, java.io.Serializable
{
	private transient IdentityHashMap<E, Object> map;

	// dummy value to associate with an element in the backing identity map
	private static final Object PRESENT = new Object();

	/**
	 * Construct a new, empty set; the backing {@link IdentityHashMap} instance
	 * has a default expected maximum size (21).
	 */
	public IdentityHashSet() {
		super();
		this.map = new IdentityHashMap<>();
	}

	/**
	 * Construct a new, empty set; the backing {@link IdentityHashMap} instance
	 * has the specified expected maximum size.
	 * 
	 * @param expectedMaxSize the expected maximum size of the map
	 * @throws IllegalArgumentException if the expected maximum size is less
	 *   than zero
	 */
	public IdentityHashSet(int expectedMaxSize) {
		super();
		this.map = new IdentityHashMap<>(expectedMaxSize);
	}

	/**
	 * Construct a new set containing the elements in the specified
	 * collection.
	 * 
	 * @param c the collection whose elements are to be placed into this set
	 * @throws NullPointerException if the specified collection is <code>null</code>
	 */
	public IdentityHashSet(Collection<? extends E> c) {
		super();
		this.map = new IdentityHashMap<>((int) ((1 + c.size()) * 1.1));
		this.addAll(c);
	}

	@Override
	public Iterator<E> iterator() {
		return this.map.keySet().iterator();
	}

	@Override
	public int size() {
		return this.map.size();
	}

	@Override
	public boolean isEmpty() {
		return this.map.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return this.map.containsKey(o);
	}

	@Override
	public boolean add(E e) {
		return this.map.put(e, PRESENT) == null;
	}

	@Override
	public boolean remove(Object o) {
		return this.map.remove(o) == PRESENT;
	}

	/**
	 * Override to use object-identity.
	 */
	@Override
	public boolean removeAll(Collection<?> c) {
		boolean modified = false;
		for (Object e : c) {
			modified |= this.remove(e);
		}
		return modified;
	}

	/**
	 * Override to use object-identity.
	 */
	@Override
	public boolean retainAll(Collection<?> c) {
		if ( ! (c instanceof IdentityHashSet)) {
			c = new IdentityHashSet<Object>(c);
		}
		return super.retainAll(c);
	}

	@Override
	public void clear() {
		this.map.clear();
	}

	@Override
	@SuppressWarnings("unchecked")
	public IdentityHashSet<E> clone() {
		try {
			IdentityHashSet<E> clone = (IdentityHashSet<E>) super.clone();
			clone.map = (IdentityHashMap<E, Object>) this.map.clone();
			return clone;
		} catch (CloneNotSupportedException ex) {
			throw new InternalError();
		}
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (o instanceof IdentityHashSet<?>) {
			@SuppressWarnings("unchecked")
			IdentityHashSet<E> s = (IdentityHashSet<E>) o;
			return s.map.equals(this.map);
		}
		return this.equals_(o);
	}

	// hmmm...
	private boolean equals_(Object o) {
		return (o instanceof Set<?>) && 
				new HashSet<Object>(this).equals(o);
	}

	@Override
	public int hashCode() {
		int h = 0;
		for (E o : this) {
			h += System.identityHashCode(o);
		}
		return h;
	}

	/**
	 * Save the state of this set to a stream (i.e. serialize it).
	 *
	 * @serialData Emit the size of the set (int), followed by all of its
	 *     elements(each an Object) in no particular order.
	 */
	private void writeObject(java.io.ObjectOutputStream s) throws java.io.IOException {
		// write out any hidden stuff
		s.defaultWriteObject();

		// write out size
		s.writeInt(this.map.size());

		// write out elements
		for (E e : this.map.keySet())
			s.writeObject(e);
	}

	static final long serialVersionUID = 1L;

	/**
	 * Reconstitute the set from a stream (i.e. deserialize it).
	 */
	private void readObject(java.io.ObjectInputStream s) throws java.io.IOException, ClassNotFoundException {
		// read in any hidden stuff
		s.defaultReadObject();

		// read in size and create backing map
		int size = s.readInt();
		this.map = new IdentityHashMap<>(size);

		// read the elements and add to the set
		for (int i = 0; i < size; i++) {
			@SuppressWarnings("unchecked")
			E element = (E) s.readObject();
			this.map.put(element, PRESENT);
		}
	}
}

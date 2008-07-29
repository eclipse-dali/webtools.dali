/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal;

import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This class implements the <code>Bag</code> interface with a
 * hash table, using object-identity in place of object-equality when
 * comparing elements. In other words, in an <code>IdentityHashBag</code>,
 * two objects <code>k1</code> and <code>k2</code> are considered
 * equal if and only if <code>(k1 == k2)</code>. (In normal <code>Bag</code>
 * implementations (like <code>HashBag</code>) two objects <code>k1</code>
 * and <code>k2</code> are considered equal if and only if
 * <code>(k1 == null ? k2 == null : k1.equals(k2))</code>.)
 * <p>
 * <b>
 * This class is <i>not</i> a general-purpose <code>Bag</code>
 * implementation! While this class implements the <code>Bag</code> interface, it
 * intentionally violates <code>Bag's</code> general contract, which mandates the
 * use of the <code>equals</code> method when comparing objects. This class is
 * designed for use only in the rare cases wherein object-identity
 * semantics are required.
 * </b>
 * <p>
 * This class makes no guarantees as to the iteration order of
 * the bag's elements; in particular, it does not guarantee that the order
 * will remain constant over time. This class permits the <code>null</code>
 * element.
 * <p>
 * This class offers constant time performance for the basic operations
 * (<code>add</code>, <code>remove</code>, <code>contains</code> and
 * <code>size</code>), assuming the system identity hash function
 * ({@link System#identityHashCode(Object)}) disperses elements properly
 * among the buckets. Iterating over this bag requires time
 * proportional to the sum of the bag's size (the number of elements) plus the
 * "capacity" of the backing hash table (the number of buckets). Thus, it is
 * important not to set the initial capacity too high (or the load factor too
 * low) if iteration performance is important.
 * <p>
 * <b>Note that this implementation is not synchronized.</b> If multiple
 * threads access a bag concurrently, and at least one of the threads modifies
 * the bag, it <i>must</i> be synchronized externally. This is typically
 * accomplished by synchronizing on some object that naturally encapsulates
 * the bag. If no such object exists, the bag should be "wrapped" using the
 * <code>Collections.synchronizedCollection</code> method. This is
 * best done at creation time, to prevent accidental unsynchronized access
 * to the bag:
 * <pre>
 * Collection c = Collections.synchronizedCollection(new IdentityHashBag(...));
 * </pre>
 * <p>
 * The iterators returned by this class's <code>iterator</code> method are
 * <i>fail-fast</i>: if the bag is modified at any time after the iterator is
 * created, in any way except through the iterator's own <code>remove</code>
 * method, the iterator throws a <code>ConcurrentModificationException</code>.
 * Thus, in the face of concurrent modification, the iterator fails quickly
 * and cleanly, rather than risking arbitrary, non-deterministic behavior at
 * an undetermined time in the future.
 * 
 * @see	Collections#synchronizedCollection(Collection)
 */

public class IdentityHashBag<E>
	extends AbstractCollection<E>
	implements Bag<E>, Cloneable, Serializable
{

	/** The hash table. */
	transient Entry<E>[] table;

	/** The total number of entries in the bag. */
	transient int count = 0;

	/** The number of unique entries in the bag. */
	transient int uniqueCount = 0;

	/**
	 * The hash table is rehashed when its size exceeds this threshold. (The
	 * value of this field is (int)(capacity * loadFactor).)
	 *
	 * @serial
	 */
	private int threshold;

	/**
	 * The load factor for the hash table.
	 *
	 * @serial
	 */
	private float loadFactor;

	/**
	 * The number of times this bag has been structurally modified.
	 * Structural modifications are those that change the number of entries in
	 * the bag or otherwise modify its internal structure (e.g. rehash).
	 * This field is used to make iterators on this bag fail-fast.
	 *
	 * @see java.util.ConcurrentModificationException
	 */
	transient int modCount = 0;

	/**
	 * Constructs a new, empty bag with the
	 * default capacity, which is 11, and load factor, which is 0.75.
	 */
	public IdentityHashBag() {
		this(11, 0.75f);
	}

	/**
	 * Constructs a new, empty bag with the specified initial capacity
	 * and default load factor, which is 0.75.
	 *
	 * @param initialCapacity the initial capacity of the backing map.
	 * @throws IllegalArgumentException if the initial capacity is less
	 *     than zero.
	 */
	public IdentityHashBag(int initialCapacity) {
		this(initialCapacity, 0.75f);
	}

	/**
	 * Constructs a new, empty bag with
	 * the specified initial capacity and the specified load factor.
	 *
	 * @param initialCapacity the initial capacity of the backing map.
	 * @param loadFactor the load factor of the backing map.
	 * @throws IllegalArgumentException if the initial capacity is less
	 *     than zero, or if the load factor is nonpositive.
	 */
	public IdentityHashBag(int initialCapacity, float loadFactor) {
		if (initialCapacity < 0) {
			throw new IllegalArgumentException("Illegal Initial Capacity: " + initialCapacity);
		}
		if (loadFactor <= 0 || Float.isNaN(loadFactor)) {
			throw new IllegalArgumentException("Illegal Load factor: " + loadFactor);
		}
		if (initialCapacity == 0) {
			initialCapacity = 1;
		}
		this.loadFactor = loadFactor;
		this.table = this.buildTable(initialCapacity);
		this.threshold = (int) (initialCapacity * loadFactor);
	}

	/**
	 * Constructs a new bag containing the elements in the specified
	 * collection. The capacity of the bag is
	 * twice the size of the specified collection or 11 (whichever is
	 * greater), and the default load factor, which is 0.75, is used.
	 *
	 * @param c the collection whose elements are to be placed into this bag.
	 */
	public IdentityHashBag(Collection<? extends E> c) {
		this(Math.max(2*c.size(), 11));
		this.addAll(c);
	}

	/**
	 * This implementation simply returns the maintained count.
	 */
	@Override
	public int size() {
		return this.count;
	}

	/**
	 * This implementation simply compares the maintained count to zero.
	 */
	@Override
	public boolean isEmpty() {
		return this.count == 0;
	}

	/**
	 * This implementation searches for the object in the hash table by
	 * calculating the object's identity hash code and examining the
	 * entries in the corresponding hash table bucket.
	 */
	@Override
	public boolean contains(Object o) {
		Entry<E>[] tab = this.table;
		int hash = System.identityHashCode(o);
		int index = (hash & 0x7FFFFFFF) % tab.length;
		for (Entry<E> e = tab[index]; e != null; e = e.next) {
			if ((e.hash == hash) && (e.object == o)) {
				return true;
			}
		}
		return false;
	}

	public int count(Object o) {
		Entry<E>[] tab = this.table;
		int hash = System.identityHashCode(o);
		int index = (hash & 0x7FFFFFFF) % tab.length;
		for (Entry<E> e = tab[index]; e != null; e = e.next) {
			if ((e.hash == hash) && (e.object == o)) {
				return e.count;
			}
		}
		return 0;
	}

	/**
	 * Rehashes the contents of this bag into a new hash table
	 * with a larger capacity. This method is called when the
	 * number of different elements in this map exceeds its
	 * capacity and load factor.
	 */
	private void rehash() {
		Entry<E>[] oldMap = this.table;
		int oldCapacity = oldMap.length;

		int newCapacity = oldCapacity * 2 + 1;
		Entry<E>[] newTable = this.buildTable(newCapacity);

		this.modCount++;
		this.threshold = (int) (newCapacity * this.loadFactor);
		this.table = newTable;

		for (int i = oldCapacity; i-- > 0; ) {
			for (Entry<E> old = oldMap[i]; old != null; ) {
				Entry<E> e = old;
				old = old.next;

				int index = (e.hash & 0x7FFFFFFF) % newCapacity;
				e.next = newTable[index];
				newTable[index] = e;
			}
		}
	}

	@SuppressWarnings("unchecked")
	private Entry<E>[] buildTable(int capacity) {
		return new Entry[capacity];
	}

	@SuppressWarnings("unchecked")
	private <T> Entry<E> buildEntry(int hash, Object o, Entry<T> next) {
		return new Entry(hash, o, next);
	}

	@SuppressWarnings("unchecked")
	private <T> Entry<E> buildEntry(int hash, Object o, int cnt, Entry<T> next) {
		return new Entry(hash, o, cnt, next);
	}

	/**
	 * This implementation searches for the object in the hash table by
	 * calculating the object's identity hash code and examining the
	 * entries in the corresponding hash table bucket.
	 */
	@Override
	public boolean add(E o) {
		this.modCount++;
		Entry<E>[] tab = this.table;
		int hash = 0;
		int index = 0;

		// if the object is already in the bag, simply bump its count
		hash = System.identityHashCode(o);
		index = (hash & 0x7FFFFFFF) % tab.length;
		for (Entry<E> e = tab[index]; e != null; e = e.next) {
			if ((e.hash == hash) && (e.object == o)) {
				e.count++;
				this.count++;
				return true;
			}
		}

		// rehash the table if the threshold is exceeded
		if (this.uniqueCount >= this.threshold) {
			this.rehash();
			tab = this.table;
			index = (hash & 0x7FFFFFFF) % tab.length;
		}

		// create the new entry and put it in the table
		Entry<E> e = this.buildEntry(hash, o, tab[index]);
		tab[index] = e;
		this.count++;
		this.uniqueCount++;
		return true;
	}

	/**
	 * This implementation searches for the object in the hash table by
	 * calculating the object's identity hash code and examining the
	 * entries in the corresponding hash table bucket.
	 */
	public boolean add(E o, int cnt) {
		if (cnt <= 0) {
			return false;
		}
		this.modCount++;
		Entry<E>[] tab = this.table;
		int hash = 0;
		int index = 0;

		// if the object is already in the bag, simply bump its count
		hash = System.identityHashCode(o);
		index = (hash & 0x7FFFFFFF) % tab.length;
		for (Entry<E> e = tab[index]; e != null; e = e.next) {
			if ((e.hash == hash) && (e.object == o)) {
				e.count += cnt;
				this.count += cnt;
				return true;
			}
		}

		// rehash the table if the threshold is exceeded
		if (this.uniqueCount >= this.threshold) {
			this.rehash();
			tab = this.table;
			index = (hash & 0x7FFFFFFF) % tab.length;
		}

		// create the new entry and put it in the table
		Entry<E> e = this.buildEntry(hash, o, cnt, tab[index]);
		tab[index] = e;
		this.count += cnt;
		this.uniqueCount++;
		return true;
	}

	/**
	 * This implementation searches for the object in the hash table by
	 * calculating the object's identity hash code and examining the
	 * entries in the corresponding hash table bucket.
	 */
	@Override
	public boolean remove(Object o) {
		Entry<E>[] tab = this.table;
		int hash = System.identityHashCode(o);
		int index = (hash & 0x7FFFFFFF) % tab.length;
		for (Entry<E> e = tab[index], prev = null; e != null; prev = e, e = e.next) {
			if ((e.hash == hash) && (e.object == o)) {
				this.modCount++;
				e.count--;
				// if we are removing the last one, remove the entry from the table
				if (e.count == 0) {
					if (prev == null) {
						tab[index] = e.next;
					} else {
						prev.next = e.next;
					}
					this.uniqueCount--;
				}
				this.count--;
				return true;
			}
		}
		return false;
	}

	/**
	 * This implementation searches for the object in the hash table by
	 * calculating the object's identity hash code and examining the
	 * entries in the corresponding hash table bucket.
	 */
	public boolean remove(Object o, int cnt) {
		if (cnt <= 0) {
			return false;
		}
		Entry<E>[] tab = this.table;
		int hash = System.identityHashCode(o);
		int index = (hash & 0x7FFFFFFF) % tab.length;
		for (Entry<E> e = tab[index], prev = null; e != null; prev = e, e = e.next) {
			if ((e.hash == hash) && (e.object == o)) {
				this.modCount++;
				int cnt2 = (cnt < e.count) ? cnt : e.count;
				e.count -= cnt2;
				// if we are removing the last element(s), remove the entry from the table
				if (e.count == 0) {
					if (prev == null) {
						tab[index] = e.next;
					} else {
						prev.next = e.next;
					}
					this.uniqueCount--;
				}
				this.count -= cnt2;
				return true;
			}
		}
		return false;
	}

	/**
	 * This implementation uses object-identity to determine whether
	 * specified collection contains a particular element.
	 */
	@Override
	public boolean removeAll(Collection<?> c) {
		return super.removeAll(this.buildIdentityHashBag(c));
	}

	/**
	 * This implementation uses object-identity to determine whether
	 * specified collection contains a particular element.
	 */
	@Override
	public boolean retainAll(Collection<?> c) {
		return super.retainAll(this.buildIdentityHashBag(c));
	}

	@SuppressWarnings("unchecked")
	private Collection<?> buildIdentityHashBag(Collection<?> c) {
		return new IdentityHashBag(c);
	}

	/**
	 * This implementation simply clears out all of the hash table buckets.
	 */
	@Override
	public void clear() {
		Entry<E>[] tab = this.table;
		this.modCount++;
		for (int i = tab.length; --i >= 0; ) {
			tab[i] = null;
		}
		this.count = 0;
		this.uniqueCount = 0;
	}

	/**
	 * Returns a shallow copy of this bag: the elements
	 * themselves are not cloned.
	 *
	 * @return a shallow copy of this bag.
	 */
	@Override
	public IdentityHashBag<E> clone() {
		try {
			@SuppressWarnings("unchecked")
			IdentityHashBag<E> clone = (IdentityHashBag<E>) super.clone();
			clone.table = this.buildTable(this.table.length);
			for (int i = this.table.length; i-- > 0; ) {
				clone.table[i] = (this.table[i] == null) 
						? null : (Entry<E>) this.table[i].clone();
			}
			clone.modCount = 0;
			return clone;
		} catch (CloneNotSupportedException e) {
			throw new InternalError();
		}
	}


	/**
	 * Hash table collision list entry.
	 */
	private static class Entry<E> implements Bag.Entry<E> {
		int hash;
		E object;
		int count;
		Entry<E> next;

		Entry(int hash, E object, Entry<E> next) {
			this(hash, object, 1, next);
		}

		Entry(int hash, E object, int count, Entry<E> next) {
			this.hash = hash;
			this.object = object;
			this.count = count;
			this.next = next;
		}

		/**
		 * Cascade the clone to all the entries in the same bucket.
		 */
		@Override
		@SuppressWarnings("unchecked")
		protected Entry<E> clone() {
			return new Entry(this.hash, this.object, this.count,
					(this.next == null ? null : this.next.clone()));
		}


		// ***** Bag.Entry implementation
		public E getElement() {
			return this.object;
		}

		public int getCount() {
			return this.count;
		}

		public int setCount(int count) {
			if (count <= 0) {
				throw new IllegalArgumentException("count must be greater than zero: " + count);
			}
			int old = this.count;
			this.count = count;
			return old;
		}

		@Override
		public boolean equals(Object o) {
			if ( ! (o instanceof Bag.Entry)) {
				return false;
			}
			@SuppressWarnings("unchecked")
			Bag.Entry e = (Bag.Entry) o;
			return (this.object == e.getElement())
					&& (this.count == e.getCount());
		}

		@Override
		public int hashCode() {
			E o = this.object;
			return (o == null) ? 0 : (this.count * o.hashCode());
		}

		@Override
		public String toString() {
			return this.object + "=>" + this.count;
		}
	}


	@Override
	@SuppressWarnings("unchecked")
	public Iterator<E> iterator() {
		return (this.count == 0) ? EMPTY_ITERATOR : new HashIterator();
	}

	/**
	 * Return an iterator that returns each item in the bag
	 * once and only once, irrespective of how many times
	 * the item was added to the bag.
	 */
	@SuppressWarnings("unchecked")
	public Iterator<E> uniqueIterator() {
		return (this.count == 0) ? EMPTY_ITERATOR : new UniqueIterator();
	}

	@SuppressWarnings("unchecked")
	public Iterator<Bag.Entry<E>> entries() {
		return (this.count == 0) ? EMPTY_ITERATOR : new EntryIterator();
	}


	/**
	 * Empty iterator that does just about nothing.
	 */
	@SuppressWarnings("unchecked")
	private static final Iterator EMPTY_ITERATOR = new EmptyIterator();

	@SuppressWarnings("unchecked")
	private static class EmptyIterator implements Iterator {

		EmptyIterator() {
			super();
		}

		public boolean hasNext() {
			return false;
		}

		public Object next() {
			throw new NoSuchElementException();
		}

		public void remove() {
			throw new IllegalStateException();
		}
	}


	private class HashIterator implements Iterator<E> {
		private Entry<E>[] localTable = IdentityHashBag.this.table;
		private int index = this.localTable.length;	// start at the end of the table
		private Entry<E> nextEntry = null;
		private int nextEntryCount = 0;
		private Entry<E> lastReturnedEntry = null;

		/**
		 * The modCount value that the iterator believes that the backing
		 * bag should have. If this expectation is violated, the iterator
		 * has detected a concurrent modification.
		 */
		private int expectedModCount = IdentityHashBag.this.modCount;

		HashIterator() {
			super();
		}

		public boolean hasNext() {
			Entry<E> e = this.nextEntry;
			int i = this.index;
			Entry<E>[] tab = this.localTable;
			// Use locals for faster loop iteration
			while ((e == null) && (i > 0)) {
				e = tab[--i];		// move backwards through the table
			}
			this.nextEntry = e;
			this.index = i;
			return e != null;
		}

		public E next() {
			if (IdentityHashBag.this.modCount != this.expectedModCount) {
				throw new ConcurrentModificationException();
			}
			Entry<E> et = this.nextEntry;
			int i = this.index;
			Entry<E>[] tab = this.localTable;
			// Use locals for faster loop iteration
			while ((et == null) && (i > 0)) {
				et = tab[--i];		// move backwards through the table
			}
			this.nextEntry = et;
			this.index = i;
			if (et == null) {
				throw new NoSuchElementException();
			}
			Entry<E> e = this.lastReturnedEntry = this.nextEntry;
			this.nextEntryCount++;
			if (this.nextEntryCount == e.count) {
				this.nextEntry = e.next;
				this.nextEntryCount = 0;
			}
			return e.object;
		}

		public void remove() {
			if (this.lastReturnedEntry == null) {
				throw new IllegalStateException();
			}
			if (IdentityHashBag.this.modCount != this.expectedModCount) {
				throw new ConcurrentModificationException();
			}
			Entry<E>[] tab = this.localTable;
			int slot = (this.lastReturnedEntry.hash & 0x7FFFFFFF) % tab.length;
			for (Entry<E> e = tab[slot], prev = null; e != null; prev = e, e = e.next) {
				if (e == this.lastReturnedEntry) {
					IdentityHashBag.this.modCount++;
					this.expectedModCount++;
					e.count--;
					if (e.count == 0) {
						// if we are removing the last one, remove the entry from the table
						if (prev == null) {
							tab[slot] = e.next;
						} else {
							prev.next = e.next;
						}
						IdentityHashBag.this.uniqueCount--;
					} else {
						// slide back the count to account for the just-removed element
						this.nextEntryCount--;
					}
					IdentityHashBag.this.count--;
					this.lastReturnedEntry = null;	// it cannot be removed again
					return;
				}
			}
			throw new ConcurrentModificationException();
		}
	}


	private class EntryIterator implements Iterator<Entry<E>> {
		private Entry<E>[] localTable = IdentityHashBag.this.table;
		private int index = this.localTable.length;	// start at the end of the table
		private Entry<E> nextEntry = null;
		private Entry<E> lastReturnedEntry = null;

		/**
		 * The modCount value that the iterator believes that the backing
		 * bag should have. If this expectation is violated, the iterator
		 * has detected a concurrent modification.
		 */
		private int expectedModCount = IdentityHashBag.this.modCount;

		EntryIterator() {
			super();
		}

		public boolean hasNext() {
			Entry<E> e = this.nextEntry;
			int i = this.index;
			Entry<E>[] tab = this.localTable;
			// Use locals for faster loop iteration
			while ((e == null) && (i > 0)) {
				e = tab[--i];		// move backwards through the table
			}
			this.nextEntry = e;
			this.index = i;
			return e != null;
		}

		public Entry<E> next() {
			if (IdentityHashBag.this.modCount != this.expectedModCount) {
				throw new ConcurrentModificationException();
			}
			Entry<E> et = this.nextEntry;
			int i = this.index;
			Entry<E>[] tab = this.localTable;
			// Use locals for faster loop iteration
			while ((et == null) && (i > 0)) {
				et = tab[--i];		// move backwards through the table
			}
			this.nextEntry = et;
			this.index = i;
			if (et == null) {
				throw new NoSuchElementException();
			}
			Entry<E> e = this.lastReturnedEntry = this.nextEntry;
			this.nextEntry = e.next;
			return e;
		}

		public void remove() {
			if (this.lastReturnedEntry == null) {
				throw new IllegalStateException();
			}
			if (IdentityHashBag.this.modCount != this.expectedModCount) {
				throw new ConcurrentModificationException();
			}
			Entry<E>[] tab = this.localTable;
			int slot = (this.lastReturnedEntry.hash & 0x7FFFFFFF) % tab.length;
			for (Entry<E> e = tab[slot], prev = null; e != null; prev = e, e = e.next) {
				if (e == this.lastReturnedEntry) {
					IdentityHashBag.this.modCount++;
					this.expectedModCount++;
					// remove the entry from the table
					if (prev == null) {
						tab[slot] = e.next;
					} else {
						prev.next = e.next;
					}
					IdentityHashBag.this.uniqueCount--;
					IdentityHashBag.this.count -= this.lastReturnedEntry.count;
					this.lastReturnedEntry = null;	// it cannot be removed again
					return;
				}
			}
			throw new ConcurrentModificationException();
		}

	}


	private class UniqueIterator implements Iterator<E> {
		private EntryIterator entryIterator = new EntryIterator();
		
		UniqueIterator() {
			super();
		}

		public boolean hasNext() {
			return this.entryIterator.hasNext();
		}

		public E next() {
			return this.entryIterator.next().object;
		}

		public void remove() {
			this.entryIterator.remove();
		}

	}


	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		} else if (o instanceof IdentityHashBag) {
			@SuppressWarnings("unchecked")
			IdentityHashBag<E> b = (IdentityHashBag<E>) o;
			if (b.size() != this.size()) {
				return false;
			}
			IdentityHashBag<E> clone = this.clone();
			for (E e : b) {
				if ( ! clone.remove(e)) {
					return false;
				}
			}
			return clone.isEmpty();
		} else if (o instanceof Bag) {
			// hmmm...
			return this.buildBag().equals(o);
		} else {
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	private Bag<E> buildBag() {
		return new HashBag(this);
	}

	@Override
	public int hashCode() {
		int h = 0;
		for (E e : this) {
			h += System.identityHashCode(e);
		}
		return h;
	}

	/**
	 * Save the state of this bag to a stream (i.e. serialize it).
	 *
	 * @serialData Emit the capacity of the bag (int),
	 *     followed by the number of unique elements in the bag (int),
	 *     followed by all of the bag's elements (each an Object) and
	 *     their counts (each an int), in no particular order.
	 */
	private synchronized void writeObject(java.io.ObjectOutputStream s)
				throws java.io.IOException {
		// write out the threshold, load factor, and any hidden stuff
		s.defaultWriteObject();

		// write out number of buckets
		s.writeInt(this.table.length);

		// write out number of unique elements
		s.writeInt(this.uniqueCount);

		Entry<E>[] tab = this.table;
		// write out elements and counts (alternating)
		for (Entry<E> entry : tab) {
			while (entry != null) {
				s.writeObject(entry.object);
				s.writeInt(entry.count);
				entry = entry.next;
			}
		}
	}

	private static final long serialVersionUID = 1L;

	/**
	 * Reconstitute the bag from a stream (i.e. deserialize it).
	 */
	private synchronized void readObject(java.io.ObjectInputStream s)
				throws java.io.IOException, ClassNotFoundException {
		// read in the threshold, loadfactor, and any hidden stuff
		s.defaultReadObject();

		// read in number of buckets and allocate the bucket array
		this.table = this.buildTable(s.readInt());

		// read in number of unique elements
		int unique = s.readInt();

		// read the elements and counts, and put the elements in the bag
		for (int i = 0; i < unique; i++) {
			@SuppressWarnings("unchecked")
			E element = (E) s.readObject();
			int elementCount = s.readInt();
			for (int j = 0; j < elementCount; j++) {
				this.add(element);
			}
		}
	}

}

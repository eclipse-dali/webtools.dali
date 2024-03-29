/*******************************************************************************
 * Copyright (c) 2005, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.collection;

import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.eclipse.jpt.common.utility.collection.Bag;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * This class implements the {@link Bag} interface, backed by a
 * hash table. It makes no guarantees as to the iteration order of
 * the bag's elements; in particular, it does not guarantee the order
 * will remain constant over time. This class permits the <code>null</code>
 * element.
 * <p>
 * This class offers constant time performance for the basic operations
 * (<code>add</code>, <code>remove</code>, <code>contains</code> and
 * <code>size</code>), assuming the hash function disperses the elements
 * properly among the buckets. Iterating over this bag requires time
 * proportional to the sum of the bag's size (the number of elements) plus the
 * "capacity" of the backing hash table (the number of buckets). Thus, it is
 * important not to set the initial capacity too high (or the load factor too
 * low) if iteration performance is important.
 * <p>
 * <strong>Note that this implementation is not synchronized.</strong> If multiple
 * threads access a bag concurrently, and at least one of the threads modifies
 * the bag, it <em>must</em> be synchronized externally. This is typically
 * accomplished by synchronizing on some object that naturally encapsulates
 * the bag. If no such object exists, the bag should be "wrapped" using the
 * <code>Collections.synchronizedCollection</code> method. This is
 * best done at creation time, to prevent accidental unsynchronized access
 * to the bag:
 * <pre>
 * Collection c = Collections.synchronizedCollection(new HashBag(...));
 * </pre>
 * <p>
 * The iterators returned by this class's <code>iterator</code> method are
 * <em>fail-fast</em>: if the bag is modified at any time after the iterator is
 * created, in any way except through the iterator's own <code>remove</code>
 * method, the iterator throws a {@link ConcurrentModificationException}.
 * Thus, in the face of concurrent modification, the iterator fails quickly
 * and cleanly, rather than risking arbitrary, non-deterministic behavior at
 * an undetermined time in the future.
 * <p>
 * Note that the fail-fast behavior of an iterator cannot be guaranteed
 * as it is, generally speaking, impossible to make any hard guarantees in the
 * presence of unsynchronized concurrent modification. Fail-fast iterators
 * throw <code>ConcurrentModificationException</code> on a best-effort basis.
 * Therefore, it would be wrong to write a program that depended on this
 * exception for its correctness: <em>the fail-fast behavior of iterators
 * should be used only to detect bugs.</em>
 * 
 * @param <E> the type of elements maintained by the bag
 * 
 * @see Collection
 * @see Bag
 * @see SynchronizedBag
 * @see	java.util.Collections#synchronizedCollection(Collection)
 * @see IdentityHashBag
 */
public class HashBag<E>
	extends AbstractCollection<E>
	implements Bag<E>, Cloneable, Serializable
{
	/** The hash table. Resized as necessary. Length MUST Always be a power of two. */
	transient Entry[] table;

	/** The total number of entries in the bag. */
	transient int size = 0;

	/** The number of UNIQUE entries in the bag. */
	transient int uniqueCount = 0;

	/**
	 * The hash table is rehashed when its size exceeds this threshold. (The
	 * value of this field is <code>(int) (capacity * loadFactor)</code>.)
	 *
	 * @serial threshold
	 */
	private int threshold;

	/**
	 * The load factor for the hash table.
	 *
	 * @serial load factor
	 */
	private final float loadFactor;

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
	 * The default initial capacity - MUST be a power of two.
	 */
	private static final int DEFAULT_INITIAL_CAPACITY = 16;

	/**
	 * The maximum capacity, used if a higher value is implicitly specified
	 * by either of the constructors with arguments.
	 * MUST be a power of two <= (1 << 30).
	 */
	private static final int MAXIMUM_CAPACITY = 1 << 30;

	/**
	 * The load factor used when none specified in constructor.
	 */
	private static final float DEFAULT_LOAD_FACTOR = 0.75f;

	/**
	 * Construct a new, empty bag with the
	 * default capacity, which is 16, and load factor, which is 0.75.
	 */
	public HashBag() {
		this(DEFAULT_INITIAL_CAPACITY);
	}

	/**
	 * Construct a new, empty bag with the specified initial capacity
	 * and the default load factor, which is 0.75.
	 *
	 * @param initialCapacity the initial capacity
	 * @throws IllegalArgumentException if the initial capacity is less
	 *     than zero
	 */
	public HashBag(int initialCapacity) {
		this(initialCapacity, DEFAULT_LOAD_FACTOR, false);  // false = do not validate args
	}

	/**
	 * Construct a new, empty bag with
	 * the specified initial capacity and load factor.
	 *
	 * @param initialCapacity the initial capacity
	 * @param loadFactor the load factor
	 * @throws IllegalArgumentException if the initial capacity is less
	 *     than zero or if the load factor is non-positive
	 */
	public HashBag(int initialCapacity, float loadFactor) {
		this(initialCapacity, loadFactor, true);  // true = validate args
	}

	private HashBag(int initialCapacity, float loadFactor, boolean validateArgs) {
		super();
		int capacity = initialCapacity;
		if (validateArgs) {
			if (capacity < 0) {
				throw new IllegalArgumentException("Illegal Initial Capacity: " + capacity); //$NON-NLS-1$
			}
			if (capacity > MAXIMUM_CAPACITY) {
				capacity = MAXIMUM_CAPACITY;
			}
			if (loadFactor <= 0 || Float.isNaN(loadFactor)) {
				throw new IllegalArgumentException("Illegal Load factor: " + loadFactor); //$NON-NLS-1$
			}
	
			// find a power of 2 >= 'initialCapacity'
			capacity = 1;
			while (capacity < initialCapacity) {
				capacity <<= 1;
			}
		}

		this.loadFactor = loadFactor;
		this.table = this.buildTable(capacity);
		this.threshold = (int) (capacity * loadFactor);
	}

	/**
	 * Construct a new bag containing the elements in the specified
	 * collection. The bag's load factor will be the default, which is 0.75,
	 * and its initial capacity will be sufficient to hold all the elements in
	 * the specified collection.
	 * 
	 * @param c the collection whose elements are to be placed into this bag.
	 */
	public HashBag(Collection<? extends E> c) {
		this(Math.max((int) (c.size() / DEFAULT_LOAD_FACTOR) + 1, DEFAULT_INITIAL_CAPACITY), DEFAULT_LOAD_FACTOR);
		this.addAll_(c);
	}

	/**
	 * Return a hash for the specified object.
	 */
	private int hash(Object o) {
		return (o == null) ? 0 : this.tweakHash(o.hashCode());
	}

	/**
	 * Tweak the specified hash, to defend against poor implementations
	 * of {@link Object#hashCode()}.
	 */
	private int tweakHash(int h) {
		h ^= (h >>> 20) ^ (h >>> 12);
		return h ^ (h >>> 7) ^ (h >>> 4);
	}

	/**
	 * Return the index for the specified hash.
	 */
	private int index(int hash) {
		return this.index(hash, this.table.length);
	}

	/**
	 * Return the index for the specified hash
	 * within a table with the specified length.
	 */
	int index(int hash, int length) {
		return hash & (length - 1);
	}

	/**
	 * Internal {@link #addAll(Collection)} for construction and cloning.
	 * (No check for re-hash; no change to mod count; no return value.)
	 */
	private void addAll_(Iterable<? extends E> c) {
		for (E e : c) {
			this.add_(e);
		}
	}

	/**
	 * Internal {@link #add(Object)} for construction and cloning.
	 * (No check for re-hash; no change to mod count; no return value.)
	 */
	private void add_(E o) {
		this.add_(o, 1);
	}

	/**
	 * Internal {@link #add(Object, int)} for construction, cloning, and serialization.
	 * (No check for re-hash; no change to mod count; no return value.)
	 */
	private void add_(E o, int cnt) {
		int hash = this.hash(o);
		int index = this.index(hash);
		for (Entry e = this.table[index]; e != null; e = e.next) {
			Object eo;
			if ((e.hash == hash) && (((eo = e.object) == o) || ((o != null) && o.equals(eo)))) {
				e.count += cnt;
				this.size += cnt;
				return;
			}
		}

		// create the new entry and put it in the table
		Entry e = this.buildEntry(hash, o, cnt, this.table[index]);
		this.table[index] = e;
		this.size += cnt;
		this.uniqueCount++;
	}

	/**
	 * This implementation simply returns the maintained size.
	 */
	@Override
	public int size() {
		return this.size;
	}

	/**
	 * This implementation simply compares the maintained size to zero.
	 */
	@Override
	public boolean isEmpty() {
		return this.size == 0;
	}

	/**
	 * Search for the object's entry in the hash table by calculating
	 * the object's hash code and examining the entries in the corresponding hash
	 * table bucket.
	 */
	private Entry getEntry(Object o) {
		int hash = this.hash(o);
		for (Entry e = this.table[this.index(hash)]; e != null; e = e.next) {
			Object eo;
			if ((e.hash == hash) && (((eo = e.object) == o) || ((o != null) && o.equals(eo)))) {
				return e;
			}
		}
		return null;
	}

	@Override
	public boolean contains(Object o) {
		return this.getEntry(o) != null;
	}

	public int count(Object o) {
		Entry e = this.getEntry(o);
		return (e == null) ? 0 : e.count;
	}

	/**
	 * Rehash the contents of the bag into a new hash table
	 * with a larger capacity. This method is called when the
	 * number of different elements in the bag exceeds its
	 * capacity and load factor.
	 */
	private void rehash() {
		Entry[] oldTable = this.table;
		int oldCapacity = oldTable.length;

		if (oldCapacity == MAXIMUM_CAPACITY) {
			this.threshold = Integer.MAX_VALUE;
			return;
		}

		int newCapacity = 2 * oldCapacity;
		Entry[] newTable = this.buildTable(newCapacity);

		for (int i = oldCapacity; i-- > 0; ) {
			for (Entry old = oldTable[i]; old != null; ) {
				Entry e = old;
				old = old.next;

				int index = this.index(e.hash, newCapacity);
				e.next = newTable[index];
				newTable[index] = e;
			}
		}

		this.table = newTable;
		this.threshold = (int) (newCapacity * this.loadFactor);
	}

	// minimize scope of suppressed warnings
	@SuppressWarnings("unchecked")
	private Entry[] buildTable(int capacity) {
		return new HashBag.Entry[capacity];
	}

	/**
	 * This implementation searches for the object in the hash table by calculating
	 * the object's hash code and examining the entries in the corresponding hash
	 * table bucket.
	 */
	@Override
	public boolean add(E o) {
		return this.add(o, 1);
	}

	/**
	 * This implementation searches for the object in the hash table by calculating
	 * the object's hash code and examining the entries in the corresponding hash
	 * table bucket.
	 */
	public boolean add(E o, int cnt) {
		if (cnt <= 0) {
			return false;
		}
		this.modCount++;
		int hash = this.hash(o);
		int index = this.index(hash);

		// if the object is already in the bag, simply bump its count
		for (Entry e = this.table[index]; e != null; e = e.next) {
			Object eo;
			if ((e.hash == hash) && (((eo = e.object) == o) || ((o != null) && o.equals(eo)))) {
				e.count += cnt;
				this.size += cnt;
				return true;
			}
		}

		// rehash the table if we are going to exceed the threshold
		if (this.uniqueCount >= this.threshold) {
			this.rehash();
			index = this.index(hash);  // need to re-calculate the index
		}

		// create the new entry and put it in the table
		Entry e = this.buildEntry(hash, o, cnt, this.table[index]);
		this.table[index] = e;
		this.size += cnt;
		this.uniqueCount++;
		return true;
	}

	// minimize scope of suppressed warnings
	@SuppressWarnings("unchecked")
	private Entry buildEntry(int hash, Object o, int cnt, Entry next) {
		return new Entry(hash, (E) o, cnt, next);
	}

	/**
	 * This implementation searches for the object in the hash table by calculating
	 * the object's hash code and examining the entries in the corresponding hash
	 * table bucket.
	 */
	@Override
	public boolean remove(Object o) {
		return this.remove(o, 1);
	}

	/**
	 * This implementation searches for the object in the hash table by calculating
	 * the object's hash code and examining the entries in the corresponding hash
	 * table bucket.
	 */
	public boolean remove(Object o, int cnt) {
		if (cnt <= 0) {
			return false;
		}
		int hash = this.hash(o);
		int index = this.index(hash);

		for (Entry e = this.table[index], prev = null; e != null; prev = e, e = e.next) {
			Object eo;
			if ((e.hash == hash) && (((eo = e.object) == o) || ((o != null) && o.equals(eo)))) {
				this.modCount++;
				cnt = (cnt < e.count) ? cnt : e.count;
				e.count -= cnt;
				// if we are removing the last element(s), remove the entry from the table
				if (e.count == 0) {
					if (prev == null) {
						this.table[index] = e.next;
					} else {
						prev.next = e.next;
					}
					this.uniqueCount--;
				}
				this.size -= cnt;
				return true;
			}
		}

		return false;
	}

	/**
	 * This implementation simply clears out all of the hash table buckets.
	 */
	@Override
	public void clear() {
		Entry[] tab = this.table;
		this.modCount++;
		for (int i = tab.length; i-- > 0; ) {
			tab[i] = null;
		}
		this.size = 0;
		this.uniqueCount = 0;
	}

	/**
	 * Returns a shallow copy of this bag: the elements
	 * themselves are not cloned.
	 *
	 * @return a shallow copy of this bag.
	 */
	@Override
	public HashBag<E> clone() {
		try {
			@SuppressWarnings("unchecked")
			HashBag<E> clone = (HashBag<E>) super.clone();
			clone.table = this.buildTable(this.table.length);
			clone.size = 0;
			clone.uniqueCount = 0;
			clone.modCount = 0;
			clone.addAll_(this);
			return clone;
		} catch (CloneNotSupportedException e) {
			throw new InternalError();
		}
	}


	/**
	 * Hash table collision list entry.
	 */
	private class Entry
		implements Bag.Entry<E>
	{
		final int hash;
		final E object;
		int count;
		Entry next;

		Entry(int hash, E object, int count, Entry next) {
			this.hash = hash;
			this.object = object;
			this.count = count;
			this.next = next;
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
				throw new IllegalArgumentException("count must be greater than zero: " + count); //$NON-NLS-1$
			}
			int old = this.count;
			this.count = count;
			HashBag.this.size += (count - old);
			return old;
		}

		@Override
		public boolean equals(Object o) {
			if ( ! (o instanceof Bag.Entry<?>)) {
				return false;
			}
			@SuppressWarnings("rawtypes")
			Bag.Entry e = (Bag.Entry) o;
			return (this.count == e.getCount()) &&
					ObjectTools.equals(this.object, e.getElement());
		}

		@Override
		public int hashCode() {
			E o = this.object;
			return (o == null) ? 0 : (this.count * o.hashCode());
		}

		@Override
		public String toString() {
			return this.object + "=>" + this.count; //$NON-NLS-1$
		}
	}


	@Override
	@SuppressWarnings("unchecked")
	public Iterator<E> iterator() {
		return (this.size == 0) ? EMPTY_ITERATOR : new HashIterator();
	}

	@SuppressWarnings("unchecked")
	public Iterator<E> uniqueIterator() {
		return (this.size == 0) ? EMPTY_ITERATOR : new UniqueIterator();
	}

	public int uniqueCount() {
		return this.uniqueCount;
	}

	@SuppressWarnings("unchecked")
	public Iterator<Bag.Entry<E>> entries() {
		return (this.size == 0) ? EMPTY_ITERATOR : new EntryIterator();
	}


	/**
	 * Empty iterator that does just about nothing.
	 */
	@SuppressWarnings("rawtypes")
	private static final Iterator EMPTY_ITERATOR = new EmptyIterator();

	@SuppressWarnings("rawtypes")
	private static class EmptyIterator
		implements Iterator
	{
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


	private class HashIterator
		implements Iterator<E>
	{
		private int index = HashBag.this.table.length;	// start at the end of the table
		private Entry nextEntry = null;
		private int nextEntryCount = 0;
		private Entry lastReturnedEntry = null;

		/**
		 * The modCount value that the iterator believes that the backing
		 * bag should have. If this expectation is violated, the iterator
		 * has detected a concurrent modification.
		 */
		private int expectedModCount = HashBag.this.modCount;

		HashIterator() {
			super();
		}

		public boolean hasNext() {
			Entry e = this.nextEntry;
			int i = this.index;
			Entry[] tab = HashBag.this.table;
			// Use locals for faster loop iteration
			while ((e == null) && (i > 0)) {
				e = tab[--i];		// move backwards through the table
			}
			this.nextEntry = e;
			this.index = i;
			return e != null;
		}

		public E next() {
			if (HashBag.this.modCount != this.expectedModCount) {
				throw new ConcurrentModificationException();
			}
			Entry et = this.nextEntry;
			int i = this.index;
			Entry[] tab = HashBag.this.table;
			// Use locals for faster loop iteration
			while ((et == null) && (i > 0)) {
				et = tab[--i];		// move backwards through the table
			}
			this.nextEntry = et;
			this.index = i;
			if (et == null) {
				throw new NoSuchElementException();
			}
			Entry e = this.lastReturnedEntry = this.nextEntry;
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
			if (HashBag.this.modCount != this.expectedModCount) {
				throw new ConcurrentModificationException();
			}
			int slot = HashBag.this.index(this.lastReturnedEntry.hash, HashBag.this.table.length);
			for (Entry e = HashBag.this.table[slot], prev = null; e != null; prev = e, e = e.next) {
				if (e == this.lastReturnedEntry) {
					HashBag.this.modCount++;
					this.expectedModCount++;
					e.count--;
					if (e.count == 0) {
						// if we are removing the last one, remove the entry from the table
						if (prev == null) {
							HashBag.this.table[slot] = e.next;
						} else {
							prev.next = e.next;
						}
						HashBag.this.uniqueCount--;
					} else {
						// slide back the count to account for the just-removed element
						this.nextEntryCount--;
					}
					HashBag.this.size--;
					this.lastReturnedEntry = null;	// it cannot be removed again
					return;
				}
			}
			throw new ConcurrentModificationException();
		}
	}


	private class EntryIterator
		implements Iterator<Bag.Entry<E>>
	{
		private int index = HashBag.this.table.length;	// start at the end of the table
		private Entry nextEntry = null;
		private Entry lastReturnedEntry = null;

		/**
		 * The modCount value that the iterator believes that the backing
		 * bag should have. If this expectation is violated, the iterator
		 * has detected a concurrent modification.
		 */
		private int expectedModCount = HashBag.this.modCount;

		EntryIterator() {
			super();
		}

		public boolean hasNext() {
			Entry e = this.nextEntry;
			int i = this.index;
			Entry[] tab = HashBag.this.table;
			// Use locals for faster loop iteration
			while ((e == null) && (i > 0)) {
				e = tab[--i];		// move backwards through the table
			}
			this.nextEntry = e;
			this.index = i;
			return e != null;
		}

		public Entry next() {
			if (HashBag.this.modCount != this.expectedModCount) {
				throw new ConcurrentModificationException();
			}
			Entry et = this.nextEntry;
			int i = this.index;
			Entry[] tab = HashBag.this.table;
			// Use locals for faster loop iteration
			while ((et == null) && (i > 0)) {
				et = tab[--i];		// move backwards through the table
			}
			this.nextEntry = et;
			this.index = i;
			if (et == null) {
				throw new NoSuchElementException();
			}
			Entry e = this.lastReturnedEntry = this.nextEntry;
			this.nextEntry = e.next;
			return e;
		}

		public void remove() {
			if (this.lastReturnedEntry == null) {
				throw new IllegalStateException();
			}
			if (HashBag.this.modCount != this.expectedModCount) {
				throw new ConcurrentModificationException();
			}
			int slot = HashBag.this.index(this.lastReturnedEntry.hash, HashBag.this.table.length);
			for (Entry e = HashBag.this.table[slot], prev = null; e != null; prev = e, e = e.next) {
				if (e == this.lastReturnedEntry) {
					HashBag.this.modCount++;
					this.expectedModCount++;
					// remove the entry from the table
					if (prev == null) {
						HashBag.this.table[slot] = e.next;
					} else {
						prev.next = e.next;
					}
					HashBag.this.uniqueCount--;
					HashBag.this.size -= this.lastReturnedEntry.count;
					this.lastReturnedEntry = null;	// it cannot be removed again
					return;
				}
			}
			throw new ConcurrentModificationException();
		}
	}


	private class UniqueIterator
		implements Iterator<E>
	{
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
		}
		if ( ! (o instanceof Bag<?>)) {
			return false;
		}
		@SuppressWarnings("unchecked")
		Bag<E> b = (Bag<E>) o;
		if (b.size() != this.size()) {
			return false;
		}
		if (b.uniqueCount() != this.uniqueCount()) {
			return false;
		}
		for (Iterator<Bag.Entry<E>> stream = b.entries(); stream.hasNext(); ) {
			Bag.Entry<E> entry = stream.next();
			if (entry.getCount() != this.count(entry.getElement())) {
				return false;
			}
		}
		return true;
	}

	@Override
	public int hashCode() {
		int h = 0;
		for (E o : this) {
			if (o != null) {
				h += o.hashCode();
			}
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
	private void writeObject(java.io.ObjectOutputStream s)
				throws java.io.IOException {
		// write out the threshold, load factor, and any hidden stuff
		s.defaultWriteObject();

		// write out number of buckets
		s.writeInt(this.table.length);

		// write out number of unique elements
		s.writeInt(this.uniqueCount);

		// write out elements and counts (alternating)
		if (this.uniqueCount > 0) {
			for (Entry entry : this.table) {
				while (entry != null) {
					s.writeObject(entry.object);
					s.writeInt(entry.count);
					entry = entry.next;
				}
			}
		}
	}

	private static final long serialVersionUID = 1L;

	/**
	 * Reconstitute the bag from a stream (i.e. deserialize it).
	 */
	private void readObject(java.io.ObjectInputStream s)
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
			this.add_(element, elementCount);
		}
	}
}

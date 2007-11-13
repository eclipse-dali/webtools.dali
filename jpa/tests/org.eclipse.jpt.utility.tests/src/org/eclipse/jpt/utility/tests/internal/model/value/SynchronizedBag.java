/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.tests.internal.model.value;

import java.util.Collection;
import java.util.Iterator;
import org.eclipse.jpt.utility.internal.Bag;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.HashBag;
import org.eclipse.jpt.utility.internal.model.event.CollectionChangeEvent;
import org.eclipse.jpt.utility.internal.model.listener.CollectionChangeListener;
import org.eclipse.jpt.utility.internal.model.value.CollectionValueModel;

/**
 * Helper class that keeps an internal collection in synch with the
 * collection held by a collection value model.
 */
class SynchronizedBag<E> implements Bag<E>, CollectionChangeListener {

	private Bag<E> synchBag = new HashBag<E>();

	SynchronizedBag(CollectionValueModel cvm) {
		cvm.addCollectionChangeListener(CollectionValueModel.VALUES, this);
	}


	// ********** Collection implementation **********

	public boolean add(E o) {
		return this.synchBag.add(o);
	}

	public boolean addAll(Collection<? extends E> c) {
		return this.synchBag.addAll(c);
	}

	public void clear() {
		this.synchBag.clear();
	}

	public boolean contains(Object o) {
		return this.synchBag.contains(o);
	}

	public boolean containsAll(Collection<?> c) {
		return this.synchBag.containsAll(c);
	}

	public boolean isEmpty() {
		return this.synchBag.isEmpty();
	}

	public Iterator<E> iterator() {
		return this.synchBag.iterator();
	}

	public boolean remove(Object o) {
		return this.synchBag.remove(o);
	}

	public boolean removeAll(Collection<?> c) {
		return this.synchBag.removeAll(c);
	}

	public boolean retainAll(Collection<?> c) {
		return this.synchBag.retainAll(c);
	}

	public int size() {
		return this.synchBag.size();
	}

	public Object[] toArray() {
		return this.synchBag.toArray();
	}

	public <T> T[] toArray(T[] a) {
		return this.synchBag.toArray(a);
	}


	// ********** Bag implementation **********

	public int count(Object o) {
		return this.synchBag.count(o);
	}

	public boolean add(E o, int count) {
		return this.synchBag.add(o, count);
	}

	public boolean remove(Object o, int count) {
		return this.synchBag.remove(o, count);
	}

	public Iterator<E> uniqueIterator() {
		return this.synchBag.uniqueIterator();
	}

	// ********** CollectionChangeListener implementation **********

	public void itemsAdded(CollectionChangeEvent e) {
		for (@SuppressWarnings("unchecked") Iterator<E> stream = (Iterator<E>) e.items(); stream.hasNext(); ) {
			this.synchBag.add(stream.next());
		}
	}

	public void itemsRemoved(CollectionChangeEvent e) {
		for (@SuppressWarnings("unchecked") Iterator<E> stream = (Iterator<E>) e.items(); stream.hasNext(); ) {
			this.synchBag.remove(stream.next());
		}
	}

	public void collectionCleared(CollectionChangeEvent e) {
		this.synchBag.clear();
	}

	public void collectionChanged(CollectionChangeEvent e) {
		this.synchBag.clear();
		CollectionTools.addAll(this.synchBag, (Iterator) ((CollectionValueModel) e.getSource()).values());
	}


	// ********** standard methods **********

	@Override
	public boolean equals(Object o) {
		return this.synchBag.equals(o);
	}

	@Override
	public int hashCode() {
		return this.synchBag.hashCode();
	}

	@Override
	public String toString() {
		return this.synchBag.toString();
	}

}

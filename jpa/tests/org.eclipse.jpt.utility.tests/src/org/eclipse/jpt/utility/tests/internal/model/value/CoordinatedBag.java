/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
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
import org.eclipse.jpt.utility.model.event.CollectionChangeEvent;
import org.eclipse.jpt.utility.model.listener.CollectionChangeListener;
import org.eclipse.jpt.utility.model.value.CollectionValueModel;

/**
 * Helper class that keeps an internal collection in synch with the
 * collection held by a collection value model.
 */
class CoordinatedBag<E> implements Bag<E>, CollectionChangeListener {

	private Bag<E> bag = new HashBag<E>();

	CoordinatedBag(CollectionValueModel<E> cvm) {
		cvm.addCollectionChangeListener(CollectionValueModel.VALUES, this);
	}


	// ********** Collection implementation **********

	public boolean add(E o) {
		return this.bag.add(o);
	}

	public boolean addAll(Collection<? extends E> c) {
		return this.bag.addAll(c);
	}

	public void clear() {
		this.bag.clear();
	}

	public boolean contains(Object o) {
		return this.bag.contains(o);
	}

	public boolean containsAll(Collection<?> c) {
		return this.bag.containsAll(c);
	}

	public boolean isEmpty() {
		return this.bag.isEmpty();
	}

	public Iterator<E> iterator() {
		return this.bag.iterator();
	}

	public boolean remove(Object o) {
		return this.bag.remove(o);
	}

	public boolean removeAll(Collection<?> c) {
		return this.bag.removeAll(c);
	}

	public boolean retainAll(Collection<?> c) {
		return this.bag.retainAll(c);
	}

	public int size() {
		return this.bag.size();
	}

	public Object[] toArray() {
		return this.bag.toArray();
	}

	public <T> T[] toArray(T[] a) {
		return this.bag.toArray(a);
	}


	// ********** Bag implementation **********

	public int count(Object o) {
		return this.bag.count(o);
	}

	public boolean add(E o, int count) {
		return this.bag.add(o, count);
	}

	public boolean remove(Object o, int count) {
		return this.bag.remove(o, count);
	}

	public Iterator<E> uniqueIterator() {
		return this.bag.uniqueIterator();
	}

	// ********** CollectionChangeListener implementation **********

	public void itemsAdded(CollectionChangeEvent e) {
		for (@SuppressWarnings("unchecked") Iterator<E> stream = (Iterator<E>) e.items(); stream.hasNext(); ) {
			this.bag.add(stream.next());
		}
	}

	public void itemsRemoved(CollectionChangeEvent e) {
		for (@SuppressWarnings("unchecked") Iterator<E> stream = (Iterator<E>) e.items(); stream.hasNext(); ) {
			this.bag.remove(stream.next());
		}
	}

	public void collectionCleared(CollectionChangeEvent e) {
		this.bag.clear();
	}

	@SuppressWarnings("unchecked")
	public void collectionChanged(CollectionChangeEvent e) {
		this.bag.clear();
		CollectionTools.addAll(this.bag, ((CollectionValueModel<E>) e.getSource()).iterator());
	}


	// ********** standard methods **********

	@Override
	public boolean equals(Object o) {
		return this.bag.equals(o);
	}

	@Override
	public int hashCode() {
		return this.bag.hashCode();
	}

	@Override
	public String toString() {
		return this.bag.toString();
	}

}

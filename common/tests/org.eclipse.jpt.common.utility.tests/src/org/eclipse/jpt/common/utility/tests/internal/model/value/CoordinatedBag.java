/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.model.value;

import java.util.Collection;
import java.util.Iterator;
import org.eclipse.jpt.common.utility.collection.Bag;
import org.eclipse.jpt.common.utility.internal.collection.CollectionTools;
import org.eclipse.jpt.common.utility.internal.collection.HashBag;
import org.eclipse.jpt.common.utility.model.event.CollectionAddEvent;
import org.eclipse.jpt.common.utility.model.event.CollectionChangeEvent;
import org.eclipse.jpt.common.utility.model.event.CollectionClearEvent;
import org.eclipse.jpt.common.utility.model.event.CollectionRemoveEvent;
import org.eclipse.jpt.common.utility.model.listener.CollectionChangeListener;
import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;

/**
 * Helper class that keeps an internal collection in synch with the
 * collection held by a collection value model.
 */
class CoordinatedBag<E> implements Bag<E>, CollectionChangeListener {

	private Bag<E> bag = new HashBag<E>();

	CoordinatedBag(CollectionValueModel<E> cvm) {
		cvm.addCollectionChangeListener(CollectionValueModel.VALUES, this);
		for (Iterator<E> stream = cvm.iterator(); stream.hasNext(); ) {
			this.add(stream.next());
		}
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

	public int uniqueCount() {
		return this.bag.uniqueCount();
	}

	public Iterator<Bag.Entry<E>> entries() {
		return this.bag.entries();
	}

	// ********** CollectionChangeListener implementation **********

	@SuppressWarnings("unchecked")
	public void itemsAdded(CollectionAddEvent event) {
		for (E item : (Iterable<E>) event.getItems()) {
			this.bag.add(item);
		}
	}

	@SuppressWarnings("unchecked")
	public void itemsRemoved(CollectionRemoveEvent event) {
		for (E item : (Iterable<E>) event.getItems()) {
			this.bag.remove(item);
		}
	}

	public void collectionCleared(CollectionClearEvent event) {
		this.bag.clear();
	}

	@SuppressWarnings("unchecked")
	public void collectionChanged(CollectionChangeEvent event) {
		this.bag.clear();
		CollectionTools.addAll(this.bag, ((CollectionValueModel<E>) event.getSource()).iterator());
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

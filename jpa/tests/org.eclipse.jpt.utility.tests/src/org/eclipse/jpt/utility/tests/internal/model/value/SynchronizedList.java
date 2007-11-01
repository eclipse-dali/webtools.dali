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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.model.event.ListChangeEvent;
import org.eclipse.jpt.utility.internal.model.listener.ListChangeListener;
import org.eclipse.jpt.utility.internal.model.value.ListValueModel;
import org.eclipse.jpt.utility.internal.model.value.ValueModel;

/**
 * Helper class that keeps an internal list in synch with the
 * list held by a list value model.
 */
public class SynchronizedList<E> implements List<E>, ListChangeListener, ListDataListener {
	private List<E> synchList = new ArrayList<E>();

	public SynchronizedList(ListValueModel listValueModel) {
		listValueModel.addListChangeListener(ValueModel.VALUE, this);
		for (Iterator<E> stream = (ListIterator<E>) listValueModel.getValue(); stream.hasNext(); ) {
			this.add(stream.next());
		}
	}

	public SynchronizedList(ListModel listModel) {
		listModel.addListDataListener(this);
		for (int i = 0; i < listModel.getSize(); i++) {
			this.add(i, (E) listModel.getElementAt(i));
		}
	}


	// ********** List implementation **********

	public void add(int index, E element) {
		this.synchList.add(index, element);
	}

	public boolean add(E o) {
		return this.synchList.add(o);
	}

	public boolean addAll(Collection<? extends E> c) {
		return this.synchList.addAll(c);
	}

	public boolean addAll(int index, Collection<? extends E> c) {
		return this.synchList.addAll(index, c);
	}

	public void clear() {
		this.synchList.clear();
	}

	public boolean contains(Object o) {
		return this.synchList.contains(o);
	}

	public boolean containsAll(Collection<?> c) {
		return this.synchList.containsAll(c);
	}

	public E get(int index) {
		return this.synchList.get(index);
	}

	public int indexOf(Object o) {
		return this.synchList.indexOf(o);
	}

	public boolean isEmpty() {
		return this.synchList.isEmpty();
	}

	public Iterator<E> iterator() {
		return this.synchList.iterator();
	}

	public int lastIndexOf(Object o) {
		return this.synchList.lastIndexOf(o);
	}

	public ListIterator<E> listIterator() {
		return this.synchList.listIterator();
	}

	public ListIterator<E> listIterator(int index) {
		return this.synchList.listIterator(index);
	}

	public E remove(int index) {
		return this.synchList.remove(index);
	}

	public boolean remove(Object o) {
		return this.synchList.remove(o);
	}

	public boolean removeAll(Collection<?> c) {
		return this.synchList.removeAll(c);
	}

	public boolean retainAll(Collection<?> c) {
		return this.synchList.retainAll(c);
	}

	public E set(int index, E element) {
		return this.synchList.set(index, element);
	}

	public int size() {
		return this.synchList.size();
	}

	public List<E> subList(int fromIndex, int toIndex) {
		return this.synchList.subList(fromIndex, toIndex);
	}

	public Object[] toArray() {
		return this.synchList.toArray();
	}

	public <T> T[] toArray(T[] a) {
		return this.synchList.toArray(a);
	}


	// ********** ListChangeListener implementation **********

	public void itemsAdded(ListChangeEvent e) {
		int i = e.index();
		for (Iterator<E> stream = (Iterator<E>) e.items(); stream.hasNext(); ) {
			this.synchList.add(i++, stream.next());
		}
	}

	public void itemsRemoved(ListChangeEvent e) {
		int i = e.index();
		for (Iterator<E> stream = (Iterator<E>) e.items(); stream.hasNext(); ) {
			stream.next();
			this.synchList.remove(i);
		}
	}

	public void itemsReplaced(ListChangeEvent e) {
		int i = e.index();
		for (ListIterator<E> stream = (ListIterator<E>) e.items(); stream.hasNext(); ) {
			this.synchList.set(i++, stream.next());
		}
	}

	public void itemsMoved(ListChangeEvent e) {
		CollectionTools.move(this.synchList, e.targetIndex(), e.sourceIndex(), e.itemsSize());
	}

	public void listCleared(ListChangeEvent e) {
		this.synchList.clear();
	}

	public void listChanged(ListChangeEvent e) {
		this.synchList.clear();
		CollectionTools.addAll(this.synchList, (Iterator) ((ListValueModel) e.getSource()).getValue());
	}


	// ********** ListDataListener implementation **********

	public void contentsChanged(ListDataEvent e) {
		this.synchList.clear();
		ListModel lm = (ListModel) e.getSource();
		int size = lm.getSize();
		for (int i = 0; i < size; i++) {
			this.synchList.add(i, (E) lm.getElementAt(i));
		}
	}

	public void intervalAdded(ListDataEvent e) {
		ListModel lm = (ListModel) e.getSource();
		int start = Math.min(e.getIndex0(), e.getIndex1());
		int end = Math.max(e.getIndex0(), e.getIndex1());
		for (int i = start; i <= end; i++) {
			this.synchList.add(i, (E) lm.getElementAt(i));
		}
	}

	public void intervalRemoved(ListDataEvent e) {
		int start = Math.min(e.getIndex0(), e.getIndex1());
		int end = Math.max(e.getIndex0(), e.getIndex1());
		int length = end - start + 1;
		for (int i = 1; i <= length; i++) {
			this.synchList.remove(start);
		}
	}


	// ********** standard methods **********

    @Override
	public boolean equals(Object o) {
		return this.synchList.equals(o);
	}

    @Override
	public int hashCode() {
		return this.synchList.hashCode();
	}

    @Override
	public String toString() {
		return this.synchList.toString();
	}

}

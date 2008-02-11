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

/**
 * Helper class that keeps an internal list in synch with the
 * list held by a list value model.
 */
public class CoordinatedList<E> implements List<E>, ListChangeListener, ListDataListener {
	private List<E> list = new ArrayList<E>();

	public CoordinatedList(ListValueModel<E> listValueModel) {
		listValueModel.addListChangeListener(ListValueModel.LIST_VALUES, this);
		for (Iterator<E> stream = listValueModel.iterator(); stream.hasNext(); ) {
			this.add(stream.next());
		}
	}

	public CoordinatedList(ListModel listModel) {
		listModel.addListDataListener(this);
		for (int i = 0; i < listModel.getSize(); i++) {
			this.add(i, this.getElementAt(listModel, i));
		}
	}


	// ********** List implementation **********

	public void add(int index, E element) {
		this.list.add(index, element);
	}

	public boolean add(E o) {
		return this.list.add(o);
	}

	public boolean addAll(Collection<? extends E> c) {
		return this.list.addAll(c);
	}

	public boolean addAll(int index, Collection<? extends E> c) {
		return this.list.addAll(index, c);
	}

	public void clear() {
		this.list.clear();
	}

	public boolean contains(Object o) {
		return this.list.contains(o);
	}

	public boolean containsAll(Collection<?> c) {
		return this.list.containsAll(c);
	}

	public E get(int index) {
		return this.list.get(index);
	}

	public int indexOf(Object o) {
		return this.list.indexOf(o);
	}

	public boolean isEmpty() {
		return this.list.isEmpty();
	}

	public Iterator<E> iterator() {
		return this.list.iterator();
	}

	public int lastIndexOf(Object o) {
		return this.list.lastIndexOf(o);
	}

	public ListIterator<E> listIterator() {
		return this.list.listIterator();
	}

	public ListIterator<E> listIterator(int index) {
		return this.list.listIterator(index);
	}

	public E remove(int index) {
		return this.list.remove(index);
	}

	public boolean remove(Object o) {
		return this.list.remove(o);
	}

	public boolean removeAll(Collection<?> c) {
		return this.list.removeAll(c);
	}

	public boolean retainAll(Collection<?> c) {
		return this.list.retainAll(c);
	}

	public E set(int index, E element) {
		return this.list.set(index, element);
	}

	public int size() {
		return this.list.size();
	}

	public List<E> subList(int fromIndex, int toIndex) {
		return this.list.subList(fromIndex, toIndex);
	}

	public Object[] toArray() {
		return this.list.toArray();
	}

	public <T> T[] toArray(T[] a) {
		return this.list.toArray(a);
	}


	// ********** ListChangeListener implementation **********

	public void itemsAdded(ListChangeEvent e) {
		int i = e.index();
		for (Iterator<E> stream = this.items(e); stream.hasNext(); ) {
			this.list.add(i++, stream.next());
		}
	}

	public void itemsRemoved(ListChangeEvent e) {
		int i = e.index();
		for (Iterator<E> stream = this.items(e); stream.hasNext(); ) {
			stream.next();
			this.list.remove(i);
		}
	}

	public void itemsReplaced(ListChangeEvent e) {
		int i = e.index();
		for (Iterator<E> stream = this.items(e); stream.hasNext(); ) {
			this.list.set(i++, stream.next());
		}
	}

	public void itemsMoved(ListChangeEvent e) {
		CollectionTools.move(this.list, e.targetIndex(), e.sourceIndex(), e.moveLength());
	}

	public void listCleared(ListChangeEvent e) {
		this.list.clear();
	}

	public void listChanged(ListChangeEvent e) {
		this.list.clear();
		CollectionTools.addAll(this.list, this.getSource(e).iterator());
	}


	// ********** ListDataListener implementation **********

	public void contentsChanged(ListDataEvent e) {
		this.list.clear();
		ListModel lm = (ListModel) e.getSource();
		int size = lm.getSize();
		for (int i = 0; i < size; i++) {
			this.list.add(i, this.getElementAt(lm, i));
		}
	}

	public void intervalAdded(ListDataEvent e) {
		ListModel lm = (ListModel) e.getSource();
		int start = Math.min(e.getIndex0(), e.getIndex1());
		int end = Math.max(e.getIndex0(), e.getIndex1());
		for (int i = start; i <= end; i++) {
			this.list.add(i, this.getElementAt(lm, i));
		}
	}

	public void intervalRemoved(ListDataEvent e) {
		int start = Math.min(e.getIndex0(), e.getIndex1());
		int end = Math.max(e.getIndex0(), e.getIndex1());
		int length = end - start + 1;
		for (int i = 1; i <= length; i++) {
			this.list.remove(start);
		}
	}


	// ********** standard methods **********

    @Override
	public boolean equals(Object o) {
		return this.list.equals(o);
	}

    @Override
	public int hashCode() {
		return this.list.hashCode();
	}

    @Override
	public String toString() {
		return this.list.toString();
	}


	// ********** internal methods **********

	/**
	 * minimize the scope of the suppressed warnings.=
	 */
	@SuppressWarnings("unchecked")
	private E getElementAt(ListModel listModel, int index) {
		return (E) listModel.getElementAt(index);
	}

	/**
	 * minimize the scope of the suppressed warnings.=
	 */
	@SuppressWarnings("unchecked")
	private Iterator<E> items(ListChangeEvent event) {
		return (Iterator<E>) event.items();
	}

	/**
	 * minimize the scope of the suppressed warnings.=
	 */
	@SuppressWarnings("unchecked")
	private ListValueModel<E> getSource(ListChangeEvent event) {
		return (ListValueModel<E>) event.getSource();
	}

}

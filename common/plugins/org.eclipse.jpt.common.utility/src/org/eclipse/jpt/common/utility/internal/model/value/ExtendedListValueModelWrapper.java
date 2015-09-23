/*******************************************************************************
 * Copyright (c) 2007, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.model.value;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.eclipse.jpt.common.utility.internal.StringBuilderTools;
import org.eclipse.jpt.common.utility.internal.collection.CollectionTools;
import org.eclipse.jpt.common.utility.internal.collection.ListTools;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.common.utility.internal.iterator.ReadOnlyListIterator;
import org.eclipse.jpt.common.utility.model.event.ListAddEvent;
import org.eclipse.jpt.common.utility.model.event.ListChangeEvent;
import org.eclipse.jpt.common.utility.model.event.ListClearEvent;
import org.eclipse.jpt.common.utility.model.event.ListMoveEvent;
import org.eclipse.jpt.common.utility.model.event.ListRemoveEvent;
import org.eclipse.jpt.common.utility.model.event.ListReplaceEvent;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;

/**
 * This wrapper extends a {@link ListValueModel}
 * (or {@link org.eclipse.jpt.common.utility.model.value.CollectionValueModel CollectionValueModel})
 * with fixed collections of items on either end.
 * <p>
 * <strong>NB:</strong> Be careful using or wrapping this list value model, since the
 * "extended" items may be unexpected by the client code or wrapper.
 */
public class ExtendedListValueModelWrapper<E>
	extends ListValueModelWrapper<E>
	implements ListValueModel<E>
{
	/** the items "prepended" to the wrapped list */
	protected List<E> prefix;

	/** the items "appended" to the wrapped list */
	protected List<E> suffix;


	// ********** lots o' constructors **********

	/**
	 * Extend the specified list with a prefix and suffix.
	 */
	public ExtendedListValueModelWrapper(List<? extends E> prefix, ListValueModel<? extends E> listHolder, List<? extends E> suffix) {
		super(listHolder);
		this.prefix = new ArrayList<E>(prefix);
		this.suffix = new ArrayList<E>(suffix);
	}

	/**
	 * Extend the specified list with a prefix and suffix.
	 */
	public ExtendedListValueModelWrapper(E prefix, ListValueModel<? extends E> listHolder, E suffix) {
		super(listHolder);
		this.prefix = Collections.singletonList(prefix);
		this.suffix = Collections.singletonList(suffix);
	}

	/**
	 * Extend the specified list with a prefix.
	 */
	public ExtendedListValueModelWrapper(List<? extends E> prefix, ListValueModel<? extends E> listHolder) {
		super(listHolder);
		this.prefix = new ArrayList<E>(prefix);
		this.suffix = Collections.emptyList();
	}

	/**
	 * Extend the specified list with a prefix.
	 */
	public ExtendedListValueModelWrapper(E prefix, ListValueModel<? extends E> listHolder) {
		super(listHolder);
		this.prefix = Collections.singletonList(prefix);
		this.suffix = Collections.emptyList();
	}

	/**
	 * Extend the specified list with a suffix.
	 */
	public ExtendedListValueModelWrapper(ListValueModel<? extends E> listHolder, List<? extends E> suffix) {
		super(listHolder);
		this.prefix = Collections.emptyList();
		this.suffix = new ArrayList<E>(suffix);
	}

	/**
	 * Extend the specified list with a suffix.
	 */
	public ExtendedListValueModelWrapper(ListValueModel<? extends E> listHolder, E suffix) {
		super(listHolder);
		this.prefix = Collections.emptyList();
		this.suffix = Collections.singletonList(suffix);
	}

	/**
	 * Extend the specified list with a prefix containing a single null item.
	 */
	public ExtendedListValueModelWrapper(ListValueModel<? extends E> listHolder) {
		super(listHolder);
		this.prefix = Collections.singletonList(null);
		this.suffix = Collections.emptyList();
	}


	// ********** ListValueModel implementation **********

	public Iterator<E> iterator() {
		return this.listIterator();
	}

	public ListIterator<E> listIterator() {
		return new ReadOnlyListIterator<E>(this.listIterator_());
	}

	@SuppressWarnings("unchecked")
	protected ListIterator<E> listIterator_() {
		return IteratorTools.concatenateReadOnly(
			this.prefix.listIterator(),
			this.listModel.listIterator(),
			this.suffix.listIterator()
		);
	}

	public E get(int index) {
		int prefixSize = this.prefix.size();
		if (index < prefixSize) {
			return this.prefix.get(index);
		} else if (index >= prefixSize + this.listModel.size()) {
			return this.suffix.get(index - (prefixSize + this.listModel.size()));
		} else {
			return this.listModel.get(index - prefixSize);
		}
	}

	public int size() {
		return this.prefix.size() + this.listModel.size() + this.suffix.size();
	}

	public Object[] toArray() {
		ArrayList<E> list = new ArrayList<E>(this.size());
		list.addAll(this.prefix);
		CollectionTools.addAll(list, this.listModel.iterator());
		list.addAll(this.suffix);
		return list.toArray();
	}


	// ********** ListValueModelWrapper implementation/overrides **********

	@Override
	protected void itemsAdded(ListAddEvent event) {
		this.fireItemsAdded(event.clone(this, LIST_VALUES, this.prefix.size()));
	}

	@Override
	protected void itemsRemoved(ListRemoveEvent event) {
		this.fireItemsRemoved(event.clone(this, LIST_VALUES, this.prefix.size()));
	}

	@Override
	protected void itemsReplaced(ListReplaceEvent event) {
		this.fireItemsReplaced(event.clone(this, LIST_VALUES, this.prefix.size()));
	}

	@Override
	protected void itemsMoved(ListMoveEvent event) {
		this.fireItemsMoved(event.clone(this, LIST_VALUES, this.prefix.size()));
	}

	@Override
	protected void listCleared(ListClearEvent event) {
		this.fireListChanged(LIST_VALUES, this.buildList());  // not "cleared"
	}

	@Override
	protected void listChanged(ListChangeEvent event) {
		this.fireListChanged(LIST_VALUES, this.buildList());
	}

	@Override
	public void toString(StringBuilder sb) {
		StringBuilderTools.append(sb, this);
	}


	// ********** miscellaneous **********

	public void setPrefix(List<E> prefix) {
		this.prefix = prefix;
		this.fireListChanged(LIST_VALUES, this.buildList());
	}

	public void setSuffix(List<E> suffix) {
		this.suffix = suffix;
		this.fireListChanged(LIST_VALUES, this.buildList());
	}

	private List<E> buildList() {
		return ListTools.arrayList(this.listIterator_());
	}

}

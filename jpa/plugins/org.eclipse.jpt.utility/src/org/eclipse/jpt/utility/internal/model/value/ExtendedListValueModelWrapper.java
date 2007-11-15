/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal.model.value;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.eclipse.jpt.utility.internal.iterators.CompositeListIterator;
import org.eclipse.jpt.utility.internal.iterators.ReadOnlyListIterator;
import org.eclipse.jpt.utility.internal.model.event.ListChangeEvent;

/**
 * This wrapper extends a ListValueModel (or CollectionValueModel)
 * with fixed collections of items on either end.
 * 
 * NB: Be careful using or wrapping this list value model, since the
 * "extended" items may be unexpected by the client code or wrapper.
 */
public class ExtendedListValueModelWrapper
	extends ListValueModelWrapper
{
	/** the items "prepended" to the wrapped list */
	protected final List prefix;

	/** the items "appended" to the wrapped list */
	protected final List suffix;


	// ********** lots o' constructors **********

	/**
	 * Extend the specified list with a prefix and suffix.
	 */
	public ExtendedListValueModelWrapper(List prefix, ListValueModel listHolder, List suffix) {
		super(listHolder);
		this.prefix = new ArrayList(prefix);
		this.suffix = new ArrayList(suffix);
	}

	/**
	 * Extend the specified list with a prefix and suffix.
	 */
	public ExtendedListValueModelWrapper(Object prefix, ListValueModel listHolder, Object suffix) {
		this(Collections.singletonList(prefix), listHolder, Collections.singletonList(suffix));
	}

	/**
	 * Extend the specified list with a prefix.
	 */
	public ExtendedListValueModelWrapper(List prefix, ListValueModel listHolder) {
		this(prefix, listHolder, Collections.EMPTY_LIST);
	}

	/**
	 * Extend the specified list with a prefix.
	 */
	public ExtendedListValueModelWrapper(Object prefix, ListValueModel listHolder) {
		this(Collections.singletonList(prefix), listHolder, Collections.EMPTY_LIST);
	}

	/**
	 * Extend the specified list with a suffix.
	 */
	public ExtendedListValueModelWrapper(ListValueModel listHolder, List suffix) {
		this(Collections.EMPTY_LIST, listHolder, suffix);
	}

	/**
	 * Extend the specified list with a suffix.
	 */
	public ExtendedListValueModelWrapper(ListValueModel listHolder, Object suffix) {
		this(Collections.EMPTY_LIST, listHolder, Collections.singletonList(suffix));
	}

	/**
	 * Extend the specified list with a prefix containing a single null item.
	 */
	public ExtendedListValueModelWrapper(ListValueModel listHolder) {
		this(Collections.singletonList(null), listHolder, Collections.EMPTY_LIST);
	}


	// ********** ListValueModel implementation **********

	public Iterator iterator() {
		return this.listIterator();
	}

	public ListIterator listIterator() {
		// try to prevent backdoor modification of the lists
		return new ReadOnlyListIterator(
			new CompositeListIterator(
				this.prefix.listIterator(),
				this.listHolder.listIterator(),
				this.suffix.listIterator()
			)
		);
	}

	public Object get(int index) {
		int prefixSize = this.prefix.size();
		if (index < prefixSize) {
			return this.prefix.get(index);
		} else if (index >= prefixSize + this.listHolder.size()) {
			return this.suffix.get(index - (prefixSize + this.listHolder.size()));
		} else {
			return this.listHolder.get(index - prefixSize);
		}
	}

	public int size() {
		return this.prefix.size() + this.listHolder.size() + this.suffix.size();
	}


	// ********** ListValueModelWrapper implementation **********

	@Override
	protected void itemsAdded(ListChangeEvent e) {
		this.fireItemsAdded(e.cloneWithSource(this, LIST_VALUES, this.prefix.size()));
	}

	@Override
	protected void itemsRemoved(ListChangeEvent e) {
		this.fireItemsRemoved(e.cloneWithSource(this, LIST_VALUES, this.prefix.size()));
	}

	@Override
	protected void itemsReplaced(ListChangeEvent e) {
		this.fireItemsReplaced(e.cloneWithSource(this, LIST_VALUES, this.prefix.size()));
	}

	@Override
	protected void itemsMoved(ListChangeEvent e) {
		this.fireItemsMoved(e.cloneWithSource(this, LIST_VALUES, this.prefix.size()));
	}

	@Override
	protected void listCleared(ListChangeEvent e) {
		this.fireListCleared(LIST_VALUES);
	}

	@Override
	protected void listChanged(ListChangeEvent e) {
		this.fireListChanged(LIST_VALUES);
	}


	// ********** AbstractModel implementation **********

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.prefix);
		sb.append(" ");
		super.toString(sb);
		sb.append(" ");
		sb.append(this.suffix);
	}

}

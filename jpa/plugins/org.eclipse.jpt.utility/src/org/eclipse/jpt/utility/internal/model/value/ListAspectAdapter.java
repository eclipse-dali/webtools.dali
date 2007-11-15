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

import java.util.Iterator;
import java.util.ListIterator;

import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.EmptyListIterator;
import org.eclipse.jpt.utility.internal.model.Model;
import org.eclipse.jpt.utility.internal.model.event.ListChangeEvent;
import org.eclipse.jpt.utility.internal.model.listener.ChangeListener;
import org.eclipse.jpt.utility.internal.model.listener.ListChangeListener;

/**
 * This extension of AspectAdapter provides ListChange support.
 * 
 * The typical subclass will override the following methods:
 * #getValueFromSubject()
 *     at the very minimum, override this method to return a list iterator
 *     on the subject's list aspect; it does not need to be overridden if
 *     #value() is overridden and its behavior changed
 * #getItem(int)
 *     override this method to improve performance
 * #sizeFromSubject()
 *     override this method to improve performance; it does not need to be overridden if
 *     #size() is overridden and its behavior changed
 * #addItem(int, Object) and #removeItem(int)
 *     override these methods if the client code needs to *change* the contents of
 *     the subject's list aspect; oftentimes, though, the client code
 *     (e.g. UI) will need only to *get* the value
 * #addItems(int, List) and #removeItems(int, int)
 *     override these methods to improve performance, if necessary
 * #value()
 *     override this method only if returning an empty list iterator when the
 *     subject is null is unacceptable
 * #size()
 *     override this method only if returning a zero when the
 *     subject is null is unacceptable
 */
public abstract class ListAspectAdapter
	extends AspectAdapter
	implements ListValueModel
{
	/**
	 * The name of the subject's list that we use for the value.
	 */
	protected final String listName;

	/** A listener that listens to the subject's list aspect. */
	protected final ListChangeListener listChangeListener;


	// ********** constructors **********

	/**
	 * Construct a ListAspectAdapter for the specified subject
	 * and list.
	 */
	protected ListAspectAdapter(String listName, Model subject) {
		this(new ReadOnlyPropertyValueModel(subject), listName);
	}

	/**
	 * Construct a ListAspectAdapter for an "unchanging" list in
	 * the specified subject. This is useful for a list aspect that does not
	 * change for a particular subject; but the subject will change, resulting in
	 * a new list.
	 */
	protected ListAspectAdapter(ValueModel subjectHolder) {
		this(subjectHolder, null);
	}

	/**
	 * Construct a ListAspectAdapter for the specified subject holder
	 * and list.
	 */
	protected ListAspectAdapter(ValueModel subjectHolder, String listName) {
		super(subjectHolder);
		this.listName = listName;
		this.listChangeListener = this.buildListChangeListener();
	}


	// ********** initialization **********

	/**
	 * The subject's list aspect has changed, notify the listeners.
	 */
	protected ListChangeListener buildListChangeListener() {
		// transform the subject's list change events into VALUE list change events
		return new ListChangeListener() {
			public void itemsAdded(ListChangeEvent e) {
				ListAspectAdapter.this.itemsAdded(e);
			}
			public void itemsRemoved(ListChangeEvent e) {
				ListAspectAdapter.this.itemsRemoved(e);
			}
			public void itemsReplaced(ListChangeEvent e) {
				ListAspectAdapter.this.itemsReplaced(e);
			}
			public void itemsMoved(ListChangeEvent e) {
				ListAspectAdapter.this.itemsMoved(e);
			}
			public void listCleared(ListChangeEvent e) {
				ListAspectAdapter.this.listCleared(e);
			}
			public void listChanged(ListChangeEvent e) {
				ListAspectAdapter.this.listChanged(e);
			}
			@Override
			public String toString() {
				return "list change listener: " + ListAspectAdapter.this.listName;
			}
		};
	}


	// ********** ListValueModel implementation **********

	/**
	 * Return the elements of the subject's list aspect.
	 */
	public Iterator iterator() {
		return this.listIterator();
	}

	/**
	 * Return the elements of the subject's list aspect.
	 */
	public ListIterator listIterator() {
		if (this.subject == null) {
			return EmptyListIterator.instance();
		}
		return this.getValueFromSubject();
	}

	/**
	 * Return the value of the subject's list aspect.
	 * This should be a *list iterator* on the list.
	 * At this point we can be sure that the subject is not null.
	 * @see #value()
	 */
	protected ListIterator getValueFromSubject() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Return the item at the specified index of the subject's list aspect.
	 */
	public Object get(int index) {
		return CollectionTools.get((ListIterator) this.value(), index);
	}

	/**
	 * Return the size of the subject's list aspect.
	 */
	public int size() {
		return this.subject == null ? 0 : this.sizeFromSubject();
	}

	/**
	 * Return the size of the subject's list aspect.
	 * At this point we can be sure that the subject is not null.
	 * @see #size()
	 */
	protected int sizeFromSubject() {
		return CollectionTools.size((ListIterator) this.value());
	}


	// ********** AspectAdapter implementation **********

	@Override
	protected Object value() {
		return this.iterator();
	}

	@Override
	protected Class<? extends ChangeListener> listenerClass() {
		return ListChangeListener.class;
	}

	@Override
	protected String listenerAspectName() {
		return LIST_VALUES;
	}

	@Override
	protected boolean hasListeners() {
		return this.hasAnyListChangeListeners(LIST_VALUES);
	}

	@Override
	protected void fireAspectChange(Object oldValue, Object newValue) {
		this.fireListChanged(LIST_VALUES);
	}

	@Override
	protected void engageNonNullSubject() {
		if (this.listName != null) {
			((Model) this.subject).addListChangeListener(this.listName, this.listChangeListener);
		}
	}

	@Override
	protected void disengageNonNullSubject() {
		if (this.listName != null) {
			((Model) this.subject).removeListChangeListener(this.listName, this.listChangeListener);
		}
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.listName);
	}


	// ********** behavior **********

	protected void itemsAdded(ListChangeEvent e) {
		this.fireItemsAdded(e.cloneWithSource(ListAspectAdapter.this, LIST_VALUES));
	}

	protected void itemsRemoved(ListChangeEvent e) {
		this.fireItemsRemoved(e.cloneWithSource(ListAspectAdapter.this, LIST_VALUES));
	}

	protected void itemsReplaced(ListChangeEvent e) {
		this.fireItemsReplaced(e.cloneWithSource(ListAspectAdapter.this, LIST_VALUES));
	}

	protected void itemsMoved(ListChangeEvent e) {
		this.fireItemsMoved(e.cloneWithSource(ListAspectAdapter.this, LIST_VALUES));
	}

	protected void listCleared(ListChangeEvent e) {
		this.fireListCleared(LIST_VALUES);
	}

	protected void listChanged(ListChangeEvent e) {
		this.fireListChanged(LIST_VALUES);
	}

}

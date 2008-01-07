/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
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
 * #listIterator_()
 *     at the very minimum, override this method to return a list iterator
 *     on the subject's list aspect; it does not need to be overridden if
 *     #listIterator() is overridden and its behavior changed
 * #get(int)
 *     override this method to improve performance
 * #size_()
 *     override this method to improve performance; it does not need to be overridden if
 *     #size() is overridden and its behavior changed
 * #listIterator()
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

	private static final Object[] EMPTY_ARRAY = new Object[0];


	// ********** constructors **********

	/**
	 * Construct a ListAspectAdapter for the specified subject
	 * and list.
	 */
	protected ListAspectAdapter(String listName, Model subject) {
		this(new StaticPropertyValueModel(subject), listName);
	}

	/**
	 * Construct a ListAspectAdapter for an "unchanging" list in
	 * the specified subject. This is useful for a list aspect that does not
	 * change for a particular subject; but the subject will change, resulting in
	 * a new list.
	 */
	protected ListAspectAdapter(PropertyValueModel subjectHolder) {
		this(subjectHolder, null);
	}

	/**
	 * Construct a ListAspectAdapter for the specified subject holder
	 * and list.
	 */
	protected ListAspectAdapter(PropertyValueModel subjectHolder, String listName) {
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
		return (this.subject == null) ? EmptyListIterator.instance() : this.listIterator_();
	}

	/**
	 * Return the elements of the subject's list aspect.
	 * At this point we can be sure that the subject is not null.
	 * @see #listIterator()
	 */
	protected ListIterator listIterator_() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Return the element at the specified index of the subject's list aspect.
	 */
	public Object get(int index) {
		return CollectionTools.get(this.listIterator(), index);
	}

	/**
	 * Return the size of the subject's list aspect.
	 */
	public int size() {
		return this.subject == null ? 0 : this.size_();
	}

	/**
	 * Return the size of the subject's list aspect.
	 * At this point we can be sure that the subject is not null.
	 * @see #size()
	 */
	protected int size_() {
		return CollectionTools.size(this.listIterator());
	}

	/**
	 * Return an array manifestation of the subject's list aspect.
	 */
	public Object[] toArray() {
		return this.subject == null ? EMPTY_ARRAY : this.toArray_();
	}

	/**
	 * Return an array manifestation of the subject's list aspect.
	 * At this point we can be sure that the subject is not null.
	 * @see #toArray()
	 */
	protected Object[] toArray_() {
		return CollectionTools.array(this.listIterator(), this.size());
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
	protected void engageSubject_() {
		if (this.listName != null) {
			((Model) this.subject).addListChangeListener(this.listName, this.listChangeListener);
		}
	}

	@Override
	protected void disengageSubject_() {
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
		this.fireItemsAdded(e.cloneWithSource(this, LIST_VALUES));
	}

	protected void itemsRemoved(ListChangeEvent e) {
		this.fireItemsRemoved(e.cloneWithSource(this, LIST_VALUES));
	}

	protected void itemsReplaced(ListChangeEvent e) {
		this.fireItemsReplaced(e.cloneWithSource(this, LIST_VALUES));
	}

	protected void itemsMoved(ListChangeEvent e) {
		this.fireItemsMoved(e.cloneWithSource(this, LIST_VALUES));
	}

	protected void listCleared(ListChangeEvent e) {
		this.fireListCleared(LIST_VALUES);  // nothing from original event to forward
	}

	protected void listChanged(ListChangeEvent e) {
		this.fireListChanged(LIST_VALUES);  // nothing from original event to forward
	}

}

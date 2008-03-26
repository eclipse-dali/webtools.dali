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

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;

import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.EmptyListIterator;
import org.eclipse.jpt.utility.model.Model;
import org.eclipse.jpt.utility.model.event.ListChangeEvent;
import org.eclipse.jpt.utility.model.listener.ChangeListener;
import org.eclipse.jpt.utility.model.listener.ListChangeListener;
import org.eclipse.jpt.utility.model.value.ListValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;

/**
 * This extension of AspectAdapter provides ListChange support.
 * This allows us to convert a set of one or more collections into
 * a single collection, LIST_VALUES.
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
public abstract class ListAspectAdapter<S extends Model, E>
	extends AspectAdapter<S>
	implements ListValueModel<E>
{
	/**
	 * The name of the subject's lists that we use for the value.
	 */
	protected final String[] listNames;
		protected static final String[] EMPTY_LIST_NAMES = new String[0];

	/** A listener that listens to the subject's list aspect. */
	protected final ListChangeListener listChangeListener;

	private static final Object[] EMPTY_ARRAY = new Object[0];


	// ********** constructors **********

	/**
	 * Construct a ListAspectAdapter for the specified subject
	 * and list.
	 */
	protected ListAspectAdapter(String listName, S subject) {
		this(new String[] {listName}, subject);
	}

	/**
	 * Construct a ListAspectAdapter for the specified subject
	 * and lists.
	 */
	protected ListAspectAdapter(String[] listNames, S subject) {
		this(new StaticPropertyValueModel<S>(subject), listNames);
	}

	/**
	 * Construct a ListAspectAdapter for the specified subject holder
	 * and lists.
	 */
	protected ListAspectAdapter(PropertyValueModel<? extends S> subjectHolder, String... listNames) {
		super(subjectHolder);
		this.listNames = listNames;
		this.listChangeListener = this.buildListChangeListener();
	}

	/**
	 * Construct a ListAspectAdapter for the specified subject holder
	 * and lists.
	 */
	protected ListAspectAdapter(PropertyValueModel<? extends S> subjectHolder, Collection<String> listNames) {
		this(subjectHolder, listNames.toArray(new String[listNames.size()]));
	}

	/**
	 * Construct a ListAspectAdapter for an "unchanging" list in
	 * the specified subject. This is useful for a list aspect that does not
	 * change for a particular subject; but the subject will change, resulting in
	 * a new list.
	 */
	protected ListAspectAdapter(PropertyValueModel<? extends S> subjectHolder) {
		this(subjectHolder, EMPTY_LIST_NAMES);
	}


	// ********** initialization **********

	/**
	 * The subject's list aspect has changed, notify the listeners.
	 */
	protected ListChangeListener buildListChangeListener() {
		// transform the subject's list change events into VALUE list change events
		return new ListChangeListener() {
			public void itemsAdded(ListChangeEvent event) {
				ListAspectAdapter.this.itemsAdded(event);
			}
			public void itemsRemoved(ListChangeEvent event) {
				ListAspectAdapter.this.itemsRemoved(event);
			}
			public void itemsReplaced(ListChangeEvent event) {
				ListAspectAdapter.this.itemsReplaced(event);
			}
			public void itemsMoved(ListChangeEvent event) {
				ListAspectAdapter.this.itemsMoved(event);
			}
			public void listCleared(ListChangeEvent event) {
				ListAspectAdapter.this.listCleared(event);
			}
			public void listChanged(ListChangeEvent event) {
				ListAspectAdapter.this.listChanged(event);
			}
			@Override
			public String toString() {
				return "list change listener: " + Arrays.asList(ListAspectAdapter.this.listNames);
			}
		};
	}


	// ********** ListValueModel implementation **********

	/**
	 * Return the elements of the subject's list aspect.
	 */
	public Iterator<E> iterator() {
		return this.listIterator();
	}

	/**
	 * Return the elements of the subject's list aspect.
	 */
	public ListIterator<E> listIterator() {
		return (this.subject == null) ? EmptyListIterator.<E>instance() : this.listIterator_();
	}

	/**
	 * Return the elements of the subject's list aspect.
	 * At this point we can be sure that the subject is not null.
	 * @see #listIterator()
	 */
	protected ListIterator<E> listIterator_() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Return the element at the specified index of the subject's list aspect.
	 */
	public E get(int index) {
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
	protected Object getValue() {
		return this.iterator();
	}

	@Override
	protected Class<? extends ChangeListener> getListenerClass() {
		return ListChangeListener.class;
	}

	@Override
	protected String getListenerAspectName() {
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
    	for (String listName : this.listNames) {
			((Model) this.subject).addListChangeListener(listName, this.listChangeListener);
		}
	}

	@Override
	protected void disengageSubject_() {
    	for (String listName : this.listNames) {
			((Model) this.subject).removeListChangeListener(listName, this.listChangeListener);
		}
	}

	@Override
	public void toString(StringBuilder sb) {
		for (int i = 0; i < this.listNames.length; i++) {
			if (i != 0) {
				sb.append(", ");
			}
			sb.append(this.listNames[i]);
		}
	}


	// ********** behavior **********

	protected void itemsAdded(ListChangeEvent event) {
		this.fireItemsAdded(event.cloneWithSource(this, LIST_VALUES));
	}

	protected void itemsRemoved(ListChangeEvent event) {
		this.fireItemsRemoved(event.cloneWithSource(this, LIST_VALUES));
	}

	protected void itemsReplaced(ListChangeEvent event) {
		this.fireItemsReplaced(event.cloneWithSource(this, LIST_VALUES));
	}

	protected void itemsMoved(ListChangeEvent event) {
		this.fireItemsMoved(event.cloneWithSource(this, LIST_VALUES));
	}

	protected void listCleared(ListChangeEvent event) {
		this.fireListCleared(LIST_VALUES);  // nothing from original event to forward
	}

	protected void listChanged(ListChangeEvent event) {
		this.fireListChanged(LIST_VALUES);  // nothing from original event to forward
	}

}

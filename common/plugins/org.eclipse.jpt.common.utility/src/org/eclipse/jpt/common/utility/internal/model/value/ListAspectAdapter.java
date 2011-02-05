/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.model.value;

import java.util.Arrays;
import java.util.Collection;

import org.eclipse.jpt.common.utility.model.Model;
import org.eclipse.jpt.common.utility.model.event.ListAddEvent;
import org.eclipse.jpt.common.utility.model.event.ListChangeEvent;
import org.eclipse.jpt.common.utility.model.event.ListClearEvent;
import org.eclipse.jpt.common.utility.model.event.ListMoveEvent;
import org.eclipse.jpt.common.utility.model.event.ListRemoveEvent;
import org.eclipse.jpt.common.utility.model.event.ListReplaceEvent;
import org.eclipse.jpt.common.utility.model.listener.ListChangeListener;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;

/**
 * This extension of {@link AspectListValueModelAdapter} provides
 * basic list change support.
 * This converts a set of one or more lists into
 * a single {@link #LIST_VALUES} list.
 * <p>
 * The typical subclass will override the following methods (see the descriptions
 * in {@link AspectListValueModelAdapter}):<ul>
 * <li>{@link #listIterator_()}
 * <li>{@link #get(int)}
 * <li>{@link #size_()}
 * <li>{@link #toArray_()}
 * <li>{@link #listIterator()}
 * <li>{@link #size()}
 * <li>{@link #toArray()}
 * </ul>
 */
public abstract class ListAspectAdapter<S extends Model, E>
	extends AspectListValueModelAdapter<S, E>
{
	/**
	 * The name of the subject's lists that we use for the value.
	 */
	protected final String[] listNames;
		protected static final String[] EMPTY_LIST_NAMES = new String[0];

	/** A listener that listens to the subject's list aspects. */
	protected final ListChangeListener listChangeListener;


	// ********** constructors **********

	/**
	 * Construct a list aspect adapter for the specified subject
	 * and list.
	 */
	protected ListAspectAdapter(String listName, S subject) {
		this(new String[] {listName}, subject);
	}

	/**
	 * Construct a list aspect adapter for the specified subject
	 * and lists.
	 */
	protected ListAspectAdapter(String[] listNames, S subject) {
		this(new StaticPropertyValueModel<S>(subject), listNames);
	}

	/**
	 * Construct a list aspect adapter for the specified subject holder
	 * and lists.
	 */
	protected ListAspectAdapter(PropertyValueModel<? extends S> subjectHolder, String... listNames) {
		super(subjectHolder);
		this.listNames = listNames;
		this.listChangeListener = this.buildListChangeListener();
	}

	/**
	 * Construct a list aspect adapter for the specified subject holder
	 * and lists.
	 */
	protected ListAspectAdapter(PropertyValueModel<? extends S> subjectHolder, Collection<String> listNames) {
		this(subjectHolder, listNames.toArray(new String[listNames.size()]));
	}

	/**
	 * Construct a list aspect adapter for an "unchanging" list in
	 * the specified subject. This is useful for a list aspect that does not
	 * change for a particular subject; but the subject will change, resulting in
	 * a new list.
	 */
	protected ListAspectAdapter(PropertyValueModel<? extends S> subjectHolder) {
		this(subjectHolder, EMPTY_LIST_NAMES);
	}


	// ********** initialization **********

	protected ListChangeListener buildListChangeListener() {
		// transform the subject's list change events into VALUE list change events
		return new ListChangeListener() {
			public void itemsAdded(ListAddEvent event) {
				ListAspectAdapter.this.itemsAdded(event);
			}
			public void itemsRemoved(ListRemoveEvent event) {
				ListAspectAdapter.this.itemsRemoved(event);
			}
			public void itemsReplaced(ListReplaceEvent event) {
				ListAspectAdapter.this.itemsReplaced(event);
			}
			public void itemsMoved(ListMoveEvent event) {
				ListAspectAdapter.this.itemsMoved(event);
			}
			public void listCleared(ListClearEvent event) {
				ListAspectAdapter.this.listCleared(event);
			}
			public void listChanged(ListChangeEvent event) {
				ListAspectAdapter.this.listChanged(event);
			}
			@Override
			public String toString() {
				return "list change listener: " + Arrays.asList(ListAspectAdapter.this.listNames); //$NON-NLS-1$
			}
		};
	}


	// ********** AspectAdapter implementation **********

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


	// ********** behavior **********

	protected void itemsAdded(ListAddEvent event) {
		this.fireItemsAdded(event.clone(this, LIST_VALUES));
	}

	protected void itemsRemoved(ListRemoveEvent event) {
		this.fireItemsRemoved(event.clone(this, LIST_VALUES));
	}

	protected void itemsReplaced(ListReplaceEvent event) {
		this.fireItemsReplaced(event.clone(this, LIST_VALUES));
	}

	protected void itemsMoved(ListMoveEvent event) {
		this.fireItemsMoved(event.clone(this, LIST_VALUES));
	}

	protected void listCleared(ListClearEvent event) {
		this.fireListCleared(event.clone(this, LIST_VALUES));
	}

	protected void listChanged(ListChangeEvent event) {
		this.fireListChanged(event.clone(this, LIST_VALUES));
	}

}

/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.model.value;

import java.util.Collection;
import org.eclipse.jpt.common.utility.model.Model;
import org.eclipse.jpt.common.utility.model.event.ListAddEvent;
import org.eclipse.jpt.common.utility.model.event.ListChangeEvent;
import org.eclipse.jpt.common.utility.model.event.ListClearEvent;
import org.eclipse.jpt.common.utility.model.event.ListMoveEvent;
import org.eclipse.jpt.common.utility.model.event.ListRemoveEvent;
import org.eclipse.jpt.common.utility.model.event.ListReplaceEvent;
import org.eclipse.jpt.common.utility.model.listener.ListChangeAdapter;
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
 * 
 * @param <S> the type of the adapter's subject
 * @param <E> the type of the adapter's list's elements
 */
public abstract class ListAspectAdapter<S extends Model, E>
	extends AspectListValueModelAdapter<S, E>
{
	/**
	 * The name of the subject's lists that we use for the value.
	 */
	protected final String[] aspectNames;
		protected static final String[] EMPTY_ASPECT_NAMES = new String[0];

	/** A listener that listens to the subject's list aspects. */
	protected final ListChangeListener aspectChangeListener;


	// ********** constructors **********

	/**
	 * Construct a list aspect adapter for the specified subject
	 * and list aspect.
	 */
	protected ListAspectAdapter(String aspectName, S subject) {
		this(new String[] {aspectName}, subject);
	}

	/**
	 * Construct a list aspect adapter for the specified subject
	 * and list aspects.
	 */
	protected ListAspectAdapter(String[] aspectNames, S subject) {
		this(new StaticPropertyValueModel<S>(subject), aspectNames);
	}

	/**
	 * Construct a list aspect adapter for the specified subject holder
	 * and lists.
	 */
	protected ListAspectAdapter(PropertyValueModel<? extends S> subjectModel, String... aspectNames) {
		super(subjectModel);
		if (aspectNames == null) {
			throw new NullPointerException();
		}
		this.aspectNames = aspectNames;
		this.aspectChangeListener = this.buildAspectChangeListener();
	}

	/**
	 * Construct a list aspect adapter for the specified subject holder
	 * and lists.
	 */
	protected ListAspectAdapter(PropertyValueModel<? extends S> subjectModel, Collection<String> aspectNames) {
		this(subjectModel, aspectNames.toArray(new String[aspectNames.size()]));
	}

	/**
	 * Construct a list aspect adapter for an "unchanging" list in
	 * the specified subject. This is useful for a list aspect that does not
	 * change for a particular subject; but the subject will change, resulting in
	 * a new list.
	 */
	protected ListAspectAdapter(PropertyValueModel<? extends S> subjectModel) {
		this(subjectModel, EMPTY_ASPECT_NAMES);
	}


	// ********** initialization **********

	protected ListChangeListener buildAspectChangeListener() {
		return new AspectChangeListener();
	}

	/**
	 * Transform the subject's list change events into {@link #LIST_VALUES}
	 * list change events
	 */
	protected class AspectChangeListener
		extends ListChangeAdapter
	{
		@Override
		public void itemsAdded(ListAddEvent event) {
			ListAspectAdapter.this.aspectItemsAdded(event);
		}
		@Override
		public void itemsRemoved(ListRemoveEvent event) {
			ListAspectAdapter.this.aspectItemsRemoved(event);
		}
		@Override
		public void itemsReplaced(ListReplaceEvent event) {
			ListAspectAdapter.this.aspectItemsReplaced(event);
		}
		@Override
		public void itemsMoved(ListMoveEvent event) {
			ListAspectAdapter.this.aspectItemsMoved(event);
		}
		@Override
		public void listCleared(ListClearEvent event) {
			ListAspectAdapter.this.aspectListCleared(event);
		}
		@Override
		public void listChanged(ListChangeEvent event) {
			ListAspectAdapter.this.aspectListChanged(event);
		}
	}


	// ********** AspectAdapter implementation **********

	@Override
	protected void engageSubject_() {
    	for (String listName : this.aspectNames) {
			this.subject.addListChangeListener(listName, this.aspectChangeListener);
		}
	}

	@Override
	protected void disengageSubject_() {
    	for (String listName : this.aspectNames) {
			this.subject.removeListChangeListener(listName, this.aspectChangeListener);
		}
	}


	// ********** behavior **********

	protected void aspectItemsAdded(ListAddEvent event) {
		this.fireItemsAdded(event.clone(this, LIST_VALUES));
	}

	protected void aspectItemsRemoved(ListRemoveEvent event) {
		this.fireItemsRemoved(event.clone(this, LIST_VALUES));
	}

	protected void aspectItemsReplaced(ListReplaceEvent event) {
		this.fireItemsReplaced(event.clone(this, LIST_VALUES));
	}

	protected void aspectItemsMoved(ListMoveEvent event) {
		this.fireItemsMoved(event.clone(this, LIST_VALUES));
	}

	protected void aspectListCleared(ListClearEvent event) {
		this.fireListCleared(event.clone(this, LIST_VALUES));
	}

	protected void aspectListChanged(ListChangeEvent event) {
		this.fireListChanged(event.clone(this, LIST_VALUES));
	}
}

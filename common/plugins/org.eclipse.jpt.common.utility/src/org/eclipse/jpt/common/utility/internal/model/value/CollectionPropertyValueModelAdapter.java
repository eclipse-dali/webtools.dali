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

import org.eclipse.jpt.common.utility.model.event.CollectionAddEvent;
import org.eclipse.jpt.common.utility.model.event.CollectionChangeEvent;
import org.eclipse.jpt.common.utility.model.event.CollectionClearEvent;
import org.eclipse.jpt.common.utility.model.event.CollectionRemoveEvent;
import org.eclipse.jpt.common.utility.model.listener.CollectionChangeAdapter;
import org.eclipse.jpt.common.utility.model.listener.CollectionChangeListener;
import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;

/**
 * This abstract class provides the infrastructure needed to wrap
 * a collection value model, "lazily" listen to it, and convert
 * its change notifications into property value model change
 * notifications.
 * <p>
 * Subclasses must override:<ul>
 * <li>{@link #buildValue()}<p>
 *     to return the current property value, as derived from the
 *     current collection value
 * </ul>
 * Subclasses might want to override the following methods
 * to improve performance (by not recalculating the value, if possible):<ul>
 * <li>{@link #itemsAdded(CollectionAddEvent event)}
 * <li>{@link #itemsAdded(Iterable)}
 * <li>{@link #itemsRemoved(CollectionRemoveEvent event)}
 * <li>{@link #itemsRemoved(Iterable)}
 * <li>{@link #collectionCleared(CollectionClearEvent event)}
 * <li>{@link #collectionChanged(CollectionChangeEvent event)}
 * </ul>
 * 
 * @param <V> the type of the model's value
 * @param <E> the type of the wrapped collection value model's elements
 */
public abstract class CollectionPropertyValueModelAdapter<V, E>
	extends AbstractPropertyValueModelAdapter<V>
{
	/** The wrapped collection value model. */
	protected final CollectionValueModel<? extends E> collectionModel;

	/** A listener that allows us to sync with changes to the wrapped collection model. */
	protected final CollectionChangeListener collectionListener;


	// ********** constructor/initialization **********

	/**
	 * Construct a property value model with the specified wrapped
	 * collection value model.
	 */
	protected CollectionPropertyValueModelAdapter(CollectionValueModel<? extends E> collectionModel) {
		super();
		if (collectionModel == null) {
			throw new NullPointerException();
		}
		this.collectionModel = collectionModel;
		this.collectionListener = this.buildCollectionListener();
	}

	protected CollectionChangeListener buildCollectionListener() {
		return new CollectionListener();
	}

	/**
	 * Straightforward callbacks to the adapter.
	 */
	protected class CollectionListener
		extends CollectionChangeAdapter
	{
		@Override
		public void itemsAdded(CollectionAddEvent event) {
			CollectionPropertyValueModelAdapter.this.itemsAdded(event);
		}		
		@Override
		public void itemsRemoved(CollectionRemoveEvent event) {
			CollectionPropertyValueModelAdapter.this.itemsRemoved(event);
		}
		@Override
		public void collectionCleared(CollectionClearEvent event) {
			CollectionPropertyValueModelAdapter.this.collectionCleared(event);
		}
		@Override
		public void collectionChanged(CollectionChangeEvent event) {
			CollectionPropertyValueModelAdapter.this.collectionChanged(event);
		}
	}


	// ********** listener **********

	/**
	 * Start listening to the collection holder.
	 */
	@Override
	protected void engageModel_() {
		this.collectionModel.addCollectionChangeListener(CollectionValueModel.VALUES, this.collectionListener);
	}

	/**
	 * Stop listening to the collection holder.
	 */
	@Override
	protected void disengageModel_() {
		this.collectionModel.removeCollectionChangeListener(CollectionValueModel.VALUES, this.collectionListener);
	}

	
	// ********** collection change support **********

	/**
	 * Items were added to the wrapped collection holder;
	 * propagate the change notification appropriately.
	 */
	protected void itemsAdded(CollectionAddEvent event) {
		@SuppressWarnings("unchecked")
		Iterable<E> items = (Iterable<E>) event.getItems();
		this.itemsAdded(items);
	}

	/**
	 * The specified items were added to the wrapped collection holder;
	 * propagate the change notification appropriately.
	 */
	protected void itemsAdded(@SuppressWarnings("unused") Iterable<E> items) {
		// by default, simply recalculate the value and fire an event
		this.propertyChanged();
	}

	/**
	 * Items were removed from the wrapped collection holder;
	 * propagate the change notification appropriately.
	 */
	protected void itemsRemoved(CollectionRemoveEvent event) {
		@SuppressWarnings("unchecked")
		Iterable<E> items = (Iterable<E>) event.getItems();
		this.itemsRemoved(items);
	}

	/**
	 * The specified items were removed from the wrapped collection holder;
	 * propagate the change notification appropriately.
	 */
	protected void itemsRemoved(@SuppressWarnings("unused") Iterable<E> items) {
		// by default, simply recalculate the value and fire an event if necessary
		this.propertyChanged();
	}

	/**
	 * The wrapped collection holder was cleared;
	 * propagate the change notification appropriately.
	 */
	protected void collectionCleared(@SuppressWarnings("unused") CollectionClearEvent event) {
		// by default, simply recalculate the value and fire an event if necessary
		this.propertyChanged();
	}

	/**
	 * The value of the wrapped collection holder has changed;
	 * propagate the change notification appropriately.
	 */
	protected void collectionChanged(@SuppressWarnings("unused") CollectionChangeEvent event) {
		// by default, simply recalculate the value and fire an event if necessary
		this.propertyChanged();
	}
}

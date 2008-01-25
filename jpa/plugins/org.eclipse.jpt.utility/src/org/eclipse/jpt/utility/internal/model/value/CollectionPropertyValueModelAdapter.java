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

import org.eclipse.jpt.utility.internal.model.event.CollectionChangeEvent;
import org.eclipse.jpt.utility.internal.model.listener.CollectionChangeListener;

/**
 * This abstract class provides the infrastructure needed to wrap
 * a collection value model, "lazily" listen to it, and convert
 * its change notifications into property value model change
 * notifications.
 * 
 * Subclasses must override:
 * - #buildValue()
 *     to return the current property value, as derived from the
 *     current collection value
 * 
 * Subclasses might want to override:
 * - #itemsAdded(CollectionChangeEvent e)
 * - #itemsRemoved(CollectionChangeEvent e)
 * - #collectionCleared(CollectionChangeEvent e)
 * - #collectionChanged(CollectionChangeEvent e)
 *     to improve performance (by not recalculating the value, if possible)
 */
public abstract class CollectionPropertyValueModelAdapter<T>
	extends AspectPropertyValueModelAdapter<T>
{
	/** The wrapped collection value model. */
	protected final CollectionValueModel<?> collectionHolder;

	/** A listener that allows us to synch with changes to the wrapped collection holder. */
	protected final CollectionChangeListener collectionChangeListener;


	// ********** constructor/initialization **********

	/**
	 * Construct a property value model with the specified wrapped
	 * collection value model.
	 */
	protected CollectionPropertyValueModelAdapter(CollectionValueModel<?> collectionHolder) {
		super();
		this.collectionHolder = collectionHolder;
		this.collectionChangeListener = this.buildCollectionChangeListener();
	}

	protected CollectionChangeListener buildCollectionChangeListener() {
		return new CollectionChangeListener() {
			public void itemsAdded(CollectionChangeEvent e) {
				CollectionPropertyValueModelAdapter.this.itemsAdded(e);
			}		
			public void itemsRemoved(CollectionChangeEvent e) {
				CollectionPropertyValueModelAdapter.this.itemsRemoved(e);
			}
			public void collectionCleared(CollectionChangeEvent e) {
				CollectionPropertyValueModelAdapter.this.collectionCleared(e);
			}
			public void collectionChanged(CollectionChangeEvent e) {
				CollectionPropertyValueModelAdapter.this.collectionChanged(e);
			}
			@Override
			public String toString() {
				return "collection change listener";
			}
		};
	}


	// ********** behavior **********

	/**
	 * Start listening to the collection holder.
	 */
	@Override
	protected void engageModel_() {
		this.collectionHolder.addCollectionChangeListener(CollectionValueModel.VALUES, this.collectionChangeListener);
	}

	/**
	 * Stop listening to the collection holder.
	 */
	@Override
	protected void disengageModel_() {
		this.collectionHolder.removeCollectionChangeListener(CollectionValueModel.VALUES, this.collectionChangeListener);
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.collectionHolder);
	}

	
	// ********** collection change support **********

	/**
	 * Items were added to the wrapped collection holder;
	 * propagate the change notification appropriately.
	 */
	protected void itemsAdded(CollectionChangeEvent e) {
		// by default, simply recalculate the value and fire an event
		this.propertyChanged();
	}

	/**
	 * Items were removed from the wrapped collection holder;
	 * propagate the change notification appropriately.
	 */
	protected void itemsRemoved(CollectionChangeEvent e) {
		// by default, simply recalculate the value and fire an event
		this.propertyChanged();
	}

	/**
	 * The wrapped collection holder was cleared;
	 * propagate the change notification appropriately.
	 */
	protected void collectionCleared(CollectionChangeEvent e) {
		// by default, simply recalculate the value and fire an event
		this.propertyChanged();
	}

	/**
	 * The value of the wrapped collection holder has changed;
	 * propagate the change notification appropriately.
	 */
	protected void collectionChanged(CollectionChangeEvent e) {
		// by default, simply recalculate the value and fire an event
		this.propertyChanged();
	}

}

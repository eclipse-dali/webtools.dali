/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal.model.value;

import org.eclipse.jpt.utility.model.event.CollectionAddEvent;
import org.eclipse.jpt.utility.model.event.CollectionChangeEvent;
import org.eclipse.jpt.utility.model.event.CollectionClearEvent;
import org.eclipse.jpt.utility.model.event.CollectionRemoveEvent;
import org.eclipse.jpt.utility.model.listener.CollectionChangeListener;
import org.eclipse.jpt.utility.model.value.CollectionValueModel;

/**
 * This abstract class provides the infrastructure needed to wrap
 * another collection value model, "lazily" listen to it, and propagate
 * its change notifications. Subclasses must implement the appropriate
 * {@link CollectionValueModel}.
 */
public abstract class CollectionValueModelWrapper<E>
	extends AbstractCollectionValueModel
{
	/** The wrapped collection value model. */
	protected final CollectionValueModel<? extends E> collectionHolder;

	/** A listener that allows us to synch with changes to the wrapped collection holder. */
	protected final CollectionChangeListener collectionChangeListener;


	// ********** constructors **********

	/**
	 * Construct a collection value model with the specified wrapped
	 * collection value model.
	 */
	protected CollectionValueModelWrapper(CollectionValueModel<? extends E> collectionHolder) {
		super();
		this.collectionHolder = collectionHolder;
		this.collectionChangeListener = this.buildCollectionChangeListener();
	}


	// ********** initialization **********

	protected CollectionChangeListener buildCollectionChangeListener() {
		return new CollectionChangeListener() {
			public void itemsAdded(CollectionAddEvent event) {
				CollectionValueModelWrapper.this.itemsAdded(event);
			}
			public void itemsRemoved(CollectionRemoveEvent event) {
				CollectionValueModelWrapper.this.itemsRemoved(event);
			}
			public void collectionCleared(CollectionClearEvent event) {
				CollectionValueModelWrapper.this.collectionCleared(event);
			}
			public void collectionChanged(CollectionChangeEvent event) {
				CollectionValueModelWrapper.this.collectionChanged(event);
			}
			@Override
			public String toString() {
				return "collection change listener"; //$NON-NLS-1$
			}
		};
	}


	// ********** AbstractCollectionValueModel implementation **********

	/**
	 * Start listening to the collection holder.
	 */
	@Override
	protected void engageModel() {
		this.collectionHolder.addCollectionChangeListener(CollectionValueModel.VALUES, this.collectionChangeListener);
	}

	/**
	 * Stop listening to the collection holder.
	 */
	@Override
	protected void disengageModel() {
		this.collectionHolder.removeCollectionChangeListener(CollectionValueModel.VALUES, this.collectionChangeListener);
	}


	// ********** helper methods **********

	// minimize scope of suppressed warnings
	@SuppressWarnings("unchecked")
	protected Iterable<E> getItems(CollectionAddEvent event) {
		return (Iterable<E>) event.getItems();
	}

	// minimize scope of suppressed warnings
	@SuppressWarnings("unchecked")
	protected Iterable<E> getItems(CollectionRemoveEvent event) {
		return (Iterable<E>) event.getItems();
	}


	// ********** collection change support **********

	/**
	 * Items were added to the wrapped collection holder;
	 * propagate the change notification appropriately.
	 */
	protected abstract void itemsAdded(CollectionAddEvent event);

	/**
	 * Items were removed from the wrapped collection holder;
	 * propagate the change notification appropriately.
	 */
	protected abstract void itemsRemoved(CollectionRemoveEvent event);

	/**
	 * The wrapped collection holder was cleared;
	 * propagate the change notification appropriately.
	 */
	protected abstract void collectionCleared(CollectionClearEvent event);

	/**
	 * The value of the wrapped collection holder has changed;
	 * propagate the change notification appropriately.
	 */
	protected abstract void collectionChanged(CollectionChangeEvent event);

}

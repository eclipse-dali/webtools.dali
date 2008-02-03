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

import org.eclipse.jpt.utility.internal.model.AbstractModel;
import org.eclipse.jpt.utility.internal.model.ChangeSupport;
import org.eclipse.jpt.utility.internal.model.SingleAspectChangeSupport;
import org.eclipse.jpt.utility.internal.model.event.CollectionChangeEvent;
import org.eclipse.jpt.utility.internal.model.listener.CollectionChangeListener;

/**
 * This abstract class provides the infrastructure needed to wrap
 * another collection value model, "lazily" listen to it, and propagate
 * its change notifications.
 */
public abstract class CollectionValueModelWrapper<E>
	extends AbstractModel
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

	@Override
	protected ChangeSupport buildChangeSupport() {
		return new SingleAspectChangeSupport(this, CollectionChangeListener.class, CollectionValueModel.VALUES);
	}

	protected CollectionChangeListener buildCollectionChangeListener() {
		return new CollectionChangeListener() {
			public void itemsAdded(CollectionChangeEvent event) {
				CollectionValueModelWrapper.this.itemsAdded(event);
			}		
			public void itemsRemoved(CollectionChangeEvent event) {
				CollectionValueModelWrapper.this.itemsRemoved(event);
			}
			public void collectionCleared(CollectionChangeEvent event) {
				CollectionValueModelWrapper.this.collectionCleared(event);
			}
			public void collectionChanged(CollectionChangeEvent event) {
				CollectionValueModelWrapper.this.collectionChanged(event);
			}
			@Override
			public String toString() {
				return "collection change listener";
			}
		};
	}


	// ********** extend change support **********

	/**
	 * Extend to start listening to the nested model if necessary.
	 */
	@Override
	public synchronized void addCollectionChangeListener(CollectionChangeListener listener) {
		if (this.hasNoCollectionChangeListeners(CollectionValueModel.VALUES)) {
			this.engageModel();
		}
		super.addCollectionChangeListener(listener);
	}
	
	/**
	 * Extend to start listening to the nested model if necessary.
	 */
	@Override
	public synchronized void addCollectionChangeListener(String collectionName, CollectionChangeListener listener) {
		if (collectionName == CollectionValueModel.VALUES && this.hasNoCollectionChangeListeners(CollectionValueModel.VALUES)) {
			this.engageModel();
		}
		super.addCollectionChangeListener(collectionName, listener);
	}
	
	/**
	 * Extend to stop listening to the nested model if necessary.
	 */
	@Override
	public synchronized void removeCollectionChangeListener(CollectionChangeListener listener) {
		super.removeCollectionChangeListener(listener);
		if (this.hasNoCollectionChangeListeners(CollectionValueModel.VALUES)) {
			this.disengageModel();
		}
	}
	
	/**
	 * Extend to stop listening to the nested model if necessary.
	 */
	@Override
	public synchronized void removeCollectionChangeListener(String collectionName, CollectionChangeListener listener) {
		super.removeCollectionChangeListener(collectionName, listener);
		if (collectionName == CollectionValueModel.VALUES && this.hasNoCollectionChangeListeners(CollectionValueModel.VALUES)) {
			this.disengageModel();
		}
	}


	// ********** behavior **********

	/**
	 * Start listening to the collection holder.
	 */
	protected void engageModel() {
		this.collectionHolder.addCollectionChangeListener(CollectionValueModel.VALUES, this.collectionChangeListener);
	}

	/**
	 * Stop listening to the collection holder.
	 */
	protected void disengageModel() {
		this.collectionHolder.removeCollectionChangeListener(CollectionValueModel.VALUES, this.collectionChangeListener);
	}

	// minimize suppressed warnings
	@SuppressWarnings("unchecked")
	protected Iterator<E> items(CollectionChangeEvent event) {
		return (Iterator<E>) event.items();
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
	protected abstract void itemsAdded(CollectionChangeEvent event);

	/**
	 * Items were removed from the wrapped collection holder;
	 * propagate the change notification appropriately.
	 */
	protected abstract void itemsRemoved(CollectionChangeEvent event);

	/**
	 * The wrapped collection holder was cleared;
	 * propagate the change notification appropriately.
	 */
	protected abstract void collectionCleared(CollectionChangeEvent event);

	/**
	 * The value of the wrapped collection holder has changed;
	 * propagate the change notification appropriately.
	 */
	protected abstract void collectionChanged(CollectionChangeEvent event);

}

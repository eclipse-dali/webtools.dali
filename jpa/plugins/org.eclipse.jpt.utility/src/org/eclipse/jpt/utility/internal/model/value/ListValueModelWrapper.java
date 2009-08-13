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

import org.eclipse.jpt.utility.model.event.ListAddEvent;
import org.eclipse.jpt.utility.model.event.ListChangeEvent;
import org.eclipse.jpt.utility.model.event.ListClearEvent;
import org.eclipse.jpt.utility.model.event.ListMoveEvent;
import org.eclipse.jpt.utility.model.event.ListRemoveEvent;
import org.eclipse.jpt.utility.model.event.ListReplaceEvent;
import org.eclipse.jpt.utility.model.listener.ListChangeListener;
import org.eclipse.jpt.utility.model.value.ListValueModel;

/**
 * This abstract class provides the infrastructure needed to wrap
 * another list value model, "lazily" listen to it, and propagate
 * its change notifications. Subclasses must implement the appropriate
 * {@link ListValueModel}.
 */
public abstract class ListValueModelWrapper<E>
	extends AbstractListValueModel
{
	/** The wrapped list value model. */
	protected final ListValueModel<? extends E> listHolder;

	/** A listener that allows us to synch with changes to the wrapped list holder. */
	protected final ListChangeListener listChangeListener;


	// ********** constructors **********

	/**
	 * Construct a list value model with the specified wrapped
	 * list value model.
	 */
	protected ListValueModelWrapper(ListValueModel<? extends E> listHolder) {
		super();
		if (listHolder == null) {
			throw new NullPointerException();
		}
		this.listHolder = listHolder;
		this.listChangeListener = this.buildListChangeListener();
	}


	// ********** initialization **********

	protected ListChangeListener buildListChangeListener() {
		return new ListChangeListener() {
			public void itemsAdded(ListAddEvent event) {
				ListValueModelWrapper.this.itemsAdded(event);
			}
			public void itemsRemoved(ListRemoveEvent event) {
				ListValueModelWrapper.this.itemsRemoved(event);
			}
			public void itemsReplaced(ListReplaceEvent event) {
				ListValueModelWrapper.this.itemsReplaced(event);
			}
			public void itemsMoved(ListMoveEvent event) {
				ListValueModelWrapper.this.itemsMoved(event);
			}
			public void listCleared(ListClearEvent event) {
				ListValueModelWrapper.this.listCleared(event);
			}
			public void listChanged(ListChangeEvent event) {
				ListValueModelWrapper.this.listChanged(event);
			}
			@Override
			public String toString() {
				return "list change listener"; //$NON-NLS-1$
			}
		};
	}


	// ********** behavior **********

	/**
	 * Start listening to the list holder.
	 */
	@Override
	protected void engageModel() {
		this.listHolder.addListChangeListener(ListValueModel.LIST_VALUES, this.listChangeListener);
	}

	/**
	 * Stop listening to the list holder.
	 */
	@Override
	protected void disengageModel() {
		this.listHolder.removeListChangeListener(ListValueModel.LIST_VALUES, this.listChangeListener);
	}

	// minimized scope of suppressed warnings
	@SuppressWarnings("unchecked")
	protected Iterable<E> getItems(ListAddEvent event) {
		return (Iterable<E>) event.getItems();
	}

	// minimized scope of suppressed warnings
	@SuppressWarnings("unchecked")
	protected Iterable<E> getItems(ListRemoveEvent event) {
		return (Iterable<E>) event.getItems();
	}

	// minimized scope of suppressed warnings
	@SuppressWarnings("unchecked")
	protected Iterable<E> getNewItems(ListReplaceEvent event) {
		return (Iterable<E>) event.getNewItems();
	}

	// minimized scope of suppressed warnings
	@SuppressWarnings("unchecked")
	protected Iterable<E> getOldItems(ListReplaceEvent event) {
		return (Iterable<E>) event.getOldItems();
	}


	// ********** list change support **********

	/**
	 * Items were added to the wrapped list holder;
	 * propagate the change notification appropriately.
	 */
	protected abstract void itemsAdded(ListAddEvent event);

	/**
	 * Items were removed from the wrapped list holder;
	 * propagate the change notification appropriately.
	 */
	protected abstract void itemsRemoved(ListRemoveEvent event);

	/**
	 * Items were replaced in the wrapped list holder;
	 * propagate the change notification appropriately.
	 */
	protected abstract void itemsReplaced(ListReplaceEvent event);

	/**
	 * Items were moved in the wrapped list holder;
	 * propagate the change notification appropriately.
	 */
	protected abstract void itemsMoved(ListMoveEvent event);

	/**
	 * The wrapped list holder was cleared;
	 * propagate the change notification appropriately.
	 */
	protected abstract void listCleared(ListClearEvent event);

	/**
	 * The value of the wrapped list holder has changed;
	 * propagate the change notification appropriately.
	 */
	protected abstract void listChanged(ListChangeEvent event);

}

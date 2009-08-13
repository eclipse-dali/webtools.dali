/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
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
 * a list value model, "lazily" listen to it, and convert
 * its change notifications into property value model change
 * notifications.
 * <p>
 * Subclasses must override:<ul>
 * <li>{@link #buildValue()}<p>
 *     to return the current property value, as derived from the
 *     current list value
 * </ul>
 * Subclasses might want to override the following methods
 * to improve performance (by not recalculating the value, if possible):<ul>
 * <li>{@link #itemsAdded(ListAddEvent event)}
 * <li>{@link #itemsRemoved(ListRemoveEvent event)}
 * <li>{@link #itemsReplaced(ListReplaceEvent event)}
 * <li>{@link #itemsMoved(ListMoveEvent event)}
 * <li>{@link #listCleared(ListClearEvent event)}
 * <li>{@link #listChanged(ListChangeEvent event)}
 * </ul>
 */
public abstract class ListPropertyValueModelAdapter<T>
	extends AbstractPropertyValueModelAdapter<T>
{
	/** The wrapped list value model. */
	protected final ListValueModel<?> listHolder;

	/** A listener that allows us to synch with changes to the wrapped list holder. */
	protected final ListChangeListener listChangeListener;


	// ********** constructor/initialization **********

	/**
	 * Construct a property value model with the specified wrapped
	 * list value model.
	 */
	protected ListPropertyValueModelAdapter(ListValueModel<?> listHolder) {
		super();
		this.listHolder = listHolder;
		this.listChangeListener = this.buildListChangeListener();
	}

	protected ListChangeListener buildListChangeListener() {
		return new ListChangeListener() {
			public void itemsAdded(ListAddEvent event) {
				ListPropertyValueModelAdapter.this.itemsAdded(event);
			}		
			public void itemsRemoved(ListRemoveEvent event) {
				ListPropertyValueModelAdapter.this.itemsRemoved(event);
			}
			public void itemsReplaced(ListReplaceEvent event) {
				ListPropertyValueModelAdapter.this.itemsReplaced(event);
			}
			public void itemsMoved(ListMoveEvent event) {
				ListPropertyValueModelAdapter.this.itemsMoved(event);
			}
			public void listCleared(ListClearEvent event) {
				ListPropertyValueModelAdapter.this.listCleared(event);
			}
			public void listChanged(ListChangeEvent event) {
				ListPropertyValueModelAdapter.this.listChanged(event);
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
	protected void engageModel_() {
		this.listHolder.addListChangeListener(ListValueModel.LIST_VALUES, this.listChangeListener);
	}

	/**
	 * Stop listening to the list holder.
	 */
	@Override
	protected void disengageModel_() {
		this.listHolder.removeListChangeListener(ListValueModel.LIST_VALUES, this.listChangeListener);
	}

	
	// ********** collection change support **********

	/**
	 * Items were added to the wrapped list holder;
	 * propagate the change notification appropriately.
	 */
	protected void itemsAdded(@SuppressWarnings("unused") ListAddEvent event) {
		// by default, simply recalculate the value and fire an event
		this.propertyChanged();
	}

	/**
	 * Items were removed from the wrapped list holder;
	 * propagate the change notification appropriately.
	 */
	protected void itemsRemoved(@SuppressWarnings("unused") ListRemoveEvent event) {
		// by default, simply recalculate the value and fire an event
		this.propertyChanged();
	}

	/**
	 * Items were replaced in the wrapped list holder;
	 * propagate the change notification appropriately.
	 */
	protected void itemsReplaced(@SuppressWarnings("unused") ListReplaceEvent event) {
		// by default, simply recalculate the value and fire an event
		this.propertyChanged();
	}

	/**
	 * Items were moved in the wrapped list holder;
	 * propagate the change notification appropriately.
	 */
	protected void itemsMoved(@SuppressWarnings("unused") ListMoveEvent event) {
		// by default, simply recalculate the value and fire an event
		this.propertyChanged();
	}

	/**
	 * The wrapped list holder was cleared;
	 * propagate the change notification appropriately.
	 */
	protected void listCleared(@SuppressWarnings("unused") ListClearEvent event) {
		// by default, simply recalculate the value and fire an event
		this.propertyChanged();
	}

	/**
	 * The value of the wrapped list holder has changed;
	 * propagate the change notification appropriately.
	 */
	protected void listChanged(@SuppressWarnings("unused") ListChangeEvent event) {
		// by default, simply recalculate the value and fire an event
		this.propertyChanged();
	}

}

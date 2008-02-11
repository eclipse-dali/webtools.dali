/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal.model.value;

import org.eclipse.jpt.utility.internal.model.event.ListChangeEvent;
import org.eclipse.jpt.utility.internal.model.listener.ListChangeListener;

/**
 * This abstract class provides the infrastructure needed to wrap
 * a list value model, "lazily" listen to it, and convert
 * its change notifications into property value model change
 * notifications.
 * 
 * Subclasses must override:
 * - #buildValue()
 *     to return the current property value, as derived from the
 *     current list value
 * 
 * Subclasses might want to override:
 * - #itemsAdded(ListChangeEvent event)
 * - #itemsRemoved(ListChangeEvent event)
 * - #itemsReplaced(ListChangeEvent event)
 * - #itemsMoved(ListChangeEvent event)
 * - #listCleared(ListChangeEvent event)
 * - #listChanged(ListChangeEvent event)
 *     to improve performance (by not recalculating the value, if possible)
 */
public abstract class ListPropertyValueModelAdapter<T>
	extends AspectPropertyValueModelAdapter<T>
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
			public void itemsAdded(ListChangeEvent event) {
				ListPropertyValueModelAdapter.this.itemsAdded(event);
			}		
			public void itemsRemoved(ListChangeEvent event) {
				ListPropertyValueModelAdapter.this.itemsRemoved(event);
			}
			public void itemsReplaced(ListChangeEvent event) {
				ListPropertyValueModelAdapter.this.itemsReplaced(event);
			}
			public void itemsMoved(ListChangeEvent event) {
				ListPropertyValueModelAdapter.this.itemsMoved(event);
			}
			public void listCleared(ListChangeEvent event) {
				ListPropertyValueModelAdapter.this.listCleared(event);
			}
			public void listChanged(ListChangeEvent event) {
				ListPropertyValueModelAdapter.this.listChanged(event);
			}
			@Override
			public String toString() {
				return "list change listener";
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

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.listHolder);
	}

	
	// ********** collection change support **********

	/**
	 * Items were added to the wrapped list holder;
	 * propagate the change notification appropriately.
	 */
	protected void itemsAdded(ListChangeEvent event) {
		// by default, simply recalculate the value and fire an event
		this.propertyChanged();
	}

	/**
	 * Items were removed from the wrapped list holder;
	 * propagate the change notification appropriately.
	 */
	protected void itemsRemoved(ListChangeEvent event) {
		// by default, simply recalculate the value and fire an event
		this.propertyChanged();
	}

	/**
	 * Items were replaced in the wrapped list holder;
	 * propagate the change notification appropriately.
	 */
	protected void itemsReplaced(ListChangeEvent event) {
		// by default, simply recalculate the value and fire an event
		this.propertyChanged();
	}

	/**
	 * Items were moved in the wrapped list holder;
	 * propagate the change notification appropriately.
	 */
	protected void itemsMoved(ListChangeEvent event) {
		// by default, simply recalculate the value and fire an event
		this.propertyChanged();
	}

	/**
	 * The wrapped list holder was cleared;
	 * propagate the change notification appropriately.
	 */
	protected void listCleared(ListChangeEvent event) {
		// by default, simply recalculate the value and fire an event
		this.propertyChanged();
	}

	/**
	 * The value of the wrapped list holder has changed;
	 * propagate the change notification appropriately.
	 */
	protected void listChanged(ListChangeEvent event) {
		// by default, simply recalculate the value and fire an event
		this.propertyChanged();
	}

}

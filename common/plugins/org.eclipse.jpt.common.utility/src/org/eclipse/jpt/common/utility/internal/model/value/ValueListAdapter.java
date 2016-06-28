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

import java.util.Arrays;

import org.eclipse.jpt.common.utility.model.Model;
import org.eclipse.jpt.common.utility.model.event.ListAddEvent;
import org.eclipse.jpt.common.utility.model.event.ListChangeEvent;
import org.eclipse.jpt.common.utility.model.event.ListClearEvent;
import org.eclipse.jpt.common.utility.model.event.ListMoveEvent;
import org.eclipse.jpt.common.utility.model.event.ListRemoveEvent;
import org.eclipse.jpt.common.utility.model.event.ListReplaceEvent;
import org.eclipse.jpt.common.utility.model.listener.ListChangeListener;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;

/**
 * Extend {@link ValueAspectAdapter} to listen to one or more list
 * aspects of the value in the wrapped value model.
 */
public class ValueListAdapter<V extends Model>
	extends ValueAspectAdapter<V>
{
	protected final String listName;

	/** Listener that listens to the value. */
	protected final ListChangeListener valueListListener;


	// ********** constructors **********

	/**
	 * Construct an adapter for the specified value lists.
	 */
	public ValueListAdapter(ModifiablePropertyValueModel<V> valueHolder, String listName) {
		super(valueHolder);
		if (listName == null) {
			throw new NullPointerException();
		}
		this.listName = listName;
		this.valueListListener = this.buildValueListListener();
	}


	// ********** initialization **********

	protected ListChangeListener buildValueListListener() {
		return new ListChangeListener() {
			public void itemsAdded(ListAddEvent event) {
				ValueListAdapter.this.itemsAdded(event);
			}
			public void itemsRemoved(ListRemoveEvent event) {
				ValueListAdapter.this.itemsRemoved(event);
			}
			public void itemsReplaced(ListReplaceEvent event) {
				ValueListAdapter.this.itemsReplaced(event);
			}
			public void itemsMoved(ListMoveEvent event) {
				ValueListAdapter.this.itemsMoved(event);
			}
			public void listCleared(ListClearEvent event) {
				ValueListAdapter.this.listCleared(event);
			}
			public void listChanged(ListChangeEvent event) {
				ValueListAdapter.this.listChanged(event);
			}
			@Override
			public String toString() {
				return "value list listener: " + Arrays.asList(ValueListAdapter.this.listName); //$NON-NLS-1$
			}
		};
	}


	// ********** ValueAspectAdapter implementation **********

	@Override
	protected void engageValue_() {
		this.value.addListChangeListener(this.listName, this.valueListListener);
	}

	@Override
	protected void disengageValue_() {
		this.value.removeListChangeListener(this.listName, this.valueListListener);
	}


	// ********** change events **********

	protected void itemsAdded(@SuppressWarnings("unused") ListAddEvent event) {
		this.valueAspectChanged();
	}

	protected void itemsRemoved(@SuppressWarnings("unused") ListRemoveEvent event) {
		this.valueAspectChanged();
	}

	protected void itemsReplaced(@SuppressWarnings("unused") ListReplaceEvent event) {
		this.valueAspectChanged();
	}

	protected void itemsMoved(@SuppressWarnings("unused") ListMoveEvent event) {
		this.valueAspectChanged();
	}

	protected void listCleared(@SuppressWarnings("unused") ListClearEvent event) {
		this.valueAspectChanged();
	}

	protected void listChanged(@SuppressWarnings("unused") ListChangeEvent event) {
		this.valueAspectChanged();
	}

}

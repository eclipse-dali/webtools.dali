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

import java.util.Arrays;

import org.eclipse.jpt.utility.model.Model;
import org.eclipse.jpt.utility.model.event.ListChangeEvent;
import org.eclipse.jpt.utility.model.listener.ListChangeListener;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;

/**
 * Extend ValueAspectAdapter to listen to one or more list
 * aspects of the value in the wrapped value model.
 */
public class ValueListAdapter<T extends Model>
	extends ValueAspectAdapter<T>
{

	/** The names of the value's lists that we listen to. */
	protected final String[] listNames;

	/** Listener that listens to the value. */
	protected final ListChangeListener valueListListener;


	// ********** constructors **********

	/**
	 * Construct an adapter for the specified value lists.
	 */
	public ValueListAdapter(WritablePropertyValueModel<T> valueHolder, String... listNames) {
		super(valueHolder);
		this.listNames = listNames;
		this.valueListListener = this.buildValueListListener();
	}


	// ********** initialization **********

	/**
	 * All we really care about is the fact that a List aspect has 
	 * changed. Do the same thing no matter which event occurs.
	 */
	protected ListChangeListener buildValueListListener() {
		return new ListChangeListener() {
			public void itemsAdded(ListChangeEvent event) {
				ValueListAdapter.this.valueAspectChanged();
			}
			public void itemsRemoved(ListChangeEvent event) {
				ValueListAdapter.this.valueAspectChanged();
			}
			public void itemsReplaced(ListChangeEvent event) {
				ValueListAdapter.this.valueAspectChanged();
			}
			public void itemsMoved(ListChangeEvent event) {
				ValueListAdapter.this.valueAspectChanged();
			}
			public void listCleared(ListChangeEvent event) {
				ValueListAdapter.this.valueAspectChanged();
			}
			public void listChanged(ListChangeEvent event) {
				ValueListAdapter.this.valueAspectChanged();
			}
			@Override
			public String toString() {
				return "value list listener: " + Arrays.asList(ValueListAdapter.this.listNames);
			}
		};
	}

	@Override
	protected void engageValue_() {
		for (String listName : this.listNames) {
			this.value.addListChangeListener(listName, this.valueListListener);
		}
	}

	@Override
	protected void disengageValue_() {
		for (String listName : this.listNames) {
			this.value.removeListChangeListener(listName, this.valueListListener);
		}
	}

}

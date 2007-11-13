/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal.model.value;

import org.eclipse.jpt.utility.internal.model.Model;
import org.eclipse.jpt.utility.internal.model.event.StateChangeEvent;
import org.eclipse.jpt.utility.internal.model.listener.StateChangeListener;

/**
 * Extend ItemAspectListValueModelAdapter to listen to the
 * "state" of each item in the wrapped list model.
 */
public class ItemStateListValueModelAdapter
	extends ItemAspectListValueModelAdapter
{
	/** Listener that listens to all the items in the list. */
	protected final StateChangeListener itemStateListener;


	// ********** constructors **********

	/**
	 * Construct an adapter for the item state.
	 */
	public ItemStateListValueModelAdapter(ListValueModel listHolder) {
		super(listHolder);
		this.itemStateListener = this.buildItemStateListener();
	}

	/**
	 * Construct an adapter for the item state.
	 */
	public ItemStateListValueModelAdapter(CollectionValueModel collectionHolder) {
		this(new CollectionListValueModelAdapter(collectionHolder));
	}


	// ********** initialization **********

	protected StateChangeListener buildItemStateListener() {
		return new StateChangeListener() {
			public void stateChanged(StateChangeEvent e) {
				ItemStateListValueModelAdapter.this.itemAspectChanged(e);
			}
			@Override
			public String toString() {
				return "item state listener";
			}
		};
	}
	

	// ********** behavior **********

	@Override
	protected void startListeningToItem(Model item) {
		item.addStateChangeListener(this.itemStateListener);
	}

	@Override
	protected void stopListeningToItem(Model item) {
		item.removeStateChangeListener(this.itemStateListener);
	}

}

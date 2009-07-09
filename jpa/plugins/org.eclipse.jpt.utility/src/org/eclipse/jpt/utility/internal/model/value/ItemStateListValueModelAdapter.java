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

import org.eclipse.jpt.utility.model.Model;
import org.eclipse.jpt.utility.model.event.StateChangeEvent;
import org.eclipse.jpt.utility.model.listener.StateChangeListener;
import org.eclipse.jpt.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.utility.model.value.ListValueModel;

/**
 * Extend ItemAspectListValueModelAdapter to listen to the
 * "state" of each item in the wrapped list model.
 */
public class ItemStateListValueModelAdapter<E>
	extends ItemAspectListValueModelAdapter<E>
{
	/** Listener that listens to all the items in the list. */
	protected final StateChangeListener itemStateListener;


	// ********** constructors **********

	/**
	 * Construct an adapter for the item state.
	 */
	public ItemStateListValueModelAdapter(ListValueModel<E> listHolder) {
		super(listHolder);
		this.itemStateListener = this.buildItemStateListener();
	}

	/**
	 * Construct an adapter for the item state.
	 */
	public ItemStateListValueModelAdapter(CollectionValueModel<E> collectionHolder) {
		this(new CollectionListValueModelAdapter<E>(collectionHolder));
	}


	// ********** initialization **********

	protected StateChangeListener buildItemStateListener() {
		return new StateChangeListener() {
			public void stateChanged(StateChangeEvent event) {
				ItemStateListValueModelAdapter.this.itemAspectChanged(event);
			}
			@Override
			public String toString() {
				return "item state listener"; //$NON-NLS-1$
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

/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.model.value;

import org.eclipse.jpt.common.utility.model.Model;
import org.eclipse.jpt.common.utility.model.event.ChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.ChangeListener;
import org.eclipse.jpt.common.utility.model.listener.AbstractChangeListener;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;

/**
 * Extend {@link ItemAspectListValueModelAdapter} to listen to all of the changes
 * of each item in the wrapped list model.
 */
public class ItemChangeListValueModelAdapter<E>
	extends ItemAspectListValueModelAdapter<E>
{
	/** Listener that listens to all the items in the list. */
	protected final ChangeListener itemChangeListener;


	// ********** constructors **********

	/**
	 * Construct an adapter for the specified items.
	 */
	public ItemChangeListValueModelAdapter(ListValueModel<E> listHolder) {
		super(listHolder);
		this.itemChangeListener = this.buildItemChangeListener();
	}


	// ********** initialization **********

	protected ChangeListener buildItemChangeListener() {
		return new ItemChangeListener();
	}

	protected class ItemChangeListener
		extends AbstractChangeListener
	{
		@Override
		protected void modelChanged(ChangeEvent event) {
			ItemChangeListValueModelAdapter.this.itemAspectChanged(event);
		}
	}


	// ********** behavior **********

	@Override
	protected void engageItem_(Model item) {
		item.addChangeListener(this.itemChangeListener);
	}

	@Override
	protected void disengageItem_(Model item) {
		item.removeChangeListener(this.itemChangeListener);
	}

}

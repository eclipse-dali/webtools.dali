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
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;

/**
 * Extend {@link ValueAspectAdapter} to listen to all the aspects
 * of the value in the wrapped value model.
 */
public class ValueChangeAdapter<V extends Model>
	extends ValueAspectAdapter<V>
{
	/** Listener that listens to the value. */
	protected final ChangeListener valueAspectListener;


	// ********** constructors **********

	/**
	 * Construct a change adapter for the specified value.
	 */
	public ValueChangeAdapter(ModifiablePropertyValueModel<V> valueHolder) {
		super(valueHolder);
		this.valueAspectListener = this.buildValueAspectListener();
	}


	// ********** initialization **********

	protected ChangeListener buildValueAspectListener() {
		return new ValueAspectListener();
	}
	
	protected class ValueAspectListener
		extends AbstractChangeListener
	{
		@Override
		protected void modelChanged(ChangeEvent event) {
			ValueChangeAdapter.this.valueAspectChanged(event);
		}
	}
	

	// ********** ValueAspectAdapter implementation **********

	@Override
	protected void engageValue_() {
		this.value.addChangeListener(this.valueAspectListener);
	}

	@Override
	protected void disengageValue_() {
		this.value.removeChangeListener(this.valueAspectListener);
	}


	// ********** change events **********

	protected void valueAspectChanged(@SuppressWarnings("unused") ChangeEvent event) {
		this.valueAspectChanged();
	}

}

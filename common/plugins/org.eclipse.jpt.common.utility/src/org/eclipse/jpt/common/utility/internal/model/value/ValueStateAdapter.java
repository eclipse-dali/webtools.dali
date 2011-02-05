/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.model.value;

import org.eclipse.jpt.common.utility.model.Model;
import org.eclipse.jpt.common.utility.model.event.StateChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.StateChangeListener;
import org.eclipse.jpt.common.utility.model.value.WritablePropertyValueModel;

/**
 * Extend {@link ValueAspectAdapter} to listen to the
 * "state" of the value in the wrapped value model.
 */
public class ValueStateAdapter<V extends Model>
	extends ValueAspectAdapter<V>
{
	/** Listener that listens to value. */
	protected final StateChangeListener valueStateListener;


	// ********** constructors **********

	/**
	 * Construct an adapter for the value state.
	 */
	public ValueStateAdapter(WritablePropertyValueModel<V> valueHolder) {
		super(valueHolder);
		this.valueStateListener = this.buildValueStateListener();
	}


	// ********** initialization **********

	protected StateChangeListener buildValueStateListener() {
		return new StateChangeListener() {
			public void stateChanged(StateChangeEvent event) {
				ValueStateAdapter.this.stateChanged(event);
			}
			@Override
			public String toString() {
				return "value state listener"; //$NON-NLS-1$
			}
		};
	}


	// ********** ValueAspectAdapter implementation **********

	@Override
	protected void engageValue_() {
		this.value.addStateChangeListener(this.valueStateListener);
	}

	@Override
	protected void disengageValue_() {
		this.value.removeStateChangeListener(this.valueStateListener);
	}


	// ********** change events **********

	protected void stateChanged(@SuppressWarnings("unused") StateChangeEvent event) {
		this.valueAspectChanged();
	}

}

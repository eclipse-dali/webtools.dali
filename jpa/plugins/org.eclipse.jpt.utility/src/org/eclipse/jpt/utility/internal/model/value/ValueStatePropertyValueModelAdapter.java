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
 * Extend ValueAspectPropertyValueModelAdapter to listen to the
 * "state" of the value in the wrapped value model.
 */
public class ValueStatePropertyValueModelAdapter<T extends Model>
	extends ValueAspectPropertyValueModelAdapter<T>
{
	/** Listener that listens to value. */
	protected final StateChangeListener valueStateListener;


	// ********** constructors **********

	/**
	 * Construct an adapter for the value state.
	 */
	public ValueStatePropertyValueModelAdapter(WritablePropertyValueModel<T> valueHolder) {
		super(valueHolder);
		this.valueStateListener = this.buildValueStateListener();
	}


	// ********** initialization **********

	protected StateChangeListener buildValueStateListener() {
		return new StateChangeListener() {
			public void stateChanged(StateChangeEvent e) {
				ValueStatePropertyValueModelAdapter.this.valueAspectChanged();
			}
			@Override
			public String toString() {
				return "value state listener";
			}
		};
	}
	

	// ********** behavior **********

	@Override
	protected void engageValue_() {
		this.value.addStateChangeListener(this.valueStateListener);
	}

	@Override
	protected void disengageValue_() {
		this.value.removeStateChangeListener(this.valueStateListener);
	}

}

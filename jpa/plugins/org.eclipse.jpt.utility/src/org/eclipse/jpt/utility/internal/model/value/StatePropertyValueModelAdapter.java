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

import org.eclipse.jpt.utility.internal.model.Model;
import org.eclipse.jpt.utility.internal.model.event.StateChangeEvent;
import org.eclipse.jpt.utility.internal.model.listener.StateChangeListener;

/**
 * This abstract class provides the infrastructure needed to wrap
 * a model, "lazily" listen to it, and convert
 * its state change notifications into property value model change
 * notifications.
 * 
 * Subclasses must override:
 * - #buildValue()
 *     to return the current property value, as derived from the
 *     current model
 * 
 * Subclasses might want to override:
 * - #stateChanged(StateChangeEvent event)
 *     to improve performance (by not recalculating the value, if possible)
 */
public abstract class StatePropertyValueModelAdapter<T>
	extends AspectPropertyValueModelAdapter<T>
{
	/** The wrapped model. */
	protected final Model model;

	/** A listener that allows us to synch with changes to the wrapped model. */
	protected final StateChangeListener stateChangeListener;


	// ********** constructor/initialization **********

	/**
	 * Construct a property value model with the specified wrapped model.
	 */
	protected StatePropertyValueModelAdapter(Model model) {
		super();
		this.model = model;
		this.stateChangeListener = this.buildStateChangeListener();
	}

	protected StateChangeListener buildStateChangeListener() {
		return new StateChangeListener() {
			public void stateChanged(StateChangeEvent event) {
				StatePropertyValueModelAdapter.this.stateChanged(event);
			}
			@Override
			public String toString() {
				return "state change listener";
			}
		};
	}


	// ********** behavior **********

	/**
	 * Start listening to the model.
	 */
	@Override
	protected void engageModel_() {
		this.model.addStateChangeListener(this.stateChangeListener);
	}

	/**
	 * Stop listening to the model.
	 */
	@Override
	protected void disengageModel_() {
		this.model.removeStateChangeListener(this.stateChangeListener);
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.model);
	}

	
	// ********** state change support **********

	/**
	 * The model's state changed;
	 * propagate the change notification appropriately.
	 */
	protected void stateChanged(StateChangeEvent event) {
		// by default, simply recalculate the value and fire an event
		this.propertyChanged();
	}

}

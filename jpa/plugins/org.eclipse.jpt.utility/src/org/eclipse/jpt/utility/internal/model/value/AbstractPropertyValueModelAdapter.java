/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal.model.value;

import org.eclipse.jpt.utility.model.value.PropertyValueModel;

/**
 * This abstract class provides the infrastructure needed to wrap
 * a model, "lazily" listen to it, and convert
 * its change notifications into property value model change
 * notifications.
 * <p>
 * Subclasses must implement:<ul>
 * <li>{@link #buildValue()}<p>
 *     to return the current property value, as derived from the
 *     current model
 * <li>{@link #engageModel_()}<p>
 *     to start listening to the adapted model
 * <li>{@link #disengageModel_()}<p>
 *     to stop listening to the adapted model
 * </ul>
 * Subclasses can call {@link #propertyChanged()} whenever the calculated
 * value of the property changes (as determined by the subclass).
 */
public abstract class AbstractPropertyValueModelAdapter<V>
	extends AbstractPropertyValueModel
	implements PropertyValueModel<V>
{
	/**
	 * Cache the current value so we can pass an "old value" when
	 * we fire a property change event.
	 * We need this because the value may be calculated and we may
	 * not able to derive the "old value" from any fired events.
	 */
	protected V value;


	// ********** constructor/initialization **********

	protected AbstractPropertyValueModelAdapter() {
		super();
		// our value is null when we are not listening to the model
		this.value = null;
	}


	// ********** PropertyValueModel implementation **********

	/**
	 * Return the cached value.
	 */
	public V getValue() {
		return this.value;
	}


	// ********** behavior **********

	/**
	 * Start listening to the model and build the value.
	 */
	@Override
	protected void engageModel() {
		this.engageModel_();
		// synch our value *after* we start listening to the collection,
		// since the collection's value might change when a listener is added
		this.value = this.buildValue();
	}

	/**
	 * Start listening to the model.
	 */
	protected abstract void engageModel_();

	/**
	 * Build and return the current value, as derived from the
	 * current state of the underlying model.
	 */
	protected abstract V buildValue();

	/**
	 * Stop listening to the model and clear the value.
	 */
	@Override
	protected void disengageModel() {
		this.disengageModel_();
		// clear out our value when we are not listening to the collection
		this.value = null;
	}

	/**
	 * Stop listening to the model.
	 */
	protected abstract void disengageModel_();

	/**
	 * The underlying model changed in some fashion.
	 * Recalculate the value and notify any listeners.
	 */
	protected void propertyChanged() {
		Object old = this.value;
		this.value = this.buildValue();
		this.firePropertyChanged(VALUE, old, this.value);
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.value);
	}

}

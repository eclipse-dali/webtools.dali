/*******************************************************************************
 * Copyright (c) 2011, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.model.value;

import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;

/**
 * Wrap a writable property value model and cache its value so any changes
 * made via the wrapper model will not fire any events to its listeners.
 * Thus, a client can modify and listen to the wrapper model and not receive
 * a change event when the client modifies the wrapper model; but any changes
 * made by other clients (directly to the original model) will fire events
 * to the client.
 * 
 * @param <V> the type of the model's value
 */
public class CachingPropertyValueModelWrapper<V>
	extends PropertyValueModelWrapper<V>
	implements ModifiablePropertyValueModel<V>
{
	/**
	 * Cache the value so we ignore any property change events
	 * we ourselves trigger.
	 */
	protected volatile V value;


	/**
	 * Construct a caching writable property value model with the specified
	 * nested writable property value model.
	 */
	public CachingPropertyValueModelWrapper(ModifiablePropertyValueModel<V> valueModel) {
		super(valueModel);
	}

	/**
	 * Return the cached value, since it is up-to-date.
	 */
	public V getValue() {
		return this.value;
	}

	/**
	 * Cache the new value so we ignore the resulting property change event.
	 */
	public void setValue(V value) {
		this.value = value;
		this.getValueModel().setValue(value);
	}

	@Override
	protected void wrappedValueChanged(V oldValue, V newValue) {
		V old = this.value;
		// the new event will be suppressed if it is the result of a new value
		// forwarded from this wrapper (i.e. 'old' and 'newValue' are equal)
		this.firePropertyChanged(VALUE, old, this.value = newValue);
	}

	/**
	 * Our constructors accept only a
	 * {@link ModifiablePropertyValueModel}{@code<V>},
	 * so this cast should be safe.
	 */
	@SuppressWarnings("unchecked")
	protected ModifiablePropertyValueModel<V> getValueModel() {
		return (ModifiablePropertyValueModel<V>) this.valueModel;
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.value);
	}


	// ********** listeners **********

	/**
	 * We have listeners, cache the nested value.
	 */
	@Override
	protected void engageModel() {
		super.engageModel();
		this.value = this.valueModel.getValue();
	}

	/**
	 * We have no more listeners, clear the cached value.
	 */
	@Override
	protected void disengageModel() {
		this.value = null;
		super.disengageModel();
	}
}

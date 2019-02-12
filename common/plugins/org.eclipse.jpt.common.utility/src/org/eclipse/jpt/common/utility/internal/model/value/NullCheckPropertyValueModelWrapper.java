/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.model.value;

import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;

/**
 * A <code>NullCheckPropertyValueModelWrapper</code> wraps another
 * {@link PropertyValueModel} and returns a client-configured
 * non-<code>null</code> value when the wrapped value is <code>null</code>.
 * 
 * @param <V> the type of the <em>wrapped</em> model's value
 */
public class NullCheckPropertyValueModelWrapper<V>
	extends PropertyValueModelWrapper<V>
	implements PropertyValueModel<V>
{
	private final V nullValue;


	/**
	 * Construct a property value model with the specified <code>null</code>
	 * value.
	 */
	public NullCheckPropertyValueModelWrapper(PropertyValueModel<? extends V> valueModel, V nullValue) {
		super(valueModel);
		if (nullValue == null) {
			throw new NullPointerException();
		}
		this.nullValue = nullValue;
	}

	/**
	 * Check for <code>null</code> from the wrapped model.
	 */
	public V getValue() {
		return this.convertValue(this.valueModel.getValue());
	}

	/**
	 * Propagate the event with the appropriate values.
	 */
	@Override
	protected void wrappedValueChanged(V oldValue, V newValue) {
		this.firePropertyChanged(VALUE, this.convertValue(oldValue), this.convertValue(newValue));
	}

	private V convertValue(V value) {
		return (value != null) ? value : this.nullValue;
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.getValue());
	}
}

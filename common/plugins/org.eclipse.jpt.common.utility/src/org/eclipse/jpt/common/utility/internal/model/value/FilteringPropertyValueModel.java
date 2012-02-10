/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.model.value;

import org.eclipse.jpt.common.utility.Filter;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;

/**
 * A <code>FilteringPropertyValueModel</code> wraps another
 * {@link PropertyValueModel} and uses a {@link Filter}
 * to determine when the wrapped value is to be returned by calls
 * to {@link #getValue()}.
 * <p>
 * One, possibly undesirable, side-effect of using this value model is that
 * it must return <em>something</em> as the value. The default behavior is
 * to return <code>null</code> whenever the wrapped value is not "accepted",
 * which can be configured and/or overridden ({@link #getDefaultValue()}).
 * 
 * @param <V> the type of the model's <em>filtered</em> value
 * @see Filter
 */
public class FilteringPropertyValueModel<V>
	extends PropertyValueModelWrapper<V>
	implements PropertyValueModel<V>
{
	/**
	 * The model returns any wrapped value accepted by this filter and returns
	 * the {@link #defaultValue} in place of any wrapped value rejected by this
	 * filter.
	 */
	protected final Filter<V> filter;

	/**
	 * The value returned by the model if the wrapped value is reject by the
	 * {@link #filter}.
	 */
	protected final V defaultValue;


	// ********** constructors **********

	/**
	 * Construct a filtering property value model with the specified nested
	 * property value model and filter.
	 * The default value will be <code>null</code>.
	 */
	public FilteringPropertyValueModel(PropertyValueModel<? extends V> valueModel, Filter<V> filter) {
		this(valueModel, filter, null);
	}

	/**
	 * Construct a filtering property value model with the specified nested
	 * property value model, filter, and default value.
	 */
	public FilteringPropertyValueModel(PropertyValueModel<? extends V> valueModel, Filter<V> filter, V defaultValue) {
		super(valueModel);
		if (filter == null) {
			throw new NullPointerException();
		}
		this.filter = filter;
		this.defaultValue = defaultValue;
	}


	// ********** PropertyValueModel implementation **********

	public V getValue() {
		return this.filterValue(this.valueModel.getValue());
	}


	// ********** PropertyValueModelWrapper implementation **********

	@Override
	protected void wrappedValueChanged(V oldValue, V newValue) {
		// filter the values before propagating the change event
		this.firePropertyChanged(VALUE, this.filterValue(oldValue), this.filterValue(newValue));
	}


	// ********** queries **********

	/**
	 * If the specified value is "accepted" simply return it,
	 * otherwise return the default value.
	 */
	protected V filterValue(V value) {
		return this.filter.accept(value) ? value : this.getDefaultValue();
	}

	/**
	 * Return the object that should be returned if
	 * the nested value was rejected by the filter.
	 * The default is <code>null</code>.
	 */
	protected V getDefaultValue() {
		return this.defaultValue;
	}

	/**
	 * Print the filtered value.
	 */
	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.getValue());
	}
}

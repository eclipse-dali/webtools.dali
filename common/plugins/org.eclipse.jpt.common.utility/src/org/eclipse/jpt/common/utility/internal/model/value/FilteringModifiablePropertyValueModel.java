/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.model.value;

import org.eclipse.jpt.common.utility.filter.Filter;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;

/**
 * A <code>FilteringModifiablePropertyValueModel</code> wraps another
 * {@link ModifiablePropertyValueModel} and uses a pair of {@link Filter}s
 * to determine when the wrapped value is to be returned by calls
 * to {@link #getValue()} and modified by calls to
 * {@link #setValue(Object) setValue(V)}.
 * <p>
 * One, possibly undesirable, side-effect of using this value model is that
 * it must return <em>something</em> as the value. The default behavior is
 * to return <code>null</code> whenever the wrapped value is not "accepted",
 * which can be configured and/or overridden
 * ({@link FilteringPropertyValueModel#getDefaultValue() getDefaultValue()}).
 * <p>
 * Another, possibly undesirable, side-effect of using this value model is that
 * it will not fire an event if a new value is not "accepted", even if it is
 * different than the current value.
 * <p>
 * Similarly, if an incoming value is not "reverse accepted", <em>nothing</em>
 * will passed through to the wrapped value model, not even <code>null</code>.
 * 
 * @param <V> the type of the model's <em>filtered</em> value
 * @see Filter
 */
public class FilteringModifiablePropertyValueModel<V>
	extends FilteringPropertyValueModel<V>
	implements ModifiablePropertyValueModel<V>
{
	/**
	 * The model sets the wrapped value to any value accepted by this filter
	 * and does nothing with any value rejected by this filter.
	 */
	protected final Filter<V> setFilter;


	// ********** constructors **********

	/**
	 * Construct a filtering property value model with the specified nested
	 * property value model, <em>get</em> filter, and <em>set</em> filter.
	 * The default value will be <code>null</code>.
	 */
	public FilteringModifiablePropertyValueModel(ModifiablePropertyValueModel<V> valueModel, Filter<V> getFilter, Filter<V> setFilter) {
		this(valueModel, getFilter, setFilter, null);
	}

	/**
	 * Construct a filtering property value model with the specified nested
	 * property value model, <em>get</em> filter, <em>set</em> filter,
	 * and default value.
	 */
	public FilteringModifiablePropertyValueModel(ModifiablePropertyValueModel<V> valueModel, Filter<V> getFilter, Filter<V> setFilter, V defaultValue) {
		super(valueModel, getFilter, defaultValue);
		if (setFilter == null) {
			throw new NullPointerException();
		}
		this.setFilter = setFilter;
	}


	// ********** ModifiablePropertyValueModel implementation **********

	public void setValue(V value) {
		if (this.setFilter.accept(value)) {
			this.getValueModel().setValue(value);
		}
	}

	/**
	 * Our constructor accepts only a {@link ModifiablePropertyValueModel}{@code<T>}.
	 */
	@SuppressWarnings("unchecked")
	protected ModifiablePropertyValueModel<V> getValueModel() {
		return (ModifiablePropertyValueModel<V>) this.valueModel;
	}
}

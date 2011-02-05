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

import org.eclipse.jpt.common.utility.Filter;
import org.eclipse.jpt.common.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;

/**
 * A <code>FilteringPropertyValueModel</code> wraps another
 * {@link PropertyValueModel} and uses a {@link Filter}
 * to determine when the wrapped value is to be returned by calls
 * to {@link #getValue()}.
 * <p>
 * As an alternative to building a {@link Filter}, a subclass
 * can override {@link #accept(T)}.
 * <p>
 * One, possibly undesirable, side-effect of using this value model is that
 * it must return *something* as the value. The default behavior is
 * to return <code>null</code> whenever the wrapped value is not "accepted",
 * which can be configured and/or overridden ({@link #getDefaultValue()}).
 */
public class FilteringPropertyValueModel<T>
	extends PropertyValueModelWrapper<T>
	implements PropertyValueModel<T>
{
	protected final Filter<T> filter;
	protected final T defaultValue;


	// ********** constructors **********

	/**
	 * Construct a filtering property value model with the specified nested
	 * property value model and a disabled filter.
	 * Use this constructor if you want to override {@link #accept(T)}
	 * instead of building a {@link Filter}.
	 * The default value will be <code>null</code>.
	 */
	public FilteringPropertyValueModel(PropertyValueModel<? extends T> valueHolder) {
		this(valueHolder, Filter.Disabled.<T>instance(), null);
	}

	/**
	 * Construct a filtering property value model with the specified nested
	 * property value model, specified default value, and a disabled filter.
	 * Use this constructor if you want to override {@link #accept(T)}
	 * instead of building a {@link Filter}
	 * <em>and</em> you need to specify
	 * a default value other than <code>null</code>.
	 */
	public FilteringPropertyValueModel(PropertyValueModel<? extends T> valueHolder, T defaultValue) {
		this(valueHolder, Filter.Disabled.<T>instance(), defaultValue);
	}

	/**
	 * Construct a filtering property value model with the specified nested
	 * property value model and filter.
	 * The default value will be <code>null</code>.
	 */
	public FilteringPropertyValueModel(PropertyValueModel<? extends T> valueHolder, Filter<T> filter) {
		this(valueHolder, filter, null);
	}

	/**
	 * Construct a filtering property value model with the specified nested
	 * property value model, filter, and default value.
	 */
	public FilteringPropertyValueModel(PropertyValueModel<? extends T> valueHolder, Filter<T> filter, T defaultValue) {
		super(valueHolder);
		this.filter = filter;
		this.defaultValue = defaultValue;
	}


	// ********** PropertyValueModel implementation **********

	public T getValue() {
		return this.filterValue(this.valueHolder.getValue());
	}


	// ********** PropertyValueModelWrapper implementation **********

	@Override
	protected void valueChanged(PropertyChangeEvent event) {
		// filter the values before propagating the change event
		@SuppressWarnings("unchecked")
		T eventOldValue = (T) event.getOldValue();
		Object oldValue = this.filterValue(eventOldValue);
		@SuppressWarnings("unchecked")
		T eventNewValue = (T) event.getNewValue();
		Object newValue = this.filterValue(eventNewValue);
		this.firePropertyChanged(VALUE, oldValue, newValue);
	}


	// ********** queries **********

	/**
	 * If the specified value is "accepted" simply return it,
	 * otherwise return the default value.
	 */
	protected T filterValue(T value) {
		return this.accept(value) ? value : this.getDefaultValue();
	}

	/**
	 * Return whether the filtering property value model should
	 * return the specified value from a call to
	 * {@link #getValue()}; the value came
	 * from the nested property value model
	 * <p>
	 * This method can be overridden by a subclass as an
	 * alternative to building a {@link Filter}.
	 */
	protected boolean accept(T value) {
		return this.filter.accept(value);
	}

	/**
	 * Return the object that should be returned if
	 * the nested value was rejected by the filter.
	 * The default is <code>null</code>.
	 */
	protected T getDefaultValue() {
		return this.defaultValue;
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.getValue());
	}

}

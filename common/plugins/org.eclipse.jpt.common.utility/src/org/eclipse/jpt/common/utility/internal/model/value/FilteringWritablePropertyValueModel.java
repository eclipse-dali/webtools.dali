/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.model.value;

import org.eclipse.jpt.common.utility.internal.BidiFilter;
import org.eclipse.jpt.common.utility.model.value.WritablePropertyValueModel;

/**
 * A <code>FilteringWritablePropertyValueModel</code> wraps another
 * {@link WritablePropertyValueModel} and uses a {@link BidiFilter}
 * to determine when the wrapped value is to be returned by calls
 * to {@link FilteringPropertyValueModel#getValue() getValue()} and modified by calls to
 * {@link #setValue(T)}.
 * <p>
 * As an alternative to building a {@link BidiFilter}, a subclass
 * can override {@link FilteringPropertyValueModel#accept(T) accept(T)} and {@link #reverseAccept(T)}.
 * <p>
 * One, possibly undesirable, side-effect of using this value model is that
 * it must return *something* as the value. The default behavior is
 * to return <code>null</code> whenever the wrapped value is not "accepted",
 * which can be configured and/or overridden ({@link FilteringPropertyValueModel#getDefaultValue() getDefaultValue()}).
 * <p>
 * Similarly, if an incoming value is not "reverse accepted", <em>nothing</em>
 * will passed through to the wrapped value holder, not even <code>null</code>.
 */
public class FilteringWritablePropertyValueModel<T>
	extends FilteringPropertyValueModel<T>
	implements WritablePropertyValueModel<T>
{


	// ********** constructors **********

	/**
	 * Construct a filtering property value model with the specified nested
	 * property value model and a disabled filter.
	 * Use this constructor if you want to override
	 * {@link #accept(T)} and {@link #reverseAccept(T)}
	 * instead of building a {@link BidiFilter}.
	 * The default value will be <code>null</code>.
	 */
	public FilteringWritablePropertyValueModel(WritablePropertyValueModel<T> valueHolder) {
		this(valueHolder, BidiFilter.Disabled.<T>instance(), null);
	}

	/**
	 * Construct a filtering property value model with the specified nested
	 * property value model, specified default value, and a disabled filter.
	 * Use this constructor if you want to override
	 * {@link #accept(T)} and {@link #reverseAccept(T)}
	 * instead of building a {@link BidiFilter}.
	 * <em>and</em> you need to specify
	 * a default value other than <code>null</code>.
	 */
	public FilteringWritablePropertyValueModel(WritablePropertyValueModel<T> valueHolder, T defaultValue) {
		this(valueHolder, BidiFilter.Disabled.<T>instance(), defaultValue);
	}

	/**
	 * Construct an property value model with the specified nested
	 * property value model and filter.
	 * The default value will be <code>null</code>.
	 */
	public FilteringWritablePropertyValueModel(WritablePropertyValueModel<T> valueHolder, BidiFilter<T> filter) {
		this(valueHolder, filter, null);
	}

	/**
	 * Construct an property value model with the specified nested
	 * property value model, filter, and default value.
	 */
	public FilteringWritablePropertyValueModel(WritablePropertyValueModel<T> valueHolder, BidiFilter<T> filter, T defaultValue) {
		super(valueHolder, filter, defaultValue);
	}


	// ********** WritablePropertyValueModel implementation **********

	public void setValue(T value) {
		if (this.reverseAccept(value)) {
			this.getValueHolder().setValue(value);
		}
	}


	// ********** queries **********

	/**
	 * Return whether the filtering writable property value model
	 * should pass through the specified value to the nested
	 * writable property value model in a call to
	 * {@link #setValue(T)}.
	 * <p>
	 * This method can be overridden by a subclass as an
	 * alternative to building a {@link BidiFilter}.
	 */
	protected boolean reverseAccept(T value) {
		return this.getFilter().reverseAccept(value);
	}

	/**
	 * Our constructor accepts only a {@link WritablePropertyValueModel}{@code<T>}.
	 */
	@SuppressWarnings("unchecked")
	protected WritablePropertyValueModel<T> getValueHolder() {
		return (WritablePropertyValueModel<T>) this.valueHolder;
	}

	/**
	 * Our constructors accept only a bidirectional filter.
	 */
	protected BidiFilter<T> getFilter() {
		return (BidiFilter<T>) this.filter;
	}

}

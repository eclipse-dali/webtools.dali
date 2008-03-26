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

import org.eclipse.jpt.utility.internal.BidiFilter;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;

/**
 * A <code>FilteringWritablePropertyValueModel</code> wraps another
 * <code>WritabelPropertyValueModel</code> and uses a <code>BidiFilter</code>
 * to determine when the wrapped value is to be returned by calls
 * to <code>value()</code> and modified by calls to
 * <code>setValue(Object)</code>.
 * <p>
 * As an alternative to building a <code>BidiFilter</code>, a subclass
 * of <code>FilteringWritablePropertyValueModel</code> can override the
 * <code>accept(Object)</code> and <code>reverseAccept(Object)</code>
 * methods.
 * <p>
 * One, possibly undesirable, side-effect of using this value model is that
 * it must return *something* as the value. The default behavior is
 * to return <code>null</code> whenever the wrapped value is not "accepted",
 * which can be configured and/or overridden.
 * <p>
 * Similarly, if an incoming value is not "reverseAccepted", *nothing* will passed
 * through to the wrapped value holder, not even <code>null</code>.
 */
public class FilteringWritablePropertyValueModel<T>
	extends FilteringPropertyValueModel<T>
	implements WritablePropertyValueModel<T>
{


	// ********** constructors **********

	/**
	 * Construct a property value model with the specified nested
	 * property value model and a disabled filter.
	 * Use this constructor if you want to override the
	 * <code>accept(Object)</code> and <code>reverseAccept(Object)</code>
	 * methods instead of building a <code>BidiFilter</code>.
	 * The default value will be <code>null</code>.
	 */
	public FilteringWritablePropertyValueModel(WritablePropertyValueModel<T> valueHolder) {
		this(valueHolder, BidiFilter.Disabled.<T>instance(), null);
	}

	/**
	 * Construct a property value model with the specified nested
	 * property value model, specified default value, and a disabled filter.
	 * Use this constructor if you want to override the
	 * <code>accept(Object)</code> and <code>reverseAccept(Object)</code>
	 * methods instead of building a <code>BidiFilter</code>
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
			this.valueHolder().setValue(value);
		}
	}


	// ********** queries **********

	/**
	 * Return whether the <code>FilteringWritablePropertyValueModel</code>
	 * should pass through the specified value to the nested
	 * writable property value model in a call to the
	 * <code>setValue(Object)</code> method
	 * <p>
	 * This method can be overridden by a subclass as an
	 * alternative to building a <code>BidiFilter</code>.
	 */
	protected boolean reverseAccept(T value) {
		return this.getFilter().reverseAccept(value);
	}

	/**
	 * Our constructors accept only a WritablePropertyValueModel<T>.
	 */
	@SuppressWarnings("unchecked")
	protected WritablePropertyValueModel<T> valueHolder() {
		return (WritablePropertyValueModel<T>) this.valueHolder;
	}

	/**
	 * Our constructors accept only a bidirectional filter.
	 */
	protected BidiFilter<T> getFilter() {
		return (BidiFilter<T>) this.filter;
	}

}

/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal.model.value;

import org.eclipse.jpt.utility.internal.BidiTransformer;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;

/**
 * A <code>TransformationPropertyValueModel</code> wraps another
 * <code>PropertyValueModel</code> and uses a <code>Transformer</code>
 * to transform the wrapped value before it is returned by <code>value()</code>.
 * <p>
 * As an alternative to building a <code>Transformer</code>,
 * a subclass of <code>TransformationPropertyValueModel</code> can
 * either override the <code>transform_(Object)</code> method or,
 * if something other than null should be returned when the wrapped value
 * is null, override the <code>transform(Object)</code> method.
 */
public class CachingTransformationWritablePropertyValueModel<T1, T2>
	extends TransformationWritablePropertyValueModel<T1, T2>
	implements PropertyValueModel<T2>
{

	protected T2 cachedValue;
	
	// ********** constructors/initialization **********

	/**
	 * Construct a property value model with the specified nested
	 * property value model and the default transformer.
	 * Use this constructor if you want to override the
	 * <code>transform_(Object)</code> or <code>transform(Object)</code>
	 * method instead of building a <code>Transformer</code>.
	 */
	public CachingTransformationWritablePropertyValueModel(WritablePropertyValueModel<T1> valueHolder) {
		super(valueHolder);
	}

	/**
	 * Construct an property value model with the specified nested
	 * property value model and transformer.
	 */
	public CachingTransformationWritablePropertyValueModel(WritablePropertyValueModel<T1> valueHolder, BidiTransformer<T1, T2> transformer) {
		super(valueHolder, transformer);
	}
	
	// ********** behavior **********

	@Override
	protected void engageValueHolder() {
		super.engageValueHolder();
		this.cachedValue = this.transform(this.valueHolder.getValue());
	}
	
	@Override
	protected void disengageValueHolder() {
		super.disengageValueHolder();
		this.cachedValue = null;
	}

	@Override
	public T2 getValue() {
		return this.cachedValue;
	}
	
	@Override
	protected T2 transformNew(T1 value) {
		this.cachedValue = super.transformNew(value);
		return this.cachedValue;
	};
	
	@Override
	protected T2 transformOld(T1 value) {
		return this.cachedValue;
	}
}

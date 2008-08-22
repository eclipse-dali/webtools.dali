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

import org.eclipse.jpt.utility.internal.Transformer;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;

/**
 * A <code>CachingTransformationPropertyValueModel</code> wraps another
 * <code>PropertyValueModel</code> and uses a <code>Transformer</code>
 * to transform the wrapped value before it is returned by <code>value()</code>.
 * It also caches the value so that a transform is not done on the oldValue when
 * firing a property change event. 
 * <p>
 * As an alternative to building a <code>Transformer</code>,
 * a subclass of <code>CachingTransformationPropertyValueModel</code> can
 * either override the <code>transform_(Object)</code> method or,
 * if something other than null should be returned when the wrapped value
 * is null, override the <code>transform(Object)</code> method.
 */
public class CachingTransformationPropertyValueModel<T1, T2>
	extends TransformationPropertyValueModel<T1, T2>
	implements PropertyValueModel<T2>
{

	/**
	 * Cache the transformed value so that during property change event notification
	 * we do not have to transform the value.  The oldValue could no longer be valid in
	 * the model, thus transforming it would not be valid.
	 */
	protected T2 cachedValue;
	
	// ********** constructors/initialization **********

	/**
	 * Construct a property value model with the specified nested
	 * property value model and the default transformer.
	 * Use this constructor if you want to override the
	 * <code>transform_(Object)</code> or <code>transform(Object)</code>
	 * method instead of building a <code>Transformer</code>.
	 */
	public CachingTransformationPropertyValueModel(PropertyValueModel<? extends T1> valueHolder) {
		super(valueHolder);
	}

	/**
	 * Construct an property value model with the specified nested
	 * property value model and transformer.
	 */
	public CachingTransformationPropertyValueModel(PropertyValueModel<? extends T1> valueHolder, Transformer<T1, T2> transformer) {
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
	
	/**
	 * Transform the new value, caching it before returning it
	 */
	@Override
	protected T2 transformNew(T1 value) {
		this.cachedValue = super.transformNew(value);
		return this.cachedValue;
	};
	
	/**
	 * no transformation, just return the cachedValue which is already transformed
	 */
	@Override
	protected T2 transformOld(T1 value) {
		return this.cachedValue;
	}
}

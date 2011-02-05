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

import org.eclipse.jpt.common.utility.internal.BidiTransformer;
import org.eclipse.jpt.common.utility.model.value.WritablePropertyValueModel;

/**
 * A <code>CachingTransformationWritablePropertyValueModel<code> augments the
 * behavior of a {@link TransformationWritablePropertyValueModel} by caching
 * the transformed value.
 * The transformed value is calculated and cached during initialization and every
 * time the wrapped value changes. This can be useful when the old value
 * passed in to {@link #valueChanged(org.eclipse.jpt.common.utility.model.event.PropertyChangeEvent)}
 * can no longer be "transformed" because its state is no longer valid.
 * This caching can also improve time performance in some situations.
 */
public class CachingTransformationWritablePropertyValueModel<T1, T2>
	extends TransformationWritablePropertyValueModel<T1, T2>
{
	/**
	 * Cache the transformed value so that during property change event notification
	 * we do not have to transform the old value. The old value could no longer be valid in
	 * the model; as a result, transforming it would not be valid.
	 */
	protected T2 cachedValue;


	// ********** constructors/initialization **********

	/**
	 * Construct a writable property value model with the specified nested
	 * writable property value model and the default bidi transformer.
	 * Use this constructor if you want to override
	 * {@link #transform_(Object)} and {@link reverseTransform_(Object)}
	 * (or {@link #transform(Object)} and {@link #reverseTransform(Object)})
	 * methods instead of building a {@link BidiTransformer}.
	 */
	public CachingTransformationWritablePropertyValueModel(WritablePropertyValueModel<T1> valueHolder) {
		super(valueHolder);
	}

	/**
	 * Construct a writable property value model with the specified nested
	 * writable property value model and bidi transformer.
	 */
	public CachingTransformationWritablePropertyValueModel(WritablePropertyValueModel<T1> valueHolder, BidiTransformer<T1, T2> transformer) {
		super(valueHolder, transformer);
	}


	// ********** behavior **********

	/**
	 * We have listeners, transform the nested value and cache the result.
	 */
	@Override
	protected void engageModel() {
		super.engageModel();
		this.cachedValue = this.transform(this.valueHolder.getValue());
	}

	/**
	 * We have no more listeners, clear the cached value.
	 */
	@Override
	protected void disengageModel() {
		this.cachedValue = null;
		super.disengageModel();
	}

	/**
	 * No need to transform the nested value, simply return the cached value,
	 * which is already transformed.
	 */
	@Override
	public T2 getValue() {
		return this.cachedValue;
	}

	/**
	 * Transform the specified new value, caching it before returning it.
	 * A bit of a side-effect, but it seems reasonable.
	 */
	@Override
	protected T2 transformNew(T1 value) {
		this.cachedValue = super.transformNew(value);
		return this.cachedValue;
	}

	/**
	 * No need to transform the old value, simply return the cached value,
	 * which is already transformed.
	 */
	@Override
	protected T2 transformOld(T1 value) {
		return this.cachedValue;
	}

}

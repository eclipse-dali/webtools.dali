/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.model.value;

import org.eclipse.jpt.common.utility.internal.transformer.AbstractTransformer;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * A <code>TransformationPropertyValueModel</code> wraps another
 * {@link PropertyValueModel} and uses a {@link Transformer}
 * to transform the wrapped value before it is returned by {@link #getValue()}.
 * <p>
 * The transformed value is calculated and cached during initialization and every
 * time the wrapped value changes. This can be useful when the old value
 * passed in to {@link #wrappedValueChanged(org.eclipse.jpt.common.utility.model.event.PropertyChangeEvent)}
 * can no longer be "transformed" because its state is no longer valid.
 * This caching can also improve time performance in some situations.
 * <p>
 * As an alternative to building a {@link Transformer},
 * a subclass of <code>TransformationPropertyValueModel</code> can
 * either override {@link #transform_(Object)} or,
 * if something other than <code>null</code> should be returned when the
 * wrapped value is <code>null</code>, override {@link #transform(Object)}.
 * 
 * @param <V1> the type of the <em>wrapped</em> model's value
 * @param <V2> the type of the model's <em>transformed</em> value
 * @see Transformer
 */
public class TransformationPropertyValueModel<V1, V2>
	extends PropertyValueModelWrapper<V1>
	implements PropertyValueModel<V2>
{
	/**
	 * Cache the transformed value so that during property change event
	 * notification we do not have to transform the old value. It is possible
	 * the old value is no longer be valid in the model; as a result,
	 * transforming it would not be valid.
	 */
	protected volatile V2 value;

	protected final Transformer<V1, V2> transformer;


	// ********** constructors/initialization **********

	/**
	 * Construct a property value model with the specified nested
	 * property value model and the default transformer.
	 * Use this constructor if you want to override
	 * {@link #transform_(Object)} or {@link #transform(Object)}
	 * method instead of building a {@link Transformer}.
	 */
	public TransformationPropertyValueModel(PropertyValueModel<? extends V1> valueModel) {
		super(valueModel);
		this.transformer = this.buildTransformer();
	}

	/**
	 * Construct a property value model with the specified nested
	 * property value model and transformer. Depending on the nested model,
	 * the transformer may be required to handle a <code>null</code> value.
	 */
	public TransformationPropertyValueModel(PropertyValueModel<? extends V1> valueModel, Transformer<V1, V2> transformer) {
		super(valueModel);
		if (transformer == null) {
			throw new NullPointerException();
		}
		this.transformer = transformer;
	}

	protected Transformer<V1, V2> buildTransformer() {
		return new DefaultTransformer();
	}


	// ********** PropertyValueModel implementation **********

	/**
	 * No need to transform the nested value, simply return the cached value,
	 * which is already transformed.
	 */
	public V2 getValue() {
		return this.value;
	}


	// ********** PropertyValueModelWrapper implementation **********

	/**
	 * Propagate the event with transformed values.
	 */
	@Override
	protected void wrappedValueChanged(V1 oldValue, V1 newValue) {
		V2 old = this.value;
		this.firePropertyChanged(VALUE, old, this.value = this.transform(newValue));
	}


	// ********** transformation **********

	/**
	 * Transform the specified value and return the result.
	 */
	protected V2 transform(V1 v) {
		return this.transformer.transform(v);
	}

	/**
	 * Transform the specified, non-<code>null</code>, value and return the result.
	 */
	protected V2 transform_(@SuppressWarnings("unused") V1 v) {
		throw new RuntimeException("This method was not overridden."); //$NON-NLS-1$
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.value);
	}


	// ********** listeners **********

	/**
	 * We have listeners, transform the nested value and cache the result.
	 */
	@Override
	protected void engageModel() {
		super.engageModel();
		this.value = this.transform(this.valueModel.getValue());
	}

	/**
	 * We have no more listeners, clear the cached value.
	 */
	@Override
	protected void disengageModel() {
		this.value = null;
		super.disengageModel();
	}


	// ********** default transformer **********

	/**
	 * The default transformer will return <code>null</code> if the wrapped
	 * value is <code>null</code>. If the wrapped value is not
	 * <code>null</code>, it is transformed by a subclass
	 * implementation of {@link TransformationPropertyValueModel#transform_(Object)}.
	 */
	protected class DefaultTransformer
		extends AbstractTransformer<V1, V2>
	{
		@Override
		public V2 transform_(V1 v) {
			return TransformationPropertyValueModel.this.transform_(v);
		}
	}
}

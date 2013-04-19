/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.model.value;

import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.predicate.Predicate;

/**
 * A <code>PredicatePropertyValueModel</code> wraps another
 * {@link PropertyValueModel} and uses a {@link Predicate}
 * to evaluate the wrapped value before it is returned by {@link #getValue()}.
 * <p>
 * The evaluated value is calculated and cached during initialization and every
 * time the wrapped value changes. This can be useful when the old value
 * passed in to {@link #wrappedValueChanged(org.eclipse.jpt.common.utility.model.event.PropertyChangeEvent)}
 * can no longer be "evaluated" because its state is no longer valid.
 * This caching can also improve time performance in some situations.
 * 
 * @param <V> the type of the <em>wrapped</em> model's value
 * @see Predicate
 */
public class PredicatePropertyValueModel<V>
	extends PropertyValueModelWrapper<V>
	implements PropertyValueModel<Boolean>
{
	/**
	 * Cache the predicate value so that during property change event
	 * notification we do not have to evaluate the old value. It is possible
	 * the old value is no longer be valid in the model; as a result,
	 * evaluating it would not be valid.
	 */
	protected volatile Boolean value;

	protected final Predicate<? super V> predicate;


	// ********** constructors/initialization **********

	/**
	 * Construct a property value model with the specified nested
	 * property value model and predicate. Depending on the nested model,
	 * the transformer may be required to handle a <code>null</code> value.
	 */
	public PredicatePropertyValueModel(PropertyValueModel<? extends V> valueModel, Predicate<? super V> predicate) {
		super(valueModel);
		if (predicate == null) {
			throw new NullPointerException();
		}
		this.predicate = predicate;
	}

	/**
	 * No need to evaluate the nested value, simply return the cached value,
	 * which is already evaluated.
	 */
	public Boolean getValue() {
		return this.value;
	}

	/**
	 * Propagate the event with transformed values.
	 */
	@Override
	protected void wrappedValueChanged(V oldValue, V newValue) {
		Boolean old = this.value;
		this.firePropertyChanged(VALUE, old, this.value = this.evaluate(newValue));
	}


	// ********** transformation **********

	/**
	 * Evaluate the specified value and return the result.
	 */
	protected Boolean evaluate(V v) {
		return Boolean.valueOf(this.predicate.evaluate(v));
	}

	/**
	 * We have listeners, transform the nested value and cache the result.
	 */
	@Override
	protected void engageModel() {
		super.engageModel();
		this.value = this.evaluate(this.valueModel.getValue());
	}

	/**
	 * We have no more listeners, clear the cached value.
	 */
	@Override
	protected void disengageModel() {
		this.value = null;
		super.disengageModel();
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.value);
	}
}

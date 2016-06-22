/*******************************************************************************
 * Copyright (c) 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.model.value;

import org.eclipse.jpt.common.utility.closure.Closure;
import org.eclipse.jpt.common.utility.internal.predicate.PredicateTools;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerTools;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * {@link PropertyValueModel Property value model} utility methods.
 */
public final class PropertyValueModelTools {


	// ********** pluggable PVMs **********

	/**
	 * Construct a property value model adapter for the specified adapter factory.
	 * @see PluggablePropertyValueModel
	 */
	public static <V> PropertyValueModel<V> propertyValueModel(PluggablePropertyValueModel.Adapter.Factory<V> adapterFactory) {
		return new PluggablePropertyValueModel<>(adapterFactory);
	}

	/**
	 * Construct a modifiable property value model adapter for the specified adapter factory.
	 * @see PluggableModifiablePropertyValueModel
	 */
	public static <V> ModifiablePropertyValueModel<V> modifiablePropertyValueModel(PluggableModifiablePropertyValueModel.Adapter.Factory<V> adapterFactory) {
		return new PluggableModifiablePropertyValueModel<>(adapterFactory);
	}

	/**
	 * Construct a <em>modifiable</em> property value model adapter for the specified
	 * property value model adapter adapter factory and closure.
	 * The specified closure is invoked when the model's value is set.
	 */
	public static <V> ModifiablePropertyValueModel<V> pluggableModifiablePropertyValueModel(AbstractPluggablePropertyValueModel.Adapter.Factory<V, ? extends AbstractPluggablePropertyValueModel.Adapter<V>> factory, Closure<V> setValueClosure) {
		return new PluggableModifiablePropertyValueModel<>(new PluggableModifiablePropertyValueModelAdapter.Factory<>(factory, setValueClosure));
	}


	// ********** Boolean adapters **********

	/**
	 * Construct a property value model adapter for the specified
	 * property value model that returns whether the property's value
	 * equals the specified value.
	 */
	public static PropertyValueModel<Boolean> valueEquals(PropertyValueModel<?> propertyModel, Object value) {
		return propertyValueModel(pluggablePropertyValueModelAdapterFactory(propertyModel, TransformerTools.adapt(PredicateTools.isEqual(value))));
	}


	// ********** PVM wrappers **********

	/**
	 * Construct a property value model that wraps the specified
	 * property value model and transforms its value with the specified
	 * transformer.
	 * @see PluggablePropertyValueModel
	 */
	public static <V1, V2> PropertyValueModel<V2> wrap(PropertyValueModel<? extends V1> propertyModel, Transformer<? super V1, V2> transformer) {
		return propertyValueModel(pluggablePropertyValueModelAdapterFactory(propertyModel, transformer));
	}

	/**
	 * Construct a pluggable property value model adapter factory for the specified
	 * property value model and transformer.
	 * @see PluggablePropertyValueModel
	 */
	public static <V1, V2> PluggablePropertyValueModel.Adapter.Factory<V2> pluggablePropertyValueModelAdapterFactory(PropertyValueModel<? extends V1> propertyModel, Transformer<? super V1, V2> transformer) {
		return new PropertyPluggablePropertyValueModelAdapter.Factory<>(propertyModel, transformer);
	}


	// ********** double PVMs **********

	/**
	 * Construct a double property value model for the specified
	 * <em>middle</em> property value model.
	 * @see AbstractDoublePropertyValueModel
	 */
	public static <V> PropertyValueModel<V> doubleWrap(PropertyValueModel<? extends PropertyValueModel<? extends V>> propertyModel) {
		return new DoublePropertyValueModel<>(propertyModel);
	}

	/**
	 * Construct a modifiable double property value model for the specified
	 * <em>middle</em> property value model.
	 * @see AbstractDoublePropertyValueModel
	 */
	public static <V> ModifiablePropertyValueModel<V> doubleWrapModifiable(PropertyValueModel<? extends ModifiablePropertyValueModel<V>> propertyModel) {
		return new DoubleModifiablePropertyValueModel<>(propertyModel);
	}


	// ********** suppressed constructor **********

	/**
	 * Suppress default constructor, ensuring non-instantiability.
	 */
	private PropertyValueModelTools() {
		super();
		throw new UnsupportedOperationException();
	}
}

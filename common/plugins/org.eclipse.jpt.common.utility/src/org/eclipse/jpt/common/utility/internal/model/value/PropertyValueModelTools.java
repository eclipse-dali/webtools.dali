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
import org.eclipse.jpt.common.utility.predicate.Predicate;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * {@link PropertyValueModel Property value model} utility methods.
 */
public final class PropertyValueModelTools {


	// ********** Boolean adapters **********

	/**
	 * Construct a property value model adapter for the specified
	 * property value model that returns whether the property's value
	 * is <code>null</code>.
	 */
	public static PropertyValueModel<Boolean> valueIsNull(PropertyValueModel<?> propertyModel) {
		return valueIsInSet(propertyModel, PredicateTools.isNull());
	}

	/**
	 * Construct a property value model adapter for the specified
	 * property value model that returns whether the property's value
	 * is <em>not</em> <code>null</code>.
	 */
	public static PropertyValueModel<Boolean> valueIsNotNull(PropertyValueModel<?> propertyModel) {
		return valueIsInSet(propertyModel, PredicateTools.isNotNull());
	}

	/**
	 * Construct a property value model adapter for the specified
	 * property value model that returns whether the property's value
	 * equals the specified value.
	 */
	public static PropertyValueModel<Boolean> valueEquals(PropertyValueModel<?> propertyModel, Object value) {
		return valueIsInSet(propertyModel, PredicateTools.isEqual(value));
	}

	/**
	 * Construct a property value model adapter for the specified
	 * property value model that returns whether the property's value
	 * does <em>not</em> equal the specified value.
	 */
	public static PropertyValueModel<Boolean> valueNotEquals(PropertyValueModel<?> propertyModel, Object value) {
		return valueIsInSet(propertyModel, PredicateTools.isNotEqual(value));
	}

	/**
	 * Construct a property value model adapter for the specified
	 * property value model that returns whether the property's value
	 * is <em>identical</em> to the specified value.
	 */
	public static PropertyValueModel<Boolean> valueIsIdentical(PropertyValueModel<?> propertyModel, Object value) {
		return valueIsInSet(propertyModel, PredicateTools.isIdentical(value));
	}

	/**
	 * Construct a property value model adapter for the specified
	 * property value model that returns whether the property's value
	 * is <em>not identical</em> to the specified value.
	 */
	public static PropertyValueModel<Boolean> valueIsNotIdentical(PropertyValueModel<?> propertyModel, Object value) {
		return valueIsInSet(propertyModel, PredicateTools.isNotIdentical(value));
	}

	/**
	 * Construct a property value model adapter for the specified
	 * property value model that returns whether the property's value
	 * is in the set defined by the specified predicate.
	 */
	public static <V> PropertyValueModel<Boolean> valueIsInSet(PropertyValueModel<? extends V> propertyModel, Predicate<? super V> predicate) {
		return transform(propertyModel, TransformerTools.adapt(predicate));
	}


	// ********** filtering wrappers **********

	/**
	 * Construct a property value model that wraps the specified
	 * property value model and filters its value with the specified
	 * filter. If the wrapped value passes the filter,
	 * the model simply returns it; otherwise it returns <code>null</code>.
	 * @see #filter(PropertyValueModel, Predicate, Object)
	 * @see PluggablePropertyValueModel
	 */
	public static <V> PropertyValueModel<V> filter(PropertyValueModel<? extends V> propertyModel, Predicate<? super V> filter) {
		return filter(propertyModel, filter, null);
	}

	/**
	 * Construct a property value model that wraps the specified
	 * property value model and filters its value with the specified
	 * filter. If the wrapped value passes the filter,
	 * the model simply returns it; otherwise it returns the specified
	 * default value.
	 * @see PluggablePropertyValueModel
	 */
	public static <V> PropertyValueModel<V> filter(PropertyValueModel<? extends V> propertyModel, Predicate<? super V> filter, V defaultValue) {
		return transform(propertyModel, TransformerTools.filteringTransformer(filter, defaultValue));
	}


	/**
	 * Construct a modifiable property value model that wraps the specified
	 * modifiable property value model and filters its value with the specified
	 * filters. If the wrapped value passes the "get" filter,
	 * the model simply returns it; otherwise it returns <code>null</code>.
	 * If a new value passes the "set" filter, the model passes it to the wrapped
	 * model; otherwise it sets the wrapped model's value to <code>null</code>.
	 * @see #filterModifiable(ModifiablePropertyValueModel, Predicate, Object, Predicate, Object)
	 * @see PluggablePropertyValueModel
	 */
	public static <V> ModifiablePropertyValueModel<V> filterModifiable(ModifiablePropertyValueModel<V> propertyModel, Predicate<? super V> getFilter, Predicate<? super V> setFilter) {
		return filterModifiable(propertyModel, getFilter, null, setFilter, null);
	}

	/**
	 * Construct a modifiable property value model that wraps the specified
	 * modifiable property value model and filters its value with the specified
	 * filters. If the wrapped value passes the "get" filter,
	 * the model simply returns it; otherwise it returns the specified
	 * default "get" value.
	 * If a new value passes the "set" filter, the model passes it to the wrapped
	 * model; otherwise it sets the wrapped model's value to the specified
	 * default "set" value.
	 * @see PluggablePropertyValueModel
	 */
	public static <V> ModifiablePropertyValueModel<V> filterModifiable(ModifiablePropertyValueModel<V> propertyModel, Predicate<? super V> getFilter, V defaultGetValue, Predicate<? super V> setFilter, V defaultSetValue) {
		return transform(propertyModel, TransformerTools.filteringTransformer(getFilter, defaultGetValue), TransformerTools.filteringTransformer(setFilter, defaultSetValue));
	}


	// ********** null check wrapper **********

	/**
	 * Construct a property value model that wraps the specified
	 * property value model and returns the specified null value
	 * if the specified model's value is <code>null</code>.
	 * @see PluggablePropertyValueModel
	 */
	public static <V> PropertyValueModel<V> nullCheck(PropertyValueModel<? extends V> propertyModel, V nullValue) {
		return transform(propertyModel, TransformerTools.nullCheck(nullValue));
	}


	// ********** transforming wrappers **********

	/**
	 * Construct a property value model that wraps the specified
	 * property value model and transforms its value with the specified
	 * transformer.
	 * @see PluggablePropertyValueModel
	 */
	public static <V1, V2> PropertyValueModel<V2> transform(PropertyValueModel<? extends V1> propertyModel, Transformer<? super V1, ? extends V2> transformer) {
		return propertyValueModel(pluggablePropertyValueModelAdapterFactory(propertyModel, transformer));
	}

	/**
	 * Construct a pluggable property value model adapter factory for the specified
	 * property value model and transformer.
	 * @see PluggablePropertyValueModel
	 */
	public static <V1, V2> PluggablePropertyValueModel.Adapter.Factory<V2> pluggablePropertyValueModelAdapterFactory(PropertyValueModel<? extends V1> propertyModel, Transformer<? super V1, ? extends V2> transformer) {
		return new PropertyPluggablePropertyValueModelAdapter.Factory<>(propertyModel, transformer);
	}

	/**
	 * Construct a property value model that wraps the specified
	 * property value model and transforms its value with the specified
	 * transformer.
	 * @see PluggablePropertyValueModel
	 */
	public static <V1, V2> ModifiablePropertyValueModel<V2> transform(ModifiablePropertyValueModel<V1> propertyModel, Transformer<? super V1, ? extends V2> getTransformer, Transformer<? super V2, ? extends V1> setTransformer) {
		return modifiablePropertyValueModel(pluggableModifiablePropertyValueModelAdapterFactory(propertyModel, getTransformer, setTransformer));
	}

	/**
	 * Construct a pluggable property value model adapter factory for the specified
	 * property value model and transformer.
	 * @see PluggablePropertyValueModel
	 */
	public static <V1, V2> PluggableModifiablePropertyValueModel.Adapter.Factory<V2> pluggableModifiablePropertyValueModelAdapterFactory(ModifiablePropertyValueModel<V1> propertyModel, Transformer<? super V1, ? extends V2> getTransformer, Transformer<? super V2, ? extends V1> setTransformer) {
		return new PropertyPluggableModifiablePropertyValueModelAdapter.Factory<>(propertyModel, getTransformer, setTransformer);
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
	public static <V> ModifiablePropertyValueModel<V> pluggableModifiablePropertyValueModel(BasePluggablePropertyValueModel.Adapter.Factory<V, ? extends BasePluggablePropertyValueModel.Adapter<V>> factory, Closure<V> setValueClosure) {
		return new PluggableModifiablePropertyValueModel<>(new PluggableModifiablePropertyValueModelAdapter.Factory<>(factory, setValueClosure));
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

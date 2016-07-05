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
		return valueIsInSet_(propertyModel, PredicateTools.isNull());
	}

	/**
	 * Construct a property value model adapter for the specified
	 * property value model that returns whether the property's value
	 * is <em>not</em> <code>null</code>.
	 */
	public static PropertyValueModel<Boolean> valueIsNotNull(PropertyValueModel<?> propertyModel) {
		return valueIsInSet_(propertyModel, PredicateTools.isNotNull());
	}

	/**
	 * Construct a property value model adapter for the specified
	 * property value model that returns whether the property's value
	 * equals the specified value.
	 */
	public static PropertyValueModel<Boolean> valueEquals(PropertyValueModel<?> propertyModel, Object value) {
		return valueIsInSet_(propertyModel, PredicateTools.isEqual(value));
	}

	/**
	 * Construct a property value model adapter for the specified
	 * property value model that returns whether the property's value
	 * does <em>not</em> equal the specified value.
	 */
	public static PropertyValueModel<Boolean> valueNotEquals(PropertyValueModel<?> propertyModel, Object value) {
		return valueIsInSet_(propertyModel, PredicateTools.isNotEqual(value));
	}

	/**
	 * Construct a property value model adapter for the specified
	 * property value model that returns whether the property's value
	 * is <em>identical</em> to the specified value.
	 */
	public static PropertyValueModel<Boolean> valueIsIdentical(PropertyValueModel<?> propertyModel, Object value) {
		return valueIsInSet_(propertyModel, PredicateTools.isIdentical(value));
	}

	/**
	 * Construct a property value model adapter for the specified
	 * property value model that returns whether the property's value
	 * is <em>not identical</em> to the specified value.
	 */
	public static PropertyValueModel<Boolean> valueIsNotIdentical(PropertyValueModel<?> propertyModel, Object value) {
		return valueIsInSet_(propertyModel, PredicateTools.isNotIdentical(value));
	}

	/**
	 * Construct a property value model adapter for the specified
	 * property value model that returns whether the property's value
	 * is in the set defined by the specified predicate.
	 * <p>
	 * <strong>NB:</strong> If specified model's value is <code>null</code>,
	 * the returned model's value will also be a <code>null</code>
	 * {@link Boolean}; and the value will never be passed to the specified
	 * predicate.
	 * @see #valueIsInSet_(PropertyValueModel, Predicate)
	 * @see #valueIsInSet(PropertyValueModel, Predicate, Boolean)
	 */
	public static <V> PropertyValueModel<Boolean> valueIsInSet(PropertyValueModel<? extends V> propertyModel, Predicate<? super V> predicate) {
		return valueIsInSet(propertyModel, predicate, null);
	}

	/**
	 * Construct a property value model adapter for the specified
	 * property value model that returns whether the property's value
	 * is in the set defined by the specified predicate.
	 * <p>
	 * <strong>NB:</strong> If specified model's value is <code>null</code>,
	 * the returned model's value will be the specified null result;
	 * and the value will never be passed to the specified predicate.
	 * @see #valueIsInSet(PropertyValueModel, Predicate)
	 * @see #valueIsInSet_(PropertyValueModel, Predicate)
	 */
	public static <V> PropertyValueModel<Boolean> valueIsInSet(PropertyValueModel<? extends V> propertyModel, Predicate<? super V> predicate, Boolean nullResult) {
		return transform_(propertyModel, TransformerTools.adapt(predicate, nullResult));
	}

	/**
	 * Construct a property value model adapter for the specified
	 * property value model that returns whether the property's value
	 * is in the set defined by the specified predicate.
	 * <p>
	 * <strong>NB:</strong> The specified predicate must be able to
	 * handle a <code>null</code> variable.
	 * @see #valueIsInSet(PropertyValueModel, Predicate)
	 * @see #valueIsInSet(PropertyValueModel, Predicate, Boolean)
	 */
	public static <V> PropertyValueModel<Boolean> valueIsInSet_(PropertyValueModel<? extends V> propertyModel, Predicate<? super V> predicate) {
		return transform_(propertyModel, TransformerTools.adapt(predicate));
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
		return transform_(propertyModel, TransformerTools.nullCheck(nullValue));
	}


	// ********** transforming wrappers **********

	/**
	 * Construct a property value model that wraps the specified
	 * property value model and transforms its value with the specified
	 * transformer.
	 * <p>
	 * <strong>NB:</strong> The specified transformer will never be passed a <code>null</code> input.
	 * Instead, a <code>null</code> input will be transformed into a <code>null</code> output.
	 * @see PluggablePropertyValueModel
	 */
	public static <V1, V2> PropertyValueModel<V2> transform(PropertyValueModel<? extends V1> propertyModel, Transformer<? super V1, ? extends V2> transformer) {
		return propertyValueModel(pluggablePropertyValueModelAdapterFactory(propertyModel, transformer));
	}

	/**
	 * Construct a property value model that wraps the specified
	 * property value model and transforms its value with the specified
	 * transformer.
	 * <p>
	 * <strong>NB:</strong> The specified transformer must be able to handle a <code>null</code> input.
	 * @see PluggablePropertyValueModel
	 */
	public static <V1, V2> PropertyValueModel<V2> transform_(PropertyValueModel<? extends V1> propertyModel, Transformer<? super V1, ? extends V2> transformer) {
		return propertyValueModel(pluggablePropertyValueModelAdapterFactory_(propertyModel, transformer));
	}

	/**
	 * Construct a modifiable property value model that wraps the specified
	 * property value model and transforms its value with the specified
	 * transformer. The specified closure is invoked when the model's value is set.
	 * <p>
	 * <strong>NB:</strong> The specified transformer will never be passed a <code>null</code> input.
	 * Instead, a <code>null</code> input will be transformed into a <code>null</code> output.
	 * @see PluggablePropertyValueModel
	 */
	public static <V1, V2> ModifiablePropertyValueModel<V2> transform(PropertyValueModel<? extends V1> propertyModel, Transformer<? super V1, ? extends V2> transformer, Closure<? super V2> setValueClosure) {
		return pluggableModifiablePropertyValueModel(pluggablePropertyValueModelAdapterFactory(propertyModel, transformer), setValueClosure);
	}

	/**
	 * Construct a modifiable property value model that wraps the specified
	 * property value model and transforms its value with the specified
	 * transformer. The specified closure is invoked when the model's value is set.
	 * <p>
	 * <strong>NB:</strong> The specified transformer must be able to handle a <code>null</code> input.
	 * @see PluggablePropertyValueModel
	 */
	public static <V1, V2> ModifiablePropertyValueModel<V2> transform_(PropertyValueModel<? extends V1> propertyModel, Transformer<? super V1, ? extends V2> transformer, Closure<? super V2> setValueClosure) {
		return pluggableModifiablePropertyValueModel(pluggablePropertyValueModelAdapterFactory_(propertyModel, transformer), setValueClosure);
	}

	/**
	 * Construct a pluggable property value model adapter factory for the specified
	 * property value model and transformer.
	 * <p>
	 * <strong>NB:</strong> The specified transformer will never be passed a <code>null</code> input.
	 * Instead, a <code>null</code> input will be transformed into a <code>null</code> output.
	 * @see PluggablePropertyValueModel
	 */
	public static <V1, V2> PluggablePropertyValueModel.Adapter.Factory<V2> pluggablePropertyValueModelAdapterFactory(PropertyValueModel<? extends V1> propertyModel, Transformer<? super V1, ? extends V2> transformer) {
		return pluggablePropertyValueModelAdapterFactory_(propertyModel, TransformerTools.nullCheck(transformer));
	}

	/**
	 * Construct a pluggable property value model adapter factory for the specified
	 * property value model and transformer.
	 * <p>
	 * <strong>NB:</strong> The specified transformer must be able to handle a <code>null</code> input.
	 * @see PluggablePropertyValueModel
	 */
	public static <V1, V2> PluggablePropertyValueModel.Adapter.Factory<V2> pluggablePropertyValueModelAdapterFactory_(PropertyValueModel<? extends V1> propertyModel, Transformer<? super V1, ? extends V2> transformer) {
		return new PropertyPluggablePropertyValueModelAdapter.Factory<>(propertyModel, transformer);
	}

	/**
	 * Construct a property value model that wraps the specified
	 * property value model and transforms its value with the specified
	 * transformer.
	 * <p>
	 * <strong>NB:</strong> The specified transformers will never be passed a <code>null</code> input.
	 * Instead, a <code>null</code> input will be transformed into a <code>null</code> output.
	 * @see PluggablePropertyValueModel
	 */
	public static <V1, V2> ModifiablePropertyValueModel<V2> transform(ModifiablePropertyValueModel<V1> propertyModel, Transformer<? super V1, ? extends V2> getTransformer, Transformer<? super V2, ? extends V1> setTransformer) {
		return modifiablePropertyValueModel(pluggableModifiablePropertyValueModelAdapterFactory(propertyModel, getTransformer, setTransformer));
	}

	/**
	 * Construct a property value model that wraps the specified
	 * property value model and transforms its value with the specified
	 * transformer.
	 * <p>
	 * <strong>NB:</strong> The specified transformers must be able to handle a <code>null</code> input.
	 * @see PluggablePropertyValueModel
	 */
	public static <V1, V2> ModifiablePropertyValueModel<V2> transform_(ModifiablePropertyValueModel<V1> propertyModel, Transformer<? super V1, ? extends V2> getTransformer, Transformer<? super V2, ? extends V1> setTransformer) {
		return modifiablePropertyValueModel(pluggableModifiablePropertyValueModelAdapterFactory_(propertyModel, getTransformer, setTransformer));
	}

	/**
	 * Construct a pluggable property value model adapter factory for the specified
	 * property value model and transformer.
	 * <p>
	 * <strong>NB:</strong> The specified transformers will never be passed a <code>null</code> input.
	 * Instead, a <code>null</code> input will be transformed into a <code>null</code> output.
	 * @see PluggablePropertyValueModel
	 */
	public static <V1, V2> PluggableModifiablePropertyValueModel.Adapter.Factory<V2> pluggableModifiablePropertyValueModelAdapterFactory(ModifiablePropertyValueModel<V1> propertyModel, Transformer<? super V1, ? extends V2> getTransformer, Transformer<? super V2, ? extends V1> setTransformer) {
		return pluggableModifiablePropertyValueModelAdapterFactory_(propertyModel, TransformerTools.nullCheck(getTransformer), TransformerTools.nullCheck(setTransformer));
	}

	/**
	 * Construct a pluggable property value model adapter factory for the specified
	 * property value model and transformer.
	 * <p>
	 * <strong>NB:</strong> The specified transformers must be able to handle a <code>null</code> input.
	 * @see PluggablePropertyValueModel
	 */
	public static <V1, V2> PluggableModifiablePropertyValueModel.Adapter.Factory<V2> pluggableModifiablePropertyValueModelAdapterFactory_(ModifiablePropertyValueModel<V1> propertyModel, Transformer<? super V1, ? extends V2> getTransformer, Transformer<? super V2, ? extends V1> setTransformer) {
		return new PropertyPluggableModifiablePropertyValueModelAdapter.Factory<>(propertyModel, getTransformer, setTransformer);
	}


	// ********** double PVMs **********

	/**
	 * Construct a double property value model for the specified <em>outer</em> property value model.
	 */
	public static <V> PropertyValueModel<V> doubleWrap(PropertyValueModel<? extends PropertyValueModel<? extends V>> outerModel) {
		return propertyValueModel(doublePropertyValueModelAdapterFactory(outerModel));
	}

	/**
	 * Construct a double property value model adapter factory for the specified <em>outer</em> property value model.
	 */
	public static <V> PluggablePropertyValueModel.Adapter.Factory<V> doublePropertyValueModelAdapterFactory(PropertyValueModel<? extends PropertyValueModel<? extends V>> outerModel) {
		return new DoublePropertyValueModelAdapter.Factory<>(outerModel);
	}

	/**
	 * Construct a modifiable double property value model
	 * for the specified <em>outer</em> property value model.
	 */
	public static <V> ModifiablePropertyValueModel<V> doubleWrapModifiable(PropertyValueModel<? extends ModifiablePropertyValueModel<V>> outerModel) {
		return modifiablePropertyValueModel(doubleModifiablePropertyValueModelAdapterFactory(outerModel));
	}

	/**
	 * Construct a modifiable double property value model adapter factory
	 * for the specified <em>outer</em> property value model.
	 */
	public static <V> PluggableModifiablePropertyValueModel.Adapter.Factory<V> doubleModifiablePropertyValueModelAdapterFactory(PropertyValueModel<? extends ModifiablePropertyValueModel<V>> outerModel) {
		return new DoubleModifiablePropertyValueModelAdapter.Factory<>(outerModel);
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
	public static <V> ModifiablePropertyValueModel<V> pluggableModifiablePropertyValueModel(BasePluggablePropertyValueModel.Adapter.Factory<V, ? extends BasePluggablePropertyValueModel.Adapter<V>> factory, Closure<? super V> setValueClosure) {
		return new PluggableModifiablePropertyValueModel<>(new PluggableModifiablePropertyValueModelAdapter.Factory<>(factory, setValueClosure));
	}


	// ********** value transformers **********

	/**
	 * Return a transformer that converts a property value model to its value
	 * but first checks whether the property value model passed to it is <code>null</code>.
	 * If the property value model is <code>null</code>, the transformer returns
	 * <code>null</code>.
	 * @see PropertyValueModel#VALUE_TRANSFORMER
	 */
	public static <V, PVM extends PropertyValueModel<? extends V>> Transformer<PVM, V> nullCheckValueTransformer() {
		return nullCheckValueTransformer(null);
	}

	/**
	 * Return a transformer that converts a property value model to its value
	 * but first checks whether the property value model passed to it is <code>null</code>.
	 * If the property value model is <code>null</code>, the transformer returns
	 * the specified null value.
	 * @see PropertyValueModel#VALUE_TRANSFORMER
	 */
	public static <V, PVM extends PropertyValueModel<? extends V>> Transformer<PVM, V> nullCheckValueTransformer(V nullValue) {
		return TransformerTools.nullCheck(valueTransformer(), nullValue);
	}

	/**
	 * Return a transformer that converts a property value model to its value.
	 * If the property value model is <code>null</code>, the transformer throws
	 * a {@link NullPointerException}.
	 * @see PropertyValueModel#VALUE_TRANSFORMER
	 */
	@SuppressWarnings("unchecked")
	public static <V, PVM extends PropertyValueModel<? extends V>> Transformer<PVM, V> valueTransformer() {
		return PropertyValueModel.VALUE_TRANSFORMER;
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

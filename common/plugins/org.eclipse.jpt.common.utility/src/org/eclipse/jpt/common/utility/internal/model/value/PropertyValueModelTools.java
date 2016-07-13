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

import java.util.Collection;
import org.eclipse.jpt.common.utility.closure.Closure;
import org.eclipse.jpt.common.utility.internal.predicate.PredicateTools;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerTools;
import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;
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
	 * 
	 * @see #valueIsNotNull(PropertyValueModel)
	 */
	public static PropertyValueModel<Boolean> valueIsNull(PropertyValueModel<?> propertyModel) {
		return valueIsInSet_(propertyModel, PredicateTools.isNull());
	}

	/**
	 * Construct a property value model adapter for the specified
	 * property value model that returns whether the property's value
	 * is <em>not</em> <code>null</code>.
	 * 
	 * @see #valueIsNull(PropertyValueModel)
	 */
	public static PropertyValueModel<Boolean> valueIsNotNull(PropertyValueModel<?> propertyModel) {
		return valueIsInSet_(propertyModel, PredicateTools.isNotNull());
	}

	/**
	 * Construct a property value model adapter for the specified
	 * property value model that returns whether the property's value
	 * equals the specified value.
	 * <p>
	 * <strong>NB:</strong> If specified model's value is <code>null</code>,
	 * the returned model's value will be <em>true</em> if the specified value is
	 * also <code>null</code>.
	 * 
	 * @see #valueNotEquals(PropertyValueModel, Object)
	 * @see #valueEquals_(PropertyValueModel, Object)
	 */
	public static PropertyValueModel<Boolean> valueEquals(PropertyValueModel<?> propertyModel, Object value) {
		return valueIsInSet_(propertyModel, PredicateTools.isEqual(value));
	}

	/**
	 * Construct a property value model adapter for the specified
	 * property value model that returns whether the property's value
	 * equals the specified value.
	 * <p>
	 * <strong>NB:</strong> If specified model's value is <code>null</code>,
	 * the returned model's value will also be a <code>null</code>
	 * {@link Boolean}.
	 * 
	 * @see #valueNotEquals_(PropertyValueModel, Object)
	 * @see #valueEquals(PropertyValueModel, Object)
	 */
	public static PropertyValueModel<Boolean> valueEquals_(PropertyValueModel<?> propertyModel, Object value) {
		return valueIsInSet(propertyModel, PredicateTools.isEqual(value));
	}

	/**
	 * Construct a property value model adapter for the specified
	 * property value model that returns whether the property's value
	 * does <em>not</em> equal the specified value.
	 * <p>
	 * <strong>NB:</strong> If specified model's value is <code>null</code>,
	 * the returned model's value will be <em>false</em> if the specified value is
	 * also <code>null</code>.
	 * 
	 * @see #valueNotEquals_(PropertyValueModel, Object)
	 * @see #valueEquals(PropertyValueModel, Object)
	 */
	public static PropertyValueModel<Boolean> valueNotEquals(PropertyValueModel<?> propertyModel, Object value) {
		return valueIsInSet_(propertyModel, PredicateTools.isNotEqual(value));
	}

	/**
	 * Construct a property value model adapter for the specified
	 * property value model that returns whether the property's value
	 * does <em>not</em> equal the specified value.
	 * <p>
	 * <strong>NB:</strong> If specified model's value is <code>null</code>,
	 * the returned model's value will also be a <code>null</code>
	 * {@link Boolean}.
	 * 
	 * @see #valueNotEquals(PropertyValueModel, Object)
	 * @see #valueEquals_(PropertyValueModel, Object)
	 */
	public static PropertyValueModel<Boolean> valueNotEquals_(PropertyValueModel<?> propertyModel, Object value) {
		return valueIsInSet_(propertyModel, PredicateTools.isNotEqual(value));
	}

	/**
	 * Construct a property value model adapter for the specified
	 * property value model that returns whether the property's value
	 * is <em>identical</em> to the specified value.
	 * <p>
	 * <strong>NB:</strong> If specified model's value is <code>null</code>,
	 * the returned model's value will be <em>true</em> if the specified value is
	 * also <code>null</code>.
	 * 
	 * @see #valueIsIdentical_(PropertyValueModel, Object)
	 * @see #valueIsNotIdentical(PropertyValueModel, Object)
	 */
	public static PropertyValueModel<Boolean> valueIsIdentical(PropertyValueModel<?> propertyModel, Object value) {
		return valueIsInSet_(propertyModel, PredicateTools.isIdentical(value));
	}

	/**
	 * Construct a property value model adapter for the specified
	 * property value model that returns whether the property's value
	 * is <em>identical</em> to the specified value.
	 * <p>
	 * <strong>NB:</strong> If specified model's value is <code>null</code>,
	 * the returned model's value will also be a <code>null</code>
	 * {@link Boolean}.
	 * 
	 * @see #valueIsIdentical(PropertyValueModel, Object)
	 * @see #valueIsNotIdentical_(PropertyValueModel, Object)
	 */
	public static PropertyValueModel<Boolean> valueIsIdentical_(PropertyValueModel<?> propertyModel, Object value) {
		return valueIsInSet(propertyModel, PredicateTools.isIdentical(value));
	}

	/**
	 * Construct a property value model adapter for the specified
	 * property value model that returns whether the property's value
	 * is <em>not identical</em> to the specified value.
	 * <p>
	 * <strong>NB:</strong> If specified model's value is <code>null</code>,
	 * the returned model's value will be <em>false</em> if the specified value is
	 * also <code>null</code>.
	 * 
	 * @see #valueIsNotIdentical_(PropertyValueModel, Object)
	 * @see #valueIsIdentical(PropertyValueModel, Object)
	 */
	public static PropertyValueModel<Boolean> valueIsNotIdentical(PropertyValueModel<?> propertyModel, Object value) {
		return valueIsInSet_(propertyModel, PredicateTools.isNotIdentical(value));
	}

	/**
	 * Construct a property value model adapter for the specified
	 * property value model that returns whether the property's value
	 * is <em>not identical</em> to the specified value.
	 * <p>
	 * <strong>NB:</strong> If specified model's value is <code>null</code>,
	 * the returned model's value will also be a <code>null</code>
	 * {@link Boolean}.
	 * 
	 * @see #valueIsNotIdentical(PropertyValueModel, Object)
	 * @see #valueIsIdentical_(PropertyValueModel, Object)
	 */
	public static PropertyValueModel<Boolean> valueIsNotIdentical_(PropertyValueModel<?> propertyModel, Object value) {
		return valueIsInSet(propertyModel, PredicateTools.isNotIdentical(value));
	}

	/**
	 * Construct a property value model adapter for the specified
	 * property value model that returns whether the property's value
	 * is in the set defined by the specified predicate.
	 * <p>
	 * <strong>NB:</strong> If specified model's value is <code>null</code>,
	 * the returned model's value will also be a <code>null</code>
	 * {@link Boolean}; and the value will <em>never</em> be passed to the specified
	 * predicate.
	 * 
	 * @see #valueIsInSet_(PropertyValueModel, Predicate)
	 * @see #valueIsInSet(PropertyValueModel, Predicate, boolean)
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
	 * and the value will <em>never</em> be passed to the specified predicate.
	 * 
	 * @see #valueIsInSet(PropertyValueModel, Predicate)
	 * @see #valueIsInSet_(PropertyValueModel, Predicate)
	 * @see #valueIsInSet(PropertyValueModel, Predicate, Boolean)
	 */
	public static <V> PropertyValueModel<Boolean> valueIsInSet(PropertyValueModel<? extends V> propertyModel, Predicate<? super V> predicate, boolean nullResult) {
		return valueIsInSet(propertyModel, predicate, Boolean.valueOf(nullResult));
	}

	/**
	 * Construct a property value model adapter for the specified
	 * property value model that returns whether the property's value
	 * is in the set defined by the specified predicate.
	 * <p>
	 * <strong>NB:</strong> If specified model's value is <code>null</code>,
	 * the returned model's value will be the specified null result;
	 * and the value will <em>never</em> be passed to the specified predicate.
	 * 
	 * @see #valueIsInSet(PropertyValueModel, Predicate)
	 * @see #valueIsInSet_(PropertyValueModel, Predicate)
	 * @see #valueIsInSet(PropertyValueModel, Predicate, boolean)
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
	 * 
	 * @see #valueIsInSet(PropertyValueModel, Predicate)
	 * @see #valueIsInSet(PropertyValueModel, Predicate, boolean)
	 * @see #valueIsInSet(PropertyValueModel, Predicate, Boolean)
	 */
	public static <V> PropertyValueModel<Boolean> valueIsInSet_(PropertyValueModel<? extends V> propertyModel, Predicate<? super V> predicate) {
		return transform_(propertyModel, TransformerTools.adapt(predicate));
	}


	// ********** Composite boolean adapters **********

	/**
	 * Construct a boolean property value model that is a composite AND of the
	 * specified boolean property value models; i.e. the model's value is {@link Boolean#TRUE}
	 * if all the contained models' values are {@link Boolean#TRUE},
	 * otherwise its value is {@link Boolean#FALSE}.
	 * The model's default value, when it contains no nested models, is {@link Boolean#TRUE}.
	 */
	@SafeVarargs
	public static PropertyValueModel<Boolean> and(PropertyValueModel<Boolean>... models) {
		return CollectionValueModelTools.compositePropertyValueModel(AND_TRANSFORMER, models);
	}

	/**
	 * Construct a boolean property value model that is a composite AND of the
	 * specified boolean property value models; i.e. the model's value is {@link Boolean#TRUE}
	 * if all the contained models' values are {@link Boolean#TRUE},
	 * otherwise its value is {@link Boolean#FALSE}.
	 * The model's default value, when it contains no nested models, is {@link Boolean#TRUE}.
	 */
	public static PropertyValueModel<Boolean> and(Collection<? extends PropertyValueModel<Boolean>> models) {
		return CollectionValueModelTools.compositePropertyValueModel(models, AND_TRANSFORMER);
	}

	/**
	 * @see AndTransformer
	 */
	public static final Transformer<Collection<Boolean>, Boolean> AND_TRANSFORMER = new AndTransformer();

	/**
	 * A transformer that transforms a collection of {@link Boolean}s into a single
	 * {@link Boolean} by ANDing them together. Its default value is {@link Boolean#TRUE}.
	 * @see CollectionValueModelTools#and(CollectionValueModel)
	 */
	public static final class AndTransformer
		extends TransformerAdapter<Collection<Boolean>, Boolean>
	{
		@Override
		public Boolean transform(Collection<Boolean> booleans) {
			for (Boolean b : booleans) {
				if ( ! b.booleanValue()) {
					return Boolean.FALSE;
				}
			}
			return Boolean.TRUE;
		}
	}

	/**
	 * Construct a boolean property value model that is a composite OR of the
	 * specified boolean property value models; i.e. the model's value is {@link Boolean#FALSE}
	 * if all the contained models' values are {@link Boolean#FALSE},
	 * otherwise its value is {@link Boolean#TRUE}.
	 * The model's default value, when it contains no nested models, is {@link Boolean#FALSE}.
	 */
	@SafeVarargs
	public static PropertyValueModel<Boolean> or(PropertyValueModel<Boolean>... models) {
		return CollectionValueModelTools.compositePropertyValueModel(OR_TRANSFORMER, models);
	}

	/**
	 * Construct a boolean property value model that is a composite OR of the
	 * specified boolean property value models; i.e. the model's value is {@link Boolean#FALSE}
	 * if all the contained models' values are {@link Boolean#FALSE},
	 * otherwise its value is {@link Boolean#TRUE}.
	 * The model's default value, when it contains no nested models, is {@link Boolean#FALSE}.
	 */
	public static PropertyValueModel<Boolean> or(Collection<? extends PropertyValueModel<Boolean>> models) {
		return CollectionValueModelTools.compositePropertyValueModel(models, OR_TRANSFORMER);
	}

	/**
	 * @see OrTransformer
	 */
	public static final Transformer<Collection<Boolean>, Boolean> OR_TRANSFORMER = new OrTransformer();

	/**
	 * A transformer that transforms a collection of {@link Boolean}s into a single
	 * {@link Boolean} by ORing them together. Its default value is {@link Boolean#FALSE}.
	 * @see CollectionValueModelTools#or(CollectionValueModel)
	 */
	public static final class OrTransformer
		extends TransformerAdapter<Collection<Boolean>, Boolean>
	{
		@Override
		public Boolean transform(Collection<Boolean> booleans) {
			for (Boolean b : booleans) {
				if (b.booleanValue()) {
					return Boolean.TRUE;
				}
			}
			return Boolean.FALSE;
		}
	}


	// ********** filtering wrappers **********

	/**
	 * Construct a property value model that filters the specified
	 * property value model to return the wrapped value only if it is an instance
	 * of the specified class. If the wrapped value is <em>not</em> an instance
	 * of the specified class, it will return <code>null</code>.
	 * <p>
	 * <strong>NB:</strong> If the wrapped value is <code>null</code>,
	 * the model returns a <code>null</code> value.
	 * 
	 * @see #filter(PropertyValueModel, Class, Object)
	 * @see PredicateTools#instanceOf(Class)
	 */
	public static <V> PropertyValueModel<V> filter(PropertyValueModel<?> propertyModel, Class<V> clazz) {
		return filter(propertyModel, clazz, null);
	}

	/**
	 * Construct a property value model that filters the specified
	 * property value model to return the wrapped value only if it is an instance
	 * of the specified class. If the wrapped value is <em>not</em> an instance
	 * of the specified class, it will return the specified default value.
	 * <p>
	 * <strong>NB:</strong> If the wrapped value is <code>null</code>,
	 * the model returns the specified default value.
	 * 
	 * @see #filter(PropertyValueModel, Class)
	 * @see PredicateTools#instanceOf(Class)
	 */
	public static <V> PropertyValueModel<V> filter(PropertyValueModel<?> propertyModel, Class<V> clazz, V defaultValue) {
		return transform(propertyModel, TransformerTools.cast(TransformerTools.filteringTransformer(PredicateTools.instanceOf(clazz), defaultValue)));
	}

	/**
	 * Construct a property value model that wraps the specified
	 * property value model and filters its value with the specified
	 * filter. If the wrapped value passes the filter,
	 * the model simply returns it; otherwise it returns <code>null</code>.
	 * <p>
	 * <strong>NB:</strong> The specified filter will <em>never</em> be passed
	 * a <code>null</code> variable.
	 * Instead, if the wrapped value is <code>null</code>, the model returns a
	 * <code>null</code> value.
	 * 
	 * @see #filter(PropertyValueModel, Predicate, Object)
	 * @see #filter_(PropertyValueModel, Predicate)
	 * @see PluggablePropertyValueModel
	 */
	public static <V> PropertyValueModel<V> filter(PropertyValueModel<? extends V> propertyModel, Predicate<? super V> filter) {
		return filter(propertyModel, filter, null);
	}

	/**
	 * Construct a property value model that wraps the specified
	 * property value model and filters its value with the specified
	 * filter. If the wrapped value passes the filter,
	 * the model simply returns it; otherwise it returns <code>null</code>.
	 * <p>
	 * <strong>NB:</strong> The specified filter must be able to handle
	 * a <code>null</code> variable.
	 * 
	 * @see #filter(PropertyValueModel, Predicate)
	 * @see #filter_(PropertyValueModel, Predicate, Object)
	 * @see PluggablePropertyValueModel
	 */
	public static <V> PropertyValueModel<V> filter_(PropertyValueModel<? extends V> propertyModel, Predicate<? super V> filter) {
		return filter_(propertyModel, filter, null);
	}

	/**
	 * Construct a property value model that wraps the specified
	 * property value model and filters its value with the specified
	 * filter. If the wrapped value passes the filter,
	 * the model simply returns it; otherwise it returns the specified
	 * default value.
	 * <p>
	 * <strong>NB:</strong> The specified filter will <em>never</em> be passed
	 * a <code>null</code> variable.
	 * Instead, if the wrapped value is <code>null</code>, the model returns the specified
	 * default value.
	 * 
	 * @see #filter(PropertyValueModel, Predicate)
	 * @see #filter_(PropertyValueModel, Predicate, Object)
	 * @see PluggablePropertyValueModel
	 */
	public static <V> PropertyValueModel<V> filter(PropertyValueModel<? extends V> propertyModel, Predicate<? super V> filter, V defaultValue) {
		return filter_(propertyModel, PredicateTools.nullCheck(filter), defaultValue);
	}

	/**
	 * Construct a property value model that wraps the specified
	 * property value model and filters its value with the specified
	 * filter. If the wrapped value passes the filter,
	 * the model simply returns it; otherwise it returns the specified
	 * default value.
	 * <p>
	 * <strong>NB:</strong> The specified filter must be able to handle
	 * a <code>null</code> variable.
	 * 
	 * @see #filter(PropertyValueModel, Predicate, Object)
	 * @see #filter_(PropertyValueModel, Predicate)
	 * @see PluggablePropertyValueModel
	 */
	public static <V> PropertyValueModel<V> filter_(PropertyValueModel<? extends V> propertyModel, Predicate<? super V> filter, V defaultValue) {
		return transform(propertyModel, TransformerTools.filteringTransformer(filter, defaultValue));
	}

	/**
	 * Construct a modifiable property value model that wraps the specified
	 * modifiable property value model and filters its value with the specified
	 * filters. If the wrapped value passes the "get" filter,
	 * the model simply returns it; otherwise it returns <code>null</code>.
	 * If a new value passes the "set" filter, the model passes it to the wrapped
	 * model; otherwise it sets the wrapped model's value to <code>null</code>.
	 * 
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
	 * 
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
	 * 
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
	 * <strong>NB:</strong> The specified transformer will <em>never</em> be passed a <code>null</code> input.
	 * Instead, a <code>null</code> input will be transformed into a <code>null</code> output.
	 * 
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
	 * 
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
	 * <strong>NB:</strong> The specified transformer will <em>never</em> be passed a <code>null</code> input.
	 * Instead, a <code>null</code> input will be transformed into a <code>null</code> output.
	 * 
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
	 * 
	 * @see PluggablePropertyValueModel
	 */
	public static <V1, V2> ModifiablePropertyValueModel<V2> transform_(PropertyValueModel<? extends V1> propertyModel, Transformer<? super V1, ? extends V2> transformer, Closure<? super V2> setValueClosure) {
		return pluggableModifiablePropertyValueModel(pluggablePropertyValueModelAdapterFactory_(propertyModel, transformer), setValueClosure);
	}

	/**
	 * Construct a pluggable property value model adapter factory for the specified
	 * property value model and transformer.
	 * <p>
	 * <strong>NB:</strong> The specified transformer will <em>never</em> be passed a <code>null</code> input.
	 * Instead, a <code>null</code> input will be transformed into a <code>null</code> output.
	 * 
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
	 * 
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
	 * <strong>NB:</strong> The specified transformers will <em>never</em> be passed a <code>null</code> input.
	 * Instead, a <code>null</code> input will be transformed into a <code>null</code> output.
	 * 
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
	 * 
	 * @see PluggablePropertyValueModel
	 */
	public static <V1, V2> ModifiablePropertyValueModel<V2> transform_(ModifiablePropertyValueModel<V1> propertyModel, Transformer<? super V1, ? extends V2> getTransformer, Transformer<? super V2, ? extends V1> setTransformer) {
		return modifiablePropertyValueModel(pluggableModifiablePropertyValueModelAdapterFactory_(propertyModel, getTransformer, setTransformer));
	}

	/**
	 * Construct a pluggable property value model adapter factory for the specified
	 * property value model and transformer.
	 * <p>
	 * <strong>NB:</strong> The specified transformers will <em>never</em> be passed a <code>null</code> input.
	 * Instead, a <code>null</code> input will be transformed into a <code>null</code> output.
	 * 
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
	 * 
	 * @see PluggablePropertyValueModel
	 */
	public static <V1, V2> PluggableModifiablePropertyValueModel.Adapter.Factory<V2> pluggableModifiablePropertyValueModelAdapterFactory_(ModifiablePropertyValueModel<V1> propertyModel, Transformer<? super V1, ? extends V2> getTransformer, Transformer<? super V2, ? extends V1> setTransformer) {
		return new PropertyPluggableModifiablePropertyValueModelAdapter.Factory<>(propertyModel, getTransformer, setTransformer);
	}


	// ********** compound PVMs **********

	/**
	 * Construct a compound property value model for the specified <em>outer</em> property value model.
	 */
	public static <V> PropertyValueModel<V> compound(PropertyValueModel<? extends PropertyValueModel<? extends V>> outerModel) {
		return propertyValueModel(compoundPropertyValueModelAdapterFactory(outerModel));
	}

	/**
	 * Construct a compound property value model adapter factory for the specified <em>outer</em> property value model.
	 */
	public static <V> PluggablePropertyValueModel.Adapter.Factory<V> compoundPropertyValueModelAdapterFactory(PropertyValueModel<? extends PropertyValueModel<? extends V>> outerModel) {
		return new CompoundPropertyValueModelAdapter.Factory<>(outerModel);
	}

	/**
	 * Construct a modifiable compound property value model
	 * for the specified <em>outer</em> property value model.
	 */
	public static <V> ModifiablePropertyValueModel<V> compoundModifiable(PropertyValueModel<? extends ModifiablePropertyValueModel<V>> outerModel) {
		return modifiablePropertyValueModel(compoundModifiablePropertyValueModelAdapterFactory(outerModel));
	}

	/**
	 * Construct a modifiable compound property value model adapter factory
	 * for the specified <em>outer</em> property value model.
	 */
	public static <V> PluggableModifiablePropertyValueModel.Adapter.Factory<V> compoundModifiablePropertyValueModelAdapterFactory(PropertyValueModel<? extends ModifiablePropertyValueModel<V>> outerModel) {
		return new CompoundModifiablePropertyValueModelAdapter.Factory<>(outerModel);
	}


	// ********** pluggable PVMs **********

	/**
	 * Construct a property value model adapter for the specified adapter factory.
	 * 
	 * @see PluggablePropertyValueModel
	 */
	public static <V> PropertyValueModel<V> propertyValueModel(PluggablePropertyValueModel.Adapter.Factory<V> adapterFactory) {
		return new PluggablePropertyValueModel<>(adapterFactory);
	}

	/**
	 * Construct a modifiable property value model adapter for the specified adapter factory.
	 * 
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


	// ********** factories **********

	/**
	 * Construct property value model that can be used for
	 * returning a static value, but still allows listeners to be added.
	 * Listeners will <em>never</em> be notified of any changes, because there should be none.
	 */
	public static <V> PropertyValueModel<V> staticPropertyValueModel(V value) {
		return new StaticPropertyValueModel<>(value);
	}


	// ********** value transformers **********

	/**
	 * Return a transformer that converts a property value model to its value
	 * but first checks whether the property value model passed to it is <code>null</code>.
	 * If the property value model is <code>null</code>, the transformer returns
	 * <code>null</code>.
	 * 
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
	 * 
	 * @see PropertyValueModel#VALUE_TRANSFORMER
	 */
	public static <V, PVM extends PropertyValueModel<? extends V>> Transformer<PVM, V> nullCheckValueTransformer(V nullValue) {
		return TransformerTools.nullCheck(valueTransformer(), nullValue);
	}

	/**
	 * Return a transformer that converts a property value model to its value.
	 * If the property value model is <code>null</code>, the transformer throws
	 * a {@link NullPointerException}.
	 * 
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

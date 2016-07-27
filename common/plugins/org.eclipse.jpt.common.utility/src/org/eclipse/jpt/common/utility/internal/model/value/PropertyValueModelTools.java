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
import org.eclipse.jpt.common.utility.Association;
import org.eclipse.jpt.common.utility.closure.BiClosure;
import org.eclipse.jpt.common.utility.closure.Closure;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.SimpleAssociation;
import org.eclipse.jpt.common.utility.internal.closure.ClosureTools;
import org.eclipse.jpt.common.utility.internal.predicate.PredicateTools;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerTools;
import org.eclipse.jpt.common.utility.model.Model;
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
		return valueAffirms_(propertyModel, PredicateTools.isNull());
	}

	/**
	 * Construct a property value model adapter for the specified
	 * property value model that returns whether the property's value
	 * is <em>not</em> <code>null</code>.
	 * 
	 * @see #valueIsNull(PropertyValueModel)
	 */
	public static PropertyValueModel<Boolean> valueIsNotNull(PropertyValueModel<?> propertyModel) {
		return valueAffirms_(propertyModel, PredicateTools.isNotNull());
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
		return valueAffirms_(propertyModel, PredicateTools.isEqual(value));
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
		return valueAffirms(propertyModel, PredicateTools.isEqual(value));
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
		return valueAffirms_(propertyModel, PredicateTools.isNotEqual(value));
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
		return valueAffirms(propertyModel, PredicateTools.isNotEqual(value));
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
		return valueAffirms_(propertyModel, PredicateTools.isIdentical(value));
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
		return valueAffirms(propertyModel, PredicateTools.isIdentical(value));
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
		return valueAffirms_(propertyModel, PredicateTools.isNotIdentical(value));
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
		return valueAffirms(propertyModel, PredicateTools.isNotIdentical(value));
	}

	/**
	 * Construct a property value model adapter for the specified
	 * property value model that returns whether the property's value
	 * affirms the specified predicate.
	 * <p>
	 * <strong>NB:</strong> If specified model's value is <code>null</code>,
	 * the returned model's value will also be a <code>null</code>
	 * {@link Boolean}; and the value will <em>never</em> be passed to the specified
	 * predicate.
	 * 
	 * @see #valueAffirms_(PropertyValueModel, Predicate)
	 * @see #valueAffirms(PropertyValueModel, Predicate, boolean)
	 * @see #valueAffirms(PropertyValueModel, Predicate, Boolean)
	 */
	public static <V> PropertyValueModel<Boolean> valueAffirms(PropertyValueModel<? extends V> propertyModel, Predicate<? super V> predicate) {
		return valueAffirms(propertyModel, predicate, null);
	}

	/**
	 * Construct a property value model adapter for the specified
	 * property value model that returns whether the property's value
	 * affirms the specified predicate.
	 * <p>
	 * <strong>NB:</strong> If specified model's value is <code>null</code>,
	 * the returned model's value will be the specified null result;
	 * and the value will <em>never</em> be passed to the specified predicate.
	 * 
	 * @see #valueAffirms(PropertyValueModel, Predicate)
	 * @see #valueAffirms_(PropertyValueModel, Predicate)
	 * @see #valueAffirms(PropertyValueModel, Predicate, Boolean)
	 */
	public static <V> PropertyValueModel<Boolean> valueAffirms(
			PropertyValueModel<? extends V> propertyModel,
			Predicate<? super V> predicate,
			boolean nullResult
	) {
		return valueAffirms(propertyModel, predicate, Boolean.valueOf(nullResult));
	}

	/**
	 * Construct a property value model adapter for the specified
	 * property value model that returns whether the property's value
	 * affirms the specified predicate.
	 * <p>
	 * <strong>NB:</strong> If specified model's value is <code>null</code>,
	 * the returned model's value will be the specified null result;
	 * and the value will <em>never</em> be passed to the specified predicate.
	 * 
	 * @see #valueAffirms(PropertyValueModel, Predicate)
	 * @see #valueAffirms_(PropertyValueModel, Predicate)
	 * @see #valueAffirms(PropertyValueModel, Predicate, boolean)
	 */
	public static <V> PropertyValueModel<Boolean> valueAffirms(
			PropertyValueModel<? extends V> propertyModel,
			Predicate<? super V> predicate,
			Boolean nullResult
	) {
		return transform_(propertyModel, TransformerTools.adapt(predicate, nullResult));
	}

	/**
	 * Construct a property value model adapter for the specified
	 * property value model that returns whether the property's value
	 * affirms the specified predicate.
	 * <p>
	 * <strong>NB:</strong> The specified predicate must be able to
	 * handle a <code>null</code> variable.
	 * 
	 * @see #valueAffirms(PropertyValueModel, Predicate)
	 * @see #valueAffirms(PropertyValueModel, Predicate, boolean)
	 * @see #valueAffirms(PropertyValueModel, Predicate, Boolean)
	 */
	public static <V> PropertyValueModel<Boolean> valueAffirms_(PropertyValueModel<? extends V> propertyModel, Predicate<? super V> predicate) {
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
	 * the model returns a <code>null</code> value.
	 * 
	 * @see #filter(PropertyValueModel, Class)
	 * @see PredicateTools#instanceOf(Class)
	 */
	public static <V> PropertyValueModel<V> filter(
			PropertyValueModel<?> propertyModel,
			Class<V> clazz,
			V defaultValue
	) {
		return transform(propertyModel, TransformerTools.cast(TransformerTools.filteringTransformer(PredicateTools.instanceOf(clazz), defaultValue)));
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
	public static <V> PropertyValueModel<V> filter_(
			PropertyValueModel<?> propertyModel,
			Class<V> clazz,
			V defaultValue
	) {
		return transform_(propertyModel, TransformerTools.cast(TransformerTools.filteringTransformer(PredicateTools.instanceOf(clazz), defaultValue)));
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
	public static <V> PropertyValueModel<V> filter(
			PropertyValueModel<? extends V> propertyModel,
			Predicate<? super V> filter,
			V defaultValue
	) {
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
	public static <V> PropertyValueModel<V> filter_(
			PropertyValueModel<? extends V> propertyModel,
			Predicate<? super V> filter,
			V defaultValue
	) {
		return transform_(propertyModel, TransformerTools.filteringTransformer(filter, defaultValue));
	}


	// ********** buffered wrapper **********

	/**
	 * Construct a trigger that can be used to accept or reset one or more
	 * buffered property value models (or any other listeners).
	 * 
	 * @see #buffer(ModifiablePropertyValueModel, BufferedPropertyValueModelAdapter.Trigger)
	 */
	public static BufferedPropertyValueModelAdapter.Trigger bufferedPropertyValueModelAdapterTrigger() {
		return new BufferedPropertyValueModelAdapter.Trigger();
	}

	/**
	 * Construct a property value model that wraps the specified
	 * property value model and buffers its value using the specified trigger.
	 * Return an association of the buffered model and another boolean model
	 * that indicates whether the buffered model is actually buffering a value.
	 * 
	 * @see BufferedPropertyValueModelAdapter
	 * @see #bufferedPropertyValueModelAdapterTrigger()
	 * @see PluggablePropertyValueModel
	 */
	public static <V> Association<ModifiablePropertyValueModel<V>, PropertyValueModel<Boolean>> buffer(
			ModifiablePropertyValueModel<V> propertyModel,
			BufferedPropertyValueModelAdapter.Trigger trigger
	) {
		BufferedPropertyValueModelAdapter.Factory<V> factory = new BufferedPropertyValueModelAdapter.Factory<>(propertyModel, trigger);
		ModifiablePropertyValueModel<V> model = modifiablePropertyValueModel(factory);
		PropertyValueModel<Boolean> bufferingModel = factory.getBufferingModel();
		return new SimpleAssociation<>(model, bufferingModel);
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
	public static <V1, V2> PropertyValueModel<V2> transform(
			PropertyValueModel<? extends V1> propertyModel,
			Transformer<? super V1, ? extends V2> transformer
	) {
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
	public static <V1, V2> PropertyValueModel<V2> transform_(
			PropertyValueModel<? extends V1> propertyModel,
			Transformer<? super V1, ? extends V2> transformer
	) {
		return propertyValueModel(pluggablePropertyValueModelAdapterFactory_(propertyModel, transformer));
	}

	/**
	 * Construct a modifiable property value model that wraps the specified
	 * property value model and transforms its value with the specified
	 * transformers.
	 * <p>
	 * <strong>NB:</strong> The specified transformers will <em>never</em> be passed a <code>null</code> input.
	 * Instead, a <code>null</code> input will be transformed into a <code>null</code> output.
	 * 
	 * @see PluggablePropertyValueModel
	 */
	public static <V1, V2> ModifiablePropertyValueModel<V2> transform(
			ModifiablePropertyValueModel<V1> propertyModel,
			Transformer<? super V1, ? extends V2> getTransformer,
			Transformer<? super V2, ? extends V1> setTransformer
	) {
		return transform_(propertyModel, TransformerTools.nullCheck(getTransformer), TransformerTools.nullCheck(setTransformer));
	}

	/**
	 * Construct a modifiable property value model that wraps the specified
	 * property value model and transforms its value with the specified
	 * transformers.
	 * <p>
	 * <strong>NB:</strong> The specified transformers must be able to handle a <code>null</code> input.
	 * 
	 * @see PluggablePropertyValueModel
	 */
	public static <V1, V2> ModifiablePropertyValueModel<V2> transform_(
			ModifiablePropertyValueModel<V1> propertyModel,
			Transformer<? super V1, ? extends V2> getTransformer,
			Transformer<? super V2, ? extends V1> setTransformer
	) {
		return transform_(propertyModel, getTransformer, setTransformerClosureAdapter(propertyModel, setTransformer));
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
	public static <V1, V2> ModifiablePropertyValueModel<V2> transform(
			PropertyValueModel<? extends V1> propertyModel,
			Transformer<? super V1, ? extends V2> transformer,
			Closure<? super V2> setValueClosure
	) {
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
	public static <V1, V2> ModifiablePropertyValueModel<V2> transform_(
			PropertyValueModel<? extends V1> propertyModel,
			Transformer<? super V1, ? extends V2> transformer,
			Closure<? super V2> setValueClosure
	) {
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
	public static <V1, V2> PluggablePropertyValueModel.Adapter.Factory<V2> pluggablePropertyValueModelAdapterFactory(
			PropertyValueModel<? extends V1> propertyModel,
			Transformer<? super V1, ? extends V2> transformer
	) {
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
	public static <V1, V2> PluggablePropertyValueModel.Adapter.Factory<V2> pluggablePropertyValueModelAdapterFactory_(
			PropertyValueModel<? extends V1> propertyModel,
			Transformer<? super V1, ? extends V2> transformer
	) {
		return new PropertyPluggablePropertyValueModelAdapter.Factory<>(propertyModel, transformer);
	}


	// ********** misc **********

	/**
	 * Construct a "set" closure that wraps the specified modifiable property value model and
	 * uses the specified "set" transformer to transform the incoming value before
	 * forwarding it to the model.
	 */
	public static <V1, V2> Closure<V2> setTransformerClosureAdapter(ModifiablePropertyValueModel<V1> propertyModel, Transformer<? super V2, ? extends V1> setTransformer) {
		return new SetTransformerClosureAdapter<>(propertyModel, setTransformer);
	}

	/**
	 * "Set" closure that wraps a modifiable property value model and
	 * uses a "set" transformer to transform the incoming value before
	 * forwarding it to the model.
	 */
	public static final class SetTransformerClosureAdapter<V1, V2>
		implements Closure<V2>
	{
		private final ModifiablePropertyValueModel<V1> propertyModel;
		private final Transformer<? super V2, ? extends V1> setTransformer;
		
		public SetTransformerClosureAdapter(ModifiablePropertyValueModel<V1> propertyModel, Transformer<? super V2, ? extends V1> setTransformer) {
			super();
			if (propertyModel == null) {
				throw new NullPointerException();
			}
			this.propertyModel = propertyModel;
			if (setTransformer == null) {
				throw new NullPointerException();
			}
			this.setTransformer = setTransformer;
		}

		public void execute(V2 value) {
			this.propertyModel.setValue(this.setTransformer.transform(value));
		}

		@Override
		public String toString() {
			return ObjectTools.toString(this, this.setTransformer);
		}
	}

	/**
	 * Construct a "set" closure that wraps another "set" closure and
	 * provides a hook to do nothing if the target object is <code>null</code>.
	 */
	public static <A1, A2> BiClosure<A1, A2> nullCheckSetClosureWrapper(BiClosure<? super A1, ? super A2> closure) {
		return nullCheckSetClosureWrapper(closure, ClosureTools.nullClosure());
	}

	/**
	 * Construct a "set" closure that wraps another "set" closure and
	 * provides a hook to execute a different closure
	 * if the target object is <code>null</code>.
	 */
	public static <A1, A2> BiClosure<A1, A2> nullCheckSetClosureWrapper(BiClosure<? super A1, ? super A2> closure, Closure<? super A2> nullTargetSetClosure) {
		return new NullCheckSetClosureWrapper<>(closure, nullTargetSetClosure);
	}

	/**
	 * "Set" closure that wraps another "set" closure and
	 * provides a hook to execute a different closure
	 * if the target object is <code>null</code>.
	 */
	public static final class NullCheckSetClosureWrapper<A1, A2>
		implements BiClosure<A1, A2>
	{
		private final BiClosure<? super A1, ? super A2> closure;
		private final Closure<? super A2> nullTargetClosure;

		public NullCheckSetClosureWrapper(BiClosure<? super A1, ? super A2> closure, Closure<? super A2> nullTargetClosure) {
			super();
			if (closure == null) {
				throw new NullPointerException();
			}
			this.closure = closure;
			if (nullTargetClosure == null) {
				throw new NullPointerException();
			}
			this.nullTargetClosure = nullTargetClosure;
		}

		public void execute(A1 argument1, A2 argument2) {
			if (argument1 != null) {
				this.closure.execute(argument1, argument2);
			} else {
				this.nullTargetClosure.execute(argument2);
			}
		}

		@Override
		public String toString() {
			return ObjectTools.toString(this, this.closure);
		}
	}

	/**
	 * Construct a "set" bi-closure that wraps another "set" closure and
	 * forwards only the second argument to it.
	 */
	public static <A1, A2> BiClosure<A1, A2> setClosureAdapter(Closure<? super A2> closure) {
		return new SetClosureAdapter<>(closure);
	}

	/**
	 * "Set" bi-closure that wraps another "set" closure and
	 * forwards only the second argument to it.
	 */
	public static final class SetClosureAdapter<A1, A2>
		implements BiClosure<A1, A2>
	{
		private final Closure<? super A2> closure;
		
		public SetClosureAdapter(Closure<? super A2> closure) {
			super();
			if (closure == null) {
				throw new NullPointerException();
			}
			this.closure = closure;
		}

		public void execute(A1 argument1, A2 argument2) {
			this.closure.execute(argument2);
		}

		@Override
		public String toString() {
			return ObjectTools.toString(this, this.closure);
		}
	}


	// ********** compound PVMs **********

	/**
	 * Construct a compound property value model for the
	 * specified <em>outer</em> property value model.
	 * <p>
	 * <strong>NB:</strong>
	 * If the <em>outer</em> model's value is ever <code>null</code>,
	 * the model's value will also be <code>null</code>.
	 */
	public static <V> PropertyValueModel<V> compound(PropertyValueModel<? extends PropertyValueModel<? extends V>> outerModel) {
		return propertyValueModel(compoundPropertyValueModelAdapterFactory(outerModel));
	}

	/**
	 * Construct a compound property value model for the
	 * specified <em>outer</em> property value model.
	 * <p>
	 * <strong>NB:</strong>
	 * If the <em>outer</em> model's value is ever <code>null</code>,
	 * the model will throw a {@link NullPointerException}.
	 */
	public static <V> PropertyValueModel<V> compound_(PropertyValueModel<? extends PropertyValueModel<? extends V>> outerModel) {
		return propertyValueModel(compoundPropertyValueModelAdapterFactory_(outerModel));
	}

	/**
	 * Construct a compound property value model adapter factory for
	 * the specified <em>outer</em> property value model.
	 * <p>
	 * <strong>NB:</strong>
	 * If the <em>outer</em> model's value is ever <code>null</code>,
	 * the factory's model's value will also be <code>null</code>.
	 */
	public static <V, IM extends PropertyValueModel<? extends V>, OM extends PropertyValueModel<? extends IM>> PluggablePropertyAspectAdapter.Factory<V, IM, OM> compoundPropertyValueModelAdapterFactory(OM outerModel) {
		return modelAspectAdapterFactory_(
				outerModel,
				PropertyValueModel.VALUE,
				valueTransformer()
			);
	}

	/**
	 * Construct a compound property value model adapter factory for
	 * the specified <em>outer</em> property value model.
	 * <p>
	 * <strong>NB:</strong>
	 * If the <em>outer</em> model's value is ever <code>null</code>,
	 * the factory's model will throw a {@link NullPointerException}.
	 */
	public static <V, IM extends PropertyValueModel<? extends V>, OM extends PropertyValueModel<? extends IM>> PluggablePropertyAspectAdapter.Factory<V, IM, OM> compoundPropertyValueModelAdapterFactory_(OM outerModel) {
		return modelAspectAdapterFactory_(
				outerModel,
				PropertyValueModel.VALUE,
				valueTransformer_()
			);
	}

	/**
	 * Construct a modifiable compound property value model
	 * for the specified <em>outer</em> property value model.
	 * <p>
	 * <strong>NB:</strong>
	 * If the <em>outer</em> model's value is ever <code>null</code>,
	 * the model's value will also be <code>null</code>
	 * and, if the model's value is set, it will do nothing.
	 */
	public static <V> ModifiablePropertyValueModel<V> compoundModifiable(PropertyValueModel<? extends ModifiablePropertyValueModel<V>> outerModel) {
		return modifiablePropertyValueModel(compoundModifiablePropertyValueModelAdapterFactory(outerModel));
	}

	/**
	 * Construct a modifiable compound property value model
	 * for the specified <em>outer</em> property value model.
	 * <p>
	 * <strong>NB:</strong>
	 * If the <em>outer</em> model's value is ever <code>null</code>,
	 * the model will throw a {@link NullPointerException}
	 * when either its value is get or set.
	 */
	public static <V> ModifiablePropertyValueModel<V> compoundModifiable_(PropertyValueModel<? extends ModifiablePropertyValueModel<V>> outerModel) {
		return modifiablePropertyValueModel(compoundModifiablePropertyValueModelAdapterFactory_(outerModel));
	}

	/**
	 * Construct a modifiable compound property value model adapter factory
	 * for the specified <em>outer</em> property value model.
	 * <p>
	 * <strong>NB:</strong>
	 * If the <em>outer</em> model's value is ever <code>null</code>,
	 * the model's value will also be <code>null</code>
	 * and, if the model's value is set, it will do nothing.
	 */
	public static <V> PluggableModifiablePropertyValueModel.Adapter.Factory<V> compoundModifiablePropertyValueModelAdapterFactory(PropertyValueModel<? extends ModifiablePropertyValueModel<V>> outerModel) {
		return modifiablePropertyAspectAdapterFactory_(
				compoundPropertyValueModelAdapterFactory(outerModel),
				setValueClosure()
			);
	}

	/**
	 * Construct a modifiable compound property value model adapter factory
	 * for the specified <em>outer</em> property value model.
	 * <p>
	 * <strong>NB:</strong>
	 * If the <em>outer</em> model's value is ever <code>null</code>,
	 * the factory's model will throw a {@link NullPointerException}
	 * when either its value is get or set.
	 */
	public static <V> PluggableModifiablePropertyValueModel.Adapter.Factory<V> compoundModifiablePropertyValueModelAdapterFactory_(PropertyValueModel<? extends ModifiablePropertyValueModel<V>> outerModel) {
		return modifiablePropertyAspectAdapterFactory_(
				compoundPropertyValueModelAdapterFactory_(outerModel),
				setValueClosure_()
			);
	}


	// ********** aspect adapters **********

	/**
	 * Construct a model property aspect adapter for the
	 * specified subject model, aspect name, and transformer.
	 * <p>
	 * <strong>NB:</strong>
	 * The specified transformer will <em>never</em> be passed a <code>null</code> subject.
	 * Instead, a <code>null</code> subject will be transformed into a <code>null</code> value.
	 */
	public static <V, S extends Model, SM extends PropertyValueModel<? extends S>> PropertyValueModel<V> modelAspectAdapter(
			SM subjectModel,
			String aspectName,
			Transformer<? super S, ? extends V> transformer
	) {
		return propertyValueModel(modelAspectAdapterFactory(subjectModel, aspectName, transformer));
	}

	/**
	 * Construct a model property aspect adapter for the
	 * specified subject model, aspect name, and transformer.
	 * <p>
	 * <strong>NB:</strong>
	 * The specified transformer must be able to handle a <code>null</code> subject.
	 */
	public static <V, S extends Model, SM extends PropertyValueModel<? extends S>> PropertyValueModel<V> modelAspectAdapter_(
			SM subjectModel,
			String aspectName,
			Transformer<? super S, ? extends V> transformer
	) {
		return propertyValueModel(modelAspectAdapterFactory_(subjectModel, aspectName, transformer));
	}

	/**
	 * Construct a model property aspect adapter factory for the
	 * specified subject model, aspect name, and transformer.
	 * <p>
	 * <strong>NB:</strong>
	 * The specified transformer will <em>never</em> be passed a <code>null</code> subject.
	 * Instead, a <code>null</code> subject will be transformed into a <code>null</code> value.
	 */
	public static <V, S extends Model, SM extends PropertyValueModel<? extends S>> PluggablePropertyAspectAdapter.Factory<V, S, SM> modelAspectAdapterFactory(
			SM subjectModel,
			String aspectName,
			Transformer<? super S, ? extends V> transformer
	) {
		return modelAspectAdapterFactory_(subjectModel, aspectName, TransformerTools.nullCheck(transformer));
	}

	/**
	 * Construct a model property aspect adapter factory for the
	 * specified subject model, aspect name, and transformer.
	 * <p>
	 * <strong>NB:</strong>
	 * The specified transformer must be able to handle a <code>null</code> subject.
	 */
	public static <V, S extends Model, SM extends PropertyValueModel<? extends S>> PluggablePropertyAspectAdapter.Factory<V, S, SM> modelAspectAdapterFactory_(
			SM subjectModel,
			String aspectName,
			Transformer<? super S, ? extends V> transformer
	) {
		return pluggableAspectAdapterFactory(subjectModel, new ModelPropertyAspectAdapter.Factory<>(aspectName, transformer));
	}

	/**
	 * Construct a property aspect adapter for the
	 * specified subject model and subject adapter factory.
	 */
	public static <V, S, SM extends PropertyValueModel<? extends S>> PropertyValueModel<V> aspectAdapter(
			SM subjectModel,
			PluggablePropertyAspectAdapter.SubjectAdapter.Factory<V, S> subjectAdapterFactory
	) {
		return propertyValueModel(pluggableAspectAdapterFactory(subjectModel, subjectAdapterFactory));
	}

	/**
	 * Construct a property aspect adapter factory for the
	 * specified subject model and subject adapter factory.
	 */
	public static <V, S, SM extends PropertyValueModel<? extends S>> PluggablePropertyAspectAdapter.Factory<V, S, SM> pluggableAspectAdapterFactory(
			SM subjectModel,
			PluggablePropertyAspectAdapter.SubjectAdapter.Factory<V, S> subjectAdapterFactory
	) {
		return new PluggablePropertyAspectAdapter.Factory<>(subjectModel, subjectAdapterFactory);
	}


	// ********** modifiable aspect adapters **********

	/**
	 * Construct a modifiable property aspect adapter for the
	 * specified subject model, aspect name, transformer, and closure.
	 * <p>
	 * <strong>NB:</strong>
	 * The specified transformer will <em>never</em> be passed a <code>null</code> subject.
	 * Instead, a <code>null</code> subject will be transformed into a <code>null</code> value.
	 * Likewise, if the subject is <code>null</code>, the specified closure will
	 * not be executed.
	 */
	public static <V, S extends Model, SM extends PropertyValueModel<S>> ModifiablePropertyValueModel<V> modifiablePropertyAspectAdapter(
			SM subjectModel,
			String aspectName,
			Transformer<? super S, ? extends V> getTransformer,
			BiClosure<? super S, ? super V> setClosure
	) {
		return modifiablePropertyValueModel(modifiablePropertyAspectAdapterFactory(subjectModel, aspectName, getTransformer, setClosure));
	}

	/**
	 * Construct a modifiable property aspect adapter for the
	 * specified subject model, aspect name, transformer, and closure.
	 * <p>
	 * <strong>NB:</strong>
	 * The specified transformer must be able to handle a <code>null</code> subject.
	 * Likewise, the specified closure must be able to handle a <code>null</code>
	 * subject (i.e. first argument).
	 */
	public static <V, S extends Model, SM extends PropertyValueModel<S>> ModifiablePropertyValueModel<V> modifiablePropertyAspectAdapter_(
			SM subjectModel,
			String aspectName,
			Transformer<? super S, ? extends V> getTransformer,
			BiClosure<? super S, ? super V> setClosure
	) {
		return modifiablePropertyValueModel(modifiablePropertyAspectAdapterFactory_(subjectModel, aspectName, getTransformer, setClosure));
	}

	/**
	 * Construct a modifiable property aspect adapter factory for the
	 * specified subject model, aspect name, transformer, and closure.
	 * <p>
	 * <strong>NB:</strong>
	 * The specified transformer will <em>never</em> be passed a <code>null</code> subject.
	 * Instead, a <code>null</code> subject will be transformed into a <code>null</code> value.
	 * Likewise, if the subject is <code>null</code>, the specified closure will
	 * not be executed.
	 */
	public static <V, S extends Model, SM extends PropertyValueModel<S>> PluggableModifiablePropertyValueModel.Adapter.Factory<V> modifiablePropertyAspectAdapterFactory(
			SM subjectModel,
			String aspectName,
			Transformer<? super S, ? extends V> getTransformer,
			BiClosure<? super S, ? super V> setClosure
	) {
		return modifiablePropertyAspectAdapterFactory(modelAspectAdapterFactory(subjectModel, aspectName, getTransformer), setClosure);
	}

	/**
	 * Construct a modifiable property aspect adapter factory for the
	 * specified subject model, aspect name, transformer, and closure.
	 * <p>
	 * <strong>NB:</strong>
	 * The specified transformer must be able to handle a <code>null</code> subject.
	 * Likewise, the specified closure must be able to handle a <code>null</code>
	 * subject (i.e. first argument).
	 */
	public static <V, S extends Model, SM extends PropertyValueModel<S>> PluggableModifiablePropertyValueModel.Adapter.Factory<V> modifiablePropertyAspectAdapterFactory_(
			SM subjectModel,
			String aspectName,
			Transformer<? super S, ? extends V> getTransformer,
			BiClosure<? super S, ? super V> setClosure
	) {
		return modifiablePropertyAspectAdapterFactory_(modelAspectAdapterFactory_(subjectModel, aspectName, getTransformer), setClosure);
	}

	/**
	 * Construct a modifiable property aspect adapter factory for the
	 * specified factory and closure.
	 * <p>
	 * <strong>NB:</strong>
	 * If the subject is <code>null</code>, the specified closure will
	 * not be executed.
	 */
	public static <V, S> PluggableModifiablePropertyValueModel.Adapter.Factory<V> modifiablePropertyAspectAdapterFactory(
			ModifiablePropertyAspectAdapter.GetAdapter.Factory<V, S> getAdapterFactory,
			BiClosure<? super S, ? super V> setClosure
	) {
		return modifiablePropertyAspectAdapterFactory_(getAdapterFactory, nullCheckSetClosureWrapper(setClosure));
	}

	/**
	 * Construct a modifiable property aspect adapter factory for the
	 * specified factory and closure.
	 * <p>
	 * <strong>NB:</strong>
	 * The specified closure must be able to handle a <code>null</code>
	 * subject (i.e. first argument).
	 */
	public static <V, S> PluggableModifiablePropertyValueModel.Adapter.Factory<V> modifiablePropertyAspectAdapterFactory_(
			ModifiablePropertyAspectAdapter.GetAdapter.Factory<V, S> getAdapterFactory,
			BiClosure<? super S, ? super V> setClosure
	) {
		return new ModifiablePropertyAspectAdapter.Factory<>(getAdapterFactory, setClosure);
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
	 * Construct a <em>modifiable</em> property value model adapter for the specified
	 * property value model adapter adapter factory and closure.
	 * The specified closure is invoked when the model's value is set.
	 * 
	 * @see PluggableModifiablePropertyValueModel
	 */
	public static <V> ModifiablePropertyValueModel<V> pluggableModifiablePropertyValueModel(BasePluggablePropertyValueModel.Adapter.Factory<V, ? extends BasePluggablePropertyValueModel.Adapter<V>> factory, Closure<? super V> setValueClosure) {
		return modifiablePropertyValueModel(new PluggableModifiablePropertyValueModelAdapter.Factory<>(factory, setValueClosure));
	}

	/**
	 * Construct a modifiable property value model adapter for the specified adapter factory.
	 * 
	 * @see PluggableModifiablePropertyValueModel
	 */
	public static <V> ModifiablePropertyValueModel<V> modifiablePropertyValueModel(PluggableModifiablePropertyValueModel.Adapter.Factory<V> adapterFactory) {
		return new PluggableModifiablePropertyValueModel<>(adapterFactory);
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


	// ********** value transformers/closures **********

	/**
	 * Return a transformer that converts a property value model to its value
	 * but first checks whether the property value model passed to it is <code>null</code>.
	 * <p>
	 * <strong>NB:</strong>
	 * If the property value model is <code>null</code>, the transformer returns
	 * <code>null</code>.
	 * 
	 * @see #valueTransformer(Object)
	 * @see #valueTransformer_()
	 * @see PropertyValueModel#VALUE_TRANSFORMER
	 */
	@SuppressWarnings("unchecked")
	public static <V, PVM extends PropertyValueModel<? extends V>> Transformer<PVM, V> valueTransformer() {
		return NULL_CHECK_VALUE_TRANSFORMER;
	}

	@SuppressWarnings("rawtypes")
	public static final Transformer NULL_CHECK_VALUE_TRANSFORMER = TransformerTools.nullCheck(valueTransformer_(), null);

	/**
	 * Return a transformer that converts a property value model to its value
	 * but first checks whether the property value model passed to it is <code>null</code>.
	 * <p>
	 * <strong>NB:</strong>
	 * If the property value model is <code>null</code>, the transformer returns
	 * the specified null value.
	 * 
	 * @see #valueTransformer()
	 * @see #valueTransformer_()
	 * @see PropertyValueModel#VALUE_TRANSFORMER
	 */
	public static <V, PVM extends PropertyValueModel<? extends V>> Transformer<PVM, V> valueTransformer(V nullValue) {
		return TransformerTools.nullCheck(valueTransformer_(), nullValue);
	}

	/**
	 * Return a transformer that converts a property value model to its value.
	 * <p>
	 * <strong>NB:</strong>
	 * If the property value model is <code>null</code>, the transformer throws
	 * a {@link NullPointerException}.
	 * 
	 * @see #valueTransformer(Object)
	 * @see #valueTransformer()
	 * @see PropertyValueModel#VALUE_TRANSFORMER
	 */
	@SuppressWarnings("unchecked")
	public static <V, PVM extends PropertyValueModel<? extends V>> Transformer<PVM, V> valueTransformer_() {
		return PropertyValueModel.VALUE_TRANSFORMER;
	}

	/**
	 * Return a closure that sets a property value model's value.
	 * <p>
	 * <strong>NB:</strong>
	 * If the property value model is <code>null</code>, the closure
	 * does nothing.
	 * 
	 * @see #setValueClosure_()
	 * @see PropertyValueModel#VALUE_TRANSFORMER
	 */
	@SuppressWarnings("unchecked")
	public static <V, PVM extends ModifiablePropertyValueModel<? super V>> BiClosure<PVM, V> setValueClosure() {
		return NULL_CHECK_SET_VALUE_CLOSURE;
	}

	@SuppressWarnings("rawtypes")
	public static final BiClosure NULL_CHECK_SET_VALUE_CLOSURE = nullCheckSetClosureWrapper(setValueClosure_());

	/**
	 * Return a closure that sets a property value model's value.
	 * <p>
	 * <strong>NB:</strong>
	 * If the property value model is <code>null</code>, the closure throws
	 * a {@link NullPointerException}.
	 * 
	 * @see #setValueClosure()
	 * @see PropertyValueModel#VALUE_TRANSFORMER
	 */
	@SuppressWarnings("unchecked")
	public static <V, PVM extends ModifiablePropertyValueModel<? super V>> BiClosure<PVM, V> setValueClosure_() {
		return ModifiablePropertyValueModel.SET_VALUE_CLOSURE;
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

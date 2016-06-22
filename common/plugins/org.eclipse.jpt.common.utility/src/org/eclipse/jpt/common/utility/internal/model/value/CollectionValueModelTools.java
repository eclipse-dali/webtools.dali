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

import java.util.Arrays;
import java.util.Collection;
import org.eclipse.jpt.common.utility.closure.Closure;
import org.eclipse.jpt.common.utility.internal.closure.BooleanClosure;
import org.eclipse.jpt.common.utility.internal.closure.ClosureTools;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerTools;
import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.predicate.Predicate;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * {@link CollectionValueModel Collection value model} utility methods.
 */
public final class CollectionValueModelTools {

	// ********** composite PVMs **********

	/**
	 * Construct a composite property value model adapter for the specified
	 * transformer and property value models.
	 */
	@SafeVarargs
	public static <E, V> PropertyValueModel<V> compositePropertyValueModel(Transformer<? super Collection<E>, V> transformer, PropertyValueModel<? extends E>... propertyValueModels) {
		return compositePropertyValueModel(Arrays.asList(propertyValueModels), transformer);
	}

	/**
	 * Construct a composite property value model adapter for the specified
	 * property value models and transformer.
	 */
	public static <E, V> PropertyValueModel<V> compositePropertyValueModel(Collection<? extends PropertyValueModel<? extends E>> propertyValueModels, Transformer<? super Collection<E>, V> transformer) {
		return compositePropertyValueModel(new StaticCollectionValueModel<>(propertyValueModels), transformer);
	}

	/**
	 * Construct a composite property value model adapter for the specified
	 * collection value model and transformer.
	 * @see PluggablePropertyValueModel
	 */
	public static <E, V> PropertyValueModel<V> compositePropertyValueModel(CollectionValueModel<? extends PropertyValueModel<? extends E>> collectionModel, Transformer<? super Collection<E>, V> transformer) {
		return PropertyValueModelTools.propertyValueModel(new CompositePropertyValueModelAdapter.Factory<>(collectionModel, transformer));
	}


	// ********** collection meta data adapters **********

	/**
	 * Construct a property value model adapter for the specified
	 * collection value model.
	 * If the collection is empty, the model's value is <code>null</code>;
	 * otherwise, it is the first element in the collection.
	 */
	public static <E> PropertyValueModel<E> firstElementPropertyValueModel(CollectionValueModel<? extends E> collectionModel) {
		return propertyValueModel(collectionModel, TransformerTools.collectionFirstElementTransformer());
	}

	/**
	 * Construct a property value model adapter for the specified
	 * collection value model.
	 * If the collection is empty, the model's value is <code>null</code>;
	 * otherwise, it is the last element in the collection.
	 */
	public static <E> PropertyValueModel<E> lastElementPropertyValueModel(CollectionValueModel<? extends E> collectionModel) {
		return propertyValueModel(collectionModel, TransformerTools.collectionLastElementTransformer());
	}

	/**
	 * Construct a property value model adapter for the specified
	 * collection value model.
	 * If the collection contains <em>only</em> a single element,
	 * the model's value is that element; otherwise,
	 * the model's value is <code>null</code>.
	 */
	public static <E> PropertyValueModel<E> singleElementPropertyValueModel(CollectionValueModel<? extends E> collectionModel) {
		return propertyValueModel(collectionModel, TransformerTools.collectionSingleElementTransformer());
	}

	/**
	 * Construct a property value model adapter for the specified
	 * collection value model that returns whether the collection is <em>not</em> empty.
	 */
	public static PropertyValueModel<Boolean> isNotEmptyPropertyValueModel(CollectionValueModel<?> collectionModel) {
		return propertyValueModel(collectionModel, TransformerTools.collectionIsNotEmptyTransformer());
	}

	/**
	 * Construct a <em>modifiable</em> property value model adapter for the specified
	 * collection value model that returns whether the collection is <em>not</em> empty.
	 * This model can also be used to populate or clear the collection value model.
	 * The specified collection mutator is used to convert the collection value model:
	 * it should populate the collection value model when passed <code>true</code>
	 * and clear the collection value model when passed <code>false</code>.
	 */
	public static ModifiablePropertyValueModel<Boolean> isNotEmptyModifiablePropertyValueModel(CollectionValueModel<?> collectionModel, BooleanClosure.Adapter collectionMutator) {
		return booleanModifiablePropertyValueModel(collectionModel, TransformerTools.collectionIsNotEmptyTransformer(), collectionMutator);
	}

	/**
	 * Construct a property value model adapter for the specified
	 * collection value model that returns whether the collection is empty.
	 */
	public static PropertyValueModel<Boolean> isEmptyPropertyValueModel(CollectionValueModel<?> collectionModel) {
		return propertyValueModel(collectionModel, TransformerTools.collectionIsEmptyTransformer());
	}

	/**
	 * Construct a <em>modifiable</em> property value model adapter for the specified
	 * collection value model that returns whether the collection is empty.
	 * This model can also be used to populate or clear the collection value model.
	 * The specified collection mutator is used to convert the collection value model:
	 * it should clear the collection value model when passed <code>true</code>
	 * and populate the collection value model when passed <code>false</code>.
	 */
	public static ModifiablePropertyValueModel<Boolean> isEmptyModifiablePropertyValueModel(CollectionValueModel<?> collectionModel, BooleanClosure.Adapter collectionMutator) {
		return booleanModifiablePropertyValueModel(collectionModel, TransformerTools.collectionIsEmptyTransformer(), collectionMutator);
	}

	/**
	 * Construct a property value model adapter for the specified
	 * collection value model that returns whether the collection contains
	 * exactly one element.
	 */
	public static PropertyValueModel<Boolean> containsSingleElementPropertyValueModel(CollectionValueModel<?> collectionModel) {
		return propertyValueModel(collectionModel, TransformerTools.collectionContainsSingleElementTransformer());
	}

	/**
	 * Construct a property value model adapter for the specified
	 * collection value model that returns whether the collection's size
	 * equals the specified size.
	 */
	public static PropertyValueModel<Boolean> sizeEqualsPropertyValueModel(CollectionValueModel<?> collectionModel, int size) {
		return propertyValueModel(collectionModel, TransformerTools.collectionSizeEqualsTransformer(size));
	}


	// ********** boolean PVM adapters **********

	/**
	 * Construct a boolean property value model that is a composite AND of the
	 * specified boolean property value models; i.e. the model's value is {@link Boolean#TRUE}
	 * if all the contained models' values are {@link Boolean#TRUE},
	 * otherwise its value is {@link Boolean#FALSE}.
	 * The model's default value, when it contains no nested models, is {@link Boolean#TRUE}.
	 */
	@SafeVarargs
	public static PropertyValueModel<Boolean> and(PropertyValueModel<Boolean>... models) {
		return compositePropertyValueModel(AND_TRANSFORMER, models);
	}

	/**
	 * Construct a boolean property value model that is a composite AND of the
	 * specified boolean property value models; i.e. the model's value is {@link Boolean#TRUE}
	 * if all the contained models' values are {@link Boolean#TRUE},
	 * otherwise its value is {@link Boolean#FALSE}.
	 * The model's default value, when it contains no nested models, is {@link Boolean#TRUE}.
	 */
	public static PropertyValueModel<Boolean> and(Collection<? extends PropertyValueModel<Boolean>> models) {
		return compositePropertyValueModel(models, AND_TRANSFORMER);
	}

	/**
	 * Construct a boolean property value model that is a composite AND of the
	 * specified boolean property value models; i.e. the model's value is {@link Boolean#TRUE}
	 * if all the contained models' values are {@link Boolean#TRUE},
	 * otherwise its value is {@link Boolean#FALSE}.
	 * The model's default value, when it contains no nested models, is {@link Boolean#TRUE}.
	 */
	public static PropertyValueModel<Boolean> and(CollectionValueModel<? extends PropertyValueModel<Boolean>> collectionModel) {
		return compositePropertyValueModel(collectionModel, AND_TRANSFORMER);
	}

	/**
	 * @see AndTransformer
	 */
	public static final Transformer<Collection<Boolean>, Boolean> AND_TRANSFORMER = new AndTransformer();

	/**
	 * A transformer that transforms a collection of {@link Boolean}s into a single
	 * {@link Boolean} by ANDing them together. Its default value is {@link Boolean#TRUE}.
	 * @see #and(CollectionValueModel)
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
		return compositePropertyValueModel(OR_TRANSFORMER, models);
	}

	/**
	 * Construct a boolean property value model that is a composite OR of the
	 * specified boolean property value models; i.e. the model's value is {@link Boolean#FALSE}
	 * if all the contained models' values are {@link Boolean#FALSE},
	 * otherwise its value is {@link Boolean#TRUE}.
	 * The model's default value, when it contains no nested models, is {@link Boolean#FALSE}.
	 */
	public static PropertyValueModel<Boolean> or(Collection<? extends PropertyValueModel<Boolean>> models) {
		return compositePropertyValueModel(models, OR_TRANSFORMER);
	}

	/**
	 * Construct a boolean property value model that is a composite OR of the
	 * specified boolean property value models; i.e. the model's value is {@link Boolean#FALSE}
	 * if all the contained models' values are {@link Boolean#FALSE},
	 * otherwise its value is {@link Boolean#TRUE}.
	 * The model's default value, when it contains no nested models, is {@link Boolean#FALSE}.
	 */
	public static PropertyValueModel<Boolean> or(CollectionValueModel<? extends PropertyValueModel<Boolean>> collectionModel) {
		return compositePropertyValueModel(collectionModel, OR_TRANSFORMER);
	}

	/**
	 * @see OrTransformer
	 */
	public static final Transformer<Collection<Boolean>, Boolean> OR_TRANSFORMER = new OrTransformer();

	/**
	 * A transformer that transforms a collection of {@link Boolean}s into a single
	 * {@link Boolean} by ORing them together. Its default value is {@link Boolean#FALSE}.
	 * @see #or(CollectionValueModel)
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


	// ********** PVM adapters **********

	/**
	 * Construct a boolean property value model adapter for the specified
	 * collection value model and predicate. The returned model will indicate whether
	 * the collection belongs to the set defined by the predicate.
	 */
	public static PropertyValueModel<Boolean> booleanPropertyValueModel(CollectionValueModel<?> collectionModel, Predicate<? super Collection<?>> predicate) {
		return propertyValueModel(collectionModel, TransformerTools.adapt(predicate));
	}


	/**
	 * Construct a property value model adapted to the specified
	 * collection value model and transformer.
	 * @see PluggablePropertyValueModel
	 */
	public static <E, V> PropertyValueModel<V> propertyValueModel(CollectionValueModel<? extends E> collectionModel, Transformer<? super Collection<E>, V> transformer) {
		return PropertyValueModelTools.propertyValueModel(pluggablePropertyValueModelAdapterFactory(collectionModel, transformer));
	}

	/**
	 * Construct a pluggable property value model adapter factory for the specified
	 * collection value model and transformer.
	 * @see PluggablePropertyValueModel
	 */
	public static <E, V> PluggablePropertyValueModel.Adapter.Factory<V> pluggablePropertyValueModelAdapterFactory(CollectionValueModel<? extends E> collectionModel, Transformer<? super Collection<E>, V> transformer) {
		return new CollectionPluggablePropertyValueModelAdapter.Factory<>(collectionModel, transformer);
	}

	/**
	 * Construct a <em>modifiable</em> property value model adapted to the specified
	 * collection value model that returns whether the collection belongs to the set defined
	 * by the specified predicate.
	 * This model will use the specified collection mutator to modify the collection value model.
	 */
	public static ModifiablePropertyValueModel<Boolean> booleanModifiablePropertyValueModel(CollectionValueModel<?> collectionModel, Predicate<? super Collection<?>> predicate, BooleanClosure.Adapter collectionMutator) {
		return booleanModifiablePropertyValueModel(collectionModel, TransformerTools.adapt(predicate), collectionMutator);
	}

	/**
	 * Construct a <em>modifiable</em> property value model adapter for the specified
	 * collection value model that returns whether the collection belongs to the set defined
	 * by the specified transformer.
	 * This model will use the specified collection mutator to modify the collection value model.
	 */
	public static ModifiablePropertyValueModel<Boolean> booleanModifiablePropertyValueModel(CollectionValueModel<?> collectionModel, Transformer<? super Collection<?>, Boolean> transformer, BooleanClosure.Adapter collectionMutator) {
		PluggablePropertyValueModel.Adapter.Factory<Boolean> factory = pluggablePropertyValueModelAdapterFactory(collectionModel, transformer);
		Closure<Boolean> closure = ClosureTools.booleanClosure(collectionMutator);
		return PropertyValueModelTools.pluggableModifiablePropertyValueModel(factory, closure);
	}


	// ********** filtering **********

	/**
	 * Construct a collection value model with the specified wrapped collection value model and filter.
	 */
	public static <E> CollectionValueModel<E> filter(CollectionValueModel<? extends E> collectionModel, Predicate<E> filter) {
		return new FilteringCollectionValueModel<>(collectionModel, filter);
	}


	// ********** suppressed constructor **********

	/**
	 * Suppress default constructor, ensuring non-instantiability.
	 */
	private CollectionValueModelTools() {
		super();
		throw new UnsupportedOperationException();
	}
}

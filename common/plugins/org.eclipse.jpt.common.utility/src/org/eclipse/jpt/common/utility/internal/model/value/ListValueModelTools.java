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
import java.util.List;
import org.eclipse.jpt.common.utility.closure.Closure;
import org.eclipse.jpt.common.utility.internal.closure.BooleanClosure;
import org.eclipse.jpt.common.utility.internal.closure.ClosureTools;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerTools;
import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.predicate.Predicate;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * {@link ListValueModel List value model} utility methods.
 */
public final class ListValueModelTools {

	// ********** composite PVMs **********

	/**
	 * Construct a composite property value model adapter for the specified
	 * transformer and property value models.
	 */
	@SafeVarargs
	public static <E, V> PropertyValueModel<V> compositePropertyValueModel(Transformer<? super List<E>, V> transformer, PropertyValueModel<? extends E>... propertyValueModels) {
		return compositePropertyValueModel(Arrays.asList(propertyValueModels), transformer);
	}

	/**
	 * Construct a composite property value model adapter for the specified
	 * property value models and transformer.
	 */
	public static <E, V> PropertyValueModel<V> compositePropertyValueModel(Collection<? extends PropertyValueModel<? extends E>> propertyValueModels, Transformer<? super List<E>, V> transformer) {
		return compositePropertyValueModel(new StaticListValueModel<>(propertyValueModels), transformer);
	}

	/**
	 * Construct a composite property value model adapter for the specified
	 * list value model and transformer.
	 * @see PluggablePropertyValueModel
	 */
	public static <E, V> PropertyValueModel<V> compositePropertyValueModel(ListValueModel<? extends PropertyValueModel<? extends E>> listModel, Transformer<? super List<E>, V> transformer) {
		return PropertyValueModelTools.propertyValueModel(new ListCompositePropertyValueModelAdapter.Factory<>(listModel, transformer));
	}


	// ********** list meta data adapters **********

	/**
	 * Construct a property value model adapter for the specified
	 * list value model.
	 * If the list is empty, the model's value is <code>null</code>;
	 * otherwise, it is the first element in the list.
	 */
	public static <E> PropertyValueModel<E> firstElementPropertyValueModel(ListValueModel<? extends E> listModel) {
		return propertyValueModel(listModel, TransformerTools.collectionFirstElementTransformer());
	}

	/**
	 * Construct a property value model adapter for the specified
	 * list value model.
	 * If the list is empty, the model's value is <code>null</code>;
	 * otherwise, it is the last element in the list.
	 */
	public static <E> PropertyValueModel<E> lastElementPropertyValueModel(ListValueModel<? extends E> listModel) {
		return propertyValueModel(listModel, TransformerTools.collectionLastElementTransformer());
	}

	/**
	 * Construct a property value model adapter for the specified
	 * list value model.
	 * If the list contains <em>only</em> a single element,
	 * the model's value is that element; otherwise,
	 * the model's value is <code>null</code>.
	 */
	public static <E> PropertyValueModel<E> singleElementPropertyValueModel(ListValueModel<? extends E> listModel) {
		return propertyValueModel(listModel, TransformerTools.collectionSingleElementTransformer());
	}

	/**
	 * Construct a property value model adapter for the specified
	 * list value model that returns whether the list is <em>not</em> empty.
	 */
	public static PropertyValueModel<Boolean> isNotEmptyPropertyValueModel(ListValueModel<?> listModel) {
		return propertyValueModel(listModel, TransformerTools.collectionIsNotEmptyTransformer());
	}

	/**
	 * Construct a <em>modifiable</em> property value model adapter for the specified
	 * list value model that returns whether the list is <em>not</em> empty.
	 * This model can also be used to populate or clear the list value model.
	 * The specified list mutator is used to convert the list value model:
	 * it should populate the list value model when passed <code>true</code>
	 * and clear the list value model when passed <code>false</code>.
	 */
	public static ModifiablePropertyValueModel<Boolean> isNotEmptyModifiablePropertyValueModel(ListValueModel<?> listModel, BooleanClosure.Adapter listMutator) {
		return booleanModifiablePropertyValueModel(listModel, TransformerTools.collectionIsNotEmptyTransformer(), listMutator);
	}

	/**
	 * Construct a property value model adapter for the specified
	 * list value model that returns whether the list is empty.
	 */
	public static PropertyValueModel<Boolean> isEmptyPropertyValueModel(ListValueModel<?> listModel) {
		return propertyValueModel(listModel, TransformerTools.collectionIsEmptyTransformer());
	}

	/**
	 * Construct a <em>modifiable</em> property value model adapter for the specified
	 * list value model that returns whether the list is empty.
	 * This model can also be used to populate or clear the list value model.
	 * The specified list mutator is used to convert the list value model:
	 * it should populate the list value model when passed <code>true</code>
	 * and clear the list value model when passed <code>false</code>.
	 */
	public static ModifiablePropertyValueModel<Boolean> isEmptyModifiablePropertyValueModel(ListValueModel<?> listModel, BooleanClosure.Adapter listMutator) {
		return booleanModifiablePropertyValueModel(listModel, TransformerTools.collectionIsEmptyTransformer(), listMutator);
	}

	/**
	 * Construct a property value model adapter for the specified
	 * list value model that returns whether the list contains
	 * exactly one element.
	 */
	public static PropertyValueModel<Boolean> containsSingleElementPropertyValueModel(ListValueModel<?> listModel) {
		return propertyValueModel(listModel, TransformerTools.collectionContainsSingleElementTransformer());
	}

	/**
	 * Construct a property value model adapter for the specified
	 * list value model that returns whether the list's size
	 * equals the specified size.
	 */
	public static PropertyValueModel<Boolean> sizeEqualsPropertyValueModel(ListValueModel<?> listModel, int size) {
		return propertyValueModel(listModel, TransformerTools.collectionSizeEqualsTransformer(size));
	}

	// ********** PVM adapters **********

	/**
	 * Construct a boolean property value model adapter for the specified
	 * list value model and predicate. The returned model will indicate whether
	 * the list belongs to the set defined by the predicate.
	 */
	public static PropertyValueModel<Boolean> booleanPropertyValueModel(ListValueModel<?> listModel, Predicate<? super List<?>> predicate) {
		return propertyValueModel(listModel, TransformerTools.adapt(predicate));
	}

	/**
	 * Construct a property value model adapted to the specified
	 * list value model and transformer.
	 * @see PluggablePropertyValueModel
	 */
	public static <E, V> PropertyValueModel<V> propertyValueModel(ListValueModel<? extends E> listModel, Transformer<? super List<E>, V> transformer) {
		return PropertyValueModelTools.propertyValueModel(pluggablePropertyValueModelAdapterFactory(listModel, transformer));
	}

	/**
	 * Construct a pluggable property value model adapter factory for the specified
	 * list value model and transformer.
	 * @see PluggablePropertyValueModel
	 */
	public static <E, V> PluggablePropertyValueModel.Adapter.Factory<V> pluggablePropertyValueModelAdapterFactory(ListValueModel<? extends E> listModel, Transformer<? super List<E>, V> transformer) {
		return new ListPluggablePropertyValueModelAdapter.Factory<>(listModel, transformer);
	}

	/**
	 * Construct a <em>modifiable</em> property value model adapter for the specified
	 * list value model that returns whether the list belongs to the set defined
	 * by the specified predicate.
	 * This model will use the specified list mutator to modify the list value model.
	 */
	public static ModifiablePropertyValueModel<Boolean> booleanModifiablePropertyValueModel(ListValueModel<?> listModel, Predicate<? super Collection<?>> predicate, BooleanClosure.Adapter listMutator) {
		return booleanModifiablePropertyValueModel(listModel, TransformerTools.adapt(predicate), listMutator);
	}

	/**
	 * Construct a <em>modifiable</em> property value model adapted to the specified
	 * list value model that returns whether the list belongs to the set defined
	 * by the specified transformer.
	 * This model will use the specified list mutator to modify the list value model.
	 */
	public static ModifiablePropertyValueModel<Boolean> booleanModifiablePropertyValueModel(ListValueModel<?> listModel, Transformer<? super Collection<?>, Boolean> transformer, BooleanClosure.Adapter listMutator) {
		PluggablePropertyValueModel.Adapter.Factory<Boolean> factory = pluggablePropertyValueModelAdapterFactory(listModel, transformer);
		Closure<Boolean> closure = ClosureTools.booleanClosure(listMutator);
		return PropertyValueModelTools.pluggableModifiablePropertyValueModel(factory, closure);
	}


	// ********** filtering **********

	/**
	 * Construct a collection value model with the specified wrapped list value model and filter.
	 */
	public static <E> CollectionValueModel<E> filter(ListValueModel<? extends E> listModel, Predicate<? super E> filter) {
		return CollectionValueModelTools.filter(new ListCollectionValueModelAdapter<E>(listModel), filter);
	}


	// ********** suppressed constructor **********

	/**
	 * Suppress default constructor, ensuring non-instantiability.
	 */
	private ListValueModelTools() {
		super();
		throw new UnsupportedOperationException();
	}
}

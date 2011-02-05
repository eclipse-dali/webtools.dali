/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.model.value;

import java.util.Collection;

import org.eclipse.jpt.common.utility.internal.iterables.TransformationIterable;
import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;

/**
 * A <code>CompositeBooleanPropertyValueModel</code> adapts a
 * {@link CollectionValueModel} holding boolean {@link PropertyValueModel}s
 * to a single boolean {@link PropertyValueModel}. The composite value is
 * determined by the {@link CompositeBooleanPropertyValueModel.Adapter} provided
 * at construction. Typically the composite will be either an AND or an OR of
 * all the boolean values.
 * <p>
 * If there are <em>no</em> boolean {@link PropertyValueModel}s, the composite
 * will return its default value; which, by default, is <code>null</code>.
 * <p>
 * <strong>NB:</strong> None of the wrapped boolean models can return a
 * <code>null</code> value or this class will throw an exception. The assumption
 * is that all the wrapped boolean models are configured with "default" values
 * that determine the model's value (TRUE or FALSE) when <code>null</code>.
 */
public class CompositeBooleanPropertyValueModel
	extends CompositePropertyValueModel<Boolean>
{
	/**
	 * Calculation of the model's value is delegated to this adapter.
	 */
	protected final Adapter adapter;

	/**
	 * The default setting for the composite boolean property value model;
	 * for when the underlying collection model is empty.
	 * The default [default value] is <code>null</code>.
	 */
	private final Boolean defaultValue;


	// ********** AND factory methods **********

	/**
	 * Construct a boolean property value model that is a composite AND of the
	 * specified boolean property value models; i.e. the model's value is true
	 * if all the contained models are true, otherwise its value is false.
	 * The model's default value, when it contains no nested models, is
	 * <code>null</code>.
	 */
	public static CompositeBooleanPropertyValueModel and(PropertyValueModel<Boolean>... array) {
		return new CompositeBooleanPropertyValueModel(AND_ADAPTER, array);
	}

	/**
	 * Construct a boolean property value model that is a composite AND of the
	 * specified boolean property value models; i.e. the model's value is true
	 * if all the contained models are true, otherwise its value is false.
	 * When the model contains no nested models, its value will be the
	 * specified default.
	 */
	public static CompositeBooleanPropertyValueModel and(Boolean defaultValue, PropertyValueModel<Boolean>... array) {
		return new CompositeBooleanPropertyValueModel(AND_ADAPTER, defaultValue, array);
	}

	/**
	 * Construct a boolean property value model that is a composite AND of the
	 * specified boolean property value models; i.e. the model's value is true
	 * if all the contained models are true, otherwise its value is false.
	 * The model's default value, when it contains no nested models, is
	 * <code>null</code>.
	 */
	public static <E extends PropertyValueModel<Boolean>> CompositeBooleanPropertyValueModel and(Collection<E> collection) {
		return new CompositeBooleanPropertyValueModel(AND_ADAPTER, collection);
	}

	/**
	 * Construct a boolean property value model that is a composite AND of the
	 * specified boolean property value models; i.e. the model's value is true
	 * if all the contained models are true, otherwise its value is false.
	 * When the model contains no nested models, its value will be the
	 * specified default.
	 */
	public static <E extends PropertyValueModel<Boolean>> CompositeBooleanPropertyValueModel and(Boolean defaultValue, Collection<E> collection) {
		return new CompositeBooleanPropertyValueModel(AND_ADAPTER, defaultValue, collection);
	}

	/**
	 * Construct a boolean property value model that is a composite AND of the
	 * specified boolean property value models; i.e. the model's value is true
	 * if all the contained models are true, otherwise its value is false.
	 * The model's default value, when it contains no nested models, is
	 * <code>null</code>.
	 */
	public static CompositeBooleanPropertyValueModel and(CollectionValueModel<? extends PropertyValueModel<Boolean>> collectionModel) {
		return new CompositeBooleanPropertyValueModel(AND_ADAPTER, collectionModel);
	}

	/**
	 * Construct a boolean property value model that is a composite AND of the
	 * specified boolean property value models; i.e. the model's value is true
	 * if all the contained models are true, otherwise its value is false.
	 * When the model contains no nested models, its value will be the
	 * specified default.
	 */
	public static CompositeBooleanPropertyValueModel and(Boolean defaultValue, CollectionValueModel<? extends PropertyValueModel<Boolean>> collectionModel) {
		return new CompositeBooleanPropertyValueModel(AND_ADAPTER, defaultValue, collectionModel);
	}


	// ********** OR factory methods **********

	/**
	 * Construct a boolean property value model that is a composite OR of the
	 * specified boolean property value models; i.e. the model's value is false
	 * if all the contained models are false, otherwise its value is true.
	 * The model's default value, when it contains no nested models, is
	 * <code>null</code>.
	 */
	public static CompositeBooleanPropertyValueModel or(PropertyValueModel<Boolean>... array) {
		return new CompositeBooleanPropertyValueModel(OR_ADAPTER, array);
	}

	/**
	 * Construct a boolean property value model that is a composite OR of the
	 * specified boolean property value models; i.e. the model's value is false
	 * if all the contained models are false, otherwise its value is true.
	 * When the model contains no nested models, its value will be the
	 * specified default.
	 */
	public static CompositeBooleanPropertyValueModel or(Boolean defaultValue, PropertyValueModel<Boolean>... array) {
		return new CompositeBooleanPropertyValueModel(OR_ADAPTER, defaultValue, array);
	}

	/**
	 * Construct a boolean property value model that is a composite OR of the
	 * specified boolean property value models; i.e. the model's value is false
	 * if all the contained models are false, otherwise its value is true.
	 * The model's default value, when it contains no nested models, is
	 * <code>null</code>.
	 */
	public static <E extends PropertyValueModel<Boolean>> CompositeBooleanPropertyValueModel or(Collection<E> collection) {
		return new CompositeBooleanPropertyValueModel(OR_ADAPTER, collection);
	}

	/**
	 * Construct a boolean property value model that is a composite OR of the
	 * specified boolean property value models; i.e. the model's value is false
	 * if all the contained models are false, otherwise its value is true.
	 * When the model contains no nested models, its value will be the
	 * specified default.
	 */
	public static <E extends PropertyValueModel<Boolean>> CompositeBooleanPropertyValueModel or(Boolean defaultValue, Collection<E> collection) {
		return new CompositeBooleanPropertyValueModel(OR_ADAPTER, defaultValue, collection);
	}

	/**
	 * Construct a boolean property value model that is a composite OR of the
	 * specified boolean property value models; i.e. the model's value is false
	 * if all the contained models are false, otherwise its value is true.
	 * The model's default value, when it contains no nested models, is
	 * <code>null</code>.
	 */
	public static CompositeBooleanPropertyValueModel or(CollectionValueModel<? extends PropertyValueModel<Boolean>> collectionModel) {
		return new CompositeBooleanPropertyValueModel(OR_ADAPTER, collectionModel);
	}

	/**
	 * Construct a boolean property value model that is a composite OR of the
	 * specified boolean property value models; i.e. the model's value is false
	 * if all the contained models are false, otherwise its value is true.
	 * When the model contains no nested models, its value will be the
	 * specified default.
	 */
	public static CompositeBooleanPropertyValueModel or(Boolean defaultValue, CollectionValueModel<? extends PropertyValueModel<Boolean>> collectionModel) {
		return new CompositeBooleanPropertyValueModel(OR_ADAPTER, defaultValue, collectionModel);
	}


	// ********** constructors **********

	/**
	 * Construct a boolean property value model that is a composite of the specified
	 * boolean property value models, as defined by the specified adapter.
	 * The model's default value, when it contains no nested models, is
	 * <code>null</code>.
	 */
	public CompositeBooleanPropertyValueModel(Adapter adapter, PropertyValueModel<Boolean>... array) {
		this(adapter, null, array);
	}

	/**
	 * Construct a boolean property value model that is a composite of the specified
	 * boolean property value models, as defined by the specified adapter.
	 * When the model contains no nested models, its value will be the
	 * specified default.
	 */
	public CompositeBooleanPropertyValueModel(Adapter adapter, Boolean defaultValue, PropertyValueModel<Boolean>... array) {
		super(array);
		this.adapter = adapter;
		this.defaultValue = defaultValue;
	}

	/**
	 * Construct a boolean property value model that is a composite of the specified
	 * boolean property value models, as defined by the specified adapter.
	 * The model's default value, when it contains no nested models, is
	 * <code>null</code>.
	 */
	public <E extends PropertyValueModel<Boolean>> CompositeBooleanPropertyValueModel(Adapter adapter, Collection<E> collection) {
		this(adapter, null, collection);
	}

	/**
	 * Construct a boolean property value model that is a composite of the specified
	 * boolean property value models, as defined by the specified adapter.
	 * When the model contains no nested models, its value will be the
	 * specified default.
	 */
	public <E extends PropertyValueModel<Boolean>> CompositeBooleanPropertyValueModel(Adapter adapter, Boolean defaultValue, Collection<E> collection) {
		super(collection);
		this.adapter = adapter;
		this.defaultValue = defaultValue;
	}

	/**
	 * Construct a boolean property value model that is a composite of the specified
	 * boolean property value models, as defined by the specified adapter.
	 * The model's default value, when it contains no nested models, is
	 * <code>null</code>.
	 */
	public CompositeBooleanPropertyValueModel(Adapter adapter, CollectionValueModel<? extends PropertyValueModel<Boolean>> collectionModel) {
		this(adapter, null, collectionModel);
	}

	/**
	 * Construct a boolean property value model that is a composite of the specified
	 * boolean property value models, as defined by the specified adapter.
	 * When the model contains no nested models, its value will be the
	 * specified default.
	 */
	public CompositeBooleanPropertyValueModel(Adapter adapter, Boolean defaultValue, CollectionValueModel<? extends PropertyValueModel<Boolean>> collectionModel) {
		super(collectionModel);
		this.adapter = adapter;
		this.defaultValue = defaultValue;
	}


	// ********** implementation **********

	/**
	 * Return true if all the contained booleans are true; otherwise return
	 * false.
	 */
	@Override
	protected Boolean buildValue() {
		return (this.collectionModel.size() == 0) ? this.defaultValue : this.buildValue_();
	}

	protected Boolean buildValue_() {
		return this.adapter.buildValue(this.getBooleans());
	}

	protected Iterable<Boolean> getBooleans() {
		return new TransformationIterable<PropertyValueModel<Boolean>, Boolean>(this.getCollectionModel()) {
			@Override
			protected Boolean transform(PropertyValueModel<Boolean> booleanModel) {
				return booleanModel.getValue();
			}
		};
	}

	@Override
	@SuppressWarnings("unchecked")
	protected CollectionValueModel<? extends PropertyValueModel<Boolean>> getCollectionModel() {
		return (CollectionValueModel<? extends PropertyValueModel<Boolean>>) super.getCollectionModel();
	}


	// ********** adapter **********

	/**
	 * This adapter allows the {@link CompositeBooleanPropertyValueModel} to be
	 * pluggable.
	 */
	public interface Adapter {
		/**
		 * Return the composite boolean value of the specified collection
		 * of boolean models.
		 */
		Boolean buildValue(Iterable<Boolean> booleans);
	}

	/**
	 * Return true if all the booleans are true; otherwise return false.
	 */
	public static final Adapter AND_ADAPTER = new Adapter() {
		public Boolean buildValue(Iterable<Boolean> booleans) {
			for (Boolean b : booleans) {
				if ( ! b.booleanValue()) {
					return Boolean.FALSE;
				}
			}
			return Boolean.TRUE;
		}
		@Override
		public String toString() {
			return "AND_ADAPTER"; //$NON-NLS-1$
		}
	};

	/**
	 * Return false if all the booleans are false; otherwise return true.
	 */
	public static final Adapter OR_ADAPTER = new Adapter() {
		public Boolean buildValue(Iterable<Boolean> booleans) {
			for (Boolean b : booleans) {
				if (b.booleanValue()) {
					return Boolean.TRUE;
				}
			}
			return Boolean.FALSE;
		}
		@Override
		public String toString() {
			return "OR_ADAPTER"; //$NON-NLS-1$
		}
	};

}

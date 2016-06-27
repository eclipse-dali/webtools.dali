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
import java.util.Collections;
import java.util.LinkedList;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.collection.CollectionTools;
import org.eclipse.jpt.common.utility.internal.model.value.PluggablePropertyValueModel.Adapter;
import org.eclipse.jpt.common.utility.model.event.CollectionAddEvent;
import org.eclipse.jpt.common.utility.model.event.CollectionChangeEvent;
import org.eclipse.jpt.common.utility.model.event.CollectionClearEvent;
import org.eclipse.jpt.common.utility.model.event.CollectionRemoveEvent;
import org.eclipse.jpt.common.utility.model.listener.CollectionChangeListener;
import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * Adapt a {@link CollectionValueModel collection value model} to
 * a {@link PropertyValueModel property value model}, sorta.
 * <p>
 * This adapter is constructed with a {@link CollectionValueModel
 * collection value model} and a {@link Transformer transformer} that can
 * transform the collection to a single value.
 * <p>
 * This is an adapter that can be plugged into a {@link PluggablePropertyValueModel}.
 * 
 * @param <E> the type of the adapted collection value model's elements
 * @param <V> the type of the model's derived value
 * 
 * @see PluggablePropertyValueModel
 */
public final class CollectionPluggablePropertyValueModelAdapter<E, V>
	implements PluggablePropertyValueModel.Adapter<V>, CollectionChangeListener
{
	/** The wrapped model */
	private final CollectionValueModel<? extends E> collectionModel;

	/** Transformer that converts the wrapped model's value to this model's value. */
	private final Transformer<? super Collection<E>, V> transformer;

	/** The <em>real</em> adapter. */
	private final AbstractPluggablePropertyValueModel.Adapter.Listener<V> listener;

	/** Cached copy of model's elements. */
	private final LinkedList<E> collection;

	/** Protects {@link #collection} from {@link Factory#transformer}. */
	private final Collection<E> unmodifiableCollection;

	/** The derived value. */
	private volatile V value;


	// ********** constructors **********

	public CollectionPluggablePropertyValueModelAdapter(Factory<E, V> factory, AbstractPluggablePropertyValueModel.Adapter.Listener<V> listener) {
		super();
		if (factory == null) {
			throw new NullPointerException();
		}
		this.collectionModel = factory.collectionModel;
		this.transformer = factory.transformer;
		if (listener == null) {
			throw new NullPointerException();
		}
		this.listener = listener;
		this.collection = new LinkedList<>();
		this.unmodifiableCollection = Collections.unmodifiableCollection(this.collection);
	}


	// ********** PluggablePropertyValueModel.Adapter **********

	public V getValue() {
		return this.value;
	}

	public void engageModel() {
		this.collectionModel.addCollectionChangeListener(CollectionValueModel.VALUES, this);
		CollectionTools.addAll(this.collection, this.collectionModel);
		this.value = this.buildValue();
	}

	public void disengageModel() {
		this.value = null;
		this.collection.clear();
		this.collectionModel.removeCollectionChangeListener(CollectionValueModel.VALUES, this);
	}


	// ********** CollectionChangeListener **********

	@SuppressWarnings("unchecked")
	public void itemsAdded(CollectionAddEvent event) {
		CollectionTools.addAll(this.collection, (Iterable<E>) event.getItems());
		this.update();
	}

	public void itemsRemoved(CollectionRemoveEvent event) {
		CollectionTools.removeAll(this.collection, event.getItems());
		this.update();
	}

	public void collectionCleared(CollectionClearEvent event) {
		this.collection.clear();
		this.update();
	}

	@SuppressWarnings("unchecked")
	public void collectionChanged(CollectionChangeEvent event) {
		this.collection.clear();
		CollectionTools.addAll(this.collection, (Iterable<E>) event.getCollection());
		this.update();
	}


	// ********** misc **********

	private void update() {
		this.listener.valueChanged(this.value = this.buildValue());
	}

	private V buildValue() {
		return this.transformer.transform(this.unmodifiableCollection);
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.value);
	}


	// ********** PluggablePropertyValueModel.Adapter.Factory **********

	public static class Factory<E, V>
		implements PluggablePropertyValueModel.Adapter.Factory<V>
	{
		/* CU private */ final CollectionValueModel<? extends E> collectionModel;
		/* CU private */ final Transformer<? super Collection<E>, V> transformer;

		public Factory(CollectionValueModel<? extends E> collectionModel, Transformer<? super Collection<E>, V> transformer) {
			super();
			if (collectionModel == null) {
				throw new NullPointerException();
			}
			this.collectionModel = collectionModel;
			if (transformer == null) {
				throw new NullPointerException();
			}
			this.transformer = transformer;
		}

		public Adapter<V> buildAdapter(AbstractPluggablePropertyValueModel.Adapter.Listener<V> listener) {
			return new CollectionPluggablePropertyValueModelAdapter<>(this, listener);
		}

		@Override
		public String toString() {
			return ObjectTools.toString(this);
		}
	}
}

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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.IdentityHashMap;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.collection.ListTools;
import org.eclipse.jpt.common.utility.internal.model.value.PluggablePropertyValueModel.Adapter;
import org.eclipse.jpt.common.utility.model.event.CollectionAddEvent;
import org.eclipse.jpt.common.utility.model.event.CollectionChangeEvent;
import org.eclipse.jpt.common.utility.model.event.CollectionClearEvent;
import org.eclipse.jpt.common.utility.model.event.CollectionRemoveEvent;
import org.eclipse.jpt.common.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.CollectionChangeListener;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeAdapter;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * Adapt a {@link CollectionValueModel collection value model} holding
 * {@link PropertyValueModel property value models}
 * to a single {@link PropertyValueModel property value model}, sorta.
 * <p>
 * This adapter is constructed with a {@link CollectionValueModel
 * collection value model} and a {@link Transformer transformer} that can
 * transform the collection's property value models' values to a single value.
 * <p>
 * This is an adapter that can be plugged into a {@link PluggablePropertyValueModel}.
 * <p>
 * <strong>NB:</strong> The wrapped collection value model must not contain any
 * <code>null</code>s or duplicate property value models.
 * 
 * @param <E> the type of the adapted collection value model's
 *     property value models' values
 * @param <V> the type of the model's derived value
 * 
 * @see PluggablePropertyValueModel
 * @see CollectionPluggablePropertyValueModelAdapter
 */
public final class CompositePropertyValueModelAdapter<E, V>
	implements PluggablePropertyValueModel.Adapter<V>, CollectionChangeListener
{
	/** The wrapped model */
	private final CollectionValueModel<? extends PropertyValueModel<? extends E>> collectionModel;

	/** Transformer that converts the wrapped model's value to this model's value. */
	private final Transformer<? super Collection<E>, V> transformer;

	/**
	 * The <em>real</em> adapter, passed to us as a listener.
	 */
	private final BasePluggablePropertyValueModel.Adapter.Listener<V> listener;

	/**
	 * Listen to every property value model in the collection value model.
	 * If one changes, we need to re-calculate {@link #value}
	 * and notify @{link #listener}.
	 */
	private final PropertyChangeListener componentListener;

	/**
	 * Cached copy of {@link Factory#collectionModel}'s elements
	 * and their values.
	 */
	private final IdentityHashMap<PropertyValueModel<? extends E>, E> values;

	/**
	 * Protects {@link #values} from {@link Factory#transformer}.
	 */
	private final Collection<E> unmodifiableValues;

	/**
	 * The derived value.
	 */
	private volatile V value;


	// ********** constructor **********

	public CompositePropertyValueModelAdapter(Factory<E, V> factory, BasePluggablePropertyValueModel.Adapter.Listener<V> listener) {
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
		this.componentListener = this.buildComponentListener();
		this.values = new IdentityHashMap<>();
		this.unmodifiableValues = Collections.unmodifiableCollection(this.values.values());
	}


	// ********** PluggablePropertyValueModel.Adapter **********

	public V engageModel() {
		this.collectionModel.addCollectionChangeListener(CollectionValueModel.VALUES, this);
		this.addComponentPVMs(this.collectionModel);
		return this.value = this.buildValue();
	}

	public V disengageModel() {
		this.collectionModel.removeCollectionChangeListener(CollectionValueModel.VALUES, this);
		this.removeCachedPVMs();
		return this.value = null;
	}


	// ********** CollectionChangeListener **********

	@SuppressWarnings("unchecked")
	public void itemsAdded(CollectionAddEvent event) {
		this.addComponentPVMs((Iterable<? extends PropertyValueModel<? extends E>>) event.getItems());
		this.update();
	}

	@SuppressWarnings("unchecked")
	public void itemsRemoved(CollectionRemoveEvent event) {
		this.removeComponentPVMs((Iterable<? extends PropertyValueModel<? extends E>>) event.getItems());
		this.update();
	}

	public void collectionCleared(CollectionClearEvent event) {
		this.removeCachedPVMs();
		this.update();
	}

	@SuppressWarnings("unchecked")
	public void collectionChanged(CollectionChangeEvent event) {
		this.removeCachedPVMs();
		this.addComponentPVMs((Iterable<? extends PropertyValueModel<? extends E>>) event.getCollection());
		this.update();
	}


	// ********** add/remove component PVMs **********

	private void addComponentPVMs(Iterable<? extends PropertyValueModel<? extends E>> pvms) {
		for (PropertyValueModel<? extends E> pvm : pvms) {
			this.addComponentPVM(pvm);
		}
	}

	private void addComponentPVM(PropertyValueModel<? extends E> pvm) {
		if (pvm == null) {
			throw new NullPointerException();
		}
		if (this.values.containsKey(pvm)) {
			throw new IllegalStateException("duplicate component: " + pvm); //$NON-NLS-1$
		}
		pvm.addPropertyChangeListener(PropertyValueModel.VALUE, this.componentListener);
		this.values.put(pvm, pvm.getValue());
	}

	private void removeCachedPVMs() {
		// copy the list so we don't eat our own tail
		ArrayList<PropertyValueModel<? extends E>> copy = ListTools.arrayList(this.values.keySet());
		this.removeComponentPVMs(copy);
	}

	private void removeComponentPVMs(Iterable<? extends PropertyValueModel<? extends E>> pvms) {
		for (PropertyValueModel<? extends E> pvm : pvms) {
			this.removeComponentPVM(pvm);
		}
	}

	private void removeComponentPVM(PropertyValueModel<? extends E> pvm) {
		if ( ! this.values.containsKey(pvm)) {
			throw new IllegalStateException("missing component: " + pvm); //$NON-NLS-1$
		}
		this.values.remove(pvm);
		pvm.removePropertyChangeListener(PropertyValueModel.VALUE, this.componentListener);
	}


	// ********** misc **********

	private void update() {
		this.listener.valueChanged(this.value = this.buildValue());
	}

	private V buildValue() {
		return this.transformer.transform(this.unmodifiableValues);
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.value);
	}


	// ********** component listener **********

	private PropertyChangeListener buildComponentListener() {
		return new ComponentListener();
	}

	/* CU private */ class ComponentListener
		extends PropertyChangeAdapter
	{
		@Override
		public void propertyChanged(PropertyChangeEvent event) {
			CompositePropertyValueModelAdapter.this.componentChanged(event);
		}
	}

	/* CU private */ void componentChanged(PropertyChangeEvent event) {
		@SuppressWarnings("unchecked")
		PropertyValueModel<? extends E> source = (PropertyValueModel<? extends E>) event.getSource();
		if ( ! this.values.containsKey(source)) {
			throw new IllegalStateException("invalid component: " + source); //$NON-NLS-1$
		}
		@SuppressWarnings("unchecked")
		E newValue = (E) event.getNewValue();
		this.values.put(source, newValue);
		this.update();
	}


	// ********** PluggablePropertyValueModel.Adapter.Factory **********

	public static class Factory<E, V>
		implements PluggablePropertyValueModel.Adapter.Factory<V>
	{
		/* CU private */ final CollectionValueModel<? extends PropertyValueModel<? extends E>> collectionModel;
		/* CU private */ final Transformer<? super Collection<E>, V> transformer;

		public Factory(CollectionValueModel<? extends PropertyValueModel<? extends E>> collectionModel, Transformer<? super Collection<E>, V> transformer) {
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

		public Adapter<V> buildAdapter(BasePluggablePropertyValueModel.Adapter.Listener<V> listener) {
			return new CompositePropertyValueModelAdapter<>(this, listener);
		}

		@Override
		public String toString() {
			return ObjectTools.toString(this);
		}
	}
}

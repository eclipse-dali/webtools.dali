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
import java.util.Iterator;
import java.util.List;
import org.eclipse.jpt.common.utility.Association;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.SimpleAssociation;
import org.eclipse.jpt.common.utility.internal.collection.ListTools;
import org.eclipse.jpt.common.utility.internal.model.value.PluggablePropertyValueModel.Adapter;
import org.eclipse.jpt.common.utility.model.event.ListAddEvent;
import org.eclipse.jpt.common.utility.model.event.ListChangeEvent;
import org.eclipse.jpt.common.utility.model.event.ListClearEvent;
import org.eclipse.jpt.common.utility.model.event.ListMoveEvent;
import org.eclipse.jpt.common.utility.model.event.ListRemoveEvent;
import org.eclipse.jpt.common.utility.model.event.ListReplaceEvent;
import org.eclipse.jpt.common.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.ListChangeListener;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeAdapter;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * Adapt a {@link ListValueModel list value model} holding
 * {@link PropertyValueModel property value models}
 * to a single {@link PropertyValueModel property value model}, sorta.
 * <p>
 * This adapter is constructed with a {@link ListValueModel
 * list value model} and a {@link Transformer transformer} that can
 * transform the list's property value models' values to a single value.
 * <p>
 * This is an adapter that can be plugged into a {@link PluggablePropertyValueModel}.
 * <p>
 * <strong>NB:</strong> The wrapped list value model must not contain any
 * <code>null</code>s or duplicate property value models.
 * 
 * @param <E> the type of the adapted list value model's
 *     property value models' values
 * @param <V> the type of the model's derived value
 * 
 * @see PluggablePropertyValueModel
 * @see ListTransformationPluggablePropertyValueModelAdapter
 */
public final class ListCompositePropertyValueModelAdapter<E, V>
	implements PluggablePropertyValueModel.Adapter<V>, ListChangeListener
{
	/** The wrapped model */
	private final ListValueModel<? extends PropertyValueModel<? extends E>> listModel;

	/** Transformer that converts the wrapped model's value to this model's value. */
	private final Transformer<? super List<E>, V> transformer;

	/**
	 * The <em>real</em> adapter, passed to us as a listener.
	 */
	private final BasePluggablePropertyValueModel.Adapter.Listener<V> listener;

	/**
	 * Listen to every property value model in the list value model.
	 * If one changes, we need to re-calculate {@link #value}
	 * and notify @{link #listener}.
	 */
	private final PropertyChangeListener componentListener;

	/**
	 * Cached copy of {@link Factory#listModel}'s elements' values.
	 */
	private final ArrayList<SimpleAssociation<PropertyValueModel<? extends E>, E>> values;

	/**
	 * The derived value.
	 */
	private volatile V value;


	// ********** constructor **********

	public ListCompositePropertyValueModelAdapter(Factory<E, V> factory, BasePluggablePropertyValueModel.Adapter.Listener<V> listener) {
		super();
		if (factory == null) {
			throw new NullPointerException();
		}
		this.listModel = factory.listModel;
		this.transformer = factory.transformer;
		if (listener == null) {
			throw new NullPointerException();
		}
		this.listener = listener;
		this.componentListener = this.buildComponentListener();
		this.values = new ArrayList<>();
	}


	// ********** PluggablePropertyValueModel.Adapter **********

	public V engageModel() {
		this.listModel.addListChangeListener(ListValueModel.LIST_VALUES, this);
		this.addComponentPVMs(0, this.listModel);
		return this.value = this.buildValue();
	}

	public V disengageModel() {
		this.listModel.removeListChangeListener(ListValueModel.LIST_VALUES, this);
		this.removeCachedPVMs();
		return this.value = null;
	}


	// ********** ListChangeListener **********

	@SuppressWarnings("unchecked")
	public void itemsAdded(ListAddEvent event) {
		this.addComponentPVMs(event.getIndex(), (Iterable<? extends PropertyValueModel<? extends E>>) event.getItems());
		this.update();
	}

	@SuppressWarnings("unchecked")
	public void itemsRemoved(ListRemoveEvent event) {
		this.removeComponentPVMs(event.getIndex(), event.getItemsSize(), (Iterable<? extends PropertyValueModel<? extends E>>) event.getItems());
		this.update();
	}

	public void itemsMoved(ListMoveEvent event) {
		ListTools.move(this.values, event.getTargetIndex(), event.getSourceIndex(), event.getLength());
		this.update();
	}

	@SuppressWarnings("unchecked")
	public void itemsReplaced(ListReplaceEvent event) {
		this.removeComponentPVMs(event.getIndex(), event.getItemsSize(), (Iterable<? extends PropertyValueModel<? extends E>>) event.getOldItems());
		this.addComponentPVMs(event.getIndex(), (Iterable<? extends PropertyValueModel<? extends E>>) event.getNewItems());
		this.update();
	}

	public void listCleared(ListClearEvent event) {
		this.removeCachedPVMs();
		this.update();
	}

	@SuppressWarnings("unchecked")
	public void listChanged(ListChangeEvent event) {
		this.removeCachedPVMs();
		this.addComponentPVMs(0, (Iterable<? extends PropertyValueModel<? extends E>>) event.getList());
		this.update();
	}


	// ********** add/remove component PVMs **********

	private void addComponentPVMs(int index, Iterable<? extends PropertyValueModel<? extends E>> pvms) {
		for (PropertyValueModel<? extends E> pvm : pvms) {
			this.addComponentPVM(index++, pvm);
		}
	}

	private void addComponentPVM(int index, PropertyValueModel<? extends E> pvm) {
		if (pvm == null) {
			throw new NullPointerException();
		}
		for (SimpleAssociation<PropertyValueModel<? extends E>, E> each : this.values) {
			if (each.getKey() == pvm) {
				throw new IllegalStateException("duplicate component: " + pvm); //$NON-NLS-1$
			}
		}
		pvm.addPropertyChangeListener(PropertyValueModel.VALUE, this.componentListener);
		this.values.add(index, new SimpleAssociation<>(pvm, pvm.getValue()));
	}

	private void removeCachedPVMs() {
		@SuppressWarnings("unchecked")
		Transformer<SimpleAssociation<PropertyValueModel<? extends E>, E>, PropertyValueModel<? extends E>> t = Association.KEY_TRANSFORMER;
		this.removeComponentPVMs(this.values, ListTools.transform(this.values, t));
	}

	private void removeComponentPVMs(int index, int length, Iterable<? extends PropertyValueModel<? extends E>> expectedPVMs) {
		this.removeComponentPVMs(this.values.subList(index, index + length), expectedPVMs);
	}

	private void removeComponentPVMs(List<SimpleAssociation<PropertyValueModel<? extends E>, E>> subList, Iterable<? extends PropertyValueModel<? extends E>> expectedPVMs) {
		Iterator<? extends PropertyValueModel<? extends E>> stream = expectedPVMs.iterator();
		for (SimpleAssociation<PropertyValueModel<? extends E>, E> each : subList) {
			PropertyValueModel<? extends E> pvm = each.getKey();
			PropertyValueModel<? extends E> expectedPVM = stream.next();
			if (pvm != expectedPVM) {
				throw new IllegalStateException("inconsistent component: " + pvm + " - expected: " + expectedPVM); //$NON-NLS-1$ //$NON-NLS-2$
			}
			pvm.removePropertyChangeListener(PropertyValueModel.VALUE, this.componentListener);
		}
		subList.clear();
	}


	// ********** misc **********

	private void update() {
		this.listener.valueChanged(this.value = this.buildValue());
	}

	private V buildValue() {
		@SuppressWarnings("unchecked")
		Transformer<SimpleAssociation<PropertyValueModel<? extends E>, E>, E> t = Association.VALUE_TRANSFORMER;
		return this.transformer.transform(ListTools.transform(this.values, t));
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
			ListCompositePropertyValueModelAdapter.this.componentChanged(event);
		}
	}

	/* CU private */ void componentChanged(PropertyChangeEvent event) {
		@SuppressWarnings("unchecked")
		PropertyValueModel<? extends E> source = (PropertyValueModel<? extends E>) event.getSource();
		for (SimpleAssociation<PropertyValueModel<? extends E>, E> each : this.values) {
			if (each.getKey() == source) {
				@SuppressWarnings("unchecked")
				E newValue = (E) event.getNewValue();
				each.setValue(newValue);
				this.update();
				return;
			}
		}
		throw new IllegalStateException("invalid component: " + source); //$NON-NLS-1$
	}


	// ********** PluggablePropertyValueModel.Adapter.Factory **********

	public static class Factory<E, V>
		implements PluggablePropertyValueModel.Adapter.Factory<V>
	{
		/* CU private */ final ListValueModel<? extends PropertyValueModel<? extends E>> listModel;
		/* CU private */ final Transformer<? super List<E>, V> transformer;

		public Factory(ListValueModel<? extends PropertyValueModel<? extends E>> listModel, Transformer<? super List<E>, V> transformer) {
			super();
			if (listModel == null) {
				throw new NullPointerException();
			}
			this.listModel = listModel;
			if (transformer == null) {
				throw new NullPointerException();
			}
			this.transformer = transformer;
		}

		public Adapter<V> buildAdapter(BasePluggablePropertyValueModel.Adapter.Listener<V> listener) {
			return new ListCompositePropertyValueModelAdapter<>(this, listener);
		}

		@Override
		public String toString() {
			return ObjectTools.toString(this);
		}
	}
}

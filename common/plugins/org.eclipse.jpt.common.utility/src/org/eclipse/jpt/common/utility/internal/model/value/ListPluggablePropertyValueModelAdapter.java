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
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.collection.CollectionTools;
import org.eclipse.jpt.common.utility.internal.collection.ListTools;
import org.eclipse.jpt.common.utility.internal.model.value.PluggablePropertyValueModel.Adapter;
import org.eclipse.jpt.common.utility.model.event.ListAddEvent;
import org.eclipse.jpt.common.utility.model.event.ListChangeEvent;
import org.eclipse.jpt.common.utility.model.event.ListClearEvent;
import org.eclipse.jpt.common.utility.model.event.ListMoveEvent;
import org.eclipse.jpt.common.utility.model.event.ListRemoveEvent;
import org.eclipse.jpt.common.utility.model.event.ListReplaceEvent;
import org.eclipse.jpt.common.utility.model.listener.ListChangeListener;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * Adapt a {@link ListValueModel list value model} to
 * a {@link PropertyValueModel property value model}, sorta.
 * <p>
 * This adapter is constructed with a {@link ListValueModel
 * list value model} and a {@link Transformer transformer} that can
 * transform the list to a single value.
 * <p>
 * This is an adapter that can be plugged into a {@link PluggablePropertyValueModel}.
 * 
 * @param <E> the type of the adapted list value model's elements
 * @param <V> the type of the model's derived value
 * 
 * @see PluggablePropertyValueModel
 */
public final class ListPluggablePropertyValueModelAdapter<E, V>
	implements PluggablePropertyValueModel.Adapter<V>, ListChangeListener
{
	/** The wrapped model */
	private final ListValueModel<? extends E> listModel;

	/** Transformer that converts the wrapped model's value to this model's value. */
	private final Transformer<? super List<E>, V> transformer;

	/** The <em>real</em> adapter. */
	private final BasePluggablePropertyValueModel.Adapter.Listener<V> listener;

	/** Cached copy of model's elements. */
	private final ArrayList<E> list;

	/** Protects {@link #list} from {@link Factory#transformer}. */
	private final List<E> unmodifiableList;

	/** The derived value. */
	private volatile V value;


	// ********** constructors **********

	public ListPluggablePropertyValueModelAdapter(Factory<E, V> factory, BasePluggablePropertyValueModel.Adapter.Listener<V> listener) {
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
		this.list = new ArrayList<>();
		this.unmodifiableList = Collections.unmodifiableList(this.list);
	}


	// ********** PluggablePropertyValueModel.Adapter **********

	public V getValue() {
		return this.value;
	}

	public void engageModel() {
		this.listModel.addListChangeListener(ListValueModel.LIST_VALUES, this);
		ListTools.addAll(this.list, 0, this.listModel);
		this.value = this.buildValue();
	}

	public void disengageModel() {
		this.value = null;
		this.list.clear();
		this.listModel.removeListChangeListener(ListValueModel.LIST_VALUES, this);
	}


	// ********** ListChangeListener **********

	@SuppressWarnings("unchecked")
	public void itemsAdded(ListAddEvent event) {
		ListTools.addAll(this.list, event.getIndex(), (Iterable<E>) event.getItems(), event.getItemsSize());
		this.update();
	}

	public void itemsRemoved(ListRemoveEvent event) {
		ListTools.removeElementsAtIndex(this.list, event.getIndex(), event.getItemsSize());
		this.update();
	}

	public void itemsReplaced(ListReplaceEvent event) {
		@SuppressWarnings("unchecked")
		Iterable<E> newItems = (Iterable<E>) event.getNewItems();
		Iterator<E> stream = newItems.iterator();
		int last = event.getIndex() + event.getItemsSize();
		for (int i = event.getIndex(); i < last; i++) {
			this.list.set(i, stream.next());
		}
		this.update();
	}

	public void itemsMoved(ListMoveEvent event) {
		ListTools.move(this.list, event.getTargetIndex(), event.getSourceIndex(), event.getLength());
		this.update();
	}

	public void listCleared(ListClearEvent event) {
		this.list.clear();
		this.update();
	}

	@SuppressWarnings("unchecked")
	public void listChanged(ListChangeEvent event) {
		this.list.clear();
		CollectionTools.addAll(this.list, (Iterable<E>) event.getList());
		this.update();
	}


	// ********** misc **********

	private void update() {
		this.listener.valueChanged(this.value = this.buildValue());
	}

	private V buildValue() {
		return this.transformer.transform(this.unmodifiableList);
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.value);
	}


	// ********** PluggablePropertyValueModel.Adapter.Factory **********

	public static class Factory<E, V>
		implements PluggablePropertyValueModel.Adapter.Factory<V>
	{
		/* CU private */ final ListValueModel<? extends E> listModel;
		/* CU private */ final Transformer<? super List<E>, V> transformer;

		public Factory(ListValueModel<? extends E> listModel, Transformer<? super List<E>, V> transformer) {
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
			return new ListPluggablePropertyValueModelAdapter<>(this, listener);
		}

		@Override
		public String toString() {
			return ObjectTools.toString(this);
		}
	}
}

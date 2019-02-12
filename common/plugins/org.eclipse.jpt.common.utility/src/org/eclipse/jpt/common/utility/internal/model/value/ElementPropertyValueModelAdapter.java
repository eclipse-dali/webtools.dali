/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.model.value;

import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.model.event.CollectionChangeEvent;
import org.eclipse.jpt.common.utility.model.event.CollectionClearEvent;
import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.common.utility.predicate.Predicate;

/**
 * Adapt an element in a collection value model to a property value model.
 * The property model's value is determined by whether the collection model
 * contains the value: If the collection model contains the value,
 * the property model's value is <em>that</em> element; otherwise, the property
 * model's value is <code>null</code>. A {@link #predicate} is used to determine
 * whether the collection model contains the relevant value.
 * <p>
 * This is useful for a client (e.g. a UI widget) that is longer-living than its
 * underlying model. Obviously, the client must be prepared to handle a value of
 * <code>null</code>.
 * 
 * @param <V> the type of the both the model's value and
 * the wrapped collection value model's elements
 */
public class ElementPropertyValueModelAdapter<V>
	extends CollectionPropertyValueModelAdapter<V, V>
{
	/**
	 * A predicate used to determine whether an element in the wrapped
	 * collection model is the model's value.
	 */
	protected final Predicate<V> predicate;


	/**
	 * Construct a property value model whose value depends on whether the
	 * specified collection value model contains the value. The specified
	 * filter is used to determine whether an element in the specified
	 * collection model is the property value.
	 */
	public ElementPropertyValueModelAdapter(CollectionValueModel<? extends V> collectionModel, Predicate<V> predicate) {
		super(collectionModel);
		if (predicate == null) {
			throw new NullPointerException();
		}
		this.predicate = predicate;
	}

	/**
	 * If the collection model contains the property model's {@link #value},
	 * return that element; otherwise return <code>null</code>.
	 */
	@Override
	protected V buildValue() {
		for (V each : this.collectionModel) {
			if (this.predicate.evaluate(each)) {
				return each;
			}
		}
		return null;
	}

	/**
	 * Check whether the wrapped collection model now contains the
	 * {@link #value}.
	 */
	@Override
	protected void itemsAdded(Iterable<V> items) {
		if (this.value == null) {
			this.itemsAdded_(items);
		}
	}

	protected void itemsAdded_(Iterable<V> items) {
		for (V each : items) {
			if (this.predicate.evaluate(each)) {
				this.firePropertyChanged(VALUE, null, this.value = each);
				return;
			}
		}
	}

	/**
	 * Check whether the wrapped collection model no longer contains the
	 * {@link #value}.
	 */
	@Override
	protected void itemsRemoved(Iterable<V> items) {
		if (this.value != null) {
			this.itemsRemoved_(items);
		}
	}

	protected void itemsRemoved_(Iterable<V> items) {
		for (V each : items) {
			if (ObjectTools.equals(each, this.value)) {
				V old = this.value;
				this.firePropertyChanged(VALUE, old, this.value = null);
				return;
			}
		}
	}

	/**
	 * The {@link #value} must now be <code>null</code>.
	 */
	@Override
	protected void collectionCleared(CollectionClearEvent event) {
		if (this.value != null) {
			V old = this.value;
			this.firePropertyChanged(VALUE, old, this.value = null);
		}
	}

	/**
	 * Re-calculate the {@link #value}.
	 */
	@Override
	protected void collectionChanged(CollectionChangeEvent event) {
		V old = this.value;
		this.firePropertyChanged(VALUE, old, this.value = this.buildValue());
	}
}

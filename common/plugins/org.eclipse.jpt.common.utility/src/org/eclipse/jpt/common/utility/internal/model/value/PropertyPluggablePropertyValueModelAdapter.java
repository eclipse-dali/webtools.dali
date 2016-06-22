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

import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.model.value.PluggablePropertyValueModel.Adapter;
import org.eclipse.jpt.common.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * Adapt a {@link PropertyValueModel property value model} to
 * another {@link PropertyValueModel property value model}, sorta.
 * <p>
 * This adapter is constructed with a {@link PropertyValueModel
 * property value model} and a {@link Transformer transformer} that can
 * transform the property to another value.
 * <p>
 * This is an adapter that can be plugged into a {@link PluggablePropertyValueModel}.
 * 
 * @param <V1> the type of the <em>wrapped</em> model's value
 * @param <V2> the type of the model's <em>derived</em> value
 * 
 * @see PluggablePropertyValueModel
 */
public class PropertyPluggablePropertyValueModelAdapter<V1, V2>
	implements PluggablePropertyValueModel.Adapter<V2>, PropertyChangeListener
{
	private final Factory<V1, V2> factory;

	/** The <em>real</em> adapter. */
	private final AbstractPluggablePropertyValueModel.Adapter.Listener<V2> listener;

	/** Cached copy of model's value. */
	private volatile V1 wrappedValue;

	/** The derived value. */
	private volatile V2 value;


	// ********** constructors **********

	public PropertyPluggablePropertyValueModelAdapter(Factory<V1, V2> factory, AbstractPluggablePropertyValueModel.Adapter.Listener<V2> listener) {
		super();
		if (factory == null) {
			throw new NullPointerException();
		}
		this.factory = factory;
		if (listener == null) {
			throw new NullPointerException();
		}
		this.listener = listener;
	}


	// ********** PluggablePropertyValueModel.Adapter **********

	public V2 getValue() {
		return this.value;
	}

	public void engageModel() {
		this.factory.propertyModel.addPropertyChangeListener(PropertyValueModel.VALUE, this);
		this.wrappedValue = this.factory.propertyModel.getValue();
		this.value = this.buildValue();
	}

	public void disengageModel() {
		this.value = null;
		this.wrappedValue = null;
		this.factory.propertyModel.removePropertyChangeListener(PropertyValueModel.VALUE, this);
	}


	// ********** PropertyChangeListener **********

	@SuppressWarnings("unchecked")
	public void propertyChanged(PropertyChangeEvent event) {
		this.wrappedValue = (V1) event.getNewValue();
		this.update();
	}


	// ********** misc **********

	private void update() {
		this.listener.valueChanged(this.value = this.buildValue());
	}

	private V2 buildValue() {
		return this.factory.transformer.transform(this.wrappedValue);
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.value);
	}


	// ********** PluggablePropertyValueModel.Adapter.Factory **********

	public static class Factory<V1, V2>
		implements PluggablePropertyValueModel.Adapter.Factory<V2>
	{
		/* CU private */ final PropertyValueModel<? extends V1> propertyModel;
		/* CU private */ final Transformer<? super V1, V2> transformer;

		public Factory(PropertyValueModel<? extends V1> propertyModel, Transformer<? super V1, V2> transformer) {
			super();
			if (propertyModel == null) {
				throw new NullPointerException();
			}
			this.propertyModel = propertyModel;
			if (transformer == null) {
				throw new NullPointerException();
			}
			this.transformer = transformer;
		}

		public Adapter<V2> buildAdapter(AbstractPluggablePropertyValueModel.Adapter.Listener<V2> listener) {
			return new PropertyPluggablePropertyValueModelAdapter<>(this, listener);
		}

		@Override
		public String toString() {
			return ObjectTools.toString(this);
		}
	}
}

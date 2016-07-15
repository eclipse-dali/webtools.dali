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
 * This is an adapter that can be used by a {@link BasePluggablePropertyValueModel}.
 * 
 * @param <V1> the type of the <em>wrapped</em> model's value
 * @param <V2> the type of the model's <em>derived</em> value
 * @param <M> the type of the <em>wrapped</em> model
 * @param <A> the type of the adapter itself
 * @param <F> the type of the adapter factory
 * 
 * @see BasePluggablePropertyValueModel
 */
public abstract class BasePropertyPluggablePropertyValueModelAdapter<V1, V2, M extends PropertyValueModel<? extends V1>, A extends BasePluggablePropertyValueModel.Adapter<V2>, F extends BasePropertyPluggablePropertyValueModelAdapter.Factory<V1, V2, M, A>>
	implements BasePluggablePropertyValueModel.Adapter<V2>, PropertyChangeListener
{
	/** The wrapped model */
	protected final M propertyModel;

	/** Transformer that converts the wrapped model's value to this model's value. */
	private final Transformer<? super V1, ? extends V2> transformer;

	/** The <em>real</em> adapter. */
	private final BasePluggablePropertyValueModel.Adapter.Listener<V2> listener;

	/** Cached copy of model's value. */
	private volatile V1 propertyModelValue;

	/** The derived value. */
	private volatile V2 value;


	// ********** constructors **********

	public BasePropertyPluggablePropertyValueModelAdapter(F factory, BasePluggablePropertyValueModel.Adapter.Listener<V2> listener) {
		super();
		if (factory == null) {
			throw new NullPointerException();
		}
		this.propertyModel = factory.propertyModel;
		this.transformer = factory.transformer;
		if (listener == null) {
			throw new NullPointerException();
		}
		this.listener = listener;
	}


	// ********** BasePluggablePropertyValueModel.Adapter **********

	public V2 getValue() {
		return this.value;
	}

	public V2 engageModel() {
		this.propertyModel.addPropertyChangeListener(PropertyValueModel.VALUE, this);
		this.propertyModelValue = this.propertyModel.getValue();
		return this.value = this.buildValue();
	}

	public V2 disengageModel() {
		this.propertyModel.removePropertyChangeListener(PropertyValueModel.VALUE, this);
		this.propertyModelValue = null;
		return this.value = null;
	}


	// ********** PropertyChangeListener **********

	@SuppressWarnings("unchecked")
	public void propertyChanged(PropertyChangeEvent event) {
		this.propertyModelValue = (V1) event.getNewValue();
		this.update();
	}


	// ********** misc **********

	private void update() {
		this.listener.valueChanged(this.value = this.buildValue());
	}

	private V2 buildValue() {
		return this.transformer.transform(this.propertyModelValue);
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.value);
	}


	// ********** Factory **********

	public abstract static class Factory<V1, V2, M extends PropertyValueModel<? extends V1>, A extends BasePluggablePropertyValueModel.Adapter<V2>>
		implements BasePluggablePropertyValueModel.Adapter.Factory<V2, A>
	{
		/* CU private */ final M propertyModel;
		/* CU private */ final Transformer<? super V1, ? extends V2> transformer;

		public Factory(M propertyModel, Transformer<? super V1, ? extends V2> transformer) {
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

		public abstract A buildAdapter(BasePluggablePropertyValueModel.Adapter.Listener<V2> listener);

		@Override
		public String toString() {
			return ObjectTools.toString(this);
		}
	}
}

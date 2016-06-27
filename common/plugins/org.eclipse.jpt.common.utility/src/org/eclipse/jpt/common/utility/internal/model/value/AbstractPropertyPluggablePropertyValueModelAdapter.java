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
 * This is an adapter that can be used by a {@link AbstractPluggablePropertyValueModel}.
 * 
 * @param <V1> the type of the <em>wrapped</em> model's value
 * @param <V2> the type of the model's <em>derived</em> value
 * @param <M> the type of the <em>wrapped</em> model
 * @param <A> the type of the adapter itself
 * @param <F> the type of the adapter factory
 * 
 * @see AbstractPluggablePropertyValueModel
 */
public abstract class AbstractPropertyPluggablePropertyValueModelAdapter<V1, V2, M extends PropertyValueModel<? extends V1>, A extends AbstractPluggablePropertyValueModel.Adapter<V2>, F extends AbstractPropertyPluggablePropertyValueModelAdapter.Factory<V1, V2, M, A>>
	implements AbstractPluggablePropertyValueModel.Adapter<V2>, PropertyChangeListener
{
	protected final F factory;

	/** The <em>real</em> adapter. */
	private final AbstractPluggablePropertyValueModel.Adapter.Listener<V2> listener;

	/** Cached copy of model's value. */
	private volatile V1 propertyModelValue;

	/** The derived value. */
	private volatile V2 value;


	// ********** constructors **********

	public AbstractPropertyPluggablePropertyValueModelAdapter(F factory, AbstractPluggablePropertyValueModel.Adapter.Listener<V2> listener) {
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


	// ********** AbstractPluggablePropertyValueModel.Adapter **********

	public V2 getValue() {
		return this.value;
	}

	public void engageModel() {
		this.factory.propertyModel.addPropertyChangeListener(PropertyValueModel.VALUE, this);
		this.propertyModelValue = this.factory.propertyModel.getValue();
		this.value = this.buildValue();
	}

	public void disengageModel() {
		this.value = null;
		this.propertyModelValue = null;
		this.factory.propertyModel.removePropertyChangeListener(PropertyValueModel.VALUE, this);
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
		return this.factory.transformer.transform(this.propertyModelValue);
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.value);
	}


	// ********** AbstractPluggablePropertyValueModel.Adapter.Factory **********

	public abstract static class Factory<V1, V2, M extends PropertyValueModel<? extends V1>, A extends AbstractPluggablePropertyValueModel.Adapter<V2>>
		implements AbstractPluggablePropertyValueModel.Adapter.Factory<V2, A>
	{
		public final M propertyModel;
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

		public abstract A buildAdapter(AbstractPluggablePropertyValueModel.Adapter.Listener<V2> listener);

		@Override
		public String toString() {
			return ObjectTools.toString(this);
		}
	}
}

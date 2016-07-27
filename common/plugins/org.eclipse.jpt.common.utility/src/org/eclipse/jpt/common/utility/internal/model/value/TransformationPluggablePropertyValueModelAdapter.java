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
 * transform the wrapped model's value to this model's derived value.
 * <p>
 * This is an adapter that can be used by a {@link PluggablePropertyValueModel}.
 * 
 * @param <V1> the type of the <em>wrapped</em> model's value
 * @param <V2> the type of the model's <em>derived</em> value
 * 
 * @see PluggablePropertyValueModel
 */
public final class TransformationPluggablePropertyValueModelAdapter<V1, V2>
	implements PluggablePropertyValueModel.Adapter<V2>, PropertyChangeListener
{
	/** The wrapped model */
	private final PropertyValueModel<? extends V1> propertyModel;

	/** Transformer that converts the wrapped model's value to this model's value. */
	private final Transformer<? super V1, ? extends V2> transformer;

	/** The <em>real</em> adapter. */
	private final BasePluggablePropertyValueModel.Adapter.Listener<V2> listener;

	/** Cached copy of model's value. */
	/* package */ volatile V1 propertyModelValue;


	// ********** constructors **********

	public TransformationPluggablePropertyValueModelAdapter(
			PropertyValueModel<? extends V1> propertyModel,
			Transformer<? super V1, ? extends V2> transformer,
			BasePluggablePropertyValueModel.Adapter.Listener<V2> listener
	) {
		super();
		if (propertyModel == null) {
			throw new NullPointerException();
		}
		this.propertyModel = propertyModel;
		if (transformer == null) {
			throw new NullPointerException();
		}
		this.transformer = transformer;
		if (listener == null) {
			throw new NullPointerException();
		}
		this.listener = listener;
	}


	// ********** BasePluggablePropertyValueModel.Adapter **********

	public V2 engageModel() {
		this.propertyModel.addPropertyChangeListener(PropertyValueModel.VALUE, this);
		this.propertyModelValue = this.propertyModel.getValue();
		return this.buildValue();
	}

	public V2 disengageModel() {
		this.propertyModel.removePropertyChangeListener(PropertyValueModel.VALUE, this);
		this.propertyModelValue = null;
		return null;
	}


	// ********** PropertyChangeListener **********

	@SuppressWarnings("unchecked")
	public void propertyChanged(PropertyChangeEvent event) {
		this.propertyModelValue = (V1) event.getNewValue();
		this.update();
	}


	// ********** misc **********

	private void update() {
		this.listener.valueChanged(this.buildValue());
	}

	private V2 buildValue() {
		return this.transformer.transform(this.propertyModelValue);
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.buildValue());
	}


	// ********** Factory **********

	public static final class Factory<V1, V2>
		implements PluggablePropertyValueModel.Adapter.Factory<V2>
	{
		private final PropertyValueModel<? extends V1> propertyModel;
		private final Transformer<? super V1, ? extends V2> transformer;

		public Factory(PropertyValueModel<? extends V1> propertyModel, Transformer<? super V1, ? extends V2> transformer) {
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

		public TransformationPluggablePropertyValueModelAdapter<V1, V2> buildAdapter(BasePluggablePropertyValueModel.Adapter.Listener<V2> listener) {
			return new TransformationPluggablePropertyValueModelAdapter<>(this.propertyModel, this.transformer, listener);
		}

		@Override
		public String toString() {
			return ObjectTools.toString(this);
		}
	}
}

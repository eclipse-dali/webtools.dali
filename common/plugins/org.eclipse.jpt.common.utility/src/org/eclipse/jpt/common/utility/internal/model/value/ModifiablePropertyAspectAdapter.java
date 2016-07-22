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

import org.eclipse.jpt.common.utility.closure.BiClosure;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.model.Model;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * Adapt a {@link ModifiablePropertyValueModel property value model} to
 * another {@link ModifiablePropertyValueModel property value model}, sorta.
 * <p>
 * This adapter is constructed with a {@link ModifiablePropertyValueModel
 * property value model} and two {@link Transformer transformers} that can
 * transform the property to another value and vice versa.
 * <p>
 * This is an adapter that can be used by a {@link PluggableModifiablePropertyValueModel}.
 * 
 * @param <V> the type of the adapter's value
 * @param <S> the type of the subject (and the subject model's value)
 * @param <SM> the type of the subject model
 * 
 * @see PluggableModifiablePropertyValueModel
 */
public final class ModifiablePropertyAspectAdapter<V, S extends Model, SM extends PropertyValueModel<S>>
	implements PluggableModifiablePropertyValueModel.Adapter<V>
{
	/** The "get" half of the aspect adapter */
	private final PropertyAspectAdapter<V, S, SM> adapter;

	/** The closure used to "set" the subject's new aspect value. */
	private final BiClosure<? super S, ? super V> setClosure;


	public ModifiablePropertyAspectAdapter(PropertyAspectAdapter<V, S, SM> adapter, BiClosure<? super S, ? super V> setClosure) {
		super();
		if (adapter == null) {
			throw new NullPointerException();
		}
		this.adapter = adapter;
		if (setClosure == null) {
			throw new NullPointerException();
		}
		this.setClosure = setClosure;
	}

	public V engageModel() {
		return this.adapter.engageModel();
	}

	public V disengageModel() {
		return this.adapter.disengageModel();
	}

	public void setValue(V value) {
		this.setClosure.execute(this.adapter.subject, value);
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.adapter.buildValue());
	}


	// ********** Factory **********

	public static final class Factory<V, S extends Model, SM extends PropertyValueModel<S>>
		implements PluggableModifiablePropertyValueModel.Adapter.Factory<V>
	{
		private final PropertyAspectAdapter.Factory<V, S, SM> factory;
		private final BiClosure<? super S, ? super V> setClosure;

		public Factory(PropertyAspectAdapter.Factory<V, S, SM> factory, BiClosure<? super S, ? super V> setClosure) {
			super();
			if (factory == null) {
				throw new NullPointerException();
			}
			this.factory = factory;
			if (setClosure == null) {
				throw new NullPointerException();
			}
			this.setClosure = setClosure;
		}

		@Override
		public PluggableModifiablePropertyValueModel.Adapter<V> buildAdapter(BasePluggablePropertyValueModel.Adapter.Listener<V> listener) {
			return new ModifiablePropertyAspectAdapter<>(this.factory.buildAdapter(listener), this.setClosure);
		}
	}
}

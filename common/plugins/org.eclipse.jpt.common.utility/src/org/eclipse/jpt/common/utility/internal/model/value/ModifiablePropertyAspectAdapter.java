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
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
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
 * 
 * @see PluggableModifiablePropertyValueModel
 */
public final class ModifiablePropertyAspectAdapter<V, S>
	implements PluggableModifiablePropertyValueModel.Adapter<V>
{
	/** The adapter used to "get" the subject's aspect value. */
	private final GetAdapter<V, S> getAdapter;

	/** The closure used to "set" the subject's new aspect value. */
	private final BiClosure<? super S, ? super V> setClosure;


	public ModifiablePropertyAspectAdapter(GetAdapter<V, S> getAdapter, BiClosure<? super S, ? super V> setClosure) {
		super();
		if (getAdapter == null) {
			throw new NullPointerException();
		}
		this.getAdapter = getAdapter;

		if (setClosure == null) {
			throw new NullPointerException();
		}
		this.setClosure = setClosure;
	}

	public V engageModel() {
		return this.getAdapter.engageModel();
	}

	public V disengageModel() {
		return this.getAdapter.disengageModel();
	}

	public void setValue(V value) {
		this.setClosure.execute(this.getAdapter.getSubject(), value);
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.getAdapter);
	}

	/**
	 * Extend the pluggable pvm adapter so we can get the
	 * other adapter's subject and set the value of its aspect.
	 */
	public interface GetAdapter<V, S>
		extends PluggablePropertyValueModel.Adapter<V>
	{
		/**
		 * Return the adapter's subject.
		 */
		S getSubject();

		interface Factory<V, S> {
			GetAdapter<V, S> buildAdapter(BasePluggablePropertyValueModel.Adapter.Listener<V> listener);
		}
	}
		

	// ********** Factory **********

	public static final class Factory<V, S>
		implements PluggableModifiablePropertyValueModel.Adapter.Factory<V>
	{
		private final ModifiablePropertyAspectAdapter.GetAdapter.Factory<V, S> getAdapterFactory;
		private final BiClosure<? super S, ? super V> setClosure;

		public Factory(GetAdapter.Factory<V, S> getAdapterFactory, BiClosure<? super S, ? super V> setClosure) {
			super();
			if (getAdapterFactory == null) {
				throw new NullPointerException();
			}
			this.getAdapterFactory = getAdapterFactory;

			if (setClosure == null) {
				throw new NullPointerException();
			}
			this.setClosure = setClosure;
		}

		@Override
		public PluggableModifiablePropertyValueModel.Adapter<V> buildAdapter(BasePluggablePropertyValueModel.Adapter.Listener<V> listener) {
			return new ModifiablePropertyAspectAdapter<>(this.getAdapterFactory.buildAdapter(listener), this.setClosure);
		}
	}
}

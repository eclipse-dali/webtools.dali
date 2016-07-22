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

import org.eclipse.jpt.common.utility.closure.Closure;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * This class adds support for plugging in a closure that can be used to set
 * the model's value.
 * <p>
 * This class is most useful when the adapted model is changed <em>outside</em>
 * the property value model; typically by modifying the original
 * <em>domain</em> model.
 * 
 * @param <V> the type of the model's derived value
 */
public class PluggableModifiablePropertyValueModelAdapter<V>
	implements PluggableModifiablePropertyValueModel.Adapter<V>
{
	/** Read the adapted model with this. */
	private final BasePluggablePropertyValueModel.Adapter<V> adapter;

	/** Write the adapted model with this. */
	private final Closure<? super V> closure;


	public PluggableModifiablePropertyValueModelAdapter(BasePluggablePropertyValueModel.Adapter<V> adapter, Closure<? super V> closure) {
		super();
		if (adapter == null) {
			throw new NullPointerException();
		}
		this.adapter = adapter;
		if (closure == null) {
			throw new NullPointerException();
		}
		this.closure = closure;
	}

	public V engageModel() {
		return this.adapter.engageModel();
	}

	public V disengageModel() {
		return this.adapter.disengageModel();
	}

	public void setValue(V value) {
		this.closure.execute(value);
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.adapter);
	}


	// ********** Factory **********

	public static class Factory<V>
		implements PluggableModifiablePropertyValueModel.Adapter.Factory<V>
	{
		private final BasePluggablePropertyValueModel.Adapter.Factory<V, ? extends BasePluggablePropertyValueModel.Adapter<V>> factory;
		private final Closure<? super V> closure;

		public Factory(BasePluggablePropertyValueModel.Adapter.Factory<V, ? extends BasePluggablePropertyValueModel.Adapter<V>> factory, Closure<? super V> closure) {
			super();
			if (factory == null) {
				throw new NullPointerException();
			}
			this.factory = factory;
			if (closure == null) {
				throw new NullPointerException();
			}
			this.closure = closure;
		}

		public PluggableModifiablePropertyValueModel.Adapter<V> buildAdapter(BasePluggablePropertyValueModel.Adapter.Listener<V> listener) {
			return new PluggableModifiablePropertyValueModelAdapter<>(this.factory.buildAdapter(listener), this.closure);
		}

		@Override
		public String toString() {
			return ObjectTools.toString(this);
		}
	}
}

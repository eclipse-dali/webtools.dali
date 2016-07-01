/*******************************************************************************
 * Copyright (c) 2012, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.model.value;

import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;

/**
 * Add support for changing the model's value.
 * This also constrains the value's type, since it is read <em>and</em> written.
 * <p>
 * <strong>NB:</strong> If the model has no listeners,
 * {@link #setValue(Object) changes} will <em>not</em> be forwarded to the
 * <em>inner</em> model.
 * 
 * @param <V> the type of both the adapter's and the <em>inner</em> model's values
 * @param <IM> the type of the <em>inner</em> model (and the <em>outer</em> model's value)
 * @param <OM> the type of the <em>outer</em> model
 */
public final class DoubleModifiablePropertyValueModelAdapter<V, IM extends ModifiablePropertyValueModel<V>, OM extends PropertyValueModel<IM>>
	extends BaseDoublePropertyValueModelAdapter<V, IM, OM, PluggableModifiablePropertyValueModel.Adapter<V>, DoubleModifiablePropertyValueModelAdapter.Factory<V, IM, OM>>
	implements PluggableModifiablePropertyValueModel.Adapter<V>
{

	public DoubleModifiablePropertyValueModelAdapter(OM outerModel, BasePluggablePropertyValueModel.Adapter.Listener<V> listener) {
		super(outerModel, listener);
	}

	/**
	 * Forward the specified value to the <em>inner</em> model.
	 * Client's should be aware of a <code>null</code> <em>inner</em> model
	 * (typically the <em>inner</em> will never be <code>null</code>).
	 */
	public void setValue(V value) {
		if (this.innerModel == null) {
			throw new IllegalStateException();
		}
		this.innerModel.setValue(value);
	}


	// ********** Factory **********

	public static class Factory<V, IM extends ModifiablePropertyValueModel<V>, OM extends PropertyValueModel<IM>>
		extends BaseDoublePropertyValueModelAdapter.Factory<V,IM, OM, PluggableModifiablePropertyValueModel.Adapter<V>>
		implements PluggableModifiablePropertyValueModel.Adapter.Factory<V>
	{
		public Factory(OM outerModel) {
			super(outerModel);
		}

		@Override
		public PluggableModifiablePropertyValueModel.Adapter<V> buildAdapter(BasePluggablePropertyValueModel.Adapter.Listener<V> listener) {
			return new DoubleModifiablePropertyValueModelAdapter<>(this.outerModel, listener);
		}
	}
}

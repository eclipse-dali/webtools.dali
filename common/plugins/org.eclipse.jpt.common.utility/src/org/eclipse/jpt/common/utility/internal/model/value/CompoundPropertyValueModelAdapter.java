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

import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;

/**
 * This adapter adapts an (<em>outer</em>) property value model whose
 * value is yet another (<em>inner</em>) property value model
 * and treats the <em>inner</em> model's value as this adapter's models's value.
 * As a result, this adapter listens for changes to either model
 * (<em>inner</em> or <em>outer</em>).
 * <p>
 * This is an adapter that can be used by a {@link BasePluggablePropertyValueModel}.
 * 
 * @param <V> the type of both the adapter's and the <em>inner</em> model's values
 * @param <IM> the type of the <em>inner</em> model (and the <em>outer</em> model's value)
 * @param <OM> the type of the <em>outer</em> model
 * 
 * @see BasePluggablePropertyValueModel
 */
public final class CompoundPropertyValueModelAdapter<V, IM extends PropertyValueModel<? extends V>, OM extends PropertyValueModel<IM>>
	extends BaseCompoundPropertyValueModelAdapter<V, IM, OM, PluggablePropertyValueModel.Adapter<V>, CompoundPropertyValueModelAdapter.Factory<V, IM, OM>>
	implements PluggablePropertyValueModel.Adapter<V>
{

	public CompoundPropertyValueModelAdapter(OM outerModel, BasePluggablePropertyValueModel.Adapter.Listener<V> listener) {
		super(outerModel, listener);
	}


	// ********** Factory **********

	public static class Factory<V, IM extends PropertyValueModel<? extends V>, OM extends PropertyValueModel<IM>>
		extends BaseCompoundPropertyValueModelAdapter.Factory<V, IM, OM, PluggablePropertyValueModel.Adapter<V>>
		implements PluggablePropertyValueModel.Adapter.Factory<V>
	{
		public Factory(OM outerModel) {
			super(outerModel);
		}

		@Override
		public PluggablePropertyValueModel.Adapter<V> buildAdapter(BasePluggablePropertyValueModel.Adapter.Listener<V> listener) {
			return new CompoundPropertyValueModelAdapter<>(this.outerModel, listener);
		}
	}
}

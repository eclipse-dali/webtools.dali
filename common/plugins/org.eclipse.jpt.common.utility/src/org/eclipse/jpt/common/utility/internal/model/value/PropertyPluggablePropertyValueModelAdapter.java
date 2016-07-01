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
 * This is an adapter that can be used by a {@link PluggablePropertyValueModel}.
 * 
 * @param <V1> the type of the <em>wrapped</em> model's value
 * @param <V2> the type of the model's <em>derived</em> value
 * 
 * @see PluggablePropertyValueModel
 */
public final class PropertyPluggablePropertyValueModelAdapter<V1, V2>
	extends BasePropertyPluggablePropertyValueModelAdapter<V1, V2, PropertyValueModel<? extends V1>, PluggablePropertyValueModel.Adapter<V2>, PropertyPluggablePropertyValueModelAdapter.Factory<V1, V2>>
	implements PluggablePropertyValueModel.Adapter<V2>
{

	public PropertyPluggablePropertyValueModelAdapter(Factory<V1, V2> factory, BasePluggablePropertyValueModel.Adapter.Listener<V2> listener) {
		super(factory, listener);
	}


	// ********** Factory **********

	public static class Factory<V1, V2>
		extends BasePropertyPluggablePropertyValueModelAdapter.Factory<V1, V2, PropertyValueModel<? extends V1>, PluggablePropertyValueModel.Adapter<V2>>
		implements PluggablePropertyValueModel.Adapter.Factory<V2>
	{
		public Factory(PropertyValueModel<? extends V1> propertyModel, Transformer<? super V1, ? extends V2> transformer) {
			super(propertyModel, transformer);
		}

		@Override
		public PluggablePropertyValueModel.Adapter<V2> buildAdapter(BasePluggablePropertyValueModel.Adapter.Listener<V2> listener) {
			return new PropertyPluggablePropertyValueModelAdapter<>(this, listener);
		}
	}
}

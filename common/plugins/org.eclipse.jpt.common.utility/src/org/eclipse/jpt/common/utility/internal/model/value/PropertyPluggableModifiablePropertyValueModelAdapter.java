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
 * @param <V1> the type of the <em>wrapped</em> model's value
 * @param <V2> the type of the model's <em>derived</em> value
 * 
 * @see PluggableModifiablePropertyValueModel
 */
public class PropertyPluggableModifiablePropertyValueModelAdapter<V1, V2>
	extends AbstractPropertyPluggablePropertyValueModelAdapter<V1, V2, ModifiablePropertyValueModel<V1>, PluggableModifiablePropertyValueModel.Adapter<V2>, PropertyPluggableModifiablePropertyValueModelAdapter.Factory<V1, V2>>
	implements PluggableModifiablePropertyValueModel.Adapter<V2>
{
	private final Transformer<? super V2, ? extends V1> setTransformer;

	public PropertyPluggableModifiablePropertyValueModelAdapter(Factory<V1, V2> factory, BasePluggablePropertyValueModel.Adapter.Listener<V2> listener) {
		super(factory, listener);
		this.setTransformer = factory.setTransformer;
	}

	public void setValue(V2 value) {
		this.propertyModel.setValue(this.setTransformer.transform(value));
	}


	// ********** PluggableModifiablePropertyValueModel.Adapter.Factory **********

	public static class Factory<V1, V2>
		extends AbstractPropertyPluggablePropertyValueModelAdapter.Factory<V1, V2, ModifiablePropertyValueModel<V1>, PluggableModifiablePropertyValueModel.Adapter<V2>>
		implements PluggableModifiablePropertyValueModel.Adapter.Factory<V2>
	{
		/* CU private */ final Transformer<? super V2, ? extends V1> setTransformer;

		public Factory(ModifiablePropertyValueModel<V1> propertyModel, Transformer<? super V1, ? extends V2> getTransformer, Transformer<? super V2, ? extends V1> setTransformer) {
			super(propertyModel, getTransformer);
			this.setTransformer = setTransformer;
		}

		@Override
		public PluggableModifiablePropertyValueModel.Adapter<V2> buildAdapter(BasePluggablePropertyValueModel.Adapter.Listener<V2> listener) {
			return new PropertyPluggableModifiablePropertyValueModelAdapter<>(this, listener);
		}
	}
}

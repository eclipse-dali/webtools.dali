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

/**
 * This class provides the infrastructure needed to wrap
 * a <em>modifiable</em> model, "lazily" listen to it, and convert
 * its change notifications into <em>property</em> value model change
 * notifications.
 * 
 * @param <V> the type of the model's derived value
 */
public final class PluggableModifiablePropertyValueModel<V>
	extends BasePluggablePropertyValueModel<V, PluggableModifiablePropertyValueModel.Adapter<V>>
	implements ModifiablePropertyValueModel<V>
{
	public PluggableModifiablePropertyValueModel(Adapter.Factory<V> adapterFactory) {
		super(adapterFactory);
	}


	// ********** ModifiablePropertyValueModel implementation **********

	/**
	 * Forward the new value to the adapter.
	 * Our value will be updated by notification from the adapter,
	 * if appropriate.
	 */
	public void setValue(V value) {
		this.adapter.setValue(value);
	}


	// ********** Adapter interfaces **********

	public interface Adapter<AV>
		extends BasePluggablePropertyValueModel.Adapter<AV>
	{
		/**
		 * Set the adapted model's value,
		 * based on the specified new value of the property value model.
		 */
		void setValue(AV value);

		interface Factory<AFV>
			extends BasePluggablePropertyValueModel.Adapter.Factory<AFV, Adapter<AFV>>
		{
			// NOP
		}
	}
}

/*******************************************************************************
 * Copyright (c) 2008, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.model.value;

/**
 * This class provides the infrastructure needed to wrap
 * a model, "lazily" listen to it, and convert
 * its change notifications into <em>property</em> value model change
 * notifications.
 * 
 * @param <V> the type of the model's derived value
 */
public final class PluggablePropertyValueModel<V>
	extends BasePluggablePropertyValueModel<V, PluggablePropertyValueModel.Adapter<V>>
{
	public PluggablePropertyValueModel(Adapter.Factory<V> adapterFactory) {
		super(adapterFactory);
	}


	// ********** Adapter interfaces **********

	public interface Adapter<AV>
		extends BasePluggablePropertyValueModel.Adapter<AV>
	{
		interface Factory<AFV>
			extends BasePluggablePropertyValueModel.Adapter.Factory<AFV, Adapter<AFV>>
		{
			// NOP
		}
	}
}

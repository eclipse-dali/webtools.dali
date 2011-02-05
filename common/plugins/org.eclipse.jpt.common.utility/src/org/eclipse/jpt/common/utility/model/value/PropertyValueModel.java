/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.model.value;

import org.eclipse.jpt.common.utility.model.Model;

/**
 * Interface used to abstract property accessing and
 * change notification and make it more pluggable.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @param <T> the type of value held by the model
 */
public interface PropertyValueModel<T>
	extends Model
{

	/**
	 * Return the property's value.
	 */
	T getValue();
		String VALUE = "value"; //$NON-NLS-1$

}

/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.model.value;

/**
 * Extend {@link ListValueModel} to allow the setting of the
 * list's values.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @param <E> the type of values held by the model
 */
public interface ModifiableListValueModel<E>
	extends ListValueModel<E>
{
	/**
	 * Set the list values and fire a list change notification.
	 * @see ListValueModel#LIST_VALUES
	 */
	void setListValues(Iterable<E> values);
}

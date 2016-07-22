/*******************************************************************************
 * Copyright (c) 2007, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.model.value;

import org.eclipse.jpt.common.utility.closure.BiClosure;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * Extend {@link PropertyValueModel} to allow the setting of the property's value.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @param <V> the type of value held by the model
 */
public interface ModifiablePropertyValueModel<V>
	extends PropertyValueModel<V>
{
	/**
	 * Set the value and fire a property change notification.
	 * @see PropertyValueModel#VALUE
	 */
	void setValue(V value);

	@SuppressWarnings("rawtypes")
	BiClosure SET_VALUE_CLOSURE = new SetValueClosure();
	class SetValueClosure<V>
		implements BiClosure<ModifiablePropertyValueModel<V>, V>
	{
		public void execute(ModifiablePropertyValueModel<V> pvm, V value) {
			pvm.setValue(value);
		}
		@Override
		public String toString() {
			return ObjectTools.singletonToString(this);
		}
	}
}

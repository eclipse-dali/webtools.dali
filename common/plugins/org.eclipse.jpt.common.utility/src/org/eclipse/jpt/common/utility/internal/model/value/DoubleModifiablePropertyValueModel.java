/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.model.value;

import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;

/**
 * Add support for changing the double property value model.
 * 
 * @param <V> the type of the both the <em>wrapped</em> and <em>wrapper</em>
 * models' values
 */
public class DoubleModifiablePropertyValueModel<V>
	extends DoublePropertyValueModel<V>
	implements ModifiablePropertyValueModel<V>
{
	/**
	 * Construct a double modifiable property value model for the specified
	 * wrapped property value model model.
	 */
	public DoubleModifiablePropertyValueModel(PropertyValueModel<? extends ModifiablePropertyValueModel<V>> valueModel) {
		super(valueModel);
	}

	public void setValue(V value) {
		ModifiablePropertyValueModel<V> vmv = this.getValueModelValueModel();
		if (vmv == null) {
			this.setValue_(value);
		} else {
			vmv.setValue(value);
		}
	}

	@SuppressWarnings("unchecked")
	protected ModifiablePropertyValueModel<V> getValueModelValueModel() {
		return (ModifiablePropertyValueModel<V>) this.valueModelValueModel;
	}

	/**
	 * The wrapped value model model is missing. Handle the specified new value.
	 */
	protected void setValue_(@SuppressWarnings("unused") V value) {
		// do nothing by default
	}
}

/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
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
 * Add support for changing the double property value model's value.
 * This also constrains the value's type, since it is read <em>and</em> written.
 * <p>
 * <strong>NB:</strong> If the <em>outer</em> model has no listeners,
 * {@link #setValue(Object) changes} will <em>not</em> be forwarded to the
 * <em>inner</em> model.
 * 
 * @param <V> the type of the both the <em>inner</em> and <em>outer</em>
 * models' values
 */
public class DoubleModifiablePropertyValueModel<V>
	extends AbstractDoublePropertyValueModel<V, ModifiablePropertyValueModel<V>>
	implements ModifiablePropertyValueModel<V>
{
	/**
	 * Construct a double property value model for the specified
	 * <em>middle</em> property value model.
	 */
	public DoubleModifiablePropertyValueModel(PropertyValueModel<? extends ModifiablePropertyValueModel<V>> valueModel) {
		super(valueModel);
	}

	/**
	 * Forwad the specified value to the <em>inner</em> model.
	 */
	public void setValue(V value) {
		if (this.valueModelValueModel != null) {
			this.valueModelValueModel.setValue(value);
		}
	}
}

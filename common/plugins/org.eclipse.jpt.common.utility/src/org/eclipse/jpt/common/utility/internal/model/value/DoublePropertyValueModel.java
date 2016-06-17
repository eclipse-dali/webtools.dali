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

import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;

/**
 * This [<em>outer</em>] property value model wraps another [<em>middle</em>]
 * property value model that wraps yet another [<em>inner</em>] property value model
 * and treats the <em>inner</em> model's value as the <em>outer</em>'s value.
 * 
 * @param <V> the type of the both the <em>inner</em> and <em>outer</em>
 * models' values
 */
public final class DoublePropertyValueModel<V>
	extends AbstractDoublePropertyValueModel<V, PropertyValueModel<? extends V>>
{
	/**
	 * Construct a double property value model for the specified
	 * <em>middle</em> property value model.
	 */
	public DoublePropertyValueModel(PropertyValueModel<? extends PropertyValueModel<? extends V>> valueModel) {
		super(valueModel);
	}
}

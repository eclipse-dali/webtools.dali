/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal.model.value;

/**
 * Extend ValueModel to allow the setting of the property's value.
 */
public interface WritablePropertyValueModel<T>
	extends PropertyValueModel<T>
{

	/**
	 * Set the value and fire a property change notification.
	 * @see PropertyValueModel#VALUE
	 */
	void setValue(T value);

}

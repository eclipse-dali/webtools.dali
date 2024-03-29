/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.model.value.swing;

import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;

/**
 * This javax.swing.ButtonModel can be used to keep a listener
 * (e.g. a JCheckBox) in synch with a PropertyValueModel that
 * holds a boolean.
 * 
 * Maybe not the richest class in our toolbox, but it was the
 * victim of refactoring....  ~bjv
 */
public class CheckBoxModelAdapter
	extends ToggleButtonModelAdapter
{
	private static final long serialVersionUID = 1L;

	// ********** constructors **********

	/**
	 * Constructor - the boolean holder is required.
	 */
	public CheckBoxModelAdapter(ModifiablePropertyValueModel<Boolean> booleanHolder, boolean defaultValue) {
		super(booleanHolder, defaultValue);
	}

	/**
	 * Constructor - the boolean holder is required.
	 * The default value will be false.
	 */
	public CheckBoxModelAdapter(ModifiablePropertyValueModel<Boolean> booleanHolder) {
		super(booleanHolder);
	}

}

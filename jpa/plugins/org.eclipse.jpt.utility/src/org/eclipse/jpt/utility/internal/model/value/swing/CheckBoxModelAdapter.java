/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal.model.value.swing;

import org.eclipse.jpt.utility.internal.model.value.WritablePropertyValueModel;

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

	// ********** constructors **********

	/**
	 * Constructor - the boolean holder is required.
	 */
	public CheckBoxModelAdapter(WritablePropertyValueModel booleanHolder, boolean defaultValue) {
		super(booleanHolder, defaultValue);
	}

	/**
	 * Constructor - the boolean holder is required.
	 * The default value will be false.
	 */
	public CheckBoxModelAdapter(WritablePropertyValueModel booleanHolder) {
		super(booleanHolder);
	}

}

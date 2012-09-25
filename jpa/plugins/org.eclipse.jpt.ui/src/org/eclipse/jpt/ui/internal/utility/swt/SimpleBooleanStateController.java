/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.utility.swt;

import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Control;

/**
 * This controller enables a boolean model to control either the
 * <em>enabled</em> or <em>visible</em> properties of an SWT control; i.e. the
 * control's property is kept in synch with the boolean model,
 * but <em>not</em> vice-versa.
 * <p>
 * Once the control is disposed, this controller is kaput.
 * 
 * @see PropertyValueModel
 * @see Control#setEnabled(boolean)
 * @see Control#setVisible(boolean)
 */
final class SimpleBooleanStateController
	extends BooleanStateController
{
	private final Control control;


	// ********** constructor **********

	/**
	 * Constructor - the boolean model, the control, and the adapter are required.
	 */
	SimpleBooleanStateController(
			PropertyValueModel<Boolean> booleanModel,
			Control control,
			boolean defaultValue,
			Adapter adapter
	) {
		super(booleanModel, defaultValue, adapter);
		if (control == null) {
			throw new NullPointerException();
		}
		this.control = control;
		this.engageBooleanModel();
		this.engageControl(control);
		this.setControlState(control, this.getBooleanValue());
	}


	// ********** controls **********

	@Override
	void setControlState(boolean b) {
		this.setControlState(this.control, b);
	}

	@Override
	void controlDisposed(Control c) {
		super.controlDisposed(c);
		this.disengageBooleanModel();
	}

}

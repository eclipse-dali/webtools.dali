/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal.swt.bindings;

import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Control;

/**
 * This controller enables a <code>boolean</code> model to control either the
 * <em>enabled</em> or <em>visible</em> properties of an SWT control; i.e. the
 * control's property is kept in sync with the <code>boolean</code> model,
 * but <em>not</em> vice-versa.
 * <p>
 * Once the control is disposed, this controller is kaput.
 * 
 * @see PropertyValueModel
 * @see Control#setEnabled(boolean)
 * @see Control#setVisible(boolean)
 */
final class SimpleBooleanControlStateModelBinding<C extends Control>
	extends BooleanControlStateModelBinding<C>
{
	private final C control;


	/**
	 * Constructor - the <code>boolean</code> model, the control,
	 * and the adapter are required.
	 */
	SimpleBooleanControlStateModelBinding(
			PropertyValueModel<Boolean> booleanModel,
			C control,
			boolean defaultValue,
			Adapter<C> adapter
	) {
		super(booleanModel, defaultValue, adapter);
		if (control == null) {
			throw new NullPointerException();
		}
		this.control = control;
		this.engageBooleanModel();
		this.engageControl(control);
		this.setControlState();
	}


	@Override
	void setControlState() {
		this.setControlState(this.control);
	}

	@Override
	void controlDisposed(C c) {
		super.controlDisposed(c);
		this.disengageBooleanModel();
	}

	@Override
	boolean controlIsDisposed() {
		return this.control.isDisposed();
	}
}

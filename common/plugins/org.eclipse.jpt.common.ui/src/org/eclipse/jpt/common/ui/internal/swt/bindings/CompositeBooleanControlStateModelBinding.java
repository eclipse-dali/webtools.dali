/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal.swt.bindings;

import java.util.HashSet;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Control;

/**
 * This controller enables a <code>boolean</code> model to control either the
 * <em>enabled</em> or <em>visible</em> properties of a set of SWT controls;
 * i.e. the controls' properties are kept in sync with the <code>boolean</code>
 * model, but <em>not</em> vice-versa.
 * <p>
 * Once all the controls are disposed, this controller is kaput.
 * <p>
 * <strong>NB:</strong> This controller assumes all the controls will be,
 * effectively, disposed at the same time.
 * 
 * @see PropertyValueModel
 * @see Control#setEnabled(boolean)
 * @see Control#setVisible(boolean)
 */
final class CompositeBooleanControlStateModelBinding<C extends Control>
	extends BooleanControlStateModelBinding<C>
{
	private final HashSet<C> controls = new HashSet<C>();


	/**
	 * Constructor - the <code>boolean</code> model, the set of controls,
	 * and the adapter are required.
	 */
	CompositeBooleanControlStateModelBinding(
			PropertyValueModel<Boolean> booleanModel,
			Iterable<C> controls,
			boolean defaultValue,
			Adapter<C> adapter
	) {
		super(booleanModel, defaultValue, adapter);
		if (controls == null) {
			throw new NullPointerException();
		}
		for (C control : controls) {
			if (control == null) {
				throw new NullPointerException();
			}
			if ( ! this.controls.add(control)) {
				throw new IllegalArgumentException("duplicate control: " + control); //$NON-NLS-1$
			}
		}
		if (this.controls.isEmpty()) {
			throw new IllegalArgumentException("no controls"); //$NON-NLS-1$
		}
		this.engageBooleanModel();
		for (C control : this.controls) {
			this.engageControl(control);
		}
		this.setControlState();
	}


	@Override
	void setControlState() {
		for (C control : this.controls) {
			this.setControlState(control);
		}
	}

	@Override
	void controlDisposed(C control) {
		super.controlDisposed(control);
		this.controls.remove(control);
		if (this.controls.isEmpty()) {
			this.disengageBooleanModel();
		}
	}

	@Override
	boolean controlIsDisposed() {
		for (C control : this.controls) {
			if (control.isDisposed()) {
				return true;
			}
		}
		return false;
	}
}

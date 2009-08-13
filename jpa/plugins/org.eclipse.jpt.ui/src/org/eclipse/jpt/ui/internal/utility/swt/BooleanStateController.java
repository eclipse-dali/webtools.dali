/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.utility.swt;

import java.util.HashSet;

import org.eclipse.jpt.ui.internal.listeners.SWTPropertyChangeListenerWrapper;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Widget;

/**
 * This controller enables a model boolean to control either the 'enabled' or
 * 'visible' properties of a set of SWT controls; i.e. the
 * controls' properties are kept in synch with the model boolean,
 * but not vice-versa.
 * 
 * @see PropertyValueModel
 * @see Control#setEnabled(boolean)
 * @see Control#setVisible(boolean)
 */
final class BooleanStateController {

	/** A value model on the underlying model boolean. */
	private final PropertyValueModel<Boolean> booleanHolder;

	/**
	 * A listener that allows us to synchronize the control states with
	 * changes in the value of the model boolean.
	 */
	private final PropertyChangeListener booleanChangeListener;

	/**
	 * The set of controls whose state is kept in sync with the model boolean.
	 */
	private final HashSet<Control> controls = new HashSet<Control>();

	/**
	 * A listener that allows us to stop listening to stuff when all the
	 * controls are disposed. (Critical for preventing memory leaks.)
	 */
	private final DisposeListener controlDisposeListener;

	/**
	 * The default setting for the state; for when the underlying model is
	 * <code>null</code>. The default [default value] is <code>false<code>.
	 */
	private final boolean defaultValue;

	/**
	 * The adapter determines whether the 'enabled' or 'visible' property is
	 * controlled.
	 */
	private final Adapter adapter;


	// ********** constructor **********

	/**
	 * Constructor - the boolean holder, the controls, and the adapter are required.
	 */
	BooleanStateController(
			PropertyValueModel<Boolean> booleanHolder,
			Iterable<? extends Control> controls,
			boolean defaultValue,
			Adapter adapter
	) {
		super();
		if ((booleanHolder == null) || (controls == null) || (adapter == null)) {
			throw new NullPointerException();
		}
		this.booleanHolder = booleanHolder;
		this.defaultValue = defaultValue;
		this.adapter = adapter;

		this.booleanChangeListener = this.buildBooleanChangeListener();
		this.booleanHolder.addPropertyChangeListener(PropertyValueModel.VALUE, this.booleanChangeListener);

		this.controlDisposeListener = this.buildControlDisposeListener();
		this.addControls(controls);
	}


	// ********** initialization **********

	private PropertyChangeListener buildBooleanChangeListener() {
		return new SWTPropertyChangeListenerWrapper(this.buildBooleanChangeListener_());
	}

	private PropertyChangeListener buildBooleanChangeListener_() {
		return new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent event) {
				BooleanStateController.this.booleanChanged(event);
			}
			@Override
			public String toString() {
				return "boolean listener"; //$NON-NLS-1$
			}
		};
	}

	private DisposeListener buildControlDisposeListener() {
		return new DisposeListener() {
			public void widgetDisposed(DisposeEvent event) {
				BooleanStateController.this.controlDisposed(event);
			}
			@Override
			public String toString() {
				return "control dispose listener"; //$NON-NLS-1$
			}
		};
	}


	// ********** behavior **********

	private void addControls(Iterable<? extends Control> controls_) {
		boolean b = this.getBooleanValue();
		for (Control control : controls_) {
			this.addControl(control, b);
		}
	}

	private void addControl(Control control, boolean b) {
		if (this.controls.add(control)) {
			control.addDisposeListener(this.controlDisposeListener);
			this.setControlState(control, b);
		}
	}

	/**
	 * The model has changed - synchronize the controls.
	 * If the new model value is null, use the controller's default value
	 * (which is typically false).
	 */
	void booleanChanged(PropertyChangeEvent event) {
		this.setControlStates((Boolean) event.getNewValue());
	}

	private void setControlStates(Boolean b) {
		this.setControlStates(this.booleanValue(b));
	}

	private void setControlStates(boolean b) {
		for (Control control : this.controls) {
			this.setControlState(control, b);
		}
	}

	private boolean getBooleanValue() {
		return this.booleanValue(this.booleanHolder.getValue());
	}

	private boolean booleanValue(Boolean b) {
		return (b != null) ? b.booleanValue() : this.getDefaultValue();
	}

	private void setControlState(Control control, boolean b) {
		if ( ! control.isDisposed()) {
			this.adapter.setState(control, b);
		}
	}

	private boolean getDefaultValue() {
		return this.defaultValue;
	}


	// ********** dispose **********

	void controlDisposed(DisposeEvent event) {
		// the control is not yet "disposed" when we receive this event
		// so we can still remove our listener
		Widget control = event.widget;
		control.removeDisposeListener(this.controlDisposeListener);
		this.controls.remove(control);
		if (this.controls.isEmpty()) {
			this.booleanHolder.removePropertyChangeListener(PropertyValueModel.VALUE, this.booleanChangeListener);
		}
	}


	// ********** standard methods **********

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.booleanHolder);
	}


	// ********** adapters **********

	interface Adapter {
		void setState(Control control, boolean b);
	}

}

/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal.swt.bind;

import org.eclipse.jpt.common.ui.internal.listeners.SWTPropertyChangeListenerWrapper;
import org.eclipse.jpt.common.ui.internal.swt.events.DisposeAdapter;
import org.eclipse.jpt.common.ui.internal.swt.widgets.DisplayTools;
import org.eclipse.jpt.common.utility.internal.RunnableAdapter;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeAdapter;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Control;

/**
 * This controller enables a boolean model to control either the
 * <em>enabled</em> or <em>visible</em> properties of SWT controls; i.e. the
 * controls' properties are kept in synch with the boolean model,
 * but <em>not</em> vice-versa.
 * <p>
 * Subclasses must manage the listeners; i.e. the engaging and disengaging of
 * the boolean model and the control(s).
 * 
 * @see PropertyValueModel
 * @see Control#setEnabled(boolean)
 * @see Control#setVisible(boolean)
 */
abstract class BooleanStateController {

	/**
	 * The controlling boolean model.
	 */
	private final PropertyValueModel<Boolean> booleanModel;

	/**
	 * A listener that allows us to synchronize the control states with
	 * changes in the value of the boolean model.
	 */
	private final PropertyChangeListener booleanChangeListener;

	/**
	 * A listener that allows us to stop listening to stuff when all the
	 * controls are disposed. (Critical for preventing memory leaks.)
	 */
	private final DisposeListener controlDisposeListener;

	/**
	 * The default setting for the state; for when the underlying boolean model is
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
	 * Constructor - the boolean model and the adapter are required.
	 */
	BooleanStateController(PropertyValueModel<Boolean> booleanModel, boolean defaultValue, Adapter adapter) {
		super();
		if ((booleanModel == null) || (adapter == null)) {
			throw new NullPointerException();
		}
		this.booleanModel = booleanModel;
		this.defaultValue = defaultValue;
		this.adapter = adapter;

		this.booleanChangeListener = this.buildBooleanChangeListener();
		this.controlDisposeListener = this.buildControlDisposeListener();
	}


	// ********** initialization **********

	private PropertyChangeListener buildBooleanChangeListener() {
		return new SWTPropertyChangeListenerWrapper(this.buildBooleanChangeListener_());
	}

	private PropertyChangeListener buildBooleanChangeListener_() {
		return new BooleanChangeListener();
	}

	/* CU private */ class BooleanChangeListener
		extends PropertyChangeAdapter
	{
		@Override
		public void propertyChanged(PropertyChangeEvent event) {
			BooleanStateController.this.booleanChanged(event);
		}
	}

	private DisposeListener buildControlDisposeListener() {
		return new ControlDisposeListener();
	}

	/* CU private */ class ControlDisposeListener
		extends DisposeAdapter
	{
		@Override
		public void widgetDisposed(DisposeEvent event) {
			// the control is not yet "disposed" when we receive this event
			// so we can still remove our listener
			BooleanStateController.this.controlDisposed((Control) event.widget);
		}
	}


	// ********** boolean model **********

	void engageBooleanModel() {
		this.booleanModel.addPropertyChangeListener(PropertyValueModel.VALUE, this.booleanChangeListener);
	}

	void disengageBooleanModel() {
		this.booleanModel.removePropertyChangeListener(PropertyValueModel.VALUE, this.booleanChangeListener);
	}

	/**
	 * The boolean model has changed - synchronize the controls.
	 * If the new boolean model value is <code>null</code>, use the controller's
	 * default value (which is typically false).
	 */
	/* CU private */ void booleanChanged(PropertyChangeEvent event) {
		this.setControlState((Boolean) event.getNewValue());
	}


	boolean getBooleanValue() {
		return this.booleanValue(this.booleanModel.getValue());
	}

	private boolean booleanValue(Boolean b) {
		return (b != null) ? b.booleanValue() : this.defaultValue;
	}


	// ********** control **********

	void engageControl(Control control) {
		control.addDisposeListener(this.controlDisposeListener);
	}

	void disengageControl(Control control) {
		control.removeDisposeListener(this.controlDisposeListener);
	}

	private void setControlState(Boolean controlState) {
		this.setControlState(this.booleanValue(controlState));
	}

	abstract void setControlState(boolean controlState);

	void setControlState(Control control, boolean controlState) {
		DisplayTools.execute(new SetControlStateRunnable(control, controlState));
	}

	/* CU private */ class SetControlStateRunnable
		extends RunnableAdapter
	{
		private final Control control;
		private final boolean controlState;
		SetControlStateRunnable(Control control, boolean controlState) {
			super();
			this.control = control;
			this.controlState = controlState;
		}
		@Override
		public void run() {
			BooleanStateController.this.setControlState_(this.control, this.controlState);
		}
	}

	/* CU private */ void setControlState_(Control control, boolean controlState) {
		if ( ! control.isDisposed()) {
			this.adapter.setState(control, controlState);
		}
	}

	void controlDisposed(Control control) {
		this.disengageControl(control);
	}


	// ********** misc **********

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.booleanModel);
	}


	// ********** adapter interface **********

	interface Adapter {
		void setState(Control control, boolean controlState);
	}
}

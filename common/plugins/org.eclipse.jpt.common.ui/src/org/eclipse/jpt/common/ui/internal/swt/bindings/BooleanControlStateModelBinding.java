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

import org.eclipse.jpt.common.ui.internal.swt.events.DisposeAdapter;
import org.eclipse.jpt.common.ui.internal.swt.listeners.SWTListenerTools;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeAdapter;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Control;

/**
 * This binding enables a <code>boolean</code> model to control either the
 * <em>enabled</em> or <em>visible</em> properties of SWT controls; i.e. the
 * controls' properties are kept in sync with the <code>boolean</code> model,
 * but <em>not</em> vice-versa.
 * <p>
 * Subclasses must manage the listeners; i.e. the engaging and disengaging of
 * the <code>boolean</code> model and the control(s).
 * 
 * @param <C> the type of the control
 * 
 * @see PropertyValueModel
 * @see Control#setEnabled(boolean)
 * @see Control#setVisible(boolean)
 */
abstract class BooleanControlStateModelBinding<C extends Control> {

	/**
	 * The controlling <code>boolean</code> model.
	 */
	private final PropertyValueModel<Boolean> booleanModel;

	/**
	 * A listener that allows us to synchronize the control states with
	 * changes in the value of the <code>boolean</code> model.
	 */
	private final PropertyChangeListener booleanListener;

	/**
	 * Cache the <code>boolean</code> value.
	 */
	boolean booleanValue;

	/**
	 * The default setting for the state; for when the underlying
	 * <code>boolean</code> model is <code>null</code>.
	 * The default [default value] is <code>false<code>.
	 */
	private final boolean defaultValue;

	/**
	 * The adapter determines whether the <em>enabled</em> or <em>visible</em>
	 * property is controlled.
	 */
	private final Adapter<C> adapter;

	/**
	 * A listener that allows us to stop listening to stuff when all the
	 * controls are disposed. (Critical for preventing memory leaks.)
	 */
	private final DisposeListener controlDisposeListener;


	/**
	 * Constructor - the <code>boolean</code> model and the adapter are required.
	 */
	BooleanControlStateModelBinding(PropertyValueModel<Boolean> booleanModel, boolean defaultValue, Adapter<C> adapter) {
		super();
		if ((booleanModel == null) || (adapter == null)) {
			throw new NullPointerException();
		}
		this.booleanModel = booleanModel;
		this.defaultValue = defaultValue;
		this.booleanValue = this.booleanValue(null);
		this.adapter = adapter;

		this.booleanListener = this.buildBooleanListener();
		this.controlDisposeListener = this.buildControlDisposeListener();
	}


	// ********** initialization **********

	private PropertyChangeListener buildBooleanListener() {
		return SWTListenerTools.wrap(new BooleanListener());
	}

	/* CU private */ class BooleanListener
		extends PropertyChangeAdapter
	{
		@Override
		public void propertyChanged(PropertyChangeEvent event) {
			BooleanControlStateModelBinding.this.booleanChanged(event);
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
			@SuppressWarnings("unchecked")
			C control = (C) event.widget;
			BooleanControlStateModelBinding.this.controlDisposed(control);
		}
	}


	// ********** boolean model **********

	void engageBooleanModel() {
		this.booleanModel.addPropertyChangeListener(PropertyValueModel.VALUE, this.booleanListener);
		this.booleanValue = this.booleanValue(this.booleanModel.getValue());
	}

	void disengageBooleanModel() {
		this.booleanModel.removePropertyChangeListener(PropertyValueModel.VALUE, this.booleanListener);
		this.booleanValue = this.booleanValue(null);
	}

	/**
	 * The <code>boolean</code> model has changed - synchronize the controls.
	 * If the new <code>boolean</code> model value is <code>null</code>, use
	 * the controller's default value (which is typically <code>false</code>).
	 */
	/* CU private */ void booleanChanged(PropertyChangeEvent event) {
		if ( ! this.controlIsDisposed()) {
			this.booleanChanged((Boolean) event.getNewValue());
		}
	}

	private void booleanChanged(Boolean b) {
		this.booleanValue = this.booleanValue(b);
		this.setControlState();
	}

	private boolean booleanValue(Boolean b) {
		return (b != null) ? b.booleanValue() : this.defaultValue;
	}


	// ********** control **********

	void engageControl(C control) {
		control.addDisposeListener(this.controlDisposeListener);
	}

	void disengageControl(C control) {
		control.removeDisposeListener(this.controlDisposeListener);
	}

	/**
	 * Set the state of the control(s).
	 * @see #setControlState(Control)
	 */
	abstract void setControlState();

	void setControlState(C control) {
		if ( ! control.isDisposed()) {
			this.adapter.setState(control, this.booleanValue);
		}
	}

	void controlDisposed(C control) {
		this.disengageControl(control);
	}


	// ********** misc **********

	abstract boolean controlIsDisposed();

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.booleanModel);
	}


	// ********** adapter interface **********

	interface Adapter<C extends Control> {
		void setState(C control, boolean controlState);
	}
}

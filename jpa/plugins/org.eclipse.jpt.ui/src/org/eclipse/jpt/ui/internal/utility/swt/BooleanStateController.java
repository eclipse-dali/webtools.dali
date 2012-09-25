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

import org.eclipse.jpt.ui.internal.listeners.SWTPropertyChangeListenerWrapper;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
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
				// the control is not yet "disposed" when we receive this event
				// so we can still remove our listener
				BooleanStateController.this.controlDisposed((Control) event.widget);
			}
			@Override
			public String toString() {
				return "control dispose listener"; //$NON-NLS-1$
			}
		};
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
	/* private */ void booleanChanged(PropertyChangeEvent event) {
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

	private void setControlState(Boolean b) {
		this.setControlState(this.booleanValue(b));
	}

	abstract void setControlState(boolean b);

	void setControlState(Control control, boolean b) {
		if ( ! control.isDisposed()) {
			this.adapter.setState(control, b);
		}
	}

	void controlDisposed(Control control) {
		this.disengageControl(control);
	}


	// ********** standard methods **********

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.booleanModel);
	}


	// ********** adapter interface **********

	interface Adapter {
		void setState(Control control, boolean b);
	}

}

/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.swt;

import org.eclipse.jpt.ui.internal.listeners.SWTPropertyChangeListenerWrapper;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;

/**
 * This adapter can be used to keep a check box or toggle button in synch with
 * a model boolean.
 */
@SuppressWarnings("nls")
public class BooleanButtonModelAdapter {

	/** A value model on the underlying model boolean. */
	protected final WritablePropertyValueModel<Boolean> booleanHolder;

	/**
	 * A listener that allows us to synchronize the button's selection state with
	 * the model boolean.
	 */
	protected final PropertyChangeListener booleanChangeListener;

	/** The check box/toggle button we synchronize with the model boolean. */
	protected final Button button;

	/**
	 * A listener that allows us to synchronize the model boolean with
	 * the button's selection state.
	 */
	protected final SelectionListener buttonSelectionListener;

	/**
	 * A listener that allows us to stop listening to stuff when the button
	 * is disposed.
	 */
	protected final DisposeListener buttonDisposeListener;

	/**
	 * The default setting for the check box/toggle button; for when the
	 * underlying model is null.
	 * The default [default value] is false (i.e. the button is checked/popped out).
	 */
	protected final boolean defaultValue;


	// ********** static methods **********

	/**
	 * Adapt the specified boolean to the specified button.
	 * If the boolean is null, the button's value will be "unselected".
	 */
	public static BooleanButtonModelAdapter adapt(WritablePropertyValueModel<Boolean> booleanHolder, Button button) {
		return new BooleanButtonModelAdapter(booleanHolder, button);
	}

	/**
	 * Adapt the specified boolean to the specified button.
	 * If the boolean is null, the button's value will be the specified default value.
	 */
	public static BooleanButtonModelAdapter adapt(WritablePropertyValueModel<Boolean> booleanHolder, Button button, boolean defaultValue) {
		return new BooleanButtonModelAdapter(booleanHolder, button, defaultValue);
	}


	// ********** constructors **********

	/**
	 * Constructor - the boolean holder and button are required.
	 * The default value will be false.
	 */
	protected BooleanButtonModelAdapter(WritablePropertyValueModel<Boolean> booleanHolder, Button button) {
		this(booleanHolder, button, false);
	}

	/**
	 * Constructor - the boolean holder and button are required.
	 */
	protected BooleanButtonModelAdapter(WritablePropertyValueModel<Boolean> booleanHolder, Button button, boolean defaultValue) {
		super();
		if ((booleanHolder == null) || (button == null)) {
			throw new NullPointerException();
		}
		this.booleanHolder = booleanHolder;
		this.button = button;
		this.defaultValue = defaultValue;

		this.booleanChangeListener = this.buildBooleanChangeListener();
		this.booleanHolder.addPropertyChangeListener(PropertyValueModel.VALUE, this.booleanChangeListener);

		this.buttonDisposeListener = this.buildButtonDisposeListener();
		this.button.addDisposeListener(this.buttonDisposeListener);

		this.buttonSelectionListener = this.buildButtonSelectionListener();
		this.button.addSelectionListener(this.buttonSelectionListener);

		this.setButtonSelection(this.booleanHolder.getValue());
	}


	// ********** initialization **********

	protected PropertyChangeListener buildBooleanChangeListener() {
		return new SWTPropertyChangeListenerWrapper(this.buildBooleanChangeListener_());
	}

	protected PropertyChangeListener buildBooleanChangeListener_() {
		return new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent event) {
				BooleanButtonModelAdapter.this.booleanChanged(event);
			}
		    @Override
			public String toString() {
				return "boolean listener";
			}
		};
	}

	protected SelectionListener buildButtonSelectionListener() {
		return new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				BooleanButtonModelAdapter.this.buttonSelected(event);
			}
		    @Override
			public String toString() {
				return "button selection listener";
			}
		};
	}

	protected DisposeListener buildButtonDisposeListener() {
		return new DisposeListener() {
			public void widgetDisposed(DisposeEvent event) {
				BooleanButtonModelAdapter.this.buttonDisposed(event);
			}
		    @Override
			public String toString() {
				return "button dispose listener";
			}
		};
	}


	// ********** behavior **********

	/**
	 * The model has changed - synchronize the button.
	 * If the new model value is null, use the adapter's default value
	 * (which is typically false).
	 */
	protected void booleanChanged(PropertyChangeEvent event) {
		this.setButtonSelection((Boolean) event.getNewValue());
	}

	protected void setButtonSelection(Boolean b) {
		if (this.button.isDisposed()) {
			return;
		}
		this.button.setSelection(this.booleanValue(b));
	}

	protected boolean booleanValue(Boolean b) {
		return (b != null) ? b.booleanValue() : this.defaultValue();
	}

	protected boolean defaultValue() {
		return this.defaultValue;
	}

	/**
	 * The button has been "selected" - synchronize the model.
	 */
	protected void buttonSelected(SelectionEvent event) {
		if (this.button.isDisposed()) {
			return;
		}
		this.booleanHolder.setValue(Boolean.valueOf(this.button.getSelection()));
	}


	// ********** dispose **********

	protected void buttonDisposed(DisposeEvent event) {
		// the button is not yet "disposed" when we receive this event
		// so we can still remove our listeners
		this.button.removeSelectionListener(this.buttonSelectionListener);
		this.button.removeDisposeListener(this.buttonDisposeListener);
		this.booleanHolder.removePropertyChangeListener(PropertyValueModel.VALUE, this.booleanChangeListener);
	}


	// ********** standard methods **********

    @Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.booleanHolder);
	}

}

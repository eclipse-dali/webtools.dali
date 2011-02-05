/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal.utility.swt;

import org.eclipse.jpt.common.ui.internal.listeners.SWTPropertyChangeListenerWrapper;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.WritablePropertyValueModel;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;

/**
 * This binding can be used to keep a check-box, toggle button, or radio button
 * "selection" synchronized with a model boolean.
 * 
 * @see WritablePropertyValueModel
 * @see Button
 */
@SuppressWarnings("nls")
final class BooleanButtonModelBinding {

	// ***** model
	/** A value model on the underlying model boolean. */
	private final WritablePropertyValueModel<Boolean> booleanModel;

	/**
	 * A listener that allows us to synchronize the button's selection state with
	 * the model boolean.
	 */
	private final PropertyChangeListener booleanChangeListener;

	/**
	 * The default setting for the check-box/toggle button/radio button;
	 * for when the underlying model is <code>null</code>.
	 * The default [default value] is <code>false</code> (i.e. the check-box
	 * is unchecked/toggle button popped out/radio button unchecked).
	 */
	private final boolean defaultValue;

	// ***** UI
	/** The check-box/toggle button/radio button we synchronize with the model boolean. */
	private final Button button;

	/**
	 * A listener that allows us to synchronize the model boolean with
	 * the button's selection state.
	 */
	private final SelectionListener buttonSelectionListener;

	/**
	 * A listener that allows us to stop listening to stuff when the button
	 * is disposed. (Critical for preventing memory leaks.)
	 */
	private final DisposeListener buttonDisposeListener;


	// ********** constructor **********

	/**
	 * Constructor - the boolean model and button are required.
	 */
	BooleanButtonModelBinding(WritablePropertyValueModel<Boolean> booleanModel, Button button, boolean defaultValue) {
		super();
		if ((booleanModel == null) || (button == null)) {
			throw new NullPointerException();
		}
		this.booleanModel = booleanModel;
		this.button = button;
		this.defaultValue = defaultValue;

		this.booleanChangeListener = this.buildBooleanChangeListener();
		this.booleanModel.addPropertyChangeListener(PropertyValueModel.VALUE, this.booleanChangeListener);

		this.buttonSelectionListener = this.buildButtonSelectionListener();
		this.button.addSelectionListener(this.buttonSelectionListener);

		this.buttonDisposeListener = this.buildButtonDisposeListener();
		this.button.addDisposeListener(this.buttonDisposeListener);

		this.setButtonSelection(this.booleanModel.getValue());
	}


	// ********** initialization **********

	private PropertyChangeListener buildBooleanChangeListener() {
		return new SWTPropertyChangeListenerWrapper(this.buildBooleanChangeListener_());
	}

	private PropertyChangeListener buildBooleanChangeListener_() {
		return new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent event) {
				BooleanButtonModelBinding.this.booleanChanged(event);
			}
		    @Override
			public String toString() {
				return "boolean listener";
			}
		};
	}

	private SelectionListener buildButtonSelectionListener() {
		return new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				BooleanButtonModelBinding.this.buttonSelected();
			}
		    @Override
			public String toString() {
				return "button selection listener";
			}
		};
	}

	private DisposeListener buildButtonDisposeListener() {
		return new DisposeListener() {
			public void widgetDisposed(DisposeEvent event) {
				BooleanButtonModelBinding.this.buttonDisposed();
			}
		    @Override
			public String toString() {
				return "button dispose listener";
			}
		};
	}


	// ********** boolean model events **********

	/**
	 * The model has changed - synchronize the button.
	 * If the new model value is null, use the binding's default value
	 * (which is typically false).
	 */
	/* CU private */ void booleanChanged(PropertyChangeEvent event) {
		this.setButtonSelection((Boolean) event.getNewValue());
	}

	private void setButtonSelection(Boolean b) {
		if ( ! this.button.isDisposed()) {
			this.button.setSelection(this.booleanValue(b));
		}
	}

	private boolean booleanValue(Boolean b) {
		return (b != null) ? b.booleanValue() : this.defaultValue;
	}


	// ********** button events **********

	/**
	 * The button has been "selected" - synchronize the model.
	 */
	/* CU private */ void buttonSelected() {
		if ( ! this.button.isDisposed()) {
			this.booleanModel.setValue(Boolean.valueOf(this.button.getSelection()));
		}
	}

	/* CU private */ void buttonDisposed() {
		// the button is not yet "disposed" when we receive this event
		// so we can still remove our listeners
		this.button.removeSelectionListener(this.buttonSelectionListener);
		this.button.removeDisposeListener(this.buttonDisposeListener);
		this.booleanModel.removePropertyChangeListener(PropertyValueModel.VALUE, this.booleanChangeListener);
	}


	// ********** standard methods **********

    @Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.booleanModel);
	}

}

/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.swt;

import org.eclipse.jpt.ui.internal.listeners.SWTPropertyChangeListenerWrapper;
import org.eclipse.jpt.ui.internal.widgets.TriStateCheckBox;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.model.event.PropertyChangeEvent;
import org.eclipse.jpt.utility.internal.model.listener.PropertyChangeListener;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.WritablePropertyValueModel;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

/**
 * This adapter can be used to keep a tri-state check box in synch with
 * a model Boolean where the value can be <code>null</code>.
 */
@SuppressWarnings("nls")
public class TriStateBooleanButtonModelAdapter {

	/** A value model on the underlying model boolean. */
	protected final WritablePropertyValueModel<Boolean> booleanHolder;

	/**
	 * A listener that allows us to synchronize the button's selection state with
	 * the model boolean.
	 */
	protected final PropertyChangeListener booleanChangeListener;

	/** The check box/toggle button we synchronize with the model boolean. */
	protected final TriStateCheckBox button;

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


	// ********** static methods **********

	/**
	 * Adapt the specified boolean to the specified button.
	 * If the boolean is null, the button's value will be "unselected".
	 */
	public static TriStateBooleanButtonModelAdapter adapt(WritablePropertyValueModel<Boolean> booleanHolder, TriStateCheckBox button) {
		return new TriStateBooleanButtonModelAdapter(booleanHolder, button);
	}


	// ********** constructors **********

	/**
	 * Constructor - the boolean holder and button are required.
	 */
	protected TriStateBooleanButtonModelAdapter(WritablePropertyValueModel<Boolean> booleanHolder, TriStateCheckBox button) {
		super();
		if ((booleanHolder == null) || (button == null)) {
			throw new NullPointerException();
		}
		this.booleanHolder = booleanHolder;
		this.button = button;

		this.booleanChangeListener = this.buildBooleanChangeListener();
		this.booleanHolder.addPropertyChangeListener(PropertyValueModel.VALUE, this.booleanChangeListener);

		this.buttonDisposeListener = this.buildButtonDisposeListener();
		this.button.addDisposeListener(this.buttonDisposeListener);

		this.buttonSelectionListener = this.buildButtonSelectionListener();
		this.button.addSelectionListener(this.buttonSelectionListener);

		this.setButtonSelection(this.booleanHolder.value());
	}


	// ********** initialization **********

	protected PropertyChangeListener buildBooleanChangeListener() {
		return new SWTPropertyChangeListenerWrapper(this.buildBooleanChangeListener_());
	}

	protected PropertyChangeListener buildBooleanChangeListener_() {
		return new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent event) {
				TriStateBooleanButtonModelAdapter.this.booleanChanged(event);
			}
			@Override
			public String toString() {
				return "tri-state boolean listener";
			}
		};
	}

	protected SelectionListener buildButtonSelectionListener() {
		return new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				TriStateBooleanButtonModelAdapter.this.buttonSelected(event);
			}
			@Override
			public String toString() {
				return "tri-state button selection listener";
			}
		};
	}

	protected DisposeListener buildButtonDisposeListener() {
		return new DisposeListener() {
			public void widgetDisposed(DisposeEvent event) {
				TriStateBooleanButtonModelAdapter.this.buttonDisposed(event);
			}
		    @Override
			public String toString() {
				return "tri-state button dispose listener";
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
		this.setButtonSelection((Boolean) event.newValue());
	}

	protected void setButtonSelection(Boolean selection) {
		this.button.setSelection(selection);
	}

	/**
	 * The button has been "selected" - synchronize the model.
	 */
	protected void buttonSelected(SelectionEvent event) {
		this.booleanHolder.setValue(button.getSelection());
	}


	// ********** dispose **********

	protected void buttonDisposed(DisposeEvent event) {
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

/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal.swt;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jpt.common.ui.internal.swt.listeners.SWTListenerTools;
import org.eclipse.jpt.common.ui.internal.widgets.TriStateCheckBox;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
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
public class TriStateCheckBoxModelAdapter {

	/** A value model on the underlying model boolean. */
	protected final ModifiablePropertyValueModel<Boolean> booleanHolder;

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
	 * If the boolean is null, the button's value will be "partially checked"
	 * (i.e. the button will be checked but grayed out).
	 */
	public static TriStateCheckBoxModelAdapter adapt(ModifiablePropertyValueModel<Boolean> booleanHolder, TriStateCheckBox button) {
		return new TriStateCheckBoxModelAdapter(booleanHolder, button);
	}


	// ********** constructors **********

	/**
	 * Constructor - the boolean holder and button are required.
	 */
	protected TriStateCheckBoxModelAdapter(ModifiablePropertyValueModel<Boolean> booleanHolder, TriStateCheckBox button) {
		super();

		Assert.isNotNull(booleanHolder, "The boolean holder cannot be null");
		Assert.isNotNull(button, "The check box cannot be null");

		this.booleanHolder = booleanHolder;
		this.button = button;

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
		return SWTListenerTools.wrap(this.buildBooleanChangeListener_(), this.button.getCheckBox());
	}

	protected PropertyChangeListener buildBooleanChangeListener_() {
		return new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent event) {
				TriStateCheckBoxModelAdapter.this.booleanChanged(event);
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
				TriStateCheckBoxModelAdapter.this.buttonSelected(event);
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
				TriStateCheckBoxModelAdapter.this.buttonDisposed(event);
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
		this.setButtonSelection((Boolean) event.getNewValue());
	}

	protected void setButtonSelection(Boolean selection) {
		if (this.button.isDisposed()) {
			return;
		}
		this.button.setSelection(selection);
	}

	/**
	 * The button has been "selected" - synchronize the model.
	 */
	protected void buttonSelected(SelectionEvent event) {
		if (this.button.isDisposed()) {
			return;
		}
		this.booleanHolder.setValue(button.getSelection());
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
		return ObjectTools.toString(this, this.booleanHolder);
	}

}

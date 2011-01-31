/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal.swt;

import org.eclipse.jpt.common.ui.internal.listeners.SWTPropertyChangeListenerWrapper;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Spinner;

/**
 *
 */
@SuppressWarnings("nls")
public class SpinnerModelAdapter {

	/**
	 * A value model on the underlying model list.
	 */
	protected final WritablePropertyValueModel<Integer> numberHolder;

	/**
	 * A listener that allows us to synchronize the spinner's contents with
	 * the model list.
	 */
	protected final PropertyChangeListener propertyChangeListener;

	/**
	 * The spinner we keep synchronized with the model string.
	 */
	protected final Spinner spinner;

	/**
	 * A listener that allows us to synchronize our selection number holder
	 * with the spinner's value.
	 */
	protected final ModifyListener spinnerModifyListener;

	/**
	 * A listener that allows us to stop listening to stuff when the spinner
	 * is disposed.
	 */
	protected final DisposeListener spinnerDisposeListener;

	/**
	 * The value shown when the number holder's value is <code>null</code>.
	 */
	protected final int defaultValue;

	/**
	 * This lock is used to prevent the listeners to be notified when the value
	 * changes from the spinner or from the holder.
	 */
	private boolean locked;

	// ********** static methods **********

	/**
	 * Adapt the specified model list and selections to the specified spinner.
	 * Use the specified string converter to convert the model items to strings
	 * to be displayed in the spinner.
	 */
	public static SpinnerModelAdapter adapt(
			WritablePropertyValueModel<Integer> numberHolder,
			Spinner spinner,
			int defaultValue)
	{
		return new SpinnerModelAdapter(numberHolder, spinner, defaultValue);
	}


	// ********** constructors **********

	/**
	 * Constructor - the list holder, selections holder, list box, and
	 * string converter are required.
	 */
	protected SpinnerModelAdapter(WritablePropertyValueModel<Integer> numberHolder,
	                              Spinner spinner,
	                              int defaultValue) {
		super();
		if ((numberHolder == null) || (spinner == null)) {
			throw new NullPointerException();
		}
		this.numberHolder = numberHolder;
		this.spinner = spinner;
		this.defaultValue = defaultValue;

		this.propertyChangeListener = this.buildPropertyChangeListener();
		this.numberHolder.addPropertyChangeListener(PropertyValueModel.VALUE, this.propertyChangeListener);

		this.spinnerModifyListener = this.buildSpinnerModifyListener();
		this.spinner.addModifyListener(this.spinnerModifyListener);

		this.spinnerDisposeListener = this.buildSpinnerDisposeListener();
		this.spinner.addDisposeListener(this.spinnerDisposeListener);

		this.updateSpinner(numberHolder.getValue());
	}


	// ********** initialization **********

	protected PropertyChangeListener buildPropertyChangeListener() {
		return new SWTPropertyChangeListenerWrapper(this.buildPropertyChangeListener_());
	}

	protected PropertyChangeListener buildPropertyChangeListener_() {
		return new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent event) {
				SpinnerModelAdapter.this.valueChanged(event);
			}
			@Override
			public String toString() {
				return "spinner listener";
			}
		};
	}

	protected ModifyListener buildSpinnerModifyListener() {
		return new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				SpinnerModelAdapter.this.spinnerModified(e);
			}
			@Override
			public String toString() {
				return "spinner selection listener";
			}
		};
	}

	protected DisposeListener buildSpinnerDisposeListener() {
		return new DisposeListener() {
			public void widgetDisposed(DisposeEvent event) {
				SpinnerModelAdapter.this.spinnerDisposed(event);
			}
			@Override
			public String toString() {
				return "spinner dispose listener";
			}
		};
	}


	// ********** model events **********

	protected void valueChanged(PropertyChangeEvent event) {
		if (!this.locked) {
			this.updateSpinner((Integer) event.getNewValue());
		}
	}


	// ********** spinner events **********

	protected void spinnerModified(ModifyEvent event) {
		if (!this.locked) {
			this.locked = true;
			try {
				this.numberHolder.setValue(this.spinner.getSelection());
			}
			finally {
				this.locked = false;
			}
		}
	}

	protected void spinnerDisposed(DisposeEvent event) {
		// the spinner is not yet "disposed" when we receive this event
		// so we can still remove our listeners
		this.spinner.removeDisposeListener(this.spinnerDisposeListener);
		this.spinner.removeModifyListener(this.spinnerModifyListener);
		this.numberHolder.removePropertyChangeListener(PropertyValueModel.VALUE, this.propertyChangeListener);
	}

	// ********** update **********

	protected void updateSpinner(Integer value) {
		if (this.spinner.isDisposed()) {
			return;
		}
		// the model can be null, but the spinner cannot
		if (value == null) {
			value = defaultValue;
		}
		this.locked = true;
		try {
			this.spinner.setSelection(value);
		}
		finally {
			this.locked = false;
		}
	}

	// ********** standard methods **********

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.numberHolder);
	}
}

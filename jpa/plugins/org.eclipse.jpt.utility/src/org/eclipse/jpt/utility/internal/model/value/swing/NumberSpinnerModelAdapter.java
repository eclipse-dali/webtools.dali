/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal.model.value.swing;

import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeListener;

import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.model.event.PropertyChangeEvent;
import org.eclipse.jpt.utility.internal.model.listener.PropertyChangeListener;
import org.eclipse.jpt.utility.internal.model.listener.awt.AWTPropertyChangeListenerWrapper;
import org.eclipse.jpt.utility.internal.model.value.WritablePropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;

/**
 * This javax.swing.SpinnerNumberModel can be used to keep a ChangeListener
 * (e.g. a JSpinner) in synch with a PropertyValueModel that holds a number.
 * 
 * This class must be a sub-class of SpinnerNumberModel because of some
 * crappy jdk code....  ~bjv
 * @see javax.swing.JSpinner#createEditor(javax.swing.SpinnerModel)
 * 
 * If this class needs to be modified, it would behoove us to review the
 * other, similar classes:
 * @see DateSpinnerModelAdapter
 * @see ListSpinnerModelAdapter
 */
public class NumberSpinnerModelAdapter
	extends SpinnerNumberModel
{

	/**
	 * The default spinner value; used when the
	 * underlying model number value is null.
	 */
	private final Number defaultValue;

	/** A value model on the underlying number. */
	private final WritablePropertyValueModel<Number> numberHolder;

	/**
	 * A listener that allows us to synchronize with
	 * changes made to the underlying number.
	 */
	private final PropertyChangeListener numberChangeListener;


	// ********** constructors **********

	/**
	 * Constructor - the number holder is required.
	 * The default spinner value is zero.
	 * The step size is one.
	 */
	public NumberSpinnerModelAdapter(WritablePropertyValueModel<Number> numberHolder) {
		this(numberHolder, 0);
	}

	/**
	 * Constructor - the number holder is required.
	 * The step size is one.
	 */
	public NumberSpinnerModelAdapter(WritablePropertyValueModel<Number> numberHolder, int defaultValue) {
		this(numberHolder, null, null, new Integer(1), new Integer(defaultValue));
	}

	/**
	 * Constructor - the number holder is required.
	 * Use the minimum value as the default spinner value.
	 */
	public NumberSpinnerModelAdapter(WritablePropertyValueModel<Number> numberHolder, int minimum, int maximum, int stepSize) {
		this(numberHolder, minimum, maximum, stepSize, minimum);
	}

	/**
	 * Constructor - the number holder is required.
	 */
	public NumberSpinnerModelAdapter(WritablePropertyValueModel<Number> numberHolder, int minimum, int maximum, int stepSize, int defaultValue) {
		this(numberHolder, new Integer(minimum), new Integer(maximum), new Integer(stepSize), new Integer(defaultValue));
	}

	/**
	 * Constructor - the number holder is required.
	 * Use the minimum value as the default spinner value.
	 */
	public NumberSpinnerModelAdapter(WritablePropertyValueModel<Number> numberHolder, double value, double minimum, double maximum, double stepSize) {
		this(numberHolder, value, minimum, maximum, stepSize, minimum);
	}

	/**
	 * Constructor - the number holder is required.
	 */
	public NumberSpinnerModelAdapter(WritablePropertyValueModel<Number> numberHolder, double value, double minimum, double maximum, double stepSize, double defaultValue) {
		this(numberHolder, new Double(minimum), new Double(maximum), new Double(stepSize), new Double(defaultValue));
	}

	/**
	 * Constructor - the number holder is required.
	 */
	public NumberSpinnerModelAdapter(WritablePropertyValueModel<Number> numberHolder, Comparable<?> minimum, Comparable<?> maximum, Number stepSize, Number defaultValue) {
		super(numberHolder.value() == null ? defaultValue : (Number) numberHolder.value(), minimum, maximum, stepSize);
		this.numberHolder = numberHolder;
		this.numberChangeListener = this.buildNumberChangeListener();
		// postpone listening to the underlying number
		// until we have listeners ourselves...
		this.defaultValue = defaultValue;
	}


	// ********** initialization **********

	protected PropertyChangeListener buildNumberChangeListener() {
		return new AWTPropertyChangeListenerWrapper(this.buildNumberChangeListener_());
	}

	protected PropertyChangeListener buildNumberChangeListener_() {
		return new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent event) {
				NumberSpinnerModelAdapter.this.synchronize(event.newValue());
			}
			@Override
			public String toString() {
				return "number listener";
			}
		};
	}


	// ********** SpinnerModel implementation **********

	/**
	 * Extend to check whether this method is being called before we 
	 * have any listeners.
	 * This is necessary because some crappy jdk code gets the value
	 * from the model *before* listening to the model.  ~bjv
	 * @see javax.swing.JSpinner.DefaultEditor(javax.swing.JSpinner)
	 */
    @Override
	public Object getValue() {
		if (this.getChangeListeners().length == 0) {
			// sorry about this "lateral" call to super  ~bjv
			super.setValue(this.spinnerValueOf(this.numberHolder.value()));
		}
		return super.getValue();
	}

	/**
	 * Extend to update the underlying number directly.
	 * The resulting event will be ignored: @see #synchronizeDelegate(Object).
	 */
    @Override
	public void setValue(Object value) {
		super.setValue(value);
		this.numberHolder.setValue((Number) value);
	}

	/**
	 * Extend to start listening to the underlying number if necessary.
	 */
    @Override
	public void addChangeListener(ChangeListener listener) {
		if (this.getChangeListeners().length == 0) {
			this.numberHolder.addPropertyChangeListener(PropertyValueModel.VALUE, this.numberChangeListener);
			this.synchronize(this.numberHolder.value());
		}
		super.addChangeListener(listener);
	}

	/**
	 * Extend to stop listening to the underlying number if appropriate.
	 */
    @Override
	public void removeChangeListener(ChangeListener listener) {
		super.removeChangeListener(listener);
		if (this.getChangeListeners().length == 0) {
			this.numberHolder.removePropertyChangeListener(PropertyValueModel.VALUE, this.numberChangeListener);
		}
	}


	// ********** queries **********

	protected Number getDefaultValue() {
		return this.defaultValue;
	}

	/**
	 * Convert to a non-null value.
	 */
	protected Object spinnerValueOf(Object value) {
		return (value == null) ? this.getDefaultValue() : value;
	}


	// ********** behavior **********

	/**
	 * Set the spinner value if it has changed.
	 */
	void synchronize(Object value) {
		Object newValue = this.spinnerValueOf(value);
		// check to see whether the date has already been synchronized
		// (via #setValue())
		if ( ! this.getValue().equals(newValue)) {
			this.setValue(newValue);
		}
	}


	// ********** standard methods **********

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.numberHolder);
	}

}

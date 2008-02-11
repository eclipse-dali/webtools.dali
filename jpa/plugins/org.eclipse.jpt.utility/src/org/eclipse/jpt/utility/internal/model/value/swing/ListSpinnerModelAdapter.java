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

import java.util.Arrays;
import java.util.List;

import javax.swing.SpinnerListModel;
import javax.swing.event.ChangeListener;

import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.model.event.PropertyChangeEvent;
import org.eclipse.jpt.utility.internal.model.listener.PropertyChangeListener;
import org.eclipse.jpt.utility.internal.model.listener.awt.AWTPropertyChangeListenerWrapper;
import org.eclipse.jpt.utility.internal.model.value.WritablePropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;

/**
 * This javax.swing.SpinnerListModel can be used to keep a ChangeListener
 * (e.g. a JSpinner) in synch with a PropertyValueModel that holds a value
 * in the list.
 * 
 * This class must be a sub-class of SpinnerListModel because of some
 * crappy jdk code....  ~bjv
 * @see javax.swing.JSpinner#createEditor(javax.swing.SpinnerModel)
 * 
 * NB: This model should only be used for values that have a reasonably
 * inexpensive #equals() implementation.
 * @see #synchronize(Object)
 * 
 * If this class needs to be modified, it would behoove us to review the
 * other, similar classes:
 * @see DateSpinnerModelAdapter
 * @see NumberSpinnerModelAdapter
 */
public class ListSpinnerModelAdapter
	extends SpinnerListModel
{

	/**
	 * The default spinner value; used when the underlying model value is null.
	 * The default is the first item on the list.
	 */
	private final Object defaultValue;

	/** A value model on the underlying value. */
	private final WritablePropertyValueModel<Object> valueHolder;

	/** A listener that allows us to synchronize with changes made to the underlying value. */
	private final PropertyChangeListener valueChangeListener;


	// ********** constructors **********

	/**
	 * Constructor - the value holder is required.
	 * Use the model value itself as the default spinner value.
	 */
	public ListSpinnerModelAdapter(WritablePropertyValueModel<Object> valueHolder) {
		this(valueHolder, valueHolder.value());
	}

	/**
	 * Constructor - the value holder is required.
	 */
	public ListSpinnerModelAdapter(WritablePropertyValueModel<Object> valueHolder, Object defaultValue) {
		this(valueHolder, new Object[] {defaultValue}, defaultValue);
	}

	/**
	 * Constructor - the value holder is required.
	 * Use the first item in the list of values as the default spinner value.
	 */
	public ListSpinnerModelAdapter(WritablePropertyValueModel<Object> valueHolder, Object[] values) {
		this(valueHolder, values, values[0]);
	}

	/**
	 * Constructor - the value holder is required.
	 */
	public ListSpinnerModelAdapter(WritablePropertyValueModel<Object> valueHolder, Object[] values, Object defaultValue) {
		this(valueHolder, Arrays.asList(values), defaultValue);
	}

	/**
	 * Constructor - the value holder is required.
	 * Use the first item in the list of values as the default spinner value.
	 */
	public ListSpinnerModelAdapter(WritablePropertyValueModel<Object> valueHolder, List<Object> values) {
		this(valueHolder, values, values.get(0));
	}

	/**
	 * Constructor - the value holder is required.
	 */
	public ListSpinnerModelAdapter(WritablePropertyValueModel<Object> valueHolder, List<Object> values, Object defaultValue) {
		super(values);
		this.valueHolder = valueHolder;
		this.valueChangeListener = this.buildValueChangeListener();
		// postpone listening to the underlying value
		// until we have listeners ourselves...
		this.defaultValue = defaultValue;
	}


	// ********** initialization **********

	protected PropertyChangeListener buildValueChangeListener() {
		return new AWTPropertyChangeListenerWrapper(this.buildValueChangeListener_());
	}

	protected PropertyChangeListener buildValueChangeListener_() {
		return new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent event) {
				ListSpinnerModelAdapter.this.synchronize(event.newValue());
			}
			@Override
			public String toString() {
				return "value listener";
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
			super.setValue(this.spinnerValueOf(this.valueHolder.value()));
		}
		return super.getValue();
	}

	/**
	 * Extend to update the underlying value directly.
	 * The resulting event will be ignored: @see #synchronize(Object).
	 */
    @Override
	public void setValue(Object value) {
		super.setValue(value);
		this.valueHolder.setValue(value);
	}

	/**
	 * Extend to start listening to the underlying value if necessary.
	 */
    @Override
	public void addChangeListener(ChangeListener listener) {
		if (this.getChangeListeners().length == 0) {
			this.valueHolder.addPropertyChangeListener(PropertyValueModel.VALUE, this.valueChangeListener);
			this.synchronize(this.valueHolder.value());
		}
		super.addChangeListener(listener);
	}

	/**
	 * Extend to stop listening to the underlying value if appropriate.
	 */
    @Override
	public void removeChangeListener(ChangeListener listener) {
		super.removeChangeListener(listener);
		if (this.getChangeListeners().length == 0) {
			this.valueHolder.removePropertyChangeListener(PropertyValueModel.VALUE, this.valueChangeListener);
		}
	}


	// ********** queries **********

	protected Object getDefaultValue() {
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
		// check to see whether the spinner value has already been synchronized
		// (via #setValue())
		if ( ! this.getValue().equals(newValue)) {
			this.setValue(newValue);
		}
	}


	// ********** standard methods **********

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.valueHolder);
	}

}

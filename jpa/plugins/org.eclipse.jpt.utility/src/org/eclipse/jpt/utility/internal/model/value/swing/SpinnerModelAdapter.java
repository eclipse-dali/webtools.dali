/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal.model.value.swing;

import javax.swing.AbstractSpinnerModel;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.model.event.PropertyChangeEvent;
import org.eclipse.jpt.utility.internal.model.listener.PropertyChangeListener;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.ValueModel;

/**
 * This javax.swing.SpinnerModel can be used to keep a ChangeListener
 * (e.g. a JSpinner) in synch with a PropertyValueModel that holds a value.
 * 
 * Note: it is likely you want to use one of the following classes instead of
 * this one:
 *     DateSpinnerModelAdapter
 *     NumberSpinnerModelAdapter
 *     ListSpinnerModelAdapter
 * 
 * NB: This model should only be used for values that have a fairly
 * inexpensive #equals() implementation.
 * @see #synchronizeDelegate(Object)
 */
public class SpinnerModelAdapter extends AbstractSpinnerModel {

	/** The delegate spinner model whose behavior we "enhance". */
	protected SpinnerModel delegate;

	/** A listener that allows us to forward any changes made to the delegate spinner model. */
	protected ChangeListener delegateListener;

	/** A value model on the underlying value. */
	protected PropertyValueModel valueHolder;

	/** A listener that allows us to synchronize with changes made to the underlying value. */
	protected PropertyChangeListener valueListener;


	// ********** constructors **********

	/**
	 * Default constructor - initialize stuff.
	 */
	private SpinnerModelAdapter() {
		super();
		this.initialize();
	}

	/**
	 * Constructor - the value holder and delegate are required.
	 */
	public SpinnerModelAdapter(PropertyValueModel valueHolder, SpinnerModel delegate) {
		this();
		if (valueHolder == null || delegate == null) {
			throw new NullPointerException();
		}
		this.valueHolder = valueHolder;
		this.delegate = delegate;
		// postpone listening to the underlying value
		// until we have listeners ourselves...
	}

	/**
	 * Constructor - the value holder is required.
	 * This will wrap a simple number spinner model.
	 */
	public SpinnerModelAdapter(PropertyValueModel valueHolder) {
		this(valueHolder, new SpinnerNumberModel());
	}


	// ********** initialization **********

	protected void initialize() {
		this.valueListener = this.buildValueListener();
		this.delegateListener = this.buildDelegateListener();
	}

	protected PropertyChangeListener buildValueListener() {
		return new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent e) {
				SpinnerModelAdapter.this.valueChanged(e);
			}
			@Override
			public String toString() {
				return "value listener";
			}
		};
	}

	/**
	 * expand access a bit for inner class
	 */
	@Override
	protected void fireStateChanged() {
		super.fireStateChanged();
	}

	protected ChangeListener buildDelegateListener() {
		return new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				// forward the event, with this as the source
				SpinnerModelAdapter.this.fireStateChanged();
			}
			@Override
			public String toString() {
				return "delegate listener";
			}
		};
	}


	// ********** SpinnerModel implementation **********

	public Object getValue() {
		return this.delegate.getValue();
	}

	/**
	 * Extend to update the underlying value directly.
	 * The resulting event will be ignored: @see #synchronizeDelegate(Object).
	 */
	public void setValue(Object value) {
		this.delegate.setValue(value);
		this.valueHolder.setValue(value);
	}

	public Object getNextValue() {
		return this.delegate.getNextValue();
	}

	public Object getPreviousValue() {
		return this.delegate.getPreviousValue();
	}

	/**
	 * Extend to start listening to the underlying value if necessary.
	 */
    @Override
	public void addChangeListener(ChangeListener listener) {
		if (this.listenerList.getListenerCount(ChangeListener.class) == 0) {
			this.delegate.addChangeListener(this.delegateListener);
			this.engageValueHolder();
		}
		super.addChangeListener(listener);
	}

	/**
	 * Extend to stop listening to the underlying value if appropriate.
	 */
    @Override
	public void removeChangeListener(ChangeListener listener) {
		super.removeChangeListener(listener);
		if (this.listenerList.getListenerCount(ChangeListener.class) == 0) {
			this.disengageValueHolder();
			this.delegate.removeChangeListener(this.delegateListener);
		}
	}


	// ********** behavior **********

	/**
	 * A third party has modified the underlying value.
	 * Synchronize the delegate model accordingly.
	 */
	protected void valueChanged(PropertyChangeEvent e) {
		this.synchronizeDelegate(e.newValue());
	}

	/**
	 * Set the delegate's value if it has changed.
	 */
	protected void synchronizeDelegate(Object value) {
		// check to see whether the delegate has already been synchronized
		// (via #setValue())
		if ( ! this.delegate.getValue().equals(value)) {
			this.delegate.setValue(value);
		}
	}

	protected void engageValueHolder() {
		this.valueHolder.addPropertyChangeListener(ValueModel.VALUE, this.valueListener);
		this.synchronizeDelegate(this.valueHolder.getValue());
	}

	protected void disengageValueHolder() {
		this.valueHolder.removePropertyChangeListener(ValueModel.VALUE, this.valueListener);
	}


	// ********** standard methods **********

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.valueHolder);
	}

}

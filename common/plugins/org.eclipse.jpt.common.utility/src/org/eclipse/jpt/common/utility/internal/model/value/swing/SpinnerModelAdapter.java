/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.model.value.swing;

import javax.swing.AbstractSpinnerModel;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.model.listener.awt.AWTPropertyChangeListenerWrapper;
import org.eclipse.jpt.common.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;

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
public class SpinnerModelAdapter
	extends AbstractSpinnerModel
{
	/** The delegate spinner model whose behavior we "enhance". */
	protected final SpinnerModel delegate;

	/** A listener that allows us to forward any changes made to the delegate spinner model. */
	protected final ChangeListener delegateListener;

	/** A value model on the underlying value. */
	protected final ModifiablePropertyValueModel<Object> valueHolder;

	/** A listener that allows us to synchronize with changes made to the underlying value. */
	protected final PropertyChangeListener valueListener;


	// ********** constructors **********

	/**
	 * Constructor - the value holder and delegate are required.
	 */
	public SpinnerModelAdapter(ModifiablePropertyValueModel<Object> valueHolder, SpinnerModel delegate) {
		super();
		if (valueHolder == null || delegate == null) {
			throw new NullPointerException();
		}
		this.valueHolder = valueHolder;
		this.delegate = delegate;
		// postpone listening to the underlying value
		// until we have listeners ourselves...
		this.valueListener = this.buildValueListener();
		this.delegateListener = this.buildDelegateListener();
	}

	/**
	 * Constructor - the value holder is required.
	 * This will wrap a simple number spinner model.
	 */
	public SpinnerModelAdapter(ModifiablePropertyValueModel<Object> valueHolder) {
		this(valueHolder, new SpinnerNumberModel());
	}


	// ********** initialization **********

	protected PropertyChangeListener buildValueListener() {
		return new AWTPropertyChangeListenerWrapper(this.buildValueListener_());
	}

	protected PropertyChangeListener buildValueListener_() {
		return new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent event) {
				SpinnerModelAdapter.this.valueChanged(event);
			}
			@Override
			public String toString() {
				return "value listener"; //$NON-NLS-1$
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
			public void stateChanged(ChangeEvent event) {
				// forward the event, with this as the source
				SpinnerModelAdapter.this.fireStateChanged();
			}
			@Override
			public String toString() {
				return "delegate listener"; //$NON-NLS-1$
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
	protected void valueChanged(PropertyChangeEvent event) {
		this.synchronizeDelegate(event.getNewValue());
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
		this.valueHolder.addPropertyChangeListener(PropertyValueModel.VALUE, this.valueListener);
		this.synchronizeDelegate(this.valueHolder.getValue());
	}

	protected void disengageValueHolder() {
		this.valueHolder.removePropertyChangeListener(PropertyValueModel.VALUE, this.valueListener);
	}


	// ********** standard methods **********

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.valueHolder);
	}

}

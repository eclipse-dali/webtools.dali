/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal.model.value;

import org.eclipse.jpt.utility.internal.model.ChangeSupport;
import org.eclipse.jpt.utility.internal.model.event.PropertyChangeEvent;
import org.eclipse.jpt.utility.internal.model.listener.StateChangeListener;

/**
 * Abstract model that provides behavior for wrapping a property
 * value model and listening for changes to aspects of the *value* contained
 * by the property value model. Changes to the actual value are also monitored.
 * 
 * This is useful if you have a value that may change, but whose aspects can also
 * change in a fashion that might be of interest to the client.
 * 
 * NB: Clients will need to listen for two different change notifications: a property
 * change event will be be fired when the value changes; a state change event
 * will be fired when an aspect of the value changes.
 * 
 * Subclasses need to override two methods:
 * 
 * #engageValue_()
 *     begin listening to the appropriate aspect of the value and call
 *     #valueAspectChanged(Object) whenever the aspect changes
 * 
 * #disengageValue_()
 *     stop listening to the appropriate aspect of the value
 */
public abstract class ValueAspectAdapter<T>
	extends PropertyValueModelWrapper<T>
	implements WritablePropertyValueModel<T>
{
	/** Cache the value so we can disengage. */
	protected T value;


	// ********** constructors/initialization **********

	/**
	 * Constructor - the value holder is required.
	 */
	protected ValueAspectAdapter(WritablePropertyValueModel<T> valueHolder) {
		super(valueHolder);
	}

	@Override
	protected void initialize() {
		super.initialize();
		this.value = null;
	}

	/**
	 * Override to allow both property value model change and state change
	 * listeners.
	 */
	@Override
	protected ChangeSupport buildChangeSupport() {
		return new ChangeSupport(this);
	}


	// ********** PropertyValueModel implementation **********

	public T value() {
		return this.value;
	}


	// ********** WritablePropertyValueModel implementation **********

	public void setValue(T value) {
		this.valueHolder().setValue(value);
	}


	// ********** PropertyValueModelWrapper implementation **********

	@Override
	protected void valueChanged(PropertyChangeEvent event) {
		this.disengageValue();
		this.engageValue();
		this.firePropertyChanged(event.cloneWithSource(this));
	}


	// ********** extend change support **********

	@Override
	public synchronized void addStateChangeListener(StateChangeListener listener) {
		if (this.hasNoStateChangeListeners()) {
			this.engageValue();
		}
		super.addStateChangeListener(listener);
	}

	@Override
	public synchronized void removeStateChangeListener(StateChangeListener listener) {
		super.removeStateChangeListener(listener);
		if (this.hasNoStateChangeListeners()) {
			this.disengageValue();
		}
	}


	// ********** behavior **********

	/**
	 * Start listening to the current value.
	 */
	protected void engageValue() {
		this.value = this.valueHolder.value();
		if (this.value != null) {
			this.engageValue_();
		}
	}

	/**
	 * Start listening to the current value.
	 * At this point we can be sure that the value is not null.
	 */
	protected abstract void engageValue_();

	/**
	 * Stop listening to the current value.
	 */
	protected void disengageValue() {
		if (this.value != null) {
			this.disengageValue_();
			this.value = null;
		}
	}

	/**
	 * Stop listening to the current value.
	 * At this point we can be sure that the value is not null.
	 */
	protected abstract void disengageValue_();

	/**
	 * Subclasses should call this method whenever the value's aspect changes.
	 */
	protected void valueAspectChanged() {
		this.fireStateChanged();
	}

	/**
	 * Our constructors accept only a WritablePropertyValueModel<T1>.
	 */
	@SuppressWarnings("unchecked")
	protected WritablePropertyValueModel<T> valueHolder() {
		return (WritablePropertyValueModel<T>) this.valueHolder;
	}

}

/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal.model.value;

import org.eclipse.jpt.utility.internal.model.ChangeSupport;
import org.eclipse.jpt.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.utility.model.listener.StateChangeListener;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;

/**
 * Abstract class that provides support for wrapping a {@link WritablePropertyValueModel}
 * and listening for changes to <em>aspects</em> of the <em>value</em> contained
 * by the {@link WritablePropertyValueModel}. Changes to the {@link WritablePropertyValueModel}'s
 * value are also monitored.
 * <p>
 * This is useful if you have a value that may change, but whose aspects can also
 * change in a fashion that might be of interest to the client.
 * <p>
 * <strong>NB:</strong> Clients will need to listen for two different change notifications:
 * a property change event will be be fired when the <em>value</em> changes;
 * a state change event will be fired when an <em>aspect</em> of the value changes.
 * <p>
 * Subclasses need to override two methods:<ul>
 * <li>{@link #engageValue_()}<p>
 *     begin listening to the appropriate aspect of the value and call
 *     {@link #valueAspectChanged()} whenever the aspect changes
 *     (this will fire a state change event)
 * <li>{@link #disengageValue_()}<p>
 *     stop listening to the appropriate aspect of the value
 * </ul>
 */
public abstract class ValueAspectAdapter<V>
	extends PropertyValueModelWrapper<V>
	implements WritablePropertyValueModel<V>
{
	/** Cache the value so we can disengage. Null until we have a listener*/
	protected V value;


	// ********** constructors/initialization **********

	/**
	 * Constructor - the value holder is required.
	 */
	protected ValueAspectAdapter(WritablePropertyValueModel<V> valueHolder) {
		super(valueHolder);
		this.value = null;
	}

	/**
	 * Override to allow both property and state change listeners.
	 */
	@Override
	protected ChangeSupport buildChangeSupport() {
		return new ChangeSupport(this);
	}


	// ********** PropertyValueModel implementation **********

	public V getValue() {
		return this.value;
	}


	// ********** WritablePropertyValueModel implementation **********

	public void setValue(V value) {
		this.getValueHolder().setValue(value);
	}


	// ********** PropertyValueModelWrapper implementation **********

	@Override
	protected void valueChanged(PropertyChangeEvent event) {
		this.disengageValue();
		this.engageValue();
		this.firePropertyChanged(event.clone(this));
	}


	// ********** extend change support **********

	/**
	 * Extend to start listening to the underlying model if necessary.
	 */
	@Override
	public synchronized void addStateChangeListener(StateChangeListener listener) {
		if (this.hasNoListeners()) {
			this.engageModel();
		}
		super.addStateChangeListener(listener);
	}

	/**
	 * Extend to stop listening to the underlying model if necessary.
	 */
	@Override
	public synchronized void removeStateChangeListener(StateChangeListener listener) {
		super.removeStateChangeListener(listener);
		if (this.hasNoListeners()) {
			this.disengageModel();
		}
	}


	// ********** AbstractPropertyValueModel overrides **********

	/**
	 * Extend to check for state change listeners.
	 */
	@Override
	protected boolean hasListeners() {
		return this.hasAnyStateChangeListeners() || super.hasListeners();
	}


	// ********** PropertyValueModelWrapper overrides **********

	/**
	 * Extend to engage an aspect of the value model's value.
	 */
	@Override
	protected void engageModel() {
		super.engageModel();
		this.engageValue();
	}

	/**
	 * Extend to disengage an aspect of the value model's value.
	 */
	@Override
	protected void disengageModel() {
		this.disengageValue();
		super.disengageModel();
	}


	// ********** behavior **********

	/**
	 * Start listening to an aspect of the current value.
	 */
	protected void engageValue() {
		this.value = this.valueHolder.getValue();
		if (this.value != null) {
			this.engageValue_();
		}
	}

	/**
	 * Start listening to some aspect of the current value.
	 * At this point we can be sure the value is not null.
	 */
	protected abstract void engageValue_();

	/**
	 * Stop listening to an aspect of the current value.
	 */
	protected void disengageValue() {
		if (this.value != null) {
			this.disengageValue_();
			this.value = null;
		}
	}

	/**
	 * Stop listening to an aspect of the current value.
	 * At this point we can be sure the value is not null.
	 */
	protected abstract void disengageValue_();

	/**
	 * Subclasses should call this method whenever the value's aspect changes.
	 */
	protected void valueAspectChanged() {
		this.fireStateChanged();
	}

	/**
	 * Our constructor accepts only a {@link WritablePropertyValueModel}{@code<V>}.
	 */
	@SuppressWarnings("unchecked")
	protected WritablePropertyValueModel<V> getValueHolder() {
		return (WritablePropertyValueModel<V>) this.valueHolder;
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.getValue());
	}

}

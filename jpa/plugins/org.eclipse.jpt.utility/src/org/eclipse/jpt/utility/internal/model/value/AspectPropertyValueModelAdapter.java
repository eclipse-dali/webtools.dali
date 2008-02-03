/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal.model.value;

import org.eclipse.jpt.utility.internal.model.AbstractModel;
import org.eclipse.jpt.utility.internal.model.ChangeSupport;
import org.eclipse.jpt.utility.internal.model.SingleAspectChangeSupport;
import org.eclipse.jpt.utility.internal.model.listener.PropertyChangeListener;

/**
 * This abstract class provides the infrastructure needed to wrap
 * a value model, "lazily" listen to it, and convert
 * its change notifications into property value model change
 * notifications.
 * 
 * Subclasses must override:
 * - #buildValue()
 *     to return the current property value, as derived from the
 *     current model value
 * 
 */
public abstract class AspectPropertyValueModelAdapter<T>
	extends AbstractModel
	implements PropertyValueModel<T>
{
	/**
	 * Cache the current value so we can pass an "old value" when
	 * we fire a property change event.
	 * We need this because the value may be calculated and we may
	 * not able to derive the "old value" from the collection
	 * change event fired by the collection value model.
	 */
	protected T value;


	// ********** constructor/initialization **********

	protected AspectPropertyValueModelAdapter() {
		super();
		// our value is null when we are not listening to the collection holder
		this.value = null;
	}

	@Override
	protected ChangeSupport buildChangeSupport() {
		return new SingleAspectChangeSupport(this, PropertyChangeListener.class, VALUE);
	}


	// ********** PropertyValueModel implementation **********

	/**
	 * Return the cached value.
	 */
	public T value() {
		return this.value;
	}


	// ********** extend change support **********

	/**
	 * Extend to start listening to the wrapped collection if necessary.
	 */
	@Override
	public synchronized void addPropertyChangeListener(PropertyChangeListener listener) {
		if (this.hasNoListeners()) {
			this.engageModel();
		}
		super.addPropertyChangeListener(listener);
	}
	
	/**
	 * Extend to start listening to the wrapped collection if necessary.
	 */
	@Override
	public synchronized void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		if (propertyName == VALUE && this.hasNoListeners()) {
			this.engageModel();
		}
		super.addPropertyChangeListener(propertyName, listener);
	}
	
	/**
	 * Extend to stop listening to the wrapped collection if necessary.
	 */
	@Override
	public synchronized void removePropertyChangeListener(PropertyChangeListener listener) {
		super.removePropertyChangeListener(listener);
		if (this.hasNoListeners()) {
			this.disengageModel();
		}
	}
	
	/**
	 * Extend to stop listening to the wrapped collection if necessary.
	 */
	@Override
	public synchronized void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		super.removePropertyChangeListener(propertyName, listener);
		if (propertyName == VALUE && this.hasNoListeners()) {
			this.disengageModel();
		}
	}


	// ********** queries **********

	/**
	 * Return whether there are any listeners for the aspect.
	 */
	protected boolean hasListeners() {
		return this.hasAnyPropertyChangeListeners(VALUE);
	}

	/**
	 * Return whether there are any listeners for the aspect.
	 */
	protected boolean hasNoListeners() {
		return ! this.hasListeners();
	}


	// ********** behavior **********

	/**
	 * Start listening to the model and build the value.
	 */
	protected void engageModel() {
		this.engageModel_();
		// synch our value *after* we start listening to the collection,
		// since the collection's value might change when a listener is added
		this.value = this.buildValue();
	}

	/**
	 * Start listening to the model.
	 */
	protected abstract void engageModel_();

	/**
	 * Build and return the current value, as derived from the
	 * current state of the wrapped model.
	 */
	protected abstract T buildValue();

	/**
	 * Stop listening to the model and clear the value.
	 */
	protected void disengageModel() {
		this.disengageModel_();
		// clear out our value when we are not listening to the collection
		this.value = null;
	}

	/**
	 * Stop listening to the model.
	 */
	protected abstract void disengageModel_();

	/**
	 * The wrapped model changed in some fashion.
	 * Recalculate the value and notify any listeners.
	 */
	protected void propertyChanged() {
		Object old = this.value;
		this.value = this.buildValue();
		this.firePropertyChanged(VALUE, old, this.value);
	}

}

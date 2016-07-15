/*******************************************************************************
 * Copyright (c) 2008, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.model.value;

import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.model.AbstractModel;
import org.eclipse.jpt.common.utility.internal.model.ChangeSupport;
import org.eclipse.jpt.common.utility.internal.model.SingleAspectChangeSupport;
import org.eclipse.jpt.common.utility.model.listener.ChangeListener;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;

/**
 * This class provides the infrastructure needed to wrap
 * a model, "lazily" listen to it, and convert
 * its change notifications into <em>property</em> value model change
 * notifications.
 * 
 * @param <V> the type of the model's derived value
 */
public abstract class BasePluggablePropertyValueModel<V, A extends BasePluggablePropertyValueModel.Adapter<V>>
	extends AbstractModel
	implements PropertyValueModel<V>
{
	/**
	 * Adapter that listens to some model and
	 * calls back whenever that model changes in a way that
	 * affects this model's value.
	 */
	protected final A adapter;

	/**
	 * Cache the current value so we can pass an "old value" when
	 * we fire a property change event.
	 * We need this because the value may be calculated and we may
	 * not able to derive the "old value" from any fired events.
	 */
	protected volatile V value;


	// ********** constructor/initialization **********

	protected BasePluggablePropertyValueModel(Adapter.Factory<V, A> adapterFactory) {
		super();
		if (adapterFactory == null) {
			throw new NullPointerException();
		}
		// a bit of instance leakage...
		this.adapter = adapterFactory.buildAdapter(new AdapterListener());
		// our value is null when we are not listening to the model
		this.value = null;
	}

	@Override
	protected ChangeSupport buildChangeSupport() {
		return new SingleAspectChangeSupport(this, PropertyChangeListener.class, PropertyValueModel.VALUE);
	}


	// ********** PropertyValueModel implementation **********

	/**
	 * Return the cached value.
	 */
	public V getValue() {
		return this.value;
	}


	// ********** listeners **********

	/**
	 * Extend to start listening to the underlying model if necessary.
	 */
	@Override
	public synchronized void addChangeListener(ChangeListener listener) {
		if (this.hasNoListeners()) {
			this.engageModel();
		}
		super.addChangeListener(listener);
	}

	/**
	 * Extend to stop listening to the underlying model if necessary.
	 */
	@Override
	public synchronized void removeChangeListener(ChangeListener listener) {
		super.removeChangeListener(listener);
		if (this.hasNoListeners()) {
			this.disengageModel();
		}
	}

	/**
	 * Extend to start listening to the underlying model if necessary.
	 */
	@Override
	public synchronized void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		if (propertyName.equals(PropertyValueModel.VALUE) && this.hasNoListeners()) {
			this.engageModel();
		}
		super.addPropertyChangeListener(propertyName, listener);
	}

	/**
	 * Extend to stop listening to the underlying model if necessary.
	 */
	@Override
	public synchronized void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		super.removePropertyChangeListener(propertyName, listener);
		if (propertyName.equals(PropertyValueModel.VALUE) && this.hasNoListeners()) {
			this.disengageModel();
		}
	}

	/**
	 * Return whether the model has no property value listeners.
	 */
	public boolean hasNoListeners() {
		return ! this.hasListeners();
	}

	/**
	 * Return whether the model has any property value listeners.
	 */
	public boolean hasListeners() {
		return this.hasAnyPropertyChangeListeners(PropertyValueModel.VALUE);
	}

	/**
	 * Start listening to the underlying model and build the value.
	 */
	protected void engageModel() {
		// sync our value *after* we start listening to the model,
		// since the model's value might change when a listener is added
		this.value = this.adapter.engageModel();
	}

	/**
	 * Clear the value and stop listening to the underlying model.
	 */
	private void disengageModel() {
		// clear out our value when we are not listening to the model
		this.value = null;
		this.adapter.disengageModel();
	}


	// ********** Misc **********

	/**
	 * The underlying model changed in some fashion.
	 * Notify our listeners.
	 */
	/* CU private */ void valueChanged(V newValue) {
		Object old = this.value;
		this.firePropertyChanged(VALUE, old, this.value = newValue);
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.value);
	}


	// ********** Adapter Listener **********

	/**
	 * Simple callback. Allows us to keep the callback method internal.
	 */
	/* CU private */ class AdapterListener
		implements Adapter.Listener<V>
	{
		public void valueChanged(V newValue) {
			BasePluggablePropertyValueModel.this.valueChanged(newValue);
		}
		@Override
		public String toString() {
			return ObjectTools.toString(this);
		}
	}


	// ********** Adapter interfaces **********

	/**
	 * Minimal methods necessary to adapt some model to {@link PropertyValueModel}.
	 * This adapter is expected to listen to its adapted model and notify the
	 * listener passed to it via its {@link Adapter.Factory factory}
	 * about any model changes.
	 */
	public interface Adapter<AV> {
		/**
		 * Start listening to the adapted model
		 * and return its current value.
		 */
		AV engageModel();

		/**
		 * Stop listening to the adapted model.
		 */
		void disengageModel();

		/**
		 * Callback interface.
		 */
		interface Listener<ALV> {
			/**
			 * Callback to notify listener that the adapted model has changed.
			 */
			void valueChanged(ALV newValue);
		}

		/**
		 * Adapter factory interface.
		 * This factory allows both the pluggable property value model and its
		 * adapter to have circular <em>final</em> references to each other.
		 */
		interface Factory<AFV, A extends Adapter<AFV>> {
			/**
			 * Create an adapter with the specified listener.
			 */
			A buildAdapter(Listener<AFV> listener);
		}
	}
}

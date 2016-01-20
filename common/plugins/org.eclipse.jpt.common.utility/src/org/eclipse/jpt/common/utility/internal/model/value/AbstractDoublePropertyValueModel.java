/*******************************************************************************
 * Copyright (c) 2012, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.model.value;

import org.eclipse.jpt.common.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeAdapter;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;

/**
 * This [<em>outer</em>] property value model wraps another [<em>middle</em>]
 * property value model that wraps yet another [<em>inner</em>] property value model
 * and treats the <em>inner</em> model's value as the <em>outer</em>'s value.
 * Any change events fired by the <em>inner</em> model are simply forwarded by
 * the <em>outer</em> model as its own.
 * Similarly, changing the <em>middle</em> model's <em>inner</em> model
 * can also trigger a change event
 * (see {@link #wrappedValueChanged(PropertyValueModel)}).
 * <p>
 * <ul>
 * <li>Double (<em>outer</em>) property value model - a client can listen to
 *     this model and receive the same change notification whether the
 *     <em>middle</em> model's value changes or the <em>inner</em> model's
 *     value changes;
 *     much like an {@link PropertyAspectAdapter aspect adapter} whose subject
 *     model is <em>not</em> another property value model
 *     <ul>
 *     <li>Wrapped (<em>middle</em>) property value model - this model is built
 *         and maintained by the server that also builds the <em>inner</em> model
 *         (i.e. the server will monitor some other model that determines when
 *         the <em>inner</em> model is changed)
 *         <ul>
 *         <li>Original (<em>inner</em>) property value model - this model is
 *             the "original" model that contains the value of interest
 *         </ul>
 *     </ul>
 * </ul>
 * <p>
 * This wrapper is useful when a change in the <em>middle</em> model's value is
 * signaled by a non-value event and a third-party would like to change it.
 * 
 * @param <V> the type of the both the <em>inner</em> and <em>outer</em>
 * models' values
 * @param <VMV> the type of the <em>middle</em> model's value (i.e. the type
 * of the <em>inner</em> model itself)
 */
public abstract class AbstractDoublePropertyValueModel<V, VMV extends PropertyValueModel<? extends V>>
	extends PropertyValueModelWrapper<VMV>
	implements PropertyValueModel<V>
{
	/**
	 * The optionally present <em>inner</em> model; held by the <em>middle</em>
	 * model {@link #valueModel}. This may be <code>null</code>.
	 */
	protected volatile VMV valueModelValue;

	/**
	 * A listener that allows us to sync with changes to the
	 * {@link #valueModelValue <em>inner</em> model}.
	 */
	protected final PropertyChangeListener valueModelValueListener;

	/**
	 * The <em>inner</em> model's value.
	 */
	private volatile V value;


	/**
	 * Construct a double property value model for the specified
	 * <em>middle</em> property value model.
	 */
	protected AbstractDoublePropertyValueModel(PropertyValueModel<? extends VMV> valueModel) {
		super(valueModel);
		this.valueModelValueListener = this.buildValueModelValueListener();
	}

	protected PropertyChangeListener buildValueModelValueListener() {
		return new ValueModelValueListener();
	}

	/* CU private */ class ValueModelValueListener
		extends PropertyChangeAdapter
	{
		@Override
		public void propertyChanged(PropertyChangeEvent event) {
			AbstractDoublePropertyValueModel.this.wrappedValueModelValueChanged(event);
		}
	}


	// ********** PropertyValueModel implementation **********

	public V getValue() {
		return this.value;
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.value);
	}


	// ********** middle model **********

	/**
	 * The <em>middle</em> model has changed.
	 * Move our <em>inner</em> model listener and
	 * notify listeners the <em>outer</em> model's value has changed.
	 */
	@Override
	protected synchronized void wrappedValueChanged(VMV newValueModelValue) {
		V oldValue = null;
		V newValue = null;
    	synchronized (this) {
			if (this.hasListeners()) { // it's possible listeners were removed before event gets here...
				oldValue = this.value;
				this.disengageValueModelValue();
				this.valueModelValue = newValueModelValue;
				if (newValueModelValue != null) {
					newValueModelValue.addPropertyChangeListener(VALUE, this.valueModelValueListener);
					newValue = newValueModelValue.getValue();
					this.value = newValue;
				}
			}
    	}
		this.firePropertyChanged(VALUE, oldValue, newValue);
	}


	// ********** inner model **********

	/**
	 * The <em>inner</em> model has changed.
	 * Cache the new value and
	 * notify listeners the <em>outer</em> model's value has changed.
	 */
	@SuppressWarnings("unchecked")
    protected void wrappedValueModelValueChanged(PropertyChangeEvent event) {
		V oldValue = null;
		V newValue = (V) event.getNewValue();
    	synchronized (this) {
			oldValue = this.value;
			this.value = newValue;
    	}
		this.firePropertyChanged(VALUE, oldValue, newValue);
	}

	/**
	 * Begin listening to the <em>inner</em> model.
	 */
	@Override
	protected void engageModel() {
		super.engageModel();
		this.engageValueModelValue();
	}

	protected void engageValueModelValue() {
		this.valueModelValue = this.valueModel.getValue();
		if (this.valueModelValue != null) {
			this.valueModelValue.addPropertyChangeListener(VALUE, this.valueModelValueListener);
			this.value = this.valueModelValue.getValue();
		}
	}

	/**
	 * Stop listening to the <em>inner</em> model.
	 */
	@Override
	protected void disengageModel() {
		this.disengageValueModelValue();
		super.disengageModel();
	}

	protected void disengageValueModelValue() {
		if (this.valueModelValue != null) {
			this.value = null;
			this.valueModelValue.removePropertyChangeListener(VALUE, this.valueModelValueListener);
			this.valueModelValue = null;
		}
	}
}

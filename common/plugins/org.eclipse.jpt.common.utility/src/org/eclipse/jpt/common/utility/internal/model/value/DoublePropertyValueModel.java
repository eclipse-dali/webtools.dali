/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
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
 * This property value model <em>wrapper</em> wraps another property value model
 * and treats the <em>wrapped</em> model's value as the <em>wrapper</em>'s value.
 * Any change events fired by the <em>wrapped</em> model are simply forwarded by
 * the <em>wrapper</em> as its own.
 * Similarly, changing the <em>wrapper</em>'s <em>wrapped</em> model can also
 * trigger a change event
 * (see {@link #wrappedValueChanged(PropertyValueModel, PropertyValueModel)}).
 * That is, the <em>wrapped</em> model is held by yet another property value
 * model!
 * <p>
 * <ul>
 * <li>Double (<em>wrapper</em>) property value model - a client can listen to
 *     this model and receive the same change notification whether the
 *     <em>wrapped</em> model or the <em>wrapped</em> model's value changes;
 *     much like an {@link PropertyAspectAdapter aspect adapter} whose subject
 *     model is <em>not</em> another property value model
 *     <ul>
 *     <li><em>Wrapped</em> property value model - this model is built and
 *         maintained by the server that also builds the <em>wrapper</em> model
 *         (i.e. the server will monitor some other model that determines when
 *         the <em>wrapped</em> model is changed)
 *         <ul>
 *         <li>Original property value model - this model is the "original"
 *             model that contains the value of interest
 *         </ul>
 *     </ul>
 * </ul>
 * <p>
 * This wrapper is useful when a change in the <em>wrapped</em> model is
 * signaled by a non-value event and a third-party would like to change it.
 * 
 * @param <V> the type of the both the <em>wrapped</em> and <em>wrapper</em>
 * models' values
 */
public class DoublePropertyValueModel<V>
	extends PropertyValueModelWrapper<PropertyValueModel<? extends V>>
	implements PropertyValueModel<V>
{
	/**
	 * The optionally present wrapped value model value; held by
	 * {@link #valueModel}. This may be <code>null</code>.
	 */
	protected volatile PropertyValueModel<? extends V> valueModelValueModel;

	/**
	 * A listener that allows us to sync with changes to the wrapped value
	 * model model.
	 */
	protected final PropertyChangeListener valueModelValueListener;


	// ********** constructors/initialization **********

	/**
	 * Construct a double property value model for the specified
	 * wrapped property value model model.
	 */
	public DoublePropertyValueModel(PropertyValueModel<? extends PropertyValueModel<? extends V>> valueModel) {
		super(valueModel);
		this.valueModelValueListener = this.buildValueModelValueListener();
	}

	protected PropertyChangeListener buildValueModelValueListener() {
		return new ValueModelListener();
	}

	/* CU private */ class ValueModelListener
		extends PropertyChangeAdapter
	{
		@Override
		public void propertyChanged(PropertyChangeEvent event) {
			DoublePropertyValueModel.this.wrappedValueModelValueChanged(event);
		}
	}


	// ********** PropertyValueModel implementation **********

	public V getValue() {
		return (this.valueModelValueModel == null) ? null : this.valueModelValueModel.getValue();
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.getValue());
	}


	// ********** wrapped value model **********

	/**
	 * The value model has changed.
	 * Move our value model listener and
	 * notify listeners that the value has changed.
	 */
	@Override
	protected void wrappedValueChanged(PropertyValueModel<? extends V> oldValue, PropertyValueModel<? extends V> newValue) {
		if (this.hasListeners()) {
			V old = this.getValue();
			this.disengageValueModel();
			this.engageValueModel();
			this.firePropertyChanged(VALUE, old, this.getValue());
		}
	}


	// ********** wrapped value model value **********

	/**
	 * The value of the wrapped value model's value model has changed.
	 * Forward the event as our own.
	 */
    protected void wrappedValueModelValueChanged(PropertyChangeEvent event) {
		this.firePropertyChanged(event.clone(this));
	}

	/**
	 * Begin listening to the value model.
	 */
	@Override
	protected void engageModel() {
		super.engageModel();
		this.engageValueModel();
	}

	protected void engageValueModel() {
		this.valueModelValueModel = this.valueModel.getValue();
		if (this.valueModelValueModel != null) {
			this.valueModelValueModel.addPropertyChangeListener(VALUE, this.valueModelValueListener);
		}
	}

	/**
	 * Stop listening to the value model.
	 */
	@Override
	protected void disengageModel() {
		this.disengageValueModel();
		super.disengageModel();
	}

	protected void disengageValueModel() {
		if (this.valueModelValueModel != null) {
			this.valueModelValueModel.removePropertyChangeListener(VALUE, this.valueModelValueListener);
		}
		this.valueModelValueModel = null;
	}
}

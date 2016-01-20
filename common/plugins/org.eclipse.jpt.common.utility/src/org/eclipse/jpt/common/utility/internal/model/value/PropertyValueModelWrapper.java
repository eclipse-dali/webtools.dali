/*******************************************************************************
 * Copyright (c) 2007, 2016 Oracle. All rights reserved.
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
 * This abstract class provides the infrastructure needed to wrap
 * another property value model, "lazily" listen to it, and propagate
 * its change notifications. Subclasses must implement the appropriate
 * {@link PropertyValueModel}.
 * <p>
 * Subclasses must implement at least one of the following methods:<ul>
 * <li>{@link #wrappedValueChanged(PropertyChangeEvent)}<p>
 *     implement this method to propagate the appropriate change notification
 * <li>{@link #wrappedValueChanged(Object, Object) wrappedValueChanged(V, V)}<p>
 *     implement this method to propagate the appropriate change notification
 * <li>{@link #wrappedValueChanged(Object) wrappedValueChanged(V)}<p>
 *     implement this method to propagate the appropriate change notification
 * </ul>
 * 
 * @param <V> the type of the <em>wrapped</em> model's value
 */
public abstract class PropertyValueModelWrapper<V>
	extends AbstractPropertyValueModel
{
	/** The wrapped property value model. Never <code>null</code>. */
	protected final PropertyValueModel<? extends V> valueModel;

	/** A listener that allows us to sync with changes to the wrapped value model. */
	protected final PropertyChangeListener valueListener;


	// ********** constructors/initialization **********

	/**
	 * Construct a property value model with the specified wrapped
	 * property value model.
	 */
	protected PropertyValueModelWrapper(PropertyValueModel<? extends V> valueModel) {
		super();
		if (valueModel == null) {
			throw new NullPointerException();
		}
		this.valueModel = valueModel;
		this.valueListener = this.buildValueListener();
	}

	protected PropertyChangeListener buildValueListener() {
		return new ValueListener();
	}

	/* CU private */ class ValueListener
		extends PropertyChangeAdapter
	{
		@Override
		public void propertyChanged(PropertyChangeEvent event) {
			PropertyValueModelWrapper.this.wrappedValueChanged(event);
		}
	}


	// ********** listen to wrapped value model **********

	/**
	 * Begin listening to the value model.
	 */
	@Override
	protected void engageModel() {
		this.valueModel.addPropertyChangeListener(PropertyValueModel.VALUE, this.valueListener);
	}

	/**
	 * Stop listening to the value model.
	 */
	@Override
	protected void disengageModel() {
		this.valueModel.removePropertyChangeListener(PropertyValueModel.VALUE, this.valueListener);
	}


	// ********** property change support **********

	/**
	 * The value of the wrapped value model has changed;
	 * propagate the change notification appropriately.
	 * @see #wrappedValueChanged(Object, Object)
	 * @see #wrappedValueChanged(Object)
	 */
	@SuppressWarnings("unchecked")
	protected void wrappedValueChanged(PropertyChangeEvent event) {
		this.wrappedValueChanged((V) event.getOldValue(), (V) event.getNewValue());
	}

	/**
	 * The value of the wrapped value model has changed;
	 * propagate the change notification appropriately.
	 * @see #wrappedValueChanged(PropertyChangeEvent)
	 * @see #wrappedValueChanged(Object)
	 */
	protected void wrappedValueChanged(@SuppressWarnings("unused") V oldValue, V newValue) {
		this.wrappedValueChanged(newValue);
	}

	/**
	 * The value of the wrapped value model has changed;
	 * propagate the change notification appropriately.
	 * @see #wrappedValueChanged(PropertyChangeEvent)
	 * @see #wrappedValueChanged(Object, Object)
	 */
	protected void wrappedValueChanged(@SuppressWarnings("unused") V newValue) {
		throw new RuntimeException("This method was not overridden."); //$NON-NLS-1$
	}
}

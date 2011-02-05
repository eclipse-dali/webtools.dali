/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.model.value;

import org.eclipse.jpt.common.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;

/**
 * This abstract class provides the infrastructure needed to wrap
 * another property value model, "lazily" listen to it, and propagate
 * its change notifications. Subclasses must implement the appropriate
 * {@link PropertyValueModel}.
 * <p>
 * Subclasses must implement the following methods:<ul>
 * <li>{@link #valueChanged(PropertyChangeEvent)}<p>
 *     implement this method to propagate the appropriate change notification
 * </ul>
 */
public abstract class PropertyValueModelWrapper<V>
	extends AbstractPropertyValueModel
{
	/** The wrapped property value model. */
	protected final PropertyValueModel<? extends V> valueHolder;

	/** A listener that allows us to synch with changes to the wrapped value holder. */
	protected final PropertyChangeListener valueChangeListener;


	// ********** constructors/initialization **********

	/**
	 * Construct a property value model with the specified wrapped
	 * property value model. The value holder is required.
	 */
	protected PropertyValueModelWrapper(PropertyValueModel<? extends V> valueHolder) {
		super();
		if (valueHolder == null) {
			throw new NullPointerException();
		}
		this.valueHolder = valueHolder;
		this.valueChangeListener = this.buildValueChangeListener();
	}
	
	protected PropertyChangeListener buildValueChangeListener() {
		return new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent event) {
				PropertyValueModelWrapper.this.valueChanged(event);
			}
		    @Override
			public String toString() {
				return "value change listener"; //$NON-NLS-1$
			}
		};
	}
	

	// ********** behavior **********
	
	/**
	 * Begin listening to the value holder.
	 */
	@Override
	protected void engageModel() {
		this.valueHolder.addPropertyChangeListener(PropertyValueModel.VALUE, this.valueChangeListener);
	}
	
	/**
	 * Stop listening to the value holder.
	 */
	@Override
	protected void disengageModel() {
		this.valueHolder.removePropertyChangeListener(PropertyValueModel.VALUE, this.valueChangeListener);
	}
	

	// ********** property change support **********

	/**
	 * The value of the wrapped value holder has changed;
	 * propagate the change notification appropriately.
	 */
	protected abstract void valueChanged(PropertyChangeEvent event);

}

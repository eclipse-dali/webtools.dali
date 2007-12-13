/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal.model.value;

import java.util.Arrays;

import org.eclipse.jpt.utility.internal.model.Model;
import org.eclipse.jpt.utility.internal.model.event.PropertyChangeEvent;
import org.eclipse.jpt.utility.internal.model.listener.PropertyChangeListener;

/**
 * Extend ValueAspectPropertyValueModelAdapter to listen to one or more
 * properties of the value in the wrapped value model.
 */
public class ValuePropertyPropertyValueModelAdapter<T extends Model>
	extends ValueAspectPropertyValueModelAdapter<T>
{
	/** The names of the value's properties that we listen to. */
	protected final String[] propertyNames;

	/** Listener that listens to the value. */
	protected final PropertyChangeListener valuePropertyListener;


	// ********** constructors **********

	/**
	 * Construct an adapter for the specified value properties.
	 */
	public ValuePropertyPropertyValueModelAdapter(WritablePropertyValueModel<T> valueHolder, String... propertyNames) {
		super(valueHolder);
		this.propertyNames = propertyNames;
		this.valuePropertyListener = this.buildValuePropertyListener();
	}


	// ********** initialization **********

	protected PropertyChangeListener buildValuePropertyListener() {
		return new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent e) {
				ValuePropertyPropertyValueModelAdapter.this.valueAspectChanged();
			}
			@Override
			public String toString() {
				return "value property listener: " + Arrays.asList(ValuePropertyPropertyValueModelAdapter.this.propertyNames);
			}
		};
	}
	

	// ********** behavior **********

	@Override
	protected void engageValue_() {
		for (String propertyName : this.propertyNames) {
			this.value.addPropertyChangeListener(propertyName, this.valuePropertyListener);
		}
	}

	@Override
	protected void disengageValue_() {
		for (String propertyName : this.propertyNames) {
			this.value.removePropertyChangeListener(propertyName, this.valuePropertyListener);
		}
	}

}

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

import java.util.Arrays;
import org.eclipse.jpt.common.utility.model.Model;
import org.eclipse.jpt.common.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.common.utility.model.value.WritablePropertyValueModel;

/**
 * Extend {@link ValueAspectAdapter} to listen to one or more
 * properties of the value in the wrapped value model.
 */
public class ValuePropertyAdapter<V extends Model>
	extends ValueAspectAdapter<V>
{
	/** The names of the value's properties we listen to. */
	protected final String[] propertyNames;

	/** Listener that listens to the value. */
	protected final PropertyChangeListener valuePropertyListener;


	// ********** constructors **********

	/**
	 * Construct an adapter for the specified value properties.
	 */
	public ValuePropertyAdapter(WritablePropertyValueModel<V> valueHolder, String... propertyNames) {
		super(valueHolder);
		this.propertyNames = propertyNames;
		this.valuePropertyListener = this.buildValuePropertyListener();
	}


	// ********** initialization **********

	protected PropertyChangeListener buildValuePropertyListener() {
		return new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent event) {
				ValuePropertyAdapter.this.propertyChanged(event);
			}
			@Override
			public String toString() {
				return "value property listener: " + Arrays.asList(ValuePropertyAdapter.this.propertyNames); //$NON-NLS-1$
			}
		};
	}
	

	// ********** ValueAspectAdapter implementation **********

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


	// ********** change events **********

	protected void propertyChanged(@SuppressWarnings("unused") PropertyChangeEvent event) {
		this.valueAspectChanged();
	}

}

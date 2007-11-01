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
public class ValuePropertyPropertyValueModelAdapter
	extends ValueAspectPropertyValueModelAdapter
{
	/** The names of the value's properties that we listen to. */
	protected String[] propertyNames;

	/** Listener that listens to the value. */
	protected PropertyChangeListener valuePropertyListener;


	// ********** constructors **********

	/**
	 * Construct an adapter for the specified value property.
	 */
	public ValuePropertyPropertyValueModelAdapter(PropertyValueModel valueHolder, String propertyName) {
		this(valueHolder, new String[] {propertyName});
	}

	/**
	 * Construct an adapter for the specified value properties.
	 */
	public ValuePropertyPropertyValueModelAdapter(PropertyValueModel valueHolder, String propertyName1, String propertyName2) {
		this(valueHolder, new String[] {propertyName1, propertyName2});
	}

	/**
	 * Construct an adapter for the specified value properties.
	 */
	public ValuePropertyPropertyValueModelAdapter(PropertyValueModel valueHolder, String propertyName1, String propertyName2, String propertyName3) {
		this(valueHolder, new String[] {propertyName1, propertyName2, propertyName3});
	}

	/**
	 * Construct an adapter for the specified value properties.
	 */
	public ValuePropertyPropertyValueModelAdapter(PropertyValueModel valueHolder, String[] propertyNames) {
		super(valueHolder);
		this.propertyNames = propertyNames;
	}


	// ********** initialization **********

	@Override
	protected void initialize() {
		super.initialize();
		this.valuePropertyListener = this.buildValuePropertyListener();
	}

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
	protected void startListeningToValue() {
		Model v = (Model) this.value;
		for (int i = this.propertyNames.length; i-- > 0; ) {
			v.addPropertyChangeListener(this.propertyNames[i], this.valuePropertyListener);
		}
	}

	@Override
	protected void stopListeningToValue() {
		Model v = (Model) this.value;
		for (int i = this.propertyNames.length; i-- > 0; ) {
			v.removePropertyChangeListener(this.propertyNames[i], this.valuePropertyListener);
		}
	}


	// ********** item change support **********

}

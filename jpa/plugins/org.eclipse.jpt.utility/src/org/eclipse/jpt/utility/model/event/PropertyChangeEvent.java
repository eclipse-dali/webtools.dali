/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.model.event;

import org.eclipse.jpt.utility.model.Model;

/**
 * A "property change" event gets delivered whenever a model changes a "bound"
 * or "constrained" property. A <code>PropertyChangeEvent</code> is sent as an
 * argument to the {@link org.eclipse.jpt.utility.model.listener.PropertyChangeListener}.
 * A <code>PropertyChangeEvent</code> is accompanied by the old and new values
 * of the property.
 * <p>
 * Provisional API: This class is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public final class PropertyChangeEvent extends ChangeEvent {

	/** Name of the property that changed. */
	private final String propertyName;

	/** The property's old value, before the change. */
	private final Object oldValue;

	/** The property's new value, after the change. */
	private final Object newValue;

	private static final long serialVersionUID = 1L;


	// ********** constructors **********

	/**
	 * Construct a new property change event.
	 *
	 * @param source The object on which the event initially occurred.
	 * @param propertyName The programmatic name of the property that was changed.
	 * @param oldValue The old value of the property.
	 * @param newValue The new value of the property.
	 */
	public PropertyChangeEvent(Model source, String propertyName, Object oldValue, Object newValue) {
		super(source);
		if (propertyName == null) {
			throw new NullPointerException();
		}
		this.propertyName = propertyName;
		this.oldValue = oldValue;
		this.newValue = newValue;
	}


	// ********** standard state **********

	/**
	 * Return the programmatic name of the property that was changed.
	 */
	public String getPropertyName() {
		return this.propertyName;
	}

	/**
	 * Return the old value of the property.
	 */
	public Object getOldValue() {
		return this.oldValue;
	}

	/**
	 * Return the new value of the property.
	 */
	public Object getNewValue() {
		return this.newValue;
	}

	@Override
	protected void toString(StringBuilder sb) {
		sb.append(this.propertyName);
		sb.append(": "); //$NON-NLS-1$
		sb.append(this.oldValue);
		sb.append(" => "); //$NON-NLS-1$
		sb.append(this.newValue);
	}


	// ********** cloning **********

	public PropertyChangeEvent clone(Model newSource) {
		return new PropertyChangeEvent(newSource, this.propertyName, this.oldValue, this.newValue);
	}

	/**
	 * Return a copy of the event with the specified source and property name
	 * replacing the current source and property name.
	 */
	public PropertyChangeEvent clone(Model newSource, String newPropertyName) {
		return new PropertyChangeEvent(newSource, newPropertyName, this.oldValue, this.newValue);
	}

}

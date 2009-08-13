/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
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
import org.eclipse.jpt.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;

/**
 * Implementation of {@link WritablePropertyValueModel} that simply holds on to an
 * object and uses it as the value.
 */
public class SimplePropertyValueModel<T>
	extends AbstractModel
	implements WritablePropertyValueModel<T>
{
	/** The value. */
	protected T value;


	/**
	 * Construct a property value model for the specified value.
	 */
	public SimplePropertyValueModel(T value) {
		super();
		this.value = value;
	}

	/**
	 * Construct a property value model with a starting value of null.
	 */
	public SimplePropertyValueModel() {
		this(null);
	}

	@Override
	protected ChangeSupport buildChangeSupport() {
		return new SingleAspectChangeSupport(this, PropertyChangeListener.class, VALUE);
	}


	public T getValue() {
		return this.value;
	}

	public void setValue(T value) {
		T old = this.value;
		this.value = value;
		this.firePropertyChanged(VALUE, old, value);
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.value);
	}

}

/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.model.value;

import org.eclipse.jpt.common.utility.internal.model.AbstractModel;
import org.eclipse.jpt.common.utility.internal.model.ChangeSupport;
import org.eclipse.jpt.common.utility.internal.model.SingleAspectChangeSupport;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;

/**
 * Implementation of {@link ModifiablePropertyValueModel} that simply holds on to
 * an object, uses it as the value, and fires the appropriate event when the
 * value changes.
 * 
 * @param <V> the type of the model's value
 */
public class SimplePropertyValueModel<V>
	extends AbstractModel
	implements ModifiablePropertyValueModel<V>
{
	/** The value. */
	protected V value;


	/**
	 * Construct a property value model for the specified value.
	 */
	public SimplePropertyValueModel(V value) {
		super();
		this.value = value;
	}

	/**
	 * Construct a property value model with a value of <code>null</code>.
	 */
	public SimplePropertyValueModel() {
		this(null);
	}

	@Override
	protected ChangeSupport buildChangeSupport() {
		return new SingleAspectChangeSupport(this, PropertyChangeListener.class, VALUE);
	}


	public V getValue() {
		return this.value;
	}

	public void setValue(V value) {
		V old = this.value;
		this.value = value;
		this.firePropertyChanged(VALUE, old, value);
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.value);
	}
}

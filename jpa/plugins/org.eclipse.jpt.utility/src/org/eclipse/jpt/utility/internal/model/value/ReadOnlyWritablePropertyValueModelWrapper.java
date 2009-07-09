/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal.model.value;

import org.eclipse.jpt.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;

/**
 * A simple implementation of {@link WritablePropertyValueModel} that actually
 * ... <i>isn't</i> ... writable.  It can however be used in places that require a 
 * {@link WritablePropertyValueModel} and where the developer is sure that no 
 * attempt will be made to write to it.
 */
public class ReadOnlyWritablePropertyValueModelWrapper<T>
	extends PropertyValueModelWrapper<T>
	implements WritablePropertyValueModel<T>
{
	public ReadOnlyWritablePropertyValueModelWrapper(PropertyValueModel<? extends T> valueHolder) {
		super(valueHolder);
	}
	
	
	public T getValue() {
		return this.valueHolder.getValue();
	}
	
	public void setValue(T value) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	protected void valueChanged(PropertyChangeEvent event) {
		firePropertyChanged(event.clone(this));
	}
}

/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.model.value;

import org.eclipse.jpt.common.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;

/**
 * A simple implementation of {@link ModifiablePropertyValueModel} that actually
 * ... <em>isn't</em> ... modifiable.  It can however be used in places that require a 
 * {@link ModifiablePropertyValueModel} and where the developer is sure that no 
 * attempt will be made to write to it.
 */
public class ReadOnlyModifiablePropertyValueModelWrapper<T>
	extends PropertyValueModelWrapper<T>
	implements ModifiablePropertyValueModel<T>
{
	public ReadOnlyModifiablePropertyValueModelWrapper(PropertyValueModel<? extends T> valueModel) {
		super(valueModel);
	}


	public T getValue() {
		return this.valueModel.getValue();
	}

	public void setValue(T value) {
		throw new UnsupportedOperationException();
	}

	@Override
	protected void wrappedValueChanged(PropertyChangeEvent event) {
		this.firePropertyChanged(event.clone(this));
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.getValue());
	}
}

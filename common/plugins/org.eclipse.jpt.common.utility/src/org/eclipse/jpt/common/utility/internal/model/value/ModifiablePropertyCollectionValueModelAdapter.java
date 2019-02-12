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

import java.util.Iterator;

import org.eclipse.jpt.common.utility.model.value.ModifiableCollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;

/**
 * An adapter that allows us to make a {@link ModifiablePropertyValueModel} behave like
 * a single-element {@link ModifiableCollectionValueModel}, sorta.
 * <p>
 * If the property's value is <code>null</code>, an empty iterator is returned
 * (i.e. you can't have a collection with a <code>null</code> element).
 * Also, only a single-element collection can be written to the adapter.
 */
public class ModifiablePropertyCollectionValueModelAdapter<E>
	extends PropertyCollectionValueModelAdapter<E>
	implements ModifiableCollectionValueModel<E>
{
	// ********** constructor **********

	/**
	 * Convert the specified writable property value model to a writable
	 * collection value model.
	 */
	public ModifiablePropertyCollectionValueModelAdapter(ModifiablePropertyValueModel<E> valueHolder) {
		super(valueHolder);
	}


	// ********** WritableCollectionValueModel implementation **********

	public void setValues(Iterable<E> values) {
		Iterator<E> stream = values.iterator();
		if (stream.hasNext()) {
			E newValue = stream.next();
			if (stream.hasNext()) {
				throw new IllegalArgumentException("non-singleton collection: " + values); //$NON-NLS-1$
			}
			this.getValueHolder().setValue(newValue);
		} else {
			this.getValueHolder().setValue(null);
		}
	}

	// our constructor takes only writable property value models
	@SuppressWarnings("unchecked")
	protected ModifiablePropertyValueModel<E> getValueHolder() {
		return (ModifiablePropertyValueModel<E>) this.valueHolder;
	}
}

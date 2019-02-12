/*******************************************************************************
 * Copyright (c) 2013, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.reference;

import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.reference.ModifiableObjectReference;

/**
 * Implement some of the methods in {@link ModifiableObjectReference} that can
 * be defined in terms of the other methods.
 * Subclasses need only implement<ul>
 * <li>{@link #getValue()}
 * <li>{@link #setValue(Object)}
 * </ul>
 * 
 * @param <V> the type of the reference's value
 */
public abstract class AbstractModifiableObjectReference<V>
	extends AbstractObjectReference<V>
	implements ModifiableObjectReference<V>
{
	protected AbstractModifiableObjectReference() {
		super();
	}

	public V setNull() {
		return this.setValue(null);
	}

	public boolean commit(V newValue, V expectedValue) {
		if (ObjectTools.equals(this.getValue(), expectedValue)) {
			this.setValue(newValue);
			return true;
		}
		return false;
	}

	public V swap(ModifiableObjectReference<V> other) {
		if (other == this) {
			return this.getValue();
		}
		V otherValue = other.getValue();
		if (ObjectTools.equals(this.getValue(), otherValue)) {
			return this.getValue();
		}
		other.setValue(this.getValue());
		this.setValue(otherValue);
		return otherValue;
	}
}

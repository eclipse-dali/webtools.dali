/*******************************************************************************
 * Copyright (c) 2012, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.reference;

import org.eclipse.jpt.common.utility.reference.ModifiableBooleanReference;

/**
 * Convenience abstract class for modifiable boolean reference implementations.
 * Subclasses need only implement<ul>
 * <li>{@link #getValue()}
 * <li>{@link #setValue(boolean)}
 * </ul>
 */
public abstract class AbstractModifiableBooleanReference
	extends AbstractBooleanReference
	implements ModifiableBooleanReference
{
	protected AbstractModifiableBooleanReference() {
		super();
	}

	public boolean flip() {
		boolean v = ! this.getValue();
		this.setValue(v);
		return v;
	}

	public boolean and(boolean b) {
		boolean v = this.getValue() && b;
		this.setValue(v);
		return v;
	}

	public boolean or(boolean b) {
		boolean v = this.getValue() || b;
		this.setValue(v);
		return v;
	}

	public boolean xor(boolean b) {
		boolean v = this.getValue() ^ b;
		this.setValue(v);
		return v;
	}

	public boolean setNot(boolean value) {
		return this.setValue( ! value);
	}

	public boolean setTrue() {
		return this.setValue(true);
	}

	public boolean setFalse() {
		return this.setValue(false);
	}

	public boolean commit(boolean newValue, boolean expectedValue) {
		if (this.getValue() == expectedValue) {
			this.setValue(newValue);
			return true;
		}
		return false;
	}

	public boolean swap(ModifiableBooleanReference other) {
	    if (other == this) {
	        return this.getValue();
	    }
	    boolean thisValue = this.getValue();
	    boolean otherValue = other.getValue();
	    if (thisValue != otherValue) {
	        other.setValue(thisValue);
	        this.setValue(otherValue);
	    }
	    return otherValue;
	}
}

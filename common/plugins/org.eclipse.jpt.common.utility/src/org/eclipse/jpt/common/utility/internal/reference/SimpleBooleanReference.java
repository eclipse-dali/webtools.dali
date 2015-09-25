/*******************************************************************************
 * Copyright (c) 2008, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.reference;

import java.io.Serializable;
import org.eclipse.jpt.common.utility.reference.ModifiableBooleanReference;

/**
 * Provide a container for passing a flag that can be changed by the recipient.
 * 
 * @see SynchronizedBoolean
 */
public class SimpleBooleanReference
	implements ModifiableBooleanReference, Cloneable, Serializable
{
	/** Backing <code>boolean</code>. */
	protected volatile boolean value;

	private static final long serialVersionUID = 1L;


	// ********** constructors **********

	/**
	 * Create a <code>boolean</code> reference with the specified initial value.
	 */
	public SimpleBooleanReference(boolean value) {
		super();
		this.value = value;
	}

	/**
	 * Create a <code>boolean</code> reference with an initial value of
	 * <code>false</code>.
	 */
	public SimpleBooleanReference() {
		this(false);
	}


	// ********** accessors **********

	public boolean getValue() {
		return this.value;
	}

	public boolean is(boolean v) {
		return this.value == v;
	}

	public boolean isNot(boolean v) {
		return this.value != v;
	}

	public boolean isTrue() {
		return this.value;
	}

	public boolean isFalse() {
		return ! this.value;
	}

	public boolean setValue(boolean value) {
		boolean old = this.value;
		this.value = value;
		return old;
	}

	public boolean flip() {
		return this.value = ! this.value;
	}

	public boolean and(boolean b) {
		return this.value &= b;
	}

	public boolean or(boolean b) {
		return this.value |= b;
	}

	public boolean xor(boolean b) {
		return this.value ^= b;
	}

	public boolean setNot(boolean b) {
		return this.setValue( ! b);
	}

	public boolean setTrue() {
		return this.setValue(true);
	}

	public boolean setFalse() {
		return this.setValue(false);
	}

	public boolean commit(boolean newValue, boolean expectedValue) {
		if (this.value == expectedValue) {
			this.value = newValue;
			return true;
		}
		return false;
	}

	public boolean swap(ModifiableBooleanReference other) {
	    if (other == this) {
	        return this.value;
	    }
	    boolean thisValue = this.value;
	    boolean otherValue = other.getValue();
	    if (thisValue != otherValue) {
	        other.setValue(thisValue);
	        this.value = otherValue;
	    }
	    return otherValue;
	}


	// ********** standard methods **********

	@Override
	public SimpleBooleanReference clone() {
		try {
			return (SimpleBooleanReference) super.clone();
		} catch (CloneNotSupportedException ex) {
			throw new InternalError();
		}
	}

	/**
	 * Object identity is critical to boolean references.
	 * There is no reason for two different boolean references to be
	 * <em>equal</em>.
	 * 
	 * @see #is(boolean)
	 */
	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	/**
	 * @see #equals(Object)
	 */
	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public String toString() {
		return '[' + String.valueOf(this.value) + ']';
	}
}

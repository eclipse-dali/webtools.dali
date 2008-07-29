/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal;

import java.io.Serializable;

/**
 * Provide a container for passing a flag that can be changed by the recipient.
 * @see SynchronizedBoolean
 */
public class BooleanHolder
	implements Cloneable, Serializable
{
	/** Backing boolean. */
	private boolean value;

	private static final long serialVersionUID = 1L;


	// ********** constructors **********

	/**
	 * Create a boolean holder with the specified initial value.
	 */
	public BooleanHolder(boolean value) {
		super();
		this.value = value;
	}

	/**
	 * Create a boolean holder with an initial value of false.
	 */
	public BooleanHolder() {
		this(false);
	}


	// ********** accessors **********

	/**
	 * Return the current boolean value.
	 */
	public boolean getValue() {
		return this.value;
	}

	/**
	 * Return whether the current boolean value is true.
	 */
	public boolean isTrue() {
		return this.value;
	}

	/**
	 * Return whether the current boolean value is false.
	 */
	public boolean isFalse() {
		return ! this.value;
	}

	/**
	 * Return whether the current boolean value is the specified value.
	 */
	public boolean is(boolean v) {
		return this.value == v;
	}

	/**
	 * Set the boolean value.
	 */
	public void setValue(boolean value) {
		this.value = value;
	}

	/**
	 * Set the boolean value to true.
	 */
	public void setTrue() {
		this.value = true;
	}

	/**
	 * Set the boolean value to false.
	 */
	public void setFalse() {
		this.value = false;
	}


	// ********** standard methods **********

	@Override
	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException ex) {
			throw new InternalError();
		}
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof BooleanHolder) {
			return this.value == ((BooleanHolder) o).value;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return this.value ? 1 : 0;
	}

	@Override
	public String toString() {
		return '[' + String.valueOf(this.value) + ']';
	}

}

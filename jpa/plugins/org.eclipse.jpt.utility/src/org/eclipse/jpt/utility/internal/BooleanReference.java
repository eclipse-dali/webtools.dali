/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
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
 * 
 * @see SynchronizedBoolean
 */
public class BooleanReference
	implements Cloneable, Serializable
{
	/** Backing <code>boolean</code>. */
	private volatile boolean value;

	private static final long serialVersionUID = 1L;


	// ********** constructors **********

	/**
	 * Create a <code>boolean</code> reference with the specified initial value.
	 */
	public BooleanReference(boolean value) {
		super();
		this.value = value;
	}

	/**
	 * Create a <code>boolean</code> reference with an initial value of
	 * <code>false</code>.
	 */
	public BooleanReference() {
		this(false);
	}


	// ********** accessors **********

	/**
	 * Return the current <code>boolean</code> value.
	 */
	public boolean getValue() {
		return this.value;
	}

	/**
	 * Return whether the current <code>boolean</code> value is the specified
	 * value.
	 */
	public boolean is(boolean v) {
		return this.value == v;
	}

	/**
	 * Return whether the current <code>boolean</code> value is not the
	 * specified value.
	 */
	public boolean isNot(boolean v) {
		return this.value != v;
	}

	/**
	 * Return whether the current <code>boolean</code> value is
	 * <code>true</code>.
	 */
	public boolean isTrue() {
		return this.value;
	}

	/**
	 * Return whether the current <code>boolean</code> value is
	 * <code>false</code>.
	 */
	public boolean isFalse() {
		return ! this.value;
	}

	/**
	 * Set the <code>boolean</code> value.
	 * Return the previous value.
	 */
	public boolean setValue(boolean value) {
		boolean old = this.value;
		this.value = value;
		return old;
	}

	/**
	 * Set the <code>boolean</code> value to the NOT of its current value.
	 * Return the new value.
	 */
	public boolean flip() {
		return this.value = ! this.value;
	}

	/**
	 * Set the <code>boolean</code> value to the NOT of the specified value.
	 * Return the previous value.
	 */
	public boolean setNot(boolean v) {
		return this.setValue( ! v);
	}

	/**
	 * Set the <code>boolean</code> value to <code>true</code>.
	 * Return the previous value.
	 */
	public boolean setTrue() {
		return this.setValue(true);
	}

	/**
	 * Set the <code>boolean</code> value to <code>false</code>.
	 * Return the previous value.
	 */
	public boolean setFalse() {
		return this.setValue(false);
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
		return (o instanceof BooleanReference) &&
			(this.value == ((BooleanReference) o).value);
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

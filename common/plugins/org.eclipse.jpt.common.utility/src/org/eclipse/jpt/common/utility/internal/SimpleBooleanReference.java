/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal;

import java.io.Serializable;

/**
 * Provide a container for passing a flag that can be changed by the recipient.
 * 
 * @see SynchronizedBoolean
 */
public class SimpleBooleanReference
	implements BooleanReference, Cloneable, Serializable
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

	public boolean setNot(boolean v) {
		return this.setValue( ! v);
	}

	public boolean setTrue() {
		return this.setValue(true);
	}

	public boolean setFalse() {
		return this.setValue(false);
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

	@Override
	public String toString() {
		return '[' + String.valueOf(this.value) + ']';
	}
}

/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.reference;

import org.eclipse.jpt.common.utility.reference.BooleanReference;

/**
 * Convenience abstract class for boolean reference implementations.
 * Subclasses need only implement<ul>
 * <li>{@link #getValue()}
 * </ul>
 */
public abstract class AbstractBooleanReference
	implements BooleanReference
{
	protected AbstractBooleanReference() {
		super();
	}

	public boolean is(boolean value) {
		return this.getValue() == value;
	}

	public boolean isNot(boolean value) {
		return this.getValue() != value;
	}

	public boolean isTrue() {
		return this.getValue();
	}

	public boolean isFalse() {
		return ! this.getValue();
	}


	// ********** standard methods **********

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
		return '[' + String.valueOf(this.getValue()) + ']';
	}
}

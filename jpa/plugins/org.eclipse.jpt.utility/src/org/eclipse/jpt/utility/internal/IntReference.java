/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal;

/**
 * Interface for a container for passing an integer that can be changed by
 * the recipient.
 */
public interface IntReference
	extends ReadOnlyIntReference
{
	/**
	 * Set the <code>int</code> value.
	 * Return the previous value.
	 */
	int setValue(int value);

	/**
	 * Set the <code>int</code> value to zero.
	 * Return the previous value.
	 */
	int setZero();

	/**
	 * Increment and return the <code>int</code> value.
	 */
	int increment();

	/**
	 * Decrement and return the <code>int</code> value.
	 */
	int decrement();
}

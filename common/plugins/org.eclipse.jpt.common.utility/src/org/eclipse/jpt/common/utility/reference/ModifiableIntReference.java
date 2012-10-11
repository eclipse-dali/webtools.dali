/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.reference;

/**
 * Interface for a container for passing an integer that can be changed by
 * the recipient.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface ModifiableIntReference
	extends IntReference
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

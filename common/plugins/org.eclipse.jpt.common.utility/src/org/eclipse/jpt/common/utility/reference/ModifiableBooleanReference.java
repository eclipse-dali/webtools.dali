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
 * Interface for a container for passing a flag that can be changed by
 * the recipient.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface ModifiableBooleanReference
	extends BooleanReference
{
	/**
	 * Set the <code>boolean</code> value.
	 * Return the previous value.
	 */
	boolean setValue(boolean value);

	/**
	 * Set the <code>boolean</code> value to the NOT of its current value.
	 * Return the new value.
	 */
	boolean flip();

	/**
	 * Set the <code>boolean</code> value to the NOT of the specified value.
	 * Return the previous value.
	 */
	boolean setNot(boolean v);

	/**
	 * Set the <code>boolean</code> value to <code>true</code>.
	 * Return the previous value.
	 */
	boolean setTrue();

	/**
	 * Set the <code>boolean</code> value to <code>false</code>.
	 * Return the previous value.
	 */
	boolean setFalse();
}

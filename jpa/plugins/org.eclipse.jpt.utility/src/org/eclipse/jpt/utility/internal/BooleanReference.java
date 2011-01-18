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
 * Interface for a container for passing a flag that can be changed by
 * the recipient.
 */
public interface BooleanReference
	extends ReadOnlyBooleanReference
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

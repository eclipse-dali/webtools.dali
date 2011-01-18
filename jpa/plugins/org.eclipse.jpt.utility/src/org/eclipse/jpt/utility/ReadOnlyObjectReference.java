/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility;

/**
 * Provide a container for holding an object that cannot be changed.
 * 
 * @see ObjectReference
 */
public interface ReadOnlyObjectReference<V>
{
	/**
	 * Return the current value.
	 */
	V getValue();

	/**
	 * Return whether the current value is equal to the specified value.
	 */
	boolean valueEquals(Object object);

	/**
	 * Return whether the current value is not equal to the specified value.
	 */
	boolean valueNotEqual(Object object);

	/**
	 * Return whether the current value is <code>null</code>.
	 */
	boolean isNull();

	/**
	 * Return whether the current value is not <code>null</code>.
	 */
	boolean isNotNull();
}

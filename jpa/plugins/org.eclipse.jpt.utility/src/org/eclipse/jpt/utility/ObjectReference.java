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
 * Provide a container for passing an object that can be changed by the recipient.
 */
public interface ObjectReference<V>
	extends ReadOnlyObjectReference<V>
{
	/**
	 * Set the value.
	 * Return the previous value.
	 */
	V setValue(V value);

	/**
	 * Set the value to <code>null</code>.
	 * Return the previous value.
	 */
	V setNull();
}

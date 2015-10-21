/*******************************************************************************
 * Copyright (c) 2010, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.reference;

/**
 * Provide a container for passing an object that can be changed by the recipient.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface ModifiableObjectReference<V>
	extends ObjectReference<V>
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

	/**
	 * Set the value to the specified new value if it is
	 * currently {@link Object#equals(Object) equal} to
	 * the specified expected value.
	 * Return whether the set was successful.
	 */
	boolean commit(V newValue, V expectedValue);

	/**
 	 * Swap the value of the object reference with
 	 * the value of the specified object reference.
 	 * Return the new value.
	 */
	V swap(ModifiableObjectReference<V> other);
}

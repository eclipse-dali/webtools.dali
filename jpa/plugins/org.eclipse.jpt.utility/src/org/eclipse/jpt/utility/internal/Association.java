/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal;

/**
 * Straightforward definition of an object pairing.
 * The key is immutable.
 */
public interface Association<K, V> {

	/**
	 * Return the association's key.
	 */
	K key();

	/**
	 * Return the association's value.
	 */
	V value();

	/**
	 * Set the association's value.
	 * Return the previous value.
	 */
	V setValue(V value);

	/**
	 * Return true if both the associations' keys and values
	 * are equal.
	 */
	boolean equals(Object o);

	/**
	 * Return a hash code based on the association's
	 * key and value.
	 */
	int hashCode();

}

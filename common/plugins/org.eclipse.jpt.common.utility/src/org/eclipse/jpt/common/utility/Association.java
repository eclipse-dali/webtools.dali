/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility;

import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * Straightforward definition of an object pairing.
 * The key is immutable.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @param <K> the type of the association's key
 * @param <V> the type of the association's value
 * @see org.eclipse.jpt.common.utility.Pair
 */
public interface Association<K, V> {

	/**
	 * Return the association's key.
	 */
	K getKey();

	@SuppressWarnings("rawtypes")
	Transformer KEY_TRANSFORMER = new KeyTransformer();
	class KeyTransformer<K, V>
		implements Transformer<Association<K, V>, K>
	{
		public K transform(Association<K, V> association) {
			return association.getKey();
		}
		@Override
		public String toString() {
			return ObjectTools.singletonToString(this);
		}
	}

	/**
	 * Return the association's value.
	 */
	V getValue();

	@SuppressWarnings("rawtypes")
	Transformer VALUE_TRANSFORMER = new ValueTransformer();
	class ValueTransformer<K, V>
		implements Transformer<Association<K, V>, V>
	{
		public V transform(Association<K, V> association) {
			return association.getValue();
		}
		@Override
		public String toString() {
			return ObjectTools.singletonToString(this);
		}
	}

	/**
	 * Set the association's value.
	 * Return the previous value.
	 */
	V setValue(V value);

	/**
	 * Return true if the associations' keys and values
	 * are equal.
	 */
	boolean equals(Object o);

	/**
	 * Return a hash code based on the association's
	 * key and value.
	 */
	int hashCode();
}

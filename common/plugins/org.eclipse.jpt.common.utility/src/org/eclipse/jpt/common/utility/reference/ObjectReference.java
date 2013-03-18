/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.reference;

import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.transformer.Transformer;


/**
 * Provide a container for holding an object.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @see ModifiableObjectReference
 */
public interface ObjectReference<V> {
	/**
	 * Return the current value.
	 */
	V getValue();

	@SuppressWarnings("rawtypes")
	Transformer TRANSFORMER = new ValueTransformer();
	class ValueTransformer<V>
		implements Transformer<ObjectReference<V>, V>
	{
		public V transform(ObjectReference<V> ref) {
			return ref.getValue();
		}
		@Override
		public String toString() {
			return ObjectTools.singletonToString(this);
		}
	}

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

/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.factory;

import org.eclipse.jpt.common.utility.factory.Factory;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * Wrap a factory that returns an object of type <code>X</code>,
 * converting it into a factory that returns
 * an object of type <code>T</code>. <em>Assume</em> the wrapped
 * factory returns only objects of type <code>T</code>. The result is a
 * {@link ClassCastException} if this assumption is false.
 * 
 * @param <X> the type of object returned by the wrapped factory
 * @param <T> the type of object returned by the factory - this
 *   is the same object returned by the wrapped factory, simply
 *   cast to <code>T</code>
 * 
 * @see DowncastingFactoryWrapper
 * @see UpcastingFactoryWrapper
 */
public class CastingFactoryWrapper<X, T>
	implements Factory<T>
{
	private final Factory<X> factory;


	public CastingFactoryWrapper(Factory<X> factory) {
		super();
		if (factory == null) {
			throw new NullPointerException();
		}
		this.factory = factory;
	}

	/**
	 * Cast the result and hope for the best.
	 */
	@SuppressWarnings("unchecked")
	public T create() {
		return (T) this.factory.create();
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.factory);
	}
}

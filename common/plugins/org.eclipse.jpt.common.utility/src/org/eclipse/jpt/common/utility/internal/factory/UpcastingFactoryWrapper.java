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

import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.factory.Factory;

/**
 * Wrap a factory that returns an object of type <code>X</code>,
 * converting it into a factory that returns
 * an object of type <code>T</code>. <em>Assume</em> the wrapped
 * factory returns only objects of type <code>T</code>. The result is a
 * {@link ClassCastException} if this assumption is false.
 * <p>
 * This is like a {@link CastingFactoryWrapper} but with more restrictive type
 * parameters.
 * 
 * @param <X> the type of object returned by the wrapped factory
 * @param <T> the type of object returned by the factory - this
 *   is the same object returned by the wrapped factory, simply
 *   cast to <code>T</code>
 * 
 * @see CastingFactoryWrapper
 * @see DowncastingFactoryWrapper
 */
public class UpcastingFactoryWrapper<T, X extends T>
	implements Factory<T>
{
	private final Factory<X> factory;


	public UpcastingFactoryWrapper(Factory<X> factory) {
		super();
		if (factory == null) {
			throw new NullPointerException();
		}
		this.factory = factory;
	}

	/**
	 * No need for casting as the type is guaranteed by the generic type
	 * argument.
	 */
	public T create() {
		return this.factory.create();
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.factory);
	}
}

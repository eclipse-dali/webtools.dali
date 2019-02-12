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

import org.eclipse.jpt.common.utility.closure.Closure;
import org.eclipse.jpt.common.utility.factory.Factory;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * Adapt a {@link Closure} to the {@link Factory} interface.
 * The closure is passed <code>null</code> for an argument and the factory
 * always returns <code>null</code>.
 * This really only useful for a closure that accepts a
 * <code>null</code> argument.
 * 
 * @param <T> the type of the object returned by the factory
 * 
 * @see org.eclipse.jpt.common.utility.internal.closure.FactoryClosure
 */
public class ClosureFactory<T>
	implements Factory<T>
{
	private final Closure<?> closure;


	public ClosureFactory(Closure<?> closure) {
		super();
		if (closure == null) {
			throw new NullPointerException();
		}
		this.closure = closure;
	}

	public T create() {
		this.closure.execute(null);
		return null;
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.closure);
	}
}

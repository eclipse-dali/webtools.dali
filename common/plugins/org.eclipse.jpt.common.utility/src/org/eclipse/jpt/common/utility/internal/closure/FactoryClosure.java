/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.closure;

import org.eclipse.jpt.common.utility.closure.Closure;
import org.eclipse.jpt.common.utility.factory.Factory;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * Adapt a {@link Factory} to the {@link Closure} interface.
 * The closure's argument and the factory's output are ignored.
 * This really only useful for a factory that has side-effects.
 * 
 * @param <A> the type of the object passed to the closure;
 *     ignored
 * 
 * @see org.eclipse.jpt.common.utility.internal.factory.ClosureFactory
 */
public class FactoryClosure<A>
	implements Closure<A>
{
	private final Factory<?> factory;


	public FactoryClosure(Factory<?> factory) {
		super();
		if (factory == null) {
			throw new NullPointerException();
		}
		this.factory = factory;
	}

	public void execute(A argument) {
		this.factory.create();
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.factory);
	}
}

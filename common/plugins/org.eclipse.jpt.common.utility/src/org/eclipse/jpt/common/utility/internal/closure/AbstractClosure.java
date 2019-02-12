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

/**
 * Convenience superclass that does nothing if the argument
 * is <code>null</code>; otherwise it calls {@link #execute_(Object)},
 * which is to be implemented by subclasses.
 * 
 * @param <A> the type of the object passed to the closure
 * 
 * @see NullClosure
 * @see ClosureAdapter
 * @see NullCheckClosureWrapper
 */
public abstract class AbstractClosure<A>
	extends ClosureAdapter<A>
{
	@Override
	public final void execute(A argument) {
		if (argument != null) {
			this.execute_(argument);
		}
	}

	/**
	 * Process the specified argument; its value is guaranteed to be not
	 * <code>null</code>.
	 */
	protected abstract void execute_(A argument);
}

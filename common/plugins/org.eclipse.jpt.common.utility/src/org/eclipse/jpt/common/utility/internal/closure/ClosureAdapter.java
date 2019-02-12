/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.closure;

import org.eclipse.jpt.common.utility.closure.Closure;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * Convenience closure that does nothing.
 * 
 * @param <A> the type of the object passed to the closure
 * 
 * @see AbstractClosure
 * @see NullClosure
 * @see NullCheckClosureWrapper
 */
public class ClosureAdapter<A>
	implements Closure<A>
{
	public void execute(A argument) {
		// NOP
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this);
	}
}

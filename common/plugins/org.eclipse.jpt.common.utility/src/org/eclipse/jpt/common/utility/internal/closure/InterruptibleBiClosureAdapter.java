/*******************************************************************************
 * Copyright (c) 2012, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.closure;

import org.eclipse.jpt.common.utility.closure.InterruptibleBiClosure;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * Convenience interruptible bi-closure that does nothing.
 * 
 * @param <A1> the type of the first object passed to the closure
 * @param <A2> the type of the second object passed to the closure
 * 
 * @see BiClosureAdapter
 * @see NullBiClosure
 */
public class InterruptibleBiClosureAdapter<A1, A2>
	implements InterruptibleBiClosure<A1, A2>
{
	public void execute(A1 argument1, A2 argument2) throws InterruptedException {
		// NOP
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this);
	}
}

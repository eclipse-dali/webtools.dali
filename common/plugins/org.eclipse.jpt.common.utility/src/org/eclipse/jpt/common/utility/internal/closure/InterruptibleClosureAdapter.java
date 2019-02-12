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

import org.eclipse.jpt.common.utility.closure.InterruptibleClosure;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * @see ClosureAdapter
 * @see NullClosure
 * @see AbstractInterruptibleClosure
 * @see NullCheckInterruptibleClosureWrapper
 */
public class InterruptibleClosureAdapter<A>
	implements InterruptibleClosure<A>
{
	public void execute(A argument) throws InterruptedException {
		// NOP
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this);
	}
}

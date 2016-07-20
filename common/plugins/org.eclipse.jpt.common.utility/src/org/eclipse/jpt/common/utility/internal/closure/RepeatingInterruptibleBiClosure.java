/*******************************************************************************
 * Copyright (c) 2013, 2016 Oracle. All rights reserved.
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
 * BiClosure that executes another closure a specified number of times.
 * 
 * @param <A1> the type of the first object passed to the closure
 * @param <A2> the type of the second object passed to the closure
 */
public class RepeatingInterruptibleBiClosure<A1, A2>
	implements InterruptibleBiClosure<A1, A2>
{
	private final int count;
	private final InterruptibleBiClosure<? super A1, ? super A2> closure;

	public RepeatingInterruptibleBiClosure(InterruptibleBiClosure<? super A1, ? super A2> closure, int count) {
		super();
		if (closure == null) {
			throw new NullPointerException();
		}
		this.closure = closure;
		if (count < 0) {
			throw new IndexOutOfBoundsException("invalid count: " + count); //$NON-NLS-1$
		}
		this.count = count;
	}

	public void execute(A1 argument1, A2 argument2) throws InterruptedException {
		for (int i = this.count; i-- > 0;) {
			this.closure.execute(argument1, argument2);
		}
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.closure);
	}
}

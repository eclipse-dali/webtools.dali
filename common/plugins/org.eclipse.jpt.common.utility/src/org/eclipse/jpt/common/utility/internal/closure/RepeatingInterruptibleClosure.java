/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.closure;

import org.eclipse.jpt.common.utility.closure.InterruptibleClosure;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * @see RepeatingClosure
 * @see ConditionalInterruptibleClosure
 * @see SwitchInterruptibleClosure
 * @see UntilInterruptibleClosure
 * @see WhileInterruptibleClosure
 */
public class RepeatingInterruptibleClosure<A>
	implements InterruptibleClosure<A>
{
	private final int count;
	private final InterruptibleClosure<? super A> closure;

	public RepeatingInterruptibleClosure(InterruptibleClosure<? super A> closure, int count) {
		super();
		if (closure == null) {
			throw new NullPointerException();
		}
		if (count <= 0) {
			throw new IndexOutOfBoundsException("invalid count: " + count); //$NON-NLS-1$
		}
		this.closure = closure;
		this.count = count;
	}

	public void execute(A argument) throws InterruptedException {
		for (int i = this.count; i-- > 0;) {
			this.closure.execute(argument);
		}
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.closure);
	}
}

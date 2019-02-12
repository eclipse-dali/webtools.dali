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

import org.eclipse.jpt.common.utility.closure.InterruptibleClosure;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.predicate.Predicate;

/**
 * @see UntilClosure
 * @see ConditionalInterruptibleClosure
 * @see RepeatingInterruptibleClosure
 * @see SwitchInterruptibleClosure
 * @see WhileInterruptibleClosure
 */
public class UntilInterruptibleClosure<A>
	implements InterruptibleClosure<A>
{
	private final InterruptibleClosure<? super A> closure;
	private final Predicate<? super A> predicate;

	public UntilInterruptibleClosure(InterruptibleClosure<? super A> closure, Predicate<? super A> predicate) {
		super();
		if ((closure == null) || (predicate == null)) {
			throw new NullPointerException();
		}
		this.predicate = predicate;
		this.closure = closure;
	}

	public void execute(A argument) throws InterruptedException {
		do {
			this.closure.execute(argument);
		} while ( ! this.predicate.evaluate(argument));
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.predicate);
	}
}

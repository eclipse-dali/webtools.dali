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
		if (closure == null) {
			throw new NullPointerException();
		}
		this.closure = closure;
		if (predicate == null) {
			throw new NullPointerException();
		}
		this.predicate = predicate;
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

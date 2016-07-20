/*******************************************************************************
 * Copyright (c) 2013, 2015 Oracle. All rights reserved.
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
 * @see WhileClosure
 * @see ConditionalInterruptibleClosure
 * @see RepeatingInterruptibleClosure
 * @see SwitchInterruptibleClosure
 * @see UntilInterruptibleClosure
 */
public class WhileInterruptibleClosure<A>
	implements InterruptibleClosure<A>
{
	private final Predicate<? super A> predicate;
	private final InterruptibleClosure<? super A> closure;

	public WhileInterruptibleClosure(Predicate<? super A> predicate, InterruptibleClosure<? super A> closure) {
		super();
		if (predicate == null) {
			throw new NullPointerException();
		}
		this.predicate = predicate;
		if (closure == null) {
			throw new NullPointerException();
		}
		this.closure = closure;
	}

	public void execute(A argument) throws InterruptedException {
		while (this.predicate.evaluate(argument)) {
			this.closure.execute(argument);
		}
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.predicate);
	}
}

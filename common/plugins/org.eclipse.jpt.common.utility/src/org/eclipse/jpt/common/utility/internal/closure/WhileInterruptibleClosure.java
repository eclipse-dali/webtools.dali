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
import org.eclipse.jpt.common.utility.predicate.Predicate;

/**
 * InterruptibleClosure that executes another closure while a predicate evaluates to
 * <code>true</code> when passed the argument.
 * 
 * @param <A> the type of the object passed to the closure
 */
public class WhileInterruptibleClosure<A>
	implements InterruptibleClosure<A>
{
	private final Predicate<? super A> predicate;
	private final InterruptibleClosure<? super A> closure;

	public WhileInterruptibleClosure(Predicate<? super A> predicate, InterruptibleClosure<? super A> closure) {
		super();
		if ((predicate == null) || (closure == null)) {
			throw new NullPointerException();
		}
		this.predicate = predicate;
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

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

import org.eclipse.jpt.common.utility.closure.Closure;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.predicate.Predicate;

/**
 * Closure that executes another closure while a predicate evaluates to
 * <code>true</code> when passed the argument.
 * 
 * @param <A> the type of the object passed to the closure
 * 
 * @see ConditionalClosure
 * @see RepeatingClosure
 * @see SwitchClosure
 * @see UntilClosure
 */
public class WhileClosure<A>
	implements Closure<A>
{
	private final Predicate<? super A> predicate;
	private final Closure<? super A> closure;

	public WhileClosure(Predicate<? super A> predicate, Closure<? super A> closure) {
		super();
		if ((predicate == null) || (closure == null)) {
			throw new NullPointerException();
		}
		this.predicate = predicate;
		this.closure = closure;
	}

	public void execute(A argument) {
		while (this.predicate.evaluate(argument)) {
			this.closure.execute(argument);
		}
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.predicate);
	}
}

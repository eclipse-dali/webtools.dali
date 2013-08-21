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
 * Closure that executes another closure until a predicate evaluates to
 * <code>true</code> when passed the argument. The wrapped closure will
 * always execute at least once.
 * <p>
 * <strong>NB:</strong> This is the inverse of the Java <code>do-while</code>
 * statement (i.e. it executes until the predicate evaluates to
 * <strong><code>true</code></strong>,
 * <em>not</em> <code>false</code>).
 * 
 * @param <A> the type of the object passed to the closure
 * 
 * @see ConditionalClosure
 * @see RepeatingClosure
 * @see SwitchClosure
 * @see WhileClosure
 */
public class UntilClosure<A>
	implements Closure<A>
{
	private final Closure<? super A> closure;
	private final Predicate<? super A> predicate;

	public UntilClosure(Closure<? super A> closure, Predicate<? super A> predicate) {
		super();
		if ((closure == null) || (predicate == null)) {
			throw new NullPointerException();
		}
		this.predicate = predicate;
		this.closure = closure;
	}

	public void execute(A argument) {
		do {
			this.closure.execute(argument);
		} while ( ! this.predicate.evaluate(argument));
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.predicate);
	}
}

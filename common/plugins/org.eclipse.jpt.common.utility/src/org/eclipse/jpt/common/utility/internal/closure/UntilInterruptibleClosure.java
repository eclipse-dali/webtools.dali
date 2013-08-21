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
 * Interruptible closure that executes another closure until a predicate evaluates to
 * <code>true</code> when passed the argument. The
 * <p>
 * <strong>NB:</strong> This is the inverse of the Java <code>do-while</code>
 * statement (i.e. it executes until the predicate evaluates to
 * <strong><code>true</code></strong>,
 * <em>not</em> <code>false</code>).
 * 
 * @param <A> the type of the object passed to the closure
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

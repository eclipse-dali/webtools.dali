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

import org.eclipse.jpt.common.utility.closure.Closure;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.predicate.Predicate;

/**
 * Closure that passes its argument to a configured predicate to determine
 * which of its two closures to execute.
 * 
 * @param <A> the type of the object passed to the closure
 * 
 * @see RepeatingClosure
 * @see SwitchClosure
 * @see UntilClosure
 * @see WhileClosure
 */
public class ConditionalClosure<A>
	implements Closure<A>
{
	private final Predicate<? super A> predicate;
	private final Closure<? super A> trueClosure;
	private final Closure<? super A> falseClosure;

	public ConditionalClosure(Predicate<? super A> predicate, Closure<? super A> trueClosure, Closure<? super A> falseClosure) {
		super();
		if ((predicate == null) || (trueClosure == null) || (falseClosure == null)) {
			throw new NullPointerException();
		}
		this.predicate = predicate;
		this.trueClosure = trueClosure;
		this.falseClosure = falseClosure;
	}

	public void execute(A argument) {
		if (this.predicate.evaluate(argument)) {
			this.trueClosure.execute(argument);
		} else {
			this.falseClosure.execute(argument);
		}
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.predicate);
	}
}

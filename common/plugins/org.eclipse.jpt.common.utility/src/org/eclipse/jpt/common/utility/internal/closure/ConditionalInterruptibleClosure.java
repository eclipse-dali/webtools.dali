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
 * @see ConditionalClosure
 * @see RepeatingInterruptibleClosure
 * @see SwitchInterruptibleClosure
 * @see UntilInterruptibleClosure
 * @see WhileInterruptibleClosure
 */
public class ConditionalInterruptibleClosure<A>
	implements InterruptibleClosure<A>
{
	private final Predicate<? super A> predicate;
	private final InterruptibleClosure<? super A> trueClosure;
	private final InterruptibleClosure<? super A> falseClosure;

	public ConditionalInterruptibleClosure(Predicate<? super A> predicate, InterruptibleClosure<? super A> trueClosure, InterruptibleClosure<? super A> falseClosure) {
		super();
		if (predicate == null) {
			throw new NullPointerException();
		}
		this.predicate = predicate;
		if (trueClosure == null) {
			throw new NullPointerException();
		}
		this.trueClosure = trueClosure;
		if (falseClosure == null) {
			throw new NullPointerException();
		}
		this.falseClosure = falseClosure;
	}

	public void execute(A argument) throws InterruptedException {
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

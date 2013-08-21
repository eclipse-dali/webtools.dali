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

import org.eclipse.jpt.common.utility.Association;
import org.eclipse.jpt.common.utility.closure.InterruptibleClosure;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.predicate.Predicate;

/**
 * Interruptible closure that loops over a configured set of predicate/closure pairs,
 * passing its argument to each predicate to determine
 * which of the closures to execute. Only the first closure whose predicate
 * evaluates to <code>true</code> is executed, even if other, following,
 * predicates would evaluate to <code>true</code>.
 * If none of the predicates evaluates to <code>true</code>, the default closure
 * is executed.
 * 
 * @param <A> the type of the object passed to the closure
 */
public class SwitchInterruptibleClosure<A>
	implements InterruptibleClosure<A>
{
	private final Iterable<Association<Predicate<? super A>, InterruptibleClosure<? super A>>> closures;
	private final InterruptibleClosure<? super A> defaultInterruptibleClosure;

	public SwitchInterruptibleClosure(Iterable<Association<Predicate<? super A>, InterruptibleClosure<? super A>>> closures, InterruptibleClosure<? super A> defaultInterruptibleClosure) {
		super();
		if (IterableTools.isOrContainsNull(closures) || (defaultInterruptibleClosure == null)) {
			throw new NullPointerException();
		}
		this.closures = closures;
		this.defaultInterruptibleClosure = defaultInterruptibleClosure;
	}

	public void execute(A argument) throws InterruptedException {
		for (Association<Predicate<? super A>, InterruptibleClosure<? super A>> association : this.closures) {
			if (association.getKey().evaluate(argument)) {
				association.getValue().execute(argument);
				return; // execute only one closure
			}
		}
		this.defaultInterruptibleClosure.execute(argument);
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.closures);
	}
}

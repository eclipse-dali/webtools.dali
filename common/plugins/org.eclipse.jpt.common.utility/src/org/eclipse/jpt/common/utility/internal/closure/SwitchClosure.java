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

import org.eclipse.jpt.common.utility.Association;
import org.eclipse.jpt.common.utility.closure.Closure;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.predicate.Predicate;

/**
 * Closure that loops over a configured set of predicate/closure pairs,
 * passing its argument to each predicate to determine
 * which of the closures to execute. Only the first closure whose predicate
 * evaluates to <code>true</code> is executed, even if other, following,
 * predicates would evaluate to <code>true</code>.
 * If none of the predicates evaluates to <code>true</code>, the default closure
 * is executed.
 * 
 * @param <A> the type of the object passed to the closure
 * 
 * @see ConditionalClosure
 * @see RepeatingClosure
 * @see UntilClosure
 * @see WhileClosure
 */
public class SwitchClosure<A>
	implements Closure<A>
{
	private final Iterable<Association<Predicate<? super A>, Closure<? super A>>> closures;
	private final Closure<? super A> defaultClosure;

	public SwitchClosure(Iterable<Association<Predicate<? super A>, Closure<? super A>>> closures, Closure<? super A> defaultClosure) {
		super();
		if (IterableTools.isOrContainsNull(closures) || (defaultClosure == null)) {
			throw new NullPointerException();
		}
		this.closures = closures;
		this.defaultClosure = defaultClosure;
	}

	public void execute(A argument) {
		for (Association<Predicate<? super A>, Closure<? super A>> association : this.closures) {
			if (association.getKey().evaluate(argument)) {
				association.getValue().execute(argument);
				return; // execute only one closure
			}
		}
		this.defaultClosure.execute(argument);
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.closures);
	}
}

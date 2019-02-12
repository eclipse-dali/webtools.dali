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
import org.eclipse.jpt.common.utility.closure.InterruptibleClosure;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.predicate.Predicate;

/**
 * @see SwitchClosure
 * @see ConditionalInterruptibleClosure
 * @see RepeatingInterruptibleClosure
 * @see UntilInterruptibleClosure
 * @see WhileInterruptibleClosure
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

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
	private final Iterable<? extends Association<? extends Predicate<? super A>, ? extends InterruptibleClosure<? super A>>> closures;
	private final InterruptibleClosure<? super A> defaultClosure;

	public SwitchInterruptibleClosure(Iterable<? extends Association<? extends Predicate<? super A>, ? extends InterruptibleClosure<? super A>>> closures, InterruptibleClosure<? super A> defaultClosure) {
		super();
		if (IterableTools.isOrContainsNull(closures)) {
			throw new NullPointerException();
		}
		this.closures = closures;
		if (defaultClosure == null) {
			throw new NullPointerException();
		}
		this.defaultClosure = defaultClosure;
	}

	public void execute(A argument) throws InterruptedException {
		for (Association<? extends Predicate<? super A>, ? extends InterruptibleClosure<? super A>> association : this.closures) {
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

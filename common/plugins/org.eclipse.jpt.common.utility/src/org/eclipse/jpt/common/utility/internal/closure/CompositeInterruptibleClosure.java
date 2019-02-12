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

import org.eclipse.jpt.common.utility.closure.InterruptibleClosure;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;

/**
 * @see CompositeClosure
 */
public class CompositeInterruptibleClosure<A>
	implements InterruptibleClosure<A>
{
	private final Iterable<InterruptibleClosure<? super A>> closures;

	public CompositeInterruptibleClosure(Iterable<InterruptibleClosure<? super A>> closures) {
		super();
		if (IterableTools.isOrContainsNull(closures)) {
			throw new NullPointerException();
		}
		this.closures = closures;
	}

	public void execute(A argument) throws InterruptedException {
		for (InterruptibleClosure<? super A> closure : this.closures) {
			closure.execute(argument);
		}
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.closures);
	}
}

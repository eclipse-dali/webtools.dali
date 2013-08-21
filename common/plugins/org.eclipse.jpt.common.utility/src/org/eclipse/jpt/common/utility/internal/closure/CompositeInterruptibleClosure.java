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
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;

/**
 * A composite of closures. Pass the chain's argument to each closure,
 * in sequence.
 * 
 * @param <A> the type of the object passed to the closure
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

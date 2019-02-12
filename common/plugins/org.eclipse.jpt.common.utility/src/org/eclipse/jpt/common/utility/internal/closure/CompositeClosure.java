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
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;

/**
 * A composite of closures. Pass the composite's argument to each closure,
 * in sequence.
 * 
 * @param <A> the type of the object passed to the closure
 */
public class CompositeClosure<A>
	implements Closure<A>
{
	private final Iterable<Closure<? super A>> closures;

	public CompositeClosure(Iterable<Closure<? super A>> closures) {
		super();
		if (IterableTools.isOrContainsNull(closures)) {
			throw new NullPointerException();
		}
		this.closures = closures;
	}

	public void execute(A argument) {
		for (Closure<? super A> closure : this.closures) {
			closure.execute(argument);
		}
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.closures);
	}
}

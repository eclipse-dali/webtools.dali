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

import org.eclipse.jpt.common.utility.closure.InterruptibleBiClosure;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;

/**
 * A composite of closures. Pass the composite's arguments to each closure,
 * in sequence.
 * 
 * @param <A1> the type of the first object passed to the closure
 * @param <A2> the type of the second object passed to the closure
 */
public class CompositeInterruptibleBiClosure<A1, A2>
	implements InterruptibleBiClosure<A1, A2>
{
	private final Iterable<? extends InterruptibleBiClosure<? super A1, ? super A2>> closures;

	public CompositeInterruptibleBiClosure(Iterable<? extends InterruptibleBiClosure<? super A1, ? super A2>> closures) {
		super();
		if (IterableTools.isOrContainsNull(closures)) {
			throw new NullPointerException();
		}
		this.closures = closures;
	}

	public void execute(A1 argument1, A2 argument2) throws InterruptedException {
		for (InterruptibleBiClosure<? super A1, ? super A2> closure : this.closures) {
			closure.execute(argument1, argument2);
		}
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.closures);
	}
}

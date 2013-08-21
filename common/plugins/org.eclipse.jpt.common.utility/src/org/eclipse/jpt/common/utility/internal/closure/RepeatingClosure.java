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

import org.eclipse.jpt.common.utility.closure.Closure;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * Closure that executes another closure a specified number of times.
 * 
 * @param <A> the type of the object passed to the closure
 */
public class RepeatingClosure<A>
	implements Closure<A>
{
	private final int count;
	private final Closure<? super A> closure;

	public RepeatingClosure(Closure<? super A> closure, int count) {
		super();
		if (closure == null) {
			throw new NullPointerException();
		}
		if (count <= 0) {
			throw new IndexOutOfBoundsException("invalid count: " + count); //$NON-NLS-1$
		}
		this.closure = closure;
		this.count = count;
	}

	public void execute(A argument) {
		for (int i = this.count; i-- > 0;) {
			this.closure.execute(argument);
		}
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.closure);
	}
}

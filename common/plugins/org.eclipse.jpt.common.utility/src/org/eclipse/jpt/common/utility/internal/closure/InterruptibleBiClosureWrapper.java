/*******************************************************************************
 * Copyright (c) 2012, 2016 Oracle. All rights reserved.
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

/**
 * Closure wrapper that can have its wrapped closure changed,
 * allowing a client to change a previously-supplied closure's
 * behavior mid-stream.
 * 
 * @param <A1> the type of the first object passed to the closure
 * @param <A2> the type of the second object passed to the closure
 * 
 * @see #setClosure(InterruptibleBiClosure)
 */
public class InterruptibleBiClosureWrapper<A1, A2>
	implements InterruptibleBiClosure<A1, A2>
{
	protected volatile InterruptibleBiClosure<? super A1, ? super A2> closure;

	public InterruptibleBiClosureWrapper(InterruptibleBiClosure<? super A1, ? super A2> closure) {
		super();
		if (closure == null) {
			throw new NullPointerException();
		}
		this.closure = closure;
	}

	public void execute(A1 argument1, A2 argument2) throws InterruptedException {
		this.closure.execute(argument1, argument2);
	}

	public void setClosure(InterruptibleBiClosure<? super A1, ? super A2> closure) {
		if (closure == null) {
			throw new NullPointerException();
		}
		this.closure = closure;
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.closure);
	}
}

/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
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

/**
 * closure wrapper that can have its wrapped closure changed,
 * allowing a client to change a previously-supplied closure's
 * behavior mid-stream.
 * 
 * @param <A> the type of the object passed to the closure
 * 
 * @see #setClosure(InterruptibleClosure)
 */
public class InterruptibleClosureWrapper<A>
	implements InterruptibleClosure<A>
{
	protected volatile InterruptibleClosure<? super A> closure;

	public InterruptibleClosureWrapper(InterruptibleClosure<? super A> closure) {
		super();
		if (closure == null) {
			throw new NullPointerException();
		}
		this.closure = closure;
	}

	public void execute(A argument) throws InterruptedException {
		this.closure.execute(argument);
	}

	public void setClosure(InterruptibleClosure<? super A> closure) {
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

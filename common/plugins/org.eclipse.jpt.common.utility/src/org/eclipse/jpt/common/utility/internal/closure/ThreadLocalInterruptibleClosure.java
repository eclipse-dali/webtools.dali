/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.closure;

import org.eclipse.jpt.common.utility.closure.InterruptibleClosure;

/**
 * @see ThreadLocalClosure
 */
public class ThreadLocalInterruptibleClosure<A>
	implements InterruptibleClosure<A>
{
	private final ThreadLocal<InterruptibleClosure<? super A>> threadLocal;
	private final InterruptibleClosure<? super A> defaultInterruptibleClosure;

	public ThreadLocalInterruptibleClosure(InterruptibleClosure<? super A> defaultInterruptibleClosure) {
		super();
		if (defaultInterruptibleClosure == null) {
			throw new NullPointerException();
		}
		this.defaultInterruptibleClosure = defaultInterruptibleClosure;
		this.threadLocal = this.buildThreadLocal();
	}

	private ThreadLocal<InterruptibleClosure<? super A>> buildThreadLocal() {
		return new ThreadLocal<InterruptibleClosure<? super A>>();
	}

	public void execute(A argument) throws InterruptedException {
		this.get().execute(argument);
	}

	private InterruptibleClosure<? super A> get() {
		InterruptibleClosure<? super A> closure = this.threadLocal.get();
		if (closure != null) {
			return closure;
		}
		return this.defaultInterruptibleClosure;
	}

	/**
	 * Set the current thread's closure to the specified value.
	 */
	public void set(InterruptibleClosure<? super A> closure) {
		this.threadLocal.set(closure);
	}

	/**
	 * Return the string representation of the current thread's closure.
	 */
	@Override
	public String toString() {
		return this.get().toString();
	}
}

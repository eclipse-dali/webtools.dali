/*******************************************************************************
 * Copyright (c) 2008, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.closure;

import org.eclipse.jpt.common.utility.closure.BiClosure;
import org.eclipse.jpt.common.utility.closure.InterruptibleBiClosure;

/**
 * This closure allows the client to specify a different closure for each
 * thread. If there is no closure for the current thread, the configured default
 * closure is executed.
 * 
 * @param <A1> the type of the first object passed to the closure
 * @param <A2> the type of the second object passed to the closure
 * 
 * @see #set(BiClosure)
 */
public class ThreadLocalInterruptibleBiClosure<A1, A2>
	implements InterruptibleBiClosure<A1, A2>
{
	private final ThreadLocal<InterruptibleBiClosure<? super A1, ? super A2>> threadLocal;
	private final InterruptibleBiClosure<? super A1, ? super A2> defaultClosure;

	public ThreadLocalInterruptibleBiClosure(InterruptibleBiClosure<? super A1, ? super A2> defaultClosure) {
		super();
		if (defaultClosure == null) {
			throw new NullPointerException();
		}
		this.defaultClosure = defaultClosure;
		this.threadLocal = this.buildThreadLocal();
	}

	private ThreadLocal<InterruptibleBiClosure<? super A1, ? super A2>> buildThreadLocal() {
		return new ThreadLocal<>();
	}

	public void execute(A1 argument1, A2 argument2) throws InterruptedException {
		this.get().execute(argument1, argument2);
	}

	private InterruptibleBiClosure<? super A1, ? super A2> get() {
		InterruptibleBiClosure<? super A1, ? super A2> closure = this.threadLocal.get();
		return (closure != null) ? closure : this.defaultClosure;
	}

	/**
	 * Set the current thread's closure to the specified value.
	 */
	public void set(BiClosure<? super A1, ? super A2> closure) {
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

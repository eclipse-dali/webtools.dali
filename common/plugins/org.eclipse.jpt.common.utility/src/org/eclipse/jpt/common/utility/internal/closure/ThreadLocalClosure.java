/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.closure;

import org.eclipse.jpt.common.utility.closure.Closure;

/**
 * This closure allows the client to specify a different closure for each
 * thread. If there is no closure for the current thread, the configured default
 * closure is executed.
 * 
 * @param <A> the type of the object passed to the closure
 * 
 * @see #set(Closure)
 */
public class ThreadLocalClosure<A>
	implements Closure<A>
{
	private final ThreadLocal<Closure<? super A>> threadLocal;
	private final Closure<? super A> defaultClosure;

	public ThreadLocalClosure(Closure<? super A> defaultClosure) {
		super();
		if (defaultClosure == null) {
			throw new NullPointerException();
		}
		this.defaultClosure = defaultClosure;
		this.threadLocal = this.buildThreadLocal();
	}

	private ThreadLocal<Closure<? super A>> buildThreadLocal() {
		return new ThreadLocal<Closure<? super A>>();
	}

	public void execute(A argument) {
		this.get().execute(argument);
	}

	private Closure<? super A> get() {
		Closure<? super A> closure = this.threadLocal.get();
		if (closure != null) {
			return closure;
		}
		return this.defaultClosure;
	}

	/**
	 * Set the current thread's closure to the specified value.
	 */
	public void set(Closure<? super A> closure) {
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

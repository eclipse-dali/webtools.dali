/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.factory;

import org.eclipse.jpt.common.utility.factory.InterruptibleFactory;

/**
 * @see ThreadLocalFactory
 */
public class ThreadLocalInterruptibleFactory<T>
	implements InterruptibleFactory<T>
{
	private final ThreadLocal<InterruptibleFactory<? extends T>> threadLocal;
	private final InterruptibleFactory<? extends T> defaultInterruptibleFactory;

	public ThreadLocalInterruptibleFactory(InterruptibleFactory<? extends T> defaultInterruptibleFactory) {
		super();
		if (defaultInterruptibleFactory == null) {
			throw new NullPointerException();
		}
		this.defaultInterruptibleFactory = defaultInterruptibleFactory;
		this.threadLocal = this.buildThreadLocal();
	}

	private ThreadLocal<InterruptibleFactory<? extends T>> buildThreadLocal() {
		return new ThreadLocal<InterruptibleFactory<? extends T>>();
	}

	public T create() throws InterruptedException {
		return this.get().create();
	}

	private InterruptibleFactory<? extends T> get() {
		InterruptibleFactory<? extends T> factory = this.threadLocal.get();
		return (factory != null) ? factory : this.defaultInterruptibleFactory;
	}

	/**
	 * Set the current thread's factory to the specified value.
	 */
	public void set(InterruptibleFactory<? extends T> factory) {
		this.threadLocal.set(factory);
	}

	/**
	 * Return the string representation of the current thread's factory.
	 */
	@Override
	public String toString() {
		return this.get().toString();
	}
}

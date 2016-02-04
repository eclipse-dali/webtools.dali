/*******************************************************************************
 * Copyright (c) 2008, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.factory;

import org.eclipse.jpt.common.utility.factory.Factory;

/**
 * This factory allows the client to specify a different factory for each
 * thread. If there is no factory for the current thread, the configured default
 * factory is used.
 * 
 * @param <T> the type of the object returned by the factory
 * 
 * @see #set(Factory)
 */
public class ThreadLocalFactory<T>
	implements Factory<T>
{
	private final ThreadLocal<Factory<? extends T>> threadLocal;
	private final Factory<? extends T> defaultFactory;

	public ThreadLocalFactory(Factory<? extends T> defaultFactory) {
		super();
		if (defaultFactory == null) {
			throw new NullPointerException();
		}
		this.defaultFactory = defaultFactory;
		this.threadLocal = this.buildThreadLocal();
	}

	private ThreadLocal<Factory<? extends T>> buildThreadLocal() {
		return new ThreadLocal<>();
	}

	public T create() {
		return this.get().create();
	}

	private Factory<? extends T> get() {
		Factory<? extends T> factory = this.threadLocal.get();
		return (factory != null) ? factory : this.defaultFactory;
	}

	/**
	 * Set the current thread's factory to the specified value.
	 */
	public void set(Factory<? extends T> factory) {
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

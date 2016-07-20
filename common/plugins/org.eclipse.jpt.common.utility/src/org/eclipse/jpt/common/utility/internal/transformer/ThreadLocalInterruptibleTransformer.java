/*******************************************************************************
 * Copyright (c) 2008, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.transformer;

import org.eclipse.jpt.common.utility.transformer.InterruptibleTransformer;

/**
 * @see ThreadLocalTransformer
 */
public class ThreadLocalInterruptibleTransformer<I, O>
	implements InterruptibleTransformer<I, O>
{
	private final ThreadLocal<InterruptibleTransformer<? super I, ? extends O>> threadLocal;
	private final InterruptibleTransformer<? super I, ? extends O> defaultTransformer;

	public ThreadLocalInterruptibleTransformer(InterruptibleTransformer<? super I, ? extends O> defaultTransformer) {
		super();
		if (defaultTransformer == null) {
			throw new NullPointerException();
		}
		this.defaultTransformer = defaultTransformer;
		this.threadLocal = this.buildThreadLocal();
	}

	private ThreadLocal<InterruptibleTransformer<? super I, ? extends O>> buildThreadLocal() {
		return new ThreadLocal<>();
	}

	public O transform(I input) throws InterruptedException {
		return this.get().transform(input);
	}

	private InterruptibleTransformer<? super I, ? extends O> get() {
		InterruptibleTransformer<? super I, ? extends O> transformer = this.threadLocal.get();
		return (transformer != null) ? transformer : this.defaultTransformer;
	}

	/**
	 * Set the current thread's transformer to the specified value.
	 */
	public void set(InterruptibleTransformer<? super I, ? extends O> transformer) {
		this.threadLocal.set(transformer);
	}

	public InterruptibleTransformer<? super I, ? extends O> getDefaultInterruptibleTransformer() {
		return this.defaultTransformer;
	}

	/**
	 * Return the string representation of the current thread's transformer.
	 */
	@Override
	public String toString() {
		return this.get().toString();
	}
}

/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
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
	private final InterruptibleTransformer<? super I, ? extends O> defaultInterruptibleTransformer;

	public ThreadLocalInterruptibleTransformer(InterruptibleTransformer<? super I, ? extends O> defaultInterruptibleTransformer) {
		super();
		if (defaultInterruptibleTransformer == null) {
			throw new NullPointerException();
		}
		this.defaultInterruptibleTransformer = defaultInterruptibleTransformer;
		this.threadLocal = this.buildThreadLocal();
	}

	private ThreadLocal<InterruptibleTransformer<? super I, ? extends O>> buildThreadLocal() {
		return new ThreadLocal<InterruptibleTransformer<? super I, ? extends O>>();
	}

	public O transform(I input) throws InterruptedException {
		return this.get().transform(input);
	}

	private InterruptibleTransformer<? super I, ? extends O> get() {
		InterruptibleTransformer<? super I, ? extends O> transformer = this.threadLocal.get();
		if (transformer != null) {
			return transformer;
		}
		return this.defaultInterruptibleTransformer;
	}

	/**
	 * Set the current thread's transformer to the specified value.
	 */
	public void set(InterruptibleTransformer<? super I, ? extends O> transformer) {
		this.threadLocal.set(transformer);
	}

	/**
	 * Return the string representation of the current thread's transformer.
	 */
	@Override
	public String toString() {
		return this.get().toString();
	}
}

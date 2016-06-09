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

import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * This transformer allows the client to specify a different transformer for each
 * thread. If there is no transformer for the current thread, the configured default
 * transformer is used.
 * 
 * @param <I> input: the type of the object passed to the transformer
 * @param <O> output: the type of the object returned by the transformer
 * 
 * @see #set(Transformer)
 */
public class ThreadLocalTransformer<I, O>
	implements Transformer<I, O>
{
	private final ThreadLocal<Transformer<? super I, ? extends O>> threadLocal;
	private final Transformer<? super I, ? extends O> defaultTransformer;

	public ThreadLocalTransformer(Transformer<? super I, ? extends O> defaultTransformer) {
		super();
		if (defaultTransformer == null) {
			throw new NullPointerException();
		}
		this.defaultTransformer = defaultTransformer;
		this.threadLocal = this.buildThreadLocal();
	}

	private ThreadLocal<Transformer<? super I, ? extends O>> buildThreadLocal() {
		return new ThreadLocal<>();
	}

	public O transform(I input) {
		return this.get().transform(input);
	}

	private Transformer<? super I, ? extends O> get() {
		Transformer<? super I, ? extends O> transformer = this.threadLocal.get();
		if (transformer != null) {
			return transformer;
		}
		return this.defaultTransformer;
	}

	/**
	 * Set the current thread's transformer to the specified value.
	 */
	public void set(Transformer<? super I, ? extends O> transformer) {
		this.threadLocal.set(transformer);
	}

	public Transformer<? super I, ? extends O> getDefaultTransformer() {
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

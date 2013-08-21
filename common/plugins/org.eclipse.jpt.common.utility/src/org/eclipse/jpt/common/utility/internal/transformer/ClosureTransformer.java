/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.transformer;

import org.eclipse.jpt.common.utility.closure.Closure;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * Adapt a {@link Closure} to the {@link Transformer} interface.
 * The transformer will always return <code>null</code>.
 * 
 * @param <I> input: the type of the object passed to the transformer and forwarded
 *     to the closure
 * @param <O> output: the type of the object returned by the transformer; always
 *     <code>null</code>
 */
public class ClosureTransformer<I, O>
	implements Transformer<I, O>
{
	private final Closure<? super I> closure;

	public ClosureTransformer(Closure<? super I> closure) {
		super();
		if (closure == null) {
			throw new NullPointerException();
		}
		this.closure = closure;
	}

	public O transform(I input) {
		this.closure.execute(input);
		return null;
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.closure);
	}
}

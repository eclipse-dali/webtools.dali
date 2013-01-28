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

import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * Wrap a transformer that takes an object of type <code>T1</code> and returns
 * an object of type <code>T2</code>, converting it into a transformer
 * takes an object of type <code>T1</code> and returns
 * an object of type <code>T3</code>. <em>Assume</em> the wrapped
 * transformer returns only objects of type <code>T3</code>. The result is a
 * {@link ClassCastException} if this assumption is false.
 * 
 * @param <I> input: the type of the object passed to the transformer and
 *   forwarded to the wrapped transformer
 * @param <X> intermediate: the type of object returned by the wrapped
 *   transformer
 * @param <O> output: the type of object returned by the transformer - this
 *   is the same object returned by the wrapped transformer, simply
 *   cast to <code>T3</code>
 */
public class LateralTransformerWrapper<I, X, O>
	extends TransformerAdapter<I, O>
{
	private final Transformer<? super I, ? extends X> transformer;

	public LateralTransformerWrapper(Transformer<? super I, ? extends X> transformer) {
		super();
		if (transformer == null) {
			throw new NullPointerException();
		}
		this.transformer = transformer;
	}

	@Override
	@SuppressWarnings("unchecked")
	public O transform(I o) {
		return (O) this.transformer.transform(o);
	}
}

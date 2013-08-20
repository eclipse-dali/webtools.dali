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

import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * Wrap a transformer that takes an object of type <code>I</code> and returns
 * an object of type <code>X</code>, converting it into a transformer that
 * takes an object of type <code>I</code> and returns
 * an object of type <code>O</code>.
 * <p>
 * This is like a {@link CastingTransformerWrapper} but with more restrictive type
 * parameters.
 * 
 * @param <I> input: the type of the object passed to the transformer and
 *   forwarded to the wrapped transformer
 * @param <O> output: the type of object returned by the transformer - this
 *   is the same object returned by the wrapped transformer, simply
 *   cast to <code>O</code>
 * @param <X> intermediate: the type of object returned by the wrapped
 *   transformer
 * 
 * @see CastingTransformerWrapper
 * @see DowncastingTransformerWrapper
 */
public class UpcastingTransformerWrapper<I, O, X extends O>
	implements Transformer<I, O>
{
	private final Transformer<? super I, ? extends X> transformer;

	public UpcastingTransformerWrapper(Transformer<? super I, ? extends X> transformer) {
		super();
		if (transformer == null) {
			throw new NullPointerException();
		}
		this.transformer = transformer;
	}

	/**
	 * No need for casting as the type is guaranteed by the generic type
	 * argument.
	 */
	public O transform(I input) {
		return this.transformer.transform(input);
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.transformer);
	}
}

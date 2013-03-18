/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
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
 * Tranformer wrapper that can have its wrapped transformer changed,
 * allowing a client to change a previously-supplied transformer's
 * behavior mid-stream.
 * 
 * @param <I> input: the type of the object passed to the transformer
 * @param <O> output: the type of the object returned by the transformer
 * @see #setTransformer(Transformer)
 */
public class TransformerWrapper<I, O>
	implements Transformer<I, O>
{
	protected volatile Transformer<? super I, ? extends O> transformer;

	public TransformerWrapper(Transformer<? super I, ? extends O> transformer) {
		super();
		this.setTransformer(transformer);
	}

	public O transform(I input) {
		return this.transformer.transform(input);
	}

	public void setTransformer(Transformer<? super I, ? extends O> transformer) {
		if (transformer == null) {
			throw new NullPointerException();
		}
		this.transformer = transformer;
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.transformer);
	}
}

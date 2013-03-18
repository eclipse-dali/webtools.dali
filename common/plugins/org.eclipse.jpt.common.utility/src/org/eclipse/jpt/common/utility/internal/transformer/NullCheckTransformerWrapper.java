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
 * Tranformer wrapper that checks for <code>null</code> input before forwarding
 * the input to the wrapped transformer. If the input is <code>null</code>,
 * the transformer will return the configured output value.
 * 
 * @param <I> input: the type of the object passed to the transformer
 * @param <O> output: the type of the object returned by the transformer
 */
public class NullCheckTransformerWrapper<I, O>
	implements Transformer<I, O>
{
	private final Transformer<? super I, ? extends O> transformer;
	private final O nullOutput;

	public NullCheckTransformerWrapper(Transformer<? super I, ? extends O> transformer, O nullOutput) {
		super();
		if (transformer == null) {
			throw new NullPointerException();
		}
		this.transformer = transformer;
		this.nullOutput = nullOutput;
	}

	public O transform(I input) {
		return (input == null) ? this.nullOutput : this.transformer.transform(input);
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.transformer);
	}
}

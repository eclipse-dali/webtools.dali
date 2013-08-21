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
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * A chain of transformers. Pass the chain's input to the first
 * transformer; take that transformer's output and pass it to the following
 * transformer in the chain; etc. The output from the final transformer is
 * returned as the chain's output.
 * <p>
 * <strong>NB:</strong> The transformer's generic types are for convenience only
 * and cannot be enforced on the transformers in the chain.
 * 
 * @param <I> input: the type of the object passed to the transformer
 * @param <O> output: the type of the object returned by the transformer
 */
public class TransformerChain<I, O>
	implements Transformer<I, O>
{
	private final Iterable<Transformer<?, ?>> transformers;

	public TransformerChain(Iterable<Transformer<?, ?>> transformers) {
		super();
		if (IterableTools.isOrContainsNull(transformers)) {
			throw new NullPointerException();
		}
		this.transformers = transformers;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public O transform(I input) {
		Object result = input;
		for (Transformer transformer : this.transformers) {
			result = transformer.transform(result);
		}
		return (O) result;
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.transformers);
	}
}

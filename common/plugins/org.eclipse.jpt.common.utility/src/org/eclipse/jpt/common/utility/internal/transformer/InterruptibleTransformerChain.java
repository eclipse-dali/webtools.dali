/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.transformer;

import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.transformer.InterruptibleTransformer;

/**
 * @see TransformerChain
 */
public class InterruptibleTransformerChain<I, O>
	implements InterruptibleTransformer<I, O>
{
	private final Iterable<InterruptibleTransformer<?, ?>> transformers;

	public InterruptibleTransformerChain(Iterable<InterruptibleTransformer<?, ?>> transformers) {
		super();
		if (IterableTools.isOrContainsNull(transformers)) {
			throw new NullPointerException();
		}
		this.transformers = transformers;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public O transform(I input) throws InterruptedException {
		Object result = input;
		for (InterruptibleTransformer transformer : this.transformers) {
			result = transformer.transform(result);
		}
		return (O) result;
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.transformers);
	}
}

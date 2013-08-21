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
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.predicate.Predicate;

/**
 * Transformer that passes its input to a configured predicate to determine
 * which of its two transformers to execute.
 * 
 * @param <I> input: the type of the object passed to the transformer
 * @param <O> output: the type of the object returned by the transformer
 */
public class ConditionalTransformer<I, O>
	implements Transformer<I, O>
{
	private final Predicate<? super I> predicate;
	private final Transformer<? super I, ? extends O> trueTransformer;
	private final Transformer<? super I, ? extends O> falseTransformer;

	public ConditionalTransformer(Predicate<? super I> predicate, Transformer<? super I, ? extends O> trueTransformer, Transformer<? super I, ? extends O> falseTransformer) {
		super();
		if ((predicate == null) || (trueTransformer == null) || (falseTransformer == null)) {
			throw new NullPointerException();
		}
		this.predicate = predicate;
		this.trueTransformer = trueTransformer;
		this.falseTransformer = falseTransformer;
	}

	public O transform(I input) {
		return this.predicate.evaluate(input) ?
				this.trueTransformer.transform(input) :
				this.falseTransformer.transform(input);
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.predicate);
	}
}

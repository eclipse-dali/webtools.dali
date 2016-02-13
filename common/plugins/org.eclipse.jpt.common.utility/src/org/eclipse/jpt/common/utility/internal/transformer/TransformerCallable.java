/*******************************************************************************
 * Copyright (c) 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.transformer;

import java.util.concurrent.Callable;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.transformer.InterruptibleTransformer;

/**
 * Adapter class that adapts a {@link InterruptibleTransformer transformer}
 * and an appropriate input to the {@Callable} interface.
 * 
 * @param <I> input: the type of the object passed to the transformer
 * @param <O> output: the type of the object returned by the transformer
 * (and the callable)
 */
public class TransformerCallable<I, O>
	implements Callable<O>
{
	private final InterruptibleTransformer<? super I, ? extends O> transformer;
	private final I input;


	public TransformerCallable(InterruptibleTransformer<? super I, ? extends O> transformer, I input) {
		super();
		if (transformer == null) {
			throw new NullPointerException();
		}
		this.transformer = transformer;
		this.input = input;
	}

	public O call() throws InterruptedException {
		return this.transformer.transform(this.input);
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.transformer);
	}
}

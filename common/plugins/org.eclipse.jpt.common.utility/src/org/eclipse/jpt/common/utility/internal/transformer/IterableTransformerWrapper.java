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

import java.util.Iterator;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * Wrap a transformer that converts an input object into an <em>iterable</em>
 * of objects of the same type as the input object, converting that transformer
 * into one that converts the same object into an <em>iterator</em>
 * of objects of the same type as the input object.
 * 
 * @param <I> input: the type of the object passed to the transformer; also the
 *   type of object returned by the output iterator
 */
public class IterableTransformerWrapper<I>
	implements Transformer<I, Iterator<? extends I>>
{
	private final Transformer<? super I, ? extends Iterable<? extends I>> transformer;


	public IterableTransformerWrapper(Transformer<? super I, ? extends Iterable<? extends I>> transformer) {
		super();
		if (transformer == null) {
			throw new NullPointerException();
		}
		this.transformer = transformer;
	}

	public Iterator<? extends I> transform(I input) {
		return this.transformer.transform(input).iterator();
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.transformer);
	}
}

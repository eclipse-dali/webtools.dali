/*******************************************************************************
 * Copyright (c) 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.transformer.int_;

import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.transformer.IntTransformer;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * Adapt an <code>int</code> transformer to the standard object transformer
 * interface.
 * 
 * @param <I> input: the type of the object passed to the transformer
 * 
 * @see IntTransformer
 */
public class IntObjectTransformerAdapter<I>
	implements Transformer<I, Integer>
{
	private final IntTransformer<? super I> intTransformer;

	public IntObjectTransformerAdapter(IntTransformer<? super I> intTransformer) {
		super();
		if (intTransformer == null) {
			throw new NullPointerException();
		}
		this.intTransformer = intTransformer;
	}

	public Integer transform(I input) {
		return Integer.valueOf(this.intTransformer.transform(input));
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.intTransformer);
	}
}

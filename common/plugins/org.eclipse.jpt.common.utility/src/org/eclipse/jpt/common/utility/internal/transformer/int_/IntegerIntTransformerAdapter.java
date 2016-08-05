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
 * Adapt an {@link Integer} transformer to the <code>int</code> transformer
 * interface.
 * 
 * @param <I> input: the type of the object passed to the transformer
 * 
 * @see Transformer
 */
public class IntegerIntTransformerAdapter<I>
	implements IntTransformer<I>
{
	private final Transformer<? super I, Integer> transformer;

	public IntegerIntTransformerAdapter(Transformer<? super I, Integer> transformer) {
		super();
		if (transformer == null) {
			throw new NullPointerException();
		}
		this.transformer = transformer;
	}

	public int transform(I input) {
		return this.transformer.transform(input).intValue();
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.transformer);
	}
}

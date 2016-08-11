/*******************************************************************************
 * Copyright (c) 2012, 2016 Oracle. All rights reserved.
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
 * Convenience transformer that returns <code>null</code> for every
 * transformation.
 * 
 * @param <I> input: the type of the object passed to the transformer
 * @param <O> output: the type of the object returned by the transformer
 * 
 * @see TransformerTools#nullOutputTransformer()
 */
public class TransformerAdapter<I, O>
	implements Transformer<I, O>
{
	/**
	 * It is possible the specified input is <code>null</code>.
	 */
	public O transform(I input) {
		return null;
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this);
	}
}

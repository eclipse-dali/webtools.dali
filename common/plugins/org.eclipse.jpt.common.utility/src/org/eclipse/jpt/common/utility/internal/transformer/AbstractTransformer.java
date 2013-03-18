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

/**
 * Convenience superclass that returns <code>null</code> if the original
 * object is <code>null</code>; otherwise it calls {@link #transform_(Object)},
 * which is to be implemented by subclasses.
 * 
 * @param <I> input: the type of the object passed to the transformer
 * @param <O> output: the type of the object returned by the transformer
 * 
 * @see TransformerAdapter
 * @see NullCheckTransformerWrapper
 */
public abstract class AbstractTransformer<I, O>
	extends TransformerAdapter<I, O>
{
	@Override
	public final O transform(I input) {
		return (input == null) ? null : this.transform_(input);
	}

	/**
	 * Transform the specified input; its value is guaranteed to be not
	 * <code>null</code>.
	 */
	protected abstract O transform_(I input);
}

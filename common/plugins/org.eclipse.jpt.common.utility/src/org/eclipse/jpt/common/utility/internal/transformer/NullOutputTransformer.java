/*******************************************************************************
 * Copyright (c) 2005, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.transformer;

import java.io.Serializable;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * A transformer that will transform every input into <code>null</code>.
 * 
 * @param <I> input: the type of the object passed to the transformer
 * @param <O> output: the type of the object returned by the transformer
 * 
 * @see AbstractTransformer
 * @see TransformerAdapter
 */
public final class NullOutputTransformer<I, O>
	implements Transformer<I, O>, Serializable
{
	@SuppressWarnings("rawtypes")
	public static final Transformer INSTANCE = new NullOutputTransformer();

	@SuppressWarnings("unchecked")
	public static <I, O> Transformer<I, O> instance() {
		return INSTANCE;
	}

	// ensure single instance
	private NullOutputTransformer() {
		super();
	}

	/**
	 * Return <code>null</code>.
	 */
	public O transform(I input) {
		return null;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}

	private static final long serialVersionUID = 1L;
	private Object readResolve() {
		// replace this object with the singleton
		return INSTANCE;
	}
}

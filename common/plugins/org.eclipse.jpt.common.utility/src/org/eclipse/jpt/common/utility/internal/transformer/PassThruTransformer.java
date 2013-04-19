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

import java.io.Serializable;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * A transformer that will perform no transformation at all;
 * it will simply return the input.
 * 
 * @param <I> input: the type of the object passed to the transformer
 */
public final class PassThruTransformer<I>
	implements Transformer<I, I>, Serializable
{
	@SuppressWarnings("rawtypes")
	public static final Transformer INSTANCE = new PassThruTransformer();

	@SuppressWarnings("unchecked")
	public static <I> Transformer<I, I> instance() {
		return INSTANCE;
	}

	// ensure single instance
	private PassThruTransformer() {
		super();
	}

	/**
	 * Return the specified input, unchanged.
	 */
	public I transform(I input) {
		return input;
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

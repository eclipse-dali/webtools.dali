/*******************************************************************************
 * Copyright (c) 2005, 2013 Oracle. All rights reserved.
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
 * A transformer that will throw an {@link UnsupportedOperationException exception}
 * if {@link #transform(Object)} is called. This is useful in situations
 * where a transformer is optional and the default transformer should
 * not be used.
 * @param <I> input: the type of the object passed to the transformer
 * @param <O> output: the type of the object (never) returned by the transformer
 */
public final class DisabledTransformer<I, O>
	implements Transformer<I, O>, Serializable
{
	@SuppressWarnings("rawtypes")
	public static final Transformer INSTANCE = new DisabledTransformer();

	@SuppressWarnings("unchecked")
	public static <I, O> Transformer<I, O> instance() {
		return INSTANCE;
	}

	// ensure single instance
	private DisabledTransformer() {
		super();
	}

	/**
	 * Throw an {@link UnsupportedOperationException exception}.
	 */
	public O transform(I input) {
		throw new UnsupportedOperationException();
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

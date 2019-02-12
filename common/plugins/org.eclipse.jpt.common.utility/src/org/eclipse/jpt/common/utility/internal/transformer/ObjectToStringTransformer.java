/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
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
 * Transform a non-<code>null</code> object into the string returned by its
 * {@link Object#toString()} method. A <code>null</code> input will trigger
 * a {@link NullPointerException}.
 * 
 * @param <I> input: the type of the object passed to the transformer
 */
public final class ObjectToStringTransformer<I>
	implements Transformer<I, String>, Serializable
{
	@SuppressWarnings("rawtypes")
	public static final Transformer INSTANCE = new ObjectToStringTransformer();

	@SuppressWarnings("unchecked")
	public static <I> Transformer<I, String> instance() {
		return INSTANCE;
	}

	// ensure single instance
	private ObjectToStringTransformer() {
		super();
	}

	/**
	 * Return the specified input's {@link Object#toString() string
	 * representation}.
	 */
	public String transform(I input) {
		return input.toString();
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

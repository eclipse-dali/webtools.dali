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

import java.io.Serializable;
import java.util.List;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * Transformer that transforms a list into its last element.
 * If the list is empty, the transformer throws an {@link IndexOutOfBoundsException}.
 * 
 * @param <E> the type of elements held by the list
 */
public final class ListLastElementTransformer_<E>
	implements Transformer<List<? extends E>, E>, Serializable
{
	@SuppressWarnings("rawtypes")
	public static final Transformer INSTANCE = new ListLastElementTransformer_();

	@SuppressWarnings("unchecked")
	public static <E> Transformer<List<? extends E>, E> instance() {
		return INSTANCE;
	}

	// ensure single instance
	private ListLastElementTransformer_() {
		super();
	}

	public E transform(List<? extends E> list) {
		return list.get(list.size() - 1);
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
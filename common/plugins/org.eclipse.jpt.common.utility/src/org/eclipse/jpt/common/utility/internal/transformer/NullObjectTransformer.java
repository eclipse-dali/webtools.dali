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

import java.io.Serializable;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * A {@link NullObjectTransformer} will transform an object to a
 * {@link Boolean}:<ul>
 * <li>If the object is <code>null</code>,
 * the transformer will return {@link Boolean#TRUE}.
 * <li>If the object is <em>not</em> <code>null</code>,
 * the transformer will return {@link Boolean#FALSE}.
 * </ul>
 * 
 * @param <I> input: the type of the object passed to the transformer
 */
public class NullObjectTransformer<I>
	implements Transformer<I, Boolean>, Serializable
{
	@SuppressWarnings("rawtypes")
	public static final Transformer INSTANCE = new NullObjectTransformer();

	@SuppressWarnings("unchecked")
	public static <S> Transformer<S, Boolean> instance() {
		return INSTANCE;
	}

	// ensure single instance
	private NullObjectTransformer() {
		super();
	}

	public Boolean transform(I o) {
		return Boolean.valueOf(o == null);
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

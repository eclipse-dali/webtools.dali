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
 * A transformer that will transform an object to a
 * {@link Boolean}:<ul>
 * <li>If the object is <code>null</code>,
 * the transformer will return {@link Boolean#TRUE}.
 * <li>If the object is <em>not</em> <code>null</code>,
 * the transformer will return {@link Boolean#FALSE}.
 * </ul>
 * 
 * @param <I> input: the type of the object passed to the transformer
 * @see IsNotNullTransformer
 */
public final class IsNullTransformer<I>
	implements Transformer<I, Boolean>, Serializable
{
	@SuppressWarnings("rawtypes")
	public static final Transformer INSTANCE = new IsNullTransformer();

	@SuppressWarnings("unchecked")
	public static <I> Transformer<I, Boolean> instance() {
		return INSTANCE;
	}

	// ensure single instance
	private IsNullTransformer() {
		super();
	}

	public Boolean transform(I input) {
		return Boolean.valueOf(input == null);
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

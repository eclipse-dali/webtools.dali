/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.transformer;

import java.io.Serializable;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * A transformer that will clone (via reflection) the input.
 * @param <I> input: the type of the object passed to the transformer
 *   (and returned by the transformer)
 * @see Cloneable
 * @see Object#clone()
 */
public final class CloneTransformer<I extends Cloneable>
	implements Transformer<I, I>, Serializable
{
	@SuppressWarnings("rawtypes")
	public static final Transformer INSTANCE = new CloneTransformer();

	@SuppressWarnings("unchecked")
	public static <I extends Cloneable> Transformer<I, I> instance() {
		return INSTANCE;
	}

	// ensure single instance
	private CloneTransformer() {
		super();
	}

	/**
	 * Clone the input via reflection.
	 */
	@SuppressWarnings("unchecked")
	public I transform(I input) {
		return (I) ObjectTools.execute(input, "clone"); //$NON-NLS-1$
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

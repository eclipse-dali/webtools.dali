/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal;

import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * Straightforward definition of an object pairing.
 * The left and right values are immutable.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @param <L> the type of the pair's left value
 * @param <R> the type of the pair's right value
 * @see org.eclipse.jpt.common.utility.Association
 */
public interface Pair<L, R> {

	/**
	 * Return the pair's left value.
	 */
	L getLeft();

	@SuppressWarnings("rawtypes")
	Transformer LEFT_TRANSFORMER = new LeftTransformer();
	class LeftTransformer<L, R>
		implements Transformer<Pair<L, R>, L>
	{
		public L transform(Pair<L, R> pair) {
			return pair.getLeft();
		}
		@Override
		public String toString() {
			return ObjectTools.singletonToString(this);
		}
	}

	/**
	 * Return the pair's right value.
	 */
	R getRight();

	@SuppressWarnings("rawtypes")
	Transformer RIGHT_TRANSFORMER = new RightTransformer();
	class RightTransformer<L, R>
		implements Transformer<Pair<L, R>, R>
	{
		public R transform(Pair<L, R> pair) {
			return pair.getRight();
		}
		@Override
		public String toString() {
			return ObjectTools.singletonToString(this);
		}
	}

	/**
	 * Return whether the pairs' left and right values
	 * are equal.
	 */
	boolean equals(Object o);

	/**
	 * Return a hash code that is a XOR of the hash codes of the pair's
	 * left and right values.
	 */
	int hashCode();
}

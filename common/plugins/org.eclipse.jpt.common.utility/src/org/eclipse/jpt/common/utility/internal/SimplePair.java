/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal;

import org.eclipse.jpt.common.utility.Pair;

/**
 * Straightforward implementation of the {@link Pair} interface.
 * 
 * @param <L> the type of the pair's left value
 * @param <R> the type of the pair's right value
 */
public class SimplePair<L, R>
	extends AbstractPair<L, R>
{
	private final L left;
	private final R right;

	public SimplePair(L left, R right) {
		super();
		this.left = left;
		this.right = right;
	}

	public L getLeft() {
		return this.left;
	}

	public R getRight() {
		return this.right;
	}
}

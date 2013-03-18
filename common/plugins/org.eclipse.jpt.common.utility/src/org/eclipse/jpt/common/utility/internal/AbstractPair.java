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

/**
 * Implement some of the methods in {@link Pair} that can
 * be defined in terms of the other methods.
 */
public abstract class AbstractPair<L, R>
	implements Pair<L, R>
{
	/**
	 * Default constructor.
	 */
	protected AbstractPair() {
		super();
	}

	@Override
	public synchronized boolean equals(Object o) {
		if ( ! (o instanceof Pair<?, ?>)) {
			return false;
		}
		Pair<?, ?> other = (Pair<?, ?>) o;
		return this.leftEquals(other) && this.rightEquals(other);
	}

	protected boolean leftEquals(Pair<?, ?> other) {
		Object left = this.getLeft();
		return (left == null) ?
				(other.getLeft() == null) :
				left.equals(other.getLeft());
	}

	protected boolean rightEquals(Pair<?, ?> other) {
		Object right = this.getRight();
		return (right == null) ?
				(other.getRight() == null) :
				right.equals(other.getRight());
	}

	@Override
	public synchronized int hashCode() {
		return this.leftHashCode() ^ this.rightHashCode();
	}

	protected int leftHashCode() {
		Object left = this.getLeft();
		return (left == null) ? 0 : left.hashCode();
	}

	protected int rightHashCode() {
		Object right = this.getRight();
		return (right == null) ? 0 : right.hashCode();
	}

	@Override
	public synchronized String toString() {
		return this.getLeft() + "/" + this.getRight(); //$NON-NLS-1$
	}
}

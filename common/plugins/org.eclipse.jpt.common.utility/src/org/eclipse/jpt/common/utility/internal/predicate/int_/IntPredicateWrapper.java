/*******************************************************************************
 * Copyright (c) 2012, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.predicate.int_;

import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.predicate.IntPredicate;

/**
 * IntPredicate wrapper that can have its wrapped predicate changed,
 * allowing a client to change a previously-supplied predicate's
 * behavior mid-stream.
 * 
 * @see #setPredicate(IntPredicate)
 */
public class IntPredicateWrapper
	implements IntPredicate
{
	protected volatile IntPredicate predicate;


	public IntPredicateWrapper(IntPredicate predicate) {
		super();
		this.setPredicate(predicate);
	}

	public boolean evaluate(int variable) {
		return this.predicate.evaluate(variable);
	}

	public void setPredicate(IntPredicate predicate) {
		if (predicate == null) {
			throw new NullPointerException();
		}
		this.predicate = predicate;
	}

	@Override
	public boolean equals(Object o) {
		if ( ! (o instanceof IntPredicateWrapper)) {
			return false;
		}
		IntPredicateWrapper other = (IntPredicateWrapper) o;
		return this.predicate.equals(other.predicate);
	}

	@Override
	public int hashCode() {
		return this.predicate.hashCode();
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.predicate);
	}
}

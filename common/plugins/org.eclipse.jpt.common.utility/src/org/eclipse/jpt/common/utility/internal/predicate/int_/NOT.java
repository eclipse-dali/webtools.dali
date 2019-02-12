/*******************************************************************************
 * Copyright (c) 2005, 2015 Oracle. All rights reserved.
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
 * This predicate will return the NOT of the value returned by its
 * wrapped predicate.
 * 
 * @see AND
 * @see OR
 * @see XOR
 */
public class NOT
	implements IntPredicate
{
	protected final IntPredicate predicate;


	/**
	 * Construct a predicate that will return the NOT of the value returned
	 * by the specified predicate.
	 */
	public NOT(IntPredicate predicate) {
		super();
		if (predicate == null) {
			throw new NullPointerException();
		}
		this.predicate = predicate;
	}

	public boolean evaluate(int variable) {
		return ! this.predicate.evaluate(variable);
	}

	public IntPredicate getIntPredicate() {
		return this.predicate;
	}

	@Override
	public boolean equals(Object o) {
		if ( ! (o instanceof NOT)) {
			return false;
		}
		NOT other = (NOT) o;
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

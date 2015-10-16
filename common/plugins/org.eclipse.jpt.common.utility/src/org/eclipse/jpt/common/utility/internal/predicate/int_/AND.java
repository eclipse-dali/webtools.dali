/*******************************************************************************
 * Copyright (c) 2005, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.predicate.int_;

import org.eclipse.jpt.common.utility.predicate.IntPredicate;

/**
 * This compound predicate will evaluate to <code>true</code> if <em>all</em>
 * its wrapped predicates evaluate to <code>true</code>.
 * If there are <em>no</em> wrapped
 * predicates, this predicate will always evaluate to <code>true</code>.
 * If there are wrapped predicates, this predicate will
 * exhibit "short-circuit" behavior; i.e. if any wrapped predicate evaluates
 * to <code>false</code>, no following wrapped predicates will be evaluated.
 * 
 * @see OR
 * @see XOR
 * @see NOT
 */
public class AND
	extends AbstractCompoundIntPredicate
{
	/**
	 * Construct a predicate that will evaluate to <code>true</code> if <em>all</em>
	 * the specified predicates evaluate to <code>true</code>.
	 */
	@SafeVarargs
	public AND(IntPredicate... predicates) {
		super(predicates);
	}

	public boolean evaluate(int variable) {
		for (IntPredicate predicate : this.predicates) {
			if ( ! predicate.evaluate(variable)) {
				return false;
			}
		}
		return true;
	}

	@Override
	protected String operatorString() {
		return "AND"; //$NON-NLS-1$
	}
}

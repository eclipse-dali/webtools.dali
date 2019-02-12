/*******************************************************************************
 * Copyright (c) 2005, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.predicate;

import org.eclipse.jpt.common.utility.predicate.Predicate;

/**
 * This compound predicate will evaluate to <code>true</code> if <em>all</em>
 * its wrapped predicates evaluate to <code>true</code>.
 * If there are <em>no</em> wrapped
 * predicates, this predicate will always evaluate to <code>true</code>.
 * If there are wrapped predicates, this predicate will
 * exhibit "short-circuit" behavior; i.e. if any wrapped predicate evaluates
 * to <code>false</code>, no following wrapped predicates will be evaluated.
 * 
 * @param <V> the type of objects to be evaluated by the predicate
 * @see OR
 * @see XOR
 * @see NOT
 */
public class AND<V>
	extends AbstractCompoundPredicate<V>
{
	/**
	 * Construct a predicate that will evaluate to <code>true</code> if <em>all</em>
	 * the specified predicates evaluate to <code>true</code>.
	 */
	@SafeVarargs
	public AND(Predicate<? super V>... predicates) {
		super(predicates);
	}

	public boolean evaluate(V variable) {
		for (Predicate<? super V> predicate : this.predicates) {
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

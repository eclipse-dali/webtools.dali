/*******************************************************************************
 * Copyright (c) 2013, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.predicate.int_;

/**
 * This predicate evaluates to <code>true</code> if the variable is
 * greater than the criterion specified during construction.
 */
public class IsGreaterThan
	extends IntCriterionIntPredicate
{
	/**
	 * Construct a predicate that will evaluate to <code>true</code> if the
	 * variable greater than the specified criterion.
	 */
	public IsGreaterThan(int criterion) {
		super(criterion);
	}

	public boolean evaluate(int variable) {
		return variable > this.criterion;
	}
}

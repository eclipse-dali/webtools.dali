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
 * This compound predicate will evaluate to <code>true</code> if either of
 * its wrapped predicates evaluates to <code>true</code>, but <em>not</em> both.
 * Both predicates will <em>always</em> be evaluated.
 * 
 * @see AND
 * @see OR
 * @see NOT
 */
public class XOR
	extends AbstractCompoundIntPredicate
{
	/**
	 * Construct a predicate that will evaluate to <code>true</code> if either of
	 * the specified predicates evaluates to <code>true</code>, but <em>not</em> both.
	 */
	public XOR(IntPredicate predicate1, IntPredicate predicate2) {
		super(predicate1, predicate2);
	}

	public boolean evaluate(int variable) {
		return this.predicates[0].evaluate(variable) ^ this.predicates[1].evaluate(variable);
	}

	@Override
	protected String operatorString() {
		return "XOR"; //$NON-NLS-1$
	}
}

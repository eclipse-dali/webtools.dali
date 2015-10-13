/*******************************************************************************
 * Copyright (c) 2005, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.predicate;

import org.eclipse.jpt.common.utility.predicate.Predicate;

/**
 * This compound predicate will evaluate to <code>true</code> if either of
 * its wrapped predicates evaluates to <code>true</code>, but <em>not</em> both.
 * Both predicates will <em>always</em> be evaluated.
 * 
 * @param <V> the type of objects to be evaluated by the predicate
 * @see AND
 * @see OR
 * @see NOT
 */
public class XOR<V>
	extends AbstractCompoundPredicate<V>
{
	/**
	 * Construct a predicate that will evaluate to <code>true</code> if either of
	 * the specified predicates evaluates to <code>true</code>, but <em>not</em> both.
	 */
	public XOR(Predicate<? super V> predicate1, Predicate<? super V> predicate2) {
		super(predicate1, predicate2);
	}

	public boolean evaluate(V variable) {
		return this.predicates[0].evaluate(variable) ^ this.predicates[1].evaluate(variable);
	}

	@Override
	protected String operatorString() {
		return "XOR"; //$NON-NLS-1$
	}
}

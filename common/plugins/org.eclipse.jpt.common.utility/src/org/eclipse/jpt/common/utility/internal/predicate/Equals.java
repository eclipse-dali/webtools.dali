/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.predicate;

import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * This predicate evaluates to <code>true</code> if the variable is
 * {@link Object#equals(Object) equal to} the criterion specified during
 * construction. If the criterion is <code>null</code>, the predicate
 * will evaluate to <code>true</code> if the variable is also
 * <code>null</code>.
 * 
 * @param <V> the type of objects to be evaluated by the predicate
 * @see IsIdentical
 * @see IsNotNull
 * @see IsNull
 */
public class Equals<V>
	extends CriterionPredicate<V, V>
{
	/**
	 * Construct a predicate that will evaluate to <code>true</code> if the
	 * variable is <code>null</code>.
	 */
	public Equals() {
		this(null);
	}

	/**
	 * Construct a predicate that will evaluate to <code>true</code> if the
	 * variable is {@link Object#equals(Object) equal to} the
	 * specified criterion.
	 */
	public Equals(V criterion) {
		super(criterion);
	}

	public boolean evaluate(V variable) {
		return ObjectTools.equals(variable, this.criterion);
	}
}

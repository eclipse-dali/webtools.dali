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

import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.predicate.IntPredicate;

/**
 * Simple, abstract implementation of {@link IntPredicate}
 * that holds on to a criterion object that can be used in the
 * {@link #evaluate(int) evaluate} method.
 * <p>
 * This class simplifies the implementation of straightforward inner classes.
 */
public abstract class IntCriterionIntPredicate
	implements IntPredicate
{
	protected final int criterion;


	/**
	 * More useful constructor. The specified criterion can
	 * be used by a subclass to evaluate objects.
	 */
	protected IntCriterionIntPredicate(int criterion) {
		super();
		this.criterion = criterion;
	}

	/**
	 * Return the predicate's criterion.
	 */
	public int getCriterion() {
		return this.criterion;
	}

	@Override
	public boolean equals(Object o) {
		if (o.getClass() != this.getClass()) {
			return false;
		}
		IntCriterionIntPredicate other = (IntCriterionIntPredicate) o;
		return this.criterion == other.criterion;
	}

	@Override
	public int hashCode() {
		return this.criterion;
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.criterion);
	}
}

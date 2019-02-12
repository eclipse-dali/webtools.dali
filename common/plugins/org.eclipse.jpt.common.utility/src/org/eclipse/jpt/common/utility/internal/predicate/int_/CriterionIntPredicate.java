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
 * Simple, abstract implementation of {@link IntPredicate}
 * that holds on to a criterion object that can be used in the
 * {@link #evaluate(int) evaluate} method.
 * <p>
 * This class simplifies the implementation of straightforward inner classes.
 * 
 * @param <C> the type of the filter's criterion
 */
public abstract class CriterionIntPredicate<C>
	implements IntPredicate
{
	protected final C criterion;


	/**
	 * Construct a simple predicate with a <code>null</code> criterion.
	 */
	protected CriterionIntPredicate() {
		this(null);
	}

	/**
	 * More useful constructor. The specified criterion can
	 * be used by a subclass to evaluate objects.
	 */
	protected CriterionIntPredicate(C criterion) {
		super();
		this.criterion = criterion;
	}

	/**
	 * Return the predicate's criterion.
	 */
	public C getCriterion() {
		return this.criterion;
	}

	@Override
	public boolean equals(Object o) {
		if (o.getClass() != this.getClass()) {
			return false;
		}
		CriterionIntPredicate<?> other = (CriterionIntPredicate<?>) o;
		return ObjectTools.equals(this.criterion, other.criterion);
	}

	@Override
	public int hashCode() {
		return (this.criterion == null) ? 0 : this.criterion.hashCode();
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.criterion);
	}
}

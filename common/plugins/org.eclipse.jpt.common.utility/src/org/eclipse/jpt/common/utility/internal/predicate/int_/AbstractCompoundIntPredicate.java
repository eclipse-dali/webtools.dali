/*******************************************************************************
 * Copyright (c) 2005, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.predicate.int_;

import java.util.Arrays;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.StringBuilderTools;
import org.eclipse.jpt.common.utility.predicate.CompoundIntPredicate;
import org.eclipse.jpt.common.utility.predicate.IntPredicate;

/**
 * This class provides a simple framework for combining the behavior
 * of multiple predicates.
 */
public abstract class AbstractCompoundIntPredicate
	implements CompoundIntPredicate
{
	protected final IntPredicate[] predicates;


	/**
	 * Construct a compound predicate for the specified list of predicates.
	 */
	@SafeVarargs
	protected AbstractCompoundIntPredicate(IntPredicate... predicates) {
		super();
		if (ArrayTools.isOrContainsNull(predicates)) {
			throw new NullPointerException();
		}
		this.predicates = predicates;
	}

	/**
	 * Return the predicates.
	 */
	public IntPredicate[] getPredicates() {
		return this.predicates;
	}

	@Override
	public boolean equals(Object o) {
		if ((o == null) || (o.getClass() != this.getClass())) {
			return false;
		}
		AbstractCompoundIntPredicate other = (AbstractCompoundIntPredicate) o;
		return Arrays.equals(this.predicates, other.predicates);
	}

	@Override
	public int hashCode() {
		return Arrays.hashCode(this.predicates);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		StringBuilderTools.appendHashCodeToString(sb, this);
		sb.append('(');
		if (this.predicates.length > 0) {
			String operatorString = this.operatorString();
			for (IntPredicate predicate : this.predicates) {
				sb.append(predicate);
				sb.append(' ');
				sb.append(operatorString);
				sb.append(' ');
			}
			sb.setLength(sb.length() - operatorString.length() - 2);
		}
		sb.append(')');
		return sb.toString();
	}

	/**
	 * Return a string representation of the compound predicate's operator.
	 * Used by {@link #toString()}.
	 */
	protected abstract String operatorString();
}

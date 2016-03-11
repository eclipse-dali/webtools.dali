/*******************************************************************************
 * Copyright (c) 2005, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.predicate;

import java.util.Arrays;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.StringBuilderTools;
import org.eclipse.jpt.common.utility.predicate.CompoundPredicate;
import org.eclipse.jpt.common.utility.predicate.Predicate;

/**
 * This class provides a simple framework for combining the behavior
 * of multiple predicates.
 * 
 * @param <V> the type of objects to be evaluated by the predicate
 */
public abstract class AbstractCompoundPredicate<V>
	implements CompoundPredicate<V>
{
	protected final Predicate<? super V>[] predicates;


	/**
	 * Construct a compound predicate for the specified list of predicates.
	 */
	@SafeVarargs
	protected AbstractCompoundPredicate(Predicate<? super V>... predicates) {
		super();
		if (ArrayTools.isOrContainsNull(predicates)) {
			throw new NullPointerException();
		}
		this.predicates = predicates;
	}

	/**
	 * Return the predicates.
	 */
	public Predicate<? super V>[] getPredicates() {
		return this.predicates;
	}

	@Override
	public boolean equals(Object o) {
		if ((o == null) || (o.getClass() != this.getClass())) {
			return false;
		}
		@SuppressWarnings("unchecked")
		AbstractCompoundPredicate<V> other = (AbstractCompoundPredicate<V>) o;
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
			for (Predicate<? super V> predicate : this.predicates) {
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

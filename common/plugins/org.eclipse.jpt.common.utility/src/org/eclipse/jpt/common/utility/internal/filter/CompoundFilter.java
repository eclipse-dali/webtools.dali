/*******************************************************************************
 * Copyright (c) 2005, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.filter;

import java.io.Serializable;
import java.util.Arrays;
import org.eclipse.jpt.common.utility.internal.StringBuilderTools;
import org.eclipse.jpt.common.utility.predicate.Predicate;

/**
 * This filter provides a simple framework for combining the behavior
 * of multiple filters.
 * 
 * @param <T> the type of objects to be filtered
 */
public abstract class CompoundFilter<T>
	implements Predicate<T>, Cloneable, Serializable
{
	protected Predicate<? super T>[] filters;

	private static final long serialVersionUID = 1L;


	/**
	 * Construct a compound filter for the specified list of filters.
	 */
	protected CompoundFilter(Predicate<? super T>... filters) {
		super();
		if (filters == null) {
			throw new NullPointerException();
		}
		this.filters = filters;
	}

	/**
	 * Return the filters.
	 */
	public Predicate<? super T>[] getFilters() {
		return this.filters;
	}

	@Override
	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException ex) {
			throw new InternalError();
		}
	}

	@Override
	public boolean equals(Object o) {
		if ( ! (o instanceof CompoundFilter)) {
			return false;
		}
		@SuppressWarnings("unchecked")
		CompoundFilter<T> other = (CompoundFilter<T>) o;
		return Arrays.equals(this.filters, other.filters);
	}

	@Override
	public int hashCode() {
		return Arrays.hashCode(this.filters);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		StringBuilderTools.appendHashCodeToString(sb, this);
		sb.append('(');
		for (Predicate<? super T> filter : this.filters) {
			sb.append(filter);
			sb.append(' ');
			sb.append(this.operatorString());
			sb.append(' ');
		}
		if (this.filters.length > 0) {
			sb.setLength(sb.length() - this.operatorString().length() - 2);
		}
		sb.append(')');
		return sb.toString();
	}

	/**
	 * Return a string representation of the compound filter's operator.
	 * Used by {@link #toString()}.
	 */
	protected abstract String operatorString();
}

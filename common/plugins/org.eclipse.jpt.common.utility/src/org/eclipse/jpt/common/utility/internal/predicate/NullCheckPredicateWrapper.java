/*******************************************************************************
 * Copyright (c) 2013, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.predicate;

import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.predicate.Predicate;

/**
 * Predicate wrapper that checks for a <code>null</code> variable before
 * forwarding the variable to the wrapped predicate. If the variable is
 * <code>null</code>, the predicate will return the configured result value.
 * 
 * @param <V> the type of the object passed to the predicate
 */
public class NullCheckPredicateWrapper<V>
	implements Predicate<V>
{
	private final Predicate<? super V> predicate;
	private final boolean nullValue;


	public NullCheckPredicateWrapper(Predicate<? super V> predicate, boolean nullValue) {
		super();
		if (predicate == null) {
			throw new NullPointerException();
		}
		this.predicate = predicate;
		this.nullValue = nullValue;
	}

	public boolean evaluate(V variable) {
		return (variable == null) ? this.nullValue : this.predicate.evaluate(variable);
	}

	public Predicate<? super V> getPredicate() {
		return this.predicate;
	}

	public boolean getNullValue() {
		return this.nullValue;
	}

	@Override
	public boolean equals(Object o) {
		if ( ! (o instanceof NullCheckPredicateWrapper)) {
			return false;
		}
		@SuppressWarnings("unchecked")
		NullCheckPredicateWrapper<V> other = (NullCheckPredicateWrapper<V>) o;
		return this.predicate.equals(other.predicate) && (this.nullValue == other.nullValue);
	}

	@Override
	public int hashCode() {
		return this.predicate.hashCode() ^ Boolean.valueOf(this.nullValue).hashCode();
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.nullValue);
	}
}

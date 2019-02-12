/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.predicate;

import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.predicate.Predicate;

/**
 * Predicate wrapper that can have its wrapped predicate changed,
 * allowing a client to change a previously-supplied predicate's
 * behavior mid-stream.
 * 
 * @param <V> the type of objects to be evaluated by the predicate
 * @see #setPredicate(Predicate)
 */
public class PredicateWrapper<V>
	implements Predicate<V>
{
	protected volatile Predicate<? super V> predicate;


	public PredicateWrapper(Predicate<? super V> predicate) {
		super();
		this.setPredicate(predicate);
	}

	public boolean evaluate(V variable) {
		return this.predicate.evaluate(variable);
	}

	public void setPredicate(Predicate<? super V> predicate) {
		if (predicate == null) {
			throw new NullPointerException();
		}
		this.predicate = predicate;
	}

	@Override
	public boolean equals(Object o) {
		if ( ! (o instanceof PredicateWrapper)) {
			return false;
		}
		@SuppressWarnings("unchecked")
		PredicateWrapper<V> other = (PredicateWrapper<V>) o;
		return this.predicate.equals(other.predicate);
	}

	@Override
	public int hashCode() {
		return this.predicate.hashCode();
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.predicate);
	}
}

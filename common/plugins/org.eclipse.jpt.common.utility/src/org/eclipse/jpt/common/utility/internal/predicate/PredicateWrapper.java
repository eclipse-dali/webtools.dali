/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.predicate;

import java.io.Serializable;
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
	implements Predicate<V>, Cloneable, Serializable
{
	protected volatile Predicate<? super V> predicate;

	private static final long serialVersionUID = 1L;


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
	public PredicateWrapper<V> clone() {
		try {
			@SuppressWarnings("unchecked")
			PredicateWrapper<V> clone = (PredicateWrapper<V>) super.clone();
			return clone;
		} catch (CloneNotSupportedException ex) {
			throw new InternalError();
		}
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

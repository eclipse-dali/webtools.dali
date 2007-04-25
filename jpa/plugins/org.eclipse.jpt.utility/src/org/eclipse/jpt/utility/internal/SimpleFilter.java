/*******************************************************************************
 * Copyright (c) 2005, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal;

import java.io.Serializable;

/**
 * Simple, abstract implementation of <code>Filter</code>
 * that holds on to a criterion object that can be used in the
 * <code>accept(Object)</code> or <code>reject(Object)</code>
 * methods. Subclasses can override either of these methods,
 * depending on which is easier to implement. Note that at least
 * one of these methods <em>must</em> be overridden or
 * an infinite loop will occur. If both of them are overridden,
 * only the <code>accept(Object)</code> method will be used.
 * <p>
 * Simplifies the implementation of straightforward inner classes.
 * Here is an example of a filter that can be used by a
 * <code>FilteringIterator</code> to return only those strings
 * in the nested iterator start with "prefix":
 * <pre>
 *	Filter<String> filter = new SimpleFilter<String>("prefix") {
 *		public boolean accept(String o) {
 *			return o.startsWith((String) criterion);
 *		}
 *	};
 * </pre>
 */
public abstract class SimpleFilter<T, S>
	implements Filter<T>, Cloneable, Serializable
{
	protected final S criterion;

	private static final long serialVersionUID = 1L;


	/**
	 * More useful constructor. The specified criterion can
	 * be used by a subclass to "accept" or "reject" objects.
	 */
	protected SimpleFilter(S criterion) {
		super();
		this.criterion = criterion;
	}

	/**
	 * Construct a simple filter with a null criterion
	 */
	protected SimpleFilter() {
		this(null);
	}

	/**
	 * Return whether the the specified object should be "rejected".
	 * The semantics of "rejected" is determined by the client.
	 */
	protected boolean reject(T o) {
		return ! this.accept(o);
	}

	/**
	 * Return whether the the specified object should be "accepted".
	 * The semantics of "accepted" is determined by the client.
	 */
	public boolean accept(T o) {
		return ! this.reject(o);
	}

	@Override
	@SuppressWarnings("unchecked")
	public SimpleFilter<T, S> clone() {
		try {
			return (SimpleFilter<T, S>) super.clone();
		} catch (CloneNotSupportedException ex) {
			throw new InternalError();
		}
	}

	@Override
	public boolean equals(Object o) {
		if ( ! (o instanceof SimpleFilter)) {
			return false;
		}
		SimpleFilter<?, ?> other = (SimpleFilter<?, ?>) o;
		return (this.criterion == null) ?
			(other.criterion == null) : this.criterion.equals(other.criterion);
	}

	@Override
	public int hashCode() {
		return (this.criterion == null) ? 0 : this.criterion.hashCode();
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.criterion);
	}

}

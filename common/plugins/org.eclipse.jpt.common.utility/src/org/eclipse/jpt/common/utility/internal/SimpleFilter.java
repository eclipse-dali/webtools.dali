/*******************************************************************************
 * Copyright (c) 2005, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal;

import java.io.Serializable;
import org.eclipse.jpt.common.utility.Filter;

/**
 * Simple, abstract implementation of <code>Filter</code>
 * that holds on to a criterion object that can be used in the
 * {@link #accept(Object) accept} or {@link #reject(Object) reject}
 * methods. Subclasses can override either of these methods,
 * depending on which is easier to implement. Note that at least
 * one of these methods <em>must</em> be overridden or
 * an infinite loop will occur. If both of them are overridden,
 * only the {@link #accept(Object) accept} method will be used.
 * <p>
 * This class simplifies the implementation of straightforward inner classes.
 * Here is an example of a filter that can be used by a
 * {@link org.eclipse.jpt.common.utility.internal.iterables.FilteringIterable 
 * FilteringIterable} to return only those strings
 * in the nested iterable that start with <code>"prefix"</code>:
 * <pre>
 * Filter<String> filter = new SimpleFilter<String, String>("prefix") {
 *     public boolean accept(String string) {
 *         return string.startsWith(criterion);
 *     }
 * };
 * </pre>
 */
public abstract class SimpleFilter<T, C>
	implements Filter<T>, Cloneable, Serializable
{
	protected final C criterion;

	private static final long serialVersionUID = 1L;


	/**
	 * Construct a simple filter with a <code>null</code> criterion
	 */
	protected SimpleFilter() {
		this(null);
	}

	/**
	 * More useful constructor. The specified criterion can
	 * be used by a subclass to "accept" or "reject" objects.
	 */
	protected SimpleFilter(C criterion) {
		super();
		this.criterion = criterion;
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

	/**
	 * Return the filter's criterion.
	 */
	public C getCriterion() {
		return this.criterion;
	}

	@Override
	public SimpleFilter<T, C> clone() {
		try {
			@SuppressWarnings("unchecked")
			SimpleFilter<T, C> clone = (SimpleFilter<T, C>) super.clone();
			return clone;
		} catch (CloneNotSupportedException ex) {
			throw new InternalError();
		}
	}

	@Override
	public boolean equals(Object o) {
		if ( ! (o instanceof SimpleFilter<?, ?>)) {
			return false;
		}
		SimpleFilter<?, ?> other = (SimpleFilter<?, ?>) o;
		return Tools.valuesAreEqual(this.criterion, other.criterion);
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

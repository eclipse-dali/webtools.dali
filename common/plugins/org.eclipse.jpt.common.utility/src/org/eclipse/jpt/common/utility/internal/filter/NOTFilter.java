/*******************************************************************************
 * Copyright (c) 2005, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.filter;

import java.io.Serializable;
import org.eclipse.jpt.common.utility.filter.Filter;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * This filter will "accept" any object that is NOT accepted by
 * the wrapped filter.
 */
public class NOTFilter<T>
	implements Filter<T>, Cloneable, Serializable
{
	protected final Filter<T> filter;

	private static final long serialVersionUID = 1L;


	/**
	 * Construct a filter that will "accept" any object that is NOT accepted
	 * by the specified wrapped filter.
	 */
	public NOTFilter(Filter<T> filter) {
		super();
		if (filter == null) {
			throw new NullPointerException();
		}
		this.filter = filter;
	}

	public boolean accept(T o) {
		return ! this.filter.accept(o);
	}

	public Filter<T> getFilter() {
		return this.filter;
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
		if ( ! (o instanceof NOTFilter)) {
			return false;
		}
		@SuppressWarnings("unchecked")
		NOTFilter<T> other = (NOTFilter<T>) o;
		return this.filter.equals(other.filter);
	}

	@Override
	public int hashCode() {
		return this.filter.hashCode();
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.filter);
	}
}

/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.filter;

import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.predicate.Predicate;

/**
 * Filter wrapper that can have its wrapped filter changed,
 * allowing a client to change a previously-supplied filter's
 * behavior mid-stream.
 * 
 * @param <T> the type of objects to be filtered
 * @see #setFilter(Predicate)
 */
public class FilterWrapper<T>
	implements Predicate<T>
{
	protected volatile Predicate<? super T> filter;

	public FilterWrapper(Predicate<? super T> filter) {
		super();
		if (filter == null) {
			throw new NullPointerException();
		}
		this.filter = filter;
	}

	public boolean evaluate(T o) {
		return this.filter.evaluate(o);
	}

	public void setFilter(Predicate<? super T> filter) {
		if (filter == null) {
			throw new NullPointerException();
		}
		this.filter = filter;
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.filter);
	}
}

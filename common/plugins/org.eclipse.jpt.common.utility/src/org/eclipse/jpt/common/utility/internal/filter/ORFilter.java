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

import org.eclipse.jpt.common.utility.filter.Filter;

/**
 * This compound filter will "accept" any object that is accepted by
 * <em>any</em> one of the wrapped filters. If there are <em>no</em> wrapped
 * filters, this filter will always return <code>false</code>.
 * If there are wrapped filters, this filter will
 * exhibit "short-circuit" behavior; i.e. if any wrapped filter "accepts"
 * the operand, no following wrapped filters will be evaluated.
 * 
 * @param <T> the type of objects to be filtered
 */
public class ORFilter<T>
	extends CompoundFilter<T>
{
	private static final long serialVersionUID = 1L;


	/**
	 * Construct a filter that will "accept" any object that is accept by
	 * <em>any</em> one of the specified wrapped filters.
	 */
	public ORFilter(Filter<T>... filters) {
		super(filters);
	}

	public boolean accept(T o) {
		for (Filter<T> filter : this.filters) {
			if (filter.accept(o)) {
				return true;
			}
		}
		return false;
	}

	@Override
	protected String operatorString() {
		return "OR"; //$NON-NLS-1$
	}
}

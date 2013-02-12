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

import org.eclipse.jpt.common.utility.predicate.Predicate;

/**
 * This compound filter will "accept" any object that is accepted by
 * <em>all</em> the wrapped filters. If there are <em>no</em> wrapped
 * filters, this filter will always return <code>true</code>.
 * If there are wrapped filters, this filter will
 * exhibit "short-circuit" behavior; i.e. if any wrapped filter "rejects"
 * the operand, no following wrapped filters will be evaluated.
 * 
 * @param <T> the type of objects to be filtered
 */
public class ANDFilter<T>
	extends CompoundFilter<T>
{
	private static final long serialVersionUID = 1L;


	/**
	 * Construct a filter that will "accept" any object that is accept by
	 * <em>all</em> the specified wrapped filters.
	 */
	public ANDFilter(Predicate<? super T>... filters) {
		super(filters);
	}

	public boolean evaluate(T o) {
		for (Predicate<? super T> filter : this.filters) {
			if ( ! filter.evaluate(o)) {
				return false;
			}
		}
		return true;
	}

	@Override
	protected String operatorString() {
		return "AND"; //$NON-NLS-1$
	}
}

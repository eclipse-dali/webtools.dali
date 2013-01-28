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

import org.eclipse.jpt.common.utility.filter.Filter;

/**
 * This compound filter will "accept" any object that is accepted by either of
 * the wrapped filters, but not both. Both filters will always be evaluated.
 * 
 * @param <T> the type of objects to be filtered
 */
public class XORFilter<T>
	extends CompoundFilter<T>
{
	private static final long serialVersionUID = 1L;


	/**
	 * Construct a filter that will "accept" any object that is accept by either
	 * of the specified wrapped filters, but not by both.
	 */
	@SuppressWarnings("unchecked")
	public XORFilter(Filter<? super T> filter1, Filter<? super T> filter2) {
		super(filter1, filter2);
		if ((filter1 == null) || (filter2 == null)) {
			throw new NullPointerException();
		}
	}

	public boolean accept(T o) {
		return this.filters[0].accept(o) ^ this.filters[1].accept(o);
	}

	@Override
	protected String operatorString() {
		return "XOR"; //$NON-NLS-1$
	}
}

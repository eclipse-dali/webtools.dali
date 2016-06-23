/*******************************************************************************
 * Copyright (c) 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.transformer;

import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.predicate.Predicate;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * Tranformer that "filters" its input.
 * If the input is a member of the set defined by the transformer's filter,
 * the transformer simply returns the input;
 * otherwise, the transformer will return the default output.
 * 
 * @param <I> input: the type of the object passed to and
 * returned by the transformer
 */
public class FilteringTransformer<I>
	implements Transformer<I, I>
{
	private final Predicate<? super I> filter;
	private final I defaultOutput;


	public FilteringTransformer(Predicate<? super I> filter, I defaultOutput) {
		super();
		if (filter == null) {
			throw new NullPointerException();
		}
		this.filter = filter;
		this.defaultOutput = defaultOutput;
	}

	public I transform(I input) {
		return this.filter.evaluate(input) ? input : this.defaultOutput;
	}

	public Predicate<? super I> getFilter() {
		return this.filter;
	}

	public I getDefaultOutput() {
		return this.defaultOutput;
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.filter);
	}
}

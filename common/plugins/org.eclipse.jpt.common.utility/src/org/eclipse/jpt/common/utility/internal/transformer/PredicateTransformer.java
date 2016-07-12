/*******************************************************************************
 * Copyright (c) 2013, 2016 Oracle. All rights reserved.
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
 * Adapt the {@link Predicate} interface to the {@link Transformer} interface.
 * 
 * @param <I> input: the type of the object passed to the transformer (and
 *   forwarded to the wrapped predicate)
 * @see Predicate
 */
public class PredicateTransformer<I>
	implements Transformer<I, Boolean>
{
	private final Predicate<? super I> predicate;


	public PredicateTransformer(Predicate<? super I> predicate) {
		super();
		if (predicate == null) {
			throw new NullPointerException();
		}
		this.predicate = predicate;
	}

	public Boolean transform(I input) {
		return Boolean.valueOf(this.predicate.evaluate(input));
	}

	public Predicate<? super I> getPredicate() {
		return this.predicate;
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.predicate);
	}
}

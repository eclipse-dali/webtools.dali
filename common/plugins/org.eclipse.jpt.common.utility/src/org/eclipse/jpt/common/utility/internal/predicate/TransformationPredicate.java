/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.predicate;

import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.predicate.Predicate;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * Predicate that wraps another predicate and uses a {@link Transformer} to
 * convert the variable before forwarding it to the wrapped predicate.
 * 
 * @param <I> the type of objects to be evaluated by the predicate (i.e.
 *   passed to its transformer before being forwarded to the wrapped predicate)
 * @param <O> the type of objects output by the transformer and to be evaluated
 *   by the wrapped predicate
 */
public class TransformationPredicate<I, O>
	implements Predicate<I>
{
	protected final Predicate<? super O> predicate;
	protected final Transformer<? super I, O> transformer;


	public TransformationPredicate(Predicate<? super O> predicate, Transformer<? super I, O> transformer) {
		super();
		if ((predicate == null) || (transformer == null)) {
			throw new NullPointerException();
		}
		this.predicate = predicate;
		this.transformer = transformer;
	}

	public boolean evaluate(I variable) {
		return this.predicate.evaluate(this.transformer.transform(variable));
	}

	public Predicate<? super O> getPredicate() {
		return this.predicate;
	}

	public Transformer<? super I, O> getTransformer() {
		return this.transformer;
	}

	@Override
	public boolean equals(Object o) {
		if ( ! (o instanceof TransformationPredicate)) {
			return false;
		}
		@SuppressWarnings("unchecked")
		TransformationPredicate<I, O> other = (TransformationPredicate<I, O>) o;
		return this.predicate.equals(other.predicate) && this.transformer.equals(other.transformer);
	}

	@Override
	public int hashCode() {
		return this.predicate.hashCode() ^ this.transformer.hashCode();
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.predicate);
	}
}

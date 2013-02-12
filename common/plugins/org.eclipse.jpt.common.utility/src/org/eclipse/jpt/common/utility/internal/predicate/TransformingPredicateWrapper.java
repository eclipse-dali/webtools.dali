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

import java.io.Serializable;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.predicate.Predicate;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * Predicate that wraps another predicate and uses a {@link Transformer} to
 * convert the variable before forwarding it to the wrapped predicate.
 * 
 * @param <I> the type of objects to be evaluated by the predicate (i.e.
 *   passed to its transformer before being forwarded to the wrapped predicate)
 * @param <O> the type of objects to be evaluated by the wrapped predicate
 */
public class TransformingPredicateWrapper<I, O>
	implements Predicate<I>, Cloneable, Serializable
{
	protected final Predicate<? super O> predicate;
	protected final Transformer<? super I, O> transformer;

	private static final long serialVersionUID = 1L;


	public TransformingPredicateWrapper(Predicate<? super O> predicate, Transformer<? super I, O> transformer) {
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
	public TransformingPredicateWrapper<I, O> clone() {
		try {
			@SuppressWarnings("unchecked")
			TransformingPredicateWrapper<I, O> clone = (TransformingPredicateWrapper<I, O>) super.clone();
			return clone;
		} catch (CloneNotSupportedException ex) {
			throw new InternalError();
		}
	}

	@Override
	public boolean equals(Object o) {
		if ( ! (o instanceof TransformingPredicateWrapper)) {
			return false;
		}
		@SuppressWarnings("unchecked")
		TransformingPredicateWrapper<I, O> other = (TransformingPredicateWrapper<I, O>) o;
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

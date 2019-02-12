/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
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
 * Predicate that wraps a {@link Transformer} and converts its output value
 * to a predicate evaluation result (i.e. <code>true</code> or
 * <code>false</code>). If the transformer's output is <code>null</code>,
 * the predicate will evaluate to its configured null value.
 * 
 * @param <V> the type of objects to be evaluated by the predicate (i.e.
 *   passed to its transformer)
 */
public class TransformerPredicate<V>
	implements Predicate<V>, Serializable
{
	protected final Transformer<? super V, Boolean> transformer;

	private static final long serialVersionUID = 1L;


	public TransformerPredicate(Transformer<? super V, Boolean> transformer) {
		super();
		if (transformer == null) {
			throw new NullPointerException();
		}
		this.transformer = transformer;
	}

	public boolean evaluate(V variable) {
		return this.transformer.transform(variable).booleanValue();
	}

	@Override
	public boolean equals(Object o) {
		if ( ! (o instanceof TransformerPredicate)) {
			return false;
		}
		@SuppressWarnings("unchecked")
		TransformerPredicate<V> other = (TransformerPredicate<V>) o;
		return this.transformer.equals(other.transformer);
	}

	@Override
	public int hashCode() {
		return this.transformer.hashCode();
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.transformer);
	}
}

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
 * Predicate that wraps a {@link Transformer} and converts its output value
 * to a predicate evaluation result (i.e. <code>true</code> or
 * <code>false</code>). If the transformer's output is <code>null</code>,
 * the predicate will evaluate to its configured null value.
 * 
 * @param <V> the type of objects to be evaluated by the predicate (i.e.
 *   passed to its transformer)
 */
public class TransformerPredicate<V>
	implements Predicate<V>, Cloneable, Serializable
{
	protected final Transformer<? super V, Boolean> transformer;
	protected final boolean nullValue;

	private static final long serialVersionUID = 1L;


	public TransformerPredicate(Transformer<? super V, Boolean> transformer, boolean nullValue) {
		super();
		if (transformer == null) {
			throw new NullPointerException();
		}
		this.transformer = transformer;
		this.nullValue = nullValue;
	}

	public boolean evaluate(V variable) {
		Boolean value = this.evaluate_(variable);
		return (value == null) ? this.nullValue : value.booleanValue();
	}

	private Boolean evaluate_(V variable) {
		return this.transformer.transform(variable);
	}

	@Override
	public TransformerPredicate<V> clone() {
		try {
			@SuppressWarnings("unchecked")
			TransformerPredicate<V> clone = (TransformerPredicate<V>) super.clone();
			return clone;
		} catch (CloneNotSupportedException ex) {
			throw new InternalError();
		}
	}

	@Override
	public boolean equals(Object o) {
		if ( ! (o instanceof TransformerPredicate)) {
			return false;
		}
		@SuppressWarnings("unchecked")
		TransformerPredicate<V> other = (TransformerPredicate<V>) o;
		return this.transformer.equals(other.transformer) && (this.nullValue == other.nullValue);
	}

	@Override
	public int hashCode() {
		return this.transformer.hashCode() ^ Boolean.valueOf(this.nullValue).hashCode();
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.transformer);
	}
}

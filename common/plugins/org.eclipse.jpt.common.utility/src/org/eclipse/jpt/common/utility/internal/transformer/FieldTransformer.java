/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.transformer;

import java.io.Serializable;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * A transformer that uses Java reflection to transform an object into the
 * value of one of its fields. Checked exceptions are converted to
 * {@link RuntimeException}s.
 * <p>
 * <strong>NB:</strong> The actual field is determined at execution time,
 * not construction time. As a result, the transformer can be used to emulate
 * "duck typing".
 * 
 * @param <I> input: the type of objects passed to the transformer
 * @param <O> output: the type of objects returned by the transformer
 * 
 * @see MethodTransformer
 * @see Class#getDeclaredField(String)
 * @see java.lang.reflect.Field#get(Object)
 */
public class FieldTransformer<I, O>
	implements Transformer<I, O>, Serializable
{
	private final String fieldName;

	private static final long serialVersionUID = 1L;


	public FieldTransformer(String fieldName) {
		super();
		if (fieldName == null) {
			throw new NullPointerException();
		}
		this.fieldName = fieldName;
	}

	@SuppressWarnings("unchecked")
	public O transform(I input) {
		return (O) ObjectTools.get(input, this.fieldName);
	}

	@Override
	public boolean equals(Object o) {
		if ( ! (o instanceof FieldTransformer<?, ?>)) {
			return false;
		}
		FieldTransformer<?, ?> other = (FieldTransformer<?, ?>) o;
		return ObjectTools.equals(this.fieldName, other.fieldName);
	}

	@Override
	public int hashCode() {
		return this.fieldName.hashCode();
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.fieldName);
	}
}

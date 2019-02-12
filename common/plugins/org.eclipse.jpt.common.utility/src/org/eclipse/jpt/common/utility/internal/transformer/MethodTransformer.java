/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.transformer;

import java.io.Serializable;
import java.util.Arrays;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.ClassTools;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * A transformer that uses Java reflection to transform an object into the
 * value returned of one of its methods.
 * <p>
 * <strong>NB:</strong> The actual method is determined at execution time,
 * not construction time. As a result, the transformer can be used to emulate
 * "duck typing".
 * 
 * @param <I> input: the type of objects passed to the transformer
 * @param <O> output: the type of objects returned by the transformer
 * 
 * @see FieldTransformer
 * @see Class#getDeclaredMethod(String, Class...)
 * @see java.lang.reflect.Method#invoke(Object, Object...)
 */
public class MethodTransformer<I, O>
	implements Transformer<I, O>, Serializable
{
	private final String methodName;
	private final Class<?>[] parameterTypes;
	private final Object[] arguments;

	private static final long serialVersionUID = 1L;


	public MethodTransformer(String methodName, Class<?>[] parameterTypes, Object[] arguments) {
		super();
		if ((methodName == null) || ArrayTools.isOrContainsNull(parameterTypes) || (arguments == null)) {
			throw new NullPointerException();
		}
		this.methodName = methodName;
		this.parameterTypes = parameterTypes;
		this.arguments = arguments;
	}

	@SuppressWarnings("unchecked")
	public O transform(I input) {
		return (O) ObjectTools.execute(input, this.methodName, this.parameterTypes, this.arguments);
	}

	@Override
	public boolean equals(Object o) {
		if ( ! (o instanceof MethodTransformer<?, ?>)) {
			return false;
		}
		MethodTransformer<?, ?> other = (MethodTransformer<?, ?>) o;
		return ObjectTools.equals(this.methodName, other.methodName) &&
				Arrays.equals(this.parameterTypes, other.parameterTypes) &&
				Arrays.equals(this.arguments, other.arguments);
	}

	@Override
	public int hashCode() {
		return this.methodName.hashCode() ^ Arrays.hashCode(this.parameterTypes) ^ Arrays.hashCode(this.arguments);
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, ClassTools.buildMethodSignature(this.methodName, this.parameterTypes));
	}
}

/*******************************************************************************
 * Copyright (c) 2013, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.closure;

import java.io.Serializable;
import java.util.Arrays;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.ClassTools;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.closure.Closure;

/**
 * A closure that uses Java reflection to invoke a method on the argument.
 * <p>
 * <strong>NB:</strong> The actual method is determined at execution time,
 * not construction time. As a result, the closure can be used to emulate
 * "duck typing".
 * 
 * @param <A> the type of the object passed to the closure
 * 
 * @see Class#getDeclaredMethod(String, Class...)
 * @see java.lang.reflect.Method#invoke(Object, Object...)
 */
public class MethodClosure<A>
	implements Closure<A>, Serializable
{
	private final String methodName;
	private final Class<?>[] parameterTypes;
	private final Object[] arguments;

	private static final long serialVersionUID = 1L;


	public MethodClosure(String methodName, Class<?>[] parameterTypes, Object[] arguments) {
		super();
		if (methodName == null) {
			throw new NullPointerException();
		}
		this.methodName = methodName;
		if (ArrayTools.isOrContainsNull(parameterTypes)) {
			throw new NullPointerException();
		}
		this.parameterTypes = parameterTypes;
		if (arguments == null) {
			throw new NullPointerException();
		}
		this.arguments = arguments;
	}

	public void execute(A argument) {
		ObjectTools.execute(argument, this.methodName, this.parameterTypes, this.arguments);
	}

	@Override
	public boolean equals(Object o) {
		if ( ! (o instanceof MethodClosure<?>)) {
			return false;
		}
		MethodClosure<?> other = (MethodClosure<?>) o;
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

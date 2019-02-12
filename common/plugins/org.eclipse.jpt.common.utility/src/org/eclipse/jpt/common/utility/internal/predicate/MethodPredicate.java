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

import java.util.Arrays;
import org.eclipse.jpt.common.utility.internal.ClassTools;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.predicate.Predicate;

/**
 * This predicate evaluates to the (<code>boolean</code>) value one of the
 * variable's methods.
 * <p>
 * <strong>NB:</strong> The actual method is determined at execution time,
 * not construction time. As a result, the transformer can be used to emulate
 * "duck typing".
 * 
 * @param <V> the type of objects to be evaluated by the predicate
 */
public class MethodPredicate<V>
	implements Predicate<V>
{
	private final String methodName;
	private final Class<?>[] parameterTypes;
	private final Object[] arguments;


	/**
	 * Construct a predicate that evaluates to the (<code>boolean</code>) value
	 * of the specified method.
	 */
	public MethodPredicate(String methodName, Class<?>[] parameterTypes, Object[] arguments) {
		super();
		if ((methodName == null) || (parameterTypes == null) || (arguments == null)) {
			throw new NullPointerException();
		}
		this.methodName = methodName;
		this.parameterTypes = parameterTypes;
		this.arguments = arguments;
	}

	public boolean evaluate(V variable) {
		return this.evaluate_(variable).booleanValue();
	}

	private Boolean evaluate_(V variable) {
		return (Boolean) ObjectTools.execute(variable, this.methodName, this.parameterTypes, this.arguments);
	}

	@Override
	public boolean equals(Object o) {
		if ( ! (o instanceof MethodPredicate<?>)) {
			return false;
		}
		MethodPredicate<?> other = (MethodPredicate<?>) o;
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

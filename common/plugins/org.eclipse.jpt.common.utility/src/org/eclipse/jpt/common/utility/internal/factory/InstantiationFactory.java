/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.factory;

import org.eclipse.jpt.common.utility.factory.Factory;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.ClassTools;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * A factory that will instantiate (via reflection) a configured {@link Class}.
 * Checked exceptions are converted to
 * {@link RuntimeException}s.
 * @param <T> the type of the object returned by the factory
 * @see Class#newInstance()
 */
public class InstantiationFactory<T>
	implements Factory<T>
{
	private final Class<? extends T> clazz;
	private final Class<?>[] parameterTypes;
	private final Object[] arguments;


	public InstantiationFactory(Class<? extends T> clazz, Class<?>[] parameterTypes, Object[] arguments) {
		super();
		if ((clazz == null) || ArrayTools.isOrContainsNull(parameterTypes) || (arguments == null)) {
			throw new NullPointerException();
		}
		this.clazz = clazz;
		this.parameterTypes = parameterTypes;
		this.arguments = arguments;
	}

	/**
	 * Instantiate the class via reflection.
	 */
	public T create() {
		return ClassTools.newInstance(this.clazz, this.parameterTypes, this.arguments);
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.clazz.getSimpleName());
	}
}

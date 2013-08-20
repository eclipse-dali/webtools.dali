/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.factory;

import java.io.Serializable;
import org.eclipse.jpt.common.utility.factory.Factory;

/**
 * A factory that will always return <code>null</code>.
 * 
 * @param <T> the type of the object returned by the factory
 * 
 * @see FactoryAdapter
 */
public final class NullFactory<T>
	implements Factory<T>, Serializable
{
	@SuppressWarnings("rawtypes")
	public static final Factory INSTANCE = new NullFactory();

	@SuppressWarnings("unchecked")
	public static <T> Factory<T> instance() {
		return INSTANCE;
	}

	// ensure single instance
	private NullFactory() {
		super();
	}

	/**
	 * Return <code>null</code>.
	 */
	public T create() {
		return null;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}

	private static final long serialVersionUID = 1L;
	private Object readResolve() {
		// replace this object with the singleton
		return INSTANCE;
	}
}

/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.factory;

import java.io.Serializable;
import org.eclipse.jpt.common.utility.factory.Factory;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * Singleton implementation of the factory interface that will throw an
 * {@link UnsupportedOperationException exception} when executed.
 */
public final class DisabledFactory<T>
	implements Factory<T>, Serializable
{
	@SuppressWarnings("rawtypes")
	public static final Factory INSTANCE = new DisabledFactory();

	@SuppressWarnings("unchecked")
	public static <T> Factory<T> instance() {
		return INSTANCE;
	}

	// ensure single instance
	private DisabledFactory() {
		super();
	}

	// throw an exception
	public T create() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String toString() {
		return ObjectTools.singletonToString(this);
	}

	private static final long serialVersionUID = 1L;
	private Object readResolve() {
		// replace this object with the singleton
		return INSTANCE;
	}
}
